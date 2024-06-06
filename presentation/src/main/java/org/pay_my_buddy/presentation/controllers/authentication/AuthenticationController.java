package org.pay_my_buddy.presentation.controllers.authentication;


import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.api.ApiProvider;
import org.pay_my_buddy.application.use_case.user.command.authenticate_user.AuthenticateUserCommand;
import org.pay_my_buddy.application.use_case.user.command.authenticate_user.AuthenticationResponse;
import org.pay_my_buddy.entity.user.Email;
import org.pay_my_buddy.entity.user.RawPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final ApiProvider apiProvider;


    @PostMapping
    public ResponseEntity<UserTokenResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        final AuthenticateUserCommand command = AuthenticateUserCommand.of(Email.of(request.email()), RawPassword.of(request.password()));
        final AuthenticationResponse response = apiProvider.execute(command);
        final UserTokenResponse userTokenResponse = UserTokenResponse.of(response);
        return ResponseEntity.ok(userTokenResponse);
    }


}