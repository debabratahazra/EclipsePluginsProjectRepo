package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.editor.part.AbstractEnquiryEditPart;
import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabFolderFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabItemFigure;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class AddDrillDownAction extends SelectionAction {

	public static final String ACTION_ID = "ADD_DRILL_DOWN";

	/**
	 * @param part
	 */
	public AddDrillDownAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Add DrillDown");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if(obj instanceof EnquiryDiagramEditPart){
				EnquiryDiagramEditPart editPart = (EnquiryDiagramEditPart)obj;
				EnquiryFigure figure = (EnquiryFigure) editPart.getFigure();
				TabFolderFigure tabFolder = figure.getTabFolder();
				TabItemFigure tabItem = tabFolder.getSelectedTabItem();
				TabFigure tabFigure = ((TabFigure)tabItem.getItem());
				return (EnquiryFigure.DRILL_DOWN.equals(tabFigure.getText()) && figure.isFocusInDrillDownContainer());
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
			if (obj != null && obj instanceof EnquiryDiagramEditPart) {
				editPart = (EnquiryDiagramEditPart) obj;
				final Enquiry enquiry =(Enquiry)editPart.getModel();				
				CreateRequest request = new CreateRequest(){
					@Override
					public Object getNewObject() {
						DrillDown drillDown = EnquiryFactory.eINSTANCE.createDrillDown();
						EnquiryUtil.setDrillDownName(drillDown, enquiry);
						return drillDown;
					}
				};
				return editPart.getCommand(request);
			}
		}
		return null;
	}

}
