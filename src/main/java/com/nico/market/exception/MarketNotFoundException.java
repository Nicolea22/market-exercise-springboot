package com.nico.market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Market not found")

public class MarketNotFoundException extends RuntimeException{
    public MarketNotFoundException(String message) {
        super(message);
    }
}
