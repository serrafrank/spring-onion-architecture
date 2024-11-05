package org.pay_my_buddy.shared.domain.api;

public interface Presenter<VIEW> {

     void present(VIEW view);

    VIEW view();

}

