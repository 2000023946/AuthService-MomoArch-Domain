package com.authservice.domain.model.aggregates.mapping.interfaces;

/**
 * Represents a read-only, validated mapping extracted from JSON.
 * <p>
 * Implementations of this interface guarantee that the underlying data
 * has been parsed and validated, and provides safe, immutable access.
 */
public interface IMappingProof {

    /**
     * Returns the read-only map extracted from the JSON string.
     *
     * @return an unmodifiable map containing the parsed JSON data
     */
    IMap getMap();
}
