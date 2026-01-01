package com.authservice.domain.model.schemas.token.mfa;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link MfaCodeSchema} instances from
 * pre-validated {@link MfaCodeRequirement} data.
 * <p>
 * Implements <strong>Double Dispatch</strong>: only the authorized factory
 * can retrieve the mapping from the requirement.
 * Ensures that MFA token creation is strictly controlled.
 * </p>
 *
 * <p>
 * Fields extracted:
 * <ul>
 * <li>tokenId</li>
 * <li>issuedAt</li>
 * <li>expiresAt</li>
 * <li>code</li>
 * <li>userId</li>
 * </ul>
 * </p>
 *
 * <p>
 * Immutable, thread-safe, with controlled instantiation through
 * {@code createFactory()}.
 * </p>
 */
public class MfaCodeSchemaFactory implements ISchemaFactory<MfaCodeSchemaProof, MfaCodeSchemaRequirement> {

    /** */
    private MfaCodeSchemaFactory() {
    }

    @Override
    public MfaCodeSchemaProof create(MfaCodeSchemaRequirement requirement) {
        String tokenId = requirement.getMapping(this).getString("tokenId");
        LocalDateTime issuedAt = requirement.getMapping(this).getDateTime("issuedAt");
        LocalDateTime expiresAt = requirement.getMapping(this).getDateTime("expiresAt");
        int code = requirement.getMapping(this).getInt("code");
        String userId = requirement.getMapping(this).getString("userId");

        MfaCodeSchema schema = new MfaCodeSchema(tokenId, issuedAt, expiresAt, code, userId);

        return new MfaCodeSchemaProof(schema);
    }

    /**
     * Static factory method for controlled instantiation.
     *
     * @return a new instance of MfaCodeFactory
     */
    public static MfaCodeSchemaFactory createFactory() {
        return new MfaCodeSchemaFactory();
    }
}
