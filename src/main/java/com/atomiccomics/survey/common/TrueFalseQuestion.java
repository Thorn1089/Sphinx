package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Question;
import com.google.common.base.Predicate;

/**
 * The {@code TrueFalseQuestion} describes a simple question which can be answered either in the affirmative
 * or negative. The actual answer option text can be customized.
 * 
 * @author Tom
 */
public class TrueFalseQuestion implements Question {

	private final String id;
	
	private final Predicate<Void> visible;
	
	private final String questionText;
	
	private final String trueText;
	
	private final String falseText;
	
	/**
	 * Creates a new {@link TrueFalseQuestion} with custom answer text for its options.
	 * @param id A {@code String} uniquely identifying this instance.
	 * @param visible A {@link Predicate} which describes whether or not this question is visible.
	 * @param questionText A {@code String} representing the actual question asked of the respondent.
	 * @param trueText A {@code String} to display as the affirmative answer.
	 * @param falseText A {@code String} to display as the negative answer.
	 */
	public TrueFalseQuestion(final String id, final Predicate<Void> visible, 
			final String questionText, final String trueText, final String falseText) {
		this.id = id;
		this.visible = visible;
		this.questionText = questionText;
		this.trueText = trueText;
		this.falseText = falseText;
	}
	
	/**
	 * Creates a new {@link TrueFalseQuestion} which uses "true" and "false" as the answer text.
	 * @param id A {@code String} uniquely identifying this instance.
	 * @param visible A {@link Predicate} which describes whether or not this question is visible.
	 * @param questionText A {@code String} representing the actual question asked of the respondent.
	 */
	public TrueFalseQuestion(final String id, final Predicate<Void> visible, final String questionText) {
		this(id, visible, questionText, Boolean.TRUE.toString(), Boolean.FALSE.toString());
	}
	
	@Override
	public String id() {
		return id;
	}

	@Override
	public boolean isVisible() {
		return visible.apply(null);
	}
	
	/**
	 * @return A {@code String} representing the question.
	 */
	public String getQuestionText() {
		return questionText;
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
