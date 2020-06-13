package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import social.pantheon.activitystreams4j.core.ObjectDTO;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
public class W3CTests extends AbstractSerdesTest{

    @TestFactory
    Stream<DynamicTest> w3cActivityStreamsSuccessTests() throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(new URL("https://api.github.com/repos/w3c/activitystreams/contents/test"));

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), Spliterator.ORDERED), true)
            .filter(file -> file.get("name").textValue().endsWith(".json") && !file.get("name").textValue().equals("package.json"))
            .sorted(Comparator.comparing(file -> file.get("name").textValue()))
            .map(file -> {
                try {
                    String name = file.get("name").textValue();
                    String downloadUrl = file.get("download_url").textValue();

                    return DynamicTest.dynamicTest(name, new URI(downloadUrl), () -> {
                        try(InputStream inputStream = new URL(downloadUrl).openStream()){
                            String json = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                            log.info("Testing {}\n{}", name, mapper.readTree(json).toPrettyString());

                            Object result = mapper.readerFor(ObjectDTO.class).readValue(json);
                            log.info(result);
                        }
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
                .filter(file -> file.get("name").textValue().endsWith(".json") && !file.get("name").textValue().equals("package.json"))
                .sorted(Comparator.comparing(file -> file.get("name").textValue()))
                .map(file -> {
                    try {

                        String name = file.get("name").textValue();
                        String downloadUrl = file.get("download_url").textValue();

                        return DynamicTest.dynamicTest(name, new URI(downloadUrl), () -> {
                            try(InputStream inputStream = new URL(downloadUrl).openStream()){
                                String json = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                                log.info("Testing {}\n{}", name, mapper.readTree(json).toPrettyString());

                                Exception ex = assertThrows(Exception.class, () -> mapper.readerFor(ObjectDTO.class).readValue(json));
                                log.error("Expected exception", ex);
                            }
                        });
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
