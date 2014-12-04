package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder2;
import com.torstling.tdop.fluid.LevelLanguageBuilder2;
import com.torstling.tdop.fluid.WrappedTokenDefinitionBuilder;
import com.torstling.tdop.fluid.json.nodes.JsonNode;
import com.torstling.tdop.fluid.json.nodes.StringLiteralNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alext on 2014-11-23.
 */
public class JsonTest {

    @Test
    public void simpleStringLiteral() {
        JsonNode ast = parse("\"hello\"");
        assertEquals(new StringLiteralNode("hello"), ast);
    }

    private JsonNode parse(String text) {
        LanguageBuilder2<JsonNode> lb = new LanguageBuilder2<>();
        LevelLanguageBuilder2<JsonNode> l1 = lb.newLevel();
        Language<JsonNode> l =
                stringLiteral(l1).completeLanguage();
        return l.getParser().parse(text);
    }

    private WrappedTokenDefinitionBuilder<JsonNode> stringLiteral(LevelLanguageBuilder2<JsonNode> l1) {
        return l1.startToken().named("string_literal").matchesPattern("\"(\\w*)\"")
                .standaloneParseAs((previous, match) -> {
                    String withinQuotationMarks = match.group(1);
                    return new StringLiteralNode(withinQuotationMarks);
                });
    }

}
