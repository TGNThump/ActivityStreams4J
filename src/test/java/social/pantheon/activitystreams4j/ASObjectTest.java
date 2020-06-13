package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
public class ASObjectTest extends AbstractSerdesTest{

    @TestFactory
    Stream<DynamicTest> w3cActivityStreamsSuccessTests() throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(new URL("https://api.github.com/repos/w3c/activitystreams/contents/test"));

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), Spliterator.ORDERED), true)
            .filter(file -> file.get("name").textValue().endsWith(".json"))
            .map(file -> {
                try {
                    String name = file.get("name").textValue();
                    String url = file.get("download_url").textValue();

                    return DynamicTest.dynamicTest(name, new URI(url), () -> {
                        log.info("Testing {} ({})", name, url);
                        mapper.readerFor(ASObject.class).readValue(new URL(url));
                    });
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @TestFactory
    Stream<DynamicTest> w3cActivityStreamsFailTests() throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(new URL("https://api.github.com/repos/w3c/activitystreams/contents/test/fail"));

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), Spliterator.ORDERED), true)
                .filter(file -> file.get("name").textValue().endsWith(".json"))
                .map(file -> {
                    try {
                        String name = file.get("name").textValue();
                        String url = file.get("download_url").textValue();


                        return DynamicTest.dynamicTest(name, new URI(url), () -> {
                            log.info("Testing {} ({})", name, url);
                            Exception ex = assertThrows(Exception.class, () -> mapper.readerFor(ASObject.class).readValue(new URL(url)));
                            log.error("Expected exception", ex);
                        });
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
