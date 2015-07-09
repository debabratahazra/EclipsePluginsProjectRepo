package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class CheckboxPropertyFilter implements PropertyFilter {

	@Override
	public List<Property> filter(Widget widget) {
		
		// These are the WidgetType's of the Label and Field
		MetaModel mm = MetaModelRegistry.getMetaModel();
		String library = widget.getLibraryName();
		WidgetType wt = mm.findWidgetType(library, widget.getTypeName());
		
		// Add all the PropertyTypes which exist 
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(wt.getAllPropertyTypes().values());
		Widget tableCol = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
		if (tableCol != null) {
			ITableColumn column = TableHelper.getTableColumn(tableCol);
			if (column.isPlaceHolder()) {
				allowedTypes.remove(mm.findPropertyType(library, "domainAttribute"));
			} else {
				allowedTypes.remove(mm.findPropertyType(library, "column-checkbox-identifier"));
				allowedTypes.remove(mm.findPropertyType(library, "column-checkbox-securtiy"));
				allowedTypes.remove(mm.findPropertyType(library, "column-checkbox-action"));				
			}
		} else {
			allowedTypes.remove(mm.findPropertyType(library, "column-checkbox-identifier"));
			allowedTypes.remove(mm.findPropertyType(library, "column-checkbox-security"));
			allowedTypes.remove(mm.findPropertyType(library, "column-checkbox-action"));				
		}
		
		
		// DS-4763-begin -- checkbox with attribute's type based on EnumMask.
		boolean hidePropertyEnumValue = true;
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
		if (ofsProject != null) {
			String datasetName = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			if (StringUtils.isNotBlank(datasetName)) {
				DomainRepository repository = DomainRepository.getInstance(ofsProject);
				MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
				if (datasetName != null) {
					String domainAttribute = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
					if (StringUtils.isNotBlank(domainAttribute)) {
						MdfDatasetProperty domainAttributeProperty = dataset.getProperty(domainAttribute);
						MdfEntity type = domainAttributeProperty.getType();
						if (type instanceof MdfEnumeration) {
							MdfPrimitive primType = ((MdfEnumerationImpl) type).getType(); 
							if (primType.getName().equals("EnumMask")) {
								hidePropertyEnumValue = false;
							}
						}
					}
				}
			}
		}
		if (hidePropertyEnumValue) {
			allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.ENUM_VALUE));
		} else {
			allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.GROUP));
		}
		// DS-4763-end
		
		
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		
		return result;
	}

}
