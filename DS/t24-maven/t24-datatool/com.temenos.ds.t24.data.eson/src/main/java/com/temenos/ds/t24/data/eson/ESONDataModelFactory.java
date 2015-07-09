package com.temenos.ds.t24.data.eson;

import com.temenos.ds.t24.data.eson.ESONDataModel.Attribute;
import com.temenos.ds.t24.data.eson.ESONDataModel.Feature;
import com.temenos.ds.t24.data.eson.ESONDataModel.MultiValue;
import com.temenos.ds.t24.data.eson.ESONDataModel.NewObject;
import com.temenos.ds.t24.data.eson.ESONDataModel.StringAttribute;

/**
 * Factory to help creating ESON Data Model. Used by the parser and for altering ESON once parsed.
 * 
 * @author yandenmatten
 */
public class ESONDataModelFactory {

	protected ESONDataModel esonDataModel = new ESONDataModel();
	
	public ESONDataModelFactory() {
	}
	
	public void addImportedNamespace(String importedNamespace) {
		esonDataModel.importedNamespaces.add(importedNamespace);
	}
	
	public void setRootEClass(String eClass) {
		esonDataModel.root.eClass = eClass;
	}
	
	public ESONDataModel.Feature addRootFeature(String eFeature) {
		return addFeature(esonDataModel.root, eFeature);
	}

	public void addRootFeature(String eFeature, String stringAttributeValue) {
		addRootFeature(eFeature, stringAttributeValue, 1);
	}
	
	public void addRootFeature(String eFeature, String stringAttributeValue, int index) {
		Feature existingFeature = findExistingRootFeature(eFeature);
		if (existingFeature == null) {
			Feature feature = addFeature(esonDataModel.root, eFeature);
			addStringAttribute(feature, stringAttributeValue, index);
		} else {
			if (existingFeature.value == null) {
				addStringAttribute(existingFeature, stringAttributeValue, index);
			} else {
				addStringAttribute(existingFeature, stringAttributeValue, index);
			}
		}
	}

	public Feature findExistingRootFeature(String eFeature) {
		for (Feature existingFeature : esonDataModel.root.features) {
			if (eFeature.equals(existingFeature.eFeature)) {
				return existingFeature;
			}
		}
		return null;
	}

	public Feature addFeature(NewObject newObject, String eFeature) {
		Feature feature = new Feature();
		feature.eFeature = eFeature;
		newObject.features.add(feature);
		return feature;
	}
	
	public void addRootFeature(Feature feature) {
		esonDataModel.root.features.add(feature);
	}
	
	public Feature createFeature(String eFeature) {
		Feature feature = new Feature();
		feature.eFeature = eFeature;
		return feature;
	}

	public MultiValue addMultiValue(Feature feature) {
		MultiValue multiValue = new MultiValue();
		feature.value = multiValue;
		return multiValue;
	}
	
	public MultiValue createMultiValue() {
		MultiValue multiValue = new MultiValue();
		return multiValue;
	}

	public NewObject addNewObject(MultiValue multiValue, String eClass) {
		NewObject newObject = new NewObject();
		newObject.eClass = eClass;
		multiValue.values.add(newObject);
		return newObject;
	}

	public void addStringAttribute(Feature feature, String stringAttributeValue) {
		addStringAttribute(feature, stringAttributeValue, 1);
	}

	public void addStringAttribute(Feature feature, String stringAttributeValue, int index) {
		if (feature.value == null) {
			if (index == 1) {
				StringAttribute stringAttribute = new StringAttribute();
				stringAttribute.value = stringAttributeValue;
				feature.value = stringAttribute;
			} else {
				throw new IllegalStateException("Unordered add not supported yet.");
			}
		} else {
			if (feature.value instanceof MultiValue) {
				MultiValue multiValue = (MultiValue)feature.value;
				
				// Simple add at the end of the values (as index is one base, we need -1)
				if (multiValue.values.size() == index-1) {
					StringAttribute stringAttribute = new StringAttribute();
					stringAttribute.value = stringAttributeValue;
					multiValue.values.add(stringAttribute);
				} else {
					throw new IllegalStateException("Unordered add not supported yet.");
				}
			} else if (feature.value instanceof Attribute) {
				// Some updates will be needed here when we'll support other value type that stringAttribute
				
				// Here is the mutation case. A attribute value must be replaced by a MutiValue
				Attribute singleValueAttribute = (Attribute)feature.value;
				MultiValue multiValue = new MultiValue();
				feature.value = multiValue;
				multiValue.values.add(singleValueAttribute);

				if (multiValue.values.size() == index-1) {
					// New value
					StringAttribute stringAttribute = new StringAttribute();
					stringAttribute.value = stringAttributeValue;
					multiValue.values.add(stringAttribute);
				} else {
					throw new IllegalStateException("Unordered add not supported yet.");
				}
			} else {
				throw new IllegalStateException("Cannot pub a " + feature.value.getClass() + " in to a multi valued with a string attribute");
			}
		}
	}

	public StringAttribute addStringAttribute(MultiValue multiValue, String stringAttributeValue) {
		StringAttribute stringAttribute = new StringAttribute();
		stringAttribute.value = stringAttributeValue;
		multiValue.values.add(stringAttribute);
		return stringAttribute;
	}
	
	public StringAttribute createStringAttribute(String stringAttributeValue) {
		StringAttribute stringAttribute = new StringAttribute();
		stringAttribute.value = stringAttributeValue;
		return stringAttribute;
	}
	
	public NewObject addNewObject(Feature feature, String eClass) {
		NewObject newObject = new NewObject();
		newObject.eClass = eClass;
		feature.value = newObject;
		return newObject;
	}
	
	public NewObject createNewObject(String eClass) {
		NewObject newObject = new NewObject();
		newObject.eClass = eClass;
		return newObject;
	}

	public ESONDataModel getEsonDataModel() {
		return esonDataModel;
	}

}
