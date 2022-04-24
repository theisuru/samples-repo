package uk.ac.ebi.samplesrepo.samplesrepo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sample {

  @Id
  private String accession;
  private String name;
  @ElementCollection
  private Map<String, String> properties;

}
