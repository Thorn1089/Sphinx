package com.atomiccomics.survey.engine;

/**
 * The {@code QuestionCompletionListener} allows classes to subscribe for notifications
 * when a given question has changed states between being incomplete (without an {@link Answer})
 * and complete.
 * 
 * @author Tom
 */
public interface QuestionCompletionListener {

	/**
	 * Called whenever the completion status of a displayed question changes.
	 * @param complete True if there is enough information given to construct an {@link Answer};
	 * false otherwise.
	 */
	void isCompleted(final boolean complete);
	
}
