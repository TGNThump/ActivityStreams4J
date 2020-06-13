package social.pantheon.activitystreams4j.extended.activity;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import social.pantheon.activitystreams4j.core.ActivityDTO;
import social.pantheon.activitystreams4j.core.IntransitiveActivityDTO;
import social.pantheon.activitystreams4j.core.ObjectDTO;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#Question")
public class QuestionDTO extends IntransitiveActivityDTO {

    /**
     * Identifies an exclusive option for a Question.
     * Use of oneOf implies that the Question can have only a single answer.
     * To indicate that a Question can have multiple answers, use anyOf.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#oneOf")
    List<Object> oneOf;

    /**
     * Identifies an inclusive option for a Question.
     * Use of anyOf implies that the Question can have multiple answers.
     * To indicate that a Question can have only one answer, use oneOf.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#anyOf")
    List<Object> anyOf;

    /**
     * Indicates that a question has been closed, and answers are no longer accepted.
     */
    @OWLObjectProperty(iri = "https://www.w3.org/ns/activitystreams#closed")
    Object closed;
}
