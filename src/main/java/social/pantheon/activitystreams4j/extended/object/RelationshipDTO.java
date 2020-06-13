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
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Relationship")
public class RelationshipDTO extends ObjectDTO {

    /**
     * Describes the entity to which the subject is related.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#object")
    Object object;

    /**
     * Identifies one of the connected individuals.
     * For instance, for a Relationship object describing "John is related to Sally", subject would refer to John.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#subject")
    Object subject;

    /**
     * On a Relationship object, the relationship property identifies the kind of relationship that exists between subject and object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#relationship")
    Object relationship;
}
