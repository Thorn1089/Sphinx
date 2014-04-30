package com.atomiccomics.survey.core;

import com.atomiccomics.survey.engine.SurveyBlackboard;

/**
 * The {@code Visible} interface defines a high-level node which may or may not be eligible to be traversed
 * when the survey tree is being walked.
 * 
 * @author Tom
 */
public interface Visible {

	/**
	 * Determines whether the given object is 'visible' and eligible to be traversed, based on some
	 * internal criteria.
	 * @param blackboard A {@link SurveyBlackboard} representing the current state of the survey.
	 * @return True if this node is visible and should be traversed; false if it should be skipped.
	 */
	boolean isVisible(SurveyBlackboard blackboard);
	
}
