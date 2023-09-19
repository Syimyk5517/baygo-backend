package com.example.baygo.repository;

import com.example.baygo.db.dto.request.UpdateSubProductDTO;
import com.example.baygo.db.dto.response.product.ColorsOfSubProductResponse;
import com.example.baygo.db.model.SubProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {

    @Query("""
            SELECT NEW com.example.baygo.db.dto.request.UpdateSubProductDTO(
            sp.id, sp.colorHexCode, sp.color, sp.price, sp.mainImage, sp.description, sp.articulOfSeller, sp.height, sp.width, sp.length, sp.weight)
            FROM Product p
            JOIN p.subProducts sp
            WHERE p.id = ?1
            """)
    List<UpdateSubProductDTO> getSubProductsByProductId(Long productId);

    @Query("SELECT sp.images FROM SubProduct sp WHERE sp.id = ?1")
    List<String> getImagesSubProductId(Long subProductId);

    List<ColorsOfSubProductResponse> findSubProductByProductId(Long productId);

}