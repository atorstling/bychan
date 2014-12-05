package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder2;
import com.torstling.tdop.fluid.TokenDefinition;
import com.torstling.tdop.fluid.TokenDefinitionBuilder;
import com.torstling.tdop.fluid.json.nodes.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static com.torstling.tdop.fluid.json.nodes.NullLiteral.get;
import static org.junit.Assert.assertEquals;

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
                .newLevel().addToken(number())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("1");
        assertEquals(new NumberLiteralNode(1), ast);
    }

    @Test
    public void negativeExponent() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(number())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("-0.5e-5");
        assertEquals(new NumberLiteralNode(-0.5e-5f), ast);
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
    public void zero() {
        Language<JsonNode> l = new LanguageBuilder2<JsonNode>()
                .newLevel().addToken(nullLiteral())
                .completeLanguage();
        JsonNode ast = l.getParser().parse("null");
        assertEquals(NullLiteral.get(), ast);
    }

    private TokenDefinition<JsonNode> nullLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("null_literal").matchesString("null")
                .standaloneParseAs((previous, match) -> get()).build();
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
    private TokenDefinition<JsonNode> number() {
        return new TokenDefinitionBuilder<JsonNode>().named("number_literal").matchesPattern("-?[0-9]+(\\.[0-9]+)?([eE]([+-])?[0-9]+)?")
                .standaloneParseAs((previous, match) -> new NumberLiteralNode(Float.valueOf(match.getText()))).build();
    }


}
