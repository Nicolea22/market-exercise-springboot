package com.nico.market.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerCreationDTO extends CustomerDTO {

    private UUID id;

    @Size(min=5, max=20)
    @NotBlank
    private String description;
}
