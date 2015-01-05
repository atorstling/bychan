Bychan
======
A Pratt (TDOP) Expression Parser Library for Java

About
-----
Bychan is a library for TDOP parsing. TDOP is essentially recursive descent with a clever way
of incorporating shunting-yard for the handling of expressions. It is a surprisingly powerful technique
despite its relative simplicity.

Bychan does not generate code, niether the parser itself nor the AST. It is entirely runtime based.
Bychan tries to stay out of your way as much as possible.
What it does do is to help you with the boring bits: lexing, error handling and writing REPLs.

Show me the code
----------------
Sure! Let's start with the classic calculator. Let's throw in

Maturity
--------
This is the first release of Bychan. For this release I tried to focus on expressions, so functionality for statements
 might be a bit lacking.
 But there is a (not entirely correct) [JSON parser](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/json/JsonLangBuilder.java)
 implementation and a [minimal computer language](https://github.com/atorstling/bychan/blob/master/src/test/java/org/bychan/core/langs/minilang/MiniLangTest.java).

