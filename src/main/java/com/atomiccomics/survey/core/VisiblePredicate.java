package com.atomiccomics.survey.core;

import com.atomiccomics.survey.engine.SurveyBlackboard;

/**
 * The {@code VisiblePredicate} defines a functor that allows {@link Visible} objects to delegate their
 * visibility to another type at runtime.
 * 
 * @author Tom
 */
public interface VisiblePredicate {

	/**
	 * @return True if the conditions are correct to display the associated object; false otherwise.
	 * @param blackboard A {@link SurveyBlackboard} representing the current state of the survey.
	 * @see {@link Visible#isVisible(SurveyBlackboard)}
	 */
	boolean isVisible(SurveyBlackboard blackboard);
	
}
