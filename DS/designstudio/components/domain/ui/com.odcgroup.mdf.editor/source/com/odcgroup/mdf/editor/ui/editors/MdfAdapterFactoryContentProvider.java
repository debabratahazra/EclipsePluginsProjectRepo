package com.odcgroup.mdf.editor.ui.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.metamodel.MdfReverseAssociationWrapper;
import com.odcgroup.mdf.utils.ModelHelper;

/**
 * AdapterFactoryContentProvider extension to handle the display of reverse associations
 * in the destination mdfClass
 * DS-1826
 * 
 * @author pkk
 *
 */
public class MdfAdapterFactoryContentProvider  extends AdapterFactoryContentProvider {

	/**
	 * @param adapterFactory
	 */
	public MdfAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object object) {
		// mdfclass's indirect reverse associations
		if (object instanceof MdfClassImpl) {
			MdfClassImpl mdfClass = (MdfClassImpl) object;
			List initialList = Arrays.asList(super.getChildren(object));
			
			List children = new ArrayList();
			children.addAll(initialList);
			
			List list = ModelHelper.getReverseAssociations(mdfClass.getParentDomain(), mdfClass);
			MdfReverseAssociation reverse = null;
			for (Object reAssc : list) {
				reverse = (MdfReverseAssociation) reAssc;
				// avoid duplicate entries
				if (!exists(initialList, reverse)) {
					children.add(new MdfReverseAssociationWrapper(reverse));
				}
			}
			return children.toArray();
		}
		return super.getChildren(object);
	}
	
	/**
	 * @param children
	 * @param reverse
	 * @return
	 */
	private boolean exists(List children, MdfReverseAssociation reverse) {
		MdfProperty property = null;
		for (Object child : children) {
			if (child instanceof MdfProperty) {
				property = (MdfProperty) child;
				if(reverse.getQualifiedName().equals(property.getQualifiedName())) {
					return true;
				}
			}
		}
		return false;
	}
	
}