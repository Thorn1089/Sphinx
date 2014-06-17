package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.VisiblePredicate;

public final class Instructions extends AbstractQuestion {
	
	public Instructions(final String id, final VisiblePredicate delegate, final String text) {
		super(id, delegate, text);
	}

}
