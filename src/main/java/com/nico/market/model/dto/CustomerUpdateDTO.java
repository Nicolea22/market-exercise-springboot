package com.nico.market.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerUpdateDTO extends CustomerDTO {

    private UUID id;

    @Size(min=5, max=20)
    private String description;
}
