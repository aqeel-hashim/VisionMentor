import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.PsiManager;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class testCompilation implements ApplicationComponent,BulkFileListener {
    private final MessageBusConnection connection;


    public testCompilation() {
        this.connection = ApplicationManager.getApplication().getMessageBus().connect();

    }


    @Override
    public void initComponent() {


        connection.subscribe(VirtualFileManager.VFS_CHANGES, this);

        //PsiManager.getInstance(project).addPsiTreeChangeListener();


        DebugActionListner dbg = new DebugActionListner();
//
//        DebugActionListner dbl = new DebugActionListner();
//        Thread t1 =new Thread(dbl);
//        t1.run();

        ActionManager.getInstance().addAnActionListener(dbg);

        System.out.println("Test");


    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here

        connection.disconnect();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "testCompilations";


    }
    public void before(List<? extends VFileEvent> events) {
        // ...
    }

    public void after(List<? extends VFileEvent> events) {
        Messages.showMessageDialog("there are","some changes",Messages.getWarningIcon());

    }





}
