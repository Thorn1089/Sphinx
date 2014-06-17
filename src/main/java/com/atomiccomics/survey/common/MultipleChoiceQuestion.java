package com.atomiccomics.survey.common;

import java.util.List;

import com.atomiccomics.survey.core.VisiblePredicate;
import com.google.common.collect.ImmutableList;

public final class MultipleChoiceQuestion extends AbstractQuestion {
	
	private final List<String> choices;
	
	public MultipleChoiceQuestion(final String id, final VisiblePredicate delegate,
			final String question, final List<String> choices) {
		super(id, delegate, question);
		this.choices = ImmutableList.copyOf(choices);
	}
	
	public List<String> getQuestionChoices() {
		return choices;
	}

}
