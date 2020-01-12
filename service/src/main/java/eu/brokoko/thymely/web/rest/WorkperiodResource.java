package eu.brokoko.thymely.web.rest;

import eu.brokoko.thymely.domain.Workperiod;
import eu.brokoko.thymely.repository.WorkperiodRepository;
import eu.brokoko.thymely.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link eu.brokoko.thymely.domain.Workperiod}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkperiodResource {

    private final Logger log = LoggerFactory.getLogger(WorkperiodResource.class);

    private static final String ENTITY_NAME = "workperiod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkperiodRepository workperiodRepository;

    public WorkperiodResource(WorkperiodRepository workperiodRepository) {
        this.workperiodRepository = workperiodRepository;
    }

    /**
     * {@code POST  /workperiods} : Create a new workperiod.
     *
     * @param workperiod the workperiod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workperiod, or with status {@code 400 (Bad Request)} if the workperiod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workperiods")
    public ResponseEntity<Workperiod> createWorkperiod(@RequestBody Workperiod workperiod) throws URISyntaxException {
        log.debug("REST request to save Workperiod : {}", workperiod);
        if (workperiod.getId() != null) {
            throw new BadRequestAlertException("A new workperiod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Workperiod result = workperiodRepository.save(workperiod);
        return ResponseEntity.created(new URI("/api/workperiods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workperiods} : Updates an existing workperiod.
     *
     * @param workperiod the workperiod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workperiod,
     * or with status {@code 400 (Bad Request)} if the workperiod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workperiod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workperiods")
    public ResponseEntity<Workperiod> updateWorkperiod(@RequestBody Workperiod workperiod) throws URISyntaxException {
        log.debug("REST request to update Workperiod : {}", workperiod);
        if (workperiod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Workperiod result = workperiodRepository.save(workperiod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workperiod.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /workperiods} : get all the workperiods.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workperiods in body.
     */
    @GetMapping("/workperiods")
    public List<Workperiod> getAllWorkperiods() {
        log.debug("REST request to get all Workperiods");
        return workperiodRepository.findAll();
    }

    @GetMapping("/workperiods/samples")
    public List<Workperiod> getAllWorkperiodsSamples() {
        log.debug("REST request to get all Workperiods");
        return workperiodRepository.findAll();
    }

    /**
     * {@code GET  /workperiods/:id} : get the "id" workperiod.
     *
     * @param id the id of the workperiod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workperiod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workperiods/{id}")
    public ResponseEntity<Workperiod> getWorkperiod(@PathVariable Long id) {
        log.debug("REST request to get Workperiod : {}", id);
        Optional<Workperiod> workperiod = workperiodRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workperiod);
    }

    /**
     * {@code DELETE  /workperiods/:id} : delete the "id" workperiod.
     *
     * @param id the id of the workperiod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workperiods/{id}")
    public ResponseEntity<Void> deleteWorkperiod(@PathVariable Long id) {
        log.debug("REST request to delete Workperiod : {}", id);
        workperiodRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
