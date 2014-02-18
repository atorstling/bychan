package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder;
import com.torstling.tdop.fluid.TokenDefinition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonTest {

    @Test
    public void value() {
        LanguageBuilder<JsonNode, String> lb = new LanguageBuilder<>();
        TokenDefinition<JsonNode, String> stringLiteral = lb.newToken()
                .named("stringLiteral")
                .matchesPattern("\".*\"")
                .supportsStandalone((previous, match) -> new StringLiteralNode(match.getText().substring(1, match.getText().length() - 1)))
                .build();
        lb.newLowerPriorityLevel().addToken(stringLiteral).endLevel();
        Language<JsonNode, String> lang = lb.completeLanguage();
        ParseResult<JsonNode> parseResult = lang.getParser().tryParse(new JsonRootNode(), "\"hey\"");
        assertEquals(parseResult.getRootNode().evaluate(), "hey");

    }
}
