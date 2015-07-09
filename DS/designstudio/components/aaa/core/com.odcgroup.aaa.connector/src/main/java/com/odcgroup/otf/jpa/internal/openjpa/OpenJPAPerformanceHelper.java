package com.odcgroup.otf.jpa.internal.openjpa;

import java.lang.reflect.Method;

import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.OpenJPAId;

public class OpenJPAPerformanceHelper {
	
	/**
	 * Get the id of an association without needing to fetch the association.
	 * Only works if the entity is not yet detached!
	 * 
	 * @param entity a PersistenceCapable JPA Entity instance
	 * @param fieldName field name of the association
	 * @return id of the object pointed by the association, or null if the Entity was already detached
	 */
	public static Object getAssociationRef(Object entity, String fieldName) {
		if (!(entity instanceof PersistenceCapable)) {
			// Use specialized exception
			throw new RuntimeException("The entity must be an OpenJPA entity, therefore it should implements (tranparently) the PersistenceCapable interface");
		}
		PersistenceCapable pc = (PersistenceCapable)entity;
		OpenJPAStateManager stateManager = (OpenJPAStateManager)pc.pcGetStateManager();
		if (stateManager != null && !stateManager.isDetached()) {
			FieldMetaData fieldMetaData = stateManager.getMetaData().getDeclaredField(fieldName);
			if (fieldMetaData != null) {
				Object objId = stateManager.getIntermediate(fieldMetaData.getIndex());
				if (objId != null) {
					return ((OpenJPAId)objId).getIdObject();
				} else {
					return null;
				}
			} else {
				// TODO Use specialized exception
				throw new RuntimeException("The field \"" + fieldName + "\" is not found in the metadata of the " + entity.getClass() + " instance.");
			}
		} else {
			// The metadata is unavailable because the state manager is detached 
			return null;
		}
		
	}

	public static void initializeGetRef(Object datasetObject) {
		// Initialize get*Ref() methods
		Method[] methods = datasetObject.getClass().getMethods();
		for (int i=0; i<methods.length; i++) {
			// It the method of the get*Ref() form ?
			if (methods[i].getName().startsWith("get") &&
					methods[i].getName().endsWith("Ref") &&
					methods[i].getParameterTypes().length == 0) {
				// Initialize the cache
				try {
					methods[i].invoke(datasetObject);
				} catch (Exception e) {
					// TODO use specialized exception
					throw new RuntimeException("Unable to initlize properly get*Ref of the dataset: " + datasetObject, e);
				}
			}
		}
	}
}
