package eu.brokoko.thymely.web.rest;

import eu.brokoko.thymely.ThymelyApp;
import eu.brokoko.thymely.domain.Workperiod;
import eu.brokoko.thymely.repository.WorkperiodRepository;
import eu.brokoko.thymely.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static eu.brokoko.thymely.web.rest.TestUtil.sameInstant;
import static eu.brokoko.thymely.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkperiodResource} REST controller.
 */
@SpringBootTest(classes = ThymelyApp.class)
public class WorkperiodResourceIT {

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkperiodRepository workperiodRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWorkperiodMockMvc;

    private Workperiod workperiod;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkperiodResource workperiodResource = new WorkperiodResource(workperiodRepository);
        this.restWorkperiodMockMvc = MockMvcBuilders.standaloneSetup(workperiodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workperiod createEntity(EntityManager em) {
        Workperiod workperiod = new Workperiod()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .description(DEFAULT_DESCRIPTION);
        return workperiod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workperiod createUpdatedEntity(EntityManager em) {
        Workperiod workperiod = new Workperiod()
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .description(UPDATED_DESCRIPTION);
        return workperiod;
    }

    @BeforeEach
    public void initTest() {
        workperiod = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkperiod() throws Exception {
        int databaseSizeBeforeCreate = workperiodRepository.findAll().size();

        // Create the Workperiod
        restWorkperiodMockMvc.perform(post("/api/workperiods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workperiod)))
            .andExpect(status().isCreated());

        // Validate the Workperiod in the database
        List<Workperiod> workperiodList = workperiodRepository.findAll();
        assertThat(workperiodList).hasSize(databaseSizeBeforeCreate + 1);
        Workperiod testWorkperiod = workperiodList.get(workperiodList.size() - 1);
        assertThat(testWorkperiod.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testWorkperiod.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testWorkperiod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkperiodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workperiodRepository.findAll().size();

        // Create the Workperiod with an existing ID
        workperiod.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkperiodMockMvc.perform(post("/api/workperiods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workperiod)))
            .andExpect(status().isBadRequest());

        // Validate the Workperiod in the database
        List<Workperiod> workperiodList = workperiodRepository.findAll();
        assertThat(workperiodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkperiods() throws Exception {
        // Initialize the database
        workperiodRepository.saveAndFlush(workperiod);

        // Get all the workperiodList
        restWorkperiodMockMvc.perform(get("/api/workperiods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workperiod.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWorkperiod() throws Exception {
        // Initialize the database
        workperiodRepository.saveAndFlush(workperiod);

        // Get the workperiod
        restWorkperiodMockMvc.perform(get("/api/workperiods/{id}", workperiod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workperiod.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingWorkperiod() throws Exception {
        // Get the workperiod
        restWorkperiodMockMvc.perform(get("/api/workperiods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkperiod() throws Exception {
        // Initialize the database
        workperiodRepository.saveAndFlush(workperiod);

        int databaseSizeBeforeUpdate = workperiodRepository.findAll().size();

        // Update the workperiod
        Workperiod updatedWorkperiod = workperiodRepository.findById(workperiod.getId()).get();
        // Disconnect from session so that the updates on updatedWorkperiod are not directly saved in db
        em.detach(updatedWorkperiod);
        updatedWorkperiod
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .description(UPDATED_DESCRIPTION);

        restWorkperiodMockMvc.perform(put("/api/workperiods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkperiod)))
            .andExpect(status().isOk());

        // Validate the Workperiod in the database
        List<Workperiod> workperiodList = workperiodRepository.findAll();
        assertThat(workperiodList).hasSize(databaseSizeBeforeUpdate);
        Workperiod testWorkperiod = workperiodList.get(workperiodList.size() - 1);
        assertThat(testWorkperiod.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWorkperiod.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testWorkperiod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkperiod() throws Exception {
        int databaseSizeBeforeUpdate = workperiodRepository.findAll().size();

        // Create the Workperiod

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkperiodMockMvc.perform(put("/api/workperiods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workperiod)))
            .andExpect(status().isBadRequest());

        // Validate the Workperiod in the database
        List<Workperiod> workperiodList = workperiodRepository.findAll();
        assertThat(workperiodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkperiod() throws Exception {
        // Initialize the database
        workperiodRepository.saveAndFlush(workperiod);

        int databaseSizeBeforeDelete = workperiodRepository.findAll().size();

        // Delete the workperiod
        restWorkperiodMockMvc.perform(delete("/api/workperiods/{id}", workperiod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Workperiod> workperiodList = workperiodRepository.findAll();
        assertThat(workperiodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
