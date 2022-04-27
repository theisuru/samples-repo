package uk.ac.ebi.samplesrepo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class SampleStoreController {

    private final SampleRepository sampleRepository;

    public SampleStoreController(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @PostMapping("/sample")
    public ResponseEntity<Sample> saveSample(@RequestBody Sample sample) {
        Sample storedSample = sampleRepository.save(sample);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedSample.getAccession())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
