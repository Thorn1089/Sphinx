package com.atomiccomics.survey;

/**
 * <p>
 * A {@code Question} represents any sort of query to the survey taker which
 * requires a response. Questions are not limited in any way as to format -- they
 * may be multiple choice, free response, or incorporate custom data formats.
 * </p>
 * <p>
 * Questions have a unique identifier so that their answers can be correlated with
 * them. They also have the notion of <em>visibility</em> -- certain questions may not
 * be applicable for some respondents, and others may become required based on previous
 * answers.
 * </p>
 * @author Tom
 */
public interface Question {

	/**
	 * @return A unique {@code String} identifying this {@link Question}.
	 */
	String id();
	
	/**
	 * @return A {@code boolean} describing whether or not this {@link Question} should be shown to the
	 * respondent.
	 */
	boolean isVisible();
	
}
