package org.pay_my_buddy.presentation.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest  {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
