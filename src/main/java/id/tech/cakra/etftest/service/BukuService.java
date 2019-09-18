package id.tech.cakra.etftest.service;

import id.tech.cakra.etftest.service.dto.BukuDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link id.tech.cakra.etftest.domain.Buku}.
 */
public interface BukuService {

    /**
     * Save a buku.
     *
     * @param bukuDTO the entity to save.
     * @return the persisted entity.
     */
    BukuDTO save(BukuDTO bukuDTO);

    /**
     * Get all the bukus.
     *
     * @return the list of entities.
     */
    List<BukuDTO> findAll();


    /**
     * Get the "id" buku.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BukuDTO> findOne(String id);

    /**
     * Delete the "id" buku.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
