package org.pay_my_buddy.user_command.application;

import org.pay_my_buddy.api_command.AbstractOutputPresenter;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.springframework.stereotype.Service;

@Service
public class CreateUserOutputPresenter extends AbstractOutputPresenter<UserId> implements CreateUserPresenter {
}
