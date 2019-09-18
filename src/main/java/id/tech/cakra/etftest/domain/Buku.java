package id.tech.cakra.etftest.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Buku.
 */
@Document(collection = "buku")
public class Buku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 100)
    @Field("judul_buku")
    private String judulBuku;

    @Size(max = 50)
    @Field("kategori")
    private String kategori;

    @DBRef
    @Field("mahasiswa")
    @JsonIgnoreProperties("bukus")
    private Mahasiswa mahasiswa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public Buku judulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
        return this;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getKategori() {
        return kategori;
    }

    public Buku kategori(String kategori) {
        this.kategori = kategori;
        return this;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public Buku mahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        return this;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Buku)) {
            return false;
        }
        return id != null && id.equals(((Buku) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Buku{" +
            "id=" + getId() +
            ", judulBuku='" + getJudulBuku() + "'" +
            ", kategori='" + getKategori() + "'" +
            "}";
    }
}
