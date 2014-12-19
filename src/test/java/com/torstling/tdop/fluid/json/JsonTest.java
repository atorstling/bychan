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
                .newLevel().addToken(stringLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("\"hello\"");
        assertEquals(new StringLiteralNode("hello"), ast);
    }

    @Test
    public void stringWithQuoteEscape() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(stringLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("\"\\\"hello\"");
        assertEquals(new StringLiteralNode("\\\"hello"), ast);
    }

    @Test
    public void stringWithInvalidEscape() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(stringLiteral())
                .completeLanguage();
        ParseResult<JsonNode> pr = l.getParser().tryParse("\"\\phello\"");
        assertEquals(ParsingFailedInformation.forFailedLexing(new LexingFailedInformation("No matching rule for char-range starting at 0: '\"\\phello\"'", new LexingPosition(0, "\"\\phello\""))), pr.getErrorMessage());
    }


    @Test
    public void positiveInteger() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("1");
        assertEquals(new NumberLiteralNode(1), ast);
    }

    @Test
    public void negativeExponent() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("-0.5e-5");
        assertEquals(new NumberLiteralNode(-0.5e-5f), ast);
    }

    @Test
    public void leadingZeroesForbidden() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(numberLiteral())
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
                .newLevel().addToken(boolLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("true");
        assertEquals(new BooleanLiteralNode(true), ast);
    }

    @Test
    public void nul() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(nullLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("null");
        assertEquals(NullLiteral.get(), ast);
    }

    @Test
    public void emptyArray() {
        TokenDefinition<JsonNode> rbracket = rbracket();
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(rbracket).addToken(lbracket(rbracket, comma())).newLevel().addToken(numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("[]");
        assertEquals(new ArrayNode(Collections.emptyList()), ast);
    }

    @Test
    public void singleElementArray() {
        TokenDefinition<JsonNode> rbracket = rbracket();
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(rbracket).addToken(lbracket(rbracket, comma())).newLevel().addToken(numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("[3]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3))), ast);
    }

    @Test
    public void multipleElementArray() {
        TokenDefinition<JsonNode> rbracket = rbracket();
        TokenDefinition<JsonNode> comma = comma();
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(comma).addToken(rbracket).addToken(lbracket(rbracket, comma)).newLevel().addToken(numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("[3,2,4]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3), new NumberLiteralNode(2), new NumberLiteralNode(4))), ast);
    }

    @Test
    public void emptyObject() {
        TokenDefinition<JsonNode> rcurly = rcurly();
        TokenDefinition<JsonNode> comma = comma();
        TokenDefinition<JsonNode> string = stringLiteral();
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(comma).addToken(rcurly).addToken(lcurly(rcurly, comma, colon(), string)).newLevel().addToken(numberLiteral())
                .addToken(string).completeLanguage();
        JsonNode ast = l.getParser().parse("{}");
        assertEquals(new ObjectNode(Collections.emptyMap()), ast);
    }

    //@Test
    public void simpleObject() {
        TokenDefinition<JsonNode> rcurly = rcurly();
        TokenDefinition<JsonNode> comma = comma();
        TokenDefinition<JsonNode> string = stringLiteral();
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(comma).addToken(rcurly).addToken(lcurly(rcurly, comma, colon(), string)).newLevel().addToken(numberLiteral())
                .addToken(string).completeLanguage();
        JsonNode ast = l.getParser().parse("{\"a\": 3}");
        LinkedHashMap<StringLiteralNode, JsonNode> expected = new LinkedHashMap<>();
        expected.put(new StringLiteralNode("a"), new NumberLiteralNode(3));
        assertEquals(new ObjectNode(expected), ast);
    }

    @NotNull
    private TokenDefinition<JsonNode> rbracket() {
        return new TokenDefinitionBuilder<JsonNode>().named("rbracket").matchesString("]")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> rcurly() {
        return new TokenDefinitionBuilder<JsonNode>().named("rculry").matchesString("}")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> comma() {
        return new TokenDefinitionBuilder<JsonNode>().named("comma").matchesString(",")
                .build();
    }


    @NotNull
    private TokenDefinition<JsonNode> colon() {
        return new TokenDefinitionBuilder<JsonNode>().named("colon").matchesString(":")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> lbracket(TokenDefinition<JsonNode> rbracket, TokenDefinition<JsonNode> comma) {
        return new TokenDefinitionBuilder<JsonNode>().named("lbracket").matchesString("[")
                .prefixParseAs((previous, match, parser) -> {
                    ArrayList<JsonNode> expressions = new ArrayList<>();
                    while (!parser.nextIs(rbracket)) {
                        expressions.add(parser.expression(previous));
                        if (!parser.nextIs(rbracket)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(rbracket);
                    return new ArrayNode(expressions);
                }).build();
    }

    @NotNull
    private TokenDefinition<JsonNode> lcurly(TokenDefinition<JsonNode> rbracket, TokenDefinition<JsonNode> comma, TokenDefinition<JsonNode> colon, TokenDefinition<JsonNode> string) {
        return new TokenDefinitionBuilder<JsonNode>().named("lcurly").matchesString("{")
                .prefixParseAs((previous, match, parser) -> {
                    LinkedHashMap<StringLiteralNode, JsonNode> pairs = new LinkedHashMap<>();
                    while (!parser.nextIs(rbracket)) {
                        StringLiteralNode key = (StringLiteralNode) parser.parseSingleToken(previous, string);
                        parser.expectSingleToken(colon);
                        JsonNode value = parser.expression(previous);
                        pairs.put(key, value);
                        if (!parser.nextIs(rbracket)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(rbracket);
                    return new ObjectNode(pairs);
                }).build();
    }


    private TokenDefinition<JsonNode> nullLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("null_literal").matchesString("null")
                .standaloneParseAs((previous, match) -> NullLiteral.get()).build();
    }

    @NotNull
    private TokenDefinition<JsonNode> boolLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("bool_literal").matchesPattern("(true)|(false)")
                .standaloneParseAs((previous, match) -> new BooleanLiteralNode(Boolean.valueOf(match.getText()))).build();
    }

    @NotNull
    private TokenDefinition<JsonNode> stringLiteral() {
        @org.intellij.lang.annotations.Language("RegExp")
                String pattern = "\"((?:[^\"\\\\]|\\\\(?:[\"\\/bnrft]|u[0-9A-F]{4}))*)\"";
        return new TokenDefinitionBuilder<JsonNode>().named("string_literal").matchesPattern(pattern)
                .standaloneParseAs((previous, match) -> {
                    String withinQuotationMarks = match.group(1);
                    return new StringLiteralNode(withinQuotationMarks);
                }).build();
    }

    @NotNull
    private TokenDefinition<JsonNode> numberLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("number_literal").matchesPattern("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE]([+-])?[0-9]+)?")
                .standaloneParseAs((previous, match) -> new NumberLiteralNode(Float.valueOf(match.getText()))).build();
    }


}
