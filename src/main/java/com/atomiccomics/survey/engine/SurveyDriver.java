package com.atomiccomics.survey.engine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ListIterator;

import javax.annotation.Nonnull;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.google.common.collect.Lists;


/**
 * The {@code SurveyDriver} wraps several concepts from the core and engine packages to present a high-level
 * model interface for the survey as a whole, such as allowing the view layer to request updates by navigating
 * forward and back. It also allows the host application to receive events, such as when the survey ends due
 * to there being no more questions.
 * 
 * @author Tom
 */
public final class SurveyDriver {
	
	private static enum Direction { FORWARD, BACKWARD }
	
	private final Deque<ListIterator<Visible>> stack = new ArrayDeque<>();
	
	private final QuestionAsker asker;
	
	private final SurveyTerminationListener listener;
	
	private final SurveyBlackboard blackboard;
	
	private ListIterator<Visible> curr;
	
	private Question lastAsked = null;
	
	/**
	 * Creates a new {@link SurveyDriver} to navigate a given survey. The survey is represented in tree form, rooted
	 * at the given {@link Visible} object.
	 * @param root A {@code Visible} object, which is typically the top level section in the survey.
	 * @param asker A {@link QuestionAsker} implementation, which acts as the bridge between this class and the view.
	 * @param listener A {@link SurveyTerminationListener} which can receive a notification when the survey ends.
	 */
	public SurveyDriver(@Nonnull final Visible root, @Nonnull final QuestionAsker asker, 
			@Nonnull final SurveyTerminationListener listener, @Nonnull final SurveyBlackboard blackboard) {
		this.asker = asker;
		this.listener = listener;
		this.blackboard = blackboard;
		curr = Lists.newArrayList(root).listIterator();
	}
	
	/**
	 * Provides the next question to the {@link QuestionAsker}, or fires a {@link SurveyTerminationListener#surveyEnded()}
	 * event.
	 */
	public void next() {
		traverse(Direction.FORWARD);
	}
	
	/**
	 * Provides the previously asked question to the {@link QuestionAsker}, or does nothing if at the beginning of the
	 * survey.
	 */
	public void previous() {
		traverse(Direction.BACKWARD);
	}
	
	private void traverse(@Nonnull Direction dir) {
		switch(dir) {
		case FORWARD : traverseForward(); break;
		case BACKWARD: traverseBackward(); break;
		default : throw new AssertionError();
		}
	}
	
	private void traverseForward() {
		if(curr.hasNext()) {
			visit(curr.next(), Direction.FORWARD);
		} else {
			if(stack.isEmpty()) {
				listener.surveyEnded();
				return;
			}
			curr = stack.pop();
			traverse(Direction.FORWARD);
		}
	}
	
	private void traverseBackward() {
		if(curr.hasPrevious()) {
			visit(curr.previous(), Direction.BACKWARD);
		} else {
			if(stack.isEmpty()) {
				return;
			}
			curr = stack.pop();
			traverse(Direction.BACKWARD);
		}
	}
	
	private void visit(@Nonnull Visible node, @Nonnull Direction dir) {
		if(node != lastAsked && node.isVisible(blackboard)) {
			if(node instanceof Question) {
				Question question = (Question)node;
				lastAsked = question;
				asker.ask(question);
			} else if(node instanceof Section) {
				Section section = (Section)node;
				section.prepareSection();
				stack.push(curr);
				curr = section.children().listIterator();
				traverse(dir);
			}
		} else {
			traverse(dir);
		}
	}
	
	
}
