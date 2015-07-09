package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.FilterUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Filter to only display precision and scale properties 
 * <br/>for Business Type (5.1)
 */
public class TableColumnTextPropertyFilter implements PropertyFilter {
		
	@Override
	public List<Property> filter(Widget widget) {
		
		// These are the WidgetType's of the Label and Field
		MetaModel mm = MetaModelRegistry.getMetaModel();
		String library = widget.getLibraryName();
		WidgetType wt = mm.findWidgetType(library, widget.getTypeName());

		// Add all the PropertyTypes which exist 
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(wt.getAllPropertyTypes().values());		

		allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.EDITABLE));

		boolean filterPrecisionAndScale = false;
		MdfDatasetProperty dsp = getDatasetProperty(widget);
		if (dsp != null) {
			MdfEntity dspType = dsp.getType();
			if (dspType != null) {
				filterPrecisionAndScale = ! FilterUtils.isSignificantFigureType(dspType);
			}
		} else {
			filterPrecisionAndScale = true;
		}
		if (filterPrecisionAndScale) {
			allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.SCALE));
			allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.PRECISION));
		}
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		return result;
	}
	
	
    private MdfDatasetProperty getDatasetProperty(Widget widget) {
    	Property attrProp = widget.findProperty(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
    	String datasetAttribute = attrProp != null ? attrProp.getValue() : "";
    	MdfDatasetProperty dsp = null;
    	Widget wTable = widget.findAncestor(WidgetTypeConstants.TABLE_TREE);
    	if (wTable != null) {
			Property prop = wTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
			if (StringUtils.isNotEmpty(datasetName)) {
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
				DomainRepository repository = DomainRepository.getInstance(ofsProject);
				MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
				if (dataset != null) {
						dsp = dataset.getProperty(datasetAttribute);
					}
			}
		}
		return dsp;
    }

}
