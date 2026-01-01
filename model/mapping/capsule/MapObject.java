package com.authservice.domain.model.aggregates.mapping.capsule;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.exceptions.MissingKeyException;
import com.authservice.domain.exceptions.TypeMismatchException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * A type-safe wrapper around a raw {@link Map} that provides defensive
 * access methods for common types (String, int, long, boolean, double,
 * LocalDateTime).
 * <p>
 * Ensures that required keys exist and that values are of the expected type,
 * throwing descriptive exceptions otherwise.
 * </p>
 * <p>
 * This class is used as the backbone for
 * {@link com.authservice.domain.model.aggregates.schemas.ISchemaRequirement}
 * and Token factories, enforcing the integrity of the raw mapping before schema
 * creation.
 * </p>
 */
public class MapObject implements IMap {

    /**
     * Internal map storing the raw key-value data.
     */
    private final Map<String, Object> internalMap;

    /**
     * Constructs a new {@link MapObject} wrapping the provided {@link Map}.
     *
     * @param internalMap the raw data map, must not be null
     * @throws NullPointerException if {@code internalMap} is null
     */
    MapObject(Map<String, Object> internalMap) {
        this.internalMap = Objects.requireNonNull(internalMap, "Internal map cannot be null");
    }

    // --- Private Helper ---

    /**
     * Retrieves the value for the given key or throws a {@link MissingKeyException}
     * if absent.
     *
     * @param key the key to look up
     * @return the value associated with the key
     * @throws MissingKeyException if the key is not present in the map
     */
    private Object getOrThrow(String key) {
        Object value = internalMap.get(key);
        if (value == null) {
            throw new MissingKeyException("Required key '" + key + "' is missing from the data map.");
        }
        return value;
    }

    // --- Public Type-Safe Accessors ---

    /**
     * Retrieves the value associated with the given key as a {@link String}.
     *
     * @param key the key to look up
     * @return the string representation of the value
     * @throws MissingKeyException if the key is not present
     */
    @Override
    public String getString(String key) {
        return String.valueOf(getOrThrow(key));
    }

    /**
     * Retrieves the value associated with the given key as an {@code int}.
     *
     * @param key the key to look up
     * @return the integer value
     * @throws MissingKeyException   if the key is not present
     * @throws TypeMismatchException if the value is not a number
     */
    @Override
    public int getInt(String key) {
        Object value = getOrThrow(key);
        if (value instanceof Number n) {
            return n.intValue();
        }
        throw new TypeMismatchException(key, "Integer");
    }

    /**
     * Retrieves the value associated with the given key as a {@code long}.
     *
     * @param key the key to look up
     * @return the long value
     * @throws MissingKeyException   if the key is not present
     * @throws TypeMismatchException if the value is not a number
     */
    @Override
    public long getLong(String key) {
        Object value = getOrThrow(key);
        if (value instanceof Number n) {
            return n.longValue();
        }
        throw new TypeMismatchException(key, "Long");
    }

    /**
     * Retrieves the value associated with the given key as a {@code boolean}.
     * <p>
     * Accepts both {@link Boolean} and {@link String} representations
     * ("true"/"false").
     * </p>
     *
     * @param key the key to look up
     * @return the boolean value
     * @throws MissingKeyException   if the key is not present
     * @throws TypeMismatchException if the value cannot be interpreted as a boolean
     */
    @Override
    public boolean getBoolean(String key) {
        Object value = getOrThrow(key);
        if (value instanceof Boolean b) {
            return b;
        }
        if (value instanceof String s) {
            return Boolean.parseBoolean(s);
        }
        throw new TypeMismatchException(key, "Boolean");
    }

    /**
     * Retrieves the value associated with the given key as a {@code double}.
     *
     * @param key the key to look up
     * @return the double value
     * @throws MissingKeyException   if the key is not present
     * @throws TypeMismatchException if the value is not a number
     */
    @Override
    public double getDouble(String key) {
        Object value = getOrThrow(key);
        if (value instanceof Number n) {
            return n.doubleValue();
        }
        throw new TypeMismatchException(key, "Double");
    }

    /**
     * Retrieves the value associated with the given key as a {@link LocalDateTime}.
     * <p>
     * Accepts either a {@link LocalDateTime} instance or a {@link String} parseable
     * via {@link LocalDateTime#parse(CharSequence)}.
     * </p>
     *
     * @param key the key to look up
     * @return the LocalDateTime value
     * @throws MissingKeyException   if the key is not present
     * @throws TypeMismatchException if the value cannot be interpreted as a
     *                               LocalDateTime
     */
    @Override
    public LocalDateTime getDateTime(String key) {
        Object value = getOrThrow(key);
        if (value instanceof LocalDateTime ldt) {
            return ldt;
        }
        if (value instanceof String s) {
            return LocalDateTime.parse(s);
        }
        throw new TypeMismatchException(key, "LocalDateTime");
    }
}
