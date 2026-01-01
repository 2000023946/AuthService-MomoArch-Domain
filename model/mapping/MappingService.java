package com.authservice.domain.model.aggregates.mapping;

import com.authservice.domain.model.aggregates.mapping.capsule.MappingFactory;
import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.aggregates.mapping.interfaces.IMappingProof;
import com.authservice.domain.model.aggregates.mapping.requirements.MappingRequirement;
import com.authservice.domain.model.aggregates.mapping.interfaces.IMappingService;

/**
 * Default implementation of {@link IMappingService}.
 * <p>
 * This service uses a {@link MappingFactory} to transform a
 * {@link MappingRequirement} into a domain-ready {@link IMap}.
 * The factory enforces all capability-guarded logic, ensuring
 * that only authorized and validated requirements are executed.
 */
public class MappingService implements IMappingService {

    /** Factory used to create {@link IMap} objects from requirements. */
    private final MappingFactory factory;

    /**
     * Constructs a {@link MappingService} with the specified factory.
     *
     * @param factory the {@link MappingFactory} responsible for producing map
     *                objects
     */
    public MappingService(MappingFactory factory) {
        this.factory = factory;
    }

    /**
     * Executes the mapping logic using the provided requirement.
     * <p>
     * Delegates to the {@link MappingFactory} to ensure capability-guarded
     * creation and execution of the map.
     *
     * @param requirement the validated and capability-locked mapping instructions
     * @return a fully constructed {@link IMap} instance
     */
    @Override
    public IMappingProof map(MappingRequirement requirement) {
        // Use the factory to create
        return factory.create(requirement);
    }
}
