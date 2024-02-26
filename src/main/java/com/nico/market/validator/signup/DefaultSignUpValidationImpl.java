package com.nico.market.validator.signup;

import com.nico.market.model.dto.request.SignUpRequest;
import com.nico.market.repository.UserRepository;
import com.nico.market.validator.ValidationResult;
import com.nico.market.validator.ValidationStep;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class DefaultSignUpValidationImpl implements SignUpValidation {

    private final UserRepository userRepository;

    @Override
    public ValidationResult validate(SignUpRequest signUpData) {
        return new SignUpConstraintsValidationStep()
                .linkWith(new EmailDuplicationValidationStep(userRepository))
                .validate(signUpData);
    }


    private static class SignUpConstraintsValidationStep extends ValidationStep<SignUpRequest> {
        public ValidationResult validate(SignUpRequest signUpData) {
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<SignUpRequest>> constraintsViolations = validator.validate(signUpData);

                if (!constraintsViolations.isEmpty()) {
                    return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
                }
            }
            return checkNext(signUpData);
        }
    }

    @AllArgsConstructor
    private static class EmailDuplicationValidationStep extends ValidationStep<SignUpRequest> {

        private final UserRepository userRepository;

        @Override
        public ValidationResult validate(SignUpRequest signUpData) {
            if (userRepository.findByEmail(signUpData.getEmail()).isPresent()) {
                return ValidationResult.invalid(String.format("Email [%s] is already taken", signUpData.getEmail()));
            }
            return checkNext(signUpData);
        }
    }

}
