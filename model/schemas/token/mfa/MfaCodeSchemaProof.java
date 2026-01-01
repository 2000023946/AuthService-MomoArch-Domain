package com.authservice.domain.model.schemas.token.mfa;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenProof;

/**
 * A sealed proof for a validated MFA Code schema.
 * <p>
 * Guarantees that:
 * <ul>
 * <li>The {@link MfaCodeSchema#getCode()} is present and within expected
 * numeric range</li>
 * <li>The {@link MfaCodeSchema#getUserId()} is present and valid</li>
 * <li>The token ID, issuedAt, and expiresAt fields are valid</li>
 * </ul>
 * </p>
 *
 * <p>
 * Immutable and final, this proof ensures safety when reconstituting
 * MFA token aggregates.
 * </p>
 */
public final class MfaCodeSchemaProof extends AbstractTokenProof<MfaCodeSchema> {

    /**
     * Constructs a proof from a validated {@link MfaCodeSchema}.
     *
     * @param schema the validated MFA Code schema
     */
    MfaCodeSchemaProof(MfaCodeSchema schema) {
        super(schema);
    }
}
