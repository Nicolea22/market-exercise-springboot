package com.nico.market.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MarketCreationDTO extends MarketDTO {
    private UUID id;

    @Size(min=2, max=20)
    @NotBlank
    private String code;

    @Size(min=5, max=20)
    @NotBlank
    private String description;

    @Size(max=30)
    @NotBlank
    private String country;

    private List<CustomerCreationDTO> customers;
}
