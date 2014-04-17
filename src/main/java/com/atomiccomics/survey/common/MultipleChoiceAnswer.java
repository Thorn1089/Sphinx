package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Answer;

public class MultipleChoiceAnswer implements Answer {

	private final String choice;
	
	public MultipleChoiceAnswer(final String choice) {
		this.choice = choice;
	}
	
	public String getChoice() {
		return choice;
	}
	
}
