package id.tech.cakra.etftest.web.rest;

import id.tech.cakra.etftest.service.BukuService;
import id.tech.cakra.etftest.web.rest.errors.BadRequestAlertException;
import id.tech.cakra.etftest.service.dto.BukuDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link id.tech.cakra.etftest.domain.Buku}.
 */
@RestController
@RequestMapping("/api")
public class BukuResource {

    private final Logger log = LoggerFactory.getLogger(BukuResource.class);

    private static final String ENTITY_NAME = "buku";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BukuService bukuService;

    public BukuResource(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    /**
     * {@code POST  /bukus} : Create a new buku.
     *
     * @param bukuDTO the bukuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bukuDTO, or with status {@code 400 (Bad Request)} if the buku has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bukus")
    public ResponseEntity<BukuDTO> createBuku(@Valid @RequestBody BukuDTO bukuDTO) throws URISyntaxException {
        log.debug("REST request to save Buku : {}", bukuDTO);
        if (bukuDTO.getId() != null) {
            throw new BadRequestAlertException("A new buku cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BukuDTO result = bukuService.save(bukuDTO);
        return ResponseEntity.created(new URI("/api/bukus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bukus} : Updates an existing buku.
     *
     * @param bukuDTO the bukuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bukuDTO,
     * or with status {@code 400 (Bad Request)} if the bukuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bukuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bukus")
    public ResponseEntity<BukuDTO> updateBuku(@Valid @RequestBody BukuDTO bukuDTO) throws URISyntaxException {
        log.debug("REST request to update Buku : {}", bukuDTO);
        if (bukuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BukuDTO result = bukuService.save(bukuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bukuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bukus} : get all the bukus.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bukus in body.
     */
    @GetMapping("/bukus")
    public List<BukuDTO> getAllBukus() {
        log.debug("REST request to get all Bukus");
        return bukuService.findAll();
    }

    /**
     * {@code GET  /bukus/:id} : get the "id" buku.
     *
     * @param id the id of the bukuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bukuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bukus/{id}")
    public ResponseEntity<BukuDTO> getBuku(@PathVariable String id) {
        log.debug("REST request to get Buku : {}", id);
        Optional<BukuDTO> bukuDTO = bukuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bukuDTO);
    }

    /**
     * {@code DELETE  /bukus/:id} : delete the "id" buku.
     *
     * @param id the id of the bukuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bukus/{id}")
    public ResponseEntity<Void> deleteBuku(@PathVariable String id) {
        log.debug("REST request to delete Buku : {}", id);
        bukuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
