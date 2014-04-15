package com.atomiccomics.survey.acceptance;

import java.util.List;
import java.util.Scanner;

import com.atomiccomics.survey.common.MultipleChoiceAnswer;
import com.atomiccomics.survey.common.MultipleChoiceQuestion;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class MultipleChoiceFormatter implements CommandLineQuestionFormatter {

	private final SurveyBlackboard blackboard;
	
	private final Scanner scanner;
	
	public MultipleChoiceFormatter(final SurveyBlackboard blackboard, final Scanner scanner) {
		this.blackboard = blackboard;
		this.scanner = scanner;
	}
	
	@Override
	public boolean handles(Class<? extends Question> c) {
		return MultipleChoiceQuestion.class.equals(c);
	}

	@Override
	public void format(Question question) {
		MultipleChoiceQuestion q = (MultipleChoiceQuestion)question;
		//Write the question text
		System.out.printf(q.getQuestionText());
		System.out.printf("\n\n");
		//Provide options for answers
		int option = 0;
		List<String> questionChoices = q.getQuestionChoices();
		for(String choice : questionChoices) {
			System.out.printf("(%d) : %S\n", ++option, choice);
		}
		String answer = scanner.nextLine();
		blackboard.answer(q.id(), new MultipleChoiceAnswer(
				questionChoices.get(parseAnswer(answer) - 1)));
	}
	
	private int parseAnswer(String text) {
		return Integer.parseInt(text);
	}

}
