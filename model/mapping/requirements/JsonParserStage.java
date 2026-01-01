package com.authservice.domain.model.aggregates.mapping.requirements;

import com.authservice.domain.ports.IJsonParser;

/**
 * Second stage in the construction of a {@link ClaimsMappingRequirement}.
 * <p>
 * This stage requires a JSON parser capable of extracting structured
 * claim data from a validated token payload.
 *
 * <p>
 * The parser provided here is assumed to operate only on trusted input,
 * as the preceding stage guarantees the presence of a validated
 * {@link com.authservice.domain.model.aggregates.parser.tokenParser.IParsedTokenProof}.
 *
 * <p>
 * Supplying the parser advances the builder to its final stage.
 *
 * @see FinalStage
 */
public interface JsonParserStage {

    /**
     * Supplies the JSON parser used for claim extraction and advances
     * the builder to the final stage.
     *
     * @param parser a JSON parser for extracting claims
     * @return the final stage of the builder
     */
    FinalStage withParser(IJsonParser parser);
}
