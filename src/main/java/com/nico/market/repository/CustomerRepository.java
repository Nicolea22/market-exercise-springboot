package com.nico.market.repository;

import com.nico.market.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query(value = "SELECT m.COUNTRY, m.CODE FROM CUSTOMER c INNER JOIN MARKET m ON c.MARKET_ID = m.id  WHERE m.COUNTRY = :country", nativeQuery = true)
    List<Object> findFrom(String country);

    @Query(value = "SELECT COUNT(*) FROM CUSTOMER c WHERE c.MARKET_ID IS NULL", nativeQuery = true)
    Integer amountWithoutMarket();

}
