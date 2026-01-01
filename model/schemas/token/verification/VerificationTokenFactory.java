package com.authservice.domain.model.schemas.token.verification;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link VerificationTokenSchema} instances
 * from pre-validated {@link VerificationTokenRequirement} data.
 * <p>
 * Double Dispatch ensures that the requirement only exposes its mapping
 * to this authorized factory. This prevents accidental misuse in other
 * token contexts (like Password Reset).
 * </p>
 */
public class VerificationTokenFactory
        implements ISchemaFactory<VerificationTokenSchemaProof, VerificationTokenRequirement> {

    /**
     * 
     */
    private VerificationTokenFactory() {
    }

    @Override
    public VerificationTokenSchemaProof create(VerificationTokenRequirement requirement) {
        VerificationTokenSchema schema = new VerificationTokenSchema(
                requirement.getMapping(this).getString("tokenId"),
                requirement.getMapping(this).getDateTime("issuedAt"),
                requirement.getMapping(this).getDateTime("expiresAt"),
                requirement.getMapping(this).getString("userId"));

        return new VerificationTokenSchemaProof(schema);
    }

    /**
     * Static factory method for controlled instantiation.
     *
     * @return a new instance of VerificationTokenFactory
     */
    public static VerificationTokenFactory createFactory() {
        return new VerificationTokenFactory();
    }
}
