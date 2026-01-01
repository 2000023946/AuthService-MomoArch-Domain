package com.authservice.domain.model.schemas.token.verification;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenProof;

/**
 * A sealed proof for a validated Verification Token schema.
 * <p>
 * Guarantees that:
 * <ul>
 * <li>The {@link VerificationTokenSchema#getUserId()} is present and valid</li>
 * <li>The token ID, issuedAt, and expiresAt fields are valid</li>
 * </ul>
 * </p>
 *
 * <p>
 * Immutable and final, this proof ensures the Verification Token is
 * safe for aggregate reconstitution, preventing accidental use in
 * other contexts (like Password Reset).
 * </p>
 */
public final class VerificationTokenSchemaProof extends AbstractTokenProof<VerificationTokenSchema> {

    /**
     * Constructs a proof from a validated {@link VerificationTokenSchema}.
     *
     * @param schema the validated Verification Token schema
     */
    VerificationTokenSchemaProof(VerificationTokenSchema schema) {
        super(schema);
    }
}
