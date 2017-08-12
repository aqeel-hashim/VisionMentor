import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class testCompilation implements ApplicationComponent {
    public testCompilation() {
    }


    @Override
    public void initComponent() {
        DebugActionListner dbg = new DebugActionListner();

        ActionManager.getInstance().addAnActionListener(dbg);
        System.out.println("Test");
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "testCompilation";
    }

   



}
