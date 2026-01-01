package com.authservice.domain.model.parser.jwtParser.buildStages;

import com.authservice.domain.ports.IJsonParser;

/**
 * Represents a stage in the staged builder for parser requirements
 * where a JSON parser must be supplied.
 * <p>
 * This stage enforces that a valid {@link IJsonParser} is provided
 * before the requirement can be built. It is part of the capability-guarded
 * construction flow for immutable parser requirements, ensuring that
 * all necessary components are available and validated at compile time.
 * </p>
 */
public interface JsonParserStage {

    /**
     * Supplies the JSON parser to be used by the parser requirement.
     * <p>
     * Implementations of this method should store the parser and transition
     * the builder to the final stage where the requirement can be built.
     * </p>
     *
     * @param parser the {@link IJsonParser} responsible for validating and parsing
     *               JSON
     * @return the final stage of the builder
     * @throws NullPointerException if the parser is {@code null}
     */
    FinalStage withJsonParser(IJsonParser parser);
}
