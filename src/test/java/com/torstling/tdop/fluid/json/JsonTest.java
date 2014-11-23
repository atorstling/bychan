package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder2;
import com.torstling.tdop.fluid.json.nodes.JsonNode;
import com.torstling.tdop.fluid.json.nodes.StringLiteralNode;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Created by alext on 2014-11-23.
 */
public class JsonTest {


    @Test
    public void test() {
        LanguageBuilder2<JsonNode> lb = new LanguageBuilder2<>();
        String stringLiteralPattern = "\"(\\w+)\"";
        Language<JsonNode> l = lb.newLevelToken().named("string_literal").matchesPattern(stringLiteralPattern)
                .standaloneParseAs((previous, match) -> {
                    String text = match.getText();
                    Matcher matcher = Pattern.compile(stringLiteralPattern).matcher(text);
                    boolean matches = matcher.matches();
                    assert matches;
                    String withinQuotationMarks = matcher.group(1);
                    return new StringLiteralNode(withinQuotationMarks);
                })
                .completeLanguage();
        JsonNode ast = l.getParser().parse("\"hello\"");
        assertEquals(new StringLiteralNode("hello"), ast);
    }

}
