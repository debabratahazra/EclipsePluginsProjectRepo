package com.odcgroup.workbench.compare.viewer.content;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.emf.compare.ui.export.ExportMenu;
import org.eclipse.emf.compare.ui.viewer.structure.ModelStructureMergeViewer;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author pkk
 *
 */
public class BaseModelStructureMergeViewer extends ModelStructureMergeViewer {

	/**
	 * @param parent
	 * @param compareConfiguration
	 */
	public BaseModelStructureMergeViewer(Composite parent, CompareConfiguration compareConfiguration) {
		super(parent, compareConfiguration);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.structure.ModelStructureMergeViewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	protected void inputChanged(Object input, Object oldInput) {
		if (input != null) {
			super.inputChanged(input, oldInput);
		} 
	}
	
	/**
	 * This will initialize the "save as emfdiff" action and put its icon in the {@link CompareViewerPane}
	 * toolbar.
	 */
	protected void createToolItems() {
		final ToolBarManager tbm = CompareViewerPane.getToolBarManager(getControl().getParent());
		tbm.removeAll();
		if (exportMenu == null) {
			exportMenu = new ExportMenu(tbm.getControl(), this);
		}
		tbm.add(new Separator("IO")); //$NON-NLS-1$
		tbm.appendToGroup("IO", exportMenu); //$NON-NLS-1$
		tbm.update(true);
	}
	
	

}
