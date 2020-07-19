package social.pantheon.activitystreams4j;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.shaded.com.google.common.collect.HashMultiset;
import com.github.jsonldjava.shaded.com.google.common.collect.Multiset;
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
import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

public class W3CTests extends AbstractSerdesTest{

    @TestFactory
    Stream<DynamicTest> W3CActivityStreamsVocabularyExamples() throws IOException {
        Map<String, String> fixes = new HashMap<>();

        // https://github.com/w3c/activitystreams/blob/master/ERRATA.md
        fixes.put("Example 150", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"type\": \"Place\",\"name\": \"San Francisco, CA\",\"longitude\": 122.4167,\"latitude\": 37.7833}");
        fixes.put("Example 80", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"summary\": \"A simple note\",\"type\": \"Note\",\"content\": \"A simple note\",\"icon\": [{\"type\": \"Image\",\"summary\": \"Note (16x16)\",\"url\": {\"type\": \"Link\",\"href\": \"http://example.org/note1.png\",\"width\": 16,\"height\": 16}},{\"type\": \"Image\",\"summary\": \"Note (32x32)\",\"url\": {\"type\": \"Link\",\"href\": \"http://example.org/note2.png\",\"width\": 32,\"height\": 32}}]}");
        fixes.put("Example 58", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"type\": \"Mention\",\"href\": \"http://example.org/joe\",\"name\": \"Joe\"}");
        // https://github.com/w3c/activitystreams/issues/439
        fixes.put("Example 79", "{\"@context\" : \"https://www.w3.org/ns/activitystreams\",\"summary\" : \"A simple note\",\"type\" : \"Note\",\"content\" : \"This is all there is.\",\"icon\" : {  \"type\" : \"Image\",  \"name\" : \"Note icon\",  \"url\" : \"http://example.org/note.png\"}\n}");
        // https://github.com/w3c/activitystreams/issues/448
        fixes.put("Example 157", "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\"name\": \"A thank-you note\",\"type\": \"Note\",\"content\": \"Thank you <a href='http://sally.example.org'>@sally</a><br/>for all your hard work!<br/><a href='http://example.org/tags/givingthanks'>#givingthanks</a>\",\"to\": {  \"name\": \"Sally\",  \"type\": \"Person\",  \"id\": \"http://sally.example.org\"},\"tag\": {  \"id\": \"http://example.org/tags/givingthanks\",  \"name\": \"#givingthanks\"}}");
        // https://github.com/w3c/activitystreams/issues/496
        fixes.put("Example 75", "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"summary\":\"Sally's blog posts\",\"type\":\"Collection\",\"totalItems\":3,\"current\":{\"type\":\"Link\",\"href\":\"http://example.org/collection\"},\"items\":[\"http://example.org/posts/1\",\"http://example.org/posts/2\",\"http://example.org/posts/3\"]}");
        fixes.put("Example 77", "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"summary\":\"Sally's blog posts\",\"type\":\"Collection\",\"totalItems\":3,\"first\":{\"type\":\"Link\",\"href\":\"http://example.org/collection?page=0\"}}");
        fixes.put("Example 87", "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"summary\":\"A collection\",\"type\":\"Collection\",\"totalItems\":5,\"last\":{\"type\":\"Link\",\"href\":\"http://example.org/collection?page=1\"}}");
        //https://github.com/w3c/activitystreams/issues/441
        fixes.put("Example 60", "{\"@context\":\"https://www.w3.org/ns/activitystreams\",\"type\":\"OrderedCollection\",\"totalItems\":3,\"name\":\"Vacation photos 2016\",\"orderedItems\":[{\"type\":\"Image\",\"id\":\"http://image.example/1\"},{\"type\":\"Tombstone\",\"formerType\":\"Image\",\"id\":\"http://image.example/2\",\"deleted\":\"2016-03-17T00:00:00Z\"},{\"type\":\"Image\",\"id\":\"http://image.example/3\"}]}");

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
        String string = result.toString();

        int superOffset = 0;
        Multiset<Integer> superIndentSizes = HashMultiset.create();

        prettyResult.append(createSequence(AnsiEscape.YELLOW));
        outer: for (int i = 0; i < string.length(); i++) {
            char current = string.charAt(i);
            switch (current) {
                case 's':
                    if (i + 6 < string.length() && string.subSequence(i, i+6).equals("super=")){
                        superOffset += 4;
                        superIndentSizes.add(newLineIndent.length() + superOffset);
                        while (string.charAt(i) != '(') i++;
                    } else {
                        prettyResult.append(current);
                    }

                    break;
                case '(':
                case '[':
                    prettyResult.append(createSequence(AnsiEscape.DEFAULT));
                    prettyResult.append(current);
                    newLineIndent += "    ";

                    prettyResult.append(newLineIndent);

                    // Add yellow colour for object arrays
                    inner: for (int j = i+1; j < string.length(); j++){
                        switch (string.charAt(j)){
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
                    if ( current == ']' || !superIndentSizes.remove(newLineIndent.length() + superOffset)) {
                        newLineIndent = newLineIndent.substring(0, Math.max(0, newLineIndent.length() - 4));
                    } else {
                        superOffset -= 4;
                        if (string.charAt(i+1) == ' ') i++;
                        break;
                    }
                    prettyResult.append(newLineIndent);
                    prettyResult.append(current);
                    prettyResult.append(createSequence(AnsiEscape.DEFAULT));
                    break;
                case ',':
                    for (int j = i+1; j < string.length(); j++){
                        if (string.charAt(j) == '=') break;
                        if (string.charAt(j) == ',') continue outer;
                    }

                    prettyResult.append(newLineIndent);

                    if (i+1 >= string.length() || string.charAt(i+1) == ' ') i++;

                    for (int j = i+1; j < string.length(); j++){
                        if (string.charAt(j) == '(') break;
                        if (string.charAt(j) == '[') continue outer;
                        if (string.charAt(j) == '=') continue outer;
                    }

                    prettyResult.append(createSequence(AnsiEscape.YELLOW));
                    break;
                default:
                    prettyResult.append(current);
                    break;
            }
        }
        return prettyResult.toString().replaceAll("\\s*[a-zA-Z]+=null", "");
    }

    private Stream<DynamicTest> testExamplesFromW3CTechnicalReport(String baseUrl, Map<String, String> fixes) throws IOException {
        Document document = Jsoup.connect(baseUrl).get();

        return document.select(".example").stream().map(example -> {
            Element parent = example.parent();
            String id = parent.id();
            String name = example.select(".example-title").text();
            String json = fixes.getOrDefault(name, example.select(".json").text());

            if (json.isEmpty()) return null;

            String url = example.baseUri() + "#" + id;

            try {
                return DynamicTest.dynamicTest(name + " (" + id + ")", new URI(url), () -> {
                    JsonNode tree = mapper.readTree(json);
                    log(
                        AnsiEscape.YELLOW, "Testing ", name, " (", url, "):", AnsiEscape.DEFAULT, lineSeparator(), lineSeparator(),
                            AnsiEscape.YELLOW, "Input JSON-LD", AnsiEscape.DEFAULT, lineSeparator(),
                            tree.toPrettyString(), lineSeparator(), lineSeparator()
                    );

                    String expanded = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(JsonLdProcessor.expand(mapper.readValue(json, Object.class)));
                    log(
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
        }).filter(Objects::nonNull);
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
