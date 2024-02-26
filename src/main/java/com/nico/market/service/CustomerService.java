package com.nico.market.service;

import com.nico.market.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerCreationDTO save(CustomerCreationDTO customer);
    CustomerCreationDTO getById(String id);
    List<CustomerCreationDTO> findAll();
    String delete(String id);
    CustomerCreationDTO update(CustomerUpdateDTO market);
}
