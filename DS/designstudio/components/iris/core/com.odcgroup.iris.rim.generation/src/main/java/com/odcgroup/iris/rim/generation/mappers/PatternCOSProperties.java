package com.odcgroup.iris.rim.generation.mappers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.common.Translation;
import com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS;
import com.odcgroup.edge.t24ui.cos.pattern.COSPanel;
import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;
import com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec;
import com.odcgroup.edge.t24ui.cos.pattern.InitialURL;
import com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption;
import com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * A <code>PatternCOSProperties</code> instance represents a collection of property name/value pairs derived from an ESON <code>PatternCOS</code> definition pertaining to a
 * single generated [pattern] COS resource in a RIM file generated from such an ESON.<p>
 * 
 * The {@link PatternCOSPropertiesWriter} class provides the mechanism for serializing <code>COSProperties</code> instances to properties files to the appropriate sub-folder of
 * hothouse-models-gen/src/generated/edge (so making them available for loading by the edgeConnect IFP developed to generically service any pattern-based composite screen).
 *
 * @author Simon Hayes
 */
public class PatternCOSProperties implements FileSystemPersistable {
	private static HashMap<PanelOverflowOption,String> s_cssOverflowValueByPanelOverflowOption = new HashMap<PanelOverflowOption,String>();

	static {
		s_cssOverflowValueByPanelOverflowOption.put(PanelOverflowOption.HIDE_OVERFLOW, "hidden");
		s_cssOverflowValueByPanelOverflowOption.put(PanelOverflowOption.SHOW_SCROLLBAR_ALWAYS, "scroll");
		s_cssOverflowValueByPanelOverflowOption.put(PanelOverflowOption.SHOW_SCROLLBAR_IF_NEEDED, "auto");
	}
	
	private final String m_filename;
	private final COSPatternContainer m_cosPatternContainer;
	private TreeMap<String,String> m_propValueByName;
	private int m_panelCount;
	
	private static String toCssOverflowValue(PanelOverflowOption p_option) {
		String result = (p_option == null) ? null : s_cssOverflowValueByPanelOverflowOption.get(p_option);
		
		if (result == null)
			result = "hidden";
		
		return result;
	}
	
	public PatternCOSProperties(String p_filename, COSPatternContainer p_cosPatternContainer) {
		m_filename = p_filename;
		m_cosPatternContainer = p_cosPatternContainer;
	}
	
    /**
     * Adds the set of ESON-defined properties for one of the direct child panels of the composite screen definition for which this <code>COSProperties</code> instance was
     * created.
     * 
     * @param p_panel
     * @param p_panelNumber
     */
    public void addPropertiesForPanel(COSPanel p_panel) {
        ++m_panelCount;
        
        final String basePanelPropName = "cos.panels." + m_panelCount;
        
        final String panelContentUrl = extractInitialContentUrl(p_panel);
        
        if (panelContentUrl != null)
            setProperty(basePanelPropName + ".url", panelContentUrl);
        
        final boolean isHostableContentAll = p_panel.isHostableContentAll();
        
        setProperty(basePanelPropName + ".hostableContentAll", isHostableContentAll);
        
        if (! isHostableContentAll) {
            setHostableContentProperties(basePanelPropName, "ManualCOS", p_panel.isHostableBespokeCOSContentAll(), p_panel.getHostableBespokeCOSContent());
            setHostableContentProperties(basePanelPropName, "PatternCOS", p_panel.isHostableCOSContentAll(), p_panel.getHostableCOSContent());
            setHostableContentProperties(basePanelPropName, "Enquiry", p_panel.isHostableEnquiryContentAll(), p_panel.getHostableEnquiryContent());
            setHostableContentProperties(basePanelPropName, "Version", p_panel.isHostableScreenContentAll(), p_panel.getHostableScreenContent());
        }
        
        final EList<Translation> panelTitleTranslations = p_panel.getTitle();
        final int numPanelTitleTranslations = (panelTitleTranslations == null) ? 0 : panelTitleTranslations.size();
        
        if (numPanelTitleTranslations > 0) {
            final String basePanelTitlePropName = basePanelPropName + ".title";
            
            for (int i = 0; i < numPanelTitleTranslations; ++i) {
                final Translation titleTranslation = panelTitleTranslations.get(i);
                final String basePanelTitleTranslationPropName = basePanelTitlePropName + '.' + (i + 1);
                
                setProperty(basePanelTitleTranslationPropName + ".language", titleTranslation.getLanguage().getName());
                setProperty(basePanelTitleTranslationPropName + ".value", titleTranslation.getValue());
            }
        }

        setProperty(basePanelPropName + ".css.background-color", p_panel.getBackgroundColor());
        setProperty(basePanelPropName + ".css.height", p_panel.getHeight());
        setProperty(basePanelPropName + ".css.overflow-x", toCssOverflowValue(p_panel.getHorizontalOverflowOption()));
        setProperty(basePanelPropName + ".css.overflow-y", toCssOverflowValue(p_panel.getVerticalOverflowOption()));
        setProperty(basePanelPropName + ".css.width", p_panel.getWidth());
        
        if (m_propValueByName != null)
            m_propValueByName.put(basePanelPropName + ".zzzzzzzzzz", null); // line-break marker
    }
    
	/**
	 * @return	the path-less name of the file to which this <code>COSProperties</code> instance should be persisted
	 */
	@Override
    public String getFilename() {
		return m_filename;
	}
	
    @Override
    public String getFileDescription() {
        return "COS properties";
    }

	@Override
    public CharSequence getFileContent() {
	    final EList<Translation> cosTitleTranslations = (m_cosPatternContainer instanceof AbstractCOS) ? ((AbstractCOS) m_cosPatternContainer).getTitle() : null; 
	    final int numCosTitleTranslations = (cosTitleTranslations == null) ? 0 : cosTitleTranslations.size();
	    
	    if (numCosTitleTranslations > 0) {
	        final String baseCosTitlePropName = "cos.title";
	        
	        for (int i = 0; i < numCosTitleTranslations; ++i) {
                final Translation titleTranslation = cosTitleTranslations.get(i);
                final String basePanelTitleTranslationPropName = baseCosTitlePropName + '.' + (i + 1);
                
                setProperty(basePanelTitleTranslationPropName + ".language", titleTranslation.getLanguage().getName());
                setProperty(basePanelTitleTranslationPropName + ".value", titleTranslation.getValue());
	        }
	    }
	    
		final COSPattern pattern = m_cosPatternContainer.getPattern();
		final EList<String> patternSpecificAttrNames = pattern.getPatternSpecificAttributeNames(); 
		
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		
		pw.println("cos.pattern=" + pattern.getName());
		pw.println("cos.pattern.panelSeparatorOption=" + m_cosPatternContainer.getPanelSeparatorOption());
		
		if (patternSpecificAttrNames.contains("accordionPatternMultiExpandable"))
		    pw.println("cos.pattern.accordions.isMultiExpanding=" + m_cosPatternContainer.isAccordionPatternMultiExpandable());
		
		pw.println("cos.numChildPanels=" + m_panelCount);
		pw.println();
		
		if (m_propValueByName != null) {
			for(Map.Entry<String,String> entry: m_propValueByName.entrySet()) {
				if (entry.getValue() == null) // line-break marker
				    pw.println();
				
				else
				    pw.println(entry.getKey() + "=" + entry.getValue());
			}
		}
		
		return sw.getBuffer();
	}
	
	protected String extractInitialContentUrl(COSPanel p_panel) {
	    String result = null;
	    
	    if (p_panel instanceof SingleComponentPanel) {
	        final SingleComponentPanel singleComponentPanel = (SingleComponentPanel) p_panel;
	        final InitialPanelContentSpec initialContentSpec = singleComponentPanel.getInitialContent();
	        
	        if (initialContentSpec instanceof InitialURL)
	            result = ((InitialURL) initialContentSpec).getUrl();
	    }
	    
	    return result;
	}
	
	protected void setHostableContentProperties(String basePanelPropName, String entityTypeName, boolean isAllowAllOfEntityType, EList<?> restrictedEntityList) {
		setProperty(basePanelPropName + ".hostable" + entityTypeName + "ContentAll", isAllowAllOfEntityType);
		
		if (! (isAllowAllOfEntityType || (restrictedEntityList == null) || restrictedEntityList.isEmpty())) {
			setProperty(basePanelPropName + ".hostable" + entityTypeName + "Content", toPipeSeparatedIRISEntityNameList(restrictedEntityList));
		}
	}
	
	protected String toPipeSeparatedIRISEntityNameList(EList<?> entityList) {
		StringBuilder resultBuff = new StringBuilder();
	
		for(Object entity: entityList) {
			String irisResourceName = null;

			if (entity instanceof CompositeScreen) {
				irisResourceName = ParameterParser.getResourceName(RESOURCE_TYPE.composite, ((CompositeScreen) entity).getName(), null).getResourceName();
			}
			
			else if (entity instanceof Enquiry) {
				irisResourceName = ParameterParser.getResourceName(RESOURCE_TYPE.enquiry, ((Enquiry) entity).getName(), null).getResourceName();
			}
			
			else if (entity instanceof Version) {
				irisResourceName = ParameterParser.getResourceName(RESOURCE_TYPE.version, ((Version) entity).getT24Name(), null).getResourceName();				
			}
			
			if (irisResourceName != null) {
				if (resultBuff.length() > 0)
					resultBuff.append('|');
				
				resultBuff.append(irisResourceName);
			}
		}
		
		return resultBuff.toString();
	}
	
	protected void setProperty(String name, boolean value) {
		setProperty(name, String.valueOf(value));
	}
	
	protected void setProperty(String name, String value) {
		if (value != null) {
			if (m_propValueByName == null)
				m_propValueByName = new TreeMap<String,String>();
			
			m_propValueByName.put(name, value);
		}
	}
}
