package org.pay_my_buddy.core.command.application;

public interface Presenter<VIEW> {

    void present(VIEW view);

    VIEW view();

}

