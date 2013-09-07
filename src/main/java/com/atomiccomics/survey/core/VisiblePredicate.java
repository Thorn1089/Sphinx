package com.atomiccomics.survey.core;

/**
 * The {@code VisiblePredicate} defines a functor that allows {@link Visible} objects to delegate their
 * visibility to another type at runtime.
 * 
 * @author Tom
 */
public interface VisiblePredicate {

	/**
	 * @return True if the conditions are correct to display the associated object; false otherwise.
	 * @see {@link Visible#isVisible()}
	 */
	boolean isVisible();
	
}
