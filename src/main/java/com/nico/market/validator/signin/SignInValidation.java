package com.nico.market.validator.signin;

import com.nico.market.model.dto.request.SignInRequest;
import com.nico.market.validator.ValidationResult;

public interface SignInValidation {
    ValidationResult validate(SignInRequest signInData);
}
