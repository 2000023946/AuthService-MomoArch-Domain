package com.authservice.domain.model.schemas.user;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.abstractions.AbstractSchemaRequirement;

import java.util.Objects;

/**
 * Encapsulates a requirement for creating a {@link UserSchema}.
 * <p>
 * Ensures that a valid {@link IMap} and an authorized {@link UserFactory}
 * are provided before schema creation.
 * <p>
 * Uses the <strong>Step Builder Pattern</strong> to enforce compile-time
 * construction safety: developers cannot build a requirement without first
 * providing both the mapping and the factory.
 */
public final class UserRequirement extends AbstractSchemaRequirement<UserFactory> {

    /**
     * Private constructor used by the staged builder.
     *
     * @param mapping the raw {@link IMap} containing user data
     * @param target  the authorized {@link UserFactory}
     */
    private UserRequirement(IMap mapping, UserFactory target) {
        super(mapping, target);
    }

    /**
     * Entry point for the step builder.
     *
     * @return the initial stage requiring the mapping
     */
    public static MappingStage builder() {
        return new Builder();
    }

    // --- Builder stages ---

    /** Stage requiring assignment of the raw mapping */
    public interface MappingStage {
        /**
         * Assigns the mapping containing user data.
         *
         * @param mapping the {@link IMap} instance
         * @return next stage requiring the factory
         */
        TargetStage withMapping(IMap mapping);
    }

    /** Stage requiring assignment of the authorized factory */
    public interface TargetStage {
        /**
         * Assigns the authorized factory that can consume this requirement.
         *
         * @param target the {@link UserFactory}
         * @return final stage to build the requirement
         */
        FinalStage withTarget(UserFactory target);
    }

    /** Final stage allowing the construction of the requirement */
    public interface FinalStage {
        /**
         * Builds a fully initialized {@link UserRequirement}.
         *
         * @return the constructed requirement
         */
        UserRequirement build();
    }

    /** Private builder implementing all stages */
    private static class Builder implements MappingStage, TargetStage, FinalStage {
        private IMap mapping;
        private UserFactory target;

        @Override
        public TargetStage withMapping(IMap mapping) {
            this.mapping = Objects.requireNonNull(mapping, "Mapping cannot be null");
            return this;
        }

        @Override
        public FinalStage withTarget(UserFactory target) {
            this.target = Objects.requireNonNull(target, "Target factory cannot be null");
            return this;
        }

        @Override
        public UserRequirement build() {
            return new UserRequirement(mapping, target);
        }
    }
}
