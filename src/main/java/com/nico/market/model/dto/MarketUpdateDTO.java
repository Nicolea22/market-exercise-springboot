package com.nico.market.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MarketUpdateDTO extends MarketDTO {

    private UUID id;

    @Size(min=2, max=10)
    private String code;

    @Size(max=20)
    private String description;

    @Size(max=30)
    private String country;

    private List<CustomerCreationDTO> customers;

    public MarketUpdateDTO(MarketUpdateDTO market) {
        if(market.getId() != null){
            this.id = market.getId();
        }
        if(market.getCode() != null) {
            this.code = market.getCode();
        }
        if(market.getDescription() != null) {
            this.description = market.getDescription();
        }
        if(market.getCountry() != null) {
            this.country = market.getCountry();
        }
        if(market.getCustomers() != null) {
            this.customers = market.getCustomers();
        }
    }
}


