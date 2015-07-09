package com.odcgroup.page.model.translation;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class WidgetTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	/** */
	private final String DATASET = PropertyTypeConstants.DOMAIN_ENTITY;
	/** */
	private final String DATASET_PROPERTY = PropertyTypeConstants.DOMAIN_ATTRIBUTE;
	
	@Override
	protected BaseTranslation newTranslation(IProject project, Object obj) {
		
		BaseTranslation translation = null;
		
		if (obj instanceof Widget) {
			
			Widget widget = (Widget)obj;
			
			// check it's a domain widget or a simple widget.
			Property datasetProperty = widget.findProperty(DATASET_PROPERTY);
			if (datasetProperty != null) {
				translation = createTranslationForDatasetProperty(project, widget, datasetProperty);
			} else {
				// not bound to the domain
				translation = newWidgetTranslation(project, widget);
			}
			
		} else {
			throw new IllegalArgumentException("WidgetTranslationProvider doesn't support " + obj + " input");
		}
		
		return translation;
		
	}

	/**
	 * @param project
	 * @param widget
	 * @param datasetProperty
	 * @return
	 */
	private BaseTranslation createTranslationForDatasetProperty(IProject project, Widget widget, Property datasetProperty) {
		BaseTranslation translation;
		Property dataset = widget.findPropertyInParent(DATASET);
		if (dataset != null) {
			
			// it's a domain widget
			String widgetTypeName = widget.getTypeName();
			
			if (WidgetTypeConstants.RADIO_BUTTON.equals(widgetTypeName)) {
				String domAtr = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
				if (StringUtils.isEmpty(domAtr)) {
					// partially bound to the domain
					translation = newWidgetTranslation(project, widget);					
				} else {
					translation = createTranslationForRadioButton(project, widget, datasetProperty, dataset);
				}
				
			} else if (WidgetTypeConstants.CHECKBOX.equals(widgetTypeName)) {
				String domAtr = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
				if (StringUtils.isEmpty(domAtr)) {
					// partially bound to the domain
					translation = newWidgetTranslation(project, widget);					
				} else {
					translation = createTranslationForCheckBox(project, widget, datasetProperty, dataset);
				} 
			} else if (WidgetTypeConstants.TABLE_COLUMN.equals(widgetTypeName)) {
				
				translation = createTranslationForTable(project, widget, datasetProperty, dataset);
				
			} else if (WidgetTypeConstants.MATRIX_CELLITEM.equals(widgetTypeName)) { 
				
				IMatrixCellItem item = MatrixHelper.getMatrixCellItem(widget);
				translation = createTranslationForMatrixCellItem(project, widget, datasetProperty, dataset, item);
				
			} else if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widgetTypeName)) { 
				
				IMatrixCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
				translation = createTranslationForMatrixCellItem(project, widget, datasetProperty, dataset, item);
				
			} else { 
				translation = newDomainWidgetTranslation(project, widget, datasetProperty, dataset);
			}
		} 
		else {
			// partially bound to the domain
			translation = newWidgetTranslation(project, widget);
		}
		return translation;
	}

	/**
	 * @param project
	 * @param widget
	 * @param datasetProperty
	 * @param dataset
	 * @param item
	 * @return
	 */
	private BaseTranslation createTranslationForMatrixCellItem(IProject project, Widget widget,	Property datasetProperty, Property dataset, IMatrixCellItem item) {
		BaseTranslation translation;
		if (item.isComputed()) {
			translation = newWidgetTranslation(project, widget);
		} else {
			translation = newDomainWidgetTranslation(project, widget, datasetProperty, dataset);							
		}
		return translation;
	}

	private BaseTranslation createTranslationForRadioButton(IProject project, Widget widget, Property datasetProperty, Property dataset) {
		BaseTranslation translation;
		String domainAttributePropertyValue = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		
		if (StringUtils.isNotEmpty(domainAttributePropertyValue)) {
			String enumValue = widget.getPropertyValue(PropertyTypeConstants.ENUM_VALUE);
			translation = newDomainWidgetTranslation(project, widget, datasetProperty, dataset, enumValue);
		}
		else {
			translation = newWidgetTranslation(project, widget);
		}
		return translation;
	}
	
	private BaseTranslation createTranslationForCheckBox(IProject project, Widget widget, Property datasetProperty, Property dataset) {
		BaseTranslation translation;
		String domainAttributePropertyValue = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		
		if (StringUtils.isNotEmpty(domainAttributePropertyValue)) {
			String enumValue = widget.getPropertyValue(PropertyTypeConstants.ENUM_VALUE);
			translation = newDomainWidgetTranslation(project, widget, datasetProperty, dataset, enumValue);
		}
		else {
			translation = newWidgetTranslation(project, widget);
		}
		return translation;
	}

	/**
	 * Radio button is unusual in that we need the specific value of teh enumeration when creating teh radio button widget
	 * @param project
	 * @param widget
	 * @param datasetProperty
	 * @param dataset
	 * @param enumValueProperty
	 * @return
	 */
	private BaseTranslation newDomainWidgetTranslation(IProject project, Widget widget, Property datasetProperty, Property dataset, String enumValueProperty) {
		return new DomainWidgetTranslation(this, project, widget, dataset, datasetProperty, enumValueProperty);
	}

	/**
	 * @param project
	 * @param widget
	 * @param datasetProperty
	 * @param dataset
	 * @return
	 */
	private BaseTranslation createTranslationForTable(IProject project, Widget widget, Property datasetProperty, Property dataset) {
		BaseTranslation translation;
		ITableColumn column = TableHelper.getTableColumn(widget);
		if (column.isDynamic()) {
			translation = new WidgetDynamicTableColumnTranslation(this, project, widget);
		} else if (column.isComputed() || column.isPlaceHolder()) {
			translation = newWidgetTranslation(project, widget);
		} else {
			translation = newDomainWidgetTranslation(project, widget, datasetProperty, dataset);
		}
		return translation;
	}

	/**
	 * @param project
	 * @param widget
	 * @return
	 */
	protected WidgetTranslation newWidgetTranslation(IProject project, Widget widget) {
		return new WidgetTranslation(this,project,widget);
	}

	/**
	 * @param project
	 * @param widget
	 * @param datasetProperty
	 * @param dataset
	 * @return
	 */
	protected DomainWidgetTranslation newDomainWidgetTranslation(IProject project, Widget widget,
			Property datasetProperty, Property dataset) {
		return new DomainWidgetTranslation(this, project, widget, dataset, datasetProperty);
	}

}
