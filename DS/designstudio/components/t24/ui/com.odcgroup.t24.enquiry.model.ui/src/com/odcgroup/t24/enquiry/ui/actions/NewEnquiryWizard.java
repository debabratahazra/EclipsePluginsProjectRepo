package com.odcgroup.t24.enquiry.ui.actions;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.impl.TranslationDslFactoryImpl;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.ui.internal.wizards.ContainerSelectionWizardPage;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

public class NewEnquiryWizard extends AbstractNewModelResourceCreationWizard implements INewWizard {
	private static final Logger logger = LoggerFactory.getLogger(NewEnquiryWizard.class);
	private NewEnquiryWizardPage enquiryWizardPage ;
	private Enquiry enquiry;
	private int pageIndex = 0;
	private IProject project;
	private IPath selectionPath;
	
	@Override
	public void addPages() {
		setWindowTitle("New Enquiry Wizard");

		enquiry = EnquiryFactoryImpl.eINSTANCE.createEnquiry();
		if(getSelection().isEmpty()){

			addPage(new ContainerSelectionWizardPage());
			enquiryWizardPage = new NewEnquiryWizardPage(enquiry);
			pageIndex=1;
		}
		else{
			enquiryWizardPage = new NewEnquiryWizardPage(enquiry, getProject());
			selectionPath = getSelectionFullPath();
		}
			getSelection();
			addPage(enquiryWizardPage);
			addPage(new FieldSelectionWizardPage("field"));

	}
	
	@Override
	public boolean performFinish() {
		try {  
			    addEnquiryPageDetails(enquiry);
			    setMetamodelVaerison(enquiry);
				String enquiryFile = selectionPath.toString() + File.separatorChar + enquiry.getName() + ".enquiry";
				ResourceSet resourceset = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
				URI uri = URI.createPlatformResourceURI(enquiryFile, true);
				Resource resource = resourceset.createResource(uri);
				resource.getContents().add(enquiry);
				resource.save(Collections.EMPTY_MAP);
				openResourceInEditor(resource);
		} catch (IOException io) {
             logger.error("Resource Save Error",io);
		}
		return true;
	}
	
	/**
	 * @param enquiry
	 */
	private void setMetamodelVaerison(Enquiry enquiry) {
		String metamodelVersion = OfsCore.getCurrentMetaModelVersion("enquiry");
		enquiry.setMetamodelVersion(metamodelVersion);
	}
	/**
	 * @param resource
	 */
	private void openResourceInEditor(Resource resource) {
		try {
			FileEditorInput input = new FileEditorInput(OfsResourceHelper.getFile(resource));
			IWorkbench workbench = PlatformUI.getWorkbench();
			IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(input.getFile().getName());
			workbench.getActiveWorkbenchWindow().getActivePage().openEditor(input, desc.getId());
		} catch (PartInitException e) {
			logger.error("Opening Resource in Editor Error", e);
		}

	}
	
	/**
	 * @param enquiry2
	 */
	private void addEnquiryPageDetails(Enquiry enquiry) {
		  IWizardPage[] pages =getPages();
		  NewEnquiryWizardPage newPage = ((NewEnquiryWizardPage)pages[pageIndex]);
		  project = newPage.getProject();
		  if(selectionPath == null){
			  selectionPath = newPage.getFolderPath();
		  }
		  String enquiryName = newPage.getEnquiryName();
		  enquiry.setName(enquiryName);
		  MdfEntity entity = newPage.getApplication();
		  if (entity != null){
			  /*
			   * Usually, entity is null (since I removed the field selection from the wizard)
			   * But we already set the enquiry FIlename when we picked up the application. So all fine
			   * (Thierry)
			   */
//			  enquiry.setFileName("name:/"+newPage.getFormatedApplicationName(entity)+"#");
		  } else {
			  MessageDialog mDialog = new MessageDialog(null, "Missing Application for this Enquiry", null,
					  "The Application for the Enquiry does not exists!", MessageDialog.ERROR, new String[] { "OK" }, 0);
			  mDialog.open();
		  }
		  String headerText = newPage.getHeaderText();
		  if (headerText != null) {
			EnquiryHeader header = EnquiryFactoryImpl.eINSTANCE.createEnquiryHeader();
			LocalTranslation tranlsation = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslation();
			ITranslationManager translationManager = TranslationCore.getTranslationManager(project);
			Locale defaultLocale = translationManager.getPreferences().getDefaultLocale();
			tranlsation.setLocale(defaultLocale.getLanguage());
			tranlsation.setText(headerText);
			LocalTranslations localTranlsations = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslations();
			localTranlsations.getTranslations().add(tranlsation);
			header.setLabel(localTranlsations);
			header.setColumn(1);
			header.setLine(1);
			enquiry.getHeader().add(header);
		  }
		  enquiry.setStartLine(4);
		  enquiry.setEndLine(99);
		  if (pages.length > 1){
			  FieldSelectionWizardPage fieldpage =((FieldSelectionWizardPage)pages[pageIndex+1]);
			  fieldpage.addFields(enquiry, project);
		  }
		  
	}

}
