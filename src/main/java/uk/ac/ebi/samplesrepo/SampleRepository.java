package uk.ac.ebi.samplesrepo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, String> {
    Sample findByAccession(String accession);
}
