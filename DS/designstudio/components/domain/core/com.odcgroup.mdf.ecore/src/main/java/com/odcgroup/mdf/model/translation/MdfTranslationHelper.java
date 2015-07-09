package com.odcgroup.mdf.model.translation;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.ecore.ECoreModelFactory;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.TranslationNotFoundException;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Helper which gather code necessary to MdfTranslation as well as to 
 * MdfInheritableTranslation (which cannot be put in a shared super class) 
 * @author yan
 */
public class MdfTranslationHelper {
	
	/**
	 * This interface is used be the MdfTranslationHelper which share the MDF 
	 * translation logic between BaseTranslation and MdfInheritableTranslation
	 * implementations.
	 */
	public interface IMdfTranslationHelperAdapter {

		/**
		 * @return the mdf model element which owns the translation
		 */
		MdfModelElement getMdfModelElement();
		
		/**
		 * Fire the internal fireChangeTranslation
		 * @param kind
		 * @param locale
		 * @param oldText
		 * @param newText
		 */
		void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText);

		/**
		 * @return all the kind supported by this translation
		 * @throws TranslationException
		 */
		Object[] getAllKinds() throws TranslationException;
		
		/**
		 * @return the project
		 */
		IProject getProject();
		
		/**
		 * Return the annotation given a translation kind
		 */
		String getAnnotationFromKind(ITranslationKind kind);

	}
	
	protected static MdfTranslationHelper instance = new MdfTranslationHelper();
	
    public static final String NAMESPACE_URI = "http://www.odcgroup.com/mdf/translation";
    public static final String TRANSLATION_LABEL = "Label";
    public static final String TRANSLATION_TOOLTIP = "Tooltip";
	
	private static final String READ_ONLY_DOMAINS = "(aaaentities.*|aaaenumerations.*|aaaformats.*)";
    
	public static MdfTranslationHelper getInstance() {
		return instance;
	}
	
	/**
	 * Delete the text for a translation specified by its kind and locale 
	 * @param sender
	 * @param kind
	 * @param locale
	 * @return the value removed
	 * @throws TranslationException
	 */
	public String delete(IMdfTranslationHelperAdapter sender, ITranslationKind kind, Locale locale) throws TranslationException {
		String oldText = null;
		MdfAnnotation annotation = sender.getMdfModelElement().getAnnotation(NAMESPACE_URI, sender.getAnnotationFromKind(kind));
		if (annotation != null) {
			MdfAnnotationProperty property = annotation.getProperty(propertyNameFromLocale(locale));
			if (property != null) {
				oldText = property.getValue();
				annotation.getProperties().remove(property);
				sender.fireChangeTranslation(kind, locale, oldText, null);
			}
		}
		return oldText;
	}

//	/**
//	 * @param kind
//	 * @return the annotation name from the kind, or null if the translation kind is not supported
//	 */
//	protected String annotationFromKind(ITranslationKind kind) {
//		String result = null;
//		switch (kind) {
//		case NAME:
//			result = TRANSLATION_LABEL;
//			break;
//		case TEXT:
//			result = TRANSLATION_TOOLTIP;
//			break;
//		default:
//			result = null;
//			break;
//		}
//		return result;
//	}
	
	/**
	 * @param locale
	 * @return the property name according the locale
	 */
	protected String propertyNameFromLocale(Locale locale) throws TranslationException {
		return locale.toString();
	}
	
	/**
	 * @param kind
	 * @param locale
	 * @return text associated with the kind and locale
	 */
	public String getText(IMdfTranslationHelperAdapter sender, ITranslationKind kind, Locale locale) throws TranslationException {
		if (kind == null || locale == null) {
			throw new IllegalArgumentException("Kind and locale must be not null");
		}
		
		if (ITranslationKind.DOCUMENTATION == kind) {
            if (Locale.ENGLISH.equals(locale)) {
                String text = sender.getMdfModelElement().getDocumentation();
                if (StringUtils.isNotEmpty(text)) return text;
            }
		}

		String name = sender.getAnnotationFromKind(kind);
		if (name == null) {
			return null;
		}
		MdfAnnotation annotation = sender.getMdfModelElement().getAnnotation(NAMESPACE_URI, name);
		if (annotation == null) {
			return null;
		}
		
		MdfAnnotationProperty property = annotation.getProperty(propertyNameFromLocale(locale));
		if (property == null) {
			return null;
		}
		
		return property.getValue();
	}

    /**
     * Define the text for a translation specified by its kind and locale.
     * @param sender
     * @param kind
     * @param locale
     * @param newText
     * @return the old value of the text replaced
     * @throws TranslationException
     */
	public String setText(IMdfTranslationHelperAdapter sender, ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		if (kind == null || locale == null || newText == null) {
			throw new IllegalArgumentException("Kind, locale and newText must be not null");
		}
		
		if (!Arrays.asList(sender.getAllKinds()).contains(kind)) {
			return getText(sender, kind, locale);
		}
		
		String oldText = null;
		
		Boolean hasOldDocumentation = false;
        if (ITranslationKind.DOCUMENTATION == kind) {
            if (Locale.ENGLISH.equals(locale)) {
                String text = sender.getMdfModelElement().getDocumentation();
                if (StringUtils.isNotEmpty(text)) {
                    hasOldDocumentation = true;
                    oldText = text;
                   	((MdfModelElementImpl)sender.getMdfModelElement()).setDocumentation("");
                }
            }
        }
        
        MdfAnnotation annotation = (MdfAnnotation) sender.getMdfModelElement().getAnnotation(NAMESPACE_URI, sender.getAnnotationFromKind(kind));
        if (annotation == null) {
        	// No annotation, create one
            annotation = ECoreModelFactory.INSTANCE
            		.createMdfAnnotation(NAMESPACE_URI, sender.getAnnotationFromKind(kind));
            sender.getMdfModelElement().getAnnotations().add(annotation);
        }

        MdfAnnotationProperty property = annotation.getProperty(propertyNameFromLocale(locale));
        if (property == null) {
        	// No annotation property, create one 
        	property = ECoreModelFactory.INSTANCE
    				.createMdfAnnotationProperty(annotation, 
    						propertyNameFromLocale(locale), newText);
        	annotation.getProperties().add(property);
        } else {
        	if (! hasOldDocumentation) {
				// Already an annotation, save the old value
				oldText = property.getValue();
			}
			((MdfAnnotationPropertyImpl)property).setValue(newText);
        }
        
        sender.fireChangeTranslation(kind, locale, oldText, newText);

		return oldText;
	}

	/**
	 * @param helperAdapter
	 * @return <code>true</code> if the resource is a read-only domain or if the file itself
	 * is in a read-only state, <code>false</code> otherwise 
	 */
	public boolean isReadOnly(IMdfTranslationHelperAdapter helperAdapter) {
		if (isProtected(helperAdapter)){
			return true;
		}
		Resource res = ((MdfModelElementImpl) helperAdapter.getMdfModelElement()).eResource();
		if (res != null && res.getURI().isPlatformResource()) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(res.getURI().path()));
			if (file != null && file.isReadOnly()) {
				return true;
			} else if (file != null && !file.isReadOnly()) {
				return false;
			}
		}

		IOfsProject ofsProject = OfsCore.getOfsProject(helperAdapter.getProject());
		try {
			if (res != null) {
				URI uri = res.getURI();
				if (ofsProject != null) {
					if (uri.isPlatformResource()) {
						uri = ModelURIConverter.toResourceURI(uri);
					}
					return ofsProject.getOfsModelResource(uri).isReadOnly();
				}
				return false;
			} else {
				// If the resource associated to the mdf element doesn't exist,
				// it can be modified
				return false;
			}
		} catch (ModelNotFoundException e) {
			throw new IllegalStateException("The model resource must exists as the mdfElement exists");
		}
	}

	/**
	 * @param helperAdapter
	 * @return <code>true</code> if the resource is a read-only domain
	 */
	public boolean isProtected(IMdfTranslationHelperAdapter helperAdapter) {
		String domain = "";
		if (helperAdapter.getMdfModelElement() instanceof MdfDomain) {
			domain = ((MdfDomain)helperAdapter.getMdfModelElement()).getName();
		} else {
			domain = helperAdapter.getMdfModelElement().getQualifiedName().getDomain();
		}
		if (null == domain) {
			domain = "";
		}
		return domain.toLowerCase().matches(READ_ONLY_DOMAINS);
	}

	/**
	 * Retrieve the delegate (i.e. the base class of a dataset, or a class attribute of a dataset's 
	 * mapped attribute)
	 * @param helperAdapter
	 * @return the delegate (i.e. the base class of a dataset, or a class attribute of a dataset's 
	 * mapped attribute)
	 * @throws TranslationException
	 */
	public ITranslation doGetDelegate(IMdfTranslationHelperAdapter helperAdapter) throws TranslationException {
		ITranslation delegate = null;
		MdfModelElement mdfElement = helperAdapter.getMdfModelElement();
		if (mdfElement instanceof MdfDataset) {
			MdfDataset dataset = (MdfDataset)mdfElement;
			MdfClass clazz = dataset.getBaseClass();
			if (clazz != null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(helperAdapter.getProject());
				delegate = manager.getTranslation(clazz);
			} else {
				throw new TranslationNotFoundException(dataset.getQualifiedName().getQualifiedName());
			}
		} else if (mdfElement instanceof MdfDatasetMappedProperty) {
			MdfProperty property = resolvePath((MdfDatasetMappedProperty)mdfElement);
			if (property != null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(helperAdapter.getProject());
				delegate =  manager.getTranslation(property);
			} else {
				throw new TranslationNotFoundException(mdfElement.getQualifiedName().getQualifiedName());
			}
		}
		return delegate;
	}

    /**
     * Finds the corresponding MdfAttribute given the dataset path. The dataset path starts from the main Class and uses
     * bean-utils like notation. ie. clientRelation.name. This corresponds to an association to the main entity called
     * clientRelation whose MdfClass has a property called name.
     * 
     * @param dsp The MdfDatasetMappedProperty
     * @return MdfProperty The MdfProperty
     */
    private MdfProperty resolvePath(MdfDatasetMappedProperty dsp) {
    	
    	MdfProperty property = null;
    	
        MdfClass mainClass = dsp.getParentDataset().getBaseClass();
        String datasetPath = dsp.getPath();
        
        if (mainClass == null || StringUtils.isEmpty(datasetPath)) {
        	// If the dataset is not completely defined, do not resolve the path
        	return null;
        }
        
        if (!datasetPath.contains(".")) {
            
        	// This is an attribute of the main Entity
        	property = mainClass.getProperty(datasetPath);

        } else {
        	
        	// parse the path
            String[] ps = datasetPath.split("\\.");

            MdfProperty p = null;
            for (int i = 0; i < ps.length; ++i) {
                p = mainClass.getProperty(ps[i]);
                if (p instanceof MdfAssociation) {
                    MdfAssociation mdfa = (MdfAssociation) p;
                    mainClass = (MdfClass) mdfa.getType();
                } else if (p instanceof MdfReverseAssociation) { // DS-1637
                	MdfReverseAssociation mdfra = (MdfReverseAssociation) p;
                	mainClass = (MdfClass) mdfra.getType();
                }
            }

            property = p;
        }
        
        return property;
    } 

}
