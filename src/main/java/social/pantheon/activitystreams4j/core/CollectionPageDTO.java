package social.pantheon.activitystreams4j.core;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#CollectionPage")
public class CollectionPageDTO extends CollectionDTO {

    /**
     * In a paged Collection, indicates the next page of items.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#next")
    Object next;

    /**
     * In a paged Collection, identifies the previous page of items.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#prev")
    Object prev;

    /**
     * Identifies the Collection to which a CollectionPage objects items belong.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#partOf")
    Object partOf;
}
