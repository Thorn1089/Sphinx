package com.atomiccomics.survey.core;

/**
 * The {@code Section} interface defines an aggregation of related {@link Question questions}.
 * Sections may offer additional functionality. For example, some sections may act as templates, which produce
 * new questions dynamically at runtime based off of previous {@link Answer answers}.
 * 
 * @author Tom
 */
public interface Section extends Visible {
	
	/**
	 * @return True if this {@link Section} has more {@link Question#isVisible() visible} questions;
	 * false otherwise.
	 */
	boolean hasNext();
	
	/**
	 * @return The next {@link Question} in this section which is visible.
	 * @throw NoSuchElementException If there are no remaining visible questions.
	 */
	Question next();
	
	/**
	 * @return True if there are {@link Question#isVisible() visible} questions which were already returned
	 * by this {@link Section}; false otherwise.
	 */
	boolean hasPrevious();
	
	/**
	 * @return The {@link Question} returned by the immediately prior call to {@link #next()}.
	 * @throw NoSuchElementException If there are no previously visible questions.
	 */
	Question previous();
	
}
