package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.Routine;
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
public class AddRoutineAction extends SelectionAction {

	public String ROUTINE_TYPE;

	/**
	 * @param part
	 * @param string
	 */
	public AddRoutineAction(IWorkbenchPart part, String routineType) {
		super(part);
		this.ROUTINE_TYPE = routineType;
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof EnquiryDiagramEditPart) {
				EnquiryDiagramEditPart editPart = (EnquiryDiagramEditPart) obj;
				EnquiryFigure figure = (EnquiryFigure) editPart.getFigure();
				TabFolderFigure tabFolder = figure.getTabFolder();
				TabItemFigure tabItem = tabFolder.getSelectedTabItem();
				TabFigure tabFigure = ((TabFigure) tabItem.getItem());
				return (EnquiryFigure.CONFIGURATION.equals(tabFigure.getText()) && figure.isFocusInRoutineContainer());
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
			EnquiryDiagramEditPart editPart = null;
			if (obj instanceof EnquiryDiagramEditPart) {
				editPart = (EnquiryDiagramEditPart) obj;
			}
			CreateRequest request = getNewRoutine(ROUTINE_TYPE);
			return editPart.getCommand(request);
		}
		return null;
	}

	/**
	 * @return
	 */
	private CreateRequest getNewRoutine(final String routineType) {
		CreateRequest request = new CreateRequest() {
			@Override
			public Object getNewObject() {
				Routine routine = null;
				if (routineType.equals("Java")) {
					routine = EnquiryFactory.eINSTANCE.createJavaRoutine();
				} else if (routineType.equals("JBC")) {
					routine = EnquiryFactory.eINSTANCE.createJBCRoutine();
				} else {
					return null;
				}
				routine.setName("RoutineName");
				return routine;
			}
		};
		return request;
	}

}
