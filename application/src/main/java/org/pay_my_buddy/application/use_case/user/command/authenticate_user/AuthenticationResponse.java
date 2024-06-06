package org.pay_my_buddy.application.use_case.user.command.authenticate_user;

import lombok.Value;

@Value
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;

    private AuthenticationResponse(String accessToken, String refreshToken) {
        if (accessToken == null) {
            throw new IllegalArgumentException("accessToken is required");
        }
        if (refreshToken == null) {
            throw new IllegalArgumentException("refreshToken is required");
        }
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthenticationResponse of(String accessToken, String refreshToken) {
        return new AuthenticationResponse(accessToken, refreshToken);
    }
}
