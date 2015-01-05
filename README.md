#Bychan

A Pratt (TDOP) Expression Parser Library for Java

##About

Bychan is a library for TDOP parsing. TDOP is essentially recursive descent with a clever way
of incorporating shunting-yard for the handling of expressions. It is a surprisingly powerful technique
despite its relative simplicity.

Bychan does not generate code, niether the parser itself nor the AST. It is entirely runtime based.
Bychan tries to stay out of your way as much as possible.
What it does do is to help you with the boring bits: lexing, error handling and writing REPLs.

##Show me the code
Sure! Let's start with a simple calculator:
```Java
        @Test
        public void simpleCalc() {
            LanguageBuilder<Long> lb = new LanguageBuilder<>("simpleCalc");
            lb.newToken().named("digit").matchesPattern("[0-9]+")
                    .nud((previous, parser, lexeme) -> Long.parseLong(lexeme.getText()))
                    .build();
            lb.newToken().named("plus")
                    .matchesString("+")
                    .led((previous, parser, lexeme) -> previous + parser.expression(previous))
                    .build();
            lb.newToken().named("mult")
                    .matchesString("*")
                    .led((previous, parser, lexeme) -> previous * parser.expression(previous))
                    .build();
            Language<Long> language = lb.completeLanguage();
            LexParser<Long> lexParser = language.newLexParser();
            assertEquals((Long) 7l, lexParser.parse("1+2*3"));
        }
```
This language uses `Long`s as AST nodes, so we don't even get an AST in the classical sens, we get a result directly!
You are not limited to digits, though. Lets try to write a parser which converts to RPN:
```Java
    @Test
    public void simpleCalcRpn() {
        LanguageBuilder<String> lb = new LanguageBuilder<>("simpleCalc");
        lb.newToken().named("digit").matchesPattern("[0-9]+")
                .nud((previous, parser, lexeme) -> lexeme.getText())
                .build();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((previous, parser, lexeme) -> "(+ " + previous + " " + parser.expression(previous) + ")")
                .build();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((previous, parser, lexeme) -> "(* " + previous + " " + parser.expression(previous) + ")")
                .build();
        Language<String> language = lb.completeLanguage();
        LexParser<String> lexParser = language.newLexParser();
        assertEquals("(+ 1 (* 2 3))", lexParser.parse("1+2*3"));
    }
```

##License
MIT.

##Maturity
This is the first release of Bychan. For this release I tried to focus on expressions, so functionality for statements
 might be a bit lacking.
 But there is a (not entirely correct) [JSON parser](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/json/JsonLangBuilder.java)
 implementation and a [minimal computer language](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/minilang/MiniLangTest.java).

