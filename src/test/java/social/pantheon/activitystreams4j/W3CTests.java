package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jsonldjava.core.JsonLdProcessor;
import org.apache.logging.log4j.core.pattern.AnsiEscape;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import social.pantheon.activitystreams4j.core.LinkDTO;
import social.pantheon.activitystreams4j.core.ObjectDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

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

    private void log(Object... objects){
        StringBuilder stringBuilder = new StringBuilder();

        for (Object object : objects) {
            if (object instanceof AnsiEscape) {
                stringBuilder.append(createSequence((AnsiEscape) object));
            } else if (object instanceof ObjectDTO || object instanceof LinkDTO){
                stringBuilder.append(prettyPrintDTO(object));
            } else {
                stringBuilder.append(object);
            }
        }

        System.out.println(stringBuilder.toString());
    }

    private String createSequence(AnsiEscape object) {
        return AnsiEscape.createSequence(object.name());
    }

    private String prettyPrintDTO(Object result) {
        StringBuilder prettyResult = new StringBuilder();
        String newLineIndent = System.lineSeparator();
        char[] charArray = result.toString().toCharArray();

        prettyResult.append(createSequence(AnsiEscape.YELLOW));
        outer: for (int i = 0; i < charArray.length; i++) {
            char current = charArray[i];
            char lookAhead = i+1 < charArray.length ? charArray[i+1] : ' ';
            switch (current) {
                case '(':
                case '[':
                    prettyResult.append(createSequence(AnsiEscape.DEFAULT));
                    newLineIndent += "   ";
                    prettyResult.append(current);
                    prettyResult.append(newLineIndent);

                    inner: for (int j = i+1; j < charArray.length; j++){
                        switch (charArray[j]){
                            case '[':
                            case '=':
                                break inner;
                            case '(':
                                prettyResult.append(createSequence(AnsiEscape.YELLOW));
                                break inner;
                        }
                    }

                    break;
                case ')':
                case ']':
                    newLineIndent = newLineIndent.substring(0, newLineIndent.length() - 1);
                    prettyResult.append(newLineIndent);
                    prettyResult.append(current);
                    break;
                case ',':
                    prettyResult.append(current);

                    for (int j = i+1; j < charArray.length; j++){
                        if (charArray[j] == '=') break;
                        if (charArray[j] == ',') continue outer;
                    }

                    prettyResult.append(newLineIndent);
                    if (lookAhead == ' ') i++;
                    break;
                case '=':
                    prettyResult.append(current);

                    inner: for (int j = i+1; j < charArray.length; j++){
                        switch (charArray[j]){
                            case '[':
                            case '=':
                                break inner;
                            case '(':
                                prettyResult.append(createSequence(AnsiEscape.YELLOW));
                                break inner;
                        }
                    }

                    break;
                default:
                    prettyResult.append(current);
                    break;
            }
        }
        return prettyResult.toString().replaceAll("\\s*[a-zA-Z]+=null,?", "");
    }

    private Stream<DynamicTest> testExamplesFromW3CTechnicalReport(String baseUrl, Map<String, String> fixes) throws IOException {
        Document document = Jsoup.connect(baseUrl).get();

        return document.select(".example").stream().map(example -> {
            Element parent = example.parent();
            String id = parent.id();
            String name = example.select(".example-title").text();
            String json = fixes.getOrDefault(name, example.select(".json").text());

            String url = example.baseUri() + "#" + id;

            try {
                return DynamicTest.dynamicTest(name + " (" + id + ")", new URI(url), () -> {
                    JsonNode tree = mapper.readTree(json);

                    String expanded = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(JsonLdProcessor.expand(mapper.readValue(json, Object.class)));

                    log(
                        AnsiEscape.YELLOW, "Testing ", name, " (", url, "):", AnsiEscape.DEFAULT, lineSeparator(), lineSeparator(),
                            AnsiEscape.YELLOW, "Input JSON-LD", AnsiEscape.DEFAULT, lineSeparator(),
                            tree.toPrettyString(), lineSeparator(),
                            AnsiEscape.YELLOW, "Expanded JSON-LD", AnsiEscape.DEFAULT, lineSeparator(),
                            expanded, lineSeparator()
                    );

                    Object result;
                    if (tree.has("type") && (tree.get("type").asText().equals("Link") || tree.get("type").asText().equals("Mention"))){
                         result = mapper.readerFor(LinkDTO.class).readValue(json);
                    } else {
                        result = mapper.readerFor(ObjectDTO.class).readValue(json);
                    }

                    log(
                            result, lineSeparator()
                    );

                    log(
                            AnsiEscape.YELLOW, "Result JSON-LD", AnsiEscape.DEFAULT, lineSeparator(),
                            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result)
                    );
                });
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
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
