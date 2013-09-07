package com.atomiccomics.survey.common;

import java.util.Collections;
import java.util.List;

import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.atomiccomics.survey.core.VisiblePredicate;

/**
 * The {@code StaticSection} represents a {@link Section} whose attributes are fully set at creation.
 * It does not dynamically create any child nodes at runtime.
 * 
 * @author Tom
 */
public class StaticSection implements Section {

	private final VisiblePredicate delegate;
	
	private final List<Visible> children;
	
	/**
	 * Creates a new {@link StaticSection} instance.
	 * @param delegate A {@link VisiblePredicate} which determines if this section is visible.
	 * @param children A {@code List} of {@link Visible} children belonging to this section.
	 */
	public StaticSection(final VisiblePredicate delegate, final List<Visible> children) {
		this.delegate = delegate;
		this.children = Collections.unmodifiableList(children);
	}
	
	@Override
	public boolean isVisible() {
		return delegate.isVisible();
	}

	@Override
	public List<Visible> children() {
		return children;
	}

	@Override
	public void prepareSection() { }

}
