package com.atomiccomics.survey.common;

import java.util.Set;

import com.atomiccomics.survey.core.Answer;
import com.google.common.collect.Sets;

/**
 * The {@code TrueFalseAnswer} represents an {@link Answer} which only has two possible values.
 * 
 * @author Tom
 */
public class TrueFalseAnswer implements Answer {

	private static final Set<Class<?>> SUPPORTED_TYPES = Sets.newHashSet(String.class, Boolean.class);
	
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

	@Override
	public boolean supports(Class<?> type) {
		return SUPPORTED_TYPES.contains(type);
	}

	@Override
	public boolean isEqualTo(Object value) {
		if(value instanceof String) {
			return answer == Boolean.parseBoolean((String)value);
		} else if(value instanceof Boolean) {
			return answer == (Boolean)value;
		}
		
		return false;
	}

	@Override
	public boolean isLessThan(Object value) {
		throw new UnsupportedOperationException("Can't compare a boolean with numeric operators");
	}

	@Override
	public boolean isGreaterThan(Object value) {
		throw new UnsupportedOperationException("Can't compare a boolean with numeric operators");
	}
	
}
