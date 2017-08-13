import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import com.sun.jna.platform.mac.MacFileUtils;
import com.visionmentor.Common;
import com.visionmentor.listener.IDEActionListener;
import org.jetbrains.annotations.NotNull;

public class MyAppComponent implements ApplicationComponent {
    public MyAppComponent() {
    }

    @Override
    public void initComponent() {
        System.out.println("App COmp");
        ActionManager.getInstance().addAnActionListener(new IDEActionListener());
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "MyAppComponent";
    }
}
