package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class FillInQuestion implements Question {

	private final String id;
	
	private final VisiblePredicate predicate;
	
	private final String question;
	
	public FillInQuestion(final String id, final VisiblePredicate predicate, final String question) {
		this.id = id;
		this.predicate = predicate;
		this.question = question;
	}
	
	@Override
	public boolean isVisible(final SurveyBlackboard blackboard) {
		return predicate.isVisible(blackboard);
	}

	@Override
	public String id() {
		return id;
	}

	public String getQuestionText() {
		return question;
	}
}
