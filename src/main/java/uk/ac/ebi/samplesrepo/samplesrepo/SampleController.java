package uk.ac.ebi.samplesrepo.samplesrepo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private final SampleRepository sampleRepository;

    public SampleController(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @GetMapping("/samples/{accessionId}")
    ResponseEntity<?> retrieveSampleByAccessionId(@PathVariable("accessionId") String accessionId) {
        Sample sample = sampleRepository.findByAccession(accessionId);

        if (sample == null)
            return ResponseEntity
                    .notFound()
                    .build();

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sample);
    }


}
