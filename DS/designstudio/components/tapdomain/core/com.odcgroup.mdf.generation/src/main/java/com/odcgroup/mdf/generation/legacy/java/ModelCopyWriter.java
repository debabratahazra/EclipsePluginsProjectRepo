package com.odcgroup.mdf.generation.legacy.java;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.generation.legacy.mml.MmlModelWriter;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslation;


public class ModelCopyWriter implements ModelWriter {

    private static final String MODELS_FOLDER = "META-INF/models/";
    private final MmlModelWriter writer;

    public ModelCopyWriter() throws IOException {
        writer = new MmlModelWriter();
        writer.setIndenting(true);
    }

    /**
	 * @see com.odcgroup.mdf.mml.MmlModelWriter#getModelPath(java.lang.String)
	 */
    public final String getModelPath(String domain) {
        return MODELS_FOLDER + MmlModelWriter.getModelFileName(domain);
    }

    public void setMessageHandler(MessageHandler handler) {
        if (handler == null) throw new NullPointerException("handler");
        writer.setMessageHandler(handler);
    }

    public String getName() {
        return "Model Copy writer";
    }

    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
    	// ATR : clone the domain otherwise all translation are definitely lost
		MdfDomain domainClone = (MdfDomain)EcoreUtil.copy((EObject)domain);
		filterTranslations(domainClone);
    	
        writer.write(domainClone, importedDomains, new ModelFilesFactory(factory));
    }
    
	/**
	 * @param domain
	 */
	private void filterTranslations(MdfDomain domain) {
		if (domain == null) {
			return;
		}
		
		List<MdfAnnotation> dAnnot = domain.getAnnotations(MdfTranslation.NAMESPACE_URI);
		if (!dAnnot.isEmpty()) {
			domain.getAnnotations().removeAll(dAnnot);
		}
		removeTranslations(domain.getClasses());
		removeTranslations(domain.getDatasets());
		removeTranslations(domain.getBusinessTypes());
		removeTranslations(domain.getEnumerations());
		
		for (Object object : domain.getClasses()) {
			MdfClass klass = (MdfClass) object;
			removeTranslations(klass.getProperties());
		}
		
		for (Object object : domain.getDatasets()) {
			MdfDataset ds = (MdfDataset) object;
			removeTranslations(ds.getProperties());
		}
		
		for (Object object : domain.getEnumerations()) {
			MdfEnumeration enumeration = (MdfEnumeration) object;
			removeTranslations(enumeration.getValues());
		}
	}	
	
	private void removeTranslation(MdfModelElement element) {
		if (element == null) {
			return;
		}
		List<MdfAnnotation> annotations = element.getAnnotations(MdfTranslation.NAMESPACE_URI);
		if (!annotations.isEmpty()) {
			element.getAnnotations().removeAll(annotations);
		}
	}
	
	private void removeTranslations(List list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (Object object : list) {
			MdfModelElement element = (MdfModelElement) object;
			removeTranslation(element);
		}
	}

    private static class ModelFilesFactory implements OutputStreamFactory {

    	private final OutputStreamFactory factory;
    	
    	public ModelFilesFactory(OutputStreamFactory factory) {
    		this.factory = factory;
    	}
    	
		public OutputStream openStream(String path) throws IOException {
			return factory.openStream(MODELS_FOLDER + path);
		}

		public OutputStream openStream(String path, boolean append)
				throws IOException {
			return factory.openStream(MODELS_FOLDER + path, append);
		}

	}

}
