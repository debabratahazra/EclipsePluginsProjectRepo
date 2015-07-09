package com.odcgroup.t24.helptext.ui.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.xerces.dom.ElementNSImpl;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.ApplicationsImportProblemsException;
import com.odcgroup.t24.applicationimport.mapper.MappingException;
import com.odcgroup.t24.applicationimport.reader.Reader;
import com.odcgroup.t24.applicationimport.serializer.MdfDomainSerializer;
import com.odcgroup.t24.helptext.schema.T24Help;
import com.odcgroup.t24.helptext.ui.dialogs.ImportErrorDilaog;
import com.odcgroup.t24.helptextimport.mapper.T24HelpText2MdfMapper;
import com.odcgroup.t24.helptextimport.ui.wizard.HelpTextImportWizard;
import com.odcgroup.workbench.core.OfsCore;

public class HelpTextImporterUtil {

	private T24HelpText2MdfMapper mapper = new T24HelpText2MdfMapper();
	private static final Logger LOGGER = LoggerFactory.getLogger(HelpTextImportWizard.class); 
	private boolean isWritingCancled =false;
	
	private Map<String, MdfClass> domainClassMap = new HashMap<String, MdfClass>();
	/**
	 * @return Domains read. These have already been saved - they are just returned e.g. for tests to check stuff in them more easily.
	 */
	public void importHelptexts(final Collection<File> xmlFilesToImport, final IProject project,final IProgressMonitor monitor) throws ApplicationsImportProblemsException, InterruptedException {
		// Note we're doing 2*files (reading & mapping)
		// 1. Reading XML
		final Set<MdfClass> readClasses = new HashSet<MdfClass>();
		final Set<MdfDomain> readDomain = new HashSet<MdfDomain>();
		ApplicationsImportProblemsException problems = new ApplicationsImportProblemsException();
		monitor.beginTask("Reading " + xmlFilesToImport.size() + " XML files...", 2 * xmlFilesToImport.size());
		Iterable<T24Help> modules = new LinkedList<T24Help>();
		Reader reader;
		try {
			try {
				reader = new Reader();
			} catch (JAXBException e) {
				throw problems.addProblem("JAXB initialization failed", e);
			} catch (SAXException e) {
				throw problems.addProblem("JAXB initialization failed", e);
			}
			try {
				monitor.subTask("Reading XMLs " + xmlFilesToImport.toString());
				modules = reader.unmarshalHelptext(xmlFilesToImport,problems);
				monitor.worked(1);
			} catch (Exception e) {
				problems.addProblem(xmlFilesToImport.toString(), e);
			} 
			if (monitor.isCanceled())
				throw new InterruptedException();
			
			// 2. populate domain class map
			populateDomainClasses(project);
			
			// 3. Mapping XML to Class
			for (T24Help module : modules) {
				try {
					MdfClass mdfClass = getMdfClass(module);
					if (mdfClass!=null) {
						readClasses.add(mdfClass);
						readDomain.add(mdfClass.getParentDomain());
					}
				} catch (MappingException e) {
					problems.addProblem(" failed with " + e.getMessage(), e);
				} catch (Throwable e) {
					problems.addProblem(e.getMessage(), e);
				}
				monitor.worked(1);
				if (monitor.isCanceled())
					break;
			}
			if (!problems.getProblems().isEmpty())
				throw problems;
			if (monitor.isCanceled())
				throw new InterruptedException();
			if(modules!=null && modules.iterator().hasNext())
			{
				modules = null;
			}
		} catch (ApplicationsImportProblemsException e) {
			String msg = e.getMessage();
			LOGGER.error(msg, e);
			monitor.done();
			handleReadingXmlfilesStatus(e, xmlFilesToImport.size());
		}
			
		try {
			if (!isWritingCancled) {
				monitor.subTask("Writing to model " + xmlFilesToImport.toString());
				MdfDomainSerializer.serializeHelpText(readDomain, monitor);
			}
		} catch (CoreException e) {
			InvocationTargetException invocationException = new InvocationTargetException(e,
					"Importing T24 Helptexts failed during Writing Domians");
			Throwable target = invocationException
					.getTargetException() != null ? invocationException
					.getTargetException() : e;
			String errorMsg = target.toString();
			LOGGER.error(errorMsg, target);
			handleWritingFilesStatus(e, target);
		}
					
		monitor.done();
	}
	
	private void populateDomainClasses(IProject project) {
		List<MdfDomain> domains = DomainRepositoryUtil.getAllMdfDomain(project);
		for (MdfDomain mdfDomain : domains) {
			if (((EObject) mdfDomain).eIsProxy()) {
				break;
			}
			@SuppressWarnings("unchecked")
			List<MdfClass> classes = mdfDomain.getClasses();
			for (MdfClass mdfClass : classes) {
				domainClassMap.put(mdfClass.getName(), mdfClass);
			}
		}
	}

	/**
	 * get the MdfClass for the given Module
	 */
	public MdfClass getMdfClass(T24Help module) throws MappingException {
		if(module.getHeader()==null) 
			return null;
		String class_name =  module.getHeader().getProduct().trim();
		String class_table = module.getHeader().getTable().trim();
		if (class_table!=null && !class_table.isEmpty()) {
			class_table = class_table.replace(".", "_");
		}else
			throw new MappingException("No title - BUG in T24 Helptext XML");
		MdfClassImpl domClass = (MdfClassImpl) getMatchingDomainClass(class_name,class_table);
		if (domClass == null) {
			throw new MappingException("No Class found for Helptext file "+ class_table +".xml. ");
		}
		StringBuffer classDocs = new StringBuffer("");
		String clssDocs = computeClassDocs(module.getOverview(), classDocs); 
		
		if(!StringUtils.isEmpty(clssDocs)) {
			domClass.setDocumentation(clssDocs);
		}
		mapper.map(module, domClass);
		
		return domClass;
	}

	private String computeClassDocs(com.odcgroup.t24.helptext.schema.Overview overview, StringBuffer classDocs) {
		if (overview!=null ) {
			if (overview.getOvdesc()!=null && !overview.getOvdesc().getAny().isEmpty()) {
				for (Object text : overview.getOvdesc().getAny()) {
					if(text instanceof ElementNSImpl){
						classDocs.append(((ElementNSImpl)text).getTextContent().trim());
					}
					else if (text instanceof String) {
			        	classDocs.append(text.toString().trim());
			        }
					classDocs.append("\n");
				}
			}
		}
		return classDocs.toString();
	}

	/**
	 * get the class if it exists with the given application name and module
	 */
	public MdfClass getMatchingDomainClass(String moduleName, String className) throws MappingException {				
		if (!domainClassMap.isEmpty()) {
			return domainClassMap.get(className);
		}
		return null;
	}
	
    /**
     * set the cancle status of the Writing.
     * @param boolean the status of the dialog.
     */
	private void setCancledStatus(boolean cancled) {
	    this.isWritingCancled =cancled;
	}
	
	/**
	 * handle the Reading Xml files errors.
	 * @param ApplicationImportProblemException.
	 */
	protected void handleReadingXmlfilesStatus(ApplicationsImportProblemsException readingFileException,final int per) {
	    final MultiStatus multiStatus  =new MultiStatus(OfsCore.PLUGIN_ID, Status.OK, "Importing T24 Helptext failed during reading T24 Helptexts XML",null);
	    multiStatus.add(readingFileException.getErrorStatus());
	    final int prob = readingFileException.getProblems().size();
	    int percentage = (prob / per )* 100;
	    readingFileException.addProblem("T24 Helptexts Import could not load "+percentage+" % of files.");
	    Display.getDefault().syncExec(new Runnable() {
			public void run() {
			    String errorString ="T24 Helptexts Import found errors while reading some of selected files. Click on \"Continue\" to pursue the import, skipping files in error or click on \"Cancel\" to abort the process.";
			    ImportErrorDilaog dialog =new ImportErrorDilaog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Import Helptexts",errorString,multiStatus,IStatus.ERROR);
			    if(dialog.open()==IDialogConstants.CANCEL_ID){
			    	setCancledStatus(true);
			    }
			}
	    });
	}
	
       /**
        * handle the exceptions status while writing the Domain files.
        * @param e
        * @param target
        */
	protected void handleWritingFilesStatus(CoreException coreException ,Throwable target) {
	    MultiStatus multiStatus  =new MultiStatus(OfsCore.PLUGIN_ID, Status.OK, "Importing T24 Helptexts failed during Writing Domians",null);
	    if( target instanceof ApplicationsImportProblemsException ) {
	    	multiStatus.add(((ApplicationsImportProblemsException)(target)).getErrorStatus());
	    }
	    else{
	    	multiStatus.add(coreException.getStatus());
	    }
	    if(multiStatus.getChildren().length>0){	
			//show the error dialog if any exceptions while reading and writting.	
			StatusManager.getManager().handle(multiStatus,StatusManager.SHOW);
	    }
	}

}
