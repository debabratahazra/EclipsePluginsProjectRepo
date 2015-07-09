package com.odcgroup.aaa.ui.internal.page;

import java.util.List;

import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.aaa.ui.internal.page.bindings.UDEPropertiesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * This factory builds specific UI Tabs for Triple'A Format and Format Element.
 * These tabs are activated in the Domain Designer.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class AAAFormatPagesFactory implements DialogPagesFactory {
	
    /**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory#addPages(com.odcgroup.mdf.metamodel.MdfModelElement,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void addPages(MdfModelElement model, @SuppressWarnings("rawtypes") List pages) {
		
		boolean hasDictId = false;
		
		if (UDEHelper.getInstance().isUDEModel(model) && !(model instanceof MdfDomain)) {
			// Only Triple'A tab if binding is available
			if (new UDEPropertiesFactory().hasTripleAPropertyBinding(model)) {
				pages.add(new UDETripleAPage(model));
			}
		} else {
			if (model instanceof MdfBusinessType) {
				if (AAAAspect.getTripleABusinessType((MdfBusinessType)model)!=null) {
					pages.add(new MdfMetaDictBusinessTypeUIPage((MdfBusinessType)model));
					hasDictId = true;
				}
			}
			//DS-5350 made TripleA tab visible
			else if (model instanceof MdfEnumeration) {
				if (TripleAPageInspect.hasTripleAPropertyForEnumeration((MdfEnumeration)model)) {
					pages.add(new MdfMetaDictEnumerationUIPage((MdfEnumeration)model));
					hasDictId = true;
				}
			} else if (model instanceof MdfEnumValue) {
				MdfEnumValue enumValue = (MdfEnumValue)model;
				if (TripleAPageInspect.hasTripleAPropertyForEnumVal((MdfEnumValue)model)) {
					pages.add(new MdfMetaDictEnumerationValueUIPage(enumValue));
					hasDictId = true;
				}
			} else if (model instanceof MdfProperty) {
				if (TripleAPageInspect.hasTripleAPropertyForProperty((MdfProperty)model) && !TripleAPageInspect.isFormatClass((MdfProperty)model)) {
					pages.add(new MdfMetaDictPropertyUIPage((MdfProperty)model));
					hasDictId = true;
				}
			} else if (model instanceof MdfClass) {
				if (TripleAPageInspect.hasTripleAPropertyForEntity((MdfClass)model) && !TripleAPageInspect.isFormatClass((MdfClass)model)) {
					pages.add(new MdfMetaDictEntityUIPage((MdfClass)model));
					hasDictId = true;
				}
			}
			
			if (!hasDictId) {
				if (model instanceof MdfClass) {
					if (TripleAPageInspect.hasTripleAPropertyForClass((MdfClass)model) && TripleAPageInspect.isFormatClass((MdfClass)model)) {
						pages.add(new MdfFormatUIPage((MdfEntity)model));
					}
				}
				if (model instanceof MdfAttribute) {
					MdfAttribute attribute = (MdfAttribute) model;
					MdfClass clazz = attribute.getParentClass();
					if (TripleAPageInspect.hasTripleAPropertyForAttribute(attribute) && TripleAPageInspect.hasTripleAPropertyForClass((MdfClass)clazz)) {
						pages.add(new MdfFormatElementUIPage(attribute));
					}
				}
			}
		}
	}
	
	public AAAFormatPagesFactory() {
	}

}
