package com.example.baygo.db.repository;

import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.Seller;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

}