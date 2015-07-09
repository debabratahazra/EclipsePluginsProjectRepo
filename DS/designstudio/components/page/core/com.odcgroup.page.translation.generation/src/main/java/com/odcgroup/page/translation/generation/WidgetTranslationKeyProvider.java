package com.odcgroup.page.translation.generation;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.translation.generation.provider.BaseTranslationKeyProvider;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * @author atr
 */
public class WidgetTranslationKeyProvider extends BaseTranslationKeyProvider {

	/** */
	private final String DATASET = PropertyTypeConstants.DOMAIN_ENTITY;
	/** */
	private final String DATASET_PROPERTY = PropertyTypeConstants.DOMAIN_ATTRIBUTE;

	@Override
	protected ITranslationKey newTranslationKey(IProject project, ITranslation translation) {
		ITranslationKey key = null;
		Widget widget = (Widget) translation.getOwner();
		if (widget.getType().isDomainWidget()) {
			if (widget.getTranslationId() > 0) {
				key = new WidgetTranslationKey(translation);
			} else {
				// check it's a domain widget or a simple widget.
				Property datasetProperty = widget.findProperty(DATASET_PROPERTY);
				if (datasetProperty != null) {
					Property dataset = widget.findPropertyInParent(DATASET);
					if (dataset != null) {
						MdfModelElement mdfElement = findMdfElement(project, widget, datasetProperty, dataset);
						
						if (mdfElement instanceof MdfDatasetMappedProperty) {
							mdfElement = findMdfDatasetElement(widget, mdfElement);
						}
						else if (mdfElement instanceof MdfDatasetDerivedProperty) {
							mdfElement = findMdfDatasetElement(widget, mdfElement);
						}
						
						if (mdfElement != null) {
							ITranslationManager manager = TranslationCore.getTranslationManager(project);
							translation = manager.getTranslation(mdfElement);
							if (translation != null) {
								key = TranslationGenerationCore.getTranslationKey(project, translation);
							}
						}
					}
				}
			}
		} else {
			key = new WidgetTranslationKey(translation);
		}
		return key;
	}

	/**
	 * @param widget
	 * @param mdfElement
	 * @return
	 */
	private MdfModelElement findMdfDatasetElement(Widget widget, MdfModelElement mdfElement) {
		String widgetTypeName = widget.getType().getName(); 
		if (WidgetTypeConstants.RADIO_BUTTON.equals(widgetTypeName) || WidgetTypeConstants.CHECKBOX.equals(widgetTypeName)) {
			if (mdfElement instanceof MdfDatasetMappedProperty) {
				MdfDatasetMappedProperty mdfDatasetMappedProperty = (MdfDatasetMappedProperty) mdfElement;
				if (mdfDatasetMappedProperty.getType() instanceof MdfEnumeration) {
					mdfElement = getMdfEnumerationValue(widget, mdfElement,
							((MdfEnumeration) mdfDatasetMappedProperty
									.getType()));
				}
			}//DS-5705
			else if (mdfElement instanceof MdfDatasetDerivedProperty) {
				MdfDatasetDerivedProperty mdfDatasetDerivedProperty = (MdfDatasetDerivedProperty) mdfElement;
				if (mdfDatasetDerivedProperty.getType() instanceof MdfEnumeration) {
					mdfElement = getMdfEnumerationValue(widget, mdfElement,
						((MdfEnumeration) mdfDatasetDerivedProperty
								.getType()));
				}
			}
		}
		return mdfElement;
	}

	/**
	 * DS-5705
	 * @param widget - Label widget
	 * @param mdfElement - model element
	 * @param mdfEnumeration - enumeration element
	 * @return mdfModel
	 */
	private MdfModelElement getMdfEnumerationValue(Widget widget,
			MdfModelElement mdfElement,
			MdfEnumeration mdfEnumeration) {
		if (mdfEnumeration != null) {
			Property enumValueProperty = widget
					.findProperty(PropertyTypeConstants.ENUM_VALUE);
			if (enumValueProperty != null) {
				String enumValueName = enumValueProperty.getValue();
				if (StringUtils.isNotBlank(enumValueName)) {
					mdfElement = mdfEnumeration.getValue(enumValueName);
				}
			}
		}
		return mdfElement;
	}

	/**
	 * @param project
	 * @param widget
	 * @param datasetProperty
	 * @param dataset
	 * @return
	 */
	private MdfModelElement findMdfElement(IProject project, Widget widget, Property datasetProperty, Property dataset) {
		IOfsProject ofsProject = OfsCore.getOfsProject(project);
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		MdfModelElement mdfElement = widget.findDatasetProperty(repository, dataset.getValue(), datasetProperty.getValue());
		return mdfElement;
	}

}
