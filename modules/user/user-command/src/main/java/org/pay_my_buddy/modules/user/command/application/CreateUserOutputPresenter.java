package org.pay_my_buddy.modules.user.command.application;

import org.pay_my_buddy.core.command.presentation.AbstractOutputPresenter;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.springframework.stereotype.Service;

@Service
public class CreateUserOutputPresenter extends AbstractOutputPresenter<UserId> implements CreateUserPresenter {
}
