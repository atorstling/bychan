package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alext
 * Date: 3/22/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LanguageBuilder {
    @NotNull
    private final List<TokenDefinition> tokens;

    public LanguageBuilder() {
        this.tokens = new ArrayList<TokenDefinition>();
    }

    public LanguageBuilder addToken(TokenDefinition token) {
        tokens.add(token);
        return this;
    }

    public Language build() {
        return new Language();
    }
}
