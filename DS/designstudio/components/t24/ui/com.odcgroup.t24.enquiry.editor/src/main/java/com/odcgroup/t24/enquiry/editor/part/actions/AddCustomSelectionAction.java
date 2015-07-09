package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.editor.part.AbstractEnquiryEditPart;
import com.odcgroup.t24.enquiry.editor.part.CustomSelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
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
public class AddCustomSelectionAction extends SelectionAction{

	public static final String ACTION_ID = "ADD_CUSTOM_SELECTION";
	/**
	 * @param part
	 */
	public AddCustomSelectionAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Add Custom Selection");
	}

	@Override
	protected boolean calculateEnabled() {
		List selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if(obj instanceof CustomSelectionEditPart){
				return true;
			}
			else if(obj instanceof EnquiryDiagramEditPart){
				EnquiryDiagramEditPart enquiryEditPart = (EnquiryDiagramEditPart)obj;
				Enquiry model = (Enquiry)enquiryEditPart.getModel();
				EnquiryDiagramEditPart editPart = (EnquiryDiagramEditPart)obj;
				EnquiryFigure figure = (EnquiryFigure) editPart.getFigure();
				TabFolderFigure tabFolder = figure.getTabFolder();
				TabItemFigure tabItem = tabFolder.getSelectedTabItem();
				TabFigure tabFigure = ((TabFigure)tabItem.getItem());
				return (model.getCustomSelection() == null && EnquiryFigure.DATA_SELECTION.equals(tabFigure.getText()) 
						&& figure.isFocusInCustomSelectionContainer());
			}
		}
		return false;
	}

	@Override
	public void run() {
		getCommandStack().execute(getCommand());
	}
	
	/**
	 * 
	 * @return
	 */
	private Command getCommand() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			AbstractEnquiryEditPart editPart = null;
			CreateRequest request = new CreateRequest(){
				@Override
				public Object getNewObject() {
					Selection selection = EnquiryFactory.eINSTANCE.createSelection();
					selection.setField("Field");
					return selection;
				}
			};
			if (obj instanceof EnquiryDiagramEditPart) {
				editPart = (EnquiryDiagramEditPart) obj;
			}
			else if(obj instanceof CustomSelectionEditPart){
				editPart = (CustomSelectionEditPart) obj;
			}
			if(editPart !=null){
				return editPart.getCommand(request);
			}

		}
		return null;
	}

}
