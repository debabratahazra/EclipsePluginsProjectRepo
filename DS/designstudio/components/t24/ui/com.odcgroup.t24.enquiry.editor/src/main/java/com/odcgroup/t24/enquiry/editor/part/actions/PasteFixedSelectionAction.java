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

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FixedSelectionEditPart;
import com.odcgroup.t24.enquiry.editor.request.MultiCreateRequest;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabFolderFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabItemFigure;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class PasteFixedSelectionAction extends SelectionAction {

	public static final String ACTION_ID = "PASTE_PREDEFINED_SELECTION";

	/**
	 * @param part
	 */
	public PasteFixedSelectionAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Paste Predefined Selection");
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
				Enquiry container = getContainer();
				enabled = (container != null);
			}
		}

		return enabled;
	
	}

	/**
	 * @param clipContents
	 * @return the list of widgets from the clipboard
	 */
	@SuppressWarnings({ "rawtypes" })
	private List<Object> getChildren(Object clipContents){
		List<Object> children = new ArrayList<Object>();
		if (clipContents instanceof List) {
			List list = (List)clipContents;
			if (list.size() > 0) {
				if (list.get(0) instanceof FixedSelectionEditPart) {
					for(Object selection : list){
						FixedSelectionEditPart editpart = (FixedSelectionEditPart)selection;
						FixedSelection model = (FixedSelection) editpart.getModel();
						children.add(EcoreUtil.copy(model));
					}
					
				}
			}
		}
        return children;
	}

	/**
	 * @return the selected widget
	 */
	private Enquiry getContainer(){
		ISelection s = getSelection();
		Enquiry container = null;
		if (s != null && s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			if (ss.size() == 1) {
				Object obj = ss.getFirstElement();
				if(obj instanceof EnquiryDiagramEditPart){
					EnquiryFigure figure = (EnquiryFigure) ((EnquiryDiagramEditPart)obj).getFigure();
					TabFolderFigure tabFolder = figure.getTabFolder();
					TabItemFigure tabItem = tabFolder.getSelectedTabItem();
					TabFigure tabFigure = ((TabFigure)tabItem.getItem());
					if(EnquiryFigure.DATA_SELECTION.equals(tabFigure.getText()) && figure.isFocusInPredefinedContainer()){
						container  = (Enquiry) ((EnquiryDiagramEditPart)obj).getModel();
					}
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
			EnquiryDiagramEditPart editPart = null;
			if (obj instanceof EnquiryDiagramEditPart) {
				editPart = (EnquiryDiagramEditPart) obj;
			}
			MultiCreateRequest request = new MultiCreateRequest();
			request.setNewObjects(getChildren(Clipboard.getDefault().getContents()));
			return editPart.getCommand(request);
		}
		return null;
	
	}

}
