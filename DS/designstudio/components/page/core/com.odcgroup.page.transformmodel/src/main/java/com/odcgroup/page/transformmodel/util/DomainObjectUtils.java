package com.odcgroup.page.transformmodel.util;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class defines some services to open dialog related to the selection of
 * domain object(s).
 * 
 * @author atr
 */
public final class DomainObjectUtils {

	/**
	 * @param widget
	 * @return MdfDataset
	 */
	public static MdfDataset getDataset(Widget widget) {
		IOfsProject ofsProject = null;
		if (widget.eResource() != null)
			ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
		return DomainObjectUtils.getDataset(ofsProject, widget);
	}
	
	/**
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static MdfDataset getDataset(IOfsProject ofsProject, Widget widget) {
		String datasetName = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		return repository.getDataset(MdfNameFactory.createMdfName(datasetName));
	}

	
	/**
	 * @param widget
	 * @return
	 */
	public static String retrieveEnumValue(Widget widget) {
		Property datasetProperty = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		String enumValueValue = "";
		
		if (datasetProperty != null) {
			Property dataset = widget.findPropertyInParent(PropertyTypeConstants.DOMAIN_ENTITY);
			if (dataset != null) {
				IOfsProject ofsProject = OfsCore.getOfsProject(OfsResourceHelper.getOfsProject(widget.eResource()).getProject());
				DomainRepository repository = DomainRepository.getInstance(ofsProject);
				MdfModelElement mdfElement = widget.findDatasetProperty(repository, dataset.getValue(), datasetProperty.getValue());
				String widgetTypeName = widget.getType().getName(); 
				if (widgetTypeName.equals(WidgetTypeConstants.RADIO_BUTTON) || widgetTypeName.equals(WidgetTypeConstants.CHECKBOX)) {
					if (mdfElement instanceof MdfDatasetMappedProperty) {
						MdfDatasetMappedProperty mdfDatasetMappedProperty = (MdfDatasetMappedProperty) mdfElement;
						if (mdfDatasetMappedProperty.getType() instanceof MdfEnumeration) {
							enumValueValue = getMdfEnumValue(widget,
									enumValueValue, ((MdfEnumeration) mdfDatasetMappedProperty.getType()));
						}
					} 
					else if (mdfElement instanceof MdfDatasetDerivedProperty) {
						MdfDatasetDerivedProperty mdfDatasetDerivedProperty = (MdfDatasetDerivedProperty) mdfElement;
						if (mdfDatasetDerivedProperty.getType() instanceof MdfEnumeration) {
							enumValueValue = getMdfEnumValue(widget,
								enumValueValue, ((MdfEnumeration) mdfDatasetDerivedProperty.getType()));
						}
					}
				}
			}
		}
		return enumValueValue;
	}

	/**
	 * DS-5705
	 * @param widget - Label widget
	 * @param enumValueValue - enum value
	 * @param mdfEnumeration - enumeration element
	 * @return enum value
	 */
	private static String getMdfEnumValue(Widget widget, String enumValueValue,
			MdfEnumeration mdfEnumeration) {
		Property enumValueProperty = widget.findProperty(PropertyTypeConstants.ENUM_VALUE);
		if (enumValueProperty != null) {
			String enumValueName = enumValueProperty.getValue();
			if (StringUtils.isNotBlank(enumValueName)) {
				MdfEnumValue mdfEnumValue = mdfEnumeration.getValue(enumValueName);
				if (mdfEnumValue != null) {
					enumValueValue = mdfEnumValue.getValue();
				}
			}
		}
		return enumValueValue;
	}
	
	
	
	/** Cannot be instantiated */
	private DomainObjectUtils() {
	}

}
