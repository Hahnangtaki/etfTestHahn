package id.tech.cakra.etftest.web.rest;

import id.tech.cakra.etftest.EtfTestApp;
import id.tech.cakra.etftest.domain.Buku;
import id.tech.cakra.etftest.repository.BukuRepository;
import id.tech.cakra.etftest.service.BukuService;
import id.tech.cakra.etftest.service.dto.BukuDTO;
import id.tech.cakra.etftest.service.mapper.BukuMapper;
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
 * Integration tests for the {@link BukuResource} REST controller.
 */
@SpringBootTest(classes = EtfTestApp.class)
public class BukuResourceIT {

    private static final String DEFAULT_JUDUL_BUKU = "AAAAAAAAAA";
    private static final String UPDATED_JUDUL_BUKU = "BBBBBBBBBB";

    private static final String DEFAULT_KATEGORI = "AAAAAAAAAA";
    private static final String UPDATED_KATEGORI = "BBBBBBBBBB";

    @Autowired
    private BukuRepository bukuRepository;

    @Autowired
    private BukuMapper bukuMapper;

    @Autowired
    private BukuService bukuService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBukuMockMvc;

    private Buku buku;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BukuResource bukuResource = new BukuResource(bukuService);
        this.restBukuMockMvc = MockMvcBuilders.standaloneSetup(bukuResource)
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
    public static Buku createEntity() {
        Buku buku = new Buku()
            .judulBuku(DEFAULT_JUDUL_BUKU)
            .kategori(DEFAULT_KATEGORI);
        return buku;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buku createUpdatedEntity() {
        Buku buku = new Buku()
            .judulBuku(UPDATED_JUDUL_BUKU)
            .kategori(UPDATED_KATEGORI);
        return buku;
    }

    @BeforeEach
    public void initTest() {
        bukuRepository.deleteAll();
        buku = createEntity();
    }

    @Test
    public void createBuku() throws Exception {
        int databaseSizeBeforeCreate = bukuRepository.findAll().size();

        // Create the Buku
        BukuDTO bukuDTO = bukuMapper.toDto(buku);
        restBukuMockMvc.perform(post("/api/bukus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bukuDTO)))
            .andExpect(status().isCreated());

        // Validate the Buku in the database
        List<Buku> bukuList = bukuRepository.findAll();
        assertThat(bukuList).hasSize(databaseSizeBeforeCreate + 1);
        Buku testBuku = bukuList.get(bukuList.size() - 1);
        assertThat(testBuku.getJudulBuku()).isEqualTo(DEFAULT_JUDUL_BUKU);
        assertThat(testBuku.getKategori()).isEqualTo(DEFAULT_KATEGORI);
    }

    @Test
    public void createBukuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bukuRepository.findAll().size();

        // Create the Buku with an existing ID
        buku.setId("existing_id");
        BukuDTO bukuDTO = bukuMapper.toDto(buku);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBukuMockMvc.perform(post("/api/bukus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bukuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Buku in the database
        List<Buku> bukuList = bukuRepository.findAll();
        assertThat(bukuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBukus() throws Exception {
        // Initialize the database
        bukuRepository.save(buku);

        // Get all the bukuList
        restBukuMockMvc.perform(get("/api/bukus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buku.getId())))
            .andExpect(jsonPath("$.[*].judulBuku").value(hasItem(DEFAULT_JUDUL_BUKU.toString())))
            .andExpect(jsonPath("$.[*].kategori").value(hasItem(DEFAULT_KATEGORI.toString())));
    }
    
    @Test
    public void getBuku() throws Exception {
        // Initialize the database
        bukuRepository.save(buku);

        // Get the buku
        restBukuMockMvc.perform(get("/api/bukus/{id}", buku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buku.getId()))
            .andExpect(jsonPath("$.judulBuku").value(DEFAULT_JUDUL_BUKU.toString()))
            .andExpect(jsonPath("$.kategori").value(DEFAULT_KATEGORI.toString()));
    }

    @Test
    public void getNonExistingBuku() throws Exception {
        // Get the buku
        restBukuMockMvc.perform(get("/api/bukus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBuku() throws Exception {
        // Initialize the database
        bukuRepository.save(buku);

        int databaseSizeBeforeUpdate = bukuRepository.findAll().size();

        // Update the buku
        Buku updatedBuku = bukuRepository.findById(buku.getId()).get();
        updatedBuku
            .judulBuku(UPDATED_JUDUL_BUKU)
            .kategori(UPDATED_KATEGORI);
        BukuDTO bukuDTO = bukuMapper.toDto(updatedBuku);

        restBukuMockMvc.perform(put("/api/bukus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bukuDTO)))
            .andExpect(status().isOk());

        // Validate the Buku in the database
        List<Buku> bukuList = bukuRepository.findAll();
        assertThat(bukuList).hasSize(databaseSizeBeforeUpdate);
        Buku testBuku = bukuList.get(bukuList.size() - 1);
        assertThat(testBuku.getJudulBuku()).isEqualTo(UPDATED_JUDUL_BUKU);
        assertThat(testBuku.getKategori()).isEqualTo(UPDATED_KATEGORI);
    }

    @Test
    public void updateNonExistingBuku() throws Exception {
        int databaseSizeBeforeUpdate = bukuRepository.findAll().size();

        // Create the Buku
        BukuDTO bukuDTO = bukuMapper.toDto(buku);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBukuMockMvc.perform(put("/api/bukus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bukuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Buku in the database
        List<Buku> bukuList = bukuRepository.findAll();
        assertThat(bukuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBuku() throws Exception {
        // Initialize the database
        bukuRepository.save(buku);

        int databaseSizeBeforeDelete = bukuRepository.findAll().size();

        // Delete the buku
        restBukuMockMvc.perform(delete("/api/bukus/{id}", buku.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Buku> bukuList = bukuRepository.findAll();
        assertThat(bukuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Buku.class);
        Buku buku1 = new Buku();
        buku1.setId("id1");
        Buku buku2 = new Buku();
        buku2.setId(buku1.getId());
        assertThat(buku1).isEqualTo(buku2);
        buku2.setId("id2");
        assertThat(buku1).isNotEqualTo(buku2);
        buku1.setId(null);
        assertThat(buku1).isNotEqualTo(buku2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BukuDTO.class);
        BukuDTO bukuDTO1 = new BukuDTO();
        bukuDTO1.setId("id1");
        BukuDTO bukuDTO2 = new BukuDTO();
        assertThat(bukuDTO1).isNotEqualTo(bukuDTO2);
        bukuDTO2.setId(bukuDTO1.getId());
        assertThat(bukuDTO1).isEqualTo(bukuDTO2);
        bukuDTO2.setId("id2");
        assertThat(bukuDTO1).isNotEqualTo(bukuDTO2);
        bukuDTO1.setId(null);
        assertThat(bukuDTO1).isNotEqualTo(bukuDTO2);
    }
}
