package com.temenos.ds.t24.data.eson;

import org.apache.commons.lang3.StringUtils;

import com.temenos.ds.t24.data.eson.ESONDataModel.Feature;
import com.temenos.ds.t24.data.eson.ESONDataModel.MultiValue;
import com.temenos.ds.t24.data.eson.ESONDataModel.NewObject;
import com.temenos.ds.t24.data.eson.ESONDataModel.Value;

/**
 * This class holds the higher level methods helping creating ESON for T24
 * @author yandenmatten
 */
public class ESONDataModelFactoryForT24 extends ESONDataModelFactory {

	public void addRootFeature(String fieldName, String fieldValue, int mv, String mvGroupName) {
		addRootFeature(fieldName, fieldValue, mv, mvGroupName, 1, "");
	}

	public void addRootFeature(String fieldName, String fieldValue, int mv, String mvGroupName, int sv, String svGroupName) {
		Feature existingFeature = findExistingRootFeature(mvGroupName);
		Feature feature;
		if (existingFeature == null) {
			feature = addFeature(esonDataModel.root, mvGroupName);
		} else {
			feature = existingFeature;
		}

		if (feature.value == null) {
			feature.value = createMultiValue();
		}
		if (!(feature.value instanceof MultiValue)) {
			throw new IllegalStateException("The existing feature " + feature.eFeature + " is not a multitype, therefore you cannot use it to hold multivalues");
		}
		MultiValue featureMultiValue = (MultiValue)feature.value;
		
		// Verify type
		final String newObjectClassForMultiValue = esonDataModel.root.eClass.replace('.', '_') + "__" + mvGroupName.replace('.', '_');
		for (Value value : featureMultiValue.values) {
			if (!(value instanceof NewObject)) {
				throw new IllegalStateException("The multi value feature " + fieldName + " doesn't contain only NewObject. This is not legal to hold T24 data");
			}
			NewObject newObject = (NewObject)value;
			if (!newObject.eClass.equals(newObjectClassForMultiValue)) {
				throw new IllegalStateException("The multi value feature " + fieldName + " doesn't contain only NewObject with eClass " + newObjectClassForMultiValue + ". This is not legal to hold T24 data");
			}
		}

		// Add to a new or existing NewObject
		NewObject newObject;
		if (featureMultiValue.values.size() + 1 == mv) {
			// Adding to a new NewObject
			newObject = addNewObject(featureMultiValue, newObjectClassForMultiValue);
		} else if (featureMultiValue.values.size() + 1 > mv) {
			// Adding to an existing NewObject
			Value value = featureMultiValue.values.get(mv-1);
			if (value instanceof NewObject) {
				newObject = (NewObject)value;
			} else {
				throw new IllegalStateException("Unable to add a multi value field to something else than a NewObject");
			}
		} else {
			throw new IllegalStateException("Unordered multivalue adding not supported (" + fieldName + ", " + fieldValue + ", mv=" + mv + ", sv=" + sv + ").");
		}
		
		if (StringUtils.isEmpty(svGroupName)) {
			// TODO ensure the feature is not already here and fails if yes.
			Feature multiValueFeature = addFeature(newObject, fieldName);
			multiValueFeature.value = createStringAttribute(fieldValue);
		} else {
			// Sub value
			// TODO ensure the feature is not already here and fails if yes.
			Feature multiValueFeature = addFeature(newObject, svGroupName);
			MultiValue multiValueForSubValue = addMultiValue(multiValueFeature);
			// TODO: should support adding to the last multiValueForSubValue
			if (multiValueForSubValue.values.size() + 1 == sv) {
				final String newObjectForSubValue = newObjectClassForMultiValue + "__" + svGroupName;
				// TODO : do not create new object if existing
				NewObject newObject2 = createNewObject(newObjectForSubValue);
				multiValueForSubValue.values.add(newObject2);
				Feature multiValueForSubValueFeature = addFeature(newObject2, fieldName);
				multiValueForSubValueFeature.value = createStringAttribute(fieldValue);
			} else {
				throw new IllegalStateException("Unordered subvalue adding not supported yet (" + fieldName + ", " + fieldValue + ", mv=" + mv + ", sv=" + sv + ").");
			}
		}
	}

}
