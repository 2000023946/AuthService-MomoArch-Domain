package com.authservice.domain.model.schemas.session;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.abstractions.AbstractSchemaRequirement;

import java.util.Objects;

/**
 * Encapsulates a requirement for constructing a {@link SessionSchema}.
 * <p>
 * Enforces that a valid {@link IMap} and an authorized {@link SessionFactory}
 * are provided before schema creation.
 * <p>
 * Uses the <strong>Step Builder Pattern</strong> to ensure compile-time
 * safety: developers cannot build a requirement without first providing
 * both the mapping and the factory.
 */
public final class SessionRequirement
        extends AbstractSchemaRequirement<SessionFactory> {

    /**
     * Private constructor used by the builder.
     *
     * @param mapping the raw map containing session data
     * @param target  the authorized factory that can consume this requirement
     */
    private SessionRequirement(IMap mapping, SessionFactory target) {
        super(mapping, target);
    }

    /**
     * Entry point for the step builder.
     *
     * @return the first stage requiring the mapping
     */
    public static MappingStage builder() {
        return new Builder();
    }

    // --- Builder stages ---

    /** Stage for assigning the raw mapping */
    public interface MappingStage {
        /**
         * Assigns the raw session data mapping.
         *
         * @param mapping the {@link IMap} containing session data
         * @return the next stage requiring the factory
         */
        TargetStage withMapping(IMap mapping);
    }

    /** Stage for assigning the authorized factory */
    public interface TargetStage {
        /**
         * Assigns the factory authorized to consume this requirement.
         *
         * @param target the {@link SessionFactory} instance
         * @return the final stage to build the requirement
         */
        FinalStage withTarget(SessionFactory target);
    }

    /** Final stage for building the requirement */
    public interface FinalStage {
        /**
         * Builds a fully initialized {@link SessionRequirement}.
         *
         * @return the constructed requirement
         */
        SessionRequirement build();
    }

    /** Private builder implementing all stages */
    private static class Builder implements MappingStage, TargetStage, FinalStage {
        private IMap mapping;
        private SessionFactory target;

        @Override
        public TargetStage withMapping(IMap mapping) {
            this.mapping = Objects.requireNonNull(mapping, "Mapping cannot be null");
            return this;
        }

        @Override
        public FinalStage withTarget(SessionFactory target) {
            this.target = Objects.requireNonNull(target, "Target factory cannot be null");
            return this;
        }

        @Override
        public SessionRequirement build() {
            return new SessionRequirement(mapping, target);
        }
    }
}
