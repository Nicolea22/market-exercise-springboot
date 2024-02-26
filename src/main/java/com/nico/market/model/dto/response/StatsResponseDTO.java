package com.nico.market.model.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponseDTO {
    private String country;
    private HashMap<String, BigDecimal> market;
}
