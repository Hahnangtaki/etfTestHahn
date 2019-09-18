package id.tech.cakra.etftest.service;

import id.tech.cakra.etftest.service.dto.MahasiswaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link id.tech.cakra.etftest.domain.Mahasiswa}.
 */
public interface MahasiswaService {

    /**
     * Save a mahasiswa.
     *
     * @param mahasiswaDTO the entity to save.
     * @return the persisted entity.
     */
    MahasiswaDTO save(MahasiswaDTO mahasiswaDTO);

    /**
     * Get all the mahasiswas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MahasiswaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mahasiswa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MahasiswaDTO> findOne(String id);

    /**
     * Delete the "id" mahasiswa.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
