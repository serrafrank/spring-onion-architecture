package org.pay_my_buddy.user.infrastructure.presenter;

import org.pay_my_buddy.shared.infrastructure.AbstractOutputBoundaryPresenter;
import org.pay_my_buddy.user.domain.UserId;
import org.pay_my_buddy.user.domain.create_user.CreateUserPresenter;
import org.springframework.stereotype.Service;

@Service
public class CreateUserOutputBoundaryPresenter extends AbstractOutputBoundaryPresenter<UserId> implements CreateUserPresenter {
}
