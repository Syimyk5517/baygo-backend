package com.example.baygo.db.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomReviewRepository {
    public String getAllHigh(){
        return """
                select rev.id,
                       ri.images as review_photo,
                       p.articul as vonder_code,
                       s.barcode as barcode,
                       p.name as product_name,
                       p.brand as product_brand,
                       rev.grade as grade_of_product,
                       sp.color as color_product
                       from reviews rev
                    join products p on p.id = rev.product_id
                    join review_images ri on rev.id = ri.review_id
                    join sub_products sp on p.id = sp.product_id
                    join sizes s on sp.id = s.sub_product_id
                    where rev.grade > 4.5 %s
                """;
    }
    public String getAllLow (){
        return """
                select rev.id,
                       ri.images as review_photo,
                       p.articul as vonder_code,
                       s.barcode as barcode,
                       p.name as product_name,
                       p.brand as product_brand,
                       rev.grade as grade_of_product,
                       sp.color as color_product
                from reviews rev
                         join products p on p.id = rev.product_id
                         join review_images ri on rev.id = ri.review_id
                         join sub_products sp on p.id = sp.product_id
                         join sizes s on sp.id = s.sub_product_id
                         where rev.grade < 4.5 %s
                """;
    }


    public String keyword(){
        return """
                and (p.name ilike ? or p.brand ilike ? or p.articul ilike ? or cast(s.barcode as text) ilike ?)
                """;
    }

    public String limitOffset() {
        return """
                LIMIT ? OFFSET ?
                """;
    }
}
