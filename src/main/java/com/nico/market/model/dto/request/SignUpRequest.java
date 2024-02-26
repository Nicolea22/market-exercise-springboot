package com.nico.market.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Size(min=2, max=40)
    @NotBlank
    String firstName;

    @Size(min=2, max=40)
    @NotBlank
    String lastName;

    @NotBlank
    @Email(message = "Email should be valid")
    String email;

    @Size(min=5, max=30)
    @NotBlank
    String password;

}
