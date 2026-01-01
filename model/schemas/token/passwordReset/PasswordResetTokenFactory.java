package com.authservice.domain.model.schemas.token.passwordReset;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link PasswordResetTokenSchema} instances
 * from pre-validated {@link PasswordResetTokenRequirement} data.
 * <p>
 * Uses Double Dispatch to ensure that only the authorized factory can access
 * the requirement mapping. This guarantees semantic separation from
 * Verification Tokens.
 * </p>
 */
public class PasswordResetTokenFactory
        implements ISchemaFactory<PasswordResetSchemaProof, PasswordResetTokenRequirement> {

    /**
     * 
     */
    private PasswordResetTokenFactory() {
    }

    @Override
    public PasswordResetSchemaProof create(PasswordResetTokenRequirement requirement) {
        PasswordResetTokenSchema schema = new PasswordResetTokenSchema(
                requirement.getMapping(this).getString("tokenId"),
                requirement.getMapping(this).getDateTime("issuedAt"),
                requirement.getMapping(this).getDateTime("expiresAt"),
                requirement.getMapping(this).getString("userId"));

        return new PasswordResetSchemaProof(schema);
    }

    /**
     * Static factory method for controlled instantiation.
     *
     * @return a new instance of PasswordResetTokenFactory
     */
    public static PasswordResetTokenFactory createFactory() {
        return new PasswordResetTokenFactory();
    }
}
