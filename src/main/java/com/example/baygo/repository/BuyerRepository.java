package com.example.baygo.repository;


import com.example.baygo.db.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.BuyerProfileInfoResponse(b.id, b.photo, b.fullName, u.email, u.phoneNumber ) " +
            "FROM Buyer b " +
            " join b.user u " +
            "WHERE b.id = :buyerId")
    BuyerProfileInfoResponse getProfileInfo(@Param("buyerId") Long buyerId);

    @Modifying
    @Query(value = "delete from buyers_baskets bb where bb.sub_products_size_id = :sizeId", nativeQuery = true)
    void removeSizeFromBaskets(Long sizeId);


    @Modifying
    @Query(value = "delete from buyers_last_views bb where bb.sub_products_id = :subProductId", nativeQuery = true)
    void removeSizeFromLastViews(Long subProductId);
    @Modifying
    @Query(value = "delete from buyers_favorites bb where bb.sub_products_id = :subProductId", nativeQuery = true)
    void removeSizeFromFavorites(Long subProductId);
}