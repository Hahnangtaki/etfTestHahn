package id.tech.cakra.etftest.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Mahasiswa.
 */
@Document(collection = "mahasiswa")
public class Mahasiswa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(min = 5, max = 5)
    @Field("nim")
    private String nim;

    @Size(max = 50)
    @Field("nama")
    private String nama;

    @Size(max = 500)
    @Field("alamat")
    private String alamat;

    @DBRef
    @Field("buku")
    private Set<Buku> bukus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public Mahasiswa nim(String nim) {
        this.nim = nim;
        return this;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public Mahasiswa nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public Mahasiswa alamat(String alamat) {
        this.alamat = alamat;
        return this;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Set<Buku> getBukus() {
        return bukus;
    }

    public Mahasiswa bukus(Set<Buku> bukus) {
        this.bukus = bukus;
        return this;
    }

    public Mahasiswa addBuku(Buku buku) {
        this.bukus.add(buku);
        buku.setMahasiswa(this);
        return this;
    }

    public Mahasiswa removeBuku(Buku buku) {
        this.bukus.remove(buku);
        buku.setMahasiswa(null);
        return this;
    }

    public void setBukus(Set<Buku> bukus) {
        this.bukus = bukus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mahasiswa)) {
            return false;
        }
        return id != null && id.equals(((Mahasiswa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
            "id=" + getId() +
            ", nim='" + getNim() + "'" +
            ", nama='" + getNama() + "'" +
            ", alamat='" + getAlamat() + "'" +
            "}";
    }
}
