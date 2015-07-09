package com.odcgroup.edge.t24.generation.version;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.edge.t24.generation.IFieldMapper;

/**
 * This interface defines the minimum methods needed to generate the field for a version.<p>
 * 
 * Also see {@link AbstractVersionFieldMapper} for where to implement them.
 *
 * @author saleem.akbar
 *
 */
public interface IVersionFieldMapper extends IFieldMapper<IVersionFieldMapper>
{
    public boolean  displayOnSameLine();

    public int      getRow();
    public int      getCol();

    public boolean  hasAttribute(String p_attrName);
    public EList<String> getAttributes();
    public boolean  hasEnrichment();
    public boolean  displayDropDown();
    public boolean	displayPopupDropdown();

    public String   getT24Name();
    
    public boolean  getHotField();
    public boolean  getHotValidate();

    public boolean  isMV();
    public boolean  isSV();
    
    public boolean  getRightAdjust();
    
    public boolean  hasRecurrencePopupBehaviour();
    public boolean 	hasCalendarPopupBehaviour(); 
    
    public String   getSelectionEnquiry();
    public String   getEnquiryParameter();

    public boolean isEnrichmentOnly();   
    
    public boolean isExpandable();
    
    public boolean displayAsVerticalRadioButtons();
    public boolean displayAsComboBox();
    public boolean displayAsToggle();
    public boolean displayAsDropDownNoInput();
  	public int 	   getEnriLength();
  	public boolean isReKeyRequired();
}
