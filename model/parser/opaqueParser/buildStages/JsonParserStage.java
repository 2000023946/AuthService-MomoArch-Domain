package com.authservice.domain.model.parser.opaqueParser.buildStages;

import com.authservice.domain.ports.IJsonParser;

/**
 * Fourth stage of the {@link OpaqueParserRequirement} builder.
 * <p>
 * Supplies the JSON parser used to validate the token payload.
 */
public interface JsonParserStage {

    /**
     * Specifies the JSON parser.
     *
     * @param parser the {@link IJsonParser} instance
     * @return the final stage of the builder
     */
    FinalStage withJsonParser(IJsonParser parser);
}
