package com.atomiccomics.survey.engine.test;

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
import com.atomiccomics.survey.engine.SurveyDriver;

@RunWith(MockitoJUnitRunner.class)
public class SurveyDriverTest {

	@Mock
	private QuestionAsker asker;
	
	@Test
	public void testForwardWithSingleQuestion() {
		Question onlyQuestion = mock(Question.class);
		when(onlyQuestion.isVisible()).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(onlyQuestion, asker);
		driver.next();
		
		verify(asker).ask(onlyQuestion);
	}
	
	@Test
	public void testForwardWithSection() {
		Section section = mock(Section.class);
		Question first = mock(Question.class);
		Question second = mock(Question.class);
		when(section.isVisible()).thenReturn(true);
		when(section.children()).thenReturn(Arrays.<Visible> asList(first, second));
		when(first.isVisible()).thenReturn(true);
		when(second.isVisible()).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(section, asker);
		driver.next();
		verify(asker).ask(first);
	}
	
	@Test
	public void testDoublingBackAfterSecondQuestion() {
		Section section = mock(Section.class);
		Question first = mock(Question.class);
		Question second = mock(Question.class);
		when(section.isVisible()).thenReturn(true);
		when(section.children()).thenReturn(Arrays.<Visible> asList(first, second));
		when(first.isVisible()).thenReturn(true);
		when(second.isVisible()).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(section, asker);
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
		when(section.isVisible()).thenReturn(true);
		when(section.children()).thenReturn(Arrays.<Visible> asList(first, second));
		when(first.isVisible()).thenReturn(false);
		when(second.isVisible()).thenReturn(true);
		
		SurveyDriver driver = new SurveyDriver(section, asker);
		driver.next();
		verify(asker, never()).ask(first);
		verify(asker).ask(second);
	}

}
