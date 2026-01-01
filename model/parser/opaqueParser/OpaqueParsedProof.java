package com.authservice.domain.model.parser.opaqueParser;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.valueobjects.JsonString;

/**
 * Represents a proof that an opaque token has been successfully parsed
 * and validated.
 * <p>
 * This class encapsulates a {@link JsonString} that contains the
 * validated JSON payload extracted from the opaque token.
 * <p>
 * Instances of this class are immutable and thread-safe. They serve
 * as a trusted, capability-guarded representation of the parsed token.
 */
public class OpaqueParsedProof implements IParsedProof {

    /** The validated JSON payload extracted from the opaque token. */
    private final JsonString parsedOpaque;

    /**
     * Constructs an {@code OpaqueParsedProof} from a validated JSON payload.
     * <p>
     * This constructor is package-private to restrict creation to the
     * corresponding parser or factory.
     *
     * @param parsedOpaque a {@link JsonString} representing the validated payload
     */
    OpaqueParsedProof(JsonString parsedOpaque) {
        this.parsedOpaque = parsedOpaque;
    }

    /**
     * Returns the validated JSON payload contained in this proof.
     *
     * @return a {@link JsonString} representing the parsed opaque token
     */
    @Override
    public JsonString getJsonString() {
        return parsedOpaque;
    }
}
