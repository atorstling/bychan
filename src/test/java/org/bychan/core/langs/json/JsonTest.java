package org.bychan.core.langs.json;

import org.bychan.core.basic.*;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.langs.json.nodes.*;
import org.bychan.core.utils.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class JsonTest {

    @Test
    public void simpleStringLiteral() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("\"hello\"");
        assertEquals(new StringLiteralNode("hello"), ast);
    }

    @Test
    public void stringWithQuoteEscape() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("\"\\\"hello\"");
        assertEquals(new StringLiteralNode("\\\"hello"), ast);
    }

    @Test
    public void stringWithQuotedBackslash() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("\"\\\\\"");
        assertEquals(new StringLiteralNode("\\\\"), ast);
    }

    @Test
    public void stringWithUnicodeLiteral() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("\"\\uabCD\"");
        assertEquals(new StringLiteralNode("\\uabCD"), ast);
    }

    @Test
    public void stringWithCrazyText() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        String crazy = "6U閆崬밺뀫颒myj츥휘\uECED:$薈mY햚#\uF6A2rz飏+玭V㭢뾿愴Y\uEC11ꖚX亥ᮉ푊\\u0006垡㐭룝\\\"厓ᔧḅ^Sqpv媫\\\"⤽걒\\\"˽Ἆ?ꇆ䬔未tv{DV鯀Tἆl凸g\\\\㈭ĭ즿UH㽤";
        JsonNode ast = l.newLexParser().parse("\"" + crazy + "\"");
        assertEquals(new StringLiteralNode(crazy), ast);
    }

    @Test
    public void stringWithAnotherCrazyText() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        String crazy = "e\uE7A1浱u\uEFE3p\u007F蔽Cr\u0DE0JK軵xCʨ<뜡癙Y獩ｹ齈X/螗唻\uEB3F?<蘡+뷄㩤쳖3偑犾&\\\\첊xz坍崦ݻ\uE2D1鍴\\\"嵥B3㰃詤豺嚼aqJ⑆∥韼@\\u000b㢊\\u0015L臯.샥";
        JsonNode ast = l.newLexParser().parse("\"" + crazy + "\"");
        assertEquals(new StringLiteralNode(crazy), ast);
    }

    @Test
    public void stringWithInvalidEscape() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.stringLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        String indata = "\"\\phello\"";
        ParseResult<JsonNode> pr = l.newLexParser().tryParse(indata);
        //noinspection unchecked
        Lexeme<JsonNode> mockLexeme = mock(Lexeme.class);
        LexingPosition<JsonNode> lexingPosition = new LexingPosition<>(StringUtils.getTextPosition(indata, 0), indata, mockLexeme);
        LexingFailedInformation pfi = new LexingFailedInformation("No matching rule", lexingPosition);
        assertEquals(pfi, pr.getErrorMessage());
    }


    @Test
    public void positiveInteger() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.numberLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("1");
        assertEquals(NumberLiteralNode.fromFloat(1), ast);
    }

    @Test
    public void negativeExponent() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.numberLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("-0.5e-5");
        assertEquals(new NumberLiteralNode("-0.5e-5"), ast);
    }

    @Test
    public void leadingZeroesForbidden() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.numberLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        try {
            l.newLexParser().parse("01.5");
            fail("Expected exception");
        } catch (ParsingFailedException e) {
            FailureInformation actual = e.getFailureInformation();
            assertEquals("Parsing failed: 'Current token does not support led parsing' @  position 1:2 (index 1), current lexeme is number_literal(1.5), previous was number_literal(0), and remaining are [END]", actual.toString());
        }
    }

    @Test
    public void bool() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.boolLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("true");
        assertEquals(new BooleanLiteralNode(true), ast);
    }

    @Test
    public void nul() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.nullLiteral(lb);
        Language<JsonNode> l = lb.completeLanguage();
        JsonNode ast = l.newLexParser().parse("null");
        assertEquals(NullLiteral.get(), ast);
    }

    @Test
    public void emptyArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse("[]");
        assertEquals(new ArrayNode(Collections.emptyList()), ast);
    }

    @Test
    public void singleElementArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse("[3]");
        assertEquals(new ArrayNode(Arrays.asList(NumberLiteralNode.fromFloat(3))), ast);
    }

    @Test
    public void multipleElementArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse("[3,2,4]");
        assertEquals(new ArrayNode(Arrays.asList(NumberLiteralNode.fromFloat(3), NumberLiteralNode.fromFloat(2), NumberLiteralNode.fromFloat(4))), ast);
    }

    @Test
    public void emptyObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse("{}");
        assertEquals(new ObjectNode(Collections.emptyMap()), ast);
    }

    @Test
    public void simpleObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse("{\"a\":3}");
        LinkedHashMap<StringLiteralNode, JsonNode> expected = new LinkedHashMap<>();
        expected.put(new StringLiteralNode("a"), NumberLiteralNode.fromFloat(3));
        assertEquals(new ObjectNode(expected), ast);
    }

    @Test
    public void nestedObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse("{\"a\":{\"b\":3}}");
        LinkedHashMap<StringLiteralNode, JsonNode> inner = new LinkedHashMap<>();
        inner.put(new StringLiteralNode("b"), NumberLiteralNode.fromFloat(3));
        LinkedHashMap<StringLiteralNode, JsonNode> outer = new LinkedHashMap<>();
        outer.put(new StringLiteralNode("a"), new ObjectNode(inner));
        assertEquals(new ObjectNode(outer), ast);
    }

    @Test
    public void whitespace() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.newLexParser().parse(" { \"a\" :  3 }");
        LinkedHashMap<StringLiteralNode, JsonNode> outer = new LinkedHashMap<>();
        outer.put(new StringLiteralNode("a"), NumberLiteralNode.fromFloat(3));
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
        JsonNode ast = l.newLexParser().parse(data);
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
