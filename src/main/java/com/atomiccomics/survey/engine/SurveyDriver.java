package com.atomiccomics.survey.engine;

import java.util.List;
import java.util.ListIterator;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;

/**
 * The {@code SurveyDriver} wraps several concepts from the core and engine packages to present a high-level
 * model interface for the survey as a whole, such as allowing the view layer to request updates by navigating
 * forward and back.
 * 
 * @author Tom
 */
public final class SurveyDriver {

	private final ListIterator<Section> sections;
	
	private QuestionAsker asker = new NullAsker();
	
	private Section current;
	
	public SurveyDriver(final List<Section> sections) {
		assert(sections.size() > 0);
		this.sections = sections.listIterator();
		this.current = this.sections.next();
	}
	
	/**
	 * Registers the given {@link QuestionAsker} to receive updates from this {@link SurveyDriver}. Replaces
	 * any existing asker.
	 * @param asker A {@code QuestionAsker} which will provide interactive capabilities with the respondent.
	 */
	public void registerAsker(final QuestionAsker asker) {
		this.asker = asker;
	}
	
	public void next() {
		if(current.hasNext()) {
			asker.ask(current.next());
		} else if(sections.hasNext()) {
			current = sections.next();
			next();
		}
		//TODO send end of survey message
	}
	
	public void previous() {
		if(current.hasPrevious()) {
			asker.ask(current.previous());
		} else if(sections.hasPrevious()) {
			current = sections.previous();
			previous();
		}
		//TODO assertion error? Previous shouldn't be invokable at beginning
	}
	
	private static class NullAsker implements QuestionAsker {
		@Override
		public void ask(Question question) { }
	}
	
}
