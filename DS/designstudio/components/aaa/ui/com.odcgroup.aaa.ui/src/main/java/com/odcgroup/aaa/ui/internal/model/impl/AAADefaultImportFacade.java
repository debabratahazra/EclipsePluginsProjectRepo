package com.odcgroup.aaa.ui.internal.model.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.SaveOptions;

import com.odcgroup.aaa.connector.domainmodel.TypeEntity;
import com.odcgroup.aaa.connector.formats.FormatCriteria;
import com.odcgroup.aaa.connector.internal.InvalidMetaDictException;
import com.odcgroup.aaa.connector.internal.InvalidTripleAVersionException;
import com.odcgroup.aaa.connector.internal.util.FormatVO;
import com.odcgroup.aaa.connector.internal.util.FunctionVO;
import com.odcgroup.aaa.connector.internal.util.ImportReportVO;
import com.odcgroup.aaa.connector.internal.util.TAConnectionInfo;
import com.odcgroup.aaa.connector.internal.util.TADatabase;
import com.odcgroup.aaa.connector.internal.util.TADatabaseConnection;
import com.odcgroup.aaa.connector.mdfmml.MetaDictDomains;
import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;
import com.odcgroup.aaa.ui.internal.model.AAAFormatType;
import com.odcgroup.aaa.ui.internal.model.AAAFunction;
import com.odcgroup.aaa.ui.internal.model.ConnectionInfo;
import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ext.tangij.TANGIJTranslationAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.resources.OfsProject;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAADefaultImportFacade extends AAAAbstractImportFacade {
	
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(AAADefaultImportFacade.class);
	
	// Save statically so we only initialize the EntityManagerFactory once (long operation)
	private static TADatabase taDatabase;
	
	private TADatabaseConnection taDBConnection;
	
	private static final AAAFormatType ALL_FORMAT_TYPES = new AAAFormatTypeAdapter("All", null);
	
	private static final AAAFunction ALL_FUNCTIONS = new AAAFunctionAdapter("All", null);
	
	protected void doConfigureConnectionFactory(ConnectionInfo connectionInfo) throws InvocationTargetException {
		// Replace existing connection information ?
		if (taDatabase != null) {
			// Close the underlying EntityManagerFactory
			taDatabase.close();
			taDatabase = null;
		}
		
		// Create a connection info object used by the import library 
		TAConnectionInfo taConnectionInfo = new TAConnectionInfo();
		taConnectionInfo.setHostname(connectionInfo.getServer());
		taConnectionInfo.setDBName(connectionInfo.getDatabase());
		try {
			taConnectionInfo.setPort(Integer.parseInt(connectionInfo.getPort()));
		} catch (NumberFormatException e) {
			throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.invalid.connection")));
		}
		taConnectionInfo.setCharset(connectionInfo.getCharset());
		taConnectionInfo.setUsername(connectionInfo.getUsername());
		taConnectionInfo.setPassword(connectionInfo.getPassword());
		
		// Create the connection factory (EntityManagerFactory)
		try {
			taDatabase = new TADatabase(taConnectionInfo);
		} catch (Exception e) {
			LOGGER.error("Unable to create TADatabase", e);
			throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.invalid.connection")));
		}

		// Test the connection
		TADatabaseConnection connection = null;
		try {
			connection = taDatabase.newTADatabaseConnection();
			connection.testConnection();
		} catch (Exception e) {
			if (connection != null) {
				connection.close();
			}
			if (taDatabase != null) {
				taDatabase.close();
				taDatabase = null;
			}
			/*Not sure why but openJPA is unhappy is we catch the individual exceptions. Showing an error of "Context 
			has been closed" Guess it is overly-eagerly closing the EntityManager. So we have to do this instanceof nastiness.*/
			if (e instanceof InvalidTripleAVersionException) {
				LOGGER.error("Invalid TripleA Version", e);
				throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.invalid.triplea.version", e.getMessage())));
			} else if(e instanceof InvalidMetaDictException) {
				LOGGER.error("The meta-dict which is being imported seems to be invalid. Please check the logs for details.", e);
				//Connection has been successful but the metadict is invalid
				//we should still store the connection info.
				getPreferences().save(connectionInfo);
				throw new InvocationTargetException(new InvalidMetaDictException(Messages.getString("aaa.wizard.invalid.metadict"), e.getMessage()));
			} else {
				LOGGER.error("Invalid connection", e);
				throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.invalid.connection")));
			}
		}
		connection.close();
	}
	
	
	/* 
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doConnect(com.odcgroup.mdf.aaa.integration.ui.model.ConnectionInfo)
	 */
	protected boolean doConnect() {
		// No connection initialized ?
		if (taDatabase == null) {
			LOGGER.error("Unable to connect (no database initilized)");
			return false;
		}
		
		try {
			taDBConnection = taDatabase.newTADatabaseConnection();
			return true;
		} catch (Exception e) {
			LOGGER.error("Unable to connect", e);
			return false;
		}
	}

	/* 
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doConnect(com.odcgroup.mdf.aaa.integration.ui.model.ConnectionInfo)
	 */
	protected boolean doConnectForMetaDict() {
		// No connection initialized ?
		if (taDatabase == null) {
			LOGGER.error("Unable to connect (no database initilized)");
			return false;
		}
		
		try {
			taDBConnection = taDatabase.newTADatabaseConnection();
			// validate meta dict before importing
			return taDBConnection.testConnection();
		} catch (Exception e) {
			LOGGER.error("Unable to connect", e);
			return false;
		}
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doDisconnect()
	 */
	protected void doDisconnect() {
		if (taDBConnection != null) {
			try {
				taDBConnection.close();
			} catch (Exception e) {
				LOGGER.error("Unable to close the database connection", e);
			}
		}
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doImportMetaDictionary()
	 */
	protected boolean doImportMetaDictionary(IOfsProject ofsProject, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
		String aaaOutputFolder = MetaDictionnaryHelper.getAAAMetadictionaryFolder(ofsProject);
		if (aaaOutputFolder == null) {
			return false;
		}
		
		// Find the T'A metadictionary business types
		MdfDomain metaDictBusinessTypes = MetaDictionnaryHelper.getAAAMetadictionaryBusinessTypes(ofsProject);
		if (metaDictBusinessTypes == null) {
			return false;
		}

		((MdfDomainImpl)metaDictBusinessTypes).setName(metaDictBusinessTypes.getName().replace("AAA", AAACore.getFindroot().toUpperCase()));
		((MdfDomainImpl)metaDictBusinessTypes).setNamespace(metaDictBusinessTypes.getNamespace().replace("aaa", AAACore.getFindroot().toLowerCase()));

		// Invoke the metadictionary library
		boolean autoBuild = true;
		try {
			// turn off auto-building
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription desc = ws.getDescription();
			autoBuild = desc.isAutoBuilding();
			desc.setAutoBuilding(false);
			ws.setDescription(desc);

			monitor.worked(1);
			MetaDictDomains domains = taDBConnection.getMetaDictionaryDomains(false, metaDictBusinessTypes);
			monitor.worked(4);
			if(monitor.isCanceled()) throw new InterruptedException();
			
			//TODO - refactoring: when available use the new interface ITranslationImportService
			monitor.setTaskName(Messages.getString("aaa.wizard.metadict.import.translations"));
			processAllTranslations(ofsProject, domains.entitiesDomain);
			monitor.worked(1);
			if(monitor.isCanceled()) throw new InterruptedException();

			processAllTranslations(ofsProject, (MdfDomain)domains.enumerationsDomain);
			monitor.worked(1);
			if(monitor.isCanceled()) throw new InterruptedException();

			monitor.setTaskName(Messages.getString("aaa.wizard.metadict.import.writing.enums"));
			writeMdfFile(domains.enumerationsDomain, aaaOutputFolder, AAACore.AAA_ENUMS_MODEL.substring(AAACore.AAA_ENUMS_MODEL.lastIndexOf('/')+1));
			monitor.worked(1);

			monitor.setTaskName(Messages.getString("aaa.wizard.metadict.import.writing.entities"));
			writeMdfFile(domains.entitiesDomain, aaaOutputFolder, AAACore.AAA_ENTITIES_MODEL.substring(AAACore.AAA_ENTITIES_MODEL.lastIndexOf('/')+1));
			monitor.worked(1);
			if(monitor.isCanceled()) throw new InterruptedException();

			IOfsModelFolder domainFolder = ofsProject.getModelFolder("domain");
			domainFolder.getResource().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			
			// DS-2409 after replacing the domain files, we need to clear the domain cache by unloading all mdf files
            for(Resource resource : ofsProject.getModelResourceSet().getResources()) {
            	if(resource.isLoaded() && resource.getURI().fileExtension().equals("domain")) resource.unload();
            }
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Unable to import the meta-dictionary to " + ofsProject.getName(), e);
			return false;
		} finally {
			if(autoBuild) {
				// turn on auto-building
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription desc = ws.getDescription();
				desc.setAutoBuilding(true);
				try {
					ws.setDescription(desc);
				} catch (CoreException e) {
					LOGGER.error("Failed to enable auto-building again", e);
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doImportFormats()
	 */
	protected ImportReportVO doImportFormats(IOfsModelPackage ofsPackage, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {		
		// Find the T'A metadictionary entities
		MdfDomain metaDictEntities = MetaDictionnaryHelper.getAAAMetadictionaryEntities(ofsPackage.getOfsProject());
		if (metaDictEntities == null) {
			throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.metadict.entities.not.found", AAACore.getFindroot()+AAACore.AAA_ENTITIES_MODEL)));
		}
		
		// Find the T'A metadictionary enums
		MdfDomain metaDictEnums = MetaDictionnaryHelper.getAAAMetadictionaryEnums(ofsPackage.getOfsProject());
		if (metaDictEnums == null) {
			throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.metadict.enums.not.found", AAACore.getFindroot()+AAACore.AAA_ENUMS_MODEL)));
		}
		
		// Find the T'A metadictionary business types
		MdfDomain metaDictBusinessTypes = MetaDictionnaryHelper.getAAAMetadictionaryBusinessTypes(ofsPackage.getOfsProject());
		if (metaDictBusinessTypes == null) {
			throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.metadict.business.type.not.found", AAACore.getFindroot()+AAACore.AAA_BUSINESS_TYPE_MODEL)));
		}
		
		String formatOutputFolder = getFormatsOutputFolder(ofsPackage);
		if (formatOutputFolder == null) {
			throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.formats.import.not.found")));
		}

		// Invoke the metadictionary library
		boolean autoBuild = true;
		try {
			// turn off auto-building
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription desc = ws.getDescription();
			autoBuild = desc.isAutoBuilding();
			desc.setAutoBuilding(false);
			ws.setDescription(desc);
			
			if(monitor.isCanceled()) throw new InterruptedException();
			monitor.worked(1);
			
			// 1. build the list of the selected dict functions & codes 
			Set<String> selectedFunctions = new HashSet<String>();
			Set<String> selectedCodes = new HashSet<String>();
			for (AAAFormatCode formatCode: getSelectedFormatCodes()) {
				selectedFunctions.add(formatCode.getFunction() + ".domain");
				selectedCodes.add(formatCode.getCode());
			}
			
			// 2. find existing mdf, to pass them to the import lib
			Map<String, MdfDomainImpl> mdfDomains = new HashMap<String, MdfDomainImpl>();
			Collection<IOfsModelResource> models = ofsPackage.getModels();
			for (IOfsModelResource model : models) {
				if (selectedFunctions.contains(model.getName())) {
					MdfDomainImpl mdfDomain = readDomainFile(formatOutputFolder, model.getName());
					if (mdfDomain != null) {
						mdfDomain.setName(mdfDomain.getName().replace("AAA", AAACore.getFindroot().toUpperCase()));
						mdfDomain.setNamespace(mdfDomain.getNamespace().replace("aaa", AAACore.getFindroot()));
						mdfDomains.put(model.getName(), mdfDomain);
					}
				}
			}
			
			// 3. create the criteria
			FormatCriteria criteria = new FormatCriteria(selectedCodes);
			
			// 4. read the formats
			monitor.setTaskName(Messages.getString("aaa.wizard.formats.import.reading"));
			ImportReportVO reportVO = taDBConnection.addClassesFromFormats(criteria, mdfDomains, metaDictEntities, metaDictEnums, metaDictBusinessTypes);
			monitor.worked(5);
			if(monitor.isCanceled()) throw new InterruptedException();

			// 5. process the translations
			monitor.setTaskName(Messages.getString("aaa.wizard.formats.import.translating"));
			for (Map.Entry<String, MdfDomainImpl> domainEntry : mdfDomains.entrySet()) {
				processAllTranslations(ofsPackage.getOfsProject(), domainEntry.getValue());
			}
			monitor.worked(2);
			if(monitor.isCanceled()) throw new InterruptedException();
			
			// 6. Write the resulting files
			monitor.setTaskName(Messages.getString("aaa.wizard.formats.import.writing.domains"));
			String javaPackage = getJavaPackage(ofsPackage);
			for (Map.Entry<String, MdfDomainImpl> domainEntry : mdfDomains.entrySet()) {
				JavaAspectDS.setPackage(domainEntry.getValue(), javaPackage);
				writeMdfFile(domainEntry.getValue(), formatOutputFolder, domainEntry.getKey());
				if(monitor.isCanceled()) throw new InterruptedException();
			}

			monitor.setTaskName(Messages.getString("aaa.wizard.formats.import.writing.formats"));
//			IFolder newFormatOutputFolder = ofsPackage.getResource().getProject().getFolder("format");
			ModelResourceSet rs = ofsPackage.getOfsProject().getModelResourceSet();
//			if(dsFormats!=null) {
//				for (Map.Entry<String, Format> formatEntry : dsFormats.entrySet()) {
//					rs.createOfsModelResource(newFormatOutputFolder.getFile(formatEntry.getKey()), IOfsProject.SCOPE_PROJECT);
//					Resource res = rs.getResource(URI.createURI("resource:///" + formatEntry.getKey()), false);
//					res.getContents().add(formatEntry.getValue());
//					try {
//						res.save(null);
//					} catch(RuntimeException re) {
//						LOGGER.error("Could not serialize format " + formatEntry.getKey());
//						newFormatOutputFolder.getFile(formatEntry.getKey()).delete(true, null);
//					}
//					if(monitor.isCanceled()) throw new InterruptedException();
//				}
//			}
			ofsPackage.getResource().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			//unload the model after import and load it again.
			for(Map.Entry<String, MdfDomainImpl> domainEntry : mdfDomains.entrySet()){
				Resource resource=rs.getResource(URI.createURI(ofsPackage.getURI().toString()+"/"+domainEntry.getKey()), OfsProject.SCOPE_PROJECT);
				resource.unload();
			}
			monitor.worked(1);
			
			return reportVO;
			
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Unable to import the format to " + formatOutputFolder, e);
			throw new InvocationTargetException(new Exception("Unable to import the format to " + formatOutputFolder));
		} finally {
			if(autoBuild) {
				// turn on auto-building
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription desc = ws.getDescription();
				desc.setAutoBuilding(true);
				try {
					ws.setDescription(desc);
				} catch (CoreException e) {
					LOGGER.error("Failed to enable auto-building again", e);
					throw new InvocationTargetException(e);
				}
			}
		}
	}


	private String getJavaPackage(IOfsModelPackage ofsPackage) {
		String pack = ofsPackage.getName();

		IOfsElement parent = ofsPackage.getParent();
		while (parent != null && parent instanceof IOfsModelPackage) {
			IOfsModelPackage parentPack = (IOfsModelPackage)parent;
			pack = parentPack.getName() + "." + pack;
			parent = parentPack.getParent();
		}
		return pack;
	}
	
	/**
	 * Write the domain to a file
	 * @param domain
	 * @param outputDirectory
	 * @param forceFileName
	 * @throws IOException
	 */
    private void writeMdfFile(MdfDomainImpl domain, String outputDirectory, final String forceFileName) throws IOException {
    	if(forceFileName==null) throw new RuntimeException("filename has not been supplied");

        IFile outputFile;
		try {
			java.net.URI uri = new java.net.URI("file:///" + outputDirectory);
	        IContainer[] containers = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocationURI(uri);
	        if(containers.length==0) {
	        	throw new IOException("Cannot resolve output directory");
	        }
	        IContainer outputContainer = containers[0];
	        if(!outputContainer.exists() && outputContainer instanceof IFolder) {
	        	OfsCore.createFolder((IFolder) outputContainer);
	        }
	        outputFile = outputContainer.getFile(new Path(forceFileName));
		} catch (URISyntaxException e) {
			throw new IOException("Cannot resolve output directory", e);
		} catch (CoreException e) {
			throw new IOException("Cannot create output directory", e);
		}

		IOfsProject ofsProject = OfsCore.getOfsProject(outputFile.getProject());
        if(!outputFile.exists()) {
    		ofsProject.getModelResourceSet().createOfsModelResource(outputFile, IOfsProject.SCOPE_PROJECT);
        }
        Resource resource = ofsProject.getModelResourceSet().getResource(ModelURIConverter.createModelURI((IResource)outputFile), false);
        resource.getContents().clear();
        resource.getContents().add(domain);
        
//        removeAllAnnotations(domain);
        
        Map<Object, Object> options = SaveOptions.newBuilder().format().noValidation().getOptions().toOptionsMap();
        resource.save(options);
    }
    
	private MdfDomainImpl readDomainFile(String formatOutputFolder, String domainFilename) throws IOException {
		try {
	        java.net.URI uri = new java.net.URI("file:///" + formatOutputFolder);
	        IContainer[] containers = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocationURI(uri);
	        if(containers.length==0) throw new IOException("Cannot resolve output directory");
	        IContainer outputContainer = containers[0];
	        IFile domainFile = outputContainer.getFile(new Path(domainFilename));
			IOfsProject ofsProject = OfsCore.getOfsProject(domainFile.getProject());
			IOfsModelResource modelResource = ofsProject.getOfsModelResource(ModelURIConverter.createModelURI((IResource) domainFile));
			return (MdfDomainImpl) modelResource.getEMFModel().get(0);
		} catch (URISyntaxException e) {
			throw new IOException("Cannot resolve format output directory", e);
		} catch (MalformedURLException e) {
			throw new IOException(e);
		} catch (ModelNotFoundException e) {
			LOGGER.warn(e);
		} catch (InvalidMetamodelVersionException e) {
			LOGGER.error(e);
		}
		return null;
    }
    
	/**
	 * @param ofsPackage
	 * @return
	 */
	private String getFormatsOutputFolder(IOfsModelPackage ofsPackage) {
		try {
			return ofsPackage.getResource().getLocation().toPortableString();
		} catch (Exception e) {
			LOGGER.error("Unable to get formats folder", e);
		}
		return null;
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doSynchronizeFormats()
	 */
	protected boolean doSynchronizeFormats(IOfsModelResource ofsResource, MdfModelElement formatElement, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {

		String resourcePath = ofsResource.getResource().getLocation().toPortableString();
		String formatOutputFolder = resourcePath.substring(0, resourcePath.lastIndexOf("/"));
		
		IOfsProject ofsProject = ofsResource.getOfsProject();
		
		// Find the T'A metadictionary entities
		MdfDomain metaDictEntities = MetaDictionnaryHelper.getAAAMetadictionaryEntities(ofsProject);
		if (metaDictEntities == null) {
			return false;
		}
		
		// Find the T'A metadictionary enums
		MdfDomain metaDictEnums = MetaDictionnaryHelper.getAAAMetadictionaryEnums(ofsProject);
		if (metaDictEnums == null) {
			return false;
		}

		// Find the T'A metadictionary business types
		MdfDomain metaDictBusinessTypes = MetaDictionnaryHelper.getAAAMetadictionaryBusinessTypes(ofsProject);
		if (metaDictBusinessTypes == null) {
			return false;
		}
		
		// Invoke the metadictionary library
		boolean autoBuild = true;
		try {
			String domainName = ofsResource.getName();
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription desc = ws.getDescription();
			autoBuild = desc.isAutoBuilding();
			desc.setAutoBuilding(false);
			ws.setDescription(desc);
			if(monitor.isCanceled()) throw new InterruptedException();
			monitor.worked(5);

			monitor.setTaskName(Messages.getString("aaa.wizard.synchronize.reading.models"));
			MdfDomainImpl mdfDomain = readDomainFile(formatOutputFolder, domainName);
			
			Map<String, MdfDomainImpl> mdfDomains = new HashMap<String, MdfDomainImpl>();
			mdfDomains.put(domainName, mdfDomain);
			monitor.worked(10);
			if(monitor.isCanceled()) throw new InterruptedException();

			// Create the criteria containing the list of code to update
			MdfClass mdfClass = (MdfClass)formatElement;
			Set<String> codesToUpdate = new HashSet<String>();
			codesToUpdate.add(mdfClass.getName());
			FormatCriteria criteria = new FormatCriteria(codesToUpdate);
			
			monitor.setTaskName(Messages.getString("aaa.wizard.synchronize.running"));

			taDBConnection.addClassesFromFormats(criteria, mdfDomains, metaDictEntities, metaDictEnums, metaDictBusinessTypes);
			
			if(monitor.isCanceled()) throw new InterruptedException();
				
			monitor.setTaskName(Messages.getString("aaa.wizard.synchronize.updating.formats"));
			writeMdfFile(mdfDomain, formatOutputFolder, domainName);
			
			ofsResource.getResource().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) { // TODO check error raised from metadict lib
			LOGGER.error("Unable to synchronize the format to " + formatOutputFolder, e);
			return false;
		} finally {
			if (autoBuild) {
				// turn on auto-building
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription desc = ws.getDescription();
				desc.setAutoBuilding(true);
				try {
					ws.setDescription(desc);
				} catch (CoreException e) {
					LOGGER.error("Failed to enable auto-building again", e);
					throw new InvocationTargetException(e);
				}
			}
		}

		return true;
	}

	/**
	 * Process translation associated to metadictionary and
	 * formats import. The import process produces only translations 
	 * for:
	 * <ul>
	 * <li>MdfClass</li>
	 * <li>MdfEnumValue</li>
	 * <li>MdfAssociation</li>
	 * <li>MdfAttribute</li>
	 * <li>MdfReverseAssociation</li>
	 * </ul>
	 * @param domain
	 * @throws TranslationServiceException 
	 * @throws CoreException 
	 */
	@SuppressWarnings("unchecked")
	private void processAllTranslations(IOfsProject ofsProject, MdfDomain domain) throws CoreException {
		Set<String> foundLanguages = new HashSet<String>();
		
		for (MdfClass klass : (List<MdfClass>)domain.getClasses()) {
			// Process class translation
			addLanguagesFound(klass, foundLanguages);
			for (MdfProperty property : (List<MdfProperty>)klass.getProperties()) {
				// Process property translation (attribute, association, reverse)
				addLanguagesFound(property, foundLanguages);
			}
		}
		for (MdfEnumeration enumeration : (List<MdfEnumeration>)domain.getEnumerations()) {
			// The enumeration itself do not have translations, only values
			for (MdfEnumValue enumValue : (List<MdfEnumValue>)enumeration.getValues())
				addLanguagesFound(enumValue, foundLanguages);
		}

		// Update the favorite languages
		ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
		ITranslationPreferences prefs = manager.getPreferences();
		Set<Locale> locales = new HashSet<Locale>();
		for (String language: foundLanguages) {
			locales.add(new Locale(language));
		}
		prefs.addAdditionalLocale(locales);

	}
	
	/**
	 * update the language found
	 * @param ofsProject
	 * @param holder
	 * @param model
	 * @throws TranslationServiceException 
	 * @throws CoreException 
	 */
	private void addLanguagesFound(MdfModelElement model, Set<String> languagesFound) throws CoreException {
		// Retrieve the translation as annotations
		Map<String, String> translations = TANGIJTranslationAspect.getTranslations(model);
		// Add all found languages to the set of found languages 
		languagesFound.addAll(translations.keySet());
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doGetFormatTypes()
	 */
	protected List<AAAFormatType> doGetFormatTypes() {
		List<AAAFormatType> formatTypes = new ArrayList<AAAFormatType>();

		List<TypeEntity> allFormatType = taDBConnection.getAllFormatTypes();
		formatTypes.add(ALL_FORMAT_TYPES);
		for (TypeEntity type: allFormatType) {
			formatTypes.add(new AAAFormatTypeAdapter(type.getDenom(), type));
		}
		
		return formatTypes;	
	}

	/* 
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.impl.AAAAbstractImportFacade#doGetFormatCodes()
	 */
	protected List<AAAFormatCode> doGetFormatCodes() {
		List<FormatVO> allFormats = taDBConnection.getAllFormats();

		List<AAAFormatCode> formatCodes = new ArrayList<AAAFormatCode>();
		for (FormatVO format : allFormats) {
			formatCodes.add(new AAAFormatCodeAdapter(format.getCode(), format.getFunction().getCamelCaseProcNameFunction(), format.getDenom()));
		}
		return formatCodes;
	}

	protected List<AAAFunction> doGetFunctions() {
		List<AAAFunction> functions = new LinkedList<AAAFunction>();

		List<FunctionVO> allFunctions = taDBConnection.getAllFunctions();
		for (FunctionVO function : allFunctions) {
			functions.add(new AAAFunctionAdapter(function.getCamelCaseProcNameFunction(), function.getProcNameFunction()));
		}
		
		Collections.sort(functions, new Comparator<AAAFunction>(){
			public int compare(AAAFunction o1, AAAFunction o2) {
				return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
			}
		});

		// Add "All" at the top of the list
		functions.add(0, ALL_FUNCTIONS);

		return functions;
	}
	
	public AAADefaultImportFacade() {
		
	}
}
