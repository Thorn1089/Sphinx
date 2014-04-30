package com.atomiccomics.survey.core;

/**
 * <p>
 * {@code Answer} is a marker interface used to designate certain classes as representing a respondent's
 * input for a particular {@link Question}. There are no restrictions on the type of data that an answer
 * can contain.
 * </p>
 * @author Tom
 *
 */
public interface Answer {
	
	boolean supports(Class<?> type);
	
	boolean isEqualTo(Object value);
	
	boolean isLessThan(Object value);
	
	boolean isGreaterThan(Object value);
	
}
