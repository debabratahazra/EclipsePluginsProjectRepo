package com.odcgroup.menu.generation.tap;

import java.io.IOException;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.CodeGenerationPreferences;

public class MenuGenerationHelper {

	private static final String RESOURCE_URL_PREFIX = "resource:///";
	private static final String PAGEFLOW_EXTN = ".pageflow";

	/**
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * @return
	 */
	public static String getDSBuildID() {
		String designStudioFeatureID = "com.odcgroup.designstudio";
		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		if (providers != null) {
			for (int i = 0; i < providers.length; ++i) {
				IBundleGroup[] bundleGroups = providers[i].getBundleGroups();
				for (IBundleGroup bg : bundleGroups) {
					if (bg.getIdentifier().equals(designStudioFeatureID)) {
						return bg.getName() + " " + bg.getVersion();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * @param item
	 * @return
	 */
	public static String getClosetag(MenuItem item) {
		if (item.getSubmenus().isEmpty()) {
			return "/>";
		} else {
			return ">";
		}
	}

	/**
	 * @return
	 */
	public static String resolveDSPageflowURIforTAP(MenuItem item) {
		String rurl = "";
		String url = item.getPageflow();
		if (url != null) {
			if (url.startsWith(RESOURCE_URL_PREFIX)
					&& url.endsWith(PAGEFLOW_EXTN)) {
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(item
						.eResource());
				IProject project = ofsProject.getProject();
				IOfsModelResource mres;
				String targetFileName = "";
				try {
					mres = ofsProject.getOfsModelResource(URI.createURI(url));
					EList<EObject> modelContents = mres.getEMFModel();
					for (EObject model : modelContents) {
						if ("Pageflow".equals(model.eClass().getName())) {
							targetFileName = (String) model.eGet(model.eClass()
									.getEStructuralFeature("fileName"));
						}
					}
					String activity = CodeGenerationPreferences
							.getPageflowActivityPreference(project);
					rurl = "/activity/" + activity + "/pageflow/"
							+ targetFileName + "/"
							+ getModelName(url, "pageflow");
				} catch (ModelNotFoundException e) {
				} catch (IOException e) {
				} catch (InvalidMetamodelVersionException e) {
				}
			} else {
				rurl = url;
			}
		}
		return rurl;
	}

	/**
	 * @param resourceURL
	 * @return string
	 */
	private static String getModelName(String resourceURL, String extn) {
		int index = resourceURL.lastIndexOf("/");
		return resourceURL.substring(index + 1,
				resourceURL.length() - extn.length() - 1);
	}

}
