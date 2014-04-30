package com.atomiccomics.survey.acceptance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;





import com.atomiccomics.survey.common.FillInQuestion;
import com.atomiccomics.survey.common.MultipleChoiceAnswer;
import com.atomiccomics.survey.common.MultipleChoiceQuestion;
import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.common.TrueFalseAnswer;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.atomiccomics.survey.engine.SurveyDriver;

public class SampleCommandLineSurvey {
	
	private VisiblePredicate always = (bb) -> true;
	
	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			SampleBlackboard blackboard = new SampleBlackboard();

			TrueFalseQuestion first = new TrueFalseQuestion("Q-01", always, "Is this survey awesome?");
			TrueFalseQuestion second = new TrueFalseQuestion("Q-02", always, "Did you lie on the previous question?", 
					"Yes", "No");
			
			MultipleChoiceQuestion multi = new MultipleChoiceQuestion("Q-03", always, "What is your favorite pizza?", 
					Arrays.asList("Pepperoni", "Cheese", "Sausage"));
			TrueFalseQuestion veggie = new TrueFalseQuestion("Q-04", (bb) -> {
				return bb.check("Q-03").map((answer) -> {
					final MultipleChoiceAnswer mca = (MultipleChoiceAnswer)answer;
					return mca.getChoice().equals("Cheese");
				}).orElse(false);
			}, "Are you a vegetarian?");
			Section intro = new StaticSection(always, Arrays.asList(first, second, multi, veggie));
			
			FillInQuestion length = new FillInQuestion("V-01", always, "How long have you been a vegetarian?");
			Section vegetarian = new StaticSection((bb) -> {
				return bb.check("Q-04").map((answer) -> {
					final TrueFalseAnswer tfa = (TrueFalseAnswer)answer;
					return tfa.get();
				}).orElse(false);
			}, Arrays.asList(length));
			
			Section survey = new StaticSection(always, Arrays.asList(intro, vegetarian));
			
			CommandLineAsker asker = new CommandLineAsker();
			asker.registerFormatter(new TrueFalseFormatter(scanner, blackboard));
			asker.registerFormatter(new MultipleChoiceFormatter(blackboard, scanner));
			asker.registerFormatter(new FillInQuestionFormatter(blackboard, scanner));
			
			final AtomicBoolean flag = new AtomicBoolean(true);
			
			SurveyDriver driver = new SurveyDriver(survey, asker,() -> flag.set(false), blackboard);
			
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
			return Optional.ofNullable(answers.get(question));
		}

		@Override
		public void answer(String question, Answer answer) {
			answers.put(question, answer);
		}
		
	}
	
}
