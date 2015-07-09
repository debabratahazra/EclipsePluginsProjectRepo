package com.temenos.ds.t24.data.eson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.eson.ESONDataModel.Feature;
import com.temenos.ds.t24.data.eson.ESONDataModel.MultiValue;
import com.temenos.ds.t24.data.eson.ESONDataModel.NewObject;
import com.temenos.ds.t24.data.eson.ESONDataModel.StringAttribute;
import com.temenos.ds.t24.data.eson.ESONDataModel.Value;
import com.temenos.ds.t24.data.rps.ResourceProviderService;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;

/**
 * Convert Component framework response to ESON data model and vice versa
 * @author yandenmatten
 */
public class ESONResourceProviderMapper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ESONResourceProviderMapper.class);
	
	public GetResourceResponse toGetResourceResponse(ESONDataModel esonDataModel) {
		GetResourceResponse response = new ResourceProviderService.GetResourceResponse(esonDataModel.root.eClass);
		for (Feature feature : esonDataModel.root.features) {
			if (feature.value instanceof StringAttribute) {
				response.fields.add(new Field(feature.eFeature, ((StringAttribute)feature.value).value));
			} else if (feature.value instanceof MultiValue) {
				// Multivalue
				String mvGroupName = feature.eFeature;
				MultiValue multiValue = feature.getValueAsMultiValue();
				for (int mv = 1; mv <= multiValue.values.size(); mv++) {
					Value value = multiValue.values.get(mv-1);
					if (value instanceof NewObject) {
						NewObject newObject = (NewObject)value;
						for (Feature multiValueFeature: newObject.features) {
							if (multiValueFeature.value instanceof StringAttribute) {
								response.fields.add(new Field(multiValueFeature.eFeature, ((StringAttribute)multiValueFeature.value).value, mv, mvGroupName, 1, ""));
							} else if (multiValueFeature.value instanceof MultiValue) {
								// Subvalue
								MultiValue subValue = (MultiValue)multiValueFeature.value;
								for (int sv = 1; sv <= subValue.values.size(); sv++) {
									Value value2 = subValue.values.get(sv-1);
									if (value2 instanceof NewObject) {
										NewObject newObject2 = (NewObject)value2;
										String svGroupName = newObject2.eClass;
										for (Feature subValueFeature : newObject2.features) {
											response.fields.add(new Field(subValueFeature.eFeature, ((StringAttribute)subValueFeature.value).value, mv, mvGroupName, sv, svGroupName));
										}
									} else {
										throw new IllegalStateException("Feature \"" + feature.eFeature + "\" with multi value feature \"" + multiValueFeature.eFeature + "\" with subvalue value of " + value2.getClass() + " type not supported.");
									}
								}
							} else {
								throw new IllegalStateException("Feature \"" + feature.eFeature + "\" with multi value feature \"" + multiValueFeature.eFeature + "\" value of " + multiValueFeature.value.getClass() + " type not supported.");
							}
						}
					} else {
						throw new IllegalStateException("Feature \"" + feature.eFeature + "\" with multi value of " + value.getClass() + " type not supported.");
					}
				}
			} else {
				throw new IllegalStateException("Feature \"" + feature.eFeature + "\" with value of " +feature.value.getClass() + " type not supported.");
			}
		}
		return response;
	}
	
	public ESONDataModel toESON(GetResourceResponse resource) {
		ESONDataModelFactoryForT24 factory = new ESONDataModelFactoryForT24();
		factory.addImportedNamespace("t24.applications.*");
		factory.setRootEClass(resource.resourceName);
		for (Field field : resource.fields) {
			if (StringUtils.isEmpty(field.mvGroupName) && 
					field.mv == 1 &&
					StringUtils.isEmpty(field.svGroupName) &&
					field.sv == 1) {
				factory.addRootFeature(field.name, field.value);
			} else if (StringUtils.isNotEmpty(field.mvGroupName) &&
					(field.sv == 1 || StringUtils.isNotEmpty(field.svGroupName))) {
				// This field is multi valued
				if (StringUtils.isEmpty(field.svGroupName)) {
					factory.addRootFeature(field.name, field.value, field.mv, field.mvGroupName);
				} else {
					// This field is sub valued as well
					factory.addRootFeature(field.name, field.value, field.mv, field.mvGroupName, field.sv, field.svGroupName);
				}
			} else {
				LOGGER.warn("Mapping to ESON not possible with this record (" + field + ")");
			}
		}
		return factory.getEsonDataModel();
	}
}
