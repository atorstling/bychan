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
This language uses `Long`s as AST nodes, so we don't even get an AST in the classical sense. Instead we get a result directly!

You can choose any AST node type. Lets try to write a parser which converts to RPN,
and throw in some whitespace and parentheses while we're at it:
```Java
            @Test
            public void toRpn() {
                LanguageBuilder<String> lb = new LanguageBuilder<>("calc");
                lb.newToken().named("whitespace")
                                .matchesWhitespace()
                                .discardAfterLexing()
                        .build();
                lb.newToken().named("digit").matchesPattern("[0-9]+")
                        .nud((previous, parser, lexeme) -> lexeme.getText())
                        .build();
                TokenDefinition<String> rparen = lb.newToken()
                        .named("rparen")
                        .matchesString(")")
                        .build();
                lb.newToken().named("lparen").matchesString("(").nud((previous, parser, lexeme) -> {
                    String next = parser.expression(previous);
                    parser.expectSingleLexeme(rparen.getKey());
                    return next;
                }).build();
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
                assertEquals("(+ (* (+ 1 2) 3) 5)", lexParser.parse("( 1 + 2 ) * 3 + 5"));
            }
```
If you want you can use your own classes as AST nodes. Shall we try with some simple boolean logic?
```Java
    interface BoolNode{
        boolean evaluate();
    }
    class LiteralNode implements BoolNode {
        boolean value;
        public LiteralNode(boolean value) {
            this.value = value;
        }
        @Override
        public boolean evaluate() {
            return value;
        }
    }
    class AndNode implements BoolNode {
        public BoolNode left;
        public BoolNode right;
        public AndNode(BoolNode left, BoolNode right) {
            this.left = left;
            this.right = right;
        }
        @Override
        public boolean evaluate() { return left.evaluate() && right.evaluate(); }
    }
    @Test
    public void boolLogic() {
        LanguageBuilder<BoolNode> lb = new LanguageBuilder<>("boolLogic");
        lb.newToken().named("literal")
                .matchesPattern("true|false")
                .nud((previous, parser, lexeme) -> new LiteralNode(Boolean.parseBoolean(lexeme.getText())))
                .build();
        lb.newToken().named("and")
                .matchesString("&&")
                .led((previous, parser, lexeme) -> new AndNode(previous, parser.expression(previous)))
                .build();
        Language<BoolNode> l = lb.completeLanguage();
        LexParser<BoolNode> lexParser = l.newLexParser();
        BoolNode one = lexParser.parse("false&&false");
        assertFalse(one.evaluate());
        BoolNode two = lexParser.parse("true&&false");
        assertFalse(two.evaluate());
        BoolNode three = lexParser.parse("true&&true");
        assertTrue(three.evaluate());
    }
```

##License
MIT.

##Maturity
This is the first release of Bychan. For this release I tried to focus on expressions, so functionality for statements
 might be a bit lacking.
 But there is a (not entirely correct) [JSON parser](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/json/JsonLangBuilder.java)
 implementation and a [minimal computer language](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/minilang/MiniLangTest.java).

