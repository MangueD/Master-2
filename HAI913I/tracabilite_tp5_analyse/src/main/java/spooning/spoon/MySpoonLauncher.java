package spooning.spoon;

import spoon.Launcher;
import spoon.processing.AbstractProcessor;

/**
 * a easy launcher for spoon
 */
public class MySpoonLauncher {
    protected String projectPath;
    protected Launcher parser;

    public MySpoonLauncher(String projectPath){
        this.projectPath = projectPath;
        parser = new Launcher();
        parser.addInputResource(projectPath); // set project source path
        parser.getEnvironment().setSourceClasspath(new String[] {}); // set project classpath
        parser.setSourceOutputDirectory("spooned/"); // set generated source code directory path
        parser.getEnvironment().setAutoImports(true); // set auto imports
        parser.getEnvironment().setCommentEnabled(true); // set comments enabled
    }

    public void addProcessor(AbstractProcessor<?> processor){
        parser.addProcessor(processor);
    }

    public void run(){
        parser.run();
    }



}
