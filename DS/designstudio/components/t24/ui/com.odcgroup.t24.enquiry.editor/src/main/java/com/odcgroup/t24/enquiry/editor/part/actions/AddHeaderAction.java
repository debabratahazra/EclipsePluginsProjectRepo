package com.odcgroup.t24.enquiry.editor.part.actions;

import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.t24.enquiry.editor.part.AbstractEnquiryEditPart;
import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabFolderFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabItemFigure;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.impl.TranslationDslFactoryImpl;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class AddHeaderAction extends SelectionAction {

	public static final String ACTION_ID = "ADD_HEADER";
	private Enquiry enquiryModel;

	/**
	 * @param part
	 */
	public AddHeaderAction(IWorkbenchPart part) {
		super(part);
		setId(ACTION_ID);
		setText("Add Header");
	}

	@Override
	protected boolean calculateEnabled() {
		List selection = getSelectedObjects();
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if(obj instanceof EnquiryDiagramEditPart){
				EnquiryDiagramEditPart editPart = (EnquiryDiagramEditPart)obj;
				enquiryModel = (Enquiry)editPart.getModel();
				EnquiryFigure figure = (EnquiryFigure) editPart.getFigure();
				TabFolderFigure tabFolder = figure.getTabFolder();
				TabItemFigure tabItem = tabFolder.getSelectedTabItem();
				TabFigure tabFigure = ((TabFigure)tabItem.getItem());
				return (EnquiryFigure.DATA_OUTPUT.equals(tabFigure.getText()) && figure.isFocusInHeadersContainer());
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
					EnquiryHeader header = EnquiryFactory.eINSTANCE.createEnquiryHeader();
					header.setColumn(0);
					header.setLine(0);
					LocalTranslation tranlsation = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslation();
					IProject project = OfsResourceHelper.getProject(enquiryModel.eResource());
					ITranslationManager translationManager = TranslationCore.getTranslationManager(project);
					Locale defaultLocale = translationManager.getPreferences().getDefaultLocale();
					tranlsation.setLocale(defaultLocale.getLanguage());
					LocalTranslations localTranlsations = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslations();
					localTranlsations.getTranslations().add(tranlsation);
					header.setLabel(localTranlsations);
					return header;
				}
			};
			if (obj instanceof EnquiryDiagramEditPart) {
				editPart = (EnquiryDiagramEditPart) obj;
			}
			if(editPart !=null){
				return editPart.getCommand(request);
			}

		}
		return null;
	}

}
