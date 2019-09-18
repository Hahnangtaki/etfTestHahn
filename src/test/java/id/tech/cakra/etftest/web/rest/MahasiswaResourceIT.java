package id.tech.cakra.etftest.web.rest;

import id.tech.cakra.etftest.EtfTestApp;
import id.tech.cakra.etftest.domain.Mahasiswa;
import id.tech.cakra.etftest.repository.MahasiswaRepository;
import id.tech.cakra.etftest.service.MahasiswaService;
import id.tech.cakra.etftest.service.dto.MahasiswaDTO;
import id.tech.cakra.etftest.service.mapper.MahasiswaMapper;
import id.tech.cakra.etftest.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static id.tech.cakra.etftest.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MahasiswaResource} REST controller.
 */
@SpringBootTest(classes = EtfTestApp.class)
public class MahasiswaResourceIT {

    private static final String DEFAULT_NIM = "AAAAA";
    private static final String UPDATED_NIM = "BBBBB";

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_ALAMAT = "AAAAAAAAAA";
    private static final String UPDATED_ALAMAT = "BBBBBBBBBB";

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private MahasiswaMapper mahasiswaMapper;

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restMahasiswaMockMvc;

    private Mahasiswa mahasiswa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MahasiswaResource mahasiswaResource = new MahasiswaResource(mahasiswaService);
        this.restMahasiswaMockMvc = MockMvcBuilders.standaloneSetup(mahasiswaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mahasiswa createEntity() {
        Mahasiswa mahasiswa = new Mahasiswa()
            .nim(DEFAULT_NIM)
            .nama(DEFAULT_NAMA)
            .alamat(DEFAULT_ALAMAT);
        return mahasiswa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mahasiswa createUpdatedEntity() {
        Mahasiswa mahasiswa = new Mahasiswa()
            .nim(UPDATED_NIM)
            .nama(UPDATED_NAMA)
            .alamat(UPDATED_ALAMAT);
        return mahasiswa;
    }

    @BeforeEach
    public void initTest() {
        mahasiswaRepository.deleteAll();
        mahasiswa = createEntity();
    }

    @Test
    public void createMahasiswa() throws Exception {
        int databaseSizeBeforeCreate = mahasiswaRepository.findAll().size();

        // Create the Mahasiswa
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);
        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isCreated());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeCreate + 1);
        Mahasiswa testMahasiswa = mahasiswaList.get(mahasiswaList.size() - 1);
        assertThat(testMahasiswa.getNim()).isEqualTo(DEFAULT_NIM);
        assertThat(testMahasiswa.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMahasiswa.getAlamat()).isEqualTo(DEFAULT_ALAMAT);
    }

    @Test
    public void createMahasiswaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mahasiswaRepository.findAll().size();

        // Create the Mahasiswa with an existing ID
        mahasiswa.setId("existing_id");
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllMahasiswas() throws Exception {
        // Initialize the database
        mahasiswaRepository.save(mahasiswa);

        // Get all the mahasiswaList
        restMahasiswaMockMvc.perform(get("/api/mahasiswas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mahasiswa.getId())))
            .andExpect(jsonPath("$.[*].nim").value(hasItem(DEFAULT_NIM.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())));
    }
    
    @Test
    public void getMahasiswa() throws Exception {
        // Initialize the database
        mahasiswaRepository.save(mahasiswa);

        // Get the mahasiswa
        restMahasiswaMockMvc.perform(get("/api/mahasiswas/{id}", mahasiswa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mahasiswa.getId()))
            .andExpect(jsonPath("$.nim").value(DEFAULT_NIM.toString()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.alamat").value(DEFAULT_ALAMAT.toString()));
    }

    @Test
    public void getNonExistingMahasiswa() throws Exception {
        // Get the mahasiswa
        restMahasiswaMockMvc.perform(get("/api/mahasiswas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMahasiswa() throws Exception {
        // Initialize the database
        mahasiswaRepository.save(mahasiswa);

        int databaseSizeBeforeUpdate = mahasiswaRepository.findAll().size();

        // Update the mahasiswa
        Mahasiswa updatedMahasiswa = mahasiswaRepository.findById(mahasiswa.getId()).get();
        updatedMahasiswa
            .nim(UPDATED_NIM)
            .nama(UPDATED_NAMA)
            .alamat(UPDATED_ALAMAT);
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(updatedMahasiswa);

        restMahasiswaMockMvc.perform(put("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isOk());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeUpdate);
        Mahasiswa testMahasiswa = mahasiswaList.get(mahasiswaList.size() - 1);
        assertThat(testMahasiswa.getNim()).isEqualTo(UPDATED_NIM);
        assertThat(testMahasiswa.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMahasiswa.getAlamat()).isEqualTo(UPDATED_ALAMAT);
    }

    @Test
    public void updateNonExistingMahasiswa() throws Exception {
        int databaseSizeBeforeUpdate = mahasiswaRepository.findAll().size();

        // Create the Mahasiswa
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMahasiswaMockMvc.perform(put("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMahasiswa() throws Exception {
        // Initialize the database
        mahasiswaRepository.save(mahasiswa);

        int databaseSizeBeforeDelete = mahasiswaRepository.findAll().size();

        // Delete the mahasiswa
        restMahasiswaMockMvc.perform(delete("/api/mahasiswas/{id}", mahasiswa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mahasiswa.class);
        Mahasiswa mahasiswa1 = new Mahasiswa();
        mahasiswa1.setId("id1");
        Mahasiswa mahasiswa2 = new Mahasiswa();
        mahasiswa2.setId(mahasiswa1.getId());
        assertThat(mahasiswa1).isEqualTo(mahasiswa2);
        mahasiswa2.setId("id2");
        assertThat(mahasiswa1).isNotEqualTo(mahasiswa2);
        mahasiswa1.setId(null);
        assertThat(mahasiswa1).isNotEqualTo(mahasiswa2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MahasiswaDTO.class);
        MahasiswaDTO mahasiswaDTO1 = new MahasiswaDTO();
        mahasiswaDTO1.setId("id1");
        MahasiswaDTO mahasiswaDTO2 = new MahasiswaDTO();
        assertThat(mahasiswaDTO1).isNotEqualTo(mahasiswaDTO2);
        mahasiswaDTO2.setId(mahasiswaDTO1.getId());
        assertThat(mahasiswaDTO1).isEqualTo(mahasiswaDTO2);
        mahasiswaDTO2.setId("id2");
        assertThat(mahasiswaDTO1).isNotEqualTo(mahasiswaDTO2);
        mahasiswaDTO1.setId(null);
        assertThat(mahasiswaDTO1).isNotEqualTo(mahasiswaDTO2);
    }
}
