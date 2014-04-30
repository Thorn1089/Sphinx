package com.atomiccomics.survey.common;

import java.util.Set;

import com.atomiccomics.survey.core.Answer;
import com.google.common.collect.Sets;

public class FillInAnswer implements Answer {

	private static final Set<Class<?>> SUPPORTED_TYPES = Sets.newHashSet(String.class);
	
	private final String answer;
	
	public FillInAnswer(final String answer) {
		this.answer = answer;
	}
	
	public String getAnswerText() {
		return answer;
	}

	@Override
	public boolean supports(Class<?> type) {
		return SUPPORTED_TYPES.contains(type);
	}

	@Override
	public boolean isEqualTo(Object value) {
		return answer.equals(value);
	}

	@Override
	public boolean isLessThan(Object value) {
		throw new UnsupportedOperationException("Can't compare a string with numeric operators");
	}

	@Override
	public boolean isGreaterThan(Object value) {
		throw new UnsupportedOperationException("Can't compare a string with numeric operators");
	}
	
}
