package com.nico.market.validator.market;

import com.nico.market.model.dto.MarketDTO;
import com.nico.market.validator.ValidationResult;

public interface MarketValidation {
    ValidationResult validate(MarketDTO market);
}
