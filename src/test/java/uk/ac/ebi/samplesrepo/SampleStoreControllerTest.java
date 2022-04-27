package uk.ac.ebi.samplesrepo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
public class SampleStoreControllerTest {

    @Autowired
    private SampleStoreController sampleStoreController;
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
        mockMvc.perform(MockMvcRequestBuilders.post("/sample")
                        .content(objectMapper.writeValueAsString(getTestSample()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private Sample getTestSample() {
        return new Sample("test_accession", "test_name", Map.of("test_attr", "test_value"));
    }

}
