package spooning.main;

import spooning.spoon.MySpoonLauncher;
import spooning.spoon.visitors.*;

public class Main {
    static final String projectPath = "src/main/java/application";
    public static void main(String args[]){
        MySpoonLauncher launcher = new MySpoonLauncher(projectPath);

        launcher.addProcessor(new AttributeLoggingVisitor());
        launcher.addProcessor(new ClassMainVisitor());
        launcher.addProcessor(new MethodLoggingVisitor());
        launcher.addProcessor(new CatchVisitor());
        launcher.addProcessor(new ThrowVisitor());

        launcher.run();
    }
}
