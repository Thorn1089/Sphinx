package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Answer;

/**
 * The {@code TrueFalseAnswer} represents an {@link Answer} which only has two possible values.
 * 
 * @author Tom
 */
public class TrueFalseAnswer implements Answer {

	private final boolean answer;
	
	/**
	 * Creates a new {@link TrueFalseAnswer} which wraps the given boolean.
	 * @param answer A {@code boolean} representing how the associated question was answered.
	 */
	public TrueFalseAnswer(final boolean answer) {
		this.answer = answer;
	}
	
	/**
	 * @return Whether the question was answered true or false.
	 */
	public boolean get() {
		return answer;
	}
	
}
