package com.authservice.domain.model.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.aggregates.parser.interfaces.IParser;
import com.authservice.domain.model.aggregates.parser.interfaces.IParserFactory;
import com.authservice.domain.model.aggregates.parser.interfaces.IParserRequirement;

import jakarta.validation.constraints.NotNull;

/**
 * A hardened, thread-safe registry that maps {@link IParserRequirement}
 * types to their authorized {@link IParserFactory} instances.
 * <p>
 * This registry serves as a <strong>composition-time security
 * boundary</strong>,
 * ensuring that only explicitly registered parser factories may be used
 * to create parsers for a given requirement type.
 * </p>
 *
 * <p>
 * The registry enforces a <em>lock-on-startup</em> policy. Once locked,
 * no further registrations are permitted and the internal state becomes
 * immutable, preventing runtime mutation, accidental misconfiguration,
 * or dependency injection attacks.
 * </p>
 *
 * <p>
 * Intended usage:
 * <ul>
 * <li>All factories are registered at application startup</li>
 * <li>The registry is locked by the composition root</li>
 * <li>Runtime access is read-only and safe</li>
 * </ul>
 * </p>
 */
final class ParserRegistry {

    /**
     * Internal mapping of requirement types to their authorized factories.
     * This map becomes immutable once the registry is locked.
     */
    private Map<Class<?>, IParserFactory<?, ?>> factories = new HashMap<>();

    /**
     * Indicates whether the registry has been locked.
     */
    private boolean locked = false;

    /**
     * Locks the registry, making it immutable.
     * <p>
     * This method should be invoked exactly once by the composition root
     * after all parser factories have been registered.
     * </p>
     *
     * <p>
     * After locking:
     * <ul>
     * <li>No further registrations are allowed</li>
     * <li>The internal factory map becomes unmodifiable</li>
     * <li>The registry is safe for concurrent read access</li>
     * </ul>
     * </p>
     */
    public void lock() {
        if (locked) {
            return;
        }
        this.locked = true;
        this.factories = Collections.unmodifiableMap(new HashMap<>(this.factories));
    }

    /**
     * Registers an authorized parser factory for a specific requirement type.
     * <p>
     * The recursive generic bounds enforce a <strong>compile-time contract</strong>
     * ensuring that:
     * <ul>
     * <li>The requirement is designed for the given factory</li>
     * <li>The factory can only consume compatible requirements</li>
     * </ul>
     * </p>
     *
     * <p>
     * Registration is only permitted before the registry is locked.
     * </p>
     *
     * @param <P>              the parser type produced by the factory
     * @param requirementClass the concrete class of the requirement
     * @param factory          the authorized factory for the requirement
     *
     * @throws IllegalStateException if the registry has already been locked
     * @throws NullPointerException  if any argument is {@code null}
     */
    public <P extends IParser<? extends IParsedProof>> void register(
            Class<?> requirementClass,
            IParserFactory<P, ?> factory) {

        if (locked) {
            throw new IllegalStateException("Cannot register factories: Registry is locked.");
        }

        Objects.requireNonNull(requirementClass, "Requirement class cannot be null");
        Objects.requireNonNull(factory, "Factory cannot be null");

        factories.put(requirementClass, factory);
    }

    /**
     * Retrieves an authorized parser for the given requirement.
     * <p>
     * This method performs a <strong>double-handshake</strong>:
     * <ol>
     * <li>The registry resolves the factory associated with the requirement
     * type.</li>
     * <li>The factory validates and consumes the requirement to create a
     * parser.</li>
     * </ol>
     * </p>
     *
     * <p>
     * The type parameter {@code R} must satisfy the recursive generic bound
     * defined in {@link IParserRequirement}, ensuring that the requirement
     * is compatible with a factory that produces a parser of type {@code P}.
     * </p>
     *
     * <p>
     * If no factory has been registered for the requirement's concrete class,
     * parser creation is denied.
     * </p>
     *
     * @param <P>         the type of parser produced, must extend {@link IParser}
     *                    {@code P} parsers from {@code R} requirements
     * @param requirement the parsing requirement used to select and configure
     *                    the parser; must not be {@code null}
     * @return a fully configured and authorized parser of type {@code P}
     *
     * @throws NullPointerException     if {@code requirement} is {@code null}
     * @throws IllegalArgumentException if no factory is registered for the
     *                                  requirement's class
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <P extends IParser> P getParser(@NotNull IParserRequirement<?> requirement) {

        Objects.requireNonNull(requirement, "Requirement cannot be null");

        // 1. Resolve the factory from the map
        IParserFactory factory = factories.get(requirement.getClass());

        // 2. Fail-Fast Check (Crucial for a Registry)
        if (factory == null) {
            throw new IllegalArgumentException(
                    "No factory registered for: " + requirement.getClass().getSimpleName());
        }

        return (P) factory.create(requirement);
    }

}
