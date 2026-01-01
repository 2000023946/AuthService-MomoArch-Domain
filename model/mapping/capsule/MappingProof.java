package com.authservice.domain.model.aggregates.mapping.capsule;

import java.util.Map;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMappingProof;

/**
 * Represents a successfully parsed and validated JSON map.
 * <p>
 * This class encapsulates a {@link Map} that has been extracted from any
 * validated JSON string. It acts as proof that the JSON was successfully
 * parsed and is safe for read-only access.
 * </p>
 * <p>
 * The class is immutable and provides controlled access to the underlying
 * map. Construction is package-private to ensure only trusted logic within
 * the package can create instances.
 * </p>
 */
public final class MappingProof implements IMappingProof {

    /** The validated and parsed map. */
    private final MapObject map;

    /**
     * Constructs a new {@link MappingProof} by parsing the given JSON string
     * using the provided parser.
     * <p>
     * This constructor is package-private to ensure that only internal logic
     * can create instances.
     * </p>
     *
     * @param map the map
     */
    private MappingProof(MapObject map) {
        this.map = map;
    }

    /**
     * Returns the read-only map extracted from the JSON string.
     *
     * @return an unmodifiable map containing the parsed JSON data
     */
    public MapObject getMap() {
        return map;
    }

    /**
     * Factory method for creating a new {@link MappingProof}.
     *
     * @param map map
     * @return a new {@link MappingProof} instance
     */
    static MappingProof create(MapObject map) {
        return new MappingProof(map);
    }
}
