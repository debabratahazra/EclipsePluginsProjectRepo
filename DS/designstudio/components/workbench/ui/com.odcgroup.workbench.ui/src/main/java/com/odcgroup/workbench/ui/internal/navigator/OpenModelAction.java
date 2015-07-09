package com.odcgroup.workbench.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.odcgroup.workbench.core.IMetaModelVersioned;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * @author pkk
 * 
 */
public class OpenModelAction extends BaseSelectionListenerAction {

	public static final String ID = PlatformUI.PLUGIN_ID+ ".OpenOfsModelAction";
	private static final List EMPTY_LIST = Arrays.asList(new Object[0]);
	private boolean selectionDirty = true;
	private List<IOfsModelResource> resources;

	/**
	 * @param page
	 */
	protected OpenModelAction(IWorkbenchPage page) {
		super("Open Design Studio Model");
		setToolTipText("Open Design Studio Model");
		setId(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.BaseSelectionListenerAction#clearCache()
	 */
	protected void clearCache() {
		selectionDirty = true;
		resources = null;
	}

	/**
	 * @return
	 */
	protected List getSelectedResources() {
		// recompute if selection has changed.
		if (selectionDirty) {
			computeResources();
			selectionDirty = false;
		}
		if (resources == null) {
			return EMPTY_LIST;
		}
		return resources;
	}

	/**
	 * filter resources
	 * keep only IOfsModelResource
	 */
	private void computeResources() {
		resources = null;
		for (Iterator e = getStructuredSelection().iterator(); e.hasNext();) {
			Object res = e.next();
			if (res instanceof IOfsModelResource) {
				if (resources == null) {
					resources = new ArrayList<IOfsModelResource>();
				}
				resources.add((IOfsModelResource) res);
				continue;
			} else if (res instanceof IAdaptable) {
				Object resource = ((IAdaptable) res)
						.getAdapter(IOfsModelResource.class);
				if (resources == null) {
					resources = new ArrayList<IOfsModelResource>();
				}
				resources.add((IOfsModelResource) resource);
				continue;
			} 
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		Iterator itr = getSelectedResources().iterator();
		while (itr.hasNext()) {
			IOfsModelResource resource = (IOfsModelResource) itr.next();
			if(OfsEditorUtil.isCorrectVersion(resource)) {
				OfsEditorUtil.openEditor(resource);
			}
		}
	}
	
	
}
