package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.VisiblePredicate;

public final class FillInNumberQuestion extends AbstractQuestion {

	public FillInNumberQuestion(final String id, final VisiblePredicate delegate, final String questionText) {
		super(id, delegate, questionText);
	}
}
