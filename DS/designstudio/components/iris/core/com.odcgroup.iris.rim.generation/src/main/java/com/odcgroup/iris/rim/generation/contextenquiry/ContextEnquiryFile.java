package com.odcgroup.iris.rim.generation.contextenquiry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;

import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;

/**
 * This class generates the ContextEnquiry.rim file.
 * 
 * @author taubert
 * 
 */
public class ContextEnquiryFile {

	private static String FILE_URI = "platform:/resource/hothouse-models/rim/hothouse/ContextEnquiry.rim";

	public static ContextEnquiryFile INSTANCE = new ContextEnquiryFile();

	private ContextEnquiryFile() {
		/*
		 * Private constructor. Singleton.
		 */
	}

	/**
	 * Read the file and create a collection of ContextEnquiryLink based on
	 * these informations : GET ->
	 * "T24.ctxCustomerKycRelationship.CtxEnq_KycDetailsForKycRelationship" {
	 * title: "KYC Details" condition: OK(ApplicationJoinsToCustomer) }
	 * 
	 * @throws IOException
	 */
	private List<ContextEnquiryLink> load(InputStream is) throws IOException {
		ArrayList<ContextEnquiryLink> ret = new ArrayList<ContextEnquiryLink>();		
		BufferedReader bufR = null;
		
		try {
			bufR = new BufferedReader(new InputStreamReader(is));
			String line = null;
			ContextEnquiryLink currentLink = null;
			boolean inBlock = false;			
			
			while ((line = bufR.readLine()) != null) {
				line = line.trim();
				
				if (inBlock) {
					if (line.startsWith("title:")) {
						currentLink.setDescription(line.substring("title:".length()).trim());
					} else if (line.startsWith("condition:")) {
						int pos = line.indexOf("ApplicationJoinsTo");
						
						if (pos > 0) {
							String application = line.substring(pos + "ApplicationJoinsTo".length());
							
							if (application.endsWith(")")) {
								application = application.substring(0, application.length() - 1).trim();
							}
							
							application = application.toUpperCase();
							currentLink.setApplication(application);
						} else {
							if (line.indexOf("VersionMatch") > 0){
								currentLink.setIsVersion(true);
							}
						}
					}
					if (line.endsWith("}")) {
						inBlock = false;
					}
				} else if (line.startsWith("GET")) {
					inBlock = true;
					currentLink = new ContextEnquiryLink();
					ret.add(currentLink);
					int pos = line.indexOf("->");
					if (pos > 0) {
						String sLink = line.substring(pos + 2);
						if (sLink.endsWith("{")) {
							sLink = sLink.substring(0, sLink.length() - 1).trim();
						}
						currentLink.setFullyQualifiedLinkName(sLink);
					}
				}
			}
		} finally {
			if(bufR != null) {
				bufR.close();	
			}			
		}
		
		return ret;
	}


	
	public synchronized URI update(ModelLoader loader, List<ContextEnquiryLink> toUpdate) throws Exception{
		URI emfURI = URI.createURI(FILE_URI);
		Resource r = loader.getResource(emfURI);
		URIConverter uriConverter = r.getResourceSet().getURIConverter();
		List<ContextEnquiryLink> list = null;
		
		try{
			InputStream is = uriConverter.createInputStream(emfURI);
			list = load(is);
		}catch(Exception e){
			/*
			 * Certainly the file doesn't exist (first time)
			 */
			list = new ArrayList<ContextEnquiryLink>();
		}

		String sLastContextEnquiry = null;
		
		for (ContextEnquiryLink link : toUpdate){
			String sCurrentContextEnquiry = link.getFullyQualifiedLinkName();
			// eg : T24.ctxCurrency.CtxEnq_CurrencyRates
			int pos = sCurrentContextEnquiry.lastIndexOf(".");
			sCurrentContextEnquiry = sCurrentContextEnquiry.substring(0, pos+1); // keep the .
			// eg : T24.ctxCurrency.
			
			if (sLastContextEnquiry != null && sLastContextEnquiry.equals(sCurrentContextEnquiry)){
				/*
				 * Already removed, continue
				 */
				continue;
			}
			
			sLastContextEnquiry = sCurrentContextEnquiry;
			
			/*
			 * remove them all !
			 */
			Iterator<ContextEnquiryLink> iter = list.iterator();
			while (iter.hasNext()) {
			    if (iter.next().getFullyQualifiedLinkName().startsWith(sLastContextEnquiry)) {
			        iter.remove();
			    }
			}
		}
		
		/*
		 * now simply add all the link to update in the current list
		 */
		list.addAll(toUpdate);
		
		/*
		 * Now we have to check for duplicate based on resourceName and description.
		 * This is because of IRIS crashing if there are duplicates on these 2 fields ...
		 */
		Set<String> tmp = new HashSet<String>();
		for(ContextEnquiryLink link : list){
			String sKey = link.getResourceName()+"."+link.getDescription();
			int idx = 1;
			
			while (tmp.contains(sKey)){
				String sDesc = link.getDescription();
				int pos = sDesc.lastIndexOf("#");
				
				if (pos > 0){
					idx = Integer.parseInt(sDesc.substring(pos+1).trim());
					sDesc = sDesc.substring(pos-1);
				}
				
				idx++;
				link.setDescription(sDesc + " #" + idx);
				sKey = link.getResourceName()+"."+link.getDescription();
			}
			
			tmp.add(sKey);
			
		}
		
		OutputStream os = uriConverter.createOutputStream(emfURI);
		save(os, list);
		
		/*
		 * return the URI so we can generate java or more ....
		 */
		return emfURI;
	}
	

	private void save(OutputStream os, List<ContextEnquiryLink> list) throws IOException {
		
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)));

		try{
			/*
			 * Add the Header
			 */
			addHeader(writer, list);
			
			/*
			 * Then all GET -> and store the unique applications in the same run
			 */
			HashSet<String> applications = new HashSet<String>();
			for (ContextEnquiryLink link : list){
				if (!link.isVersion()){
					if (!applications.contains(link.getApplication())){
						applications.add(link.getApplication());
					}
				}
				
				link.writeToStream(writer);
				writer.println("");
			}
			
			writer.println("      }\n"); 

			/*
			 * Then create the conditions
			 * something like this :
			 * 
//			resource ApplicationJoinsToCustomer {
//				type: item
//				entity: Entity 
//				view: T24EntityMetadata {
//					properties [ JoinedTo="CUSTOMER" ]
//				}
//				path: "/ContextEnquiryCustomer/{entity}"
//			}
			*/
			
			/*
			 * First, get all the applications (unique) we could have done that 
			 */
			for (String sOneApp : applications){
				String sCCApp = EMUtils.camelCaseName(sOneApp);
				writer.println("      resource ApplicationJoinsTo" + sCCApp + " {");
				writer.println("         type: item");
				writer.println("         entity: Entity ");
				writer.println("         view: T24EntityMetadata {");
				writer.println("	        properties [ JoinedTo=\"" + sOneApp + "\" ]");
				writer.println("         }");
				writer.println("         path: \"/ContextEnquiry" + sCCApp + "/{entity}\"");
				writer.println("      }\n");
			}
			
			writer.println("   }");
			writer.println("}");			
		} finally {
			writer.flush();
			writer.close();
		}
	}

	
	private void addHeader(PrintWriter writer, List<ContextEnquiryLink> list) throws IOException{		
		writer.println("/*");
		writer.println(" * WARNING WARNING WARNING WARNING WARNING WARNING ");
		writer.println(" *  ");
		writer.println(" * THIS IS AN AUTO-GENERATED FILE. PLEASE DO NOT");
		writer.println(" * UPDATE IT MANUALY.");
		writer.println(" * ");
		writer.println(" */");		
		writer.println("domain T24 {");
		writer.println("");
		writer.println("   use common.HTTPEvents.*");
		writer.println("   use common.CoreCommands.*");
		writer.println("   use common.ODataCommands.*");
		writer.println("   use common.T24Events.*");
		writer.println("   use common.T24Commands.*");
		
		/*
		 * Add the usage of all versionContextEnquiries.
		 * Required as the is not possible to make a contition OK on a fullyQualifiedName
		 * eg, not possible to do 
		 // condition: OK(T24.ctxCurrency.ctxCurrency_VersionMatch)
		 */
		HashSet<String> alreadyDone = new HashSet<String>();
		for (ContextEnquiryLink link : list){
			if (link.isVersion()){
				if (!alreadyDone.contains(link.getRimName())){
					alreadyDone.add(link.getRimName());
					writer.println("   use " + link.getPackageName() + "." + link.getRimName() + "." + link.getRimName() + "_VersionMatch");
				}
			}
		}
		
		alreadyDone.clear();					

		writer.println("   use hothouse.Hothouse.*\n"); 
		writer.println("   rim ContextEnquiry {\n"); 
		writer.println("      command MatchCommand\n"); 
		writer.println("      basepath: \"/{companyid}\"\n"); 
		writer.println("      resource ContextEnquiryList {"); 
		writer.println("         type: item"); 
		writer.println("         entity: Entity "); 
		writer.println("         view: NoopGET"); 
		writer.println("         relations [ \"http://www.temenos.com/rels/contextenquiry\" ]"); 
		writer.println("         path: \"/ContextEnquiryList/{entity}\"\n"); 
	}
}
