package com.authservice.domain.model.parser.jwtParser;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.valueobjects.JsonString;

/**
 * Represents proof that a JWT has been successfully parsed into a valid JSON
 * structure.
 * <p>
 * This class acts as a <strong>trust boundary marker</strong> within the
 * domain:
 * possession of a {@code JwtParsedProof} instance guarantees that the
 * underlying
 * JWT string:
 * <ul>
 * <li>Has been syntactically validated as JSON</li>
 * <li>Has been successfully parsed without errors</li>
 * <li>Is safe for downstream structural or semantic inspection</li>
 * </ul>
 * </p>
 *
 * <p>
 * Instances of this class are intentionally immutable and can only be created
 * by authorized parsing components within the token parsing pipeline.
 * </p>
 */
public class JwtParsedProof implements IParsedProof {

    /** The validated JSON representation of the parsed JWT. */
    private final JsonString parsedJwt;

    /**
     * Constructs a proof object for a successfully parsed JWT.
     * <p>
     * This constructor is package-private to prevent arbitrary creation
     * of proof instances outside the parsing domain.
     * </p>
     *
     * @param parsedJwt the validated JSON representation of the JWT
     */
    JwtParsedProof(JsonString parsedJwt) {
        this.parsedJwt = parsedJwt;
    }

    @Override
    public JsonString getJsonString() {
        return parsedJwt;
    }
}
