/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar Sphinx;

NEWLINE: ('\r\n'|'\r'|'\n');

TAB: '\t';

SPACE: ' ';

PERIOD: '.';

fragment LETTER: [a-zA-Z];

fragment NUMBER: [0-9];

fragment SEPARATOR: ('_'|'-');

TYPE: LETTER+;

ID: (LETTER|NUMBER)+ (SEPARATOR (LETTER|NUMBER)+)+;

STRING: '"' .*? '"';

SECTION_PROLOGUE: NEWLINE? 'Start section ';

SECTION_EPILOGUE: 'End section' NEWLINE?;

QUESTION_PROLOGUE: 'Ask ';

ANSWER_PROLOGUE: 'Answer with';

survey: section+;

section: SECTION_PROLOGUE identifier NEWLINE 
         (question NEWLINE?)+ 
         SECTION_EPILOGUE;

question: QUESTION_PROLOGUE TYPE SPACE identifier NEWLINE 
          question_line*
          answer?
        ;

question_line: TAB STRING NEWLINE;

identifier: ID | TYPE;

answer: ANSWER_PROLOGUE NEWLINE
        answer_line+;

answer_line: TAB STRING NEWLINE;