package id.tech.cakra.etftest.service.impl;

import id.tech.cakra.etftest.service.BukuService;
import id.tech.cakra.etftest.domain.Buku;
import id.tech.cakra.etftest.repository.BukuRepository;
import id.tech.cakra.etftest.service.dto.BukuDTO;
import id.tech.cakra.etftest.service.mapper.BukuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Buku}.
 */
@Service
public class BukuServiceImpl implements BukuService {

    private final Logger log = LoggerFactory.getLogger(BukuServiceImpl.class);

    private final BukuRepository bukuRepository;

    private final BukuMapper bukuMapper;

    public BukuServiceImpl(BukuRepository bukuRepository, BukuMapper bukuMapper) {
        this.bukuRepository = bukuRepository;
        this.bukuMapper = bukuMapper;
    }

    /**
     * Save a buku.
     *
     * @param bukuDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BukuDTO save(BukuDTO bukuDTO) {
        log.debug("Request to save Buku : {}", bukuDTO);
        Buku buku = bukuMapper.toEntity(bukuDTO);
        buku = bukuRepository.save(buku);
        return bukuMapper.toDto(buku);
    }

    /**
     * Get all the bukus.
     *
     * @return the list of entities.
     */
    @Override
    public List<BukuDTO> findAll() {
        log.debug("Request to get all Bukus");
        return bukuRepository.findAll().stream()
            .map(bukuMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one buku by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BukuDTO> findOne(String id) {
        log.debug("Request to get Buku : {}", id);
        return bukuRepository.findById(id)
            .map(bukuMapper::toDto);
    }

    /**
     * Delete the buku by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Buku : {}", id);
        bukuRepository.deleteById(id);
    }
}
