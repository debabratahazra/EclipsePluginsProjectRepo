package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryFieldsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.iris.IrisEnquiryMetaData;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.temenos.connect.enquiry.IRISResultGroupDataStoreConstants;

/**
 * <code>IrisResultGroupSpec</code> is a {@link #generateDataGroup(IrisEnquiryMetaData, FormContext) materializable} specification of the full structure of the odata-response-shaped,
 * multi-instance datagroup into which each &lt;entry&gt; is from an IRIS odata enquiry response is initially decanted (by the custom rule: {@link com.temenos.connect.odata.IRISRequest IRISRequest}).<p><p>
 *
 * The lifecycle of an <code>IrisResultGroupSpec</code> instance is as follows:<p>
 * 
 * <ul>
 *     <li>
 *         Initial {@link #IrisResultGroupSpec(String, IrisEnquiryMetaData) construction} for a specified IRIS result group name
 *     </li>
 *     <li>
 *         Incremental population (comprising one or more calls to {@link #add(IrisResultItemSpec)})
 *     </li>
 *     <li>
 *         Generation / linkage with one or more {@link DisplayResultGroupSpec}(s) ({@link #createDisplayResultGroupSpecs()})
 *     </li>
 *     <li>
 *         Materialization of the specified IRIS result group ({@link #generateDataGroup(FormContext)})
 *     </li>
 * </ul><p><p>
 * 
 * author: Simon Hayes
 */
public class IrisResultGroupSpec
{
    private static final Logger LOGGER = GenLogger.getLogger(IrisResultGroupSpec.class);

    private final String m_groupName;
	private final IrisEnquiryMetaData m_irisMetaData;
	
	private final Map<Field,IrisResultItemSpec> m_irisResultItemSpecByField = new HashMap<Field,IrisResultItemSpec>();
	private final Map<String,Field> m_refdEnquiryFieldByT24Name = new HashMap<String,Field>();
	
	private final ArrayList<VisibleIrisResultItemSpec> m_visibleIrisResultItemSpecs = new ArrayList<VisibleIrisResultItemSpec>();
	private final ArrayList<InternalIrisResultItemSpec> m_internalIrisResultItemSpecs = new ArrayList<InternalIrisResultItemSpec>();
	
	private final ClubbableT24ColumnSet m_clubbableT24ColumnNumberSet = new ClubbableT24ColumnSet();
	
	private final List<String> m_declOrderedBreakChangeValueBearingT24FieldNames = new LinkedList<String>();
	
	public IrisResultGroupSpec(String p_irisResultGroupName, IrisEnquiryMetaData p_irisMetaData)
	{
		m_groupName = AssertionUtils.requireNonNullAndNonEmpty(p_irisResultGroupName, "p_irisResultGroupName");
		m_irisMetaData = AssertionUtils.requireNonNull(p_irisMetaData, "p_irisMetaData");
	}
	
	public IrisResultItemSpec findExistingItemSpecFor(Field p_enquiryField)
	{
		AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
		return m_irisResultItemSpecByField.get(p_enquiryField);
	}
	
	public boolean add(VisibleIrisResultItemSpec p_visibleIrisResultItemSpec)
	{
		AssertionUtils.requireNonNull(p_visibleIrisResultItemSpec, "p_visibleIrisResultItemSpec");
		
		boolean wasItemSpecAdded = false;
		
		if (registerIrisResultItemSpec(p_visibleIrisResultItemSpec))
		{
    		m_visibleIrisResultItemSpecs.add(p_visibleIrisResultItemSpec);
    		m_clubbableT24ColumnNumberSet.updateFor(p_visibleIrisResultItemSpec);
    		wasItemSpecAdded = true;
		}
		
		return wasItemSpecAdded;
	}
	
	public boolean add(InternalIrisResultItemSpec p_internalIrisResultItemSpec)
	{
		AssertionUtils.requireNonNull(p_internalIrisResultItemSpec, "p_internalIrisResultItemSpec");

        boolean wasItemSpecAdded = false;

        if (registerIrisResultItemSpec(p_internalIrisResultItemSpec))
        {
            m_internalIrisResultItemSpecs.add(p_internalIrisResultItemSpec);
            wasItemSpecAdded = true;
        }
        
        return wasItemSpecAdded;
	}
	
	public void notifyBreakChangeValueBearingT24FieldName(String p_t24FieldName)
	{
	    AssertionUtils.requireNonNullAndNonEmpty(p_t24FieldName, "p_t24FieldName");
	    
	    if (! m_declOrderedBreakChangeValueBearingT24FieldNames.contains(p_t24FieldName))
	        m_declOrderedBreakChangeValueBearingT24FieldNames.add(p_t24FieldName);
	}
	
	public DisplayResultGroupSpecCollection createDisplayResultGroupSpecs()
	{
		final DisplayResultGroupSpecCollection displayResultGroupCollection = new DisplayResultGroupSpecCollection();
		
		final int numIrisResultItemSpecs = m_visibleIrisResultItemSpecs.size();
		
		for (int i = 0; i < numIrisResultItemSpecs; ++i)
		{
			final VisibleIrisResultItemSpec visibleIrisResultItemSpec = m_visibleIrisResultItemSpecs.get(i);
			
			final DisplayResultGroupSpec displayResultGroupSpec = displayResultGroupCollection.findOrCreateDisplayResultGroupSpecFor(visibleIrisResultItemSpec);
			final DisplayResultItemSpec displayResultItemSpec = displayResultGroupSpec.findOrCreateDisplayResultItemSpecFor(visibleIrisResultItemSpec);
			
			visibleIrisResultItemSpec.setDisplayResultItemSpec(displayResultItemSpec);
		}
		
		final Iterator<DisplayResultGroupSpec> displayResultGroupSpecIter = displayResultGroupCollection.getDisplayOrderedResultGroupSpecIterator();
		final TreeMap<ResultsTableType,Integer> lastUsedResultGroupNameInstanceSuffixByResultsTableType = new TreeMap<ResultsTableType,Integer>();
		
		while(displayResultGroupSpecIter.hasNext())
		{
			final DisplayResultGroupSpec displayResultGroupSpec = displayResultGroupSpecIter.next();
			
			if (displayResultGroupSpec.requiresInstanceNumberForNameSuffixing())
			{
				final ResultsTableType resultsTableType = displayResultGroupSpec.getTargetResultsTableType();
				final Integer lastUsedNameInstanceSuffixForResultsTableType = lastUsedResultGroupNameInstanceSuffixByResultsTableType.get(resultsTableType);
				final Integer instanceNumberForNameSuffixing = new Integer((lastUsedNameInstanceSuffixForResultsTableType == null) ? 1 :  lastUsedNameInstanceSuffixForResultsTableType.intValue() + 1);
			
				displayResultGroupSpec.setInstanceNumberForNameSuffixing(instanceNumberForNameSuffixing);
				lastUsedResultGroupNameInstanceSuffixByResultsTableType.put(resultsTableType, instanceNumberForNameSuffixing);
			}

			if (displayResultGroupSpec.requiresClubbableT24ColumnSet())
			{
				displayResultGroupSpec.notifyClubbableT24ColumnNumberSet(m_clubbableT24ColumnNumberSet);
			}

			displayResultGroupSpec.renameMultiplyReferencedDisplayItems();
		}
		
		return displayResultGroupCollection;
	}
	
	public IrisResultGroupWrapper generateDataGroup(FormContext p_formContext, EnquiryFieldsDigest p_enqFieldsDigest)
	{
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
        AssertionUtils.requireNonNull(p_enqFieldsDigest, "p_enqFieldsDigest");
		
		final PropertyGroupWrapper irisResultGroup = PropertyGroupWrapper.create(p_formContext, m_groupName);
		irisResultGroup.setFixedSize(Boolean.FALSE);

		final List<String> pendingDeclOrderedBreakChangeValueBearingT24FieldNames = new LinkedList<String>(m_declOrderedBreakChangeValueBearingT24FieldNames);
		final TreeMap<String,PropertyGroupWrapper> irisMvElementGroupByIrisMvGroupName = new TreeMap<String,PropertyGroupWrapper>();
		final TreeMap<String,PropertyGroupWrapper> irisSvElementGroupByIrisSvGroupName = new TreeMap<String,PropertyGroupWrapper>();
        final SortedSet<IrisResultBreakChangeItem> displayOrderedIrisBreakChangeItems = new TreeSet<IrisResultBreakChangeItem>();

		final int numVisibleIrisResultItemSpecs = m_visibleIrisResultItemSpecs.size();
		
		for (int i = 0; i < numVisibleIrisResultItemSpecs; ++i)
		{
			final VisibleIrisResultItemSpec visibleIrisResultItemSpec = m_visibleIrisResultItemSpecs.get(i);
			
			final String t24ResultFieldName = visibleIrisResultItemSpec.getT24FieldName();
			final String irisResultItemName = visibleIrisResultItemSpec.getDataItemName();
			
			final String irisMvGroupName = m_irisMetaData.getIrisMultiValueGroupName(irisResultItemName);
			final String irisSvGroupName = m_irisMetaData.getIrisSubValueGroupName(irisResultItemName);

			PropertyGroupWrapper parentDataGroupForItem = irisResultGroup; // tentative
			
			if (irisMvGroupName != null)
			{
				PropertyGroupWrapper irisMvElementGroup = irisMvElementGroupByIrisMvGroupName.get(irisMvGroupName);
				boolean isMvElementGroupNewlyCreated = false;
				
				if (irisMvElementGroup == null)
				{
					final PropertyGroupWrapper irisMvGroup = PropertyGroupWrapper.create(p_formContext, irisMvGroupName);
					irisResultGroup.addChild(irisMvGroup);
					
					irisMvElementGroup = PropertyGroupWrapper.create(p_formContext, IRISResultGroupDataStoreConstants.REPEATABLE_ELEMENT_GROUP_NAME);
					irisMvElementGroup.setFixedSize(Boolean.FALSE);
					irisMvGroup.addChild(irisMvElementGroup);
					isMvElementGroupNewlyCreated = true;
					
					irisMvElementGroupByIrisMvGroupName.put(irisMvGroupName, irisMvElementGroup);
				}
				
				parentDataGroupForItem = irisMvElementGroup; // tentative

				if (irisSvGroupName != null)
				{
					PropertyGroupWrapper irisSvElementGroup = isMvElementGroupNewlyCreated ? null : irisSvElementGroupByIrisSvGroupName.get(irisSvGroupName);
					
					if (irisSvElementGroup == null)
					{
						final PropertyGroupWrapper irisSvGroup = PropertyGroupWrapper.create(p_formContext, irisSvGroupName);
						irisMvElementGroup.addChild(irisSvGroup);
						
						irisSvElementGroup = PropertyGroupWrapper.create(p_formContext, IRISResultGroupDataStoreConstants.REPEATABLE_ELEMENT_GROUP_NAME);
						irisSvElementGroup.setFixedSize(Boolean.FALSE);
						irisSvGroup.addChild(irisSvElementGroup);
						
						irisSvElementGroupByIrisSvGroupName.put(irisSvGroupName, irisSvElementGroup);
					}
					
					parentDataGroupForItem = irisSvElementGroup;
				}
			}
			
			final PropertyWrapper visibleIrisResultDataItem = visibleIrisResultItemSpec.generateDataItem(p_formContext);
			
			parentDataGroupForItem.addChild(visibleIrisResultDataItem);
			
			if (pendingDeclOrderedBreakChangeValueBearingT24FieldNames.remove(t24ResultFieldName))
			    displayOrderedIrisBreakChangeItems.add(new VisibleIrisResultBreakChangeItem(visibleIrisResultItemSpec, visibleIrisResultDataItem));
			
		} // foreach IrisResultItemSpec in allResultItemSpecs list

		final List<IrisResultBreakChangeItem> irisBreakChangeItemList = new ArrayList<IrisResultBreakChangeItem>(displayOrderedIrisBreakChangeItems);
		
		if (! pendingDeclOrderedBreakChangeValueBearingT24FieldNames.isEmpty())
		{
		    final Iterator<String> internalT24FieldNameIter = pendingDeclOrderedBreakChangeValueBearingT24FieldNames.iterator();
		    
		    while(internalT24FieldNameIter.hasNext())
		    {
		        final String t24FieldName = internalT24FieldNameIter.next();
		        final Field internalBreakChangeValueField = p_enqFieldsDigest.getEnquiryFieldByT24Name(t24FieldName);

		        if (internalBreakChangeValueField != null)
		        {
		            final InternalIrisResultItemSpec internalBreakChangeItemSpec = new InternalIrisResultItemSpec(internalBreakChangeValueField);
		            final PropertyWrapper internalBreakChangeDataItem = internalBreakChangeItemSpec.generateDataItem(p_formContext);
		            
		            irisResultGroup.addChild(internalBreakChangeDataItem);
		            irisBreakChangeItemList.add(new InternalIrisResultBreakChangeItem(internalBreakChangeItemSpec, internalBreakChangeDataItem));
		        }
		    }
		}
		
		return new IrisResultGroupWrapper(irisResultGroup.unwrap(), irisBreakChangeItemList);
	}
	
	private boolean registerIrisResultItemSpec(IrisResultItemSpec p_irisResultItemSpec)
	{
		AssertionUtils.requireNonNull(p_irisResultItemSpec, "p_irisResultItemSpec");
		
		final Field enquiryField = p_irisResultItemSpec.getEnquiryField();
		final String t24FieldName = enquiryField.getName();
		final IrisResultItemSpec existingIrisResultItemSpec = m_irisResultItemSpecByField.get(enquiryField);
		
		if (existingIrisResultItemSpec != null)
		{
			final String existingSpecSimpleClassName = existingIrisResultItemSpec.getClass().getName();
			final String indefArticle = ("AEIOU".indexOf(existingSpecSimpleClassName.toUpperCase().charAt(0)) >= 0) ? "an" : "a";
			
			throw new IllegalStateException(
				"This " + IrisResultGroupSpec.class.getSimpleName() + " ALREADY has " + indefArticle + ' ' +
				existingSpecSimpleClassName + " for enquiry Field: " + t24FieldName + " !"
			);
		}
		
		boolean wasItemSpecRegistered = false;
		
		final Field alreadyRefdFieldWithSameT24Name = m_refdEnquiryFieldByT24Name.get(t24FieldName);
		
		if (alreadyRefdFieldWithSameT24Name == null)
		{
		    m_irisResultItemSpecByField.put(enquiryField, p_irisResultItemSpec);
		    m_refdEnquiryFieldByT24Name.put(t24FieldName, enquiryField);
		    
		    wasItemSpecRegistered = true;
		}
		
		else if (LOGGER.isWarnEnabled())
		{
		    LOGGER.warn("Duplicate field name: \"" + t24FieldName + "\" (please check enquiry definition):");
            LOGGER.warn("- retaining : " + toLogFriendlyFieldDefString(alreadyRefdFieldWithSameT24Name));
		    LOGGER.warn("- ignoring  : " + toLogFriendlyFieldDefString(enquiryField));
		}
		
		return wasItemSpecRegistered;
	}
	
	private static String toLogFriendlyFieldDefString(Field p_enquiryField)
	{
	    final String s = p_enquiryField.toString();
	    return "Field " + s.substring(s.indexOf('('));
	}
}
