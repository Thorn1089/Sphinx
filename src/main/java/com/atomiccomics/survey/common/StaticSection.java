package com.atomiccomics.survey.common;

import java.util.List;
import java.util.ListIterator;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.google.common.base.Predicate;

/**
 * The {@code StaticSection} represents a {@link Section} whose content is fixed when it is created.
 * While visibility of the section and questions may change, the questions themselves and their
 * order cannot.
 * 
 * @author Tom
 */
public class StaticSection implements Section {

	private final ListIterator<Question> questions;
	
	private final Predicate<Void> visible;
	
	/**
	 * Creates a new {@link StaticSection} which will ask the given questions in order.
	 * @param questions A {@code List} of {@link Question} objects.
	 * @param visible A {@link Predicate} determining whether or not this section is visible.
	 */
	public StaticSection(final List<Question> questions, final Predicate<Void> visible) {
		this.questions = questions.listIterator();
		this.visible = visible;
	}
	
	@Override
	public boolean isVisible() {
		return visible.apply(null);
	}

	@Override
	public boolean hasNext() {
		return questions.hasNext();
	}

	@Override
	public Question next() {
		return questions.next();
	}

	@Override
	public boolean hasPrevious() {
		return questions.hasPrevious();
	}

	@Override
	public Question previous() {
		return questions.previous();
	}

}
