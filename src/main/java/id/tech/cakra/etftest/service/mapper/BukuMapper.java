package id.tech.cakra.etftest.service.mapper;

import id.tech.cakra.etftest.domain.*;
import id.tech.cakra.etftest.service.dto.BukuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Buku} and its DTO {@link BukuDTO}.
 */
@Mapper(componentModel = "spring", uses = {MahasiswaMapper.class})
public interface BukuMapper extends EntityMapper<BukuDTO, Buku> {

    @Mapping(source = "mahasiswa.id", target = "mahasiswaId")
    BukuDTO toDto(Buku buku);

    @Mapping(source = "mahasiswaId", target = "mahasiswa")
    Buku toEntity(BukuDTO bukuDTO);

    default Buku fromId(String id) {
        if (id == null) {
            return null;
        }
        Buku buku = new Buku();
        buku.setId(id);
        return buku;
    }
}
