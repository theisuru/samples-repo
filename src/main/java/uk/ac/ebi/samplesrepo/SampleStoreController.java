package uk.ac.ebi.samplesrepo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class SampleStoreController {

    private final SampleRepository sampleRepository;

    public SampleStoreController(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @PostMapping("/samples")
    public ResponseEntity<Sample> saveSample(@RequestBody Sample sample) {
        Sample storedSample = sampleRepository.save(sample);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedSample.getAccession())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/samples/{accessionId}")
    public ResponseEntity<Sample> retrieveSampleByAccessionId(@PathVariable("accessionId") String accessionId) {
        Sample sample = sampleRepository.findByAccession(accessionId);

        if (sample == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sample);
    }
}
