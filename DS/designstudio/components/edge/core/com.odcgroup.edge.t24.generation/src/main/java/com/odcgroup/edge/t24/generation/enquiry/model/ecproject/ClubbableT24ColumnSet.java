package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import java.util.Iterator;
import java.util.TreeMap;

import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class ClubbableT24ColumnSet
{
	private final TreeMap<Integer,VisibleIrisResultItemSpec> m_definingIrisResultItemSpecByT24ColumnNumber = new TreeMap<Integer,VisibleIrisResultItemSpec>();
	
	int size()
	{
		return m_definingIrisResultItemSpecByT24ColumnNumber.size();
	}
	
	void updateFor(VisibleIrisResultItemSpec p_visibleIrisResultItemSpec)
	{
		AssertionUtils.requireNonNull(p_visibleIrisResultItemSpec, "p_visibleIrisResultItemSpec");
		
		if (p_visibleIrisResultItemSpec.getTargetResultsTableType().isClubbableColumnSetMember())
		{
			final DisplayableT24FieldPosition irisFieldDisplayPosition = p_visibleIrisResultItemSpec.getT24DisplayPosition();
			final VisibleIrisResultItemSpec previouslyRegisteredIrisResultItemSpecForColumn = m_definingIrisResultItemSpecByT24ColumnNumber.get(irisFieldDisplayPosition.getT24ColumnNumber());
			
			if ((previouslyRegisteredIrisResultItemSpecForColumn == null) || (irisFieldDisplayPosition.getT24LineNumber() < previouslyRegisteredIrisResultItemSpecForColumn.getT24DisplayPosition().getT24LineNumber()))
				m_definingIrisResultItemSpecByT24ColumnNumber.put(irisFieldDisplayPosition.getT24ColumnNumber(), p_visibleIrisResultItemSpec);
		}
	}
	
	Iterator<Integer> getDisplayOrderingT24ColumnNumberIterator()
	{
		return m_definingIrisResultItemSpecByT24ColumnNumber.keySet().iterator();
	}
	
	IrisResultItemSpec getDefiningIrisResultItemSpec(int p_t24ColumnNumber)
	{
		return m_definingIrisResultItemSpecByT24ColumnNumber.get(p_t24ColumnNumber);
	}
}
