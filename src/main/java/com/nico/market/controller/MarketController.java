package com.nico.market.controller;


import com.nico.market.model.dto.MarketCreationDTO;
import com.nico.market.model.dto.MarketUpdateDTO;
import com.nico.market.service.MarketService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;
    private final String MARKET_SERVICE = "marketService";

    @Operation(summary = "Create market", description = "Create market")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@RateLimiter(name = MARKET_SERVICE, fallbackMethod = "rateLimiterFallback")
    public MarketCreationDTO save(@RequestBody MarketCreationDTO market) {
        return marketService.save(market);
    }

    @GetMapping
    @Operation(summary = "Retrieve a list of markets by Id", description = "Retrieve a list of markets by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@RateLimiter(name = MARKET_SERVICE, fallbackMethod = "rateLimiterFallback")
    public List<MarketCreationDTO> getAll() {
        return marketService.findAll();
    }

    @Operation(summary = "Retrieve a market by Id", description = "Retrieve a market by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@RateLimiter(name = MARKET_SERVICE, fallbackMethod = "rateLimiterFallback")
    public MarketCreationDTO get(@PathVariable String id) {
        return marketService.getById(id);
    }

    @Operation(summary = "Delete a market by Id", description = "Delete a market by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@RateLimiter(name = MARKET_SERVICE, fallbackMethod = "rateLimiterFallback")
    public String delete(@PathVariable String id) {
        marketService.delete(id);
        return id;
    }

    @Operation(summary = "Update a market by Id", description = "Delete a market by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@RateLimiter(name = MARKET_SERVICE, fallbackMethod = "rateLimiterFallback")
    public MarketCreationDTO update(@RequestBody MarketUpdateDTO market) {
       return marketService.update(market);
    }

    public ResponseEntity<String> rateLimiterFallback(Exception e) {
        return new ResponseEntity<>("Order service does not permit", HttpStatus.TOO_MANY_REQUESTS);
    }

}
