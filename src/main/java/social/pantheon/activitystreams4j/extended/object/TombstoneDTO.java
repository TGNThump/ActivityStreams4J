package social.pantheon.activitystreams4j.extended.object;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.ObjectDTO;

import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Tombstone")
public class TombstoneDTO extends ObjectDTO {

    /**
     * On a Tombstone object, the formerType property identifies the type of the object that was deleted.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#formerType")
    Object formerType;

    /**
     * On a Tombstone object, the deleted property is a timestamp for when the object was deleted.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#deleted")
    Temporal deleted;
}
