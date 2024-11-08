package org.pay_my_buddy.api_command.event_storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Value;
import org.pay_my_buddy.api_command.AggregateRoot;
import org.pay_my_buddy.shared.EntityId;

@Table
@Entity
public record AggregateEntity(
		@Id EntityId id,
		int version,
		@Lob AggregateRoot<?, ?> aggregate) {
}
