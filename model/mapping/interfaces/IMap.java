package com.authservice.domain.model.aggregates.mapping.interfaces;

import java.time.LocalDateTime;

/**
 * Defines a type-safe contract for extracting data from a raw mapping source.
 * <p>
 * This interface serves as an <strong>Anti-Corruption Layer</strong> between
 * raw data (e.g., {@link java.util.Map}, JSON objects, or database rows) and
 * the domain layer. It provides controlled, type-safe access to primitive and
 * complex types while abstracting the underlying storage.
 * </p>
 *
 * <p>
 * Implementations of this interface are responsible for:
 * <ul>
 * <li>Validating that requested keys exist.</li>
 * <li>Ensuring type safety of returned values.</li>
 * <li>Throwing descriptive exceptions when access fails.</li>
 * </ul>
 * </p>
 */
public interface IMap {

    /**
     * Retrieves the value associated with the given key as a {@link String}.
     *
     * @param key the identifier for the data; must not be null
     * @return the string representation of the value
     * @throws RuntimeException if the key is missing or the value cannot be
     *                          converted to a String
     */
    String getString(String key);

    /**
     * Retrieves the value associated with the given key as a primitive {@code int}.
     *
     * @param key the identifier for the data; must not be null
     * @return the integer value
     * @throws RuntimeException if the key is missing or the value is not a number
     */
    int getInt(String key);

    /**
     * Retrieves the value associated with the given key as a primitive
     * {@code long}.
     * <p>
     * Commonly used for timestamps, unique identifiers, or large numeric values.
     * </p>
     *
     * @param key the identifier for the data; must not be null
     * @return the long value
     * @throws RuntimeException if the key is missing or the value is not a number
     */
    long getLong(String key);

    /**
     * Retrieves the value associated with the given key as a primitive
     * {@code boolean}.
     * <p>
     * Accepts {@link Boolean} values or {@link String} values ("true"/"false") that
     * can be parsed to a boolean.
     * </p>
     *
     * @param key the identifier for the data; must not be null
     * @return the boolean value
     * @throws RuntimeException if the key is missing or the value cannot be
     *                          interpreted as a boolean
     */
    boolean getBoolean(String key);

    /**
     * Retrieves the value associated with the given key as a primitive
     * {@code double}.
     * <p>
     * Accepts numeric values that can be safely converted to {@code double}.
     * </p>
     *
     * @param key the identifier for the data; must not be null
     * @return the double value
     * @throws RuntimeException if the key is missing or the value is not numeric
     */
    double getDouble(String key);

    /**
     * Retrieves the value associated with the given key as a {@link LocalDateTime}.
     * <p>
     * Accepts either {@link LocalDateTime} instances directly or {@link String}
     * values
     * that can be parsed using {@link LocalDateTime#parse(CharSequence)}.
     * </p>
     *
     * @param key the identifier for the data; must not be null
     * @return the LocalDateTime instance
     * @throws RuntimeException if the key is missing or the value cannot be parsed
     *                          as a LocalDateTime
     */
    LocalDateTime getDateTime(String key);
}
