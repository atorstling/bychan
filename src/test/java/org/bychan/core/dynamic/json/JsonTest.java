package org.bychan.core.dynamic.json;

import org.bychan.core.basic.*;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.json.nodes.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonTest {

    @Test
    public void simpleStringLiteral() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.stringLiteral(lb))
                .completeLanguage();
        JsonNode ast = l.getLexParser().parse("\"hello\"");
        assertEquals(new StringLiteralNode("hello"), ast);
    }

    @Test
    public void stringWithQuoteEscape() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.stringLiteral(lb))
                .completeLanguage();
        JsonNode ast = l.getLexParser().parse("\"\\\"hello\"");
        assertEquals(new StringLiteralNode("\\\"hello"), ast);
    }

    @Test
    public void stringWithInvalidEscape() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.stringLiteral(lb))
                .completeLanguage();
        ParseResult<JsonNode> pr = l.getLexParser().tryParse("\"\\phello\"");
        assertEquals(ParsingFailedInformation.forFailedLexing(new LexingFailedInformation("No matching rule for char-range starting at 0: '\"\\phello\"'", new LexingPosition(0, "\"\\phello\""))), pr.getErrorMessage());
    }


    @Test
    public void positiveInteger() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.numberLiteral(lb))
                .completeLanguage();
        JsonNode ast = l.getLexParser().parse("1");
        assertEquals(new NumberLiteralNode(1), ast);
    }

    @Test
    public void negativeExponent() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.numberLiteral(lb))
                .completeLanguage();
        JsonNode ast = l.getLexParser().parse("-0.5e-5");
        assertEquals(new NumberLiteralNode(-0.5e-5f), ast);
    }

    @Test
    public void leadingZeroesForbidden() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.numberLiteral(lb))
                .completeLanguage();
        try {
            l.getLexParser().parse("01.5");
            fail("Expected exception");
        } catch (ParsingFailedException e) {
            ParsingFailedInformation actual = e.getParsingFailedInformation();
            assertEquals("Parsing failed: 'Current token does not support infix parsing' @  position 1:2 (index 1), current lexeme is number_literal(1.5) and remaining lexemes are [END]", actual.toString());
        }
    }

    @Test
    public void bool() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.boolLiteral(lb))
                .completeLanguage();
        JsonNode ast = l.getLexParser().parse("true");
        assertEquals(new BooleanLiteralNode(true), ast);
    }

    @Test
    public void nul() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        Language<JsonNode> l = lb.addToken(JsonLangBuilder.nullLiteral(lb))
                .completeLanguage();
        JsonNode ast = l.getLexParser().parse("null");
        assertEquals(NullLiteral.get(), ast);
    }

    @Test
    public void emptyArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse("[]");
        assertEquals(new ArrayNode(Collections.emptyList()), ast);
    }

    @Test
    public void singleElementArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse("[3]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3))), ast);
    }

    @Test
    public void multipleElementArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse("[3,2,4]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3), new NumberLiteralNode(2), new NumberLiteralNode(4))), ast);
    }

    @Test
    public void emptyObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse("{}");
        assertEquals(new ObjectNode(Collections.emptyMap()), ast);
    }

    @Test
    public void simpleObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse("{\"a\":3}");
        LinkedHashMap<StringLiteralNode, JsonNode> expected = new LinkedHashMap<>();
        expected.put(new StringLiteralNode("a"), new NumberLiteralNode(3));
        assertEquals(new ObjectNode(expected), ast);
    }

    @Test
    public void nestedObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse("{\"a\":{\"b\":3}}");
        LinkedHashMap<StringLiteralNode, JsonNode> inner = new LinkedHashMap<>();
        inner.put(new StringLiteralNode("b"), new NumberLiteralNode(3));
        LinkedHashMap<StringLiteralNode, JsonNode> outer = new LinkedHashMap<>();
        outer.put(new StringLiteralNode("a"), new ObjectNode(inner));
        assertEquals(new ObjectNode(outer), ast);
    }

    @Test
    public void whitespace() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getLexParser().parse(" { \"a\" :  3 }");
        LinkedHashMap<StringLiteralNode, JsonNode> outer = new LinkedHashMap<>();
        outer.put(new StringLiteralNode("a"), new NumberLiteralNode(3));
        assertEquals(new ObjectNode(outer), ast);
    }

    @Test
    public void document() {
        Language<JsonNode> l = makeJson();
        String data = "{\n" +
                "    \"glossary\": {\n" +
                "        \"title\": \"example glossary\",\n" +
                "\t\t\"GlossDiv\": {\n" +
                "            \"title\": \"S\",\n" +
                "\t\t\t\"GlossList\": {\n" +
                "                \"GlossEntry\": {\n" +
                "                    \"ID\": \"SGML\",\n" +
                "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
                "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
                "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
                "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
                "\t\t\t\t\t\"GlossDef\": {\n" +
                "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
                "                    },\n" +
                "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        JsonNode ast = l.getLexParser().parse(data);
        String output = ast.prettyPrint(0);
        assertEquals("{\n" +
                "\"glossary\": {\n" +
                "  \"title\": \"example glossary\",\n" +
                "  \"GlossDiv\": {\n" +
                "    \"title\": \"S\",\n" +
                "    \"GlossList\": {\n" +
                "      \"GlossEntry\": {\n" +
                "        \"ID\": \"SGML\",\n" +
                "        \"SortAs\": \"SGML\",\n" +
                "        \"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
                "        \"Acronym\": \"SGML\",\n" +
                "        \"Abbrev\": \"ISO 8879:1986\",\n" +
                "        \"GlossDef\": {\n" +
                "          \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                "          \"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
                "          },\n" +
                "        \"GlossSee\": \"markup\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}", output);
    }

    private Language<JsonNode> makeJson() {
        return new JsonLangBuilder().build();
    }


}
