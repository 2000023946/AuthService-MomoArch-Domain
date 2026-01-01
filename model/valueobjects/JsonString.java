package com.authservice.domain.model.valueobjects;

import java.util.Map;
import java.util.Objects;

import com.authservice.domain.ports.IJsonParser;

/**
 * Value Object representing a validated JSON string.
 */
public final class JsonString extends ValueObject<String> {

    private final String value;

    /**
     * 
     * @param value the json str value
     */
    private JsonString(String value) {
        this.value = value;
    }

    /**
     * Create a JsonString from a raw JSON string.
     *
     * @param json       raw JSON string
     * @param jsonParser JSON validator
     * @return JsonString
     */
    public static JsonString fromString(String json, IJsonParser jsonParser) {
        Objects.requireNonNull(json, "json cannot be null");
        Objects.requireNonNull(jsonParser, "jsonParser cannot be null");

        if (!jsonParser.isValidJson(json)) {
            throw new IllegalArgumentException("Invalid JSON string");
        }

        return new JsonString(json);
    }

    /**
     * Create a JsonString from a Map using the parser.
     *
     * @param map        key-value pairs
     * @param jsonParser JSON serializer
     * @return JsonString
     */
    public static JsonString fromMap(Map<String, ?> map, IJsonParser jsonParser) {
        Objects.requireNonNull(map, "map cannot be null");
        Objects.requireNonNull(jsonParser, "jsonParser cannot be null");

        String json = jsonParser.toJson(map);
        return new JsonString(json);
    }

    @Override
    public String getValue() {
        return value;
    }
}
