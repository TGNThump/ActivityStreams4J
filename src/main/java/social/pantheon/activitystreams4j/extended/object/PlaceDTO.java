package social.pantheon.activitystreams4j.extended.object;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.ObjectDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Place")
public class PlaceDTO extends ObjectDTO {

    /**
     * Indicates the accuracy of position coordinates on a Place objects. Expressed in properties of percentage. e.g. "94.0" means "94.0% accurate".
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#accuracy")
    Float accuracy;

    /**
     * The latitude of a place
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#latitude")
    Float latitude;

    /**
     * The longitude of a place
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#longitude")
    Float longitude;

    /**
     * The radius from the given latitude and longitude for a Place.
     * The units is expressed by the units property.
     * If units is not specified, the default is assumed to be "m" indicating "meters".
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#radius")
    Float radius;

    /**
     * Specifies the measurement units for the radius and altitude properties on a Place object.
     * If not specified, the default is assumed to be "m" for "meters".
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#units")
    String units = "m";
}
