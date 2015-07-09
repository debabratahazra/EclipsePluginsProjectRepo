package com.odcgroup.mdf.editor.ui.dialogs;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

import com.odcgroup.mdf.editor.MdfPlugin;

/**
 * DS-1349
 * @author pkk
 *
 */
public class EditionSupportFactory {
	
	private static final Logger LOGGER = Logger.getLogger(EditionSupportFactory.class);	
	public static final String EXTENSION_POINT = "com.odcgroup.mdf.editor.editionsupport_override";
	
	/**
	 * @return
	 */
	public static EditionSupport INSTANCE() {
        return INSTANCE(MdfPlugin.getDefault().getWorkbench(), null);
    }
	
	/**
	 * @param shell
	 * @return
	 */
	public static EditionSupport INSTANCE(Shell shell) {
		return INSTANCE(MdfPlugin.getDefault().getWorkbench(), shell);
	}
	
	/**
	 * @param window
	 * @return
	 */
	public static EditionSupport INSTANCE(IWorkbenchWindow window) {
        return INSTANCE(window.getWorkbench(), window.getShell());		
	}

    /**
     * @param workbench
     * @param shell
     * @return
     */
    public static EditionSupport INSTANCE(IWorkbench workbench, Shell shell) {
    	EditionSupport eSupport = null;
    	IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
        
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if ("editionsupport".equals(elements[j].getName())) {
					try {
						eSupport = (EditionSupport) elements[j].createExecutableExtension("class");
						eSupport.workbench = workbench;
						if (shell == null) {
							eSupport.shell = workbench.getActiveWorkbenchWindow().getShell();
						} else {
							eSupport.shell = shell;
						}
					} catch (CoreException e) {
						LOGGER.error(e.getLocalizedMessage(), e);
					}
				}
			}
		}
		if (eSupport == null) {
			eSupport = new EditionSupport(workbench);
		}
    	return eSupport;
    }
	
}
