package com.torstling.tdop.fluid.statements;

import com.torstling.tdop.fluid.Language;
import com.torstling.tdop.fluid.LanguageBuilder;
import com.torstling.tdop.fluid.TokenDefinition;
import org.junit.Test;

/**
 * Created by alext on 2/27/14.
 */
public class StatementsTest {

    @Test
    public void whileStatement() {
        LanguageBuilder<Object> l = new LanguageBuilder<Object>();
        TokenDefinition<Object> whitespace = l.newToken().named("whitespace").matchesPattern("\\s+").ignoredWhenParsing().build();
        TokenDefinition<Object> whileKeyword = l.newToken().named("whileKeyword").matchesString("while").build();
        TokenDefinition<Object> lparen = l.newToken().named("lparen").matchesString("(").build();
        TokenDefinition<Object> rparen = l.newToken().named("rparen").matchesString(")").build();
        TokenDefinition<Object> lcurly = l.newToken().named("lcurly").matchesString("{").build();
        TokenDefinition<Object> rcurly = l.newToken().named("rcurly").matchesString("}").build();
        TokenDefinition<Object> whileStatement = l.newStatement("whileStatement").as((previous, match, parser) -> {
            parser.expectSingleToken(whileKeyword);
            parser.expectSingleToken(lparen);
            parser.expectSingleToken(rparen);
            parser.expectSingleToken(lcurly);
            parser.expectSingleToken(rcurly);
            return new WhileStatement();
        }).build();
        Language<Object> language = l.newLowerPriorityLevel()
                .addToken(whitespace)
                .addToken(whileKeyword)
                .addToken(lparen)
                .addToken(rparen)
                .addToken(lcurly)
                .addToken(rcurly)
                .addToken(whileStatement)
                .endLevel()
                .completeLanguage();
        //language.getParser().parse("a");
    }

}
