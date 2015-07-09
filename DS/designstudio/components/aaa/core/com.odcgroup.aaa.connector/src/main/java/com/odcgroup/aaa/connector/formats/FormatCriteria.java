package com.odcgroup.aaa.connector.formats;

import java.util.HashSet;
import java.util.Set;

import com.odcgroup.aaa.connector.domainmodel.TypeEntity;


/**
 * Criteria to search/find Formats. Can be 
 * <ul><li>format code pattern and optionally eventual entity type</li>
 * <li>a set of format codes</li></ul>
 *
 * @author Michael Vorburger (MVO)
 */
public class FormatCriteria {

    /** 
     * Code of Format/s, possibly with pattern where '*' is wildcard. 
     * Just '*' is allowed and means all formats.
     */
    public String formatCodePattern;
    
    /**
     * Type of Format.
     */
    public TypeEntity type;
    
    /**
     * Selected codes
     */
    private Set<String> selectedCodes = new HashSet<String>();

    /**
     * @param formatCodePattern
     * @param type
     */
    public FormatCriteria(String formatCodePattern, TypeEntity type) {
        super();
        this.formatCodePattern = formatCodePattern;
        this.type = type;
    }

    /**
     * @param formatCodePattern
     * @param type
     */
    public FormatCriteria(Set<String> selectedCodes) {
    	this.selectedCodes.addAll(selectedCodes);
    }

    /**
     * @param formatCodePattern
     */
    public FormatCriteria(String formatCodePattern) {
        this(formatCodePattern, null);
    }

    /**
     * @return the formatCodePattern
     */
    public String getFormatCodePattern() {
        return formatCodePattern;
    }
    
    /**
     * @param formatCodePattern the formatCodePattern to set
     */
    public FormatCriteria setFormatCodePattern(String formatCodePattern) {
        this.formatCodePattern = formatCodePattern;
        return this;
    }

    /**
     * @return the type
     */
    public TypeEntity getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public FormatCriteria setType(TypeEntity type) {
        this.type = type;
        return this;
    }

	/**
	 * @return the selectedCodes
	 */
	public Set<String> getSelectedCodes() {
		return selectedCodes;
	}
}
