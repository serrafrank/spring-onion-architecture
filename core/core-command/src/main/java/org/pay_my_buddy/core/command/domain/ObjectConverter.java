package org.pay_my_buddy.core.command.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;

public class ObjectConverter {

    private final static JsonMapper MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .build();

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw BusinessException.wrap(new IllegalArgumentException("Error deserializing json", e));
        }
    }

    public static <T> T fromJson(String json, String type) {
        try {
            Class<T> clazz = (Class<T>) Class.forName(type);
            return fromJson(json, clazz);
        } catch (Exception e) {
            throw BusinessException.wrap(new IllegalArgumentException("Error serializing object", e));
        }
    }

    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw BusinessException.wrap(new IllegalArgumentException("Error serializing object", e));
        }
    }
}
