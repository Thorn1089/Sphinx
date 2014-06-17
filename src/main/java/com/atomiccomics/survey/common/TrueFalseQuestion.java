package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.VisiblePredicate;

/**
 * The {@code TrueFalseQuestion} describes a simple question which can be answered either in the affirmative
 * or negative. The actual answer option text can be customized.
 * 
 * @author Tom
 */
public final class TrueFalseQuestion extends AbstractQuestion {
	
	private final String trueText;
	
	private final String falseText;
	
	/**
	 * Creates a new {@link TrueFalseQuestion} with custom answer text for its options.
	 * @param id A {@code String} uniquely identifying this instance.
	 * @param visible A {@link VisiblePredicate} which describes whether or not this question is visible.
	 * @param questionText A {@code String} representing the actual question asked of the respondent.
	 * @param trueText A {@code String} to display as the affirmative answer.
	 * @param falseText A {@code String} to display as the negative answer.
	 */
	public TrueFalseQuestion(final String id, final VisiblePredicate delegate, 
			final String questionText, final String trueText, final String falseText) {
		super(id, delegate, questionText);
		this.trueText = trueText;
		this.falseText = falseText;
	}
	
	/**
	 * Creates a new {@link TrueFalseQuestion} which uses "true" and "false" as the answer text.
	 * @param id A {@code String} uniquely identifying this instance.
	 * @param visible A {@link VisiblePredicate} which describes whether or not this question is visible.
	 * @param questionText A {@code String} representing the actual question asked of the respondent.
	 */
	public TrueFalseQuestion(final String id, final VisiblePredicate delegate, final String questionText) {
		this(id, delegate, questionText, Boolean.TRUE.toString(), Boolean.FALSE.toString());
	}
	
	/**
	 * @return A {@code String} representing the affirmative answer text.
	 */
	public String getTrueText() {
		return trueText;
	}
	
	/**
	 * @return A {@code String} representing the negative answer text.
	 */
	public String getFalseText() {
		return falseText;
	}

}
