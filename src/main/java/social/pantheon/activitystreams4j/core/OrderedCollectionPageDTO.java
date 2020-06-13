package social.pantheon.activitystreams4j.core;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#OrderedCollectionPage")
public class OrderedCollectionPageDTO extends CollectionPageDTO {
    // TODO:  Inherits all properties from OrderedCollection and CollectionPage.

    /**
     * A non-negative integer value identifying the relative position within the logical view of a strictly ordered collection.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#startIndex")
    Integer startIndex;
}
