package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder2;
import com.torstling.tdop.fluid.TokenDefinition;
import com.torstling.tdop.fluid.TokenDefinitionBuilder;
import com.torstling.tdop.fluid.json.nodes.JsonNode;
import com.torstling.tdop.fluid.json.nodes.StringLiteralNode;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

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

    @NotNull
    private TokenDefinition<JsonNode> stringLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("string_literal").matchesPattern("\"(\\w*)\"")
                .standaloneParseAs((previous, match) -> {
                    String withinQuotationMarks = match.group(1);
                    return new StringLiteralNode(withinQuotationMarks);
                }).build();
    }

}
