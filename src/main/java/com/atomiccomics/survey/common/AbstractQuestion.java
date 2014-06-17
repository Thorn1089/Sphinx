package com.atomiccomics.survey.common;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.VisiblePredicate;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public abstract class AbstractQuestion implements Question {

	private final String id;
	
	private final VisiblePredicate predicate;
	
	private final String questionText;
	
	public AbstractQuestion(final String id, final VisiblePredicate predicate, final String questionText) {
		this.id = id;
		this.predicate = predicate;
		this.questionText = questionText;
	}
	
	@Override
	public final boolean isVisible(SurveyBlackboard blackboard) {
		return predicate.isVisible(blackboard);
	}

	@Override
	public final String id() {
		return id;
	}
	
	public final String getQuestionText() {
		return questionText;
	}

}
