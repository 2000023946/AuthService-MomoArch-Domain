package com.authservice.domain.model.schemas.token.passwordReset;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenProof;

/**
 * A sealed proof for a validated Password Reset Token schema.
 * <p>
 * Guarantees that:
 * <ul>
 * <li>The {@link PasswordResetTokenSchema#getUserId()} is present and
 * valid</li>
 * <li>The token ID, issuedAt, and expiresAt fields are valid</li>
 * </ul>
 * </p>
 *
 * <p>
 * Immutable and final, this proof ensures the Password Reset Token is
 * safe for aggregate reconstitution, and cannot be confused with
 * Verification Tokens.
 * </p>
 */
public final class PasswordResetSchemaProof extends AbstractTokenProof<PasswordResetTokenSchema> {

    /**
     * Constructs a proof from a validated {@link PasswordResetTokenSchema}.
     *
     * @param schema the validated Password Reset Token schema
     */
    PasswordResetSchemaProof(PasswordResetTokenSchema schema) {
        super(schema);
    }
}
