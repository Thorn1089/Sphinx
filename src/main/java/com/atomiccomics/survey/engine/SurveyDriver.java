package com.atomiccomics.survey.engine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ListIterator;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.google.common.collect.Lists;


/**
 * The {@code SurveyDriver} wraps several concepts from the core and engine packages to present a high-level
 * model interface for the survey as a whole, such as allowing the view layer to request updates by navigating
 * forward and back.
 * 
 * @author Tom
 */
public final class SurveyDriver {
	
	private static enum Direction { FORWARD, BACKWARD }
	
	private final Deque<ListIterator<Visible>> stack = new ArrayDeque<>();
	
	private final QuestionAsker asker;
	
	private ListIterator<Visible> curr;
	
	private Question lastAsked = null;
	
	public SurveyDriver(final Visible root, final QuestionAsker asker) {
		this.asker = asker;
		curr = Lists.newArrayList(root).listIterator();
	}
	
	public void next() {
		traverse(Direction.FORWARD);
	}
	
	public void previous() {
		traverse(Direction.BACKWARD);
	}
	
	private void traverse(Direction dir) {
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
			curr = stack.pop();//TODO Handle end
			traverse(Direction.FORWARD);
		}
	}
	
	private void traverseBackward() {
		if(curr.hasPrevious()) {
			visit(curr.previous(), Direction.BACKWARD);
		} else {
			curr = stack.pop();//TODO Handle end
			traverse(Direction.BACKWARD);
		}
	}
	
	private void visit(Visible node, Direction dir) {
		if(node != lastAsked && node.isVisible()) {
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
