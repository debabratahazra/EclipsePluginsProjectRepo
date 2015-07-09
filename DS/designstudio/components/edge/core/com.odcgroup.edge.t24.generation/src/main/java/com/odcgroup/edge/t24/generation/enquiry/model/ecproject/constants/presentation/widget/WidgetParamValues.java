package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.acquire.intelligentforms.entities.presentation.PresentationObject;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
class WidgetParamValues<TargetPresObjectType extends PresentationObjectWrapper<?>> implements Appliable<TargetPresObjectType>
{
	private static final String EC_WIDGET_ATTR_NAME_PREFIX = PresentationObject.ATTR_DISPLAY_TYPE;
	
	private SortedMap<String,String> m_paramValueByName;

	public boolean isEmpty()
	{
		return (m_paramValueByName == null) || m_paramValueByName.isEmpty();
	}
	
	@Override
	public void applyTo(TargetPresObjectType p_targetPresObject)
	{
		AssertionUtils.requireNonNull(p_targetPresObject, "p_targetPresObject");
		
		if (! isEmpty())
		{
			Iterator<Map.Entry<String,String>> nameValuePairIter = m_paramValueByName.entrySet().iterator();
			
			while(nameValuePairIter.hasNext())
			{
				final Map.Entry<String,String> nameValuePair = nameValuePairIter.next();
				p_targetPresObject.setAttribute(EC_WIDGET_ATTR_NAME_PREFIX + nameValuePair.getKey(), nameValuePair.getValue());
			}
		}
	}

	void setWidgetParamValue(String p_paramName, String p_paramValue)
	{
		AssertionUtils.requireNonNullAndNonEmpty(p_paramName, "p_paramName");
		
		if (p_paramValue != null)
		{
			if (m_paramValueByName == null)
				m_paramValueByName = new TreeMap<String,String>();
			
			m_paramValueByName.put(p_paramName, p_paramValue);
		}
		
		else if (! isEmpty())
		{
			m_paramValueByName.remove(p_paramName);
		}
	}
}
