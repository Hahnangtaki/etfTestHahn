package id.tech.cakra.etftest.web.rest;

import id.tech.cakra.etftest.service.MahasiswaService;
import id.tech.cakra.etftest.web.rest.errors.BadRequestAlertException;
import id.tech.cakra.etftest.service.dto.MahasiswaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link id.tech.cakra.etftest.domain.Mahasiswa}.
 */
@RestController
@RequestMapping("/api")
public class MahasiswaResource {

    private final Logger log = LoggerFactory.getLogger(MahasiswaResource.class);

    private static final String ENTITY_NAME = "mahasiswa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MahasiswaService mahasiswaService;

    public MahasiswaResource(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }

    /**
     * {@code POST  /mahasiswas} : Create a new mahasiswa.
     *
     * @param mahasiswaDTO the mahasiswaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mahasiswaDTO, or with status {@code 400 (Bad Request)} if the mahasiswa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mahasiswas")
    public ResponseEntity<MahasiswaDTO> createMahasiswa(@Valid @RequestBody MahasiswaDTO mahasiswaDTO) throws URISyntaxException {
        log.debug("REST request to save Mahasiswa : {}", mahasiswaDTO);
        if (mahasiswaDTO.getId() != null) {
            throw new BadRequestAlertException("A new mahasiswa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MahasiswaDTO result = mahasiswaService.save(mahasiswaDTO);
        return ResponseEntity.created(new URI("/api/mahasiswas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mahasiswas} : Updates an existing mahasiswa.
     *
     * @param mahasiswaDTO the mahasiswaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mahasiswaDTO,
     * or with status {@code 400 (Bad Request)} if the mahasiswaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mahasiswaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mahasiswas")
    public ResponseEntity<MahasiswaDTO> updateMahasiswa(@Valid @RequestBody MahasiswaDTO mahasiswaDTO) throws URISyntaxException {
        log.debug("REST request to update Mahasiswa : {}", mahasiswaDTO);
        if (mahasiswaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MahasiswaDTO result = mahasiswaService.save(mahasiswaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mahasiswaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mahasiswas} : get all the mahasiswas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mahasiswas in body.
     */
    @GetMapping("/mahasiswas")
    public ResponseEntity<List<MahasiswaDTO>> getAllMahasiswas(Pageable pageable) {
        log.debug("REST request to get a page of Mahasiswas");
        Page<MahasiswaDTO> page = mahasiswaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mahasiswas/:id} : get the "id" mahasiswa.
     *
     * @param id the id of the mahasiswaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mahasiswaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mahasiswas/{id}")
    public ResponseEntity<MahasiswaDTO> getMahasiswa(@PathVariable String id) {
        log.debug("REST request to get Mahasiswa : {}", id);
        Optional<MahasiswaDTO> mahasiswaDTO = mahasiswaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mahasiswaDTO);
    }

    /**
     * {@code DELETE  /mahasiswas/:id} : delete the "id" mahasiswa.
     *
     * @param id the id of the mahasiswaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mahasiswas/{id}")
    public ResponseEntity<Void> deleteMahasiswa(@PathVariable String id) {
        log.debug("REST request to delete Mahasiswa : {}", id);
        mahasiswaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
