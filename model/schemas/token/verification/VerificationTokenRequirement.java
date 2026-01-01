package com.authservice.domain.model.schemas.token.verification;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenRequirement;

import java.util.Objects;

/**
 * Requirement wrapper for creating {@link VerificationTokenSchema} instances.
 * <p>
 * Holds raw mapping data and enforces that only the authorized
 * {@link VerificationTokenFactory} can consume it.
 * </p>
 * <p>
 * Implements a <strong>Step Builder</strong> pattern to enforce compile-time
 * validation of mandatory fields: mapping ({@link IMap}) and target factory.
 * </p>
 */
public final class VerificationTokenRequirement extends AbstractTokenRequirement<VerificationTokenFactory> {

    /**
     * Private constructor. Use the {@link #builder()} to create an instance.
     *
     * @param mapping the raw {@link IMap} to be validated and converted
     * @param target  the only {@link VerificationTokenFactory} authorized to
     *                consume this requirement
     */
    private VerificationTokenRequirement(IMap mapping, VerificationTokenFactory target) {
        super(mapping, target);
    }

    /**
     * Starts the Step Builder for creating a {@link VerificationTokenRequirement}.
     *
     * @return the first stage of the builder where mapping must be provided
     */
    public static MappingStage builder() {
        return new Builder();
    }

    // --- Step Builder Interfaces ---

    /**
     * Builder stage to provide the mapping for the requirement.
     */
    public interface MappingStage {

        /**
         * Sets the mapping to be used for this requirement.
         *
         * @param mapping the raw {@link IMap}, must not be null
         * @return the next stage ({@link TargetStage}) for providing the target factory
         */
        TargetStage withMapping(IMap mapping);
    }

    /**
     * Builder stage to provide the target factory authorized to consume this
     * requirement.
     */
    public interface TargetStage {

        /**
         * Sets the authorized factory for this requirement.
         *
         * @param target the {@link VerificationTokenFactory}, must not be null
         * @return the final stage ({@link FinalStage}) for building the requirement
         */
        FinalStage withTarget(VerificationTokenFactory target);
    }

    /**
     * Final stage of the builder for constructing the requirement instance.
     */
    public interface FinalStage {

        /**
         * Builds the {@link VerificationTokenRequirement} instance with the provided
         * mapping and factory.
         *
         * @return the constructed {@link VerificationTokenRequirement}
         */
        VerificationTokenRequirement build();
    }

    // --- Builder Implementation ---

    /**
     * Implementation of the Step Builder interfaces for constructing
     * {@link VerificationTokenRequirement}.
     */
    private static class Builder implements MappingStage, TargetStage, FinalStage {

        /**
         * The raw mapping data for the requirement.
         */
        private IMap mapping;

        /**
         * The authorized factory instance for this requirement.
         */
        private VerificationTokenFactory target;

        /**
         * Sets the raw mapping for the requirement.
         *
         * @param mapping the raw {@link IMap}, must not be null
         * @return the next stage ({@link TargetStage}) for providing the factory
         */
        @Override
        public TargetStage withMapping(IMap mapping) {
            this.mapping = Objects.requireNonNull(mapping, "Mapping cannot be null");
            return this;
        }

        /**
         * Sets the authorized factory for this requirement.
         *
         * @param target the {@link VerificationTokenFactory}, must not be null
         * @return the final stage ({@link FinalStage}) to build the requirement
         */
        @Override
        public FinalStage withTarget(VerificationTokenFactory target) {
            this.target = Objects.requireNonNull(target, "Target factory cannot be null");
            return this;
        }

        /**
         * Builds the {@link VerificationTokenRequirement} instance with the previously
         * set mapping and target.
         *
         * @return the fully constructed {@link VerificationTokenRequirement}
         */
        @Override
        public VerificationTokenRequirement build() {
            return new VerificationTokenRequirement(mapping, target);
        }
    }
}
