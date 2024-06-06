package org.pay_my_buddy.presentation.controllers.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.pay_my_buddy.application.use_case.user.command.authenticate_user.AuthenticationResponse;
import org.springframework.http.HttpHeaders;

public record UserTokenResponse(@JsonProperty("access_token") String accessToken,
                                @JsonProperty("refresh_token") String refreshToken) {

    public static UserTokenResponse of(AuthenticationResponse response) {
        return new UserTokenResponse(response.accessToken(), response.refreshToken());
    }

    public HttpHeaders toHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", accessToken);
        headers.add("refresh_token", refreshToken);
        return headers;
    }
}
