package com.atomiccomics.survey.acceptance;

import java.util.Scanner;

import com.atomiccomics.survey.common.Instructions;
import com.atomiccomics.survey.core.Question;

public class InstructionsFormatter implements CommandLineQuestionFormatter {

	private final Scanner scanner;
	
	public InstructionsFormatter(final Scanner scanner) {
		this.scanner = scanner;
	}
	
	@Override
	public boolean handles(Class<? extends Question> c) {
		return Instructions.class.equals(c);
	}

	@Override
	public void format(Question question) {
		Instructions q = (Instructions)question;
		System.out.printf(q.getQuestionText());
		System.out.printf("\n\n");
		scanner.nextLine();
	}

}
