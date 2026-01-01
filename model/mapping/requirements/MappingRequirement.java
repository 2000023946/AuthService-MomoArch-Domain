package com.authservice.domain.model.aggregates.mapping.requirements;

import com.authservice.domain.model.aggregates.mapping.capsule.MappingFactory;
import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.valueobjects.JsonString;
import com.authservice.domain.ports.IJsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents an immutable, capability-guarded requirement for creating a
 * mapping from a validated token payload.
 * <p>
 * A {@code MappingRequirement} encapsulates the following:
 * <ul>
 * <li>{@link MappingFactory}: The authorized factory that is allowed
 * to use this requirement.</li>
 * <li>{@link JsonString}: A validated token payload acting as a
 * <strong>security proof</strong> that parsing has already occurred.</li>
 * <li>{@link IParsedProof}: An alternative parsed token proof.</li>
 * <li>{@link IJsonParser}: A parser capable of converting the JSON payload
 * into a read-only map for claims mapping.</li>
 * </ul>
 * <p>
 * Access to these internal components is <strong>capability-guarded</strong>.
 * Only the specific {@link MappingFactory} instance declared during
 * construction may retrieve the associated proof, parser, or JSON payload.
 * <p>
 * Instances of this class can only be created via a staged builder, which
 * enforces correct construction order at compile-time and prevents incomplete
 * or unauthorized configurations.
 * <p>
 * This class is immutable and thread-safe.
 *
 * @see MappingFactory
 * @see JsonString
 * @see IParsedProof
 * @see IJsonParser
 */
public final class MappingRequirement {

    /** The authorized factory that can access this requirement. */
    private final MappingFactory target;

    /** The validated token payload proof. */
    private final JsonString jsonString;

    /** The JSON parser used to extract mappings from the payload. */
    private final IJsonParser parser;

    /** The parsed token proof, if provided. */
    private final IParsedProof proof;

    /**
     * Private constructor to enforce staged builder usage.
     *
     * @param target     the authorized factory allowed to create mappings
     * @param jsonString validated token payload proof
     * @param proof      parsed token proof
     * @param parser     JSON parser for mapping extraction
     */
    private MappingRequirement(MappingFactory target, JsonString jsonString, IParsedProof proof, IJsonParser parser) {
        this.target = target;
        this.jsonString = jsonString;
        this.parser = parser;
        this.proof = proof;
    }

    /**
     * Entry point to start constructing a {@link MappingRequirement}.
     * <p>
     * Initiates a staged builder process that enforces the following order:
     * <ol>
     * <li>Specify the target factory</li>
     * <li>Provide the validated token proof (either {@link JsonString} or
     * {@link IParsedProof})</li>
     * <li>Provide the JSON parser</li>
     * <li>Build the immutable {@link MappingRequirement}</li>
     * </ol>
     *
     * @return the first stage of the builder, requiring a target factory
     */
    public static TargetStage builder() {
        return new Builder();
    }

    /**
     * Validates that the requesting factory is authorized to access
     * the internal components of this requirement.
     *
     * @param retriever the factory attempting access
     * @throws IllegalArgumentException if the retriever is not authorized
     */
    private void validateRetriever(MappingFactory retriever) {
        if (retriever != this.target) {
            throw new IllegalArgumentException("Unauthorized retriever!");
        }
    }

    // ---------------------------------------------------------------------
    // ACCESSORS
    // ---------------------------------------------------------------------

    /**
     * Returns the validated token proof.
     * <p>
     * If a {@link JsonString} was provided, it is returned; otherwise,
     * it is retrieved from the {@link IParsedProof}.
     *
     * @param retriever the factory requesting access
     * @return the validated token payload
     * @throws IllegalArgumentException if the retriever is not authorized
     */
    public JsonString getJsonString(MappingFactory retriever) {
        validateRetriever(retriever);
        if (jsonString != null) {
            return jsonString;
        }
        return proof.getJsonString();
    }

    /**
     * Returns the authorized factory associated with this requirement.
     * <p>
     * Only the authorized factory can retrieve this reference.
     *
     * @param retriever the factory requesting access
     * @return the target factory
     * @throws IllegalArgumentException if the retriever is not authorized
     */
    public MappingFactory getTarget(MappingFactory retriever) {
        validateRetriever(retriever);
        return target;
    }

    /**
     * Returns the JSON parser for extracting mappings from the payload.
     * <p>
     * Only the authorized factory can retrieve this parser.
     *
     * @param retriever the factory requesting access
     * @return the JSON parser
     * @throws IllegalArgumentException if the retriever is not authorized
     */
    public IJsonParser getParser(MappingFactory retriever) {
        validateRetriever(retriever);
        return parser;
    }

    // ---------------------------------------------------------------------
    // BUILDER IMPLEMENTATION (INNER TYPE MUST BE LAST)
    // ---------------------------------------------------------------------

    /**
     * Staged builder implementation for {@link MappingRequirement}.
     * <p>
     * This builder enforces a strict construction order by implementing
     * multiple stage interfaces. Each stage represents a required capability
     * that must be supplied before the requirement can be built.
     */
    private static final class Builder
            implements TargetStage, JsonStringStage, JsonParserStage, FinalStage {

        /** Target factory for this requirement. */
        private MappingFactory target;

        /** Stored validated token proof (JsonString). */
        private JsonString jsonString;

        /** Stored JSON parser. */
        private IJsonParser parser;

        /** Stored parsed token proof. */
        private IParsedProof proof;

        /**
         * Declares the target factory for which this requirement is being created.
         *
         * @param target the authorized factory
         * @return next builder stage requiring the token proof
         * @throws NullPointerException if target is null
         */
        @Override
        public JsonStringStage forTarget(@NotNull MappingFactory target) {
            this.target = Objects.requireNonNull(target, "Target factory cannot be null");
            return this;
        }

        /**
         * Supplies the validated token proof as a {@link JsonString}.
         *
         * @param jsonString the validated and trusted token payload
         * @return next builder stage requiring a JSON parser
         * @throws NullPointerException if jsonString is null
         */
        @Override
        public JsonParserStage withJsonString(@NotNull JsonString jsonString) {
            this.jsonString = Objects.requireNonNull(jsonString, "Proof cannot be null");
            return this;
        }

        /**
         * Supplies the validated token proof as an {@link IParsedProof}.
         *
         * @param proof the validated parsed token proof
         * @return next builder stage requiring a JSON parser
         * @throws NullPointerException if proof is null
         */
        @Override
        public JsonParserStage withParsedProof(@NotNull IParsedProof proof) {
            this.proof = Objects.requireNonNull(proof, "Proof cannot be null");
            return this;
        }

        /**
         * Supplies the JSON parser used to extract mappings.
         *
         * @param parser the JSON parser
         * @return final stage of the builder
         * @throws NullPointerException if parser is null
         */
        @Override
        public FinalStage withParser(@NotNull IJsonParser parser) {
            this.parser = Objects.requireNonNull(parser, "Parser cannot be null");
            return this;
        }

        /**
         * Builds the immutable {@link MappingRequirement} instance.
         *
         * @return fully configured {@link MappingRequirement}
         */
        @Override
        public MappingRequirement build() {
            return new MappingRequirement(target, jsonString, proof, parser);
        }
    }
}
