package org.pay_my_buddy.shared.command.presentation;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.command.domain.Presenter;

@Getter
@Accessors(fluent = true)
public abstract class AbstractOutputBoundaryPresenter<VIEW> implements Presenter<VIEW> {
    private VIEW view;

    public void present(VIEW view) {
        this.view = view;
    }

}
