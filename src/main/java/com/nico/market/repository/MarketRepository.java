package com.nico.market.repository;

import com.nico.market.model.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MarketRepository extends JpaRepository<Market, UUID> {
    @Query(value = "SELECT m.CODE FROM MARKET m WHERE m.COUNTRY = :country", nativeQuery = true)
    List<Object> getMarketsFrom(String country);

}
