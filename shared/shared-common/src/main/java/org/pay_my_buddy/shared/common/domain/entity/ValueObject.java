package org.pay_my_buddy.shared.common.domain.entity;

import java.io.Serializable;
import java.util.UUID;

public interface ValueObject<T> extends Serializable {

    UUID value();
}
