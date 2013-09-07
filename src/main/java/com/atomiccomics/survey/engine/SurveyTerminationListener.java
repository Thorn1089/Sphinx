package com.atomiccomics.survey.engine;

/**
 * The {@code SurveyTerminationListener} allows other classes to react when all
 * questions in a survey have been asked.
 * 
 * @author Tom
 */
public interface SurveyTerminationListener {

	/**
	 * Called when all visible questions in a survey have been asked.
	 */
	void surveyEnded();
	
}
