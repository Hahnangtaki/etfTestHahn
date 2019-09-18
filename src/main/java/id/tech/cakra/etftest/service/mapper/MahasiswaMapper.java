package id.tech.cakra.etftest.service.mapper;

import id.tech.cakra.etftest.domain.*;
import id.tech.cakra.etftest.service.dto.MahasiswaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mahasiswa} and its DTO {@link MahasiswaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MahasiswaMapper extends EntityMapper<MahasiswaDTO, Mahasiswa> {


    @Mapping(target = "bukus", ignore = true)
    @Mapping(target = "removeBuku", ignore = true)
    Mahasiswa toEntity(MahasiswaDTO mahasiswaDTO);

    default Mahasiswa fromId(String id) {
        if (id == null) {
            return null;
        }
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setId(id);
        return mahasiswa;
    }
}
