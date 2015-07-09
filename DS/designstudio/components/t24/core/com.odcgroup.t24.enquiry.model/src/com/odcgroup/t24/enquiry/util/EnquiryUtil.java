package com.odcgroup.t24.enquiry.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.domain.repository.IDomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.t24.application.repository.IT24DomainRepository;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.repository.IEnquiryRepository;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

/**
 * Utility class for Enquiry
 * 
 * @author satishnangi, mumesh, mvorburger
 */
public class EnquiryUtil {
	
	private static final String DOLLAR_SUFFIX = "$";
	
	public static final String RELATIVE_LEFT = "Left";
	
	public static final String RELATIVE_RIGHT = "Right";
	
	public static String getEnquiryApplicationName(String fileName) {
		 MdfName mdfName = getMdfName(fileName);
		 if(mdfName !=null){
		   return mdfName.getLocalName();
		 }
		 return null;
	}
	
	public static String getEnquiryDomianName(String fileName) {
		 MdfName mdfName = getMdfName(fileName);
		 if(mdfName !=null){
		   return mdfName.getDomain();
		 }
		 return null;
	}
	
	public static MdfClass getEnquiryApplication(String fileName, Enquiry model) {
		MdfClass mdfclass = null;
		Resource resource = model.eResource();
		if(fileName!=null && fileName.contains(DOLLAR_SUFFIX)) {
			fileName = fileName.substring(0,fileName.lastIndexOf(DOLLAR_SUFFIX));
		}
		MdfName name = getMdfName(fileName);
		if (resource != null) {			
			mdfclass =  DomainRepositoryUtil.getMdfClass(name, resource);
		}
		return mdfclass;
	}
	
	public static MdfName getMdfName(String fileName) {
		 MdfName mdfName = null;
		   if(fileName !=null && fileName.length() !=0) {
			    fileName = fileName.replace(".", "_");
			    URI uri =URI.createURI(fileName);
			    if(uri.segmentCount() != 0){
			    	 mdfName = MdfNameFactory.createMdfName(uri.segment(0));
			    }
			    else{
			    	mdfName = MdfNameFactory.createMdfName(fileName);
			    }
			   
		   }
		 return mdfName;  
	}
	
	public static void setDrillDownName(DrillDown drillDown, Enquiry model) {
		if (drillDown != null && model != null) {
			int drillDownCount = model.getDrillDowns().size();
			drillDown.setDrill_name(String.valueOf(drillDownCount + 1));
		}
	}

	/**
	 * This method will arrange the fields which are header,footer and invisible type towards left and
	 * rest of the fields with proper column value in ascending order based on column value.
	 * 
	 * @param list
	 */
	public static void sortEnquiryFields(List<Field> list) {
		List<Field> header = new ArrayList<Field>();
		List<Field> footer = new ArrayList<Field>();
		List<Field> invisible = new ArrayList<Field>();
		List<Field> sortedFields = new ArrayList<Field>();

		for (Field field : list) {
			if (isHeaderField(field)) {
				header.add(field);
			} 
			else if(isFooterField(field)){
				footer.add(field);
			}
			else if(isInvalidField(field)){
				invisible.add(field);
			}
			else {
				sortedFields.add(field);
			}
		}
		Collections.sort(sortedFields, new EnquiryFieldComparator());

		// updated the list with new order
		list.clear();
		list.addAll(header);
		list.addAll(sortedFields);
		list.addAll(footer);
		list.addAll(invisible);

	}

	// update Drill Down attribute with name (DS-7557)
	public static void fixUpDrillDownNamesInXML(Document xmlDocument) {
		final String drillDownTag = "drillDowns";
		final String drillAttribute = "drill_name";
		NodeList nodesListDrilldown = xmlDocument.getElementsByTagName(drillDownTag);
		for (int j = 0; j < nodesListDrilldown.getLength(); j++) {
			Node nodes = nodesListDrilldown.item(j);
			Attr attr = xmlDocument.createAttribute(drillAttribute);
			attr.setNodeValue("" + (j + 1));
			nodes.getAttributes().setNamedItem(attr);
		}
	}
	
	public static IT24DomainRepository getDomainRepository() {
		return (IT24DomainRepository)LanguageRepositoryProvider.getLanguageRepository(IDomainRepository.DOMAIN_LANGUAGE_NAME);
	}
	
	public static IEnquiryRepository getEnquiryRepository() {
		return (IEnquiryRepository)LanguageRepositoryProvider.getLanguageRepository(IEnquiryRepository.ENQUIRY_LANGUAGE_NAME);
	}
	
	public static List<Field> fetchDisplayFieldList(Enquiry enquiry) {
		List<Field> fields = enquiry.getFields();
		List<Field> displayFields = new ArrayList<Field>();
		for (Field field : fields) {
			if (isDisplayField(field)) {
				displayFields.add(field);
			}
		}
		Collections.sort(displayFields, new EnquiryFieldComparator());
		return displayFields;
	}

	/**
	 * Method to check whether field is invisible.
	 * 
	 * @param field 
	 * @return
	 */
	public static boolean isFieldInvisible(Field field) {
		if(isHeaderFooterField(field)){
			return false;
		}
		return field.getPosition() == null || field.getPosition().getColumn() == null;
	}
	
	/**
	 * method to check if the field is a display field
	 * @param field
	 * @return
	 */
	public static boolean isDisplayField(Field field) {
		if (!isFieldInvisible(field) && !isHeaderField(field) && !isFooterField(field)) {
			return true;
		}
		return false;
	}


	/**
	 * Method to check whether field is Header of Footer.
	 * 
	 * @return
	 */
	private static boolean isHeaderFooterField(Field field) {
		return isHeaderField(field) || isFooterField(field);
	}

	/**
	 * @param field
	 * @return
	 */
	public static boolean isFooterField(Field field) {
		return field.getDisplaySection().equals(DisplaySectionKind.FOOTER);
	}

	/**
	 * @param field
	 * @return
	 */
	public static boolean isHeaderField(Field field) {
		return field.getDisplaySection().equals(DisplaySectionKind.HEADER);
	}

	public static boolean isInvalidField(Field field) {
		if (isFieldInvisible(field) || isHeaderFooterField(field)) {
			return true;
		}
		return false;
	}

	public static boolean isFieldRelative(Field field) {
		FieldPosition position = field.getPosition();
		Integer line = position.getLine();
		if (line != null && line > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isRelativeFieldParent(Field field, List<Field> fields) {
		FieldPosition position = field.getPosition();
		Integer line = position.getLine();
		int col = position.getColumn();
		if (line == null && 
				hasRelativeFields(col, fields, field)) {
			return true;
		}
		return false;
	}
	
	private static boolean hasRelativeFields(int column, List<Field> fields, Field parent) {		
		for (Field field : fields) {
			if (!parent.equals(field)) {
				FieldPosition pos = field.getPosition();
				if (pos != null && column == pos.getColumn()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getRelativeInfo(Field model) {
		int referenceIndex = 0;
		int modelIndex = 0;
		Enquiry enquiry = (Enquiry) model.eContainer();
		if (enquiry != null) {
			List<Field> fields = enquiry.getFields();
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				if (!isInvalidField(field)) {
					if (field.getPosition().getColumn() == model.getPosition().getColumn()) {
						if (field.getPosition().getLine() == null || field.getPosition().getLine() == 0) {
							referenceIndex = i;
						} else if (field.getName().equals(model.getName())
								&& (field.getPosition().getLine() == model.getPosition().getLine())) {
							modelIndex = i;
						}
					}

				}
			}
		}
		if (referenceIndex > modelIndex) {
			return RELATIVE_RIGHT;
		} else {
			return RELATIVE_LEFT;
		}
	}
	
	
	public static List<String> getEBReportsEnquiryAttributes(Resource context) {
		return getEBReportsEnumValues(context, "ENQUIRY__ATTRIBUTES__ATTRIBUTES");
	}

	/**
	 * @param context
	 * @param attributes
	 */
	@SuppressWarnings("unchecked")
	private static List<String> getEBReportsEnumValues(Resource context, String mdfType) {
		List<String> attributes = new ArrayList<String>();
		MdfDomain domain = getEBReportsDomain(context);
		if (context != null && domain != null) {
			MdfEnumeration attri = domain.getEnumeration(mdfType);
			List<MdfEnumValue> attributes1 = attri.getValues();
			for (MdfEnumValue value : attributes1) {
				attributes.add(value.getValue());
			}
			Collections.sort(attributes);
		}
		return attributes;
	}
	
	

	/**
	 * @param context
	 * @return MdfDomain
	 */
	private static MdfDomain getEBReportsDomain(Resource context) {
		QualifiedName qualifiedName = QualifiedName.create("EB_Reports");
		MdfDomain domain = DomainRepositoryUtil.getMdfDomain(context, qualifiedName);
		return domain;
	}
	
	public static List<String> getEBReportsEnquiryDisplayTypes(Resource context) {
		return getEBReportsEnumValues(context, "ENQUIRY__FIELD_NAME__FIELD_DISP_TYPE");
	}
}
