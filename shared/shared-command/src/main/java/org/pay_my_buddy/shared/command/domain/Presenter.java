package org.pay_my_buddy.shared.command.domain;

public interface Presenter<VIEW> {

     void present(VIEW view);

    VIEW view();

}

