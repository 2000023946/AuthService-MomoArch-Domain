package com.authservice.domain.model.schemas.user;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link UserSchemaProof} instances.
 * <p>
 * Acts as the <strong>Information Expert</strong> for user construction:
 * extracts fields from the provided {@link IMap} and ensures type safety.
 * Only authorized factories may access the raw mapping, following
 * capability-based security principles.
 */
public final class UserFactory implements ISchemaFactory<UserSchemaProof, UserRequirement> {

    /** Private constructor to enforce usage of the static factory method */
    private UserFactory() {
    }

    /**
     * Creates a {@link UserSchema} from the given {@link UserRequirement}.
     *
     * @param requirement the requirement containing the raw mapping
     * @return a fully populated and validated {@link UserSchema}
     */
    @Override
    public UserSchemaProof create(UserRequirement requirement) {
        IMap map = requirement.getMapping(this);

        UserSchema userSchema = new UserSchema(
                map.getString("userId"),
                map.getString("email"),
                map.getString("passwordHash"),
                map.getBoolean("verified"),
                map.getInt("failedLoginAttempts"),
                map.getDateTime("createdAt"),
                map.getDateTime("updatedAt"),
                map.getDateTime("lastPasswordResetRequestAt"));
        return UserSchemaProof.create(userSchema);
    }

    /**
     * Static factory method to obtain a {@link UserFactory} instance.
     *
     * @return a new {@link UserFactory} instance
     */
    public static UserFactory createFactory() {
        return new UserFactory();
    }
}
