package com.authservice.domain.model.schemas;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;
import com.authservice.domain.model.schemas.interfaces.ISchemaProof;
import com.authservice.domain.model.schemas.interfaces.ISchemaRequirement;

/**
 * A hardened, thread-safe registry for {@link ISchemaFactory} instances.
 * <p>
 * This registry acts as a security-critical boundary. It enforces a
 * <em>lock-on-startup</em> policy to ensure that construction logic
 * cannot be mutated after the application has been initialized.
 * </p>
 */
public final class SchemaRegistry {

    /**
     * Internal mapping of schema types to their authorized factories.
     * Replaced by an unmodifiable map upon locking.
     */
    private Map<Class<? extends ISchemaProof>, ISchemaFactory<?, ?>> factories = new HashMap<>();

    /**
     * Indicates whether the registry has been locked.
     */
    private boolean locked = false;

    /**
     * Private constructor to enforce controlled instantiation via
     * {@link #create()}.
     */
    private SchemaRegistry() {
    }

    /**
     * Creates a new instance of the hardened registry.
     * 
     * @return a
     */
    public static SchemaRegistry create() {
        return new SchemaRegistry();
    }

    /**
     * Locks the registry to prevent further registrations.
     * <p>
     * Once locked, the internal state is immutable and safe for concurrent access.
     * </p>
     */
    public void lock() {
        if (locked) {
            return;
        }
        this.locked = true;
        // Defensive copy to an unmodifiable map
        this.factories = Collections.unmodifiableMap(new HashMap<>(this.factories));
    }

    /**
     * Registers an authorized factory for a specific schema type.
     *
     * @param <S>         the schema type
     * @param <R>         the requirement type used by the factory
     * @param <F>         the factory used here
     * @param schemaClass the concrete class of the schema
     * @param factory     the authorized factory
     * @throws IllegalStateException if the registry is locked
     * @throws NullPointerException  if any argument is null
     */
    public <S extends ISchemaProof, F extends ISchemaFactory<S, R>, R extends ISchemaRequirement<F>> void register(
            Class<S> schemaClass,
            ISchemaFactory<S, R> factory) {

        if (locked) {
            throw new IllegalStateException("Cannot register factories: SchemaRegistry is locked.");
        }

        Objects.requireNonNull(schemaClass, "Schema class cannot be null");
        Objects.requireNonNull(factory, "Factory cannot be null");

        factories.put(schemaClass, factory);
    }

    /**
     * Retrieves an authorized factory for the given requirement's target schema.
     * <p>
     * This version resolves the factory based on the concrete requirement provided,
     * maintaining the double-handshake protocol.
     * </p>
     *
     * @param <S>         the schema type
     * @param <F>         the requirement type
     * @param requirement the requirement used to identify the target factory
     * @param <R>         asdf
     * @return the authorized factory for the requirement
     * @throws IllegalArgumentException if no factory is registered for the
     *                                  requirement
     */
    @SuppressWarnings({ "unchecked" })
    public <S extends ISchemaProof, F extends ISchemaFactory<S, R>, R extends ISchemaRequirement<F>> ISchemaFactory<S, R> getFactory(
            R requirement) {

        Objects.requireNonNull(requirement, "Requirement cannot be null");

        // Use the requirement's class to find the factory
        // Or if your requirements carry the Schema class, use
        // requirement.getSchemaClass()
        ISchemaFactory<?, ?> factory = factories.get(requirement.getClass());

        if (factory == null) {
            throw new IllegalArgumentException(
                    "No factory registered for requirement type: " + requirement.getClass().getSimpleName());
        }

        return (ISchemaFactory<S, R>) factory;
    }

}