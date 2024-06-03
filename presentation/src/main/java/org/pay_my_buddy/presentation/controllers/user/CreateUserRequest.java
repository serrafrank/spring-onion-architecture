package org.pay_my_buddy.presentation.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.pay_my_buddy.presentation.controllers.Request;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class CreateUserRequest implements Request {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
