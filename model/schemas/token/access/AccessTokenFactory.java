package com.authservice.domain.model.schemas.token.access;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link AccessTokenSchema} instances from
 * pre-validated {@link AccessTokenRequirement} data.
 * <p>
 * Implements the <strong>Double Dispatch</strong> pattern: the requirement
 * only releases its mapping if this factory is the authorized consumer.
 * This enforces <strong>Capability-Based Security</strong>, ensuring
 * that only authorized factories can transform raw data into schemas.
 * </p>
 *
 * <p>
 * Construction of the schema involves extracting raw values from the
 * {@link AccessTokenRequirement#getMapping(AccessTokenFactory)} method:
 * <ul>
 * <li>tokenId</li>
 * <li>issuedAt</li>
 * <li>expiresAt</li>
 * <li>userId</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe. The static {@code createFactory()}
 * method enforces controlled instantiation, allowing potential future
 * singleton or logging logic without modifying clients.
 * </p>
 */
public class AccessTokenFactory implements ISchemaFactory<AccessTokenProof, AccessTokenRequirement> {

    /**
     * Private constructor to enforce controlled instantiation via factory method
     */
    private AccessTokenFactory() {
    }

    /**
     * Converts a pre-validated {@link AccessTokenRequirement} into a concrete
     * {@link AccessTokenSchema}.
     *
     * @param requirement the authorized requirement containing the validated raw
     *                    mapping
     * @return a fully initialized AccessTokenSchema
     */
    @Override
    public AccessTokenProof create(AccessTokenRequirement requirement) {
        String tokenId = requirement.getMapping(this).getString("tokenId");
        LocalDateTime issuedAt = requirement.getMapping(this).getDateTime("issuedAt");
        LocalDateTime expiresAt = requirement.getMapping(this).getDateTime("expiresAt");
        String userId = requirement.getMapping(this).getString("userId");

        AccessTokenSchema schema = new AccessTokenSchema(tokenId, issuedAt, expiresAt, userId);

        return new AccessTokenProof(schema);
    }

    /**
     * Static factory method for controlled instantiation.
     *
     * @return a new instance of AccessTokenFactory
     */
    public static AccessTokenFactory createFactory() {
        return new AccessTokenFactory();
    }
}
