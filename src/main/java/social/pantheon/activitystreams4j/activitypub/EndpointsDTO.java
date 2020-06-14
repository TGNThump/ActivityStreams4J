package social.pantheon.activitystreams4j.activitypub;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import lombok.Data;

import java.net.URI;

@Data
@OWLClass(iri = "https://www.w3.org/ns/activitystreams#endpoints")
public class EndpointsDTO {

    /**
     * Provides the globally unique identifier for an Object or Link.
     */
    @Id
    URI id;

    /**
     * Endpoint URI so this actor's clients may access remote ActivityStreams objects which require authentication to access.
     * To use this endpoint, the client posts an x-www-form-urlencoded id parameter with the value being the id of the requested ActivityStreams object.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#proxyUrl")
    URI proxyUrl;

    /**
     *  If OAuth 2.0 bearer tokens are being used for authenticating client to server interactions,
     *  this endpoint specifies a URI at which a browser-authenticated user may obtain a new authorization grant.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#oauthAuthorizationEndpoint")
    URI oauthAuthorizationEndpoint;

    /**
     * If OAuth 2.0 bearer tokens are being used for authenticating client to server interactions,
     * this endpoint specifies a URI at which a client may acquire an access token.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#oauthTokenEndpoint")
    URI oauthTokenEndpoint;

    /**
     * If Linked Data Signatures and HTTP Signatures are being used for authentication and authorization,
     * this endpoint specifies a URI at which browser-authenticated users may authorize a client's public key for client to server interactions.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#provideClientKey")
    URI provideClientKey;

    /**
     * If Linked Data Signatures and HTTP Signatures are being used for authentication and authorization,
     * this endpoint specifies a URI at which a client key may be signed by the actor's key for a time window to act on behalf of the actor in interacting with foreign servers.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#signClientKey")
    URI signClientKey;

    /**
     * An optional endpoint used for wide delivery of publicly addressed activities and activities sent to followers.
     * sharedInbox endpoints SHOULD also be publicly readable OrderedCollection objects containing objects addressed to the Public special collection.
     * Reading from the sharedInbox endpoint MUST NOT present objects which are not addressed to the Public endpoint.
     */
    @OWLDataProperty(iri = "https://www.w3.org/ns/activitystreams#sharedInbox")
    URI sharedInbox;
}
