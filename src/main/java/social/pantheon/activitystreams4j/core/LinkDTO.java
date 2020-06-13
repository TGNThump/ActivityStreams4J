package social.pantheon.activitystreams4j.core;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;
import java.util.Set;

@Data
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Link")
public class LinkDTO {

    /**
     * Provides the globally unique identifier for an Object or Link.
     */
    @Id
    private URI id;

    /**
     * Identifies one or more entities to which this object is attributed.
     * The attributed entities might not be Actors.
     * For instance, an object might be attributed to the completion of another activity.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#attributedTo")
    Object attributedTo;

    /**
     * Identifies an entity that provides a preview of this object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#preview")
    Object preview;

    /**
     * A simple, human-readable, plain-text name for the object.
     * HTML markup MUST NOT be included.
     *
     * TODO: The name MAY be expressed using multiple language-tagged values.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#name")
    String name;

    /**
     * Specifies a hint as to the rendering height in device-independent pixels of the linked resource.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#height")
    Integer height;

    /**
     * The target resource pointed to by a Link.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#href")
    URI href;

    /**
     * Hints as to the language used by the target resource.
     * Value MUST be a [BCP47] Language-Tag.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#hreflang")
    String hreflang;

    /**
     * Identifies the MIME media type of the referenced resource.
     * If not specified, the content property is assumed to contain text/html content.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#mediaType")
    String mediaType = "text/html";

    /**
     * A link relation associated with a Link.
     * The value MUST conform to both the [HTML5] and [RFC5988] "link relation" definitions.
     *
     * In the [HTML5], any string not containing the "space" U+0020, "tab" (U+0009), "LF" (U+000A), "FF" (U+000C), "CR" (U+000D) or "," (U+002C) characters can be used as a valid link relation.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#rel")
    Set<String> rel;

    /**
     * Specifies a hint as to the rendering width in device-independent pixels of the linked resource.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#width")
    Integer width;
}
