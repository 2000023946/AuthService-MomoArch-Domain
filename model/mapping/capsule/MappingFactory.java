package com.authservice.domain.model.aggregates.mapping.capsule;

import java.util.Map;

import com.authservice.domain.model.aggregates.mapping.requirements.MappingRequirement;
import com.authservice.domain.model.valueobjects.JsonString;
import com.authservice.domain.ports.IJsonParser;

/**
 * Factory responsible for creating {@link MappingProof} instances from JSON
 * strings.
 * <p>
 * This factory uses a {@link ClaimsMappingRequirement} to obtain the JSON
 * string and parser, ensuring that only authorized and validated data can be
 * converted into a read-only map.
 * </p>
 */
public class MappingFactory {

    /**
     * Private constructor to prevent direct instantiation.
     * <p>
     * Use {@link #createFactory()} to obtain a new instance.
     * </p>
     */
    private MappingFactory() {
    }

    /**
     * Creates a {@link MappingProof} by parsing the JSON string provided in
     * the requirement.
     * <p>
     * The resulting {@link MappingProof} contains a read-only map of the parsed
     * JSON data.
     *
     * @param requirement the requirement containing the JSON string and parser
     * @return a {@link MappingProof} wrapping the parsed JSON map
     * @throws IllegalArgumentException if parsing fails
     */
    public MappingProof create(MappingRequirement requirement) {
        JsonString json = requirement.getJsonString(this);
        IJsonParser parser = requirement.getParser(this);
        Map<String, Object> parsedMap = parser.parse(json);
        MapObject mapObject = new MapObject(parsedMap);
        return MappingProof.create(mapObject);
    }

    /**
     * Factory method to create a new {@link MappingFactory}.
     *
     * @return a new instance of {@link MappingFactory}
     */
    public static MappingFactory createFactory() {
        return new MappingFactory();
    }
}
