package com.atomiccomics.survey.engine;

import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.core.Question;

/**
 * <p>
 * The {@code AnswerListener} interface allows classes to receive updates when certain questions are answered.
 * This is useful when questions must have their visibility toggled, or new questions dynamically created from
 * the answers to previous questions.
 * </p>
 * <p>
 * Due to limitations in Java generics and type inference, implementors will have to manually cast arguments
 * to their known types.
 * </p>
 * @author Tom
 */
public interface AnswerListener {

	/**
	 * Called when a question is answered. 
	 * TODO Describe the registration mechanism used to narrow scope
	 * @param question The {@link Question} which was answered by the respondent.
	 * @param answer The {@link Answer} provided by the respondent.
	 */
	void answered(Question question, Answer answer);
	
}
