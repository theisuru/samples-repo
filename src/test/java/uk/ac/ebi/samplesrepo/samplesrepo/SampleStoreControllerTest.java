package uk.ac.ebi.samplesrepo.samplesrepo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleStoreControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private SampleStoreController sampleStoreController;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testContextLoads() {
    assertThat(sampleStoreController).isNotNull();
  }

  @Test
  void testSaveSample() throws Exception {
    Sample testSample = getTestSample();
    mockMvc.perform(post("/sample/save").content(objectMapper.writeValueAsString(testSample))
                                        .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().string(Matchers.containsString(testSample.getAccession())));

  }

  private Sample getTestSample() {
    return new Sample("testAccession", "testName", Map.of("attribute_1", "value_1"));
  }
}
