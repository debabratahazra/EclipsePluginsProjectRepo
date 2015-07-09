package com.odcgroup.edge.t24.generation.enquiry.core;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.composite.singleifp.GlobalContext;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl;

/**
 * <code>EnquirySelectionDigest</code> is a self-populating, immutable "value object" representing the results of "digesting" an Enquiry's selection (search criteria) definition in the context
 * of a supplied {@link EnquiryFieldsDigest}.<p>
 *
 * The purpose of this class is to centralize the knowledge of how to traverse/interpret the raw selection information for easy consumption by any enquiry processor.

 * @author Simon Hayes
 */
public class EnquirySelectionDigest
{
    private static final Logger LOGGER = GenLogger.getLogger(EnquirySelectionDigest.class);
    
    private final EnquiryFilterParamDef[] m_filterParamDefs;
    
	public EnquirySelectionDigest(GlobalContext p_globalContext, Enquiry p_enquiry, EnquiryFieldsDigest p_fieldsDigest)
	{
		AssertionUtils.requireNonNull(p_enquiry, "p_enquiry");
		AssertionUtils.requireNonNull(p_fieldsDigest, "p_fieldsDigest");
		
		final SelectionExpression selectionExpn = p_enquiry.getCustomSelection();
		final EList<EObject> searchParamDefs = (selectionExpn == null) ? null : selectionExpn.eContents();
		final int numSearchParamDefs = (searchParamDefs == null) ? 0 : searchParamDefs.size();
		
		EnquiryFilterParamDef[] filterParamDefsArray = EnquiryFilterParamDef.EMPTY_ARRAY;
		
		if (numSearchParamDefs > 0)
		{
			ArrayList<EnquiryFilterParamDef> filterParamDefList = new ArrayList<EnquiryFilterParamDef>(numSearchParamDefs);
			
			for (int i = 0; i < numSearchParamDefs; ++i)
			{
				final EObject searchParamDef = searchParamDefs.get(i);
				
				if (! (searchParamDef instanceof SelectionImpl)) {
					LOGGER.debug("- searchParamDefs[" + i + "]: " + searchParamDef + " (ignoring)");
					continue;
				}
				
				filterParamDefList.add(new EnquiryFilterParamDef(p_globalContext, (SelectionImpl) searchParamDef, p_fieldsDigest, LOGGER));
			}
			
			if (! filterParamDefList.isEmpty())
			{
				filterParamDefsArray = new EnquiryFilterParamDef[filterParamDefList.size()];
				filterParamDefList.toArray(filterParamDefsArray);
			}
		}
		
		m_filterParamDefs = filterParamDefsArray;
	}
	
	public EnquiryFilterParamDef[] getFilterParamDefs()
	{
		return m_filterParamDefs;
	}
}
