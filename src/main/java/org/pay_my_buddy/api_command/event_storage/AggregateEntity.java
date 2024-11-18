package org.pay_my_buddy.api_command.event_storage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.api_command.AggregateRoot;
import org.pay_my_buddy.shared.EntityId;
import org.pay_my_buddy.shared.ObjectConverter;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public final class AggregateEntity<AGGREGATE extends AggregateRoot<ID>, ID extends EntityId> {
	@Id
	private String id;
	private String aggregateType;
	@Lob
	private String aggregateData;
	private int version;

	final static JsonMapper MAPPER = JsonMapper.builder()
			.addModule(new JavaTimeModule())
			.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
			.build();

	public AggregateEntity(AGGREGATE aggregateRoot) {
		this.id = aggregateRoot.id().value();
		this.aggregateType = aggregateRoot.getClass().getName();
		this.aggregateData = ObjectConverter.toJson(aggregateRoot);
		this.version = aggregateRoot.version();
	}

	public ID aggregateId() {
		return aggregate().id();
	}

	public AGGREGATE aggregate() {
		return ObjectConverter.fromJson(aggregateData, aggregateType);
	}




}
