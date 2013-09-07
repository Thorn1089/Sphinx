package com.atomiccomics.survey.acceptance;

import com.atomiccomics.survey.core.Question;

/**
 * The {@code CommandLineQuestionFormatter} defines a standard interface for translating a {@link Question}
 * to formatted text that can be displayed on a console.
 * 
 * @author Tom
 */
public interface CommandLineQuestionFormatter {

	/**
	 * Determines whether this formatter can format questions of the given type.
	 * @param c A {@link Class} which represents a subtype of {@link Question}.
	 * @return True if this formatter can handle the given question type; false otherwise.
	 */
	boolean handles(Class<? extends Question> c);
	
	/**
	 * Writes a specific {@link Question} subtype to the standard text output of the platform.
	 * @param question A subtype {@code T} of {@code Question} to write as text.
	 */
	void format(Question question);
	
}
