package com.odcgroup.page.ui.action;

import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * copy action - copies the selected widgets to clipboard
 * @author pkk 
 */
public class CopyWidgetAction extends SelectionAction {

	/**
	 * constructor for CopyWidgetAction
	 * @param part
	 */
	public CopyWidgetAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * Calculates if the action is enables.
	 * 
	 * @return boolean
	 */
	protected boolean calculateEnabled() {
		ISelection s = getSelection();
		if (s != null && s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			if (ss.size() == 1) {
				
				Object obj = ss.getFirstElement();
				Widget w = (Widget) AdaptableUtils.getAdapter(obj, Widget.class);
				if(w == null) {
					return false;
				} 
				if(w.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
					ITableColumn column = TableHelper.getTableColumn(w);
					if(column.getGroups().size() > 0) {
						return false;
					}
				}
				
				if(w != null && w.getParent() == null){
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	* Inits the action. 
	*/ 
	protected void init() { 
		setId(ActionFactory.COPY.getId()); 
		setText("Copy"); 
		setAccelerator(SWT.CTRL | 'C'); 
		setDescription("Copy"); 
		setToolTipText("Copy"); 
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
	} 
	
	/**
	 * Runs the action.
	 */
	public void run() { 
		List selection = getSelectedObjects(); 
		if (selection != null && selection.size() > 0) { 
			for (int i = 0; i < selection.size();i++) {
				if (selection.get(i) instanceof WidgetEditPart) { 	
					Clipboard.getDefault().setContents(selection); 
					return; 
				}
			} 
		} 
	} 


}
