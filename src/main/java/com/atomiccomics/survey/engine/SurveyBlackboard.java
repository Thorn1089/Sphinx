package com.atomiccomics.survey.engine;

import com.atomiccomics.survey.core.Answer;
import com.google.common.base.Optional;

/**
 * The {@code SurveyBlackboard} represents the current state of the survey as a blackboard system; that is,
 * an object examinable by all other agents in the system. In this case, it can be queried by other
 * questions or sections to determine their visibility, or how they will present themselves.
 * 
 * @author Tom
 */
public interface SurveyBlackboard {

	/**
	 * Retrieves the answer to a previous question, if it has already been asked.
	 * @param question A {@code String} corresponding to a 
	 * {@link com.atomiccomics.survey.core.Question#id question ID}.
	 * @return An {@link Optional} reference to an {@link Answer}. The type of answer depends on
	 * the type of question identified.
	 */
	Optional<? extends Answer> check(String question);
	
}
