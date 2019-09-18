package id.tech.cakra.etftest.repository;
import id.tech.cakra.etftest.domain.Mahasiswa;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Mahasiswa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MahasiswaRepository extends MongoRepository<Mahasiswa, String> {

}
