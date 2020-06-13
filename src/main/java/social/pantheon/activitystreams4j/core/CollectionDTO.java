package social.pantheon.activitystreams4j.core;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Collection")
public class CollectionDTO extends ObjectDTO {

    /**
     * In a paged Collection, indicates the page that contains the most recently updated member items.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#current")
    Object current;

    /**
     * In a paged Collection, indicates the furthest preceeding page of items in the collection.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#first")
    Object first;

    /**
     * In a paged Collection, indicates the furthest proceeding page of the collection.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#last")
    Object last;

    /**
     * Identifies the items contained in a collection. The items might be ordered or unordered.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#items")
    List<Object> items;

    /**
     * A non-negative integer specifying the total number of objects contained by the logical view of the collection.
     * This number might not reflect the actual number of items serialized within the Collection object instance.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#totalItems")
    Integer totalItems;
}
