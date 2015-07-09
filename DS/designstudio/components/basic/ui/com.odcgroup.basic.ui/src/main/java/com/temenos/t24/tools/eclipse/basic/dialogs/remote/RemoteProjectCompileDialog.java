package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

/**
 * class for Remote Project Compile dialog
 * 
 * @author yasar
 * 
 */
public class RemoteProjectCompileDialog extends RemoteProjectDialog {

    public RemoteProjectCompileDialog(Shell parentShell, ArrayList<IFile> iFileList) {
        super(parentShell);
        this.basicFileList = iFileList;
        for (IFile basicFile : basicFileList) {
            /** Initially all files are selected. */
            basicFileSelectedMap.put(basicFile, true);
        }
    }

    /**
     * Configures the given shell in preparation for opening this window in it.
     * In this case, we set the dialog title.
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Compile Project Dialog");
    }
}
