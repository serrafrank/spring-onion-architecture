package org.pay_my_buddy.bootloader.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ResultActionsHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


    /**
     * The toJson function takes an object and returns a JSON string representation of that object.
     *
     * @param object Convert the object to a json string
     * @return A string representation of the object passed to it
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writer().writeValueAsString(object);
    }


    /**
     * The toJson function is a helper function that converts the result of a ResultActions object to JSON.
     *
     * @param resultActions Get the response from the controller
     * @return The result of the request in json format
     */
    public static String toJson(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }


    /**
     * The toObject function converts a JSON string into an object of the specified class.
     *
     * @param resultActions Get the response from the request
     * @param toClass       Specify the type of object that is returned
     * @return The object of the class type passed in
     */
    public static <T> T toObject(ResultActions resultActions, Class<T> toClass)
            throws JsonProcessingException, UnsupportedEncodingException {
        final String rawResponse = toJson(resultActions);
        return toObject(rawResponse, toClass);
    }


    /**
     * The toObject function takes a JSON string and converts it to an object of the specified class.
     *
     * @param json    Convert the json string into a java object
     * @param toClass Specify the type of object that will be returned
     * @return An object of type t
     */
    public static <T> T toObject(String json, Class<T> toClass) throws JsonProcessingException {
        return objectMapper.readValue(json, toClass);
    }


    /**
     * The toList function takes a raw JSON response and converts it to a list of objects.
     *
     * @param resultActions Get the raw response from the server
     * @param toClass       Tell the method what type of object we want to convert the json into
     * @return A list of objects of type t
     */
    public static <T> List<T> toList(ResultActions resultActions, Class<T> toClass)
            throws JsonProcessingException, UnsupportedEncodingException {
        final String rawResponse = toJson(resultActions);
        return toList(rawResponse, toClass);
    }


    /**
     * The toList function takes a JSON string and converts it to a list of objects of the specified class.
     *
     * @param json    Pass in the json string to be converted into a list of objects
     * @param toClass Tell the compiler what type of object to expect in the list
     * @return A list of objects of type T
     */
    public static <T> List<T> toList(String json, Class<T> toClass)
            throws JsonProcessingException {
        return objectMapper.readerForListOf(toClass).readValue(json);
    }
}
