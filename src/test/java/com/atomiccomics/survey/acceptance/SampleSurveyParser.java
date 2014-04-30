package com.atomiccomics.survey.acceptance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.atomiccomics.SphinxLexer;
import com.atomiccomics.SphinxParser;
import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.atomiccomics.survey.engine.SurveyDriver;
import com.atomiccomics.survey.parser.SurveyBuilder;

public class SampleSurveyParser {

	public static void main(String[] args) throws IOException {
		ANTLRFileStream input = new ANTLRFileStream("src/test/resources/sample.sphx");
		SphinxLexer lexer = new SphinxLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SphinxParser parser = new SphinxParser(tokens);
		ParseTree tree = parser.survey();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		SurveyBuilder listener = new SurveyBuilder();
		walker.walk(listener, tree);
		
		try (Scanner scanner = new Scanner(System.in)) {
			final SurveyBlackboard blackboard = new SurveyBlackboard() {
				private final Map<String, Answer> answers = new HashMap<>();
				
				@Override
				public Optional<Answer> check(String question) {
					return Optional.ofNullable(answers.get(question));
				}

				@Override
				public void answer(String question, Answer answer) {
					answers.put(question, answer);
				}
			};
			
			CommandLineAsker asker = new CommandLineAsker();
			asker.registerFormatter(new TrueFalseFormatter(scanner, blackboard));
			asker.registerFormatter(new MultipleChoiceFormatter(blackboard, scanner));
			asker.registerFormatter(new FillInQuestionFormatter(blackboard, scanner));
			asker.registerFormatter(new InstructionsFormatter(scanner));
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			SurveyDriver driver = new SurveyDriver(listener.getSurvey(), asker,() -> flag.set(false), blackboard);
			
			while(flag.get()) {
				driver.next();
			}
		}
		
	}

}
