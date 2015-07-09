package com.odcgroup.process.migration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.slf4j.Logger;

import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.Task;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * @author yan
 */
public class ProcessTranslationMigrator implements ITranslationModelMigrator {

	private static final String WORKFLOW_SUFFIX = "process.";

	private Map<String, String> saveOptions;
	
	private Logger migrationLogger;
	
	private IOfsProject ofsProject;
	
	private Set<Resource> modifiedElements = new HashSet<Resource>();
	

	public ProcessTranslationMigrator() {
		saveOptions = new HashMap<String, String>();
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
	}

	@Override
	public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		if (null == migrationLogger || null == ofsProject) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		this.migrationLogger = migrationLogger;
		this.ofsProject = ofsProject;
	}

	@Override
	public boolean isKeyAccepted(String key) {
		return key.startsWith(WORKFLOW_SUFFIX);
	}

	@Override
	public boolean process(TranslationVO vo) {
		String key = vo.key;
		ITranslationKind kind;
		final String NAME_SUFFIX = ".name"; 
		final String DESCR_SUFFIX = ".description"; 
		if (vo.key.endsWith(NAME_SUFFIX)) {
			kind = ITranslationKind.NAME;
			key = StringUtils.removeEnd(key, NAME_SUFFIX);
		} else if (vo.key.endsWith(DESCR_SUFFIX)) {
			kind = ITranslationKind.TEXT;
			key = StringUtils.removeEnd(key, DESCR_SUFFIX);
		} else {
			vo.errorStatus = "Unable to define the translation kind by the suffix";
			return false;
		}
		
		final String TASK_PREFIX = "process.task.";
		final String WORKFLOW_PREFIX = "process.";
		EObject workflowModelElement; 
		if (key.startsWith(TASK_PREFIX)) {
			// Search for a task
			key = StringUtils.removeStart(key, TASK_PREFIX);
			workflowModelElement = findWorkflowTaskModel(key);
		} else if (key.startsWith(WORKFLOW_PREFIX)) {
			// search for a workflow (root object)
			key = StringUtils.removeStart(key, WORKFLOW_PREFIX);
			workflowModelElement = findWorkflowModel(key);
		} else {
			vo.errorStatus = "Unable to define the model type associated to the translation";
			return false;
		}
		
		if (workflowModelElement == null) {
			// Skip translation migration of not found models
			vo.errorStatus = "Unable to find the associated model";
			return false;
		}
		
		if (!pushTranslationInModel(workflowModelElement, kind, new Locale(vo.language), vo.text)) {
			vo.errorStatus = "Unable to define the translation in the model";
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	protected boolean pushTranslationInModel(EObject workflowModelElement, ITranslationKind kind, Locale locale, String text) {
		if (workflowModelElement != null) {
			ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
			ITranslation translation = manager.getTranslation(workflowModelElement);
			try {
				translation.setText(kind, locale, text);
				modifiedElements.add(workflowModelElement.eResource());
				return true;
			} catch (TranslationException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @param ofsProject
	 * @param modelURI
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected EObject findWorkflowTaskModel(String key) {
		String processName;
		int index = key.indexOf(".");
		if (index != -1) {
			processName = key.substring(0, index);
			key = key.substring(index+1); // the word before the '.' is the taskid
		} else {
			migrationLogger.error("Unable to find task name in " + key);
			return null;
		}
		Process workflow = findWorkflowModel(processName);
		if (workflow != null) {
			for (Pool pool : (List<Pool>)workflow.getPools()) {
				for (Task task : (List<Task>)pool.getTasks()) {
					if (task.getID().equalsIgnoreCase(key)) {
						return task;
					}
				}
			}
			migrationLogger.error("No task with an id of " + key + " (case unsensitive)");
			return null;
		}
		return null;
	}

	/**
	 * @param ofsProject
	 * @param modelURI
	 * @param lowercaseModelName
	 * @return
	 * @throws InvalidMetamodelVersionException 
	 * @throws IOException 
	 */
	protected Process findWorkflowModel(String processName) {
		
		Process process = null;

		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, new String[]{"workflow"});
		for (IOfsModelResource ofsResource : lookup.getAllOfsModelResources(IOfsProject.SCOPE_PROJECT)) {
			try {
				Process prss = (Process)ofsResource.getEMFModel().get(0);
				if (processName.equalsIgnoreCase(prss.getName())) {
					process = prss;
					break;
				}
			} catch (IOException ex) {
				migrationLogger.error("Unable to read " + ofsResource.getName(), ex);
			} catch (InvalidMetamodelVersionException ex) {
				migrationLogger.error("Unable to read " + ofsResource.getName(), ex);
			}
		}
		
//		for (Resource resource : lookup.getAllOfsModelResources(IOfsProject.SCOPE_PROJECT)) {
//			if (StringUtils.containsIgnoreCase(resource.getURI().lastSegment(), lowercaseModelName)) {
//				IOfsModelResource ofsResource = ofsProject.getModelResourceSet().getOfsModelResource(resource.getURI());
//				try {
//					return (Process)ofsResource.getEMFModel().get(0);
//				} catch (Exception e) {
//					migrationLogger.error("Unable to read " + ofsResource.getName(), e);
//					return null;
//				}
//			}
//		}
		if (null == process) {
			migrationLogger.error("Unable to find the workflow related to " + processName);
		}
		return process;
	}

	@Override
	public List<TranslationVO> endMigration() throws TranslationMigrationException {
		for (Resource resource: modifiedElements) {
			try {
				migrationLogger.trace("Saving modifications of " + resource.getURI());
				resource.save(saveOptions);
			} catch (IOException e) {
				migrationLogger.error("Unable to save " + resource.getURI(), e);
				throw new TranslationMigrationException("Unable to save " + resource.getURI(), e);
			}
		}
		return new ArrayList<TranslationVO>();
	}

}
