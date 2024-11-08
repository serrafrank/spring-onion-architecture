package org.pay_my_buddy.api_command;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractOutputPresenter<VIEW> implements Presenter<VIEW> {
    private VIEW view;

    public void present(VIEW view) {
        this.view = view;
    }

}
