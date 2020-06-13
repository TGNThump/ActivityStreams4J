package social.pantheon.activitystreams4j.extended.object;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.ObjectDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Profile")
public class ProfileDTO extends ObjectDTO {

    /**
     * On a Profile object, the describes property identifies the object described by the Profile.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#describes")
    Object describes;
}
