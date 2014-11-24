package com.torstling.tdop.fluid;

import com.torstling.tdop.boolexp.AndNode;
import com.torstling.tdop.boolexp.BooleanExpressionNode;
import com.torstling.tdop.boolexp.NotNode;
import com.torstling.tdop.boolexp.VariableNode;
import com.torstling.tdop.core.ParseResult;
import com.torstling.tdop.core.ParsingFailedInformation;
import com.torstling.tdop.core.ParsingPosition;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanLogicTest {

    @Test
    public void terserSyntax() {
        LanguageBuilder2<BooleanExpressionNode> lb = new LanguageBuilder2<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb
                .newLevel().startToken().matchesString(")").named("rparen").completeTokenAndPause();
        Language<BooleanExpressionNode> l = lb
                .newLevel().startToken().matchesString("(").named("lparen").prefixParseAs((previous, match, parser) -> {
                    BooleanExpressionNode trailingExpression = parser.expression(previous);
                    parser.expectSingleToken(rparen);
                    return trailingExpression;
                }).completeToken()
                .startToken().matchesPattern("\\s+").named("whitespace").ignoreWhenParsing().completeToken().endLevel()
                .newLevel()
                .startToken().matchesString("!").named("not").prefixParseAs((previous, match, parser) -> new NotNode(parser.expression(previous))).completeToken()
                .endLevel()
                .newLevel()
                .startToken().matchesString("&").named("and").infixParseAs((match, previous, parser) -> new AndNode(previous, parser.expression(previous))).completeToken()
                .endLevel()
                .newLevel()
                .startToken().matchesPattern("[a-z]+").named("variable").standaloneParseAs((previous, match) -> new VariableNode(match.getText())).completeToken()
                .endLevel()
                .completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    @Test
    public void clearerSyntax() {
        LanguageBuilder<BooleanExpressionNode> lb = new LanguageBuilder<>();
        final TokenDefinition<BooleanExpressionNode> rparen = lb.newToken().matchesString(")").named("rparen").build();
        TokenDefinition<BooleanExpressionNode> lparen = lb.newToken().matchesString("(").named("lparen").supportsPrefix((previous, match, parser) -> {
            BooleanExpressionNode trailingExpression = parser.expression(previous);
            parser.expectSingleToken(rparen);
            return trailingExpression;
        }).build();
        TokenDefinition<BooleanExpressionNode> whitespace = lb.newToken().matchesPattern("\\s+").named("whitespace").ignoredWhenParsing().build();
        TokenDefinition<BooleanExpressionNode> not = lb.newToken().matchesString("!").named("not").supportsPrefix((previous, match, parser) -> new NotNode(parser.expression(previous))).build();
        TokenDefinition<BooleanExpressionNode> and = lb.newToken().matchesString("&").named("and").supportsInfix((match, previous, parser) -> new AndNode(previous, parser.expression(previous))).build();
        TokenDefinition<BooleanExpressionNode> variable = lb.newToken().matchesPattern("[a-z]+").named("variable").supportsStandalone((previous, match) -> new VariableNode(match.getText())).build();
        Language<BooleanExpressionNode> l = lb
                .newLowerPriorityLevel()
                .addToken(lparen).addToken(rparen).addToken(whitespace)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(not)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(and)
                .endLevel()
                .newLowerPriorityLevel()
                .addToken(variable)
                .endLevel()
                .completeLanguage();
        checkparanthesisPrio(l);
        checkParseFailure(l);
    }

    private void checkparanthesisPrio(@NotNull final Language<BooleanExpressionNode> l) {
        check(l, "!( a & b) ", true, false, true);
        check(l, "!( a & b) ", true, true, false);
        check(l, "!  a & b  ", false, true, true);
    }

    private void checkParseFailure(@NotNull final Language<BooleanExpressionNode> l) {
        ParseResult<BooleanExpressionNode> parseResult = l.getParser().tryParse("(a");
        Assert.assertTrue(parseResult.isFailure());
        ParsingFailedInformation errorMessage = parseResult.getErrorMessage();
        assertEquals(new ParsingFailedInformation("Expected a token of type 'rparen', but got 'END'", new ParsingPosition(2, "")), errorMessage);
    }

    private void check(@NotNull final Language<BooleanExpressionNode> l, @NotNull final String expression, final boolean aValue, final boolean bValue, final boolean expectedOutcome) {
        ParseResult<BooleanExpressionNode> result = l.getParser().tryParse(expression);
        assertTrue(result.isSuccess());
        VariableBindings bindings = new VariableBindingBuilder().bind("a", aValue).bind("b", bValue).build();
        assertEquals(result.getRootNode().evaluate(bindings), expectedOutcome);
    }
}