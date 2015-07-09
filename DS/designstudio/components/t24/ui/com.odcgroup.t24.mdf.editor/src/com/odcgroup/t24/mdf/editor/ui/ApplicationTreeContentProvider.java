package com.odcgroup.t24.mdf.editor.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.odcgroup.mdf.editor.ui.editors.providers.MdfContentProvider;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 *
 * @author phanikumark
 *
 */
public class ApplicationTreeContentProvider extends MdfContentProvider {
	


	@Override
	public Object[] getChildren(Object inputElement) {
		List<?> children = Collections.EMPTY_LIST;
		if (inputElement instanceof MdfClass) {
			MdfClass domain = (MdfClass)inputElement;
			if(domain.getName().startsWith("X_")){
				children = getPropertiesWithoutDuplicates(domain);
			}
			else{
				children = getProperties(domain);
			}
		} else if (inputElement instanceof MdfAssociation) {
			MdfAssociation association = (MdfAssociation) inputElement;
			if (association.getContainment() == MdfContainment.BY_VALUE) {
				children = getProperties((MdfClass) association.getType());
				if (children.size() == 1) {
					return Collections.EMPTY_LIST.toArray();
				}
			}
		} else if (inputElement instanceof IStructuredSelection){
			children = ((IStructuredSelection) inputElement).toList();
		}
		return children.toArray();
	}

	/**
	 * @param domain
	 */
	private List<MdfProperty> getPropertiesWithoutDuplicates(MdfClass mdfClass) {
		List<MdfProperty> filteredList = new ArrayList<MdfProperty>();
		for (Object obj : mdfClass.getProperties(true)) {
			if (obj instanceof MdfAttribute) {
				MdfAttribute attr = (MdfAttribute) obj;
				MdfAnnotation t24iAnnotation = attr.getAnnotation(T24Aspect.T24_ANNOTATIONS_NAMESPACE_URI, T24Aspect.ANNOTATION);
				if (t24iAnnotation != null) {
					MdfAnnotationProperty coreProperty = t24iAnnotation.getProperty("core");
					if (coreProperty != null) {
						if (coreProperty.getValue().equals("false")) {
							if(checkExtendedDomainContainsField(mdfClass, attr.getName())){
								continue;
							}
						}
					}
				}
			}
			filteredList.add((MdfProperty) obj);
		}
		return filteredList;
	}

	/**
	 * @param domain
	 * @param name
	 * @return
	 */
	private boolean checkExtendedDomainContainsField(MdfClass mdfClass, String name) {
		for(Object obj : mdfClass.getProperties()){
			if (obj instanceof MdfAttribute) {
				MdfAttribute attr = (MdfAttribute) obj;
				if(attr.getName().equals(name)){
					return true;
				}
			}
		}
		return false;
	}


}
