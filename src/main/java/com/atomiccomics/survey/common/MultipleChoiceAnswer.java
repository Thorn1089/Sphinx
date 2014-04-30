package com.atomiccomics.survey.common;

import java.util.Set;

import com.atomiccomics.survey.core.Answer;
import com.google.common.collect.Sets;

public class MultipleChoiceAnswer implements Answer {

	private static final Set<Class<?>> SUPPORTED_TYPES = Sets.newHashSet(String.class);
	
	private final String choice;
	
	public MultipleChoiceAnswer(final String choice) {
		this.choice = choice;
	}
	
	public String getChoice() {
		return choice;
	}

	@Override
	public boolean supports(Class<?> type) {
		return SUPPORTED_TYPES.contains(type);
	}

	@Override
	public boolean isEqualTo(Object value) {
		return choice.equals(value);
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
