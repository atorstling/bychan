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
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        TokenDefinition<JsonNode> stringLiteral = lb.newToken()
                .named("stringLiteral")
                .matchesPattern("\".*\"")
                .supportsStandalone((previous, match) -> new StringLiteralNode(match.getText().substring(1, match.getText().length() - 1)))
                .build();
        lb.newLowerPriorityLevel().addToken(stringLiteral).endLevel();
        Language<JsonNode> lang = lb.completeLanguage();
        ParseResult<JsonNode> parseResult = lang.getParser().tryParse(null, "\"hey\"");
        assertEquals(parseResult.getRootNode().evaluate(), "hey");

    }
}
