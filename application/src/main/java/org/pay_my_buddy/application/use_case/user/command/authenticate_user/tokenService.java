package org.pay_my_buddy.application.use_case.user.command.authenticate_user;

import org.pay_my_buddy.entity.user.User;

public interface tokenService {
    boolean isTokenValid(String token, User user);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
