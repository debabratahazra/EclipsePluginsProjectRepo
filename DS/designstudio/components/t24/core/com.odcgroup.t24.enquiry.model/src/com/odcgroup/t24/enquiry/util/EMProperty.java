package com.odcgroup.t24.enquiry.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class holds information about an entity field
 */
public class EMProperty {
		
	private String name;
	private List<EMTerm> terms = new ArrayList<EMTerm>();
	private List<EMProperty> subProperties = new ArrayList<EMProperty>();
	private String t24Name = "";
	private String selectionInfo = "";
	private String joinedTo = "";
	private String propertyGroup = "";
	private boolean isAAPropertyClass = false;

	public EMProperty(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public List<EMTerm> getVocabularyTerms() {
		return terms;
	}

	public void addVocabularyTerm(EMTerm term) {
		// Only set AA Property Class flag if its not set already
		if (!isAAPropertyClass && EMTermType.TERM_T24_AA_PROPERTY.equals(term.getType()))
			isAAPropertyClass = true;
		terms.add(term);
	}

	public List<EMProperty> getSubProperties() {
		return subProperties;
	}

	public void addSubProperty(EMProperty property) {
		subProperties.add(property);
	}
	
	public String getT24Name() {
		return t24Name;
	}

	public void setT24Name(String t24Name) {
		this.t24Name = t24Name != null ? t24Name : "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSelectionInfo() {
		return selectionInfo;
	}

	public void setSelectionInfo(String selectionInfo) {
		this.selectionInfo = selectionInfo;
	}

	public String getJoinedTo() {
		return joinedTo;
	}

	public void setJoinedto(String joinedTo) {
		this.joinedTo = joinedTo;
	}

	/**
	 * @return the propertyGroup
	 */
	public String getPropertyGroup() {
		return propertyGroup;
	}

	/**
	 * @param propertyGroup the propertyGroup to set
	 */
	public void setPropertyGroup(String propertyGroup) {
		this.propertyGroup = propertyGroup != null ? propertyGroup : "";
	}
	
	/**
	 * Returns true if the property contains the term, regardless of the value of the term.
	 * @param termType
	 * @return
	 */
	public boolean hasVocabularyTerm(EMTermType termType) {
		for (EMTerm term : terms) {
			if (term.getType().equals(termType)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("{").append(name);
		if (!t24Name.isEmpty()) {
			out.append(", ").append(t24Name);
		}
		if (!selectionInfo.isEmpty()) {
			out.append(", ").append(selectionInfo);
		}
		if (!joinedTo.isEmpty()) {
			out.append(", ").append(joinedTo);
		}
		if (!propertyGroup.isEmpty()) {
			out.append(", ").append(propertyGroup);
		}
		for (EMTerm term : terms) {
			out.append(", ");
			out.append(term.getName());
			out.append("=");
			out.append(term.getValue());
		}
		out.append("}");
		return out.toString();
	}

	/**
	 * @return the isAAPropertyClass
	 */
	public boolean isAAPropertyClass() {
		return isAAPropertyClass;
	}
	
	/**
	 * 
	 * @param emProperty
	 * @return true if emProperty is equal
	 */
	public boolean equals(EMProperty emProperty) {		
		if(!emProperty.name.equals(name) ||
				!emProperty.t24Name.equals(t24Name) ||
				!emProperty.terms.equals(terms) || 
				!emProperty.propertyGroup.equals(propertyGroup) ||
				!emProperty.joinedTo.equals(joinedTo) ||
				!emProperty.selectionInfo.equals(selectionInfo) ||
				emProperty.subProperties.size() != subProperties.size() ||
				emProperty.isAAPropertyClass != isAAPropertyClass) {
			return false;
		}
		
		Iterator<EMProperty> itersubProperties= subProperties.iterator();
		Iterator<EMProperty> iterEMProperty= emProperty.subProperties.iterator();
		while(itersubProperties.hasNext() && iterEMProperty.hasNext()) {
			if(!itersubProperties.next().equals(iterEMProperty.next())) {
				return false;
			}
		}
		return true;
	}
}
