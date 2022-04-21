package uk.ac.ebi.samplesrepo.samplesrepo;

import lombok.Data;

import java.util.Map;

@Data
public class Sample {

    private final String accession;
    private final String name;
    private final Map<String, String> properties;

}
