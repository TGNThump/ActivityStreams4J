package social.pantheon.activitystreams4j.extended.link;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.LinkDTO;
import social.pantheon.activitystreams4j.core.ObjectDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Mention")
public class MentionDTO extends LinkDTO {
}
