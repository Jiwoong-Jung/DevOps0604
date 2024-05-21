package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AttachGroupRepository;
import com.mycompany.myapp.service.AttachGroupService;
import com.mycompany.myapp.service.dto.AttachGroupDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AttachGroup}.
 */
@RestController
@RequestMapping("/api/attach-groups")
public class AttachGroupResource {

    private final Logger log = LoggerFactory.getLogger(AttachGroupResource.class);

    private static final String ENTITY_NAME = "attachGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachGroupService attachGroupService;

    private final AttachGroupRepository attachGroupRepository;

    public AttachGroupResource(AttachGroupService attachGroupService, AttachGroupRepository attachGroupRepository) {
        this.attachGroupService = attachGroupService;
        this.attachGroupRepository = attachGroupRepository;
    }

    /**
     * {@code POST  /attach-groups} : Create a new attachGroup.
     *
     * @param attachGroupDTO the attachGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachGroupDTO, or with status {@code 400 (Bad Request)} if the attachGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AttachGroupDTO> createAttachGroup(@Valid @RequestBody AttachGroupDTO attachGroupDTO) throws URISyntaxException {
        log.debug("REST request to save AttachGroup : {}", attachGroupDTO);
        if (attachGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        attachGroupDTO = attachGroupService.save(attachGroupDTO);
        return ResponseEntity.created(new URI("/api/attach-groups/" + attachGroupDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, attachGroupDTO.getId().toString()))
            .body(attachGroupDTO);
    }

    /**
     * {@code PUT  /attach-groups/:id} : Updates an existing attachGroup.
     *
     * @param id the id of the attachGroupDTO to save.
     * @param attachGroupDTO the attachGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachGroupDTO,
     * or with status {@code 400 (Bad Request)} if the attachGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttachGroupDTO> updateAttachGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttachGroupDTO attachGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachGroup : {}, {}", id, attachGroupDTO);
        if (attachGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        attachGroupDTO = attachGroupService.update(attachGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachGroupDTO.getId().toString()))
            .body(attachGroupDTO);
    }

    /**
     * {@code PATCH  /attach-groups/:id} : Partial updates given fields of an existing attachGroup, field will ignore if it is null
     *
     * @param id the id of the attachGroupDTO to save.
     * @param attachGroupDTO the attachGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachGroupDTO,
     * or with status {@code 400 (Bad Request)} if the attachGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachGroupDTO> partialUpdateAttachGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttachGroupDTO attachGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachGroup partially : {}, {}", id, attachGroupDTO);
        if (attachGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachGroupDTO> result = attachGroupService.partialUpdate(attachGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attach-groups} : get all the attachGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AttachGroupDTO>> getAllAttachGroups(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AttachGroups");
        Page<AttachGroupDTO> page = attachGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attach-groups/:id} : get the "id" attachGroup.
     *
     * @param id the id of the attachGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttachGroupDTO> getAttachGroup(@PathVariable("id") Long id) {
        log.debug("REST request to get AttachGroup : {}", id);
        Optional<AttachGroupDTO> attachGroupDTO = attachGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachGroupDTO);
    }

    /**
     * {@code DELETE  /attach-groups/:id} : delete the "id" attachGroup.
     *
     * @param id the id of the attachGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete AttachGroup : {}", id);
        attachGroupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
