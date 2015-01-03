package org.bychan.core.langs.boolexp;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class OrToken implements Token<BooleanExpressionNode> {
    private static final OrToken INSTANCE = new OrToken();

    @NotNull
    public Lexeme<BooleanExpressionNode> toLexeme(@NotNull LexingMatch match) {
        return new OrLexeme(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\+");
    }

    @Override
    public boolean include() {
        return true;
    }

    @NotNull
    public static OrToken get() {
        //noinspection unchecked
        return INSTANCE;
    }
}
