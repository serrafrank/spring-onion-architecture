package org.pay_my_buddy.core.framework.domain.value_object;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {

    T value();

}
