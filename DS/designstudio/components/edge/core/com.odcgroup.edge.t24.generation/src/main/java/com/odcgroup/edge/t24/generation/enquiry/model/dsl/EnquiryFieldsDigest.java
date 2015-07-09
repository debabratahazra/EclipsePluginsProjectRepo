package com.odcgroup.edge.t24.generation.enquiry.model.dsl;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;

import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryProject;
import com.odcgroup.edge.t24.generation.enquiry.EnquiryUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.constants.FieldDisplayType;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.translation.translationDsl.Translations;

/**
 * <code>EnquiryFieldsDigest</code> is a self-populating "digest" of information about the {@link Field}(s) in an {@link Enquiry}.<p>
 * 
 * <ul>
 *     <li>
 *         It is envisaged that this will eventually become the place where we discover (and store the results of discovering) <u>everything</u> that relies on a complete traversal of the
 *         Enquiry fields (so that we do that only once)
 *     </li>
 *     <li>
 *         It would also be reasonable for this class to be the home for any static query methods for divining logical information about Enquiry fields.
 *     </li>
 * </ul><p>
 * 
 * NB: This class was "forced" into existence in order to solve the immediate problem of "How do we discover the displayable Field (if any) ultimately referenced by a Field with a
 * BreakOnChange operation" (in order to get NOSTRO.POSITIONS working). It's therefore been implemented in a hurry, and is (as yet) in a fairly embryonic form.
 *
 * @author Simon Hayes
 */
public class EnquiryFieldsDigest
{
    private static final String DEFAULT_NO_RESULTS_FOUND_MESSAGE = "No records matched the selection criteria";
    
	@SuppressWarnings("serial")
	private static class EnquiryFieldSet extends HashSet<Field> { };
	
	@SuppressWarnings("serial")
    private static class SortedIntegerSet extends TreeSet<Integer> { };
	
	@SuppressWarnings("serial")
	private static class EnquiryFieldSetByT24EntityNameMap extends TreeMap<String,EnquiryFieldSet>
	{
		void register(String p_t24EntityName, Field p_enquiryField)
		{
			AssertionUtils.requireNonNullAndNonEmpty(p_t24EntityName, "p_t24EntityName");
			AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
			
			findOrCreateAndRegisterEnquiryFieldListFor(p_t24EntityName).add(p_enquiryField);
		}
		
		private EnquiryFieldSet findOrCreateAndRegisterEnquiryFieldListFor(String p_t24Name)
		{
			AssertionUtils.requireNonNullAndNonEmpty(p_t24Name, "p_t24Name");
			
			EnquiryFieldSet result = get(p_t24Name);
			
			if (result == null)
				put(p_t24Name, result = new EnquiryFieldSet());
			
			return result;
		}
	}

	private final Enquiry m_enquiry;
	
	private final TreeMap<String,Field> m_allEnqFieldsByT24Name = new TreeMap<String,Field>();
	
	private final boolean m_allVisibleFieldsHaveRelativePositions;
	
    private final boolean m_allVisibleFieldsSpecifyNoHeader;
    
    private final boolean m_noVisibleFieldsHaveLabels;
    
    private final boolean m_includesStartTreeEndTreeFields;

    private final String m_noResultsFoundMessage;
    
    private final EnquiryFieldSet m_customNoResultsFoundMessageFields;
    
	private final SortedIntegerSet m_collapsibleT24ColumnNumbers = new SortedIntegerSet();
	
	private final EnquiryFieldSetByT24EntityNameMap m_enqFieldListByRefdT24AppFieldName = new EnquiryFieldSetByT24EntityNameMap();
	
	private final EnquiryFieldSetByT24EntityNameMap m_enqFieldListByRefdT24EnqFieldName = new EnquiryFieldSetByT24EntityNameMap();

    private final BasicEnquiryProject m_enquiryProject;

	// PUBLIC_CONSTRUCTORS {
	
	public EnquiryFieldsDigest(BasicEnquiryProject p_enquiryProject, Enquiry p_enquiry)
	{
		m_enquiryProject = AssertionUtils.requireNonNull(p_enquiryProject, "p_enquiryProject");
        m_enquiry = AssertionUtils.requireNonNull(p_enquiry, "p_enquiry");
		
		final EList<Field> enquiryFieldList = p_enquiry.getFields();
		final int numEnqFields = (enquiryFieldList == null) ? 0 : enquiryFieldList.size();
        final boolean isZeroRecordsDisplay = Boolean.TRUE.equals(p_enquiry.getZeroRecordsDisplay()); 

        /*
         * zero-records-display / no-results-found message
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * 
         * Here's a are somewhat [bizarre|baroque|Byzantine] rules that (Sathish has told us) we need to apply in order to determine:
         * 
         * 1. Whether ANY sort of no-results-found message is to be displayed at our Search Results screen for this particular Enquiry, and (if so)...
         * 2. What the text of that message is supposed to be
         *  
         * The rules are these:
         * 
         *    IF zero-records-display: true is specified in the Enquiry DSL, whether we need to provide for a "no-results-found" message at the Search Results screen DEPENDS
         *    on the presence of a Field defining a *custom* no-results-found message [see extractCustomNoResultsMessage() for the 'pattern' used to 'recognise' such Field(s)]
         *  
         *    OTHERWISE [i.e. zero-records-display is either left unspecified or given as: false in the Enquiry DSL], then we DEFINITELY need to provide for a "no-results-found"
         *    message at the Search Results screen, and should take it's text to be either (i) that defined by a Field defining a *custom* nno-results-found message (if there is one), or
         *    a that of a "default" message (such as that defined by our DEFAULT_NO_RESULTS_FOUND_MESSAGE constant) otherwise.
         * 
         * 03/11/2014 Simon Hayes
         */
        
        // Our default position in the event that we DON'T find a field defining a *custom* no-results-found message...
        String noResultsFoundMessage = isZeroRecordsDisplay ? null : DEFAULT_NO_RESULTS_FOUND_MESSAGE;
        
		EnquiryFieldSet
		    breakOnChangeFields = null,
            customNoResultsFoundMessageFields = null;
		
		boolean allVisibleFieldsHaveRelativePositions = true;
		boolean allVisibleFieldsSpecifyNoHeader = true;
		boolean noVisibleFieldsHaveLabels = true;
		boolean includesStartTreeEndTreeFields = false;
		
		for (int i = 0; i < numEnqFields; ++i)
		{
			final Field enquiryField = enquiryFieldList.get(i);
			
			final String t24FieldName = getUsableT24FieldNameOrNull(enquiryField);

			if (t24FieldName == null)
				continue;
			
			m_allEnqFieldsByT24Name.put(t24FieldName, enquiryField);
			
			includesStartTreeEndTreeFields |= (isStartTreeField(enquiryField) || isEndTreeField(enquiryField));
			
			if (isCollapsibleResultColumnField(enquiryField))
				m_collapsibleT24ColumnNumbers.add(enquiryField.getPosition().getColumn());
			
			final Operation fieldOp = enquiryField.getOperation();
			
			if (fieldOp == null)
				continue;
			
			final String extractedCustomNoResultsMessageValue = extractCustomNoResultsMessage(enquiryField);
			
			if (extractedCustomNoResultsMessageValue != null)
			{
		        /*
		         * I guess it's *possible* to define > 1 custom 'no results' heading field, suggesting that maybe we ought to be laying them out in tables (as we do with both
		         * static & field-defined enquiry results headers). Having said that, though, the only plausible example I can see (from searching my locally *.enquiry) is NOSTRO.FWD.BAL,
		         * where we have both:
		         * 
		         * - NO.DETAILS
		         * - ZERONORECORDS
		         * 
		         * ...both having the same 'position' (column: 1) - suggesting that they're mutually exclusive (?) - the only difference between them otherwise (besides their constant text values)
		         * is that NO.DETAILS *doesn't* specify a display-section (whereas ZERONORECORDS specifies display-section: Header).
		         * 
		         * No time to investigate or deal with any of this now, however, so for the time being we'll continue doing what we used to prior to this refactoring, i.e. take the value from the
		         * 1st qualifying field only (quietly ignoring any others).
		         * 
		         * 03/11/2014 Simon Hayes
		         */
	            if (noResultsFoundMessage == null)
	                noResultsFoundMessage = extractedCustomNoResultsMessageValue;

	            /*
	             * Regardless of whether we were in a position to use it's value, we'll remember this field as being one that defined a custom no-results-found message to support
	             * our  isCustomNoResultsFoundMessageField(Field) query - the means by which BasicEnquiryProject can "know" that this is a field it should NOT attempt to process.
	             */
	            if (customNoResultsFoundMessageFields == null)
	                customNoResultsFoundMessageFields = new EnquiryFieldSet();
	            
	            customNoResultsFoundMessageFields.add(enquiryField);
	            
	            continue;
			}
                
            if (isVisibleEnquiryField(enquiryField))
            {
                if (! isRelative(enquiryField.getPosition()))
                    allVisibleFieldsHaveRelativePositions = false;
                
                if (! Boolean.TRUE.equals(enquiryField.getNoHeader()))
                    allVisibleFieldsSpecifyNoHeader = false;
                
                Translations fieldLabelTranslations = enquiryField.getLabel();
                
                if ((fieldLabelTranslations != null) && fieldLabelTranslations.eContents().size() > 0)
                    noVisibleFieldsHaveLabels = false;
            }
            
			if (fieldOp instanceof BreakOnChangeOperation)
			{
				if (breakOnChangeFields == null)
					breakOnChangeFields = new EnquiryFieldSet();
				
				breakOnChangeFields.add(enquiryField);
				continue;
			}
				
			final String refdT24AppFieldName = extractRefdT24AppFieldName(fieldOp);
			
			if (refdT24AppFieldName != null)
			{
				m_enqFieldListByRefdT24AppFieldName.register(refdT24AppFieldName, enquiryField);
				continue;
			}

			final String refdT24EnqFieldName = (refdT24AppFieldName == null) ? extractRefdT24EnqFieldName(fieldOp) : null;

			if (refdT24EnqFieldName != null)
			{
				m_enqFieldListByRefdT24EnqFieldName.register(refdT24EnqFieldName, enquiryField);
			}
		} // for each Field in enquiryFieldList
		
		m_allVisibleFieldsHaveRelativePositions = allVisibleFieldsHaveRelativePositions;
		m_allVisibleFieldsSpecifyNoHeader = allVisibleFieldsSpecifyNoHeader;
		m_noVisibleFieldsHaveLabels = noVisibleFieldsHaveLabels;
		m_includesStartTreeEndTreeFields = includesStartTreeEndTreeFields;
		m_noResultsFoundMessage = noResultsFoundMessage;
		m_customNoResultsFoundMessageFields = customNoResultsFoundMessageFields;
	}
	
	// } PUBLIC_CONSTRUCTORS
	
	// PUBLIC_STATIC_METHODS {
	
    public static boolean isCollapsibleResultColumnField(Field p_enquiryField)
    {
    	AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
    	return isVisibleEnquiryField(p_enquiryField) && Boolean.TRUE.equals(p_enquiryField.getHidden()) && (p_enquiryField.getDisplaySection() != DisplaySectionKind.HEADER);
    }
    
	public static boolean isBreakOnChangeField(Field p_enquiryField)
	{
		AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
		return (p_enquiryField.getOperation() instanceof BreakOnChangeOperation);
	}
	
    public static boolean isVisibleEnquiryField(Field p_enquiryField)
	{
        AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
        
		if (p_enquiryField.getDisplaySection() == DisplaySectionKind.NO_DISPLAY)
			return false;
		
		final FieldPosition fieldPosition = p_enquiryField.getPosition();
		
		if ((fieldPosition == null) || (fieldPosition.getColumn() == 0))
			return false;
		
		return true;
	}
    
    public static boolean isStartTreeField(Field p_enquiryField)
    {
        AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
        return FieldDisplayType.START_TREE.isReferencedBy(p_enquiryField);
    }
    
    public static boolean isEndTreeField(Field p_enquiryField)
    {
        AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
        return FieldDisplayType.END_TREE.isReferencedBy(p_enquiryField);
    }

    public static boolean isRelative(FieldPosition p_fieldPosition)
    {
        return ! ((p_fieldPosition == null) || (p_fieldPosition.getRelative() == null));
    }
    
    // } PUBLIC_STATIC_METHODS
    
    // PUBLIC_INSTANCE_METHODS {
    
    public Enquiry getEnquiry()
    {
        return m_enquiry;
    }
    
    public BasicEnquiryProject getEnquiryProject()
    {
        return m_enquiryProject;
    }
    
    public boolean allVisibleResultsFieldsHaveRelativePositions()
    {
        return m_allVisibleFieldsHaveRelativePositions;
    }
    
    public boolean allVisibleFieldsSpecifyNoHeader()
    {
        return m_allVisibleFieldsSpecifyNoHeader;
    }
    
    public boolean noVisibleFieldsHaveLabels()
    {
        return m_noVisibleFieldsHaveLabels;
    }
    
    public boolean includesStartTreeEndTreeFields()
    {
        return m_includesStartTreeEndTreeFields;
    }
    
    public boolean isCustomNoResultsFoundMessageField(Field p_enquiryField)
    {
        AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
        return (m_customNoResultsFoundMessageFields != null) && m_customNoResultsFoundMessageFields.contains(p_enquiryField);
    }
    
    public boolean isVisibleEnquiryFieldInCollapsibleColumn(Field p_enquiryField)
    {
        AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
        return isVisibleEnquiryField(p_enquiryField) && m_collapsibleT24ColumnNumbers.contains(p_enquiryField.getPosition().getColumn());
    }
    
    public Field getEnquiryFieldByT24Name(String p_t24EnqFieldName)
    {
        AssertionUtils.requireNonNull(p_t24EnqFieldName, "p_t24EnqFieldName");
        return m_allEnqFieldsByT24Name.get(p_t24EnqFieldName);
    }
    
    public String getApplicableNoResultsFoundMessageOrNull()
    {
        return m_noResultsFoundMessage;
    }
    
    // } PUBLIC_INSTANCE_METHODS
    
    // PRIVATE_STATIC_METHODS {
    
    private static String extractCustomNoResultsMessage(Field p_enquiryField)
    {
        if (! (FieldDisplayType.CUSTOM_NO_RESULTS_MESSAGE.isReferencedBy(p_enquiryField)))
            return null;

        final DisplaySectionKind displaySection = p_enquiryField.getDisplaySection();
        
        if (! ((displaySection == null) || (displaySection == DisplaySectionKind.UNSPECIFIED) || (displaySection == DisplaySectionKind.HEADER)))
            return null;
        
        final BreakCondition breakCondition = p_enquiryField.getBreakCondition();
        
        if (! ((breakCondition == null) || ((breakCondition.getBreak() == BreakKind.NONE) && (breakCondition.getField() == null))))
            return null;
        
        final Operation fieldOperation = p_enquiryField.getOperation();

        if (fieldOperation == null)
            return null;
        
        String result = null;

       /*
        * I'm guessing it's probably *possible* to define custom no results headings not just by ConstantOperation field value, but by direct/indirect reference to another field
        * (e.g. via a SelectionOperation).
        * 
        * However, since this is a refactoring (and in any case I don't have time to look into this now), for now we'll stick to just handling the ConstantOperation case (until
        * such time as we encounter a real Enquiry that uses something else). 
        * 
        * 03/11/2014 Simon Hayes (c) Bodge-it-and-run-boys 2014
        */ 

        if (fieldOperation instanceof ConstantOperation)
            result = ((ConstantOperation) fieldOperation).getValue();
        
        return EnquiryUtils.stripEnclosingQuotes(result);
    }
    
    private static String extractRefdT24AppFieldName(Operation p_fieldOp)
    {
        return (p_fieldOp instanceof ApplicationFieldNameOperation) ? toUsableT24FieldNameOrNull(((ApplicationFieldNameOperation) p_fieldOp).getField()) : null;
    }
    
    private static String extractRefdT24EnqFieldName(Operation fieldOp)
    {
        return (
            (fieldOp instanceof FieldExtractOperation) ? toUsableT24FieldNameOrNull(((FieldExtractOperation) fieldOp).getField()) :
            (fieldOp instanceof BreakOnChangeOperation) ? toUsableT24FieldNameOrNull(((BreakOnChangeOperation) fieldOp).getField()) : null
        );
    }
    
    private static String getUsableT24FieldNameOrNull(Field p_enquiryField)
    {
        return toUsableT24FieldNameOrNull(StringUtils.trimEmptyToNull(p_enquiryField.getName()));
    }
    
    private static String toUsableT24FieldNameOrNull(String p_t24FieldName)
    {
        return ((p_t24FieldName == null) || "null".equalsIgnoreCase(p_t24FieldName)) ? null : p_t24FieldName;
    }

    // } PRIVATE_STATIC_METHODS
    
}
