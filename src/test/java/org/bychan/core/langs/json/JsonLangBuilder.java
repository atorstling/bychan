package org.bychan.core.langs.json;

import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinition;
import org.bychan.core.langs.json.nodes.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class JsonLangBuilder {

    @NotNull
    public Language<JsonNode> build() {
        LanguageBuilder<JsonNode> lb = new LanguageBuilder<>();
        JsonLangBuilder.nullLiteral(lb);
        JsonLangBuilder.boolLiteral(lb);
        TokenDefinition<JsonNode> rcurly = rcurly(lb);
        TokenDefinition<JsonNode> comma = comma(lb);
        TokenDefinition<JsonNode> string = stringLiteral(lb);
        TokenDefinition<JsonNode> colon = colon(lb);
        TokenDefinition<JsonNode> rbracket = rbracket(lb);
        numberLiteral(lb);
        lb.newToken().named("whitespace").discardAfterLexing().matchesWhitespace().build();
        lb.newToken().named("lbracket").matchesString("[")
                .nud((left, parser, lexeme) -> {
                    ArrayList<JsonNode> expressions = new ArrayList<>();
                    while (!parser.peek().isA(rbracket.getToken())) {
                        expressions.add(parser.parseExpression(left, lexeme.leftBindingPower()));
                        if (!parser.peek().isA(rbracket.getToken())) {
                            parser.swallow(comma.getToken());
                        }
                    }
                    parser.swallow(rbracket.getToken());
                    return new ArrayNode(expressions);
                }).build();
        lb.newToken().named("lcurly").matchesString("{")
                .nud((left, parser, lexeme) -> {
                    LinkedHashMap<StringLiteralNode, JsonNode> pairs = new LinkedHashMap<>();
                    while (!parser.peek().isA(rcurly.getToken())) {
                        StringLiteralNode key = (StringLiteralNode) parser.parseSingleToken(left, string.getToken());
                        parser.swallow(colon.getToken());
                        JsonNode value = parser.parseExpression(left, lexeme.leftBindingPower());
                        pairs.put(key, value);
                        if (!parser.peek().isA(rcurly.getToken())) {
                            parser.swallow(comma.getToken());
                        }
                    }
                    parser.swallow(rcurly.getToken());
                    return new ObjectNode(pairs);
                }).build();

        return lb.completeLanguage();
    }

    @NotNull
    private TokenDefinition<JsonNode> rbracket(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("rbracket").matchesString("]")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> rcurly(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("rcurly").matchesString("}")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> comma(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("comma").matchesString(",")
                .build();
    }


    @NotNull
    private TokenDefinition<JsonNode> colon(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("colon").matchesString(":")
                .build();
    }


    static TokenDefinition<JsonNode> nullLiteral(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("null_literal").matchesString("null").nud((left, parser, lexeme) -> NullLiteral.get()).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> boolLiteral(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("bool_literal").matchesPattern("(true)|(false)").nud((left, parser, lexeme) -> new BooleanLiteralNode(Boolean.valueOf(lexeme.getText()))).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> stringLiteral(LanguageBuilder<JsonNode> lb) {
        @org.intellij.lang.annotations.Language("RegExp")
        String pattern = "\"((?:[^\"\\\\]|\\\\(?:[\"/\\\\bnrft]|u[0-9A-Fa-f]{4}))*)\"";
        return lb.newToken().named("string").matchesPattern(pattern).nud((left, parser, lexeme) -> {
            String withinQuotationMarks = lexeme.getMatch().group(1);
            return new StringLiteralNode(withinQuotationMarks);
        }).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> numberLiteral(LanguageBuilder<JsonNode> lb) {
        return lb.newToken().named("number_literal")
                .matchesPattern("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE]([+-])?[0-9]+)?")
                .nud((left, parser, lexeme) -> new NumberLiteralNode(lexeme.getText()))
                .build();
    }
}
