package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.service.BoardService;
import com.mycompany.myapp.service.dto.BoardDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Board}.
 */
@RestController
@RequestMapping("/api/boards")
public class BoardResource {

    private final Logger log = LoggerFactory.getLogger(BoardResource.class);

    private static final String ENTITY_NAME = "board";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoardService boardService;

    private final BoardRepository boardRepository;

    public BoardResource(BoardService boardService, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    /**
     * {@code POST  /boards} : Create a new board.
     *
     * @param boardDTO the boardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boardDTO, or with status {@code 400 (Bad Request)} if the board has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BoardDTO> createBoard(@Valid @RequestBody BoardDTO boardDTO) throws URISyntaxException {
        log.debug("REST request to save Board : {}", boardDTO);
        if (boardDTO.getId() != null) {
            throw new BadRequestAlertException("A new board cannot already have an ID", ENTITY_NAME, "idexists");
        }
        boardDTO = boardService.save(boardDTO);
        return ResponseEntity.created(new URI("/api/boards/" + boardDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, boardDTO.getId().toString()))
            .body(boardDTO);
    }

    /**
     * {@code PUT  /boards/:id} : Updates an existing board.
     *
     * @param id the id of the boardDTO to save.
     * @param boardDTO the boardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boardDTO,
     * or with status {@code 400 (Bad Request)} if the boardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BoardDTO boardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Board : {}, {}", id, boardDTO);
        if (boardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        boardDTO = boardService.update(boardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boardDTO.getId().toString()))
            .body(boardDTO);
    }

    /**
     * {@code PATCH  /boards/:id} : Partial updates given fields of an existing board, field will ignore if it is null
     *
     * @param id the id of the boardDTO to save.
     * @param boardDTO the boardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boardDTO,
     * or with status {@code 400 (Bad Request)} if the boardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the boardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the boardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BoardDTO> partialUpdateBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BoardDTO boardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Board partially : {}, {}", id, boardDTO);
        if (boardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BoardDTO> result = boardService.partialUpdate(boardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /boards} : get all the boards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boards in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BoardDTO>> getAllBoards(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Boards");
        Page<BoardDTO> page = boardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boards/:id} : get the "id" board.
     *
     * @param id the id of the boardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable("id") Long id) {
        log.debug("REST request to get Board : {}", id);
        Optional<BoardDTO> boardDTO = boardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boardDTO);
    }

    /**
     * {@code DELETE  /boards/:id} : delete the "id" board.
     *
     * @param id the id of the boardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        log.debug("REST request to delete Board : {}", id);
        boardService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
