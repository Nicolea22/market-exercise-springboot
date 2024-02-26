package com.nico.market.validator.market;

import com.nico.market.model.dto.CustomerCreationDTO;
import com.nico.market.model.dto.MarketDTO;
import com.nico.market.model.dto.MarketUpdateDTO;
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
@Qualifier("Update")
public class MarketValidationUpdate implements MarketValidation {

    private final MarketRepository marketRepository;
    private final CustomerRepository customerService;

    @Override
    public ValidationResult validate(MarketDTO request) {
        return new MarketConstraintsValidationStep()
                .linkWith(new MarketExistsValidationStep(marketRepository))
                .linkWith(new MarketCustomersListValidationStep(customerService))
                .validate((MarketUpdateDTO) request);
    }

    private static class MarketConstraintsValidationStep extends ValidationStep<MarketUpdateDTO> {
        @Override
        public ValidationResult validate(MarketUpdateDTO market) {
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<MarketUpdateDTO>> constraintsViolations = validator.validate(market);
                if (!constraintsViolations.isEmpty()) {
                    return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
                }
            }
            return checkNext(market);
        }
    }

    @AllArgsConstructor
    private static class MarketExistsValidationStep extends ValidationStep<MarketUpdateDTO> {

        private final MarketRepository marketRepository;

        public ValidationResult validate(MarketUpdateDTO market) {
            if(marketRepository.findById(market.getId()).isEmpty()) {
                System.out.println("Market id does not exist");
                return ValidationResult.invalid("Market id does not exist");
            }
            return checkNext(market);
        }
    }

    @AllArgsConstructor
    private static class MarketCustomersListValidationStep extends ValidationStep<MarketUpdateDTO> {

        private final CustomerRepository customerRepository;

        @Override
        public ValidationResult validate(MarketUpdateDTO request) {
            if (!request.getCustomers().isEmpty()) {
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
