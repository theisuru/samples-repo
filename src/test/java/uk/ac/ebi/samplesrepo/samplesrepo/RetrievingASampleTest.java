package uk.ac.ebi.samplesrepo.samplesrepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SampleController.class)
public class RetrievingASampleTest {

    private static final String EXISTING_SAMPLE_ACCESSION = "123";
    private static final String NON_EXISTING_SAMPLE_ACCESSION = "666";
    public static final String TEST_SAMPLE_NAME = "TEST Sample";

    @Autowired MockMvc mockMvc;

    @MockBean private SampleRepository sampleRepository;

    @BeforeEach
    public void setup() {
        Map<String, String> sampleProperties = new HashMap<>();
        sampleProperties.put("type", "cell suspension");
        sampleProperties.put("organism", "Homo sapiens");
        Sample existingSample = new Sample(EXISTING_SAMPLE_ACCESSION, TEST_SAMPLE_NAME, sampleProperties);
        given(sampleRepository.findByAccession(EXISTING_SAMPLE_ACCESSION)).willReturn(
            existingSample
        );
    }

    @Test
    public void test_when_query_not_existing_sample_returns_not_found() throws Exception {
        mockMvc.perform(get(String.format("/samples/%s", NON_EXISTING_SAMPLE_ACCESSION)).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void test_when_sample_exists_returns_the_sample() throws Exception {
        mockMvc.perform(get(String.format("/samples/%s", EXISTING_SAMPLE_ACCESSION)).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accession", is(EXISTING_SAMPLE_ACCESSION)))
                .andExpect(jsonPath("$.name", is(TEST_SAMPLE_NAME)))
                .andExpect(jsonPath("$.properties.type", is("cell suspension")))
                .andExpect(jsonPath("$.properties.organism", is("Homo sapiens")))
                .andReturn();
    }
}
