package com.authservice.domain.ports;

import java.util.Map;

import com.authservice.domain.model.valueobjects.JsonString;

public interface IJsonParser {
    /**
     * 
     * @param json the json string to parse
     * @return the returned Map
     */
    Map<String, Object> parse(JsonString json);

    /**
     * @param json raw JSON string
     * @return true if valid JSON
     */
    boolean isValidJson(String json);

    /**
     * Serialize a map into a JSON string.
     *
     * @param map key-value pairs
     * @return JSON string
     */
    String toJson(Map<String, ?> map);
}
