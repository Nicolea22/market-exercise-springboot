package com.nico.market.validator.market;

import com.nico.market.Constants;
import com.nico.market.model.dto.*;
import com.nico.market.model.entity.Customer;
import com.nico.market.repository.CustomerRepository;
import com.nico.market.repository.MarketRepository;
import com.nico.market.validator.ValidationResult;
import com.nico.market.validator.ValidationStep;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
@Qualifier("Creation")
public class MarketValidationCreation implements MarketValidation {

    private final MarketRepository marketRepository;
    private final CustomerRepository customerService;

    @Override
    public ValidationResult validate(MarketDTO request) {
        return new MarketConstraintsValidationStep()
                .linkWith(new MarketExistsValidationStep(marketRepository))
                .linkWith(new MarketCountryValidationStep())
                .linkWith(new MarketCustomersListValidationStep(customerService))
                .validate((MarketCreationDTO) request);
    }

    private static class MarketConstraintsValidationStep extends ValidationStep<MarketCreationDTO> {
        @Override
        public ValidationResult validate(MarketCreationDTO market) {
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<MarketCreationDTO>> constraintsViolations = validator.validate(market);
                if (!constraintsViolations.isEmpty()) {
                    return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
                }
            }
            return checkNext(market);
        }
    }

    @AllArgsConstructor
    private static class MarketExistsValidationStep extends ValidationStep<MarketCreationDTO> {

        private final MarketRepository marketRepository;

        public ValidationResult validate(MarketCreationDTO market) {
            if(market.getId() != null) {
                if(marketRepository.findById(market.getId()).isPresent()){
                    return ValidationResult.invalid("Market id already exist");
                }
            }
            return checkNext(market);
        }
    }

    private static class MarketCountryValidationStep extends ValidationStep<MarketCreationDTO> {
        @Override
        public ValidationResult validate(MarketCreationDTO request) {
            String country = request.getCountry().isEmpty() ? "" : request.getCountry().toUpperCase();
            if(!Constants.COUNTRIES.contains(country)) {
                return ValidationResult.invalid("Only Allow ARGENTINA and URUGUAY for the country field");
            }
            return checkNext(request);
        }
    }

    @AllArgsConstructor
    private static class MarketCustomersListValidationStep extends ValidationStep<MarketCreationDTO> {

        private final CustomerRepository customerRepository;

        @Override
        public ValidationResult validate(MarketCreationDTO request) {
            if (request.getCustomers() != null) {
                for (CustomerCreationDTO cust : request.getCustomers()) {
                    Optional<Customer> customer = customerRepository.findById(cust.getId());
                    if (customer.isEmpty()) {
                        return ValidationResult.invalid("Customer is already on the market's list");
                    }
                }
            }
            return checkNext(request);
        }
    }

}
