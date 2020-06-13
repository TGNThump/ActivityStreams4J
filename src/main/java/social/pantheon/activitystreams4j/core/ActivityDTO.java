package social.pantheon.activitystreams4j.core;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Activity")
public class ActivityDTO extends ObjectDTO {

    /**
     * Describes one or more entities that either performed or are expected to perform the activity.
     * Any single activity can have multiple actors. The actor MAY be specified using an indirect Link.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#actor")
    Set<Object> actor;

    /**
     * Identifies one or more objects used (or to be used) in the completion of an Activity.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#instrument")
    Set<Object> instrument;

    /**
     * Describes an indirect object of the activity from which the activity is directed.
     * The precise meaning of the origin is the object of the English preposition "from".
     * For instance, in the activity "John moved an item to List B from List A", the origin of the activity is "List A".
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#origin")
    Set<Object> origin;

    /**
     * Describes the direct object of the activity.
     * For instance, in the activity "John added a movie to his wishlist", the object of the activity is the movie added.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#object")
    Set<Object> object;

    /**
     * Describes the result of the activity.
     * For instance, if a particular action results in the creation of a new resource, the result property can be used to describe that new resource.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#result")
    Set<Object> result;

    /**
     * Describes the indirect object, or target, of the activity.
     * The precise meaning of the target is largely dependent on the type of action being described but will often be the object of the English preposition "to".
     * For instance, in the activity "John added a movie to his wishlist", the target of the activity is John's wishlist. An activity can have more than one target.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#target")
    Set<Object> target;

}
