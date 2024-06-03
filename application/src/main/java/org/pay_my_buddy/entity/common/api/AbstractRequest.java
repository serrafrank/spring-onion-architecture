package org.pay_my_buddy.entity.common.api;

import lombok.Value;
import org.pay_my_buddy.entity.GenericId;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.common.tools.TextConverter;


import java.time.Clock;
import java.time.LocalDateTime;

public class AbstractRequest implements Request {

    protected Metadata metadata;

    public AbstractRequest() {
        super();
        this.metadata = RequestMetadata.create(this);
    }

    public AbstractRequest(Request eventObject) {
        super();
        this.metadata = RequestMetadata.copy(eventObject);
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }


    @Value
    private static class RequestMetadata implements Request.Metadata {
        Id requestId;
        LocalDateTime occurredOn;
        String name;

        private RequestMetadata(String name) {
            this.requestId = GenericId.of();
            this.occurredOn = LocalDateTime.now(Clock.systemDefaultZone());
            this.name = name;
        }

        private RequestMetadata(Request request) {
            if (request == null) {
                throw new IllegalArgumentException("Request object cannot be null");
            }

            final Request.Metadata requestMetadata = request.metadata();

            this.requestId = requestMetadata.requestId();
            this.occurredOn = requestMetadata.occurredOn();
            this.name = requestMetadata.name();
        }

        protected static Request.Metadata create(Request request) {
            return new RequestMetadata(request.getClass().getSimpleName());
        }

        protected static Request.Metadata copy(Request request) {
            return new RequestMetadata(request);
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
    }
}
