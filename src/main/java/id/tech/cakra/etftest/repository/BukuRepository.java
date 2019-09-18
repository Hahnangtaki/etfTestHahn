package id.tech.cakra.etftest.repository;
import id.tech.cakra.etftest.domain.Buku;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Buku entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BukuRepository extends MongoRepository<Buku, String> {

}
