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

Maturity
--------
This is the first release of Bychan. For this release I tried to focus on expressions, so functionality for statements
 might be a bit lacking. But there is a (not entirely correct) JSON parser implementation and a miniature language.

