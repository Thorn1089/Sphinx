package com.atomiccomics.survey.acceptance;

import java.util.Scanner;

import com.atomiccomics.survey.common.FillInTextAnswer;
import com.atomiccomics.survey.common.FillInTextQuestion;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class FillInTextQuestionFormatter implements CommandLineQuestionFormatter {

	private final SurveyBlackboard blackboard;
	
	private final Scanner scanner;
	
	public FillInTextQuestionFormatter(final SurveyBlackboard blackboard, final Scanner scanner) {
		this.blackboard = blackboard;
		this.scanner = scanner;
	}
	
	@Override
	public boolean handles(Class<? extends Question> c) {
		return FillInTextQuestion.class.equals(c);
	}

	@Override
	public void format(Question question) {
		final FillInTextQuestion q = (FillInTextQuestion)question;
		//Write the question text
		System.out.printf(q.getQuestionText());
		System.out.printf("\n\n");
		//Read the answer
		String answer = scanner.nextLine();
		blackboard.answer(q.id(), new FillInTextAnswer(answer));
	}

}
