package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AttachGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.AttachGroup}.
 */
public interface AttachGroupService {
    /**
     * Save a attachGroup.
     *
     * @param attachGroupDTO the entity to save.
     * @return the persisted entity.
     */
    AttachGroupDTO save(AttachGroupDTO attachGroupDTO);

    /**
     * Updates a attachGroup.
     *
     * @param attachGroupDTO the entity to update.
     * @return the persisted entity.
     */
    AttachGroupDTO update(AttachGroupDTO attachGroupDTO);

    /**
     * Partially updates a attachGroup.
     *
     * @param attachGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachGroupDTO> partialUpdate(AttachGroupDTO attachGroupDTO);

    /**
     * Get all the attachGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachGroupDTO> findOne(Long id);

    /**
     * Delete the "id" attachGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
