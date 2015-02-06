package org.bychan.core.features;

import org.bychan.core.TokenMatchResult;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by alext on 2015-02-06.
 */
public class CustomMatcherTest {

    /**
     * If we use a custom lexer which performs parsing to see if there is a
     * match (for instance SimpleDateFormat), we don't want to redo the parsing
     * after lexing. For this purpose it is possible to save a value from
     * lexing which is carried over to parsing: the lexer value.
     */
    @Test
    public void carryValueOverFromLexing() {
        DateFormat fmt = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.UK);

        LanguageBuilder<Object> lb = new LanguageBuilder<>();
        lb.newToken().named("test")
                .matches((input, searchStart) -> {
                    ParsePosition p = new ParsePosition(searchStart);
                    Date result = fmt.parse(input, p);
                    if (result == null) {
                        return null;
                    }
                    //ParsePosition gets updated with end index by parse function
                    int endIndex = p.getIndex();
                    return TokenMatchResult.create(result, endIndex);
                })
                .nud((left, parser, lexeme) -> lexeme.getLexerValue()).build();
        Language<Object> l = lb.completeLanguage();
        GregorianCalendar g = new GregorianCalendar(1970, 0, 1, 11, 14, 10);
        Assert.assertEquals(g.getTime(), l.newLexParser().parse("11:14:10"));
    }
}
