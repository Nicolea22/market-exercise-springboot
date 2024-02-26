package com.nico.market.service;

import com.nico.market.model.dto.response.StatsResponseDTO;

import java.util.List;

public interface StatisticsService {
    List<StatsResponseDTO> makeStats();
}
