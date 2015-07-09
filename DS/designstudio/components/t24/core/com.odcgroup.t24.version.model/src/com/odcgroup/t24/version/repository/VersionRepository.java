package com.odcgroup.t24.version.repository;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.t24.common.repository.T24LanguageRepository;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;

public class VersionRepository extends T24LanguageRepository implements IVersionRepository {

	@Override
	public void createUserData(EObject eObject, Map<String, String> userData) {
		
		if (eObject instanceof Version) {
			
			Version version = (Version)eObject;
			
			String t24ScreenName = VersionUtils.getVersionName(version, "t24");
			String screenName = "";
			String appName = "";
			
			if (t24ScreenName != null && t24ScreenName.contains(",")){
				int pos = t24ScreenName.indexOf(',');
				appName = t24ScreenName.substring(0, pos);
				screenName = t24ScreenName;
			} 
			
			String domain = VersionUtils.getVersionApplicationName(version); 
			String product = "";
			if (domain != null && domain.contains(":")) {
				int pos = domain.indexOf(':');
				product = domain.substring(0, pos);
				if (appName.isEmpty()){
					appName = domain.substring(pos+1, domain.length());
				}
			} else {
				product = domain;
			}
			
			String component = "";
			String module = "";
			if (product != null && product.contains("_")) {
				int pos = product.indexOf('_');
				module = product.substring(0, pos);
				component = product;
			}
	
			setT24ComponentName(userData, component);
			setT24ApplicationName(userData, appName);
			setT24ModuleName(userData, module);
			setT24Name(userData, screenName);
		}

	}

	@Override
	public Iterable<IEObjectDescription> getAllVersions() {
		return getExportedObjectsByType(VersionDSLPackage.Literals.VERSION);
	}

	/**
	 * @param languageName
	 */
	public VersionRepository() {
		super(IVersionRepository.VERSION_LANGUAGE_NAME);
	}

}
