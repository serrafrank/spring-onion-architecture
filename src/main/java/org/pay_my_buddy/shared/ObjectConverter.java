package org.pay_my_buddy.shared;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectConverter {
    private final static JsonMapper MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .build();

    public static <T> T fromJson(String json, Class<T> toClass) {
        try {
            return MAPPER.readValue(json, toClass);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing json", e);
        }
    }
    public static <T> T fromJson(String json, String toClass) {
        try {
            return MAPPER.readValue(json, (Class<T>) Class.forName(toClass));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing json", e);
        }
    }



    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error serializing object", e);
        }
    }
}
