package com.nico.market.service.impl;

import com.nico.market.exception.BadRequestException;
import com.nico.market.exception.CustomerNotFoundException;
import com.nico.market.model.dto.CustomerCreationDTO;
import com.nico.market.model.dto.CustomerDTO;
import com.nico.market.model.dto.CustomerUpdateDTO;
import com.nico.market.model.entity.Customer;
import com.nico.market.model.mapper.CustomerMapper;
import com.nico.market.repository.CustomerRepository;
import com.nico.market.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public CustomerCreationDTO save(CustomerCreationDTO customer) {
        customer.setId(UUID.randomUUID());
        return mapper.toDto(customerRepository.save(mapper.toEntity(customer)));
    }

    public CustomerCreationDTO getById(String id) {
        Optional<Customer> customer = customerRepository.findById(UUID.fromString(id));
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id: " + id + "Not Found");
        }
        return mapper.toDto(customer.get());
    }

    public List<CustomerCreationDTO> findAll() {
        ArrayList<CustomerCreationDTO> result = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        for(Customer customer : customers) {
            result.add(mapper.toDto(customer));
        }
        return result;
    }

    public String delete(String id) {
        log.debug("Deleting customer with id: " + id);
        Optional<Customer> customer = customerRepository.findById(UUID.fromString(id));
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id: " + id + "Not Found");
        }
        customerRepository.delete(customer.get());
        return id;
    }

    public CustomerCreationDTO update(CustomerUpdateDTO customerDto) {
        if (customerDto.getDescription() == null) {
            throw new BadRequestException("There is nothing to update");
        }
        Optional<Customer> customer = customerRepository.findById(customerDto.getId());
        if(customer.isEmpty()) {
            throw new BadRequestException("Customer with id: " + customerDto.getId() + "does not exist");
        }
        customer.get().setDescription(customerDto.getDescription());
        return mapper.toDto(customerRepository.save(customer.get()));
    }
}
