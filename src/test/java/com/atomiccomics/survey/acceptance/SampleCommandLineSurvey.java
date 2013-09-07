package com.atomiccomics.survey.acceptance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.atomiccomics.survey.engine.SurveyDriver;
import com.atomiccomics.survey.engine.SurveyTerminationListener;
import com.google.common.base.Optional;

public class SampleCommandLineSurvey {
	
	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			TrueFalseQuestion first = new TrueFalseQuestion("Q-01", new AlwaysVisible(), "Is this survey awesome?");
			TrueFalseQuestion second = new TrueFalseQuestion("Q-02", new AlwaysVisible(), "Did you lie on the previous question?", 
					"Yes", "No");
			Section section = new StaticSection(new AlwaysVisible(), Arrays.<Visible> asList(first, second));
			
			CommandLineAsker asker = new CommandLineAsker();
			asker.registerFormatter(new TrueFalseFormatter(scanner, new SampleBlackboard()));
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			SurveyDriver driver = new SurveyDriver(section, asker, new SurveyTerminationListener() {
				@Override
				public void surveyEnded() {
					flag.set(false);
				}
			});
			
			while(flag.get()) {
				driver.next();
			}
		}
	}
	
	public static void main(String... args) {
		SampleCommandLineSurvey survey = new SampleCommandLineSurvey();
		survey.run();
	}
	
	private static final class AlwaysVisible implements VisiblePredicate {
		public boolean isVisible() { return true; }
	}
	
	private static final class SampleBlackboard implements SurveyBlackboard	 {

		private final Map<String, Answer> answers = new HashMap<>();
		
		@Override
		public Optional<? extends Answer> check(String question) {
			return Optional.fromNullable(answers.get(question));
		}

		@Override
		public void answer(String question, Answer answer) {
			answers.put(question, answer);
		}
		
	}
	
}
