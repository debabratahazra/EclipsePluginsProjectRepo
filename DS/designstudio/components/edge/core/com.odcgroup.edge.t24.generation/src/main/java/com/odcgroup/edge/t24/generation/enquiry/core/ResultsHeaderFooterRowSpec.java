package com.odcgroup.edge.t24.generation.enquiry.core;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;

import java.util.Iterator;
import java.util.TreeMap;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * A <code>ResultsHeaderFooterRowSpec</code> is an immutable value object describing a results table row that needs to be added (either as the first row, or the final row) by edgeConnect rules to the 'data' rows
 * returned by IRIS. It is little more than a T24 column-number-ordered collection of {@link  ResultsHeaderFooterCellSpec}(s). 
 * 
 * @author Simon Hayes
 */
public class ResultsHeaderFooterRowSpec {
	private final TreeMap<Integer,ResultsHeaderFooterCellSpec> m_cellsByT24ColumnNumber = new TreeMap<Integer,ResultsHeaderFooterCellSpec>();
	
	public ResultsHeaderFooterCellSpec addCellSpecFor(Field p_field, String p_ecDataItemName)
	{
		AssertionUtils.requireNonNull(p_field, "p_field");
		AssertionUtils.requireNonNullAndNonEmpty(p_ecDataItemName, "p_ecDataItemName");
		
		return m_cellsByT24ColumnNumber.put(new Integer(p_field.getPosition().getColumn()), new ResultsHeaderFooterCellSpec(p_field, p_ecDataItemName));
	}
	
	public void addDataItemsTo(PropertyGroupWrapper p_searchResultGroup, FormContext p_formContext) {
		AssertionUtils.requireNonNull(p_searchResultGroup, "p_searchResultGroup");
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
		
		final Iterator<ResultsHeaderFooterCellSpec> cellSpecIter = iterator();
		
		while(cellSpecIter.hasNext())
			cellSpecIter.next().addDataItemTo(p_searchResultGroup, p_formContext);
	}
	
	public ResultsHeaderFooterCellSpec getCellSpecForT24ColumnNumber(int p_t24ColumnNumber)
	{
		return m_cellsByT24ColumnNumber.get(p_t24ColumnNumber);
	}
	
	public Iterator<ResultsHeaderFooterCellSpec> iterator()
	{
		return m_cellsByT24ColumnNumber.values().iterator();
	}
}
