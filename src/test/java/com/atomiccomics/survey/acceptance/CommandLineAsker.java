package com.atomiccomics.survey.acceptance;

import java.util.HashSet;
import java.util.Set;

import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.engine.QuestionAsker;

public class CommandLineAsker implements QuestionAsker {
	
	private final Set<CommandLineQuestionFormatter> formatters = new HashSet<>();
	
	public void registerFormatter(CommandLineQuestionFormatter formatter) {
		formatters.add(formatter);
	}
	
	@Override
	public void ask(Question question) {
		for(CommandLineQuestionFormatter formatter : formatters) {
			if(formatter.handles(question.getClass())) {
				formatter.format(question);
				break;
			}
		}
	}

}
