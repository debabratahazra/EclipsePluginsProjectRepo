package com.odcgroup.t24.helptextimport.mapper

import com.odcgroup.mdf.ecore.MdfAssociationImpl
import com.odcgroup.mdf.ecore.MdfAttributeImpl
import com.odcgroup.mdf.ecore.MdfClassImpl
import com.odcgroup.mdf.ecore.MdfPropertyImpl
import com.odcgroup.mdf.metamodel.MdfDomain
import com.odcgroup.t24.applicationimport.mapper.MappingException
import com.odcgroup.t24.helptext.schema.T24Help
import java.util.ArrayList
import java.util.List
import org.apache.xerces.dom.ElementNSImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Node

class T24HelpText2MdfMapper {
	 
	private static Logger LOGGER = LoggerFactory::getLogger(typeof(T24HelpText2MdfMapper)) 

	def map(T24Help application, MdfClassImpl mdfClass) throws MappingException {
		var mulSubClasList = getMultiSubValueGroups(mdfClass)
		if(application.menu!=null){
			for(t : application.menu.t) {
				if(t.field != null) {
		      	 	var attrType = getClassAttribute(t.field.toValidName,mulSubClasList)
		      	 	if(attrType != null && t.desc!=null) {
		      	 		setFieldCommon(attrType, t.desc.content) 
		      	 	}
	      	 	} else {
		      	 	val msg = application + " does not have a field to save to domain";
					throwMappingError(msg)
					LOGGER.error(msg);
	      	 	}
	    	}
		}
	}
	
	def getMultiSubValueGroups(MdfClassImpl mdfClass) {
		var mdfDomain = mdfClass.eContainer as MdfDomain
 		var subClasList = new ArrayList<MdfClassImpl>()
 		subClasList.add(mdfClass)
 		for(Object multiClass : mdfDomain.classes) {
 			var multiValclass = multiClass as MdfClassImpl
 			if(multiValclass.name.startsWith(mdfClass.name+"__")) {
 				subClasList.add(multiValclass)
 			}
 		}
 		return subClasList
	}
	
	def getClassAttribute(String fieldNames,List<MdfClassImpl> mdfClasses) {
 		for(MdfClassImpl genMultiSubClass : mdfClasses){
 			var attrType= genMultiSubClass.properties.findFirst(MdfPropertyImpl e|e.name.equals(fieldNames) && (e instanceof MdfAttributeImpl || (e  instanceof MdfAssociationImpl && (e as MdfAssociationImpl).containment == 1))) as MdfPropertyImpl
 			if(attrType != null) {
 				return attrType
 			}
 		}
	}
	
	def setFieldCommon(MdfPropertyImpl property, List<?> fieldDesc) {
		var StringBuffer p = new StringBuffer()
		for(Object descText: fieldDesc){
			if(descText!=null) {
				if(descText instanceof ElementNSImpl){
					var node = (descText as ElementNSImpl)
					if(node.nodeName.equals("table")){
						var Node tr = node.getFirstChild()
						while(tr!=null){
							var Node td = tr.getFirstChild()
						    while(td != null){
								p.append(td.getTextContent().trim()).append("#");
								td = td.nextSibling
							}
							if(tr.nextSibling!=null){
								p.append("\n")	
							}
							tr = tr.nextSibling;
						}
					} else {
						p.append(node.getTextContent().trim()).append("\n");
					}
					
				} else if(descText instanceof String){
					p.append(descText.toString.trim)	
				}
			}
		}
		var description = p.toString
			if(!description.contains("Help Text for this field is unavailable.  Please refer to the T24 User Guides for further information."))
		 		property.documentation = description
		
	}
	
	def toValidName(String name) {
		var cleanName = name.trim
		cleanName = cleanName.replace('.' , '_')
		
		// Here is a toast to some really weired stuff
		cleanName = cleanName.replace("'AND'", "AND"); // DE_WORDS's field
		cleanName = cleanName.replace("\"", ""); 	   // "FIELD/" TEXT \"" !!
		cleanName = cleanName.replace("@", ""); 	   // @ID
		
		// The following appear in T24 Valid Values
		// we replace them for our Enum Names
		// (the Enum Values stay as in T24)
		cleanName = cleanName.replace(' ', '_');
		cleanName = cleanName.replace('-', '_');
		cleanName = cleanName.replace(':', '_');
		cleanName = cleanName.replace('/', '_');
		cleanName = cleanName.replace('>', '_GT');
		cleanName = cleanName.replace('<', '_LT');
		cleanName = cleanName.replace(",",  "COMMA");  // or better "_COMMA_"  ?
		cleanName = cleanName.replace("*",  "STAR");   // or better "_STAR_"   ?
		cleanName = cleanName.replace("=",  "EQUALS"); // or better "_EQUALS_" ?
		cleanName = cleanName.replace("~",  "TILDE");  // or better "_TILDE_"  ?
		cleanName = cleanName.replace("%",  "PERCENT");
		cleanName = cleanName.replace("$",  "_DOLLAR_");
		cleanName = cleanName.replace("&",  "_AND_");
		cleanName = cleanName.replace("#",  "NO");
		cleanName = cleanName.replace('\'', '_');      // "ENTRY_ID'S" 
		
		// These don't occur in the Test XML, just to be safe for the future:
		cleanName = cleanName.replace(':', '_');
		cleanName = cleanName.replace(';', '_');
		cleanName = cleanName.replace('!', '_');
		cleanName = cleanName.replace('+', '_');
		cleanName = cleanName.replace('§', '_');
		cleanName = cleanName.replace('£', '_');
		cleanName = cleanName.replace('¦', '_');
		cleanName = cleanName.replace('|', '_');
		cleanName = cleanName.replace('\\', '_');

		cleanName = cleanName.replace('(', '_');
		cleanName = cleanName.replace(')', '_');
		cleanName = cleanName.replace('[', '_');
		cleanName = cleanName.replace(']', '_');
		cleanName = cleanName.replace('{', '_');
		cleanName = cleanName.replace('}', '_');
		 	
		if (!cleanName.empty && Character::isDigit(cleanName.charAt(0)))
			cleanName = "n" + cleanName
			
		cleanName
	}
	
	def throwMappingError(String message) throws MappingException {
	 	throw new MappingException(message);
	}

 }