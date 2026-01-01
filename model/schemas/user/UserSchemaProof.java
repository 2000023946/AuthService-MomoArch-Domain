package com.authservice.domain.model.schemas.user;

import com.authservice.domain.model.schemas.abstractions.AbstractSchemaProof;

/**
 * Represents a validated proof of a {@link UserSchema}.
 * <p>
 * Acts as a "Certificate of Validity" that guarantees all required
 * user fields are present, properly typed, and authorized.
 * <p>
 * Only authorized factories may create a {@link UserProof}, ensuring
 * capability-based security.
 */
public final class UserSchemaProof extends AbstractSchemaProof<UserSchema> {

    /**
     * Constructs a new {@link UserProof}.
     *
     * @param schema the validated {@link UserSchema}
     */
    private UserSchemaProof(UserSchema schema) {
        super(schema);
    }

    /**
     * Factory method to create a {@link UserProof}.
     *
     * @param schema the validated {@link UserSchema}
     * @return a new {@link UserProof} instance
     */
    static UserSchemaProof create(UserSchema schema) {
        return new UserSchemaProof(schema);
    }

}
