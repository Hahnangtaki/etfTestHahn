package id.tech.cakra.etftest.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link id.tech.cakra.etftest.domain.Buku} entity.
 */
public class BukuDTO implements Serializable {

    private String id;

    @Size(max = 100)
    private String judulBuku;

    @Size(max = 50)
    private String kategori;


    private String mahasiswaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getMahasiswaId() {
        return mahasiswaId;
    }

    public void setMahasiswaId(String mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BukuDTO bukuDTO = (BukuDTO) o;
        if (bukuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bukuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BukuDTO{" +
            "id=" + getId() +
            ", judulBuku='" + getJudulBuku() + "'" +
            ", kategori='" + getKategori() + "'" +
            ", mahasiswa=" + getMahasiswaId() +
            "}";
    }
}
