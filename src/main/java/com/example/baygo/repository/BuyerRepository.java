package com.example.baygo.repository;
import com.example.baygo.db.dto.response.buyer.BuyerProfileInfoResponse;
import com.example.baygo.db.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer getBuyerByUserId (Long userId);
    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.BuyerProfileInfoResponse(b.photo, b.fullName, b.dateOfBirth, b.gender, b.address, u.email, u.phoneNumber ) " +
            "FROM Buyer b " +
            " join b.user u " +
            "WHERE b.id = :buyerId")
    BuyerProfileInfoResponse getProfileInfo(@Param("buyerId") Long buyerId);

    @Modifying
    @Query(value = "delete from buyers_baskets bb where bb.sub_products_size_id = :sizeId", nativeQuery = true)
    void removeSizeFromBaskets(Long sizeId);


    @Modifying
    @Query(value = "delete from buyers_last_views bb where bb.sub_products_id = :subProductId", nativeQuery = true)
    void removeSubProductFromLastViews(Long subProductId);

    @Modifying
    @Query(value = "delete from buyers_favorites bb where bb.sub_products_id = :subProductId", nativeQuery = true)
    void removeSubProductFromFavorites(Long subProductId);
}