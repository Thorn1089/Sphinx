/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar Sphinx;

NEWLINE: ('\r\n'|'\r'|'\n');

TAB: '\t';

SPACE: ' ';

IF: 'if';

IS: 'is';

LESS: 'less than';

GREATER: 'greater than';

BOOLEAN: 'true'
       | 'false';

CONDITION: 'and'
         | 'or';

fragment LETTER: [a-zA-Z];

fragment NUMBER: [0-9];

fragment SEPARATOR: ('_'|'-');

TYPE: LETTER+;

INT: NUMBER+;

ID: (LETTER|NUMBER)+ (SEPARATOR (LETTER|NUMBER)+)+;

STRING: '"' .*? '"';

SECTION_PROLOGUE: NEWLINE? 'Start section ';

SECTION_EPILOGUE: 'End section' NEWLINE?;

QUESTION_PROLOGUE: 'Ask ';

ANSWER_PROLOGUE: 'Answer with';

survey: section+;

section: SECTION_PROLOGUE identifier (SPACE predicate)? NEWLINE 
         (question NEWLINE?)+ 
         SECTION_EPILOGUE;

question: QUESTION_PROLOGUE TYPE SPACE identifier (SPACE predicate)? NEWLINE 
          question_line*
          answer?
        ;

question_line: TAB STRING NEWLINE;

identifier: ID | TYPE;

answer: ANSWER_PROLOGUE NEWLINE
        answer_line+;

answer_line: TAB STRING NEWLINE;

predicate: IF SPACE identifier SPACE IS (SPACE modifier)? SPACE expected_answer (SPACE CONDITION SPACE predicate)?;

modifier: LESS | GREATER;

expected_answer: (BOOLEAN|STRING|INT);