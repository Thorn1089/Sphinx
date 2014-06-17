package com.atomiccomics.survey.acceptance;

import java.util.Scanner;

import com.atomiccomics.survey.common.FillInNumberAnswer;
import com.atomiccomics.survey.common.FillInNumberQuestion;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class FillInNumberQuestionFormatter implements CommandLineQuestionFormatter {

	private final SurveyBlackboard blackboard;
	
	private final Scanner scanner;
	
	public FillInNumberQuestionFormatter(final SurveyBlackboard blackboard, final Scanner scanner) {
		this.blackboard = blackboard;
		this.scanner = scanner;
	}
	
	@Override
	public boolean handles(Class<? extends Question> c) {
		return FillInNumberQuestion.class.equals(c);
	}
	
	@Override
	public void format(Question question) {
		final FillInNumberQuestion q = (FillInNumberQuestion)question;
		
		System.out.printf(q.getQuestionText());
		System.out.printf("\n\n");
		
		String answer = scanner.nextLine();
		blackboard.answer(q.id(), new FillInNumberAnswer(Integer.parseInt(answer)));
	}
}
