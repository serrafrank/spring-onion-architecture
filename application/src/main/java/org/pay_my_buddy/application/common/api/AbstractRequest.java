package org.pay_my_buddy.application.common.api;

import lombok.Getter;
import org.pay_my_buddy.application.common.TextConverter;
import org.pay_my_buddy.entity.GenericId;
import org.pay_my_buddy.entity.Id;

import java.time.Clock;
import java.time.LocalDateTime;

public abstract sealed class AbstractRequest implements Request permits AbstractCommand, AbstractQuery, AbstractEvent {

    protected Metadata metadata;
    private final String requestName = this.getClass().getSimpleName();

    protected AbstractRequest() {
        this.metadata = new RequestMetadata(requestName);
    }

    protected AbstractRequest(Metadata metadata) {
        this.metadata = new RequestMetadata(metadata);
    }

    protected AbstractRequest(Request trigger) {
        this.metadata = new RequestMetadata(requestName, trigger);
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }


    @Getter
    private static class RequestMetadata implements Request.Metadata {
        private final Id requestId;
        private final LocalDateTime occurredOn;
        private final String name;
        private final Request trigger;

        protected RequestMetadata(String name) {
            this(GenericId.of(), LocalDateTime.now(Clock.systemDefaultZone()), name, null);
        }

        protected RequestMetadata(String name, Request request) {
            this(GenericId.of(), LocalDateTime.now(Clock.systemDefaultZone()), name, request);
        }

        protected RequestMetadata(Metadata metadata) {
            this(metadata.requestId(), metadata.occurredOn(), metadata.name(), metadata.trigger());
        }

        private RequestMetadata(Id requestId, LocalDateTime occurredOn, String name, Request trigger) {
            this.requestId = requestId;
            this.occurredOn = occurredOn;
            this.name = name;
            this.trigger = trigger;
        }

        @Override
        public Id requestId() {
            return requestId;
        }

        @Override
        public LocalDateTime occurredOn() {
            return occurredOn;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String type() {
            return TextConverter.toScreamingSnakeCase(name());
        }

        @Override
        public Request trigger() {
            return null;
        }
    }
}
