package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.AttachGroupAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AttachGroup;
import com.mycompany.myapp.repository.AttachGroupRepository;
import com.mycompany.myapp.service.dto.AttachGroupDTO;
import com.mycompany.myapp.service.mapper.AttachGroupMapper;
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
 * Integration tests for the {@link AttachGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttachGroupResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/attach-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AttachGroupRepository attachGroupRepository;

    @Autowired
    private AttachGroupMapper attachGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachGroupMockMvc;

    private AttachGroup attachGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachGroup createEntity(EntityManager em) {
        AttachGroup attachGroup = new AttachGroup().createdAt(DEFAULT_CREATED_AT).createdBy(DEFAULT_CREATED_BY);
        return attachGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachGroup createUpdatedEntity(EntityManager em) {
        AttachGroup attachGroup = new AttachGroup().createdAt(UPDATED_CREATED_AT).createdBy(UPDATED_CREATED_BY);
        return attachGroup;
    }

    @BeforeEach
    public void initTest() {
        attachGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createAttachGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);
        var returnedAttachGroupDTO = om.readValue(
            restAttachGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachGroupDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AttachGroupDTO.class
        );

        // Validate the AttachGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAttachGroup = attachGroupMapper.toEntity(returnedAttachGroupDTO);
        assertAttachGroupUpdatableFieldsEquals(returnedAttachGroup, getPersistedAttachGroup(returnedAttachGroup));
    }

    @Test
    @Transactional
    void createAttachGroupWithExistingId() throws Exception {
        // Create the AttachGroup with an existing ID
        attachGroup.setId(1L);
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachGroup.setCreatedAt(null);

        // Create the AttachGroup, which fails.
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        restAttachGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachGroupDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attachGroup.setCreatedBy(null);

        // Create the AttachGroup, which fails.
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        restAttachGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachGroupDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttachGroups() throws Exception {
        // Initialize the database
        attachGroupRepository.saveAndFlush(attachGroup);

        // Get all the attachGroupList
        restAttachGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())));
    }

    @Test
    @Transactional
    void getAttachGroup() throws Exception {
        // Initialize the database
        attachGroupRepository.saveAndFlush(attachGroup);

        // Get the attachGroup
        restAttachGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, attachGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachGroup.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAttachGroup() throws Exception {
        // Get the attachGroup
        restAttachGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttachGroup() throws Exception {
        // Initialize the database
        attachGroupRepository.saveAndFlush(attachGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachGroup
        AttachGroup updatedAttachGroup = attachGroupRepository.findById(attachGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAttachGroup are not directly saved in db
        em.detach(updatedAttachGroup);
        updatedAttachGroup.createdAt(UPDATED_CREATED_AT).createdBy(UPDATED_CREATED_BY);
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(updatedAttachGroup);

        restAttachGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attachGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAttachGroupToMatchAllProperties(updatedAttachGroup);
    }

    @Test
    @Transactional
    void putNonExistingAttachGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachGroup.setId(longCount.incrementAndGet());

        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attachGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttachGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachGroup.setId(longCount.incrementAndGet());

        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attachGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttachGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachGroup.setId(longCount.incrementAndGet());

        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attachGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttachGroupWithPatch() throws Exception {
        // Initialize the database
        attachGroupRepository.saveAndFlush(attachGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachGroup using partial update
        AttachGroup partialUpdatedAttachGroup = new AttachGroup();
        partialUpdatedAttachGroup.setId(attachGroup.getId());

        partialUpdatedAttachGroup.createdAt(UPDATED_CREATED_AT);

        restAttachGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttachGroup))
            )
            .andExpect(status().isOk());

        // Validate the AttachGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttachGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAttachGroup, attachGroup),
            getPersistedAttachGroup(attachGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateAttachGroupWithPatch() throws Exception {
        // Initialize the database
        attachGroupRepository.saveAndFlush(attachGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attachGroup using partial update
        AttachGroup partialUpdatedAttachGroup = new AttachGroup();
        partialUpdatedAttachGroup.setId(attachGroup.getId());

        partialUpdatedAttachGroup.createdAt(UPDATED_CREATED_AT).createdBy(UPDATED_CREATED_BY);

        restAttachGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttachGroup))
            )
            .andExpect(status().isOk());

        // Validate the AttachGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttachGroupUpdatableFieldsEquals(partialUpdatedAttachGroup, getPersistedAttachGroup(partialUpdatedAttachGroup));
    }

    @Test
    @Transactional
    void patchNonExistingAttachGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachGroup.setId(longCount.incrementAndGet());

        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attachGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attachGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttachGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachGroup.setId(longCount.incrementAndGet());

        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attachGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttachGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attachGroup.setId(longCount.incrementAndGet());

        // Create the AttachGroup
        AttachGroupDTO attachGroupDTO = attachGroupMapper.toDto(attachGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(attachGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttachGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttachGroup() throws Exception {
        // Initialize the database
        attachGroupRepository.saveAndFlush(attachGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the attachGroup
        restAttachGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, attachGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return attachGroupRepository.count();
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

    protected AttachGroup getPersistedAttachGroup(AttachGroup attachGroup) {
        return attachGroupRepository.findById(attachGroup.getId()).orElseThrow();
    }

    protected void assertPersistedAttachGroupToMatchAllProperties(AttachGroup expectedAttachGroup) {
        assertAttachGroupAllPropertiesEquals(expectedAttachGroup, getPersistedAttachGroup(expectedAttachGroup));
    }

    protected void assertPersistedAttachGroupToMatchUpdatableProperties(AttachGroup expectedAttachGroup) {
        assertAttachGroupAllUpdatablePropertiesEquals(expectedAttachGroup, getPersistedAttachGroup(expectedAttachGroup));
    }
}
