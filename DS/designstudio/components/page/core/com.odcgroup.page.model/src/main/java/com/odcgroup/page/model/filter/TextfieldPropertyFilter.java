package com.odcgroup.page.model.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.FilterUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Filter to only display precision and scale properties 
 * <br/>for Business Type (5.1)
 * 
 * <ul>
 * 	<li>Amount Exchange</li>
 *  <li>Long</li>
 *  <li>Amount</li>
 *  <li>Number</li>
 *  <li>Percent</li>
 * </ul>
 * 
 * Primitive Types (5.2)
 * 
 * <ul>
 * 	<li>mml:double</li>
 *  <li>mml:Double</li>
 *  <li>mml:decimal</li>
 *  <li>mml:float</li>
 *  <li>mml:Float</li>
 * </ul>
 * 
 * Created as part of DS-4558
 * 
 * @author can
 * 
 */
public class TextfieldPropertyFilter implements PropertyFilter {
		
	public static final String BUSINESS_TYPE = "(BT)";
	private String[] filterPropertys={PropertyTypeConstants.PREVIEW_VALUE,
		                             PropertyTypeConstants.ACCESS_KEY ,
		                             PropertyTypeConstants.TAB_INDEX ,
		                             PropertyTypeConstants.EDITABLE_IS_BASED_ON,
		                             PropertyTypeConstants.MAXCHARACTERS_TYPE,
		                             PropertyTypeConstants.REQUIRED ,
		                             PropertyTypeConstants.HORIZONTAL_ALIGNMENT ,
		                             PropertyTypeConstants.VERTICAL_ALIGNMENT ,
		                             PropertyTypeConstants.PATTERN_TYPE,
		                             PropertyTypeConstants.CSS_CLASS,
		                             PropertyTypeConstants.TYPE,
		                             PropertyTypeConstants.COLUMNS,
		                             PropertyTypeConstants.WIDGET_BORDER,
		                             PropertyTypeConstants.WIDGET_GROUP,
		                            
		                             
		                             
		
	};

	@Override
	public List<Property> filter(Widget widget) {

		EList<Property> orginalWidgetProperties = widget.getProperties();
		List<Property> filteredProperties=null;
		Set<PropertyType> allWidgetTypeProperties = FilterUtils.populateWithAllWidgetTypeProperties(widget);
		String domainAttributeName = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		
		if (StringUtils.isNotBlank(domainAttributeName)) {
			
			String datasetName = widget.findPropertyValueInParent(PropertyTypeConstants.DOMAIN_ENTITY);
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
			DomainRepository repository = DomainRepository.getInstance(ofsProject);
			MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
			if(dataset!=null){
			MdfDatasetProperty mdfDatasetProperty = dataset.getProperty(domainAttributeName);
			MdfEntity mdfDatasetPropertyType = mdfDatasetProperty.getType();
			if (mdfDatasetPropertyType!=null && !isSignificantFigureType(mdfDatasetPropertyType)) {
				filteredProperties=filterSignificantProperties(widget, orginalWidgetProperties, allWidgetTypeProperties);
			}
			}
			//removed DS#4558 not set in the widget...
		} 
		else {
			 filteredProperties= filterSignificantProperties(widget, orginalWidgetProperties, allWidgetTypeProperties);
		}
		if (filteredProperties == null) {
			filteredProperties = orginalWidgetProperties;
		}
		//filter properties if the widget is inside the xtooltip type fragment or module
		if(filteredProperties!=null){
		   filteredProperties = filterProperties(widget, filteredProperties);
		 }
		
		
		return filteredProperties;
		
	}

//	/**
//	 * @param widget
//	 * @param mdfDatasetPropertyType
//	 * @param property 
//	 */
//	@SuppressWarnings("unchecked")
//	private void replaceBusinessTypeValue(Widget widget, MdfEntity mdfDatasetPropertyType, Property property) {
//		
//		String value = property.getValue();
//
//		if(StringUtils.isBlank(value) || value.contains(BUSINESS_TYPE)) {
//			
//			List<MdfAnnotationImpl> annotations = mdfDatasetPropertyType.getAnnotations();
//
//			for (MdfAnnotationImpl mdfAnnotation : annotations) {
//				if (mdfAnnotation.getName().equalsIgnoreCase("constraints")) {
//					List<MdfAnnotationPropertyImpl> annotationProperties = mdfAnnotation.getProperties();
//					for (MdfAnnotationPropertyImpl mdfAnnotationProperty : annotationProperties) {
//						String propertyTypeName = property.getTypeName();
//						String annotationPropertyName = mdfAnnotationProperty.getName();
//
//						if (annotationPropertyName.equalsIgnoreCase(propertyTypeName)) {
//							String annotationValue = mdfAnnotationProperty.getValue();
//							property.setValue(annotationValue + " " + BUSINESS_TYPE);
//						}
//					}
//				}
//			}
//		}
//	}

	/**
	 * @param widget
	 * @param orginalWidgetProperties
	 * @param visiblePropertyTypes
	 * @return
	 */
	private List<Property> filterSignificantProperties(Widget widget, EList<Property> orginalWidgetProperties,
			Set<PropertyType> visiblePropertyTypes) {
		
		FilterUtils.hideProperty(PropertyTypeConstants.SCALE, widget, visiblePropertyTypes);
		FilterUtils.hideProperty(PropertyTypeConstants.PRECISION, widget, visiblePropertyTypes);
		
		List<Property> filteredProperties = FilterUtils.filterProperties(orginalWidgetProperties, visiblePropertyTypes);
		return filteredProperties;
	}

	/**
	 * @param type
	 * @return
	 */
	private boolean isSignificantFigureType(MdfEntity type) {
		if(type.getParentDomain().getName().equalsIgnoreCase("mml")) {
			return isPrimitiveType(type);
		}
		else if(type.getParentDomain().getName().equalsIgnoreCase("AAABusinessTypes")) {
			return isBusinessType(type);
		}
		else {
			return false;
		}
	}
	

	/**
	 * @param type
	 * @return
	 */
	private boolean isBusinessType(MdfEntity type) {
		String typeNme = type.getName();
		if (typeNme.equalsIgnoreCase("amount")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("exchange")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("longAmount")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("number")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("percent")) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @param type
	 * @return
	 */
	private boolean isPrimitiveType(MdfEntity type) {
		String typeName = type.getName();
		if (typeName.equalsIgnoreCase("double")) {
			return true;
		}
		if (typeName.equalsIgnoreCase("decimal")) {
			return true;
		}
		if (typeName.equalsIgnoreCase("float")) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * filter if the widget is insdie the Xtooltip type frgament /module
	 * 
	 */
	private List<Property> filterProperties(Widget widget,List<Property> filteredProperties){
		Widget root=widget.getRootWidget();
		Property frgamntType=root.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
		if((frgamntType!=null&&frgamntType.getValue().equals("xtooltip"))){
			Property editable=widget.findProperty(PropertyTypeConstants.EDITABLE);
			//set the editable value to false and hide the property
			if(editable!=null){
				editable.setValue(false);
				filteredProperties.remove(editable);
			}
			for(String property:Arrays.asList(filterPropertys)){
			    Property pro=widget.findProperty(property);
			    if(pro!=null){
				filteredProperties.remove(widget.findProperty(property));
			    }
			}
			
		}
		
		return filteredProperties;
	}
}
