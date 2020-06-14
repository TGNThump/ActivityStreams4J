package social.pantheon.activitystreams4j.activitypub;

import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.ObjectDTO;

import java.net.URI;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class ActorDTO extends ObjectDTO {

    /**
     * A reference to an OrderedCollection comprised of all the messages received by the actor.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#inbox")
    Object inbox;

    /**
     * An OrderedCollection comprised of all the messages produced by the actor.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#outbox")
    Object outbox;

    /**
     * A link to an collection of the actors that this actor is following.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#following")
    Object following;

    /**
     * A link to an collection of the actors that follow this actor.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#followers")
    Object followers;

    /**
     * A link to an collection of objects this actor has liked.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#liked")
    Object liked;

    /**
     * A list of supplementary Collections which may be of interest.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#streams")
    Object streams;

    /**
     * A short username which may be used to refer to the actor, with no uniqueness guarantees.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#preferredUsername")
    String preferredUsername;

    /**
     * A json object which maps additional (typically server/domain-wide) endpoints which may be useful either for this actor or someone referencing this actor.
     * This mapping may be nested inside the actor document as the value or may be a link to a JSON-LD document with these properties.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#endpoints")
    Object endpoints;
}
