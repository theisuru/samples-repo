package uk.ac.ebi.samplesrepo.samplesrepo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleStoreController {
  private final SampleRepository sampleRepository;

  public SampleStoreController(SampleRepository sampleRepository) {
    this.sampleRepository = sampleRepository;
  }

  @PostMapping("/sample/save")
  public ResponseEntity<Sample> saveSample(@RequestBody Sample sample) {
    Sample savedSample = sampleRepository.save(sample);

    return ResponseEntity.ok(savedSample);
  }
}
