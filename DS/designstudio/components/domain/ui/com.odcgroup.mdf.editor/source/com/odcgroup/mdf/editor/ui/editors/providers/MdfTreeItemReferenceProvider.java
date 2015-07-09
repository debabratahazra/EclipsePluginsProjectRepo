package com.odcgroup.mdf.editor.ui.editors.providers;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * @author pkk
 *
 */
public class MdfTreeItemReferenceProvider {
	
	/**
	 * @param selection
	 * @return
	 */
	public static MdfModelElement getTreeItemReference(MdfModelElement obj){
		if (obj instanceof MdfAttribute){
			if (((MdfAttribute)obj).getType() instanceof MdfModelElement) {
				return ((MdfAttribute)obj).getType();
			}
		} else if (obj instanceof MdfAssociation){
			if (((MdfAssociation)obj).getType() instanceof MdfModelElement) {
				return ((MdfAssociation)obj).getType();
			}			
		} else if (obj instanceof MdfReverseAssociation){
			if (((MdfReverseAssociation)obj).getType() instanceof MdfModelElement) {
				return ((MdfReverseAssociation)obj).getType();
			}			
		} else if (obj instanceof MdfClass){
			if (((MdfClass)obj).getBaseClass() instanceof MdfModelElement) {
				return ((MdfClass)obj).getBaseClass();
			}				
		} else if (obj instanceof MdfDataset){
			if (((MdfDataset)obj).getBaseClass() instanceof MdfModelElement) {
				return ((MdfDataset)obj).getBaseClass();
			}					
		} else if (obj instanceof MdfDatasetProperty){
			if (obj instanceof MdfDatasetMappedProperty) {
				//DS-3232
				MdfDatasetMappedProperty property = (MdfDatasetMappedProperty) obj;
				MdfDataset ds = property.getParentDataset();
				String path = property.getPath();
				MdfClass baseClass = ds.getBaseClass();
				if (path.indexOf(".")>0) {
					String[] strs = StringUtils.split(path, ".");
					for (int i = 0; i < strs.length; i++) {
						String str = strs[i];
						if (i != strs.length-1) {
							MdfProperty prop = baseClass.getProperty(str);
							if (prop instanceof MdfAssociation) {
								MdfEntity entity  = ((MdfAssociation) prop).getType();
								if (entity instanceof MdfClass) {
									baseClass = (MdfClass) entity;
								}
							}
						} else {
							return baseClass.getProperty(str);
						}
					}
				} else {
					return baseClass.getProperty(property.getPath());
				}
			} else {
				if (((MdfDatasetProperty)obj).getType() instanceof MdfModelElement) {
					return ((MdfDatasetProperty)obj).getType();
				}
			}
		}
		return null;
	}

}
