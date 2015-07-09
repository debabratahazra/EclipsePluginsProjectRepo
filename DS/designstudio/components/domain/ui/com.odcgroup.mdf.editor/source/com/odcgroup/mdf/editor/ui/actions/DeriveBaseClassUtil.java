package com.odcgroup.mdf.editor.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * utility class for deriving base-class properties to the dataset
 * 
 * @author pkk
 */
public class DeriveBaseClassUtil {

	public static List<MdfDatasetMappedProperty> getDatasetMappedProperties(IOfsProject ofsProject, MdfDataset dataset) throws CoreException {
		IProject project = ofsProject.getProject();
		List<MdfDatasetMappedProperty> mProperties = new ArrayList<MdfDatasetMappedProperty>();
		MdfDatasetMappedPropertyImpl mProperty = null;
		
		// DS-5991 Do *NOT* do any project.refreshLocal() for each and every DataSet here! We're doing that in DatasetFacility instead

		MdfClass baseClass = dataset.getBaseClass();
		if (baseClass == null) {
			return mProperties;
		}
				
		// MdfDomain shortDomain = getShortDatasetsDomain(dList);
		for (Object obj : baseClass.getProperties()) {
			if (obj instanceof MdfAttribute) {
				MdfAttribute attribute = (MdfAttribute) obj;
				mProperty = (MdfDatasetMappedPropertyImpl) MdfFactory.eINSTANCE
						.createMdfDatasetMappedProperty(project, attribute);
				mProperty.setName(attribute.getName());
				mProperty.setPath(attribute.getName());
				addServiceAnnotation(mProperty, attribute);
				mProperties.add(mProperty);
			} else if (obj instanceof MdfAssociation) {
				MdfAssociation association = (MdfAssociation) obj;
				if (association.getMultiplicity() == MdfMultiplicity.ONE
						&& association.getContainment() == MdfContainment.BY_REFERENCE) {
					mProperty = (MdfDatasetMappedPropertyImpl) MdfFactory.eINSTANCE
							.createMdfDatasetMappedProperty(project, association);
					mProperty.setName(association.getName());
					mProperty.setPath(association.getName());
					MdfEntity type = association.getType();
					if (type instanceof MdfClass) {
						DomainRepository repo = DomainRepository.getInstance(ofsProject);
						Collection<MdfDomain> domains = repo.getDomains();
						MdfDataset ds = fetchDataset(domains , (MdfClass) type);
						if (ds != null) {
							mProperty.setLinkDataset(ds);
							addServiceAnnotation(mProperty, association);
							mProperties.add(mProperty);
						}
					}
				}
			}
		}
		return mProperties;
	}
	
	public static void addServiceAnnotation(MdfDatasetMappedProperty mProperty, MdfProperty property) {
		MdfAnnotation serviceAnnotation = getServiceAnnotation(property);
		if (mProperty != null && serviceAnnotation != null ) {
			MdfAnnotation annotation = (MdfAnnotation) EcoreUtil.copy((EObject) serviceAnnotation);
			ModelFactory.INSTANCE.addAnnotation(mProperty, annotation);
		}
	}

	public static MdfAnnotation getServiceAnnotation(MdfModelElement property) {
		MdfAnnotation annotation = property.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI,
				AAAAspect.SERVICES_ANNOTATION);
		
		return annotation;
	}
	
	public static boolean serviceAnnotationExists(MdfModelElement property) {
		MdfAnnotation annotation = property.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI,
				AAAAspect.SERVICES_ANNOTATION);
		return (annotation == null) ? false : true;
	}
	
	public static boolean hasPermittedValues(MdfModelElement property) {
		boolean result = false;
		MdfAnnotation annotation = 
			property.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI, AAAAspect.SERVICES_ANNOTATION);
		if (annotation != null) {
			MdfAnnotationProperty aProp = annotation.getProperty(AAAAspect.SERVICES_PROPERTY_PERMITTED_VALUES);
			if (aProp != null) {
				result = Boolean.valueOf(aProp.getValue());
			}
		}
		return result;
	}

	public static boolean hasPermittedValuesAnnotation(MdfModelElement property) {
		MdfAnnotation annotation = 
			property.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI, AAAAspect.SERVICES_ANNOTATION);
		if (annotation != null) {
			MdfAnnotationProperty aProp = annotation.getProperty(AAAAspect.SERVICES_PROPERTY_PERMITTED_VALUES);
			if (aProp != null) {
				return true;
			}
		}
		return false;
	}

	public static void setLoadPermittedValue(MdfModelElement property, boolean value) {
		MdfAnnotation annotation =property.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI,
				AAAAspect.SERVICES_ANNOTATION);
		if (annotation != null) {
			annotation.getProperties().clear();
			String val = Boolean.valueOf(value).toString();
			ModelFactory.INSTANCE.addAnnotationProperty(annotation,
					AAAAspect.SERVICES_PROPERTY_PERMITTED_VALUES, val);
		}
	}

	public static void addLoadPermittedValue(MdfModelElement property, boolean value) {
		MdfAnnotation annotation =property.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI,
				AAAAspect.SERVICES_ANNOTATION);
		if (annotation != null) {
			String val = Boolean.valueOf(value).toString();
			ModelFactory.INSTANCE.addAnnotationProperty(annotation,
					AAAAspect.SERVICES_PROPERTY_PERMITTED_VALUES, val);
		}
	}

	public static MdfAnnotation createServiceAnnotation(boolean value) {
		MdfAnnotation annotation = ModelFactory.INSTANCE.createMdfAnnotation(AAAAspect.SERVICES_NAMESPACE_URI,
				AAAAspect.SERVICES_ANNOTATION);
		String val = Boolean.valueOf(value).toString();
		ModelFactory.INSTANCE.addAnnotationProperty(annotation,
				AAAAspect.SERVICES_PROPERTY_PERMITTED_VALUES, val);
		return annotation;
	}

	/**
	 * Obtains the default (or only) DataSet having the MdfClass entity as baseClass.  
	 */
	private static MdfDataset fetchDataset(Collection<MdfDomain> domainList, MdfEntity entity) {
		List<MdfDataset> datasets = new ArrayList<MdfDataset>();
		for (MdfDomain domain : domainList) {
			for (Object obj : domain.getDatasets()) {
				MdfDataset ds = (MdfDataset) obj;
				if (ds.getBaseClass() != null && ds.getBaseClass().getQualifiedName().equals(entity.getQualifiedName())) {
					datasets.add(ds);
				}
			}
		}
		if (datasets.size() == 1) {
			return datasets.get(0);
		} else if (datasets.size() > 1) {
			return getDefaultDataset(datasets, entity);
			// return (shortDomain != null) ?
			// fetchShortDataset(shortDomain.getDatasets(), entity) : null;
		}
		return null;
	}

	private static MdfDataset getDefaultDataset(List<MdfDataset> datasets, MdfEntity entity) {
		for (MdfDataset mdfDataset : datasets) {
			if (mdfDataset.isLinked()) {
				return mdfDataset;
			}
		}
		return null;
	}

}
