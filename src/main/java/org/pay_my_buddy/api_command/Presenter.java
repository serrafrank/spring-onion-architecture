package org.pay_my_buddy.api_command;

public interface Presenter<VIEW> {

    void present(VIEW view);

    VIEW view();

}

