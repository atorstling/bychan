package org.bychan.dynamic.json;

import org.bychan.dynamic.*;
import org.bychan.dynamic.json.nodes.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class JsonLangBuilder {

    @NotNull
    public Language<JsonNode> build() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        TokenDefinition<JsonNode> rcurly = rcurly(lb);
        TokenDefinition<JsonNode> comma = comma(lb);
        TokenDefinition<JsonNode> string = stringLiteral(lb);
        TokenDefinition<JsonNode> colon = colon(lb);
        TokenDefinition<JsonNode> rbracket = rbracket(lb);
        TokenDefinition<JsonNode> numberLiteral = numberLiteral(lb);
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
        TokenDefinition<JsonNode> lcurly = new TokenDefinitionBuilder<>(lb).named("lcurly").matchesString("{")
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
                .addToken(whitespace).addToken(rbracket).addToken(lbracket).addToken(comma).addToken(colon).addToken(rcurly).addToken(lcurly)
                .addToken(numberLiteral).addToken(string).addToken(nullLiteral(lb)).addToken(boolLiteral(lb))
                .completeLanguage();
    }

    @NotNull
    private TokenDefinition<JsonNode> rbracket(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("rbracket").matchesString("]")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> rcurly(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("rculry").matchesString("}")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> comma(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("comma").matchesString(",")
                .build();
    }


    @NotNull
    private TokenDefinition<JsonNode> colon(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("colon").matchesString(":")
                .build();
    }


    static TokenDefinition<JsonNode> nullLiteral(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("null_literal").matchesString("null").prefixParseAs((previous, match, parser) -> NullLiteral.get()).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> boolLiteral(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("bool_literal").matchesPattern("(true)|(false)").prefixParseAs((previous, match, parser) -> new BooleanLiteralNode(Boolean.valueOf(match.getText()))).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> stringLiteral(LanguageBuilder<JsonNode> lb) {
        @org.intellij.lang.annotations.Language("RegExp")
        String pattern = "\"((?:[^\"\\\\]|\\\\(?:[\"/bnrft]|u[0-9A-F]{4}))*)\"";
        return new TokenDefinitionBuilder<>(lb).named("string_literal").matchesPattern(pattern).prefixParseAs((previous, match, parser) -> {
            String withinQuotationMarks = match.group(1);
            return new StringLiteralNode(withinQuotationMarks);
        }).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> numberLiteral(LanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<>(lb).named("number_literal").matchesPattern("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE]([+-])?[0-9]+)?").prefixParseAs((previous, match, parser) -> new NumberLiteralNode(Float.valueOf(match.getText()))).build();
    }
}
