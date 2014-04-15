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

	/**
	 * Determines if the given object represents the same answer as this object.
	 * @param candidate An object representing an answer to a question.
	 * @return True if the given object represents the same answer data as this object.
	 */
	boolean is(Object candidate);
	
}
