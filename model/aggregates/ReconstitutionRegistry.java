package com.authservice.domain.model.aggregates;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;

/**
 * Central registry for Domain Reconstitution handshakes.
 * <p>
 * This registry maintains a mapping between a concrete
 * {@link IDomainReconstitutionRequirement} type and its corresponding
 * {@link IDomainReconstitutionFactory}. It serves as the <strong>resolution
 * vault</strong>
 * for rehydration flows, enabling factories to be discovered strictly by
 * requirement type.
 * </p>
 *
 * <p>
 * The registry supports a <strong>lifecycle lock</strong>. Once locked, no
 * further
 * registrations are permitted. This guarantees that reconstitution wiring
 * is immutable after application bootstrap.
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
 * <li>Decouples reconstitution orchestration from concrete factory wiring</li>
 * <li>Preserves strict Requirement → Factory resolution</li>
 * <li>Supports the Domain Triple-Handshake (Reanimation) pattern</li>
 * </ul>
 */
public final class ReconstitutionRegistry {

    /**
     * Internal storage mapping Requirement types to their corresponding factories.
     */
    private final Map<Class<?>, Object> registry = new ConcurrentHashMap<>();

    /**
     * Indicates whether the registry is locked against further mutation.
     */
    private boolean isLocked = false;

    /**
     * Registers a reconstitution factory for a specific requirement type.
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
    public <F extends IDomainReconstitutionFactory<?, ?>> void register(
            Class<? extends IDomainReconstitutionRequirement<F>> requirementType,
            F factory) {

        if (isLocked) {
            throw new IllegalStateException("Reconstitution Registry is locked.");
        }
        registry.put(requirementType, factory);
    }

    /**
     * Resolves the reconstitution factory associated with the given requirement
     * type.
     * <p>
     * This method performs a type-safe lookup based on the requirement's
     * concrete class. If no factory has been registered for the requirement,
     * resolution fails immediately.
     * </p>
     *
     * @param <F>             the expected factory type
     * @param requirementType the requirement class used to locate the factory
     * @return the registered reconstitution factory
     * @throws RuntimeException if no factory is registered for the requirement type
     */
    @SuppressWarnings("unchecked")
    public <F extends IDomainReconstitutionFactory<?, ?>> F getFactory(
            Class<? extends IDomainReconstitutionRequirement<F>> requirementType) {

        F factory = (F) registry.get(requirementType);
        if (factory == null) {
            throw new RuntimeException(
                    "No reconstitution factory found for: " + requirementType.getSimpleName());
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
