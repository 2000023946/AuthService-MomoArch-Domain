package com.authservice.domain.model.aggregates.mapping.requirements;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.valueobjects.JsonString;

/**
 * Represents the initial stage in building a {@link ClaimsMappingRequirement}.
 * <p>
 * At this stage, a validated token proof must be provided. This can either be:
 * <ul>
 * <li>A {@link JsonString}, representing a validated and trusted JSON
 * payload</li>
 * <li>An {@link IParsedProof}, representing a pre-parsed and validated token
 * proof</li>
 * </ul>
 * <p>
 * Providing a proof object instead of raw data enforces a trust boundary,
 * preventing unvalidated payloads from entering the claims-mapping workflow.
 * <p>
 * After supplying the proof, the builder advances to the next stage, where a
 * JSON parser must be provided.
 *
 * @see JsonParserStage
 */
public interface JsonStringStage {

    /**
     * Supplies a validated JSON string as the token proof and advances
     * the builder to the JSON parser stage.
     *
     * @param string a validated and trusted JSON string representing the token
     *               payload
     * @return the next builder stage requiring a JSON parser
     */
    JsonParserStage withJsonString(JsonString string);

    /**
     * Supplies a pre-parsed and validated token proof and advances
     * the builder to the JSON parser stage.
     *
     * @param proof a validated parsed token proof
     * @return the next builder stage requiring a JSON parser
     */
    JsonParserStage withParsedProof(IParsedProof proof);
}
