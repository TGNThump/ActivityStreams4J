package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.cvut.kbss.jsonld.ConfigParam;
import cz.cvut.kbss.jsonld.jackson.JsonLdModule;
import org.junit.jupiter.api.BeforeEach;

import java.io.StringWriter;
import java.io.Writer;

public class AbstractSerdesTest {

    ObjectMapper mapper;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JsonLdModule()
            .configure(ConfigParam.SCAN_PACKAGE, "social.pantheon.activitystreams4j")
            .configure(ConfigParam.REQUIRE_ID, "false")
            .configure(ConfigParam.ASSUME_TARGET_TYPE, "true")
        );
        mapper.registerModule(new JavaTimeModule());
    }
}
