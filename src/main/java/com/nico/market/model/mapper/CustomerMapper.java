package com.nico.market.model.mapper;

import com.nico.market.model.dto.CustomerCreationDTO;
import com.nico.market.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerCreationDTO toDto(Customer customer);
    Customer toEntity(CustomerCreationDTO customer);
}
