package com.atomiccomics.survey.acceptance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import com.atomiccomics.survey.common.MultipleChoiceQuestion;
import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.atomiccomics.survey.engine.SurveyDriver;
import com.google.common.base.Optional;

public class SampleCommandLineSurvey {
	
	private VisiblePredicate always = () -> true;
	
	private Answer wrong = (obj) -> false;
	
	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			SampleBlackboard blackboard = new SampleBlackboard();

			TrueFalseQuestion first = new TrueFalseQuestion("Q-01", always, "Is this survey awesome?");
			TrueFalseQuestion second = new TrueFalseQuestion("Q-02", always, "Did you lie on the previous question?", 
					"Yes", "No");
			
			MultipleChoiceQuestion multi = new MultipleChoiceQuestion("Q-03", always, "What is your favorite pizza?", 
					Arrays.asList("Pepperoni", "Cheese", "Sausage"));
			TrueFalseQuestion veggie = new TrueFalseQuestion("Q-04", () -> {
				return blackboard.check("Q-03").or(wrong).is("Cheese");
			}, "Are you a vegetarian?");
			Section section = new StaticSection(always, Arrays.<Visible> asList(first, second, multi, veggie));
			
			CommandLineAsker asker = new CommandLineAsker();
			asker.registerFormatter(new TrueFalseFormatter(scanner, blackboard));
			asker.registerFormatter(new MultipleChoiceFormatter(blackboard, scanner));
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			SurveyDriver driver = new SurveyDriver(section, asker,() -> flag.set(false));
			
			while(flag.get()) {
				driver.next();
			}
		}
	}
	
	public static void main(String... args) {
		SampleCommandLineSurvey survey = new SampleCommandLineSurvey();
		survey.run();
	}
	
	private static final class SampleBlackboard implements SurveyBlackboard	 {

		private final Map<String, Answer> answers = new HashMap<>();
		
		@Override
		public Optional<Answer> check(String question) {
			return Optional.fromNullable(answers.get(question));
		}

		@Override
		public void answer(String question, Answer answer) {
			answers.put(question, answer);
		}
		
	}
	
}
