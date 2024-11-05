package org.pay_my_buddy.user.command.application;

import org.pay_my_buddy.shared.command.presentation.AbstractOutputBoundaryPresenter;
import org.pay_my_buddy.user.command.domain.create_user.CreateUserPresenter;
import org.pay_my_buddy.user.common.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class CreateUserOutputBoundaryPresenter extends AbstractOutputBoundaryPresenter<UserId> implements CreateUserPresenter {
}
