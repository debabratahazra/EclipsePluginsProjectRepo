package com.odcgroup.page.ui.workbench;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * Creates a mapping of the QualifName-Cardinality for each Fragment in a
 * project.
 * 
 * @author Gary Hayes
 */
public class DefaultFragmentValidator {

	/**
	 * Used to separate the domain entity QName from the cardinality in the key.
	 */
	private static final String KEY_SEPARATOR = "_";

	/** The OFS project. */
	private IOfsProject ofsProject;

	/**
	 * The mapping between the fragments and the Domain Datasets. The key is the
	 * QName_Cardinality.
	 */
	private Map<String, List<IOfsModelResource>> defaultFragmentsMap = new HashMap<String, List<IOfsModelResource>>();

	/**
	 * Creates a new DefaultFragmentValidator.
	 * 
	 * @param ofsProject
	 *            The OFS project
	 */
	public DefaultFragmentValidator(IOfsProject ofsProject) {

		this.ofsProject = ofsProject;

		/**
		 * Creates the mapping between the Domain Entities / Datasets and the
		 * default fragments which they are associated with.
		 */
		for (IOfsModelResource modelResource : getAllFragmentResources()) {

			try {

				Model model = (Model) modelResource.getEMFModel().get(0);
				if (model != null) {

					Widget rw = model.getWidget();
	
					String ev = rw.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
					if (StringUtils.isNotEmpty(ev)) {
	
						String dv = rw.getPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT);
						boolean isDefaultFragment = Boolean.valueOf(dv);
						if (isDefaultFragment) {
		
							String cv = rw.getPropertyValue(PropertyTypeConstants.CARDINALITY);
							String key = ev + KEY_SEPARATOR + cv;

							List<IOfsModelResource> list = defaultFragmentsMap.get(key);
							if (list == null) {
								list = new ArrayList<IOfsModelResource>();
								defaultFragmentsMap.put(key, list);
							}
			
							list.add(modelResource);
						}
					}
				}

			} catch (IOException ex) {
				PageUIPlugin.getDefault().logError("Error reading the model file " + modelResource.getName(), ex);
			} catch (InvalidMetamodelVersionException ex) {
				PageUIPlugin.getDefault().logError(
						"Model file '" + modelResource.getName() + "' has an invalid metamodel version!", ex);
			}
		}
	}

	/**
	 * Gets the fragments which are defined as Default with a matching
	 * cardinality for the same Entity.
	 * 
	 * @return List of maps Key - String The QName_Cardinality Value - The List
	 *         of OFS model resources which clash
	 */
	public List<Map.Entry<String, List<IOfsModelResource>>> getNonUniqueFragments() {
		List<Map.Entry<String, List<IOfsModelResource>>> result = new ArrayList<Map.Entry<String, List<IOfsModelResource>>>();
		for (Map.Entry<String, List<IOfsModelResource>> entry : defaultFragmentsMap.entrySet()) {
			List<IOfsModelResource> list = entry.getValue();
			if (list.size() > 1) {
				result.add(entry);
			}
		}
		return result;
	}

	/**
	 * Returns the resources which are defined as Default for the same Entity
	 * and have the same Cardinality.
	 * 
	 * @param qName
	 *            The qualified name of the Entity
	 * @param cardinality
	 *            The cardinality
	 * @return List IResources or null if no other Fragment exists
	 */
	public List<IOfsModelResource> exists(String qName, String cardinality) {
		String key = qName + KEY_SEPARATOR + cardinality;
		return defaultFragmentsMap.get(key);
	}

	/**
	 * Gets all the resources that are fragments
	 * 
	 * @return List of IOfsModelResource's
	 */
	private Set<IOfsModelResource> getAllFragmentResources() {
		String[] extensions = { PageConstants.FRAGMENT_FILE_EXTENSION };
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, extensions);
		return lookup.getAllOfsModelResources();
	}
}
