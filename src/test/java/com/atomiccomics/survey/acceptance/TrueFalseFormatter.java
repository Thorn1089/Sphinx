package com.atomiccomics.survey.acceptance;

import java.util.Scanner;

import com.atomiccomics.survey.common.TrueFalseAnswer;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class TrueFalseFormatter implements CommandLineQuestionFormatter {
	
	private final SurveyBlackboard blackboard;
	
	private final Scanner scanner;
	
	public TrueFalseFormatter(Scanner scanner, SurveyBlackboard blackboard) {
		this.blackboard = blackboard;
		this.scanner = scanner;
	}
	
	@Override
	public boolean handles(Class<? extends Question> c) {
		return TrueFalseQuestion.class.equals(c);
	}

	@Override
	public void format(Question question) {
		TrueFalseQuestion q = (TrueFalseQuestion)question;
		//Write the question text
		System.out.printf(q.getQuestionText());
		System.out.printf("\n\n");
		//Provide options for answers
		System.out.printf("(T) : %S\n", q.getTrueText());
		System.out.printf("(F) : %S\n", q.getFalseText());
		//Read in the answer
		String answer = scanner.nextLine();
		blackboard.answer(q.id(), new TrueFalseAnswer(parseAnswer(answer)));
	}
	
	private boolean parseAnswer(String text) {
		if(text.equalsIgnoreCase("T")) {
			return true;
		} else if(text.equalsIgnoreCase("F")) {
			return false;
		} else {
			throw new IllegalArgumentException(text + " is not an acceptable answer");
		}
	}

}
