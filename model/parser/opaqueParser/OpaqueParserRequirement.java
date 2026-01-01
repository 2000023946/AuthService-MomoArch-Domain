package com.authservice.domain.model.parser.opaqueParser;

import com.authservice.domain.model.aggregates.parser.abstractions.AbstractParserRequirement;
import com.authservice.domain.model.aggregates.parser.opaqueParser.buildStages.FinalStage;
import com.authservice.domain.model.aggregates.parser.opaqueParser.buildStages.JsonParserStage;
import com.authservice.domain.model.aggregates.parser.opaqueParser.buildStages.RepositoryStage;
import com.authservice.domain.model.aggregates.parser.opaqueParser.buildStages.StringToParseStage;
import com.authservice.domain.model.aggregates.parser.opaqueParser.buildStages.TargetStage;
import com.authservice.domain.ports.IJsonParser;
import com.authservice.domain.ports.ITokenRepository;
import java.util.Objects;

/**
 * Represents an immutable, capability-guarded requirement for parsing
 * an opaque token.
 * <p>
 * An {@code OpaqueParserRequirement} encapsulates:
 * <ul>
 * <li>The raw opaque token string</li>
 * <li>An authorized {@link OpaqueParserFactory} that can consume it</li>
 * <li>A {@link ITokenRepository} used to retrieve the token's JSON payload</li>
 * <li>A {@link IJsonParser} used to validate the JSON payload</li>
 * </ul>
 * </p>
 *
 * <p>
 * Access to the underlying input, repository, and parser is capability-guarded.
 * Only the {@link OpaqueParserFactory} declared during construction may
 * retrieve these components.
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 */
public class OpaqueParserRequirement extends AbstractParserRequirement<OpaqueParserFactory> {

    private final ITokenRepository repository;
    private final IJsonParser jsonParser;

    /**
     * Private constructor to enforce creation through the staged builder.
     * <p>
     * Initializes an {@link OpaqueParserRequirement} with all required components:
     * the opaque token string, the authorized parser factory, the token repository,
     * and the JSON parser. Access to the repository and parser is
     * capability-guarded
     * and can only be retrieved by the authorized {@link OpaqueParserFactory}.
     * </p>
     *
     * @param stringToParse the raw, untrusted opaque token string
     * @param target        the authorized {@link OpaqueParserFactory} that can
     *                      consume this requirement
     * @param repository    the {@link ITokenRepository} used to retrieve the token
     *                      payload
     * @param jsonParser    the {@link IJsonParser} used to validate the retrieved
     *                      JSON payload
     */
    private OpaqueParserRequirement(String stringToParse,
            OpaqueParserFactory target,
            ITokenRepository repository,
            IJsonParser jsonParser) {
        super(stringToParse, target);
        this.repository = repository;
        this.jsonParser = jsonParser;
    }

    /**
     * Retrieves the repository for this requirement.
     *
     * @param receiver the factory requesting the repository
     * @return the {@link ITokenRepository} instance
     * @throws IllegalArgumentException if the receiver is not authorized
     */
    ITokenRepository getRepository(OpaqueParserFactory receiver) {
        validateReceiver(receiver);
        return repository;
    }

    /**
     * Retrieves the JSON parser for this requirement.
     *
     * @param receiver the factory requesting the parser
     * @return the {@link IJsonParser} instance
     * @throws IllegalArgumentException if the receiver is not authorized
     */
    IJsonParser getJsonParser(OpaqueParserFactory receiver) {
        validateReceiver(receiver);
        return jsonParser;
    }

    /**
     * Entry point for building an {@link OpaqueParserRequirement} using
     * a staged builder.
     *
     * @return the first stage of the builder
     */
    public static TargetStage builder() {
        return new Builder();
    }

    /**
     * Staged builder implementation for {@link OpaqueParserRequirement}.
     */
    private static class Builder implements
            TargetStage, StringToParseStage, RepositoryStage, JsonParserStage, FinalStage {

        private String s;
        private OpaqueParserFactory t;
        private ITokenRepository r;
        private IJsonParser j;

        @Override
        public StringToParseStage withTarget(OpaqueParserFactory t) {
            this.t = Objects.requireNonNull(t, "Target cannot be null");
            return this;
        }

        @Override
        public RepositoryStage withStringToParse(String s) {
            this.s = Objects.requireNonNull(s, "Opaque token string cannot be null");
            return this;
        }

        @Override
        public JsonParserStage withRepository(ITokenRepository r) {
            this.r = Objects.requireNonNull(r, "Repository cannot be null");
            return this;
        }

        @Override
        public FinalStage withJsonParser(IJsonParser j) {
            this.j = Objects.requireNonNull(j, "JSON parser cannot be null");
            return this;
        }

        @Override
        public OpaqueParserRequirement build() {
            return new OpaqueParserRequirement(s, t, r, j);
        }
    }
}
