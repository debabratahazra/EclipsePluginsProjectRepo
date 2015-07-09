package com.odcgroup.edge.t24.generation.enquiry.layout;

import gen.com.acquire.intelligentforms.entities.presentation.PresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationItemGroupWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.acquire.util.AssertionUtils;

/**
 * <code>EnquiryHeaderLayoutHelper</code> is a simple (and somewhat crude !) layout manager to help determine a suitable set of presentation sections & columns and headings to represent
 * the result screen headings for a T24-defined Enquiry.<p>
 * 
 * The usage pattern is:<p>
 * 
 * <ul>
 *     <li>
 *         Call {@link #addHeaderElement(HeaderElement) add} to for each {@link HeaderElement} to be added to the layout <i>(order of submission has no impact on end results)</i>
 *     </li>
 *     <li>
 *         Create a suitable {@link EnquiryHeaderLayoutIterationListener} implementation and pass that to the {@link #iterateHeaderItems(EnquiryHeaderLayoutIterationListener, PresentationItemGroupWrapper)
 *         iterateHeaderItems} method.
 *     </li>
 * </ul> 
 *
 * @author Simon Hayes
 */
public class EnquiryHeaderLayoutHelper
<
	PresentationItemGroupType extends PresentationItemGroupWrapper,
	PresSectionType extends PresentationFormatBreakWrapper,
	PresColumnType extends PresentationColumnBreakWrapper
>
{
	private final TreeMap<Integer,HeaderLayoutColumn<PresentationItemGroupType,PresSectionType,PresColumnType>> m_layoutColumnByT24ColumnNumber = new TreeMap<Integer,HeaderLayoutColumn<PresentationItemGroupType,PresSectionType,PresColumnType>>();
	private final TreeMap<Integer,T24ColumnNumberSet> m_columnNumberSetByT24LineNumber = new TreeMap<Integer,T24ColumnNumberSet>();
	
	private int m_t24LineNumberMin = Integer.MAX_VALUE;
	private int m_t24LineNumberMax = Integer.MIN_VALUE;
	
	public void addHeaderElement(HeaderElement p_headerElement)
	{
		AssertionUtils.requireNonNull(p_headerElement, "p_headerElement");

		final int t24ColumnNumber = p_headerElement.getT24ColumnNumber();
		final int t24LineNumber = p_headerElement.getT24LineNumber();
		
		findOrCreateLayoutColumn(t24ColumnNumber).addHeaderElement(p_headerElement);
		findOrCreateColumnNumberSet(t24LineNumber).addColumnNumber(t24ColumnNumber);
		
		m_t24LineNumberMin = Math.min(t24LineNumber, m_t24LineNumberMin);
		m_t24LineNumberMax = Math.max(t24LineNumber, m_t24LineNumberMax);
	}
	
	public void iterateHeaderItems(EnquiryHeaderLayoutIterationListener<PresentationItemGroupType,PresSectionType,PresColumnType> p_listener, PresentationItemGroupType p_presPhase)
	{
		final List<ColumnSetSection> columnSetSections = buildColumnSetSectionList();
		
		if (columnSetSections == null)
			return;
		
		for (final ColumnSetSection sectionDef: columnSetSections)
		{
			final PresSectionType presSection = p_listener.addPresSection(p_presPhase);
			
			final Iterator<Integer> columnNumberIter = sectionDef.getColumnNumberIterator();
			final T24LineNumberRange t24LineNumberRange = sectionDef.getT24LineNumberRange();
			
			while(columnNumberIter.hasNext())
			{
				final Integer t24ColumnNumber = columnNumberIter.next();
				final PresColumnType presColumn = p_listener.addPresColumn(presSection);
				final HeaderLayoutColumn<PresentationItemGroupType,PresSectionType,PresColumnType> columnDef = m_layoutColumnByT24ColumnNumber.get(t24ColumnNumber);
				
				columnDef.iterate(presColumn, t24LineNumberRange, p_listener);
			}
		}
	}
	
	private List<ColumnSetSection> buildColumnSetSectionList()
	{
		if (m_columnNumberSetByT24LineNumber.isEmpty())
			return null;
		
		final Iterator<Map.Entry<Integer,T24ColumnNumberSet>> entryIter = m_columnNumberSetByT24LineNumber.entrySet().iterator();
		final List<ColumnSetSection> resultList = new ArrayList<ColumnSetSection>();
				
		T24ColumnNumberSet columnNumberSetForNextSection = null;
		int firstLineNumberForNextSection = 0;
		int finalLineNumberForNextSection = 0;
		T24ColumnNumberSet previousColumnNumberSet = null;
		
		while(entryIter.hasNext())
		{
			final Map.Entry<Integer,T24ColumnNumberSet> entry = entryIter.next();
			final int currentLineNumber = entry.getKey().intValue(); 
			
			T24ColumnNumberSet currentColumnNumberSet = entry.getValue();

			if (columnNumberSetForNextSection == null)
			{
				columnNumberSetForNextSection = currentColumnNumberSet;
				firstLineNumberForNextSection = finalLineNumberForNextSection = currentLineNumber;
			}
			
			else if (currentColumnNumberSet.isSubsetOf(previousColumnNumberSet))
			{
				// Carry previousColumnNumberSet forward as the current one for our next section...
				currentColumnNumberSet = previousColumnNumberSet;
				finalLineNumberForNextSection = currentLineNumber;				
			}
			
			else if (currentColumnNumberSet.isSuperSetOf(previousColumnNumberSet))
			{
				// Adopt currentColumnNumberSet as the one for our next section...
				columnNumberSetForNextSection = currentColumnNumberSet;
				finalLineNumberForNextSection = currentLineNumber;
			}
			
			else
			{
				final T24LineNumberRange lineNumberRange = new T24LineNumberRange(firstLineNumberForNextSection, finalLineNumberForNextSection);
				resultList.add(new ColumnSetSection(lineNumberRange, columnNumberSetForNextSection));
				columnNumberSetForNextSection = currentColumnNumberSet;
				firstLineNumberForNextSection = finalLineNumberForNextSection = currentLineNumber;
			}			

			previousColumnNumberSet = currentColumnNumberSet;
		}
		
		// NB: The loop adds the to the list for the *previous* entry on encountering a *current* entry having a ColumnNumberSet that is incompatible with the one we're waiting to add.
		//     Hence there will always be one remaining entry to be added.
		
		final T24LineNumberRange lineNumberRange = new T24LineNumberRange(firstLineNumberForNextSection, finalLineNumberForNextSection);
		resultList.add(new ColumnSetSection(lineNumberRange, columnNumberSetForNextSection));

		return resultList;
	}
	
	private HeaderLayoutColumn<PresentationItemGroupType,PresSectionType,PresColumnType> findOrCreateLayoutColumn(int p_t24ColumnNumber)
	{
		final int lookupKey = new Integer(p_t24ColumnNumber);
		
		HeaderLayoutColumn<PresentationItemGroupType,PresSectionType,PresColumnType> result = m_layoutColumnByT24ColumnNumber.get(lookupKey);
		
		if (result == null)
			m_layoutColumnByT24ColumnNumber.put(lookupKey, result = new HeaderLayoutColumn<PresentationItemGroupType,PresSectionType,PresColumnType>());
		
		return result;
	}
	
	private T24ColumnNumberSet findOrCreateColumnNumberSet(int p_t24LineNumber)
	{
		final int lookupKey = new Integer(p_t24LineNumber);
		
		T24ColumnNumberSet result = m_columnNumberSetByT24LineNumber.get(lookupKey);
		
		if (result == null)
			m_columnNumberSetByT24LineNumber.put(lookupKey, result = new T24ColumnNumberSet());
		
		return result;
	}
}
