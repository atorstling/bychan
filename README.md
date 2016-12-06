#Bychan [![Build Status](https://travis-ci.org/atorstling/bychan.svg?branch=master)](https://travis-ci.org/atorstling/bychan)

A Pratt (TDOP) Expression Parser Library for Java

##Introduction

Bychan is a library for TDOP parsing. TDOP is essentially recursive descent with a clever way
of incorporating shunting-yard for the handling of expressions. It is a surprisingly powerful technique
despite its relative simplicity.

You can read about TDOP in the [original paper](http://hall.org.ua/halls/wizzard/pdf/Vaughan.Pratt.TDOP.pdf) or at [Eli Benderskys](http://eli.thegreenplace.net/2010/01/02/top-down-operator-precedence-parsing) or [Douglas Crockfords](http://javascript.crockford.com/tdop/tdop.html) sites.

Bychan does not generate code, niether the parser itself nor the AST. It is entirely runtime based.
Bychan tries to stay out of your way as much as possible.
What it does do is to help you with the boring bits: lexing, error handling and writing REPLs.

You could call it a middle ground between using a parser generator and writing a parser by hand.

##Show me the Code
Sure! Let's start with a simple calculator:
```Java
    @Test
    public void simpleCalc() {
        LanguageBuilder<Long> lb = new LanguageBuilder<>("simpleCalc");
        lb.newToken().named("digit")
                .matchesPattern("[0-9]+")
                .nud((left, parser, lexeme) -> Long.parseLong(lexeme.text()))
                .build();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((left, parser, lexeme) -> left + parser.expr(left, lexeme.lbp()))
                .build();
        lb.powerUp();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((left, parser, lexeme) -> left * parser.expr(left, lexeme.lbp()))
                .build();
        Language<Long> language = lb.build();
        LexParser<Long> lexParser = language.newLexParser();
        assertEquals((Long) 7L, lexParser.tryParse("1+2*3", p -> p.expr(null, 0)).root());
    }
```
This language uses `Long`s as AST nodes, so we don't even get an AST in the classical sense. Instead we keep a sort of running total as we parse. When we are finished we get a result directly! Worth noting in this example is that the left binding power (lbp) of the tokens is set automatically in increasing order unless otherwise specified. So "mult" has the highest lbp and "digit" the lowest.

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
                .nud((left, parser, lexeme) -> lexeme.text())
                .build();
        lb.newToken()
                .named("rparen")
                .matchesString(")")
                .build();
        lb.newToken().named("lparen").matchesString("(").nud((left, parser, lexeme) -> {
            String next = parser.expr(left, lexeme.lbp());
            parser.swallow("rparen");
            return next;
        }).build();
        lb.powerUp();
        lb.newToken().named("plus")
                .matchesString("+")
                .led((left, parser, lexeme) -> "(+ " + left + " " + parser.expr(left, lexeme.lbp()) + ")")
                .build();
        lb.powerUp();
        lb.newToken().named("mult")
                .matchesString("*")
                .led((left, parser, lexeme) -> "(* " + left + " " + parser.expr(left, lexeme.lbp()) + ")")
                .build();
        Language<String> language = lb.build();
        LexParser<String> lexParser = language.newLexParser();
        assertEquals("(+ (* (+ 1 2) 3) 5)", lexParser.tryParse("( 1 + 2 ) * 3 + 5", p -> p.expr(null, 0)).root());
    }
```
If you want to build an AST you can build it directly with your own classes. Shall we try with some simple boolean logic?
```Java
    @Test
    public void boolLogic() {
        LanguageBuilder<BoolNode> lb = new LanguageBuilder<>("boolLogic");
        lb.newToken().named("literal")
                .matchesPattern("true|false")
                .nud((left, parser, lexeme) -> new LiteralNode(Boolean.parseBoolean(lexeme.text())))
                .build();
        lb.newToken().named("and")
                .matchesString("&&")
                .led((left, parser, lexeme) -> new AndNode(left, parser.expr(left, lexeme.lbp())))
                .build();
        Language<BoolNode> l = lb.build();
        LexParser<BoolNode> lexParser = l.newLexParser();
        BoolNode one = lexParser.tryParse("false&&false&&false", p -> p.expr(null, 0)).root();
        assertFalse(one.evaluate());
        BoolNode two = lexParser.tryParse("true&&false&&true", p -> p.expr(null, 0)).root();
        assertFalse(two.evaluate());
        BoolNode three = lexParser.tryParse("true&&true&&true", p -> p.expr(null, 0)).root();
        assertTrue(three.evaluate());
    }

    interface BoolNode extends Evaluatable<Boolean> {
        Boolean evaluate();
    }

    class LiteralNode implements BoolNode {
        final boolean value;

        public LiteralNode(boolean value) {
            this.value = value;
        }

        @Override
        public Boolean evaluate() {
            return value;
        }
    }

    class AndNode implements BoolNode {
        final BoolNode left;
        final BoolNode right;

        public AndNode(BoolNode left, BoolNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public Boolean evaluate() {
            return left.evaluate() && right.evaluate();
        }
    }
```
If you have a language you can also get a REPL by calling `language.repl().run()`. A session looks something like this:
```
Welcome to the REPL for 'simpleCalc'
>2+3+7*8
61
>rubbish
Error:Lexing failed: 'No matching rule' @  position 1:1 (index 0), remaining text is 'rubbish'
>quit
leaving
```
##More Examples
Above given examples are available [in the repo](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/examples/ReadmeExamples.java) along with more advanced examples such as an (not extensively tested) [JSON parser](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/json/JsonLangBuilder.java)
 and a [minimal computer language](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/minilang/MiniLangTest.java).

##Installing
Install via maven central:
```xml
<dependencies>
  <dependency>
    <groupId>org.bychan</groupId>
    <artifactId>bychan-core</artifactId>
    <version>2.0.0</version>
  </dependency>
</dependencies>
```

##Feedback
Please give feedback if you are using Bychan. Create issues, send mail or whatever you prefer. I'd be glad to hear from you with or without any contributions.

##License
MIT

##Prerequisites
Bychan has no dependencies and runs on JDK 8 and above.
 
 

