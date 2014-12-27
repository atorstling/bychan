package org.bychan.fluid.json;

import org.bychan.fluid.Language;
import org.bychan.fluid.FluidLanguageBuilder;
import org.bychan.fluid.TokenDefinition;
import org.bychan.fluid.TokenDefinitionBuilder;
import org.bychan.fluid.json.nodes.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class JsonLangBuilder {

    @NotNull
    public Language<JsonNode> build() {
        FluidLanguageBuilder<JsonNode> lb = new FluidLanguageBuilder<>();
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
        TokenDefinition<JsonNode> lcurly = new TokenDefinitionBuilder<>(lb.getDelegate()).named("lcurly").matchesString("{")
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
                .newLevel()
                .addToken(numberLiteral).addToken(string).addToken(nullLiteral(lb)).addToken(boolLiteral(lb))
                .completeLanguage();
    }

    @NotNull
    private TokenDefinition<JsonNode> rbracket(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("rbracket").matchesString("]")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> rcurly(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("rculry").matchesString("}")
                .build();
    }

    @NotNull
    private TokenDefinition<JsonNode> comma(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("comma").matchesString(",")
                .build();
    }


    @NotNull
    private TokenDefinition<JsonNode> colon(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("colon").matchesString(":")
                .build();
    }


    static TokenDefinition<JsonNode> nullLiteral(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("null_literal").matchesString("null")
                .standaloneParseAs((previous, match) -> NullLiteral.get()).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> boolLiteral(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("bool_literal").matchesPattern("(true)|(false)")
                .standaloneParseAs((previous, match) -> new BooleanLiteralNode(Boolean.valueOf(match.getText()))).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> stringLiteral(FluidLanguageBuilder<JsonNode> lb) {
        @org.intellij.lang.annotations.Language("RegExp")
        String pattern = "\"((?:[^\"\\\\]|\\\\(?:[\"/bnrft]|u[0-9A-F]{4}))*)\"";
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("string_literal").matchesPattern(pattern)
                .standaloneParseAs((previous, match) -> {
                    String withinQuotationMarks = match.group(1);
                    return new StringLiteralNode(withinQuotationMarks);
                }).build();
    }

    @NotNull
    static TokenDefinition<JsonNode> numberLiteral(FluidLanguageBuilder<JsonNode> lb) {
        return new TokenDefinitionBuilder<JsonNode>(lb.getDelegate()).named("number_literal").matchesPattern("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE]([+-])?[0-9]+)?")
                .standaloneParseAs((previous, match) -> new NumberLiteralNode(Float.valueOf(match.getText()))).build();
    }
}
