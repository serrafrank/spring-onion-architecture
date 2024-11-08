package org.pay_my_buddy.shared;

import java.io.Serializable;
import java.util.UUID;

public interface ValueObject<T> extends Serializable {

    T value();

}
