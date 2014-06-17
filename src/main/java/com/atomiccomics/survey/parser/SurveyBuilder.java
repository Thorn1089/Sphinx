package com.atomiccomics.survey.parser;

import static com.google.common.base.Optional.fromNullable;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.empty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import com.atomiccomics.SphinxBaseListener;
import com.atomiccomics.SphinxParser;
import com.atomiccomics.survey.common.FillInTextQuestion;
import com.atomiccomics.survey.common.FillInNumberQuestion;
import com.atomiccomics.survey.common.Instructions;
import com.atomiccomics.survey.common.MultipleChoiceQuestion;
import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.core.Visible;
import com.atomiccomics.survey.core.VisiblePredicate;

public class SurveyBuilder extends SphinxBaseListener {

	private final Map<String, QuestionBuilder> questionBuilders = new HashMap<>();
	
	private final List<Section> sections = new ArrayList<>();
	
	private final ParseTreeProperty<Question> questions = new ParseTreeProperty<>();
	
	private final ParseTreeProperty<VisiblePredicate> predicates = new ParseTreeProperty<>();
	
	private final ParseTreeProperty<AnswerComparator> predicateModifiers = new ParseTreeProperty<>();
	
	private final Set<String> idsSeen = new HashSet<>();
	
	{
		questionBuilders.put("Instructions", (id, delegate, str, ans) -> 
			new Instructions(id, delegate, str));
		questionBuilders.put("TrueFalseQuestion", (id, delegate, str, ans) ->
			ans.isEmpty() ?
				new TrueFalseQuestion(id, delegate, str) :
				new TrueFalseQuestion(id, delegate, str, ans.split("\n")[0], ans.split("\n")[1]));
		questionBuilders.put("MultipleChoiceQuestion", (id, delegate, str, ans) ->
			new MultipleChoiceQuestion(id, delegate, str, Arrays.asList(ans.split("\n"))));
		questionBuilders.put("FillInTextQuestion", (id, delegate, str, ans) -> 
			new FillInTextQuestion(id, delegate, str));
		questionBuilders.put("FillInNumberQuestion", (id, delegate, str, ans) -> 
			new FillInNumberQuestion(id, delegate, str));
	}
	
	@Override
	public void exitSection(@NotNull SphinxParser.SectionContext ctx) {
		List<Question> parsedQuestions = ctx.question()
				.stream()
				.map(question -> questions.get(question))
				.collect(toList());
		
		final VisiblePredicate delegate = ofNullable(predicates.get(ctx.predicate())).orElse((bb) -> true);
		final Section section = new StaticSection(delegate, parsedQuestions);
		sections.add(section);
	}
	
	@Override
	public void enterQuestion(@NotNull SphinxParser.QuestionContext ctx) {
		idsSeen.add(ctx.identifier().getText());
	}
	
	@Override
	public void exitQuestion(@NotNull SphinxParser.QuestionContext ctx) {
		final String type = ctx.TYPE().getText();
		final String id = ctx.identifier().getText();
		final QuestionBuilder builder = fromNullable(questionBuilders.get(type)).or((qid, delegate, str, ans) -> null);
		
		String body = ctx.question_line()
			.stream()
			.map(q -> q.STRING().getText())
			.map(this::stripSlashes)
			.collect(Collectors.joining(" "))
			.replace('\t', ' ');
		
		String answer = ofNullable(ctx.answer())
			.map(a -> a.answer_line())
			.map(l -> l.stream())
			.orElse(empty())
			.map(a -> a.STRING().getText())
			.map(this::stripSlashes)
			.collect(Collectors.joining("\n"))
			.replace('\t', ' ');
		
		final VisiblePredicate delegate = ofNullable(predicates.get(ctx.predicate())).orElse((bb) -> true);
		questions.put(ctx, builder.build(id, delegate, body, answer));
	}
	
	@Override
	public void exitPredicate(@NotNull SphinxParser.PredicateContext ctx) {
		final String id = ctx.identifier().getText();
		
		if(!idsSeen.contains(id)) {
			System.err.println("No such identifier: " + id);
			return;
		}
		
		final String expectedAnswer = ctx.expected_answer().getText();
		final Pattern pattern = Pattern.compile("\".*\"");
		final Matcher matcher = pattern.matcher(expectedAnswer);
		
		final String value = (matcher.matches() ? stripSlashes(expectedAnswer) : expectedAnswer);
		
		final String condition = ofNullable(ctx.CONDITION()).map((c) -> c.getText()).orElse(null);
		final VisiblePredicate subPredicate = ofNullable(predicates.get(ctx.predicate())).orElse((bb) -> true);
		
		AnswerComparator comparator = ofNullable(predicateModifiers.get(ctx.modifier())).orElse(Answer::isEqualTo);
		
		final VisiblePredicate predicate = (bb) -> 
			bb.check(id).map((ans) -> comparator.compare(ans, value)).orElse(false);
			
		if(condition != null) {
			switch(condition) {
			case "and": 
				predicates.put(ctx, (bb) -> predicate.isVisible(bb) && subPredicate.isVisible(bb));
				break;
			case "or":
				predicates.put(ctx, (bb) -> predicate.isVisible(bb) || subPredicate.isVisible(bb));
				break;
			default: throw new IllegalStateException("Grammar produced illegal match");
			}
		} else {
			predicates.put(ctx, predicate);			
		}
	}
	
	@Override
	public void exitModifier(@NotNull SphinxParser.ModifierContext ctx) {
		if(ctx.LESS() != null) {
			predicateModifiers.put(ctx, Answer::isLessThan);
		} else if(ctx.GREATER() != null) {
			predicateModifiers.put(ctx, Answer::isGreaterThan);
		}
	}
	
	public Visible getSurvey() {
		return new StaticSection((bb) -> true, sections);
	}
	
	private String stripSlashes(final String input) {
		return input.substring(1, input.length() - 1);
	}
	
	private interface QuestionBuilder {
		Question build(String id, VisiblePredicate delegate, String body, String answer);
	}
	
	private interface AnswerComparator {
		boolean compare(Answer ans, Object value);
	}
	
}
