package com.odcgroup.mdf.editor.ui.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslationHelper;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelMatcher;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * Provides services to synchronize Datasets.
 * Theses services has been moved from class SynchronizeDatasetsAction in order to improve reuse.
 *
 * @author atr
 * @since DS 1.40.0
 */
public final class DatasetFacility {
	
	private IOfsProject ofsProject;

	/**
	 */
	public DatasetFacility() {
	}

	/**
	 * @param monitor
	 * @param ofsElement
	 */
	public void synchronizeDatasets(IProgressMonitor monitor, IOfsElement ofsElement) throws CoreException {
		if (ofsElement instanceof IOfsModelFolder) {
			synchronizeDatasets(monitor, (IOfsModelFolder)ofsElement);
		} else if (ofsElement instanceof IOfsModelPackage) {
			synchronizeDatasets(monitor, (IOfsModelPackage)ofsElement);
		} else if (ofsElement instanceof IOfsModelResource) {
			synchronizeDatasets(monitor, (IOfsModelResource)ofsElement);
		}
	}

	/**
	 * @param monitor
	 * @param modelFolder
	 */
	public void synchronizeDatasets(IProgressMonitor monitor, IOfsModelFolder modelFolder) throws CoreException {
		synchronizeDatasetsInDomains(monitor, fetchAllDomains(monitor, modelFolder));
	}
	
	/**
	 * @param monitor
	 * @param modelFolder
	 */
	public void synchronizeDatasets(IProgressMonitor monitor, IOfsModelPackage modelpackage) throws CoreException {
		synchronizeDatasetsInDomains(monitor, fetchAllDomains(monitor, modelpackage));
	}
	
	/**
	 * @param monitor
	 * @param modelFolder
	 */
	public void synchronizeDatasets(IProgressMonitor monitor, IOfsModelResource modelResource) throws CoreException {
		synchronizeDatasetsInDomains(monitor, fetchAllDomains(monitor, modelResource));
	}
	
	/**
	 * @param monitor
	 * @param domains
	 */
	private void synchronizeDatasetsInDomains(IProgressMonitor monitor, Collection<MdfDomain> domains) throws CoreException {
		for (MdfDomain domain : domains) {
			// see if the resource exists / read-only
			Resource res = ((MdfDomainImpl) domain).eResource();
			IOfsModelResource mResource = null;
			try {
				mResource = ofsProject.getOfsModelResource(res.getURI());
			} catch (ModelNotFoundException e1) {
				mResource = null;
			}
			if (mResource != null && !mResource.isReadOnly()) {
				monitor.beginTask("Synchronizing datasets for Domain \'" + domain.getQualifiedName().toString()
						+ "\'", 1);
				List<MdfDataset> datasets = domain.getDatasets();
				boolean changed = false;
				for (MdfDataset dataset : datasets) {
					if (dataset.isSync()) { // is marked sync
						List<MdfDatasetMappedProperty> properties = 
							DeriveBaseClassUtil.getDatasetMappedProperties(ofsProject, dataset);
						if (!properties.isEmpty()) {
							copyBaseClass(dataset, properties);
							changed = true;
						}
					}
				}
				// if resource is changed
				if (changed) {							
					IResource resource = mResource.getResource();
					try {
						MdfUtility.doSave(res, (IFile) resource, monitor);
					} catch (CoreException e) {
						MdfPlugin.getDefault().logError("Error synchronizing datasets for Domain \'"
								+ domain.getQualifiedName().toString() + "\'", e);
					}
				}
				monitor.worked(1);
			}
		}
	}
	
	private static boolean existsInBaseClass(MdfDatasetProperty prop, List<MdfDatasetMappedProperty> properties) {
		String name = prop.getName();
		for (MdfDatasetMappedProperty dmp : properties) {
			if (name.equals(dmp.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param dataset
	 * @param properties
	 */
	protected static void copyBaseClass(MdfDataset dataset, List<MdfDatasetMappedProperty> properties) {
		List props = new ArrayList();
		props.addAll(dataset.getProperties());
		dataset.getProperties().clear();
		for (Object object : props) {
			// preserve the calculated attributes (DS-2074)
			if (object instanceof MdfDatasetDerivedProperty) {
				dataset.getProperties().add(object);
			} else {
				MdfDatasetMappedProperty dmp = (MdfDatasetMappedProperty)object;
				if (dmp.getPath().contains(".")) {
					// DS-7575 preserve user defined attribute
					dataset.getProperties().add(object);
				} else if (!existsInBaseClass(dmp, properties)) {
					// DS-8595 remove mapped property that does not exist in base class
					dataset.getProperties().remove(object);
				}
			}
		}
		dataset.getProperties().addAll(properties);
		 
		// DS-3053 keep old load permitted values annotations
		for (Object object: props) {
			MdfDatasetProperty oldProperty = (MdfDatasetProperty)object;
			if (DeriveBaseClassUtil.hasPermittedValuesAnnotation(oldProperty)) {
				boolean oldHasPermittedValue = DeriveBaseClassUtil.hasPermittedValues(oldProperty);
				MdfDatasetProperty newProperty = dataset.getProperty(oldProperty.getName());
				if (newProperty != null) {
					if (DeriveBaseClassUtil.hasPermittedValuesAnnotation(newProperty)) {
						DeriveBaseClassUtil.setLoadPermittedValue(newProperty, oldHasPermittedValue);
					} else {
						if (DeriveBaseClassUtil.serviceAnnotationExists(newProperty)) {
							DeriveBaseClassUtil.addLoadPermittedValue(newProperty, oldHasPermittedValue);
						} else {
							MdfAnnotation annotation = DeriveBaseClassUtil.createServiceAnnotation(oldHasPermittedValue);
							ModelFactory.INSTANCE.addAnnotation(newProperty, annotation);
						}
					}
				}
			}
			//DS-3294 retain the documentation of the old attributes
			if (!StringUtils.isEmpty(oldProperty.getDocumentation())) {
				MdfModelElementImpl newProperty = (MdfModelElementImpl) dataset.getProperty(oldProperty.getName());
				if (newProperty != null && newProperty != oldProperty) {
					newProperty.setDocumentation(oldProperty.getDocumentation());
				}
			}
			
			//DS-4573 retain the local translations
			if (!(oldProperty instanceof MdfDatasetDerivedProperty)) {
				// note: calculated attribute are already correct.
				retainOldTranslations(oldProperty, dataset, MdfTranslationHelper.TRANSLATION_LABEL);
				retainOldTranslations(oldProperty, dataset, MdfTranslationHelper.TRANSLATION_TOOLTIP);
			}
		}
	}
	
	/**
	 * @param oldProperty
	 * @param dataset
	 * @param kind
	 */
	private static void retainOldTranslations(MdfDatasetProperty oldProperty, MdfDataset dataset, String kind) {
		MdfAnnotation oldAnnotation = getTranslationAnnotation(oldProperty, kind);
		if(oldAnnotation != null) {
			MdfDatasetProperty newProperty = dataset.getProperty(oldProperty.getName());
			if (newProperty != null && oldProperty != newProperty) {
				MdfAnnotation newAnnotation = getTranslationAnnotation(newProperty, kind);
				if (newAnnotation != null) {
					newAnnotation.getProperties().clear();
					newAnnotation.getProperties().addAll(oldAnnotation.getProperties());
				} else {
					newAnnotation = createTranslationAnnotation(kind);
					newAnnotation.getProperties().addAll(oldAnnotation.getProperties());
					ModelFactory.INSTANCE.addAnnotation(newProperty, newAnnotation);
				}
			}
		}
	}
	
	/**
	 * @param property
	 * @param kind
	 * @return
	 */
	private static MdfAnnotation getTranslationAnnotation(MdfModelElement property, String kind) {
		return property.getAnnotation(MdfTranslationHelper.NAMESPACE_URI, kind);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static MdfAnnotation createTranslationAnnotation(String kind) {
		return ModelFactory.INSTANCE.createMdfAnnotation(MdfTranslationHelper.NAMESPACE_URI, kind);
	}
	
	/**
	 * @param monitor
	 * @param modelFolder
	 * @return
	 */
	private Collection<MdfDomain> fetchAllDomains(IProgressMonitor monitor, IOfsModelFolder modelFolder) throws CoreException {
		ofsProject = modelFolder.getOfsProject();
		ofsProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		return DomainRepository.getInstance(ofsProject).getDomains();
	}

	private Collection<MdfDomain> fetchAllDomains(IProgressMonitor monitor, final IOfsModelPackage modelPackage) throws CoreException {
		ofsProject = modelPackage.getOfsProject();
		ofsProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		ModelMatcher matcher = new ModelMatcher() {
			public boolean match(Object model) {
				if(model instanceof MdfDomainImpl) {
				MdfDomainImpl domain = (MdfDomainImpl) model;
					if(domain.eResource().getURI().toString().startsWith(modelPackage.getURI().toString())) 
						return true;
					else 
						return false;
				} else {
					return false;
				}
			}
		};
		DomainRepository repo = DomainRepository.getInstance(ofsProject);
		Collection<MdfDomain> domainSet = repo.getDomains(matcher);
		return domainSet;
	}

	/**
	 * @param monitor
	 * @param modelResource
	 * @return
	 */
	private Set<MdfDomain> fetchAllDomains(IProgressMonitor monitor, IOfsModelResource modelResource) {
		Set<MdfDomain> domainSet = new HashSet<MdfDomain>();
		try {
			IResource resource = modelResource.getResource();
			if (resource != null) {
				resource.refreshLocal(IResource.DEPTH_ZERO, monitor);
			}
			ofsProject = modelResource.getOfsProject();
			ofsProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
			domainSet.add((MdfDomain) modelResource.getEMFModel().get(0));
		} catch(CoreException e) {
			MdfPlugin.getDefault().logError("Error refreshing container", e);			
		} catch (IOException e) {
			MdfPlugin.getDefault().logError("Error fetching domain models", e);
		} catch (InvalidMetamodelVersionException e) {
			MdfPlugin.getDefault().logError("Error fetching domain models", e);
		}
		return domainSet;
	}
	
}
