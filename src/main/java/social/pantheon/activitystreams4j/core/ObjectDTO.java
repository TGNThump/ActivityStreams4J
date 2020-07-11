package social.pantheon.activitystreams4j.core;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;

import javax.xml.datatype.Duration;
import java.net.URI;
import java.time.temporal.Temporal;
import java.util.Set;

@Data
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Object")
public class ObjectDTO {

    /**
     * Provides the globally unique identifier for an Object or Link.
     */
    @Id
    URI id;

    /**
     * Identifies a resource attached or related to an object that potentially requires special handling.
     * The intent is to provide a model that is at least semantically similar to attachments in email.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#attachment")
    Object attachment;

    /**
     * Identifies one or more entities to which this object is attributed.
     * The attributed entities might not be Actors.
     * For instance, an object might be attributed to the completion of another activity.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#attributedTo")
    Set<Object> attributedTo;

    /**
     * Identifies one or more entities that represent the total population of entities for which the object can considered to be relevant.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#audience")
    Set<Object> audience;

    /**
     * Identifies an entity considered to be part of the public primary audience of an Object
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#to")
    Set<Object> to;

    /**
     * Identifies one or more Objects that are part of the private secondary audience of this Object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#bcc")
    Set<Object> bcc;

    /**
     * Identifies an Object that is part of the private primary audience of this Object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#bto")
    Set<Object> bto;

    /**
     * Identifies an Object that is part of the public secondary audience of this Object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#cc")
    Set<Object> cc;

    /**
     * Identifies the context within which the object exists or an activity was performed.
     *
     * The notion of "context" used is intentionally vague.
     * The intended function is to serve as a means of grouping objects and activities that share a common originating context or purpose.
     * An example could be all activities relating to a common project or event.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#context")
    Object context;

    /**
     * Identifies the entity (e.g. an application) that generated the object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#generator")
    Object generator;

    /**
     * Indicates an entity that describes an icon for this object. The image should have an aspect ratio of one (horizontal) to one (vertical) and should be suitable for presentation at a small size.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#icon")
    Set<Object> icon;

    /**
     * Indicates an entity that describes an image for this object. Unlike the icon property, there are no aspect ratio or display size limitations assumed.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#image")
    Set<Object> image;

    /**
     * Indicates one or more entities for which this object is considered a response.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#inReplyTo")
    Object inReplyTo;

    /**
     * Indicates one or more physical or logical locations associated with the object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#location")
    Set<Object> location;

    /**
     * Identifies an entity that provides a preview of this object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#preview")
    Object preview;

    /**
     * Identifies a Collection containing objects considered to be responses to this object.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#replies")
    CollectionDTO replies;

    /**
     * One or more "tags" that have been associated with an objects.
     * A tag can be any kind of Object.
     * The key difference between attachment and tag is that the former implies association by inclusion, while the latter implies associated by reference.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#tag")
    Set<Object> tag;

    /**
     * Identifies one or more links to representations of the object
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#url")
    Set<Object> url;

    /**
     * Indicates the altitude of a place. The measurement units is indicated using the units property. If units is not specified, the default is assumed to be "m" indicating meters.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#altitude")
    Float altitude;

    /**
     * The content or textual representation of the Object encoded as a JSON string.
     * By default, the value of content is HTML.
     * The mediaType property can be used in the object to indicate a different content type.
     *
     * TODO: The content MAY be expressed using multiple language-tagged values.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#content")
    String content;

    /**
     * A simple, human-readable, plain-text name for the object.
     * HTML markup MUST NOT be included.
     *
     * TODO: The name MAY be expressed using multiple language-tagged values.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#name")
    String name;

    /**
     * When the object describes a time-bound resource, such as an audio or video, a meeting, etc, the duration property indicates the object's approximate duration.
     *
     * The value MUST be expressed as an xsd:duration as defined by [ xmlschema11-2], section 3.3.6 (e.g. a period of 5 seconds is represented as "PT5S").
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#duration")
    Duration duration;

    /**
     * Identifies the MIME media type of the value of the content property.
     * If not specified, the content property is assumed to contain text/html content.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#mediaType")
    String mediaType = "text/html";

    /**
     * The date and time describing the actual or expected ending time of the object.
     * When used with an Activity object, for instance, the endTime property specifies the moment the activity concluded or is expected to conclude.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#endTime")
    Temporal endTime;

    /**
     * The date and time at which the object was published
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#published")
    Temporal published;

    /**
     * The date and time describing the actual or expected starting time of the object.
     * When used with an Activity object, for instance, the startTime property specifies the moment the activity began or is scheduled to begin.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#startTime")
    Temporal startTime;

    /**
     * A natural language summarization of the object encoded as HTML.
     *
     * TODO: Multiple language tagged summaries MAY be provided.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#summary")
    String summary;

    /**
     * The date and time at which the object was updated
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#updated")
    Temporal updated;

    /**
     * The source property is intended to convey some sort of source from which the content markup was derived, as a form of provenance, or to support future editing by clients.
     * In general, clients do the conversion from source to content, not the other way around.
     *
     * The value of source is itself an object which uses its own content and mediaType fields to supply source information.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#source")
    Object source;

    /**
     * This is a list of all Announce activities with this object as the object property, added as a side effect.
     *
     * The shares collection MUST be either an OrderedCollection or a Collection and MAY be filtered on privileges of an authenticated user or as appropriate when no authentication is given.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#shares")
    Object shares;

    /**
     * This is a list of all Like activities with this object as the object property, added as a side effect.
     *
     * The likes collection MUST be either an OrderedCollection or a Collection and MAY be filtered on privileges of an authenticated user or as appropriate when no authentication is given.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#likes")
    Object likes;
}
