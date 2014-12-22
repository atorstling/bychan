package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder2;
import com.torstling.tdop.fluid.TokenDefinition;
import com.torstling.tdop.fluid.TokenDefinitionBuilder;
import com.torstling.tdop.fluid.json.nodes.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class JsonLangBuilder {

    @NotNull
    public Language<JsonNode> build() {
        LanguageBuilder2<JsonNode> lb = new LanguageBuilder2<>();
        TokenDefinition<JsonNode> rcurly = rcurly();
        TokenDefinition<JsonNode> comma = comma();
        TokenDefinition<JsonNode> string = stringLiteral();
        TokenDefinition<JsonNode> colon = colon();
        TokenDefinition<JsonNode> rbracket = rbracket();
        TokenDefinition<JsonNode> numberLiteral = numberLiteral();
        TokenDefinition<JsonNode> whitespace = lb.newToken().named("whitespace").ignoredWhenParsing().matchesWhitespace().build();
        TokenDefinition<JsonNode> lbracket = lb.newToken().named("lbracket").matchesString("[")
                .prefixParseAs((previous, match, parser) -> {
                    ArrayList<JsonNode> expressions = new ArrayList<>();
                    while (!parser.nextIs(rbracket)) {
                        expressions.add(parser.subExpression());
                        if (!parser.nextIs(rbracket)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(rbracket);
                    return new ArrayNode(expressions);
                }).build();
        TokenDefinition<JsonNode> lcurly = new TokenDefinitionBuilder<JsonNode>().named("lcurly").matchesString("{")
                .prefixParseAs((previous, match, parser) -> {
                    LinkedHashMap<StringLiteralNode, JsonNode> pairs = new LinkedHashMap<>();
                    while (!parser.nextIs(rcurly)) {
                        StringLiteralNode key = (StringLiteralNode) parser.parseSingleToken(previous, string);
                        parser.expectSingleToken(colon);
                        JsonNode value = parser.subExpression();
                        pairs.put(key, value);
                        if (!parser.nextIs(rcurly)) {
                            parser.expectSingleToken(comma);
                        }
                    }
                    parser.expectSingleToken(rcurly);
                    return new ObjectNode(pairs);
                }).build();

        return lb
                .newLowerPriorityLevel()
                .addToken(whitespace).addToken(rbracket).addToken(lbracket).addToken(comma).addToken(colon).addToken(rcurly).addToken(lcurly)
                .newLevel()
                .addToken(numberLiteral).addToken(string).addToken(nullLiteral()).addToken(boolLiteral())
                .completeLanguage();
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


    static TokenDefinition<JsonNode> nullLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("null_literal").matchesString("null")
                .standaloneParseAs((previous, match) -> NullLiteral.get()).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> boolLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("bool_literal").matchesPattern("(true)|(false)")
                .standaloneParseAs((previous, match) -> new BooleanLiteralNode(Boolean.valueOf(match.getText()))).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> stringLiteral() {
        @org.intellij.lang.annotations.Language("RegExp")
        String pattern = "\"((?:[^\"\\\\]|\\\\(?:[\"/bnrft]|u[0-9A-F]{4}))*)\"";
        return new TokenDefinitionBuilder<JsonNode>().named("string_literal").matchesPattern(pattern)
                .standaloneParseAs((previous, match) -> {
                    String withinQuotationMarks = match.group(1);
                    return new StringLiteralNode(withinQuotationMarks);
                }).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> numberLiteral() {
        return new TokenDefinitionBuilder<JsonNode>().named("number_literal").matchesPattern("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE]([+-])?[0-9]+)?")
                .standaloneParseAs((previous, match) -> new NumberLiteralNode(Float.valueOf(match.getText()))).build();
    }
}
