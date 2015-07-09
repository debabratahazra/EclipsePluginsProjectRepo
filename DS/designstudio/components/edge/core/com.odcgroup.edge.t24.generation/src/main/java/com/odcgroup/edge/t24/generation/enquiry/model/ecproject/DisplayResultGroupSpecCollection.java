package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import java.util.Iterator;
import java.util.TreeMap;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;


/**
 * <code>DisplayResultGroupSpecCollection</code> represents a self-sorting, incrementally-populatable collection of {@link DisplayResultGroupSpec}(s).<p>
 * 
 * Iterators {@link #getDisplayOrderedResultGroupSpecIterator() obtained} from the collection return member <code>DisplayGroupResultSpec</code>(s) in "display order" - i.e. the same
 * "top-down" order in which the tables backed by the specified groups would appear at the Enquiry's "Search Results" screen.<p>
 * 
 * @author Simon Hayes
 */
public class DisplayResultGroupSpecCollection
{
	private final TreeMap<CompositeLookupKey,DisplayResultGroupSpec> m_displayResultGroupSpecByCompositeLookupKey = new TreeMap<CompositeLookupKey,DisplayResultGroupSpec>();
	
	private boolean m_includesHeaderResultsTables;
	
	public DisplayResultGroupSpec findOrCreateDisplayResultGroupSpecFor(VisibleIrisResultItemSpec p_visibleIrisResultItemSpec)
	{
		AssertionUtils.requireNonNull(p_visibleIrisResultItemSpec, "p_visibleIrisResultItemSpec");
		
		final CompositeLookupKey compositeLookupKey = new CompositeLookupKey(p_visibleIrisResultItemSpec);
		
		DisplayResultGroupSpec result = m_displayResultGroupSpecByCompositeLookupKey.get(compositeLookupKey);
		
		if (result == null)
			m_displayResultGroupSpecByCompositeLookupKey.put(compositeLookupKey, result = new DisplayResultGroupSpec(p_visibleIrisResultItemSpec.getTargetResultsTableType()));
		
		m_includesHeaderResultsTables |= compositeLookupKey.m_resultsTableTypePrimaryKey.isEnquiryHeaderResults();
		
		return result;
	}
	
	public boolean includesHeaderResultsTables()
	{
	    return m_includesHeaderResultsTables;
	}
	
	/**
	 * @return	an iterator that will return the {@link DisplayResultGroupSpec}(s) in "display order" - i.e. the order in which the results tables backed by those groups would appear
	 * 			on screen. 
	 */
	public Iterator<DisplayResultGroupSpec> getDisplayOrderedResultGroupSpecIterator()
	{
		return m_displayResultGroupSpecByCompositeLookupKey.values().iterator();
	}
	
	private static class CompositeLookupKey implements Comparable<CompositeLookupKey>
	{
		final ResultsTableType m_resultsTableTypePrimaryKey;
		final Integer m_t24LineNumberSecondaryKey;
		
		CompositeLookupKey(VisibleIrisResultItemSpec p_visibleIrisResultItemSpec)
		{
			AssertionUtils.requireNonNull(p_visibleIrisResultItemSpec, "p_visibleIrisResultItemSpec");
			
			m_resultsTableTypePrimaryKey = p_visibleIrisResultItemSpec.getTargetResultsTableType();
			m_t24LineNumberSecondaryKey = m_resultsTableTypePrimaryKey.requiresSeparateTablePerT24LineNumber() ? new Integer(p_visibleIrisResultItemSpec.getT24DisplayPosition().getT24LineNumber()) : null;
		}

		@Override
		public int compareTo(CompositeLookupKey other) {
			if (other == null)
				return 1;
			
			final int primaryKeyCompareResult = this.m_resultsTableTypePrimaryKey.compareTo(other.m_resultsTableTypePrimaryKey);
			
			if ((primaryKeyCompareResult != 0) || (m_t24LineNumberSecondaryKey == null))
			{
				/*
				 * Either we have DIFFERENT ResultTableType primary key values, or (as implied by our m_t24LineNumberSecondaryKey being null) our common ResultTableType is one for which
				 * requiresSeparateTablePerT24LineNumber() is false.
				 */
				return primaryKeyCompareResult;
			}
			
			// post-condition: this & other have the SAME ResultTableType primary key, and requiresSeparateTablePerT24LineNumber() -> true for that ResultTableType (meaning that we BOTH have
			// non-null m_t24LineNumberSecondaryKey)
			
			return m_t24LineNumberSecondaryKey.compareTo(other.m_t24LineNumberSecondaryKey);
		}
	}
}
