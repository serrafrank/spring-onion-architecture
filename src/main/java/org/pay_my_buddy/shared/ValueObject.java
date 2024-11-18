package org.pay_my_buddy.shared;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {

	T value();

}
