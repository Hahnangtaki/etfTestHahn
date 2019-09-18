package id.tech.cakra.etftest.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link id.tech.cakra.etftest.domain.Mahasiswa} entity.
 */
public class MahasiswaDTO implements Serializable {

    private String id;

    @Size(min = 5, max = 5)
    private String nim;

    @Size(max = 50)
    private String nama;

    @Size(max = 500)
    private String alamat;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MahasiswaDTO mahasiswaDTO = (MahasiswaDTO) o;
        if (mahasiswaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mahasiswaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MahasiswaDTO{" +
            "id=" + getId() +
            ", nim='" + getNim() + "'" +
            ", nama='" + getNama() + "'" +
            ", alamat='" + getAlamat() + "'" +
            "}";
    }
}
