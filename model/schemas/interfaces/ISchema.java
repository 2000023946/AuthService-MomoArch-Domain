package com.authservice.domain.model.schemas.interfaces;

/**
 * Marker interface for immutable field schemas derived from raw mappings.
 * <p>
 * A schema represents a <strong>stable, validated projection</strong> of an
 * {@link IMap} into a typed structure whose fields correspond directly to
 * a domain aggregate's required data.
 * </p>
 *
 * <p>
 * Schemas are intentionally <em>anemic</em>:
 * <ul>
 * <li>They contain no business logic</li>
 * <li>They expose only validated, strongly-typed fields</li>
 * <li>They exist solely to support safe aggregate reconstitution</li>
 * </ul>
 * </p>
 *
 * <p>
 * By converting a flexible {@link IMap} into a schema, the domain layer
 * enforces key presence, structural correctness, and semantic stability
 * before any aggregate is rehydrated.
 * </p>
 */
public interface ISchema {
}
