package org.pay_my_buddy.core.command.presentation;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.core.command.application.Presenter;

@Getter
@Accessors(fluent = true)
public abstract class AbstractOutputPresenter<VIEW> implements Presenter<VIEW> {
    private VIEW view;

    public void present(VIEW view) {
        this.view = view;
    }

}
