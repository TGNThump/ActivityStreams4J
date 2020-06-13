package social.pantheon.activitystreams4j.extended.object;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Page")
public class PageDTO extends DocumentDTO {
}
