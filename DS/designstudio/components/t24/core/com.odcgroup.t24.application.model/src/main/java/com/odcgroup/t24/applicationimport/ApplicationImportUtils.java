package com.odcgroup.t24.applicationimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * Constants used for T24 business types import
 *
 * @author vramya
 *
 */
public final class ApplicationImportUtils {
	public static String[] BUSSINESS_TYPES = {"A","AA", "AAA", "AMTCCY", "AMTLCCY", "ANY", "D", "DD", "FQU", "PASSWD", "PERIOD", "R", "S", "SS", "SSS", "TEXT", "TIME", "TIMEH", "TIMEHS", "TIMES"};
	public static String BUSINESSTYPES_PACKAGE = "businessTypes";

	public static String BUSINESSTYPES_FILE_NAME = "BusinessTypes";
	public static String BUSINESSTYPES_DOMAIN = "T24BusinessTypes";
	public static String BUSINESSTYPES_DOMAINS_NAMESPACE = "business-types";
	
	public static String DOMAIN = "domain";
	public static String BUSINESS_TYPES_FOLDER = "/businessTypes/";
	public static String BUSINESS_TYPES_NAMESPACE_URL = "http://www.odcgroup.com/";
	
	public static String CHARTYPE_NUMERIC = "NUMERIC";
	
	/**
	 * Method to get valid business type name to be displayed
	 * 
	 * @param dictPermName
	 * @return
	 */
	public static String getBusinessTypeName(String businessType) {
		businessType = replaceSpecialCharByUnderscores(businessType);
		if (StringUtils.isNumeric(businessType)) {
			businessType = "_" + businessType.trim();
		}
		return businessType;
	}

	/**
	 * Method to replace all the special characters to '_'
	 * 
	 * @param name
	 * @return
	 */
	public static String replaceSpecialCharByUnderscores(String name) {
		if (StringUtils.isNotBlank(name)) {
			name = name.trim().replaceAll("[^A-Za-z0-9-]", "_");
		}
		return name;
	}
	
	public static Map<String, IEObjectDescription> mapDomainNameWIthDescriptors(URI uri ,IResourceDescriptions index) {
		Iterable<IEObjectDescription> objectsDescriptors = getAllDomainsinAProject(uri ,index);
		HashMap<String, IEObjectDescription> domainNameEobjectDescriptorMap = new HashMap<String, IEObjectDescription>();
		for (IEObjectDescription description : objectsDescriptors) {
				QualifiedName desQualifiedName = description.getName();
				if (desQualifiedName !=null && desQualifiedName.getSegmentCount() == 1) {
					domainNameEobjectDescriptorMap.put(desQualifiedName.getFirstSegment(), description);
				}
			}
		return domainNameEobjectDescriptorMap;
	}
	
	
	public static Iterable<IEObjectDescription> getAllDomainsinAProject(URI uri ,IResourceDescriptions index ) {
		List<IEObjectDescription> filteredDescriptors = new ArrayList<IEObjectDescription>();
			for (IEObjectDescription objDescriptor : index.getExportedObjects()) {
				if(isInSameLocation(objDescriptor, uri)){
					filteredDescriptors.add(objDescriptor);
				}
			}
		
		return filteredDescriptors;
	}
	
	public static Iterable<IEObjectDescription> getAllDomainsinAProject(String projectName ,IResourceDescriptions index ) {
		List<IEObjectDescription> filteredDescriptors = new ArrayList<IEObjectDescription>();
			for (IEObjectDescription objDescriptor : index.getExportedObjects()) {
				if (isInSameProject(objDescriptor, projectName)) {
					filteredDescriptors.add(objDescriptor);
				}
			}
		
		return filteredDescriptors;
	}

	private static boolean isInSameLocation(IEObjectDescription input, URI uri) {
		URI uri2 = input.getEObjectURI();
		if(uri.devicePath().equals(uri2.devicePath())){
			return true;
		}
		return false;
	}

	private static boolean isInSameProject(IEObjectDescription input,String projectName) {
		URI uri = input.getEObjectURI();
		if(uri !=null && uri.segmentCount() > 1){
			String projectNameSegment = uri.segment(1);
			if(projectName.equals(projectNameSegment)){
				return true;
			}
		}
		return false;
	}
}
