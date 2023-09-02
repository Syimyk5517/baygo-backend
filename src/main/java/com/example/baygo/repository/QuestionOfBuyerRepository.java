package com.example.baygo.repository;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.model.BuyerQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOfBuyerRepository extends JpaRepository<BuyerQuestion, Long> {
    @Query("""
           SELECT COUNT (bq) FROM BuyerQuestion bq
           JOIN bq.subProduct sp
           JOIN sp.product p
           WHERE p.seller.id = :sellerId AND bq.answer IS NULL
           """)
    int countOfUnansweredBySellerId(Long sellerId);

    @Query("""
           SELECT COUNT (bq) FROM BuyerQuestion bq
           JOIN bq.subProduct sp
           JOIN sp.product p
           WHERE p.seller.id = :sellerId AND bq.answer IS NOT NULL
           """)
    int countOfAnsweredBySellerId(Long sellerId);
    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.BuyerQuestionResponse(
                     bq.id, sp.id, sp.mainImage, p.name,
                     bq.question, bq.answer, sp.articulOfSeller,
                     sp.articulBG, CAST(sp.articulBG as STRING )
                     )
                 FROM BuyerQuestion bq
                 JOIN SubProduct sp ON bq.subProduct.id = sp.id
                 JOIN Product p ON sp.product.id = p.id
                 WHERE p.seller.id = :sellerId
                 AND (( :isAnswered = FALSE AND bq.answer IS NULL )
                  OR ( :isAnswered = TRUE AND bq.answer IS NOT NULL ))
                 AND (( p.name iLIKE CONCAT("%", :keyWord, "%"))
                  OR ( sp.articulOfSeller iLIKE CONCAT("%", :keyWord, "%"))
                  OR ( CAST(sp.articulBG AS STRING ) iLIKE CONCAT(:keyWord, "%") )
                  OR ( :keyWord IS NULL ))
             """)
    Page<BuyerQuestionResponse> getAllQuestions(Long sellerId,
                                                boolean isAnswered,
                                                String keyWord,
                                                Pageable pageable);
    @Query("SELECT b FROM BuyerQuestion b WHERE b.subProduct.id = ?1")
    List<BuyerQuestion> findAllQuestionsBySubProductId(Long subProductId);

}
