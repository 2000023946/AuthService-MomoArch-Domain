package com.authservice.domain.model.aggregates;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;
import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;

/**
 * Central registry for Domain Creation handshakes.
 * <p>
 * This registry maintains a mapping between a concrete
 * {@link IDomainCreationRequirement} type and its corresponding
 * {@link IDomainCreationFactory}. It acts as the <strong>resolution
 * vault</strong>
 * for creation flows, enabling factories to be discovered strictly by
 * requirement type.
 * </p>
 *
 * <p>
 * The registry supports a <strong>lifecycle lock</strong>. Once locked, no
 * further
 * registrations are permitted. This guarantees that creation wiring is
 * immutable
 * after application bootstrap.
 * </p>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * Internally backed by a {@link ConcurrentHashMap}. Read operations are
 * thread-safe.
 * The lock mechanism is intentionally simple and assumes registry configuration
 * occurs during single-threaded startup.
 * </p>
 *
 * <h2>Architectural Role</h2>
 * <ul>
 * <li>Decouples creation orchestration from concrete factory wiring</li>
 * <li>Preserves strict Requirement → Factory resolution</li>
 * <li>Supports the Domain Triple-Handshake pattern</li>
 * </ul>
 */
public final class CreationRegistry {

    /**
     * Internal storage mapping Requirement types to their corresponding factories.
     */
    private final Map<Class<?>, Object> registry = new ConcurrentHashMap<>();

    /**
     * Indicates whether the registry is locked against further mutation.
     */
    private boolean isLocked = false;

    /**
     * Registers a creation factory for a specific requirement type.
     * <p>
     * This method must be invoked during application initialization
     * <em>before</em> the registry is locked.
     * </p>
     *
     * @param <F>             the concrete factory type
     * @param requirementType the concrete requirement class used as the lookup key
     * @param factory         the factory responsible for handling the requirement
     * @throws IllegalStateException if the registry has already been locked
     */
    public <F extends IDomainCreationFactory<?, ?>> void register(
            Class<? extends IDomainCreationRequirement<F>> requirementType,
            F factory) {

        if (isLocked) {
            throw new IllegalStateException("Creation Registry is locked.");
        }
        registry.put(requirementType, factory);
    }

    /**
     * Resolves the creation factory associated with the given requirement type.
     * <p>
     * This method performs a type-safe lookup based on the requirement's
     * concrete class. If no factory has been registered for the requirement,
     * resolution fails immediately.
     * </p>
     *
     * @param <F>             the expected factory type
     * @param requirementType the requirement class used to locate the factory
     * @return the registered creation factory
     * @throws RuntimeException if no factory is registered for the requirement type
     */
    @SuppressWarnings("unchecked")
    public <F extends IDomainCreationFactory<?, ?>> F getFactory(
            Class<? extends IDomainCreationRequirement<F>> requirementType) {

        F factory = (F) registry.get(requirementType);
        if (factory == null) {
            throw new RuntimeException(
                    "No creation factory found for: " + requirementType.getSimpleName());
        }
        return factory;
    }

    /**
     * Locks the registry, preventing any further factory registrations.
     * <p>
     * Once locked, the registry becomes immutable for the remainder
     * of the application lifecycle.
     * </p>
     */
    public void lock() {
        this.isLocked = true;
    }
}
