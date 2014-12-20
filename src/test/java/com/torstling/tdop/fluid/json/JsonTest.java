package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.*;
import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder2;
import com.torstling.tdop.fluid.TokenDefinition;
import com.torstling.tdop.fluid.TokenDefinitionBuilder;
import com.torstling.tdop.fluid.json.nodes.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonTest {

    @Test
    public void simpleStringLiteral() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.stringLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("\"hello\"");
        assertEquals(new StringLiteralNode("hello"), ast);
    }

    @Test
    public void stringWithQuoteEscape() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.stringLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("\"\\\"hello\"");
        assertEquals(new StringLiteralNode("\\\"hello"), ast);
    }

    @Test
    public void stringWithInvalidEscape() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.stringLiteral())
                .completeLanguage();
        ParseResult<JsonNode> pr = l.getParser().tryParse("\"\\phello\"");
        assertEquals(ParsingFailedInformation.forFailedLexing(new LexingFailedInformation("No matching rule for char-range starting at 0: '\"\\phello\"'", new LexingPosition(0, "\"\\phello\""))), pr.getErrorMessage());
    }


    @Test
    public void positiveInteger() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("1");
        assertEquals(new NumberLiteralNode(1), ast);
    }

    @Test
    public void negativeExponent() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("-0.5e-5");
        assertEquals(new NumberLiteralNode(-0.5e-5f), ast);
    }

    @Test
    public void leadingZeroesForbidden() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.numberLiteral())
                .completeLanguage();
        try {
            l.getParser().parse("01.5");
            fail("Expected exception");
        } catch (ParsingFailedException e) {
            assertEquals(ParsingFailedInformation.forFailedAfterLexing("Expected a token of type 'EndTokenType', but got 'number_literal(1.5)'", new ParsingPosition(4, "1.5")), e.getParsingFailedInformation());
        }
    }

    @Test
    public void bool() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.boolLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("true");
        assertEquals(new BooleanLiteralNode(true), ast);
    }

    @Test
    public void nul() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(JsonLangBuilder.nullLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("null");
        assertEquals(NullLiteral.get(), ast);
    }

    @Test
    public void emptyArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getParser().parse("[]");
        assertEquals(new ArrayNode(Collections.emptyList()), ast);
    }

    @Test
    public void singleElementArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getParser().parse("[3]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3))), ast);
    }

    @Test
    public void multipleElementArray() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getParser().parse("[3,2,4]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3), new NumberLiteralNode(2), new NumberLiteralNode(4))), ast);
    }

    @Test
    public void emptyObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getParser().parse("{}");
        assertEquals(new ObjectNode(Collections.emptyMap()), ast);
    }

    @Test
    public void simpleObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getParser().parse("{\"a\":3}");
        LinkedHashMap<StringLiteralNode, JsonNode> expected = new LinkedHashMap<>();
        expected.put(new StringLiteralNode("a"), new NumberLiteralNode(3));
        assertEquals(new ObjectNode(expected), ast);
    }

    @Test
    public void nestedObject() {
        Language<JsonNode> l = makeJson();
        JsonNode ast = l.getParser().parse("{\"a\":{\"b\":3}}");
        LinkedHashMap<StringLiteralNode, JsonNode> inner = new LinkedHashMap<>();
        inner.put(new StringLiteralNode("b"), new NumberLiteralNode(3));
        LinkedHashMap<StringLiteralNode, JsonNode> outer = new LinkedHashMap<>();
        outer.put(new StringLiteralNode("a"), new ObjectNode(inner));
        assertEquals(new ObjectNode(outer), ast);
    }

    private Language<JsonNode> makeJson() {
        return new JsonLangBuilder().build();

    }


}
