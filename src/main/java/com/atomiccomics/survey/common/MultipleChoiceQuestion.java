package com.atomiccomics.survey.common;

import java.util.List;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.google.common.collect.ImmutableList;

public class MultipleChoiceQuestion implements Question {

	private final String id;
	
	private final VisiblePredicate delegate;
	
	private final String question;
	
	private final List<String> choices;
	
	public MultipleChoiceQuestion(final String id, final VisiblePredicate delegate,
			final String question, final List<String> choices) {
		this.id = id;
		this.delegate = delegate;
		this.question = question;
		this.choices = ImmutableList.copyOf(choices);
	}
	
	@Override
	public boolean isVisible(final SurveyBlackboard blackboard) {
		return delegate.isVisible(blackboard);
	}

	@Override
	public String id() {
		return id;
	}
	
	public String getQuestionText() {
		return question;
	}
	
	public List<String> getQuestionChoices() {
		return choices;
	}

}
