package com.nico.market.controller;

import com.nico.market.model.dto.CustomerCreationDTO;
import com.nico.market.model.dto.CustomerUpdateDTO;
import com.nico.market.service.CustomerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final String CUSTOMER_SERVICE = "customerService";
    private final CustomerService customerService;

    @Operation(summary = "Create customer", description = "Create customer")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CustomerCreationDTO save(@RequestBody CustomerCreationDTO customer) {
        return customerService.save(customer);
    }

    @GetMapping
    @Operation(summary = "Retrieve a list of customers by Id", description = "Retrieve a list of customers by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<CustomerCreationDTO> getAll() {
        return customerService.findAll();
    }

    @Operation(summary = "Retrieve a customer by Id", description = "Retrieve a customer by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CustomerCreationDTO get(@PathVariable String id) {
        return customerService.getById(id);
    }

    @Operation(summary = "Delete a customer by Id", description = "Delete a customer by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String delete(@PathVariable String id) {
         customerService.delete(id);
         return id;
    }

    @Operation(summary = "Update a customer by Id", description = "Update a customer by Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping
    public CustomerCreationDTO update(@RequestBody CustomerUpdateDTO customer) {
        return customerService.update(customer);
    }

}
