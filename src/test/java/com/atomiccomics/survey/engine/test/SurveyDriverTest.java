package com.atomiccomics.survey.engine.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.atomiccomics.survey.engine.QuestionAsker;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.atomiccomics.survey.engine.SurveyDriver;
import com.atomiccomics.survey.engine.SurveyTerminationListener;

@RunWith(MockitoJUnitRunner.class)
public class SurveyDriverTest {

	@Mock
	private QuestionAsker asker;
	
	@Mock
	private SurveyTerminationListener listener;
	
	@Mock
	private SurveyBlackboard blackboard;
	
	@Test
	public void testForwardWithSingleQuestion() {
		Question onlyQuestion = mock(Question.class);
		when(onlyQuestion.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(onlyQuestion, asker, listener, blackboard);
		driver.next();
		
		verify(asker).ask(onlyQuestion);
	}
	
	@Test
	public void testForwardWithSection() {
		Section section = mock(Section.class);
		Question first = mock(Question.class);
		Question second = mock(Question.class);
		when(section.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		when(section.children()).thenReturn(Arrays.<Visible> asList(first, second));
		when(first.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		when(second.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(section, asker, listener, blackboard);
		driver.next();
		verify(asker).ask(first);
	}
	
	@Test
	public void testDoublingBackAfterSecondQuestion() {
		Section section = mock(Section.class);
		Question first = mock(Question.class);
		Question second = mock(Question.class);
		when(section.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		when(section.children()).thenReturn(Arrays.<Visible> asList(first, second));
		when(first.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		when(second.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(section, asker, listener, blackboard);
		driver.next();
		driver.next();
		driver.previous();
		
		InOrder ordered = inOrder(asker);
		ordered.verify(asker).ask(first);
		ordered.verify(asker).ask(second);
		ordered.verify(asker).ask(first);
		ordered.verifyNoMoreInteractions();
	}
	
	@Test
	public void testQuestionNotAskedIfNotVisible() {
		Section section = mock(Section.class);
		Question first = mock(Question.class);
		Question second = mock(Question.class);
		when(section.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		when(section.children()).thenReturn(Arrays.<Visible> asList(first, second));
		when(first.isVisible(any(SurveyBlackboard.class))).thenReturn(false);
		when(second.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(section, asker, listener, blackboard);
		driver.next();
		verify(asker, never()).ask(first);
		verify(asker).ask(second);
	}
	
	@Test
	public void testSurveyEndedWhenAllQuestionsAsked() {
		Question onlyQuestion = mock(Question.class);
		when(onlyQuestion.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(onlyQuestion, asker, listener, blackboard);
		driver.next();
		driver.next();
		
		verify(listener).surveyEnded();
	}
	
	@Test
	public void testNothingHappensWhenTryingToGoBackBeforeFirstQuestion() {
		Question onlyQuestion = mock(Question.class);
		when(onlyQuestion.isVisible(any(SurveyBlackboard.class))).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(onlyQuestion, asker, listener, blackboard);
		driver.previous();
		verify(asker, never()).ask(any(Question.class));
	}

}
