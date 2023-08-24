package com.example.baygo.repository;

import com.example.baygo.db.dto.response.buyer.BuyerProfileInfoResponse;
import com.example.baygo.db.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.BuyerProfileInfoResponse(b.id, b.fullName, u.email, u.phoneNumber ) " +
            "FROM Buyer b " +
            " join b.user u " +
            "WHERE b.id = :buyerId")
    BuyerProfileInfoResponse getProfileInfo(@Param("buyerId") Long buyerId);
}