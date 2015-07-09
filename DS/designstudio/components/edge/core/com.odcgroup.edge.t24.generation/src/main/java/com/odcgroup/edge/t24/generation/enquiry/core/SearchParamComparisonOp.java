package com.odcgroup.edge.t24.generation.enquiry.core;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.acquire.util.AssertionUtils;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;
import com.temenos.connect.enquiry.EdgeODataComparisonOpDef;

/**
 * Each constant of the <code>SearchParamComparisonOp</code> enumeration maps a T24 <code>SelectionOperator</code> to the equivalent {@link EdgeODataComparisonOpDef} <i>(if any)</i>, or
 * <code>null</code> if there that T24 comparison operator is (as yet) unsupported in the edgeConnect projects generated from Enquiry(ies) within Design Studio.  
 *
 * @author Simon Hayes
 */
public enum SearchParamComparisonOp
{
	EQUALS(SelectionOperator.EQUALS, EdgeODataComparisonOpDef.EQUALS),
	MATCHES(SelectionOperator.MATCHES, null),
	NOT_MATCHES(SelectionOperator.NOT_MATCHES, null),
	NOT_EQUALS(SelectionOperator.NOT_EQUALS, EdgeODataComparisonOpDef.NOT_EQUALS),
	GREATER_THAN(SelectionOperator.GREATER, EdgeODataComparisonOpDef.GREATER_THAN),
	GREATER_THAN_OR_EQUAL_TO(SelectionOperator.GREATER_OR_EQUALS, EdgeODataComparisonOpDef.GREATER_THAN_OR_EQUALS),
	LESS_THAN(SelectionOperator.LESS_THAN, EdgeODataComparisonOpDef.LESS_THAN),
	LESS_THAN_OR_EQUAL_TO(SelectionOperator.LESS_OR_EQUALS, EdgeODataComparisonOpDef.LESS_THAN_OR_EQUALS),
	BETWEEN(SelectionOperator.BETWEEN, EdgeODataComparisonOpDef.BETWEEN),
	NOT_BETWEEN(SelectionOperator.NOT_BETWEEN, EdgeODataComparisonOpDef.NOT_BETWEEN),
	CONTAINS(SelectionOperator.CONTAINS, EdgeODataComparisonOpDef.CONTAINS),
	NOT_CONTAINS(SelectionOperator.DOES_NOT_CONTAIN, EdgeODataComparisonOpDef.NOT_CONTAINS),
	BEGINS_WITH(SelectionOperator.BEGINS_WITH, EdgeODataComparisonOpDef.BEGINS_WITH),
	ENDS_WITH(SelectionOperator.ENDS_WITH, EdgeODataComparisonOpDef.ENDS_WITH),
	NOT_BEGINS_WITH(SelectionOperator.DOES_NOT_BEGIN_WITH, EdgeODataComparisonOpDef.NOT_BEGINS_WITH),
	NOT_ENDS_WITH(SelectionOperator.DOES_NOT_END_WITH, EdgeODataComparisonOpDef.NOT_ENDS_WITH),
	SOUNDS_LIKE(SelectionOperator.SOUNDS_LIKE, null);
	
	private static final SearchParamComparisonOp[] EMPTY_ARRAY = new SearchParamComparisonOp[0];
	private static final SearchParamComparisonOp[] VALUES = values();
	private static final int NUM_VALUES = VALUES.length;
	
	private final SelectionOperator m_t24SelectionOperator;
	private final EdgeODataComparisonOpDef m_edgeODataComparisonOpDef;

	public static SearchParamComparisonOp[] translateToUniqueInstanceList(EList<SelectionOperator> p_selectionOperatorList, Logger p_logger)
	{
		if ((p_selectionOperatorList == null) || p_selectionOperatorList.isEmpty())
			return EMPTY_ARRAY;
		
		final int numSelectionOperators = p_selectionOperatorList.size();
		final ArrayList<SearchParamComparisonOp> resultList = new ArrayList<SearchParamComparisonOp>(numSelectionOperators);
		
		for (int i = 0; i < numSelectionOperators; ++i)
		{
			final SelectionOperator selectionOperator = p_selectionOperatorList.get(i);
			
			if (selectionOperator != null)
			{
				int resultListSizeBefore = resultList.size();
				
				for (int j = 0; j < NUM_VALUES; ++j)
				{
					final SearchParamComparisonOp instance = VALUES[j];
					
					if ((instance.m_t24SelectionOperator == selectionOperator) && ! resultList.contains(instance))
					{
						resultList.add(instance);
						break;
					}
				}
				
				if (resultList.size() == resultListSizeBefore)
					p_logger.warn("No " + SearchParamComparisonOp.class.getName() + " instance found for " + SelectionOperator.class.getName() + ": " + selectionOperator);
			}
		}
		
		final SearchParamComparisonOp[] result = new SearchParamComparisonOp[resultList.size()];
		return resultList.toArray(result);
	}
	
	public SelectionOperator getT24SelectionOperator()
	{
		return m_t24SelectionOperator;
	}
	
	public EdgeODataComparisonOpDef getEdgeODataComparisonOpDef()
	{
		return m_edgeODataComparisonOpDef;
	}
	
	private SearchParamComparisonOp(SelectionOperator p_t24SelectionOperator, EdgeODataComparisonOpDef p_edgeODataComparisonOpDef)
	{
		m_t24SelectionOperator = AssertionUtils.requireNonNull(p_t24SelectionOperator, "p_t24SelectionOperator");
		m_edgeODataComparisonOpDef = p_edgeODataComparisonOpDef;
	}
}
