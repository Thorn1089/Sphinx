package com.atomiccomics.survey.parser;

import static com.google.common.base.Optional.fromNullable;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.empty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import com.atomiccomics.SphinxBaseListener;
import com.atomiccomics.SphinxParser;
import com.atomiccomics.survey.common.Instructions;
import com.atomiccomics.survey.common.MultipleChoiceQuestion;
import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.atomiccomics.survey.core.VisiblePredicate;

public class SurveyBuilder extends SphinxBaseListener {

	private final Map<String, QuestionBuilder> questionBuilders = new HashMap<>();
	
	private final List<Section> sections = new ArrayList<>();
	
	private final ParseTreeProperty<Question> questions = new ParseTreeProperty<>();
	
	{
		questionBuilders.put("Instructions", (id, delegate, str, ans) -> 
			new Instructions(id, delegate, str));
		questionBuilders.put("TrueFalseQuestion", (id, delegate, str, ans) ->
			ans.isEmpty() ?
				new TrueFalseQuestion(id, delegate, str) :
				new TrueFalseQuestion(id, delegate, str, ans.split("\n")[0], ans.split("\n")[1]));
		questionBuilders.put("MultipleChoiceQuestion", (id, delegate, str, ans) ->
			new MultipleChoiceQuestion(id, delegate, str, Arrays.asList(ans.split("\n"))));
	}
	
	@Override
	public void exitSection(@NotNull SphinxParser.SectionContext ctx) {
		List<Question> parsedQuestions = ctx.question()
				.stream()
				.map(question -> questions.get(question))
				.collect(toList());
		
		final Section section = new StaticSection(() -> true, parsedQuestions);
		sections.add(section);
	}
	
	@Override
	public void exitQuestion(@NotNull SphinxParser.QuestionContext ctx) {
		final String type = ctx.TYPE().getText();
		final String id = ctx.identifier().getText();
		final QuestionBuilder builder = fromNullable(questionBuilders.get(type)).or((qid, delegate, str, ans) -> null);
		
		String body = ctx.question_line()
			.stream()
			.map(q -> q.STRING().getText())
			.map(s -> s.substring(1, s.length() - 1))
			.collect(Collectors.joining(" "))
			.replace('\t', ' ');
		
		
		String answer = ofNullable(ctx.answer())
			.map(a -> a.answer_line())
			.map(l -> l.stream())
			.orElse(empty())
			.map(a -> a.STRING().getText())
			.map(s -> s.substring(1, s.length() - 1))
			.collect(Collectors.joining("\n"))
			.replace('\t', ' ');
		
		questions.put(ctx, builder.build(id, () -> true, body, answer));
	}
	
	public Visible getSurvey() {
		return new StaticSection(() -> true, sections);
	}
	
	private interface QuestionBuilder {
		Question build(String id, VisiblePredicate delegate, String body, String answer);
	}
	
}
