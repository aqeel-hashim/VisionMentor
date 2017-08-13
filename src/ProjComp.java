import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import com.visionmentor.Common;
import org.jetbrains.annotations.NotNull;

public class ProjComp implements ProjectComponent {
    private Project project;
    public ProjComp(Project project) {
        this.project = project;
    }

    @Override
    public void initComponent() {
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileOpened(source, file);
                boolean isFound = false;
                System.out.println("yeeea");
                for(VirtualFile vf : Common.filesWithChanges){
                    if((vf.getName().equals(file.getName()))){
                        isFound = true;
                    }

                }
                if(!isFound) Common.filesWithChanges.add(file);
                
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileClosed(source, file);
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                super.selectionChanged(event);
            }
        });
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "ProjComp";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
    }
}
