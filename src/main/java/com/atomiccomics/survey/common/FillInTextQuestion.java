package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.VisiblePredicate;

public final class FillInTextQuestion extends AbstractQuestion {
	
	public FillInTextQuestion(final String id, final VisiblePredicate predicate, final String question) {
		super(id, predicate, question);
	}
}
