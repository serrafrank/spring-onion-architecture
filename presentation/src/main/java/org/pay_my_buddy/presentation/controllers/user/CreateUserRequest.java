package org.pay_my_buddy.presentation.controllers.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.pay_my_buddy.presentation.controllers.Request;

@Data
@Builder
@Jacksonized
public class CreateUserRequest implements Request {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
