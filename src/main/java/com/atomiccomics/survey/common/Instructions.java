package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class Instructions implements Question {

	private final VisiblePredicate delegate;
	
	private final String id;
	
	private final String text;
	
	public Instructions(final String id, final VisiblePredicate delegate, final String text) {
		this.id = id;
		this.delegate = delegate;
		this.text = text;
	}
	
	@Override
	public boolean isVisible(final SurveyBlackboard blackboard) {
		return delegate.isVisible(blackboard);
	}

	@Override
	public String id() {
		return id;
	}
	
	public String getText() {
		return text;
	}

}
