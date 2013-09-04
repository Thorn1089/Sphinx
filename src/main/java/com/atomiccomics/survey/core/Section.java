package com.atomiccomics.survey.core;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * The {@code Section} interface defines an aggregation of related {@link Question questions}.
 * Sections may offer additional functionality. For example, some sections may act as templates, which produce
 * new questions dynamically at runtime based off of previous {@link Answer answers}.
 * 
 * @author Tom
 */
public interface Section extends Visible {
	
	/**
	 * Returns the {@code List} of children of this node in the survey; may be an empty list but never null.
	 * @return The child nodes of this node. Can be a heterogenous mix of {@code Sections} and {@code Questions}.
	 */
	@Nonnull List<Visible> children();
	
}
