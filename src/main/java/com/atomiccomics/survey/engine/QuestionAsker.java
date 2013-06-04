package com.atomiccomics.survey.engine;

import com.atomiccomics.survey.core.Question;

/**
 * The {@code QuestionAsker} interface represents any component which wishes to receive
 * the latest {@link Question} in the survey for the purposes of asking the respondent.
 * 
 * @author Tom
 */
public interface QuestionAsker {

	/**
	 * Asks the given question of the respondent in some fashion.
	 * @param question The current {@link Question} to ask.
	 */
	void ask(Question question);
	
}
