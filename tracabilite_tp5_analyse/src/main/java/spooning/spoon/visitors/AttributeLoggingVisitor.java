package spooning.spoon.visitors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

/**
 * Put a logger for every class in the application
 */
public class AttributeLoggingVisitor extends AbstractProcessor<CtClass> {


    @Override
    public void process(CtClass ctClass) {
        Factory factory = getFactory();

        //creating the field for logging
        CtTypeReference<Logger> loggerCtType = factory.Code().createCtTypeReference(Logger.class);
        CtField<Logger> loggerCtField = factory.Core().createField();
        loggerCtField.setType(loggerCtType);
        loggerCtField.addModifier(ModifierKind.STATIC);
        loggerCtField.addModifier(ModifierKind.PRIVATE);
        loggerCtField.setSimpleName("logger");
        CtCodeSnippetExpression loggerExpression = factory.Code().createCodeSnippetExpression("" +
                "LogManager.getLogger(" + ctClass.getSimpleName() + ".class.getName())");
        loggerCtField.setDefaultExpression(loggerExpression);

        //import of LogManager.class
        CtTypeReference<LogManager> logManagerCtType = factory.Code().createCtTypeReference(LogManager.class);
        CtField<LogManager> logManagerCtField = factory.Core().createField();
        logManagerCtField.setType(logManagerCtType);
        logManagerCtField.addModifier(ModifierKind.PRIVATE);
        logManagerCtField.setSimpleName("lm");

        //adding the import of Main.class
        CtTypeReference mainCtType = factory.Type().get("application.main.Main").getReference();
        CtField<?> mainCtField = factory.Core().createField();
        mainCtField.setType(mainCtType);
        mainCtField.addModifier(ModifierKind.PRIVATE);
        mainCtField.setSimpleName("mm");

        //adding fields into the class
        ctClass.addFieldAtTop(loggerCtField);
        ctClass.addFieldAtTop(logManagerCtField);
        ctClass.addFieldAtTop(mainCtField);

        //comments
        CtComment comment = factory.createComment();
        comment.setContent("Generated by Spoon");
        loggerCtField.addComment(comment);
        logManagerCtField.addComment(comment);
        mainCtField.addComment(comment);

    }
}
