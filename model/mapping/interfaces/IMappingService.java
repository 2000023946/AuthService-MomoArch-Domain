package com.authservice.domain.model.aggregates.mapping.interfaces;

import com.authservice.domain.model.aggregates.mapping.requirements.MappingRequirement;

/**
 * Primary entry point for converting validated JSON strings into
 * structured, domain-ready {@link IMap} objects.
 * <p>
 * Implementations of this service encapsulate the logic required
 * to safely transform a {@link MappingRequirement} into a domain map,
 * ensuring capability-guarded access to internal components.
 */
public interface IMappingService {

    /**
     * Executes the mapping logic defined by the given {@link MappingRequirement}.
     * <p>
     * The requirement enforces capability-guarding, ensuring that only authorized
     * factories can supply the token proof and JSON parser.
     *
     * @param requirement the capability-locked mapping instructions
     * @return a proof-backed {@link IMap} containing the structured mapping
     */
    IMappingProof map(MappingRequirement requirement);
}
