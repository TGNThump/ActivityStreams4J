package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import social.pantheon.activitystreams4j.core.LinkDTO;
import social.pantheon.activitystreams4j.core.ObjectDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Log4j2
public class W3CTests extends AbstractSerdesTest{

    @TestFactory
    Stream<DynamicTest> W3CActivityStreamsVocabularyExamples() throws IOException {
        // Fixes based on https://github.com/w3c/activitystreams/blob/master/ERRATA.md
        Map<String, String> fixes = new HashMap<>();

        fixes.put("Example 150", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"type\": \"Place\",\"name\": \"San Francisco, CA\",\"longitude\": 122.4167,\"latitude\": 37.7833}");
        fixes.put("Example 80", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"summary\": \"A simple note\",\"type\": \"Note\",\"content\": \"A simple note\",\"icon\": [{\"type\": \"Image\",\"summary\": \"Note (16x16)\",\"url\": {\"type\": \"Link\",\"href\": \"http://example.org/note1.png\",\"width\": 16,\"height\": 16}},{\"type\": \"Image\",\"summary\": \"Note (32x32)\",\"url\": {\"type\": \"Link\",\"href\": \"http://example.org/note2.png\",\"width\": 32,\"height\": 32}}]}");
        fixes.put("Example 58", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"type\": \"Mention\",\"href\": \"http://example.org/joe\",\"name\": \"Joe\"}");

        return testExamplesFromW3CTechnicalReport("https://www.w3.org/TR/activitystreams-vocabulary/", fixes);
    }

    @TestFactory
    Stream<DynamicTest> W3CActivityStreamsCoreExamples() throws IOException {
        return testExamplesFromW3CTechnicalReport("https://www.w3.org/TR/activitystreams-core/");
    }

    @TestFactory
    Stream<DynamicTest> W3CActivityPubExamples() throws IOException {
        return testExamplesFromW3CTechnicalReport("https://www.w3.org/TR/activitypub/");
    }

    private Stream<DynamicTest> testExamplesFromW3CTechnicalReport(String baseUrl) throws IOException {
        return testExamplesFromW3CTechnicalReport(baseUrl, new HashMap<>());
    }


    private Stream<DynamicTest> testExamplesFromW3CTechnicalReport(String baseUrl, Map<String, String> fixes) throws IOException {
        Document document = Jsoup.connect(baseUrl).get();

        return document.select(".example").stream().map(example -> {
            Element parent = example.parent();
            String id = parent.id();
            String name = example.select(".example-title").text();
            String json = fixes.getOrDefault(name, example.select(".json").text());

            String url = example.baseUri() + "#" + id;

            return DynamicTest.dynamicTest(name + " (" + id + ")", () -> {
                JsonNode tree = mapper.readTree(json);

                log.info("Testing \"{}\" ({})\n{}", name, url, tree.toPrettyString());

                if (tree.has("type") && (tree.get("type").asText().equals("Link") || tree.get("type").asText().equals("Mention"))){
                    Object result = mapper.readerFor(LinkDTO.class).readValue(json);
                    log.info(result);
                } else {
                    Object result = mapper.readerFor(ObjectDTO.class).readValue(json);
                    log.info(result);
                }
            });
        });
    }

//    @TestFactory
//    Stream<DynamicTest> w3cActivityStreamsSuccessTests() throws IOException {
//        JsonNode jsonNode = new ObjectMapper().readTree(new URL("https://api.github.com/repos/w3c/activitystreams/contents/test"));
//
//        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), Spliterator.ORDERED), true)
//            .filter(file -> file.get("name").textValue().endsWith(".json") && !file.get("name").textValue().equals("package.json"))
//            .sorted(Comparator.comparing(file -> file.get("name").textValue()))
//            .map(file -> {
//                try {
//                    String name = file.get("name").textValue();
//                    String downloadUrl = file.get("download_url").textValue();
//
//                    return DynamicTest.dynamicTest(name, new URI(downloadUrl), () -> {
//                        try(InputStream inputStream = new URL(downloadUrl).openStream()){
//                            String json = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
//                            log.info("Testing {}\n{}", name, mapper.readTree(json).toPrettyString());
//
//                            Object result = mapper.readerFor(ObjectDTO.class).readValue(json);
//                            log.info(result);
//                        }
//                    });
//                } catch (URISyntaxException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//    }
//
//    @TestFactory
//    Stream<DynamicTest> w3cActivityStreamsFailTests() throws IOException {
//        JsonNode jsonNode = new ObjectMapper().readTree(new URL("https://api.github.com/repos/w3c/activitystreams/contents/test/fail"));
//
//        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.elements(), Spliterator.ORDERED), true)
//                .filter(file -> file.get("name").textValue().endsWith(".json") && !file.get("name").textValue().equals("package.json"))
//                .sorted(Comparator.comparing(file -> file.get("name").textValue()))
//                .map(file -> {
//                    try {
//
//                        String name = file.get("name").textValue();
//                        String downloadUrl = file.get("download_url").textValue();
//
//                        return DynamicTest.dynamicTest(name, new URI(downloadUrl), () -> {
//                            try(InputStream inputStream = new URL(downloadUrl).openStream()){
//                                String json = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
//                                log.info("Testing {}\n{}", name, mapper.readTree(json).toPrettyString());
//
//                                Exception ex = assertThrows(Exception.class, () -> mapper.readerFor(ObjectDTO.class).readValue(json));
//                                log.error("Expected exception", ex);
//                            }
//                        });
//                    } catch (URISyntaxException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//    }
}
