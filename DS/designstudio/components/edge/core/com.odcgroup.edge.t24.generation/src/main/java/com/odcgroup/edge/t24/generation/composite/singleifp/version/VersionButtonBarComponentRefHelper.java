package com.odcgroup.edge.t24.generation.composite.singleifp.version;

import gen.com.acquire.intelligentforms.entities.ComponentItemGroupWrapper;

import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.StringUtils;
import com.acquire.util.collection.SortedProperties;
import com.odcgroup.edge.t24.generation.util.FileSystemUtils;
import com.odcgroup.edge.t24.generation.util.ResourcesUtil;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class VersionButtonBarComponentRefHelper
{
	private static NVPair[] s_versionButtonBarComponentRefAttrValues;
	
	private interface PropertyValueMarkers
	{
		String SPACE = "@SPACE@";
		String IFP_FRIENDLY_VERSION_NAME = "@IFP_FRIENDLY_VERSION_NAME@";
	}

	public static ComponentItemGroupWrapper createVersionButtonBarComponentItemGroup(String p_ifpFriendlyVersionName, FormContext p_formContext) throws Exception
	{
		final ComponentItemGroupWrapper result = ComponentItemGroupWrapper.create(p_formContext);
		setAttrValues(result, p_ifpFriendlyVersionName);
		return result;
	}
	
	private static void setAttrValues(ComponentItemGroupWrapper p_versionButtonBarComponentItemGroup, String p_ifpFriendlyVersionName) throws Exception
	{
		ensurePropertiesLoaded();
		final int numAttrValues = s_versionButtonBarComponentRefAttrValues.length;
		
		for (int i = 0; i < numAttrValues; ++i)
		{
			final NVPair nvPair = s_versionButtonBarComponentRefAttrValues[i];
			p_versionButtonBarComponentItemGroup.setAttribute(nvPair.name, replaceIfpFriendlyVersionNameMarkers(nvPair.value, p_ifpFriendlyVersionName));
		}
	}
	
	private static void ensurePropertiesLoaded() throws Exception
	{
		if (s_versionButtonBarComponentRefAttrValues == null)
		{
			final URL propsFileURL = ResourcesUtil.getMandatoryPlugInResource("/properties/VersionButtonBarItemGroupComponentAttrs.properties");
			
			InputStream inStream = null;
			
			try
			{
				inStream = propsFileURL.openStream();
				final SortedProperties sortedProps = new SortedProperties();
				sortedProps.load(inStream);

				@SuppressWarnings("unchecked")
				final Iterator<Map.Entry<String,String>> entryIter = sortedProps.entrySet().iterator();
				s_versionButtonBarComponentRefAttrValues = new NVPair[sortedProps.size()];
				int i = 0;
				
				while(entryIter.hasNext())
				{
					final Map.Entry<String,String> entry = entryIter.next();
					s_versionButtonBarComponentRefAttrValues[i++] = new NVPair(entry.getKey(), replaceSpaceMarkers(entry.getValue()));
				}
			}
			
			finally
			{
				FileSystemUtils.tryClose(inStream);
			}
		}
	}
	
	private static String replaceIfpFriendlyVersionNameMarkers(String p_propValue, String p_ifpFriendlyVersionName)
	{
		return StringUtils.replaceAll(p_propValue, PropertyValueMarkers.IFP_FRIENDLY_VERSION_NAME, p_ifpFriendlyVersionName);
	}
	
	private static String replaceSpaceMarkers(String p_propValue)
	{
		return StringUtils.replaceAll(p_propValue, PropertyValueMarkers.SPACE, " ");
	}
	
	private static class NVPair
	{
		final String name;
		final String value;
		
		NVPair(String p_name, String p_value)
		{
			name = p_name;
			value = p_value;
		}
	}
}
