package com.odcgroup.t24.enquiry.properties.util;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;

/**
 *
 * @author phanikumark
 */
public enum DrilldownEnum {
	
	ApplicationType("Application", "application:", EnquiryFactory.eINSTANCE.createApplicationType()),
	ScreenType ("Screen", "screen:", EnquiryFactory.eINSTANCE.createScreenType()),
	EnquiryType ("Enquiry", "enquiry:", EnquiryFactory.eINSTANCE.createEnquiryType()),
	FromFieldType ("From Field", "from-field:", EnquiryFactory.eINSTANCE.createFromFieldType()),
	CompositeScreenType ("Composite Screen", "composite-screen:", EnquiryFactory.eINSTANCE.createCompositeScreenType()),
	TabbedScreenType ("Tabbed Screen", "tab:", EnquiryFactory.eINSTANCE.createTabbedScreenType()),
	ViewType ("View", "view:", EnquiryFactory.eINSTANCE.createViewType()),
	QuitSEEType ("Quit-SEE", "quit-SEE:", EnquiryFactory.eINSTANCE.createQuitSEEType()),
	BlankType ("Blank", "blank:", EnquiryFactory.eINSTANCE.createBlankType()),
	PWProcessType ("Pw Process", "pw-process:", EnquiryFactory.eINSTANCE.createPWProcessType()),
	DownloadType ("Download", "download:", EnquiryFactory.eINSTANCE.createDownloadType()),
	RunType ("Run", "run:", EnquiryFactory.eINSTANCE.createRunType()),
	UtilType ("Util", "util:", EnquiryFactory.eINSTANCE.createUtilType()),
	JavaScriptType ("Javascript", "javascript:", EnquiryFactory.eINSTANCE.createJavaScriptType()),
	ShouldBeChangedType("Should-be Changed", "should-be-changed:", EnquiryFactory.eINSTANCE.createShouldBeChangedType());
	
	private String name;
	private String value;
	private DrillDownType type;

	/**
	 * @param display
	 */
	DrilldownEnum(String name, String value, DrillDownType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public static List<String> getDrilldownNames(){
		List<String> drilldownList = new ArrayList<String>();
		for(DrilldownEnum drilldownEnum : values()){
			drilldownList.add(drilldownEnum.name);
		}
		return drilldownList;
	}
	
	public static DrilldownEnum getDrilldownEnum(String name){
		for(DrilldownEnum drilldownEnum : values()){
			if(name.equals(drilldownEnum.name)){
				return drilldownEnum;
			}
		}
		return null;
	}
	
	public static String getDrilldownValue(String name){
		for(DrilldownEnum drilldownEnum : values()){
			if(name.equals(drilldownEnum.name)){
				return drilldownEnum.value;
			}
		}
		return null;
	}
	
	public static String getDrilldownName(String value) {
		for(DrilldownEnum drilldownEnum : values()){
			if(value != null && value.equals(drilldownEnum.value)){
				return drilldownEnum.name;
			}
		}
		return null;
	}
	
	public static DrillDownType getDrillDownType(String name) {
		for(DrilldownEnum drilldownEnum : values()){
			if(name.equals(drilldownEnum.name)){
				return drilldownEnum.type;
			}
		}
		return null;
	}

}
