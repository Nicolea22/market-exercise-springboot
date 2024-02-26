package com.nico.market.validator.signin;

import com.nico.market.model.dto.request.SignInRequest;
import com.nico.market.model.entity.User;
import com.nico.market.repository.UserRepository;
import com.nico.market.validator.ValidationResult;
import com.nico.market.validator.ValidationStep;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class DefaultSignInValidationImpl implements SignInValidation {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ValidationResult validate(SignInRequest signInData) {
        return new SignInConstraintsValidationStep()
                .linkWith(new EmailValidationStep(userRepository, passwordEncoder))
                .validate(signInData);
    }

    private static class SignInConstraintsValidationStep extends ValidationStep<SignInRequest> {
        @Override
        public ValidationResult validate(SignInRequest signInData) {
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<SignInRequest>> constraintsViolations = validator.validate(signInData);

                if (!constraintsViolations.isEmpty()) {
                    return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
                }
            }
            return checkNext(signInData);
        }
    }

    @AllArgsConstructor
    private static class EmailValidationStep extends ValidationStep<SignInRequest> {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public ValidationResult validate(SignInRequest signInData) {
            Optional<User> user = userRepository.findByEmail(signInData.getEmail());
            if(user.isEmpty()) {
                return ValidationResult.invalid("The Email does not exists");
            }
            CharSequence charSequence = new StringBuffer(user.get().getPassword());
            if(passwordEncoder.matches(charSequence, user.get().getPassword())) {
                return ValidationResult.invalid("The password does not match");
            }
            return checkNext(signInData);
        }
    }

}
