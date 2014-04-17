package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Answer;

public class FillInAnswer implements Answer {

	private final String answer;
	
	public FillInAnswer(final String answer) {
		this.answer = answer;
	}
	
	public String getAnswerText() {
		return answer;
	}
	
}
