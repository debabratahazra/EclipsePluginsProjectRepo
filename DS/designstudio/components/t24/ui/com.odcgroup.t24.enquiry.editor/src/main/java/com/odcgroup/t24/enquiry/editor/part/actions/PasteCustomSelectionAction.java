package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.editor.part.CustomSelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.SelectionEditPart;
import com.odcgroup.t24.enquiry.editor.request.MultiCreateRequest;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class PasteCustomSelectionAction extends SelectionAction {

	public static final String ACTION_ID = "PASTE_CUSTOM_SELECTION";

	/**
	 * @param part
	 */
	public PasteCustomSelectionAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Paste Custom Selection");
		setDescription("Paste"); 
		setToolTipText("Paste"); 
	}

	@Override
	protected boolean calculateEnabled() {

		boolean enabled = false;
		Object clipCont = Clipboard.getDefault().getContents();
		
		List<Object> children;
		if (clipCont != null) {
			 children = getChildren(clipCont);
			if (!children.isEmpty()) {
				SelectionExpression container = getContainer();
				enabled = (container != null);
			}
		}

		return enabled;
	
	}

	/**
	 * @param clipContents
	 * @return the list of children from the clipboard
	 */
	@SuppressWarnings({ "rawtypes" })
	private List<Object> getChildren(Object clipContents){
		List<Object> children = new ArrayList<Object>();
		if (clipContents instanceof List) {
			List list = (List)clipContents;
			if (list.size() > 0) {
				if (list.get(0) instanceof SelectionEditPart) {
					for(Object selection : list){
						SelectionEditPart editpart = (SelectionEditPart)selection;
						Selection model = (Selection) editpart.getModel();
						children.add(EcoreUtil.copy(model));
					}
					
				}
			}
		}
        return children;
	}

	/**
	 * @return the selected object
	 */
	private SelectionExpression getContainer(){
		ISelection s = getSelection();
		SelectionExpression container = null;
		if (s != null && s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			if (ss.size() == 1) {
				Object obj = ss.getFirstElement();
				if(obj instanceof CustomSelectionEditPart){
					container  = (SelectionExpression) ((CustomSelectionEditPart)obj).getModel();
				}
				
			}
		}
		return container;
	}
	

	@Override
	public void run() {
		getCommandStack().execute(getPasteCommand());
	}

	/**
	 * @return
	 */
	private Command getPasteCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			CustomSelectionEditPart editPart = null;
			if (obj instanceof CustomSelectionEditPart) {
				editPart = (CustomSelectionEditPart) obj;
			}
			MultiCreateRequest request = new MultiCreateRequest();
			request.setNewObjects(getChildren(Clipboard.getDefault().getContents()));
			return editPart.getCommand(request);
		}
		return null;
	
	}

}
