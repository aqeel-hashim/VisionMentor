import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.ModificationTracker;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
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

        //ModificationTracker mv =VirtualFileManager.VFS_STRUCTURE_MODIFICATIONS;
//        System.out.println(VirtualFileManager.VFS_CHANGES.getDisplayName());
//        Project project= ProjectManager.getInstance().getOpenProjects()[0];

       // Document document = PsiDocumentManager.getInstance(project).getPsiFile();
//       PsiManager.getInstance(project).findFile(LocalFileSystem.getInstance().findFileByIoFile());
//

//        PsiManager.getInstance(project).
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


        VirtualFileManager.getInstance().notify();
//       if(events.)
        //System.out.println("event name");
        //System.out.println(events.getClass().toString());
//        if(events.getClass().toString().equals("Save All")) {
//            Messages.showMessageDialog("there are", "some changes", Messages.getWarningIcon());
//        }

//            events.get().getFile();
//        ModificationTracker mv =VirtualFileManager.VFS_STRUCTURE_MODIFICATIONS;
//        System.out.println(mv.toString());


    }






}
