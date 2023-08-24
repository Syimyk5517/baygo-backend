package com.example.baygo.repository;

import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.enums.SupplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query("select new com.example.baygo.db.dto.response.SuppliesResponse(" +
            "s.id,s.supplyNumber,s.supplyType,s.createdAt,s.quantityOfProducts,s.acceptedProducts,s.commission,s.supplyCost,s.plannedDate,s.actualDate,u.phoneNumber,s.status) " +
            "FROM Supply s " +
            "JOIN Seller s2 ON s.seller.id = s2.id " +
            "JOIN User u ON s2.user.id = u.id WHERE u.id = ?1 " +
            "AND (COALESCE(?2, '') = '' OR s.supplyNumber LIKE CONCAT('%', ?2)) " +
            "AND (?3 IS NULL OR s.status = ?3)")
    Page<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status,Pageable pageable);
}