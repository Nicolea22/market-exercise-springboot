package com.nico.market.service;

import com.nico.market.model.dto.MarketCreationDTO;
import com.nico.market.model.dto.MarketUpdateDTO;

import java.util.List;

public interface MarketService {
    MarketCreationDTO save(MarketCreationDTO market);
    MarketCreationDTO getById(String id);
    List<MarketCreationDTO> findAll();
    void delete(String id);
    MarketCreationDTO update(MarketUpdateDTO market);
}
