package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.kbss.jsonld.ConfigParam;
import cz.cvut.kbss.jsonld.jackson.JsonLdModule;
import org.junit.jupiter.api.BeforeEach;

import java.io.StringWriter;
import java.io.Writer;

public class AbstractSerdesTest {

    protected ObjectMapper mapper;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JsonLdModule()
            .configure(ConfigParam.SCAN_PACKAGE, "social.pantheon.activitystreams4j")
            .configure(ConfigParam.REQUIRE_ID, "true")
        );
    }

    protected String write(Object object) throws Exception {
        Writer writer = new StringWriter();
        mapper.writeValue(writer, object);
        return writer.toString();
    }

    protected <T> T read(String source, Class<T> targetType) throws Exception {
        return mapper.readValue(source, targetType);
    }

}
