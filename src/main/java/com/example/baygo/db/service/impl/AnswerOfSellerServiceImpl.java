package com.example.baygo.db.service.impl;

import com.example.baygo.db.config.jwt.JwtService;
import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.BuyerQuestion;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.repository.BuyerQuestionRepository;
import com.example.baygo.db.service.AnswerOfSellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerOfSellerServiceImpl implements AnswerOfSellerService {
    private final BuyerQuestionRepository buyerQuestionRepository;
    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;

    @Override
    public SimpleResponse addAnswer(AnswerOfSellerRequest request) {
        BuyerQuestion question = buyerQuestionRepository.findById(request.questionId()).orElseThrow(() -> new NotFoundException("Вопрос с идентификатором: " + request.questionId() + " не найден!"));
        question.setAnswer(request.answer());
        question.setReplyDate(LocalDateTime.now());
        buyerQuestionRepository.save(question);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Ответ успешно сохранено!").build();
    }

    @Override
    public PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        String sql = """
                         select (SELECT i.images FROM sub_product_images i
                                        join sub_products sp on sp.id = i.sub_product_id
                                        where sp.product_id = p.id LIMIT 1) as image,
                       b.question as question, p.articul as articul, p.name as name, b.created_at as create_at
                from buyer_questions b
                         join products p on p.id = b.product_id
                         join sellers s on s.id = p.seller_id
                where s.id = %s %s
                                                """;
        List<Object> params = new ArrayList<>();

        String keywordCondition = "";
        if (keyWord != null) {
            keywordCondition = "AND p.articul iLIKE ? ";
            params.add("%" + keyWord + "%");
        }
        sql = String.format(sql, seller.getId(), keywordCondition);

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") as count_query";
        int count = jdbcTemplate.queryForObject(countSql, params.toArray(), Integer.class);
        int totalPage = (int) Math.ceil((double) count / pageSize);
        int offset = (page - 1) * pageSize;
        sql = String.format(sql + "LIMIT %s OFFSET %s", pageSize, offset);

        List<BuyerQuestionResponse> questions = jdbcTemplate.query(sql, params.toArray(), (resultSet, i) -> new BuyerQuestionResponse(
                resultSet.getString("image"),
                resultSet.getString("question"),
                resultSet.getString("articul"),
                resultSet.getString("name"),
                resultSet.getDate("create_at").toLocalDate()));
        return PaginationResponse.<BuyerQuestionResponse>builder()
                .currentPage(page)
                .totalPages(totalPage)
                .elements(questions).build();
    }
}
