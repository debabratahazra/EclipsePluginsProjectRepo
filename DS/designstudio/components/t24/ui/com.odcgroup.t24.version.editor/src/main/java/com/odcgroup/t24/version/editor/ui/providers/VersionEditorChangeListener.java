package com.odcgroup.t24.version.editor.ui.providers;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.t24.version.editor.ui.VersionMultiPageEditor;

public class VersionEditorChangeListener implements IChangeListener {
    private static VersionEditorChangeListener changeListner = null;
    
    public static VersionEditorChangeListener getInstance() {
	if(changeListner ==null){
	  changeListner = new VersionEditorChangeListener();
	  return changeListner ;
	
	}
      return changeListner;
    }
    @Override
    public void handleChange(ChangeEvent event) {
	IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	if(part !=null && part instanceof VersionMultiPageEditor) {
	    
	}

    }

}
