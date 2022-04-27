package uk.ac.ebi.samplesrepo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class SampleStoreControllerTest {

    public static final String TEST_ACCESSION = "test_accession";
    public static final String TEST_NAME = "test_name";
    public static final String TEST_ATTR = "test_attr";
    public static final String TEST_VALUE = "test_value";
    @Autowired
    private SampleStoreController sampleStoreController;

    @MockBean
    private SampleRepository sampleRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testControllerIsLoading() {
        Assertions.assertThat(sampleStoreController).isNotNull();
    }

    @Test
    public void testSampleIsStoredAndReturnCreated() throws Exception {
        given(sampleRepository.save(any()))
                .willReturn(getTestSample());

        mockMvc.perform(MockMvcRequestBuilders.post("/samples")
                        .content(objectMapper.writeValueAsString(getTestSample()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenQueryNotExisingSampleThenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/samples/{id}", TEST_ACCESSION).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void whenQueryExistingSampleThenReturnCorrectResponse() throws Exception {
        given(sampleRepository.findByAccession(TEST_ACCESSION))
                .willReturn(getTestSample());

        mockMvc.perform(get("/samples/{id}", TEST_ACCESSION).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accession", is(TEST_ACCESSION)))
                .andExpect(jsonPath("$.name", is(TEST_NAME)))
                .andExpect(jsonPath(String.format("$.properties.%s", TEST_ATTR), is(TEST_VALUE)))
                .andReturn();
    }

    private Sample getTestSample() {
        return new Sample(TEST_ACCESSION, TEST_NAME, Map.of(TEST_ATTR, TEST_VALUE));
    }

}
