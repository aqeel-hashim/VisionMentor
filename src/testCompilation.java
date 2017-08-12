import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

public class testCompilation implements ApplicationComponent,CompilationStatusListener {
    public testCompilation() {
        initComponent();
    }

    @Override
    public void initComponent() {
//        ActionManager am = ActionManager.getInstance();
//        // TODO: insert component initialization logic here
//        CompilationStatusListener cl = new CompilationStatusListener() {
//            @Override
//            public void compilationFinished(boolean aborted, int errors, int warnings, CompileContext compileContext) {
//                System.out.println("compile error");
//                System.out.println(compileContext);
//            }
//        };


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
