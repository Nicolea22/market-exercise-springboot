package com.nico.market.validator.signup;

import com.nico.market.model.dto.request.SignUpRequest;
import com.nico.market.validator.ValidationResult;

public interface SignUpValidation {
    ValidationResult validate(SignUpRequest signUpData);
}
