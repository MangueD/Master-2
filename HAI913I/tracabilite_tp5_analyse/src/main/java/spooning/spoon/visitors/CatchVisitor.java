package spooning.spoon.visitors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtComment;
import spoon.reflect.factory.Factory;

/**
 * create a statement before every catch clause, it adds a log on the DEBUG level,
 * the message contains the userid, if it's a throw or catch, and the exception name
 */
public class CatchVisitor extends AbstractProcessor<CtCatch> {
	final static String QUOTE_JSON = "\\\"";
	final static String QUOTE_LOG = "\"";
	final static String QUOTE_CONC = " + ";

	@Override
	public void process(CtCatch ctCatch) {
		Factory factory = getFactory();

		// creating the user Json string for the app code
		String userJsonString = QUOTE_LOG + QUOTE_JSON + "user" + QUOTE_JSON + " : " + QUOTE_JSON + QUOTE_LOG + QUOTE_CONC +
				"(Main.getUserContext() == null ? null : Main.getUserContext().getId())" + QUOTE_CONC +
				QUOTE_LOG + QUOTE_JSON + QUOTE_LOG;

		//creating a json string to know if it's a throw or catch
		String whereJsonString = QUOTE_LOG + QUOTE_JSON + "where" + QUOTE_JSON + " : " + QUOTE_JSON +
				"catch" + QUOTE_JSON + QUOTE_LOG;

		// creating the Exception Json string for the app code
		String exceptionJsonString = QUOTE_LOG + QUOTE_JSON + "exception" + QUOTE_JSON + " : " + QUOTE_JSON +
				ctCatch.getParameter().getType().getSimpleName() + QUOTE_JSON + QUOTE_LOG;

		// construct the code
		CtCodeSnippetStatement statement = factory.Code().createCodeSnippetStatement("" +
				"logger.debug(" + QUOTE_LOG + "{" + QUOTE_LOG + QUOTE_CONC +
				userJsonString + QUOTE_CONC +
				QUOTE_LOG + ", " + QUOTE_LOG + QUOTE_CONC +
				whereJsonString + QUOTE_CONC +
				QUOTE_LOG + ", " + QUOTE_LOG + QUOTE_CONC +
				exceptionJsonString + QUOTE_CONC +
				QUOTE_LOG + "}" + QUOTE_LOG + ")" );
		CtBlock block = ctCatch.getBody();
		if(block != null){
			block.insertBegin(statement);
		}

		//comment on the generated code
		CtComment comment = factory.createComment();
		comment.setContent("Generated by Spoon");
		statement.addComment(comment);
	}
}
