package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.odcgroup.mdf.editor.ui.dialogs.annotations.AnnotationsPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.doc.DocPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.java.JavaPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.sql.SQLPagesFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class AllPagesFactory implements DialogPagesFactory {
	
	private static final String MDF_FACTORY = "mdf";
	private static final String JAVA_FACTORY = "java";
	private static final String SQL_FACTORY = "sql";
	private static final String ANNOTATION_FACTORY = "annotation";
	private static final String DOC_FACTORY = "doc";
	
	private static final Logger LOGGER = Logger.getLogger(AllPagesFactory.class);

	/**
	 * Constructor for AllPagesFactory
	 */
	public AllPagesFactory() {
		super();
	}
	
	private void addPages(DialogPagesFactory factory, MdfModelElement model, List pages) {
		if (factory != null) {
			factory.addPages(model, pages); 
		} else {
		}
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory#addPages(com.odcgroup.mdf.metamodel.MdfModelElement,
	 *      java.util.List)
	 */
	public void addPages(MdfModelElement model, List pages) {		
		
		Map<String, DialogPagesFactory> factories = new LinkedHashMap<String, DialogPagesFactory>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(DialogPagesFactory.EXTENSION_POINT);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j]; 
				if ("factory".equals(element.getName())) {
					try {
						DialogPagesFactory ext = (DialogPagesFactory) element.createExecutableExtension("class");
						factories.put(element.getAttribute("type"), ext);
						//ext.addPages(model, pages);
					} catch (CoreException e) {
						LOGGER.error(e, e);
					}
				}
			}
		}

		//-- Unfortunately must do that in order to have those pages first...
		DialogPagesFactory mdfPagesFactory = factories.remove(MDF_FACTORY);
		if (mdfPagesFactory == null) mdfPagesFactory = new MdfPagesFactory();
		
		DialogPagesFactory javaPagesFactory = factories.remove(JAVA_FACTORY);
		if (javaPagesFactory == null) javaPagesFactory = new JavaPagesFactory();

		DialogPagesFactory sqlPagesFactory = factories.remove(SQL_FACTORY);
		if (sqlPagesFactory == null) sqlPagesFactory = new SQLPagesFactory();
		
		DialogPagesFactory annotationsPagesFactory = factories.remove(ANNOTATION_FACTORY);
		if (annotationsPagesFactory == null) annotationsPagesFactory = new AnnotationsPagesFactory();

		DialogPagesFactory docPagesFactory = factories.remove(DOC_FACTORY);
		if (docPagesFactory == null) docPagesFactory = new DocPagesFactory();

		
		// now add pages in the desired order
		mdfPagesFactory.addPages(model, pages); 
		javaPagesFactory.addPages(model, pages); 
		sqlPagesFactory.addPages(model, pages);
		
		for (Map.Entry<String, DialogPagesFactory> entry : factories.entrySet()) {
			entry.getValue().addPages(model, pages);
		}
		
        annotationsPagesFactory.addPages(model, pages);
		docPagesFactory.addPages(model, pages);
	}

}