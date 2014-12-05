package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.ParsingFailedException;
import com.torstling.tdop.core.ParsingFailedInformation;
import com.torstling.tdop.core.ParsingPosition;
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
            assertEquals(new ParsingFailedInformation("Expected a token of type 'EndTokenType', but got 'number_literal(1.5)'", new ParsingPosition(4, "1.5")), e.getParsingFailedInformation());
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
        TokenDefinition<JsonNode> rbracket = rbracket();
        TokenDefinition<JsonNode> comma = comma();
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(comma).addToken(rbracket).addToken(lbracket(rbracket, comma)).newLevel().addToken(numberLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("[3,2,4]");
        assertEquals(new ArrayNode(Arrays.asList(new NumberLiteralNode(3), new NumberLiteralNode(2), new NumberLiteralNode(4))), ast);
    }

    @NotNull
    private TokenDefinition<JsonNode> rbracket() {
        return new TokenDefinitionBuilder<JsonNode>().named("rbracket").matchesString("]")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> comma() {
        return new TokenDefinitionBuilder<JsonNode>().named("comma").matchesString(",")
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
        return new TokenDefinitionBuilder<JsonNode>().named("string_literal").matchesPattern("\"(\\w*)\"")
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
