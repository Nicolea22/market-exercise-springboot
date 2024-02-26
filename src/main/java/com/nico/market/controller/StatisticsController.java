package com.nico.market.controller;


import com.nico.market.model.dto.MarketCreationDTO;
import com.nico.market.model.dto.response.StatsResponseDTO;
import com.nico.market.service.StatisticsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Get stats from the market", description = "Get stats from the market")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/stats")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<StatsResponseDTO> stats() {
        return statisticsService.makeStats();
    }

}
