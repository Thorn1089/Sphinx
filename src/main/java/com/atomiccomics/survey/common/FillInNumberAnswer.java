package com.atomiccomics.survey.common;

import java.math.BigInteger;
import java.util.Set;

import com.atomiccomics.survey.core.Answer;
import com.google.common.collect.Sets;

public class FillInNumberAnswer implements Answer {

	private static final Set<Class<?>> SUPPORTED_TYPES = Sets.newHashSet(Integer.class, BigInteger.class, String.class);
	
	private final int number;
	
	public FillInNumberAnswer(final int number) {
		this.number = number;
	}
	
	@Override
	public boolean supports(Class<?> type) {
		return SUPPORTED_TYPES.contains(type);
	}

	@Override
	public boolean isEqualTo(Object value) {
		if(value instanceof Integer) {
			return number == ((Integer)value).intValue();
		} else if(value instanceof BigInteger) {
			return number == ((BigInteger)value).intValue();
		} else if(value instanceof String) {
			return number == Integer.parseInt((String) value);
		}
		return false;
	}

	@Override
	public boolean isLessThan(Object value) {
		if(value instanceof Integer) {
			return number < ((Integer)value).intValue();
		} else if(value instanceof BigInteger) {
			return number < ((BigInteger)value).intValue();
		} else if(value instanceof String) {
			return number < Integer.parseInt((String) value);
		}
		return false;
	}

	@Override
	public boolean isGreaterThan(Object value) {
		if(value instanceof Integer) {
			return number > ((Integer)value).intValue();
		} else if(value instanceof BigInteger) {
			return number > ((BigInteger)value).intValue();
		} else if(value instanceof String) {
			return number > Integer.parseInt((String) value);
		}
		return false;
	}

}
