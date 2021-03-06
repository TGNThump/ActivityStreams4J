package social.pantheon.activitystreams4j.extended.activity;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.ActivityDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#TentativeAccept")
public class TentativeAcceptDTO extends ActivityDTO {
}
