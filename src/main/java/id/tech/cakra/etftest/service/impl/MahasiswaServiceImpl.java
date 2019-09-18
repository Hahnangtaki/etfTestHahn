package id.tech.cakra.etftest.service.impl;

import id.tech.cakra.etftest.service.MahasiswaService;
import id.tech.cakra.etftest.domain.Mahasiswa;
import id.tech.cakra.etftest.repository.MahasiswaRepository;
import id.tech.cakra.etftest.service.dto.MahasiswaDTO;
import id.tech.cakra.etftest.service.mapper.MahasiswaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mahasiswa}.
 */
@Service
public class MahasiswaServiceImpl implements MahasiswaService {

    private final Logger log = LoggerFactory.getLogger(MahasiswaServiceImpl.class);

    private final MahasiswaRepository mahasiswaRepository;

    private final MahasiswaMapper mahasiswaMapper;

    public MahasiswaServiceImpl(MahasiswaRepository mahasiswaRepository, MahasiswaMapper mahasiswaMapper) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.mahasiswaMapper = mahasiswaMapper;
    }

    /**
     * Save a mahasiswa.
     *
     * @param mahasiswaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MahasiswaDTO save(MahasiswaDTO mahasiswaDTO) {
        log.debug("Request to save Mahasiswa : {}", mahasiswaDTO);
        Mahasiswa mahasiswa = mahasiswaMapper.toEntity(mahasiswaDTO);
        mahasiswa = mahasiswaRepository.save(mahasiswa);
        return mahasiswaMapper.toDto(mahasiswa);
    }

    /**
     * Get all the mahasiswas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<MahasiswaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mahasiswas");
        return mahasiswaRepository.findAll(pageable)
            .map(mahasiswaMapper::toDto);
    }


    /**
     * Get one mahasiswa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<MahasiswaDTO> findOne(String id) {
        log.debug("Request to get Mahasiswa : {}", id);
        return mahasiswaRepository.findById(id)
            .map(mahasiswaMapper::toDto);
    }

    /**
     * Delete the mahasiswa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Mahasiswa : {}", id);
        mahasiswaRepository.deleteById(id);
    }
}
