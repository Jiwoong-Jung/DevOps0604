package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.BoardAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.mapper.BoardMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BoardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BoardResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_MODIFIED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/boards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoardMockMvc;

    private Board board;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Board createEntity(EntityManager em) {
        Board board = new Board()
            .title(DEFAULT_TITLE)
            .category(DEFAULT_CATEGORY)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedAt(DEFAULT_MODIFIED_AT)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return board;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Board createUpdatedEntity(EntityManager em) {
        Board board = new Board()
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return board;
    }

    @BeforeEach
    public void initTest() {
        board = createEntity(em);
    }

    @Test
    @Transactional
    void createBoard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);
        var returnedBoardDTO = om.readValue(
            restBoardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BoardDTO.class
        );

        // Validate the Board in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBoard = boardMapper.toEntity(returnedBoardDTO);
        assertBoardUpdatableFieldsEquals(returnedBoard, getPersistedBoard(returnedBoard));
    }

    @Test
    @Transactional
    void createBoardWithExistingId() throws Exception {
        // Create the Board with an existing ID
        board.setId(1L);
        BoardDTO boardDTO = boardMapper.toDto(board);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        board.setTitle(null);

        // Create the Board, which fails.
        BoardDTO boardDTO = boardMapper.toDto(board);

        restBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        board.setCategory(null);

        // Create the Board, which fails.
        BoardDTO boardDTO = boardMapper.toDto(board);

        restBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        board.setCreatedAt(null);

        // Create the Board, which fails.
        BoardDTO boardDTO = boardMapper.toDto(board);

        restBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        board.setCreatedBy(null);

        // Create the Board, which fails.
        BoardDTO boardDTO = boardMapper.toDto(board);

        restBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBoards() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        // Get all the boardList
        restBoardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(board.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(sameInstant(DEFAULT_MODIFIED_AT))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())));
    }

    @Test
    @Transactional
    void getBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        // Get the board
        restBoardMockMvc
            .perform(get(ENTITY_API_URL_ID, board.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(board.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedAt").value(sameInstant(DEFAULT_MODIFIED_AT)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBoard() throws Exception {
        // Get the board
        restBoardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the board
        Board updatedBoard = boardRepository.findById(board.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBoard are not directly saved in db
        em.detach(updatedBoard);
        updatedBoard
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY);
        BoardDTO boardDTO = boardMapper.toDto(updatedBoard);

        restBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, boardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO))
            )
            .andExpect(status().isOk());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBoardToMatchAllProperties(updatedBoard);
    }

    @Test
    @Transactional
    void putNonExistingBoard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        board.setId(longCount.incrementAndGet());

        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, boardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBoard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        board.setId(longCount.incrementAndGet());

        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(boardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBoard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        board.setId(longCount.incrementAndGet());

        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBoardWithPatch() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the board using partial update
        Board partialUpdatedBoard = new Board();
        partialUpdatedBoard.setId(board.getId());

        partialUpdatedBoard.category(UPDATED_CATEGORY).createdAt(UPDATED_CREATED_AT).modifiedAt(UPDATED_MODIFIED_AT);

        restBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoard))
            )
            .andExpect(status().isOk());

        // Validate the Board in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBoard, board), getPersistedBoard(board));
    }

    @Test
    @Transactional
    void fullUpdateBoardWithPatch() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the board using partial update
        Board partialUpdatedBoard = new Board();
        partialUpdatedBoard.setId(board.getId());

        partialUpdatedBoard
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoard))
            )
            .andExpect(status().isOk());

        // Validate the Board in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoardUpdatableFieldsEquals(partialUpdatedBoard, getPersistedBoard(partialUpdatedBoard));
    }

    @Test
    @Transactional
    void patchNonExistingBoard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        board.setId(longCount.incrementAndGet());

        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, boardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(boardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBoard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        board.setId(longCount.incrementAndGet());

        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(boardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBoard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        board.setId(longCount.incrementAndGet());

        // Create the Board
        BoardDTO boardDTO = boardMapper.toDto(board);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(boardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Board in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the board
        restBoardMockMvc
            .perform(delete(ENTITY_API_URL_ID, board.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return boardRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Board getPersistedBoard(Board board) {
        return boardRepository.findById(board.getId()).orElseThrow();
    }

    protected void assertPersistedBoardToMatchAllProperties(Board expectedBoard) {
        assertBoardAllPropertiesEquals(expectedBoard, getPersistedBoard(expectedBoard));
    }

    protected void assertPersistedBoardToMatchUpdatableProperties(Board expectedBoard) {
        assertBoardAllUpdatablePropertiesEquals(expectedBoard, getPersistedBoard(expectedBoard));
    }
}
