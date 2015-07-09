package com.odcgroup.iris.rim.generation.streams.mappers;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch;
import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.Function;
import com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch;
import com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria;
import com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.contextenquiry.ContextEnquiryLink;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.State;

/**
 * TODO: Document me!
 * 
 * @author taubert
 */
public class CtxEnquiry2StreamMapper implements StreamMapper<ContextEnquiryLink> {

	CommandFactory commandFactory = null;
	Event getEvent = null;
	State missingEnquiry = null;
	String ccAppliesToName = null; 
	String ccAppliesToField = null;
	String contextEnquiryType = "versioncontextenquiry"; // default
	private final ILangSpecificProvider<XtextProxyUtil> _proxyUtilProvider;
	private final Logger logger = LoggerFactory.getLogger(CtxEnquiry2StreamMapper.class);
	
	public CtxEnquiry2StreamMapper(ILangSpecificProvider<XtextProxyUtil> proxyUtilProvider) {
		this._proxyUtilProvider = proxyUtilProvider;
	}
	
	@Override
	public ArrayList<ContextEnquiryLink> map(EObject eObject, RimWriter destination, String rimName) {
		ContextEnquiry ctxEnq = (ContextEnquiry) eObject;
		boolean bIsVersionContextEnquiry = false;
		/*
		 * First do a bit of naming
		 */
		String sAppliesTo = ""; // eg "CURRENCY"
		if (ctxEnq instanceof ApplicationContextEnquiry) {
			ApplicationContextEnquiry appCtxEnq = (ApplicationContextEnquiry) ctxEnq;
			if (appCtxEnq.getAppliesTo() == null) {
				sAppliesTo = "MISSING.APPLICATION"; // default
				logger.error("Failed to get the apppliesTo object from the ApplicationContextEnquiry", new Exception("AppliesTo unresolved"));
			} else {
				/*
				 * A bit nervous to cast without check.
				 */
				EObject appliesTo = null;
				try{
					appliesTo = (EObject)appCtxEnq.getAppliesTo();
				}catch(ClassCastException cce){
					/*
					 * very embarrassing.
					 */
					logger.error("ApplicationContextEnquiry is not an EObject !", cce);
				}
				if (appliesTo != null && appliesTo.eIsProxy()){
					sAppliesTo = _proxyUtilProvider.get(appCtxEnq.eResource().getURI()).getProxyCrossRefAsString(appCtxEnq, appliesTo);
				}else{
					sAppliesTo = appCtxEnq.getAppliesTo().getName();
				}
			}
			ccAppliesToField = appCtxEnq.getAppliesToField();
			contextEnquiryType = "appcontextenquiry";
		} else if (ctxEnq instanceof VersionContextEnquiry) {
			bIsVersionContextEnquiry= true;
			VersionContextEnquiry verCtxEnq = (VersionContextEnquiry) ctxEnq;
			if (verCtxEnq.getAppliesTo() == null) {
				sAppliesTo = "MISSING.VERSION"; // default
				logger.error("Failed to get the apppliesTo object from the VersionContextEnquiry", new Exception("AppliesTo unresolved"));
			} else {
				if (verCtxEnq.getAppliesTo().eIsProxy()){
					sAppliesTo = _proxyUtilProvider.get(verCtxEnq.eResource().getURI()).getProxyCrossRefAsString(verCtxEnq, verCtxEnq.getAppliesTo());
				}else{
					sAppliesTo = verCtxEnq.getAppliesTo().getT24Name();
				}
			}
			ccAppliesToField = verCtxEnq.getAppliesToField();
			contextEnquiryType = "versioncontextenquiry";
		}

		if (ccAppliesToField!= null && ccAppliesToField.length() == 0){
			ccAppliesToField = null;
		}
		if (ccAppliesToField != null){
			ccAppliesToField = EMUtils.camelCaseName(ccAppliesToField);
			/*
			 * Upper Case the first char
			 */
			ccAppliesToField = ccAppliesToField.substring(0, 1).toUpperCase() + ccAppliesToField.substring(1); // eg
																											// Currency
		}
		
		
		this.ccAppliesToName = EMUtils.camelCaseName(sAppliesTo); // eg
																	// currency
		/*
		 * Upper Case the first char
		 */
		ccAppliesToName = ccAppliesToName.substring(0, 1).toUpperCase() + ccAppliesToName.substring(1); // eg
																										// Currency

		destination.println("domain "+ T24ResourceModelsGenerator.getDomain(null) + " {");
		destination.println("");
		/*
		 * This list could well be dynamic as soon as we have the capability to
		 * introspect the resources.
		 */
		destination.println("use common.HTTPEvents.*");
		destination.println("");

		destination.println("rim " + rimName + " {"); // eg ctxCurrency
		destination.println("");
		destination.println("command NoopGET");
		destination.println("command MatchCommand");
		destination.println("");
		destination.println("basepath: \"/{companyid}\"");
		destination.println("");

		/*
		 * First a Dummy enquiry. This enquiry will be referenced every times
		 * the resource wont be found.
		 */

//		destination.println("resource MissingEnquiry {");
//		destination.println("type: item");
//		destination.println("entity: Errors");
//		destination.println("view: NoopGET");
//		destination.println("}");
//		destination.println("");

		/*
		 * First, create the resource returning the list of enquiries.
		 */
		ArrayList<EnrichedAppliedEnquiry> enrichedAppliedEnquiries = createEnquiryList(ctxEnq.getEnquiries(),destination, rimName);
		
		/*
		 * Finally, add all the enquiries states in list of resources.
		 * Also, prepare the list to return in the same run
		 */
		ArrayList<ContextEnquiryLink> toReturn = new ArrayList<ContextEnquiryLink>();
		String sPackageAndRim = T24ResourceModelsGenerator.getDomain(null) + "." + rimName + ".";
		for (EnrichedAppliedEnquiry enrichedAppliedEnquiry : enrichedAppliedEnquiries) {
			createOneEnquiry(enrichedAppliedEnquiry, destination);
			toReturn.add(new ContextEnquiryLink(sPackageAndRim + enrichedAppliedEnquiry.resourceName, getEnquiryTitle(enrichedAppliedEnquiry.appliedEnquiry), sAppliesTo, bIsVersionContextEnquiry ));

		}

		
		/*
		 * Last but not least, create a special resource which will return the match of an exact enquiry.
		 * Note that this applies only to versionContextenquiries.
		 * This is used for the generation of the generic ContextEnquiry.rim file. (see T24ResourceModelGenerator & COntextEnquiryFile)
		 */
		if (bIsVersionContextEnquiry) {
			destination.println("");
			destination.println("resource " + rimName + "_VersionMatch {");
			destination.println("type: item");
			destination.println("entity: Entity ");
			destination.println("view: MatchCommand {");
			destination.ident();
			destination.println("properties [ Expression=\"{entity}='" + RESOURCE_TYPE.version.toString() + this.ccAppliesToName + "'\" ]");
			destination.println("}");
			destination.println("path: \"/VersionContextEnquiry/{entity}\"");
			destination.println("}");
			destination.println("");
		}
		destination.println("}");
		destination.println("}");
		return toReturn;
	}

	
	ArrayList<EnrichedAppliedEnquiry> createEnquiryList(EList<AppliedEnquiry> appliedEnquiries, RimWriter destination, String rimName) {

		final ArrayList<EnrichedAppliedEnquiry> ret = new ArrayList<EnrichedAppliedEnquiry>();
		/*
		 * Create a generic ContextEnquiryList
		 */
		destination.println("resource " + ccAppliesToName + "_" + rimName + "ContextEnquiryList {");
		destination.println("type: item");
		destination.println("entity: Errors");
		destination.println("view: NoopGET");
		destination.ident();
		destination.println("relations [ \"http://www.temenos.com/rels/" + this.contextEnquiryType + "\" ] "); 
		destination.println("path: \"/" + ccAppliesToName + "_ContextEnquiryList\"");
		// GET -> CtxEnq_CustomerPositions {
		// }
		String sOriginalEnquiryName = null;
		for (AppliedEnquiry ae : appliedEnquiries) {
			String sResourceName = null;
			sOriginalEnquiryName = null;
			String sAppField = null;
		
			if (ae.getEnquiry() != null) {
				if (ae.getEnquiry().eIsProxy()){
					sOriginalEnquiryName = _proxyUtilProvider.get(ae.eResource().getURI()).getProxyCrossRefAsString(ae, ae.getEnquiry());
				}else{
					sOriginalEnquiryName = ae.getEnquiry().getName();
				}
				sResourceName = "CtxEnq_" + EMUtils.camelCaseName(sOriginalEnquiryName);
			}else {
				logger.error("Failed to get enquiry object from the AppliedEnquiry", new Exception("createEnquiryList failure"));
				continue;
			}
			
//			/*
//			 * Test Test Test 
//			 */
//			sEnquiryName = "POS.MVMT.TODAY";
//			sEnquiryName = "CtxEnq_" + T24ResourceModelsGenerator.camelCase(sEnquiryName);
//			/*
//			 * Test Test Test 
//			 */
			for (SelectionCriteria sc : ae.getSelectionFields()) {
				/*
				 * find the first AppField not null. This will define the
				 * name of the link
				 */
				if (sc.getAppField() != null && sc.getAppField().length() > 0) {
					sAppField = EMUtils.camelCaseName(sc.getAppField());
					
				}
				if (sAppField != null) {
					sResourceName += "For" + sAppField;
					break;
				}
			}
			
			ret.add(new EnrichedAppliedEnquiry(ae, sResourceName, sAppField, sOriginalEnquiryName));

			destination.println("GET -> " + sResourceName + " {");
			destination.println("title: \"" + getEnquiryTitle(ae) + "\"");
			destination.println("}");

		}
		destination.println("}");
		return ret;
	}

	void createOneEnquiry(EnrichedAppliedEnquiry eae, RimWriter destination) {

		// Example :
		// resource ctxEnqPosMvmtToday {
		// type: item
		// entity: Entity
		// view: NoopGET
		// relations [ "http://www.temenos.com/rels/contextenquiry" ]
		// GET -> T24.PosMvmtToday.enqPosMvmtTodays {
		// parameters [ Currency = "{id}" ]
		// title: "Currency movements today"
		// }
		// }

		destination.println("resource " + eae.getResourceName() + " {");
		destination.println("type: item");
		destination.println("entity: Entity");
		destination.println("view: NoopGET");
		if (eae.getAppField() != null){
			destination.print("relations [ \"http://www.temenos.com/rels/fieldcontextenquiry/" + eae.getAppField() + "\"");
		}else{
			destination.print("relations [ \"http://www.temenos.com/rels/" + contextEnquiryType + "/" + ccAppliesToName + "\"");
		}
		destination.ident();
		
		AutoLaunch autoLaunch = eae.getAppliedEnquiry().getAutoLaunch();
		if (autoLaunch != null ){
			if (autoLaunch instanceof FunctionAutoLaunch){
			EList<Function> functions = ((FunctionAutoLaunch)autoLaunch).getFunctions();
			destination.println(",");
			destination.print("\"http://www.temenos.com/rels/auto\"");

			for (Function oneFunction : functions){
				String strFunction = oneFunction.getFunction().toString().toLowerCase();
				destination.println(",");
				destination.print("\"http://www.temenos.com/rels/auto/" + strFunction + "\"");
			}
			}else if (autoLaunch instanceof FunctionAutoLaunch){
				/*
				 * TODO : Need to do something with the OnChangeAutoLaunch
				 */
				
			}else{
				/*
				 * Unknown Autolaunch ????
				 */
			}
		}
		destination.println(" ]");
		/*
		 * Now do we have auto launch functions ? If yes, just add some more
		 * relations (mails "context enquiries" April 15th 2014)
		 */
		// GET -> T24.PosMvmtToday.enqPosMvmtTodays {
		String enquiryResourceName = null;
		String enqName = eae.getOriginalEnquiryName();
		if (enqName != null){
			String sPackage =  T24ResourceModelsGenerator.getDomain(null) + "." + RESOURCE_TYPE.enquiry.toString() + EMUtils.camelCaseName(enqName);
			enquiryResourceName = sPackage + "." + ParameterParser.getResourceName(RESOURCE_TYPE.enquiry, enqName, null).getResourceName();
		}
		
		if (enquiryResourceName == null){
			enquiryResourceName = "MissingEnquiry";
		}

		//		/*
//		 * Test Test Test Test Test Test Test 
//		 */
//		String enqName = "POS.MVMT.TODAY";
//		String sPackage =  T24ResourceModelsGenerator.getDomain(null) + "." + RESOURCE_TYPE.enquiry.toString() + T24ResourceModelsGenerator.camelCase(enqName);
//		enquiryResourceName = sPackage + "." + T24ResourceModelsGenerator.getResourceName(RESOURCE_TYPE.enquiry, enqName, null);
//		/*
//		 * Test Test Test Test Test Test Test 
//		 */
		
		
		destination.println("GET -> \"" + enquiryResourceName + "\" {");

		// parameters [ Currency = "{id}" ]
		if (eae.getAppliedEnquiry().getSelectionFields().size() > 0) {
			destination.println("parameters [ ");
			boolean bFirst = true;
			for (SelectionCriteria sc : eae.getAppliedEnquiry().getSelectionFields()) {
				String selAppField = sc.getAppField();
				if (selAppField == null || selAppField.length() == 0){
					/*
					 * Default to appliesToField
					 */
					selAppField = ccAppliesToField;
				}else{
					selAppField = EMUtils.camelCaseName(selAppField);
				}
				if (selAppField == null){
					/*
					 * No possible selection criteria ?? so defaulted to {id}
					 */
					selAppField = "";
				}
				String ccParam = EMUtils.camelCaseName(sc.getName());
				if (!bFirst){
					destination.println(",");
				}
				destination.print("filter = \"" + ccParam + " eq '@" + selAppField + "'\"");
				bFirst = false;
			}
			destination.println("]");

		}
		destination.println("}");
		destination.println("}");
	}


	String getEnquiryTitle(AppliedEnquiry ae) {
		String sTitle = null;
		if (ae != null) {
			sTitle = ae.getDescriptions().get(0).getText();
		}
		if (sTitle == null){
			sTitle = "Unknown";
		}
		return sTitle;
	}

	class EnrichedAppliedEnquiry {

		/**
		 * 
		 */
		private final AppliedEnquiry appliedEnquiry;

		private final String resourceName;
		private final String appField;
		private final String OriginalEnquiryName;



		protected EnrichedAppliedEnquiry(AppliedEnquiry ae, String resourceName, String appField, String sOriginalEnquiryName) {
			this.appliedEnquiry = ae;
			this.resourceName = resourceName;
			this.appField = appField;
			this.OriginalEnquiryName = sOriginalEnquiryName;

		}

		/**
		 * @return the appliedEnquiry
		 */
		public AppliedEnquiry getAppliedEnquiry() {
			return appliedEnquiry;
		}

		/**
		 * @return the resourceName
		 */
		public String getResourceName() {
			return resourceName;
		}

		/**
		 * @return the appField
		 */
		public String getAppField() {
			return appField;
		}
		
		/**
		 * @return the originalEnquiryName
		 */
		public String getOriginalEnquiryName() {
			return OriginalEnquiryName;
		}
	}

}
