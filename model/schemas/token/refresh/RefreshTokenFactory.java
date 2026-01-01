package com.authservice.domain.model.schemas.token.refresh;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link RefreshTokenSchema} instances from
 * pre-validated {@link RefreshTokenRequirement} data.
 * <p>
 * Enforces the <strong>Double Dispatch</strong> pattern: the mapping is
 * only accessible if the factory is the authorized consumer.
 * This ensures that only the intended factory can produce RefreshToken schemas.
 * </p>
 *
 * <p>
 * Extracted fields:
 * <ul>
 * <li>tokenId</li>
 * <li>issuedAt</li>
 * <li>expiresAt</li>
 * <li>sessionId</li>
 * </ul>
 * </p>
 *
 * <p>
 * Immutable, thread-safe, and uses a static factory method for controlled
 * instantiation.
 * </p>
 */
public class RefreshTokenFactory implements ISchemaFactory<RefreshTokenSchemaProof, RefreshTokenRequirement> {

    /** Private constructor to enforce controlled instantiation */
    private RefreshTokenFactory() {
    }

    /**
     * Converts a pre-validated {@link RefreshTokenRequirement} into a concrete
     * {@link RefreshTokenSchema}.
     *
     * @param requirement the authorized requirement containing the validated raw
     *                    mapping
     * @return a fully initialized RefreshTokenSchema
     */
    @Override
    public RefreshTokenSchemaProof create(RefreshTokenRequirement requirement) {
        String tokenId = requirement.getMapping(this).getString("tokenId");
        LocalDateTime issuedAt = requirement.getMapping(this).getDateTime("issuedAt");
        LocalDateTime expiresAt = requirement.getMapping(this).getDateTime("expiresAt");
        String sessionId = requirement.getMapping(this).getString("sessionId");

        RefreshTokenSchema schema = new RefreshTokenSchema(tokenId, issuedAt, expiresAt, sessionId);

        return new RefreshTokenSchemaProof(schema);
    }

    /**
     * Static factory method for controlled instantiation.
     *
     * @return a new instance of RefreshTokenFactory
     */
    public static RefreshTokenFactory createFactory() {
        return new RefreshTokenFactory();
    }
}
