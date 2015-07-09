package com.odcgroup.edge.t24.generation.composite;

import java.util.HashMap;

import org.slf4j.Logger;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24ui.Application;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.Panel;
import com.odcgroup.edge.t24ui.cos.bespoke.Section;
import com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * A <code>ESONProcessorState</code> assumes responsibility for keeping track of the overall set of {@link Application}(s), {@link Enquiry}(ies), and {@link Version}(s) referenced by the various {@link Panel}(s)
 * across <u>all</u> {@link Section}(s) during the processing of a single ESON {@link CompositeScreen} definition and detecting/reporting the situation where either:<p>
 * 
 * <ul>
 *     <li>
 *         the entity referenced by a <code>Panel</code> comes through as <code>null</code> (which can happen if the entity hasn't been imported into Design Studio or if the index of imported entities is
 *         out-of-date (solved by clean building the hothouse-models project), or...<br><br>
 *     </li>
 *     <li>
 *         a <code>Panel</code> refers to an entity that is also referred to by another <code>Panel</code> within the same ESON.<br><br>
 *     </li>
 * </ul>
 *
 * @author Simon Hayes
 */
public class ESONProcessorState {
    private static final Logger LOGGER = GenLogger.getLogger(ESONProcessorState.class);

    private static Class<?> s_panelIFClasses[] = new Class<?>[] {ApplicationPanel.class, EnquiryPanel.class, VersionPanel.class};
    
    private HashMap<Class<? extends Panel>,PanelReferenceTracker<?,?>> m_panelReferenceTrackerByPanelInterfaceClass = new HashMap<Class<? extends Panel>,PanelReferenceTracker<?,?>>();

    public ESONProcessorState()
    {
    	m_panelReferenceTrackerByPanelInterfaceClass.put (ApplicationPanel.class, new ApplicationPanelReferenceTracker());
    	m_panelReferenceTrackerByPanelInterfaceClass.put (EnquiryPanel.class, new EnquiryPanelReferenceTracker());
    	m_panelReferenceTrackerByPanelInterfaceClass.put(VersionPanel.class, new VersionPanelReferenceTracker());
    }
    
    public boolean updateForPanel(Panel p_panel, int p_panelIndexWithinSection, int p_sectionIndexWithinESON)
    {
    	if (p_panel == null)
    	{
			LOGGER.error("(Section).getResources().get(" + p_panelIndexWithinSection + ") -> null for Section [" + p_sectionIndexWithinESON + "]");
			return false;
    	}
    	
    	final PanelReferenceTracker<?,?> panelReferenceTracker = m_panelReferenceTrackerByPanelInterfaceClass.get(getPanelInterfaceClass(p_panel));
    	
    	if (panelReferenceTracker == null)
    	{
    		LOGGER.error("Unhandled panel type: " + p_panel.getClass().getName() + " at index: " + p_panelIndexWithinSection + " within Section [" + p_sectionIndexWithinESON + "]");
    		return false;
    	}
    	
    	return panelReferenceTracker.validatePanelDef(p_panel, p_panelIndexWithinSection, p_sectionIndexWithinESON);
    }
    
    private static Class<?> getPanelInterfaceClass(Panel p_panel)
    {
    	AssertionUtils.requireNonNull(p_panel, "p_panel");
    	final Class<?> panelClass = p_panel.getClass();
    	
    	for (int i = 0; i < s_panelIFClasses.length; ++i)
    	{
    		final Class<?> panelIFClass = s_panelIFClasses[i];
    		
    		if (panelIFClass.isAssignableFrom(panelClass))
    			return panelIFClass;
    	}
    	
    	return null;
    }
    
    private static class PanelDef
    {
    	private final int m_panelIndexWithinSection;
    	private final int m_sectionIndexWithinESON;
    	
    	PanelDef(int p_panelIndexWithinSection, int p_sectionIndexWithinESON)
    	{
    		m_panelIndexWithinSection = p_panelIndexWithinSection;
    		m_sectionIndexWithinESON = p_sectionIndexWithinESON;
    	}
    }

    private static abstract class PanelReferenceTracker<
	    	PanelType extends Panel,	/* the relevant type-specific I/F derived from the Panel I/F - i.e. ApplicationPanel, EnquiryPanel or VersionPanel */
	    	ReferencedEntityType		/* the class representing the entity type that a Panel of PanelType refers to - i.e. MdfClass, Enquiry, Version */
	    >
    {
    	private final HashMap<String,PanelDef> m_panelDefByT24EntityName = new HashMap<String,PanelDef>();
    	private final String m_panelTypeSimpleClassName;
    	private final String m_refdEntityTypeSimpleClassName;
    	
    	PanelReferenceTracker(Class<PanelType> p_panelType, Class<ReferencedEntityType> p_refdEntityType)
    	{
    		m_panelTypeSimpleClassName = p_panelType.getSimpleName();
    		m_refdEntityTypeSimpleClassName = p_refdEntityType.getSimpleName();
    	}
    	
        public boolean validatePanelDef(Panel p_panel, int p_panelIndexWithinSection, int p_sectionIndexWithinESON)
    	{
    		final ReferencedEntityType referencedEntity = getReferencedEntity(toPanelType(p_panel));
    		
    		if (referencedEntity == null)
    		{
    			LOGGER.error("(" + m_panelTypeSimpleClassName + ").get" + m_refdEntityTypeSimpleClassName + "() -> null for " + m_panelTypeSimpleClassName + " [" + p_panelIndexWithinSection + "] within Section [" + p_sectionIndexWithinESON + "]");
    			return false;
    		}
    		
    		final String t24EntityName = getT24Name(referencedEntity);
    		
    		if (t24EntityName == null)
    		{
    			LOGGER.error("(" + m_panelTypeSimpleClassName + ").get" + m_refdEntityTypeSimpleClassName + "().getName() -> null for " + m_panelTypeSimpleClassName + " [" + p_panelIndexWithinSection + "] within Section [" + p_sectionIndexWithinESON + "]");
    			return false;
    		}
    		
    		final PanelDef priorDefiningPanelDef = m_panelDefByT24EntityName.get(t24EntityName);
    		
    		if (priorDefiningPanelDef != null)
    		{
    			LOGGER.error(
    				m_refdEntityTypeSimpleClassName + ": " + t24EntityName + " referenced by " + m_panelTypeSimpleClassName + " [" + p_panelIndexWithinSection + "] within Section [" + p_sectionIndexWithinESON +
    				"] is also referenced by " + m_panelTypeSimpleClassName + " [" + priorDefiningPanelDef.m_panelIndexWithinSection + "] within Section [" + priorDefiningPanelDef.m_sectionIndexWithinESON + "]"
    			);
    			
    			return false;
    		}
    		
    		m_panelDefByT24EntityName.put(t24EntityName, new PanelDef(p_panelIndexWithinSection, p_sectionIndexWithinESON));
    		
    		LOGGER.info("Processing " + m_panelTypeSimpleClassName + " [" + p_panelIndexWithinSection + "] within Section [" + p_sectionIndexWithinESON + "]");
    		return true;
    	}

    	protected abstract ReferencedEntityType getReferencedEntity(PanelType p_panel);
    	
    	protected abstract String getT24Name(ReferencedEntityType p_referencedEntity);

    	@SuppressWarnings("unchecked")
		private PanelType toPanelType(Panel p_panel)
    	{
    		return (PanelType) p_panel;
    	}
    }

	private static class ApplicationPanelReferenceTracker extends PanelReferenceTracker<ApplicationPanel,MdfClass>
	{
		ApplicationPanelReferenceTracker()
		{
			super(ApplicationPanel.class, MdfClass.class);
		}
		
		@Override
		protected MdfClass getReferencedEntity(ApplicationPanel p_panel) {
			return p_panel.getApplication();
		}

		@Override
		protected String getT24Name(MdfClass p_referencedEntity) {
			return p_referencedEntity.getName();
		}    		
	}

    private static class EnquiryPanelReferenceTracker extends PanelReferenceTracker<EnquiryPanel, Enquiry>
    {
    	EnquiryPanelReferenceTracker()
    	{
    		super(EnquiryPanel.class, Enquiry.class);
    	}

		@Override
		protected Enquiry getReferencedEntity(EnquiryPanel p_panel) {
			return p_panel.getEnquiry();
		}

		@Override
		protected String getT24Name(Enquiry p_referencedEntity) {
			return p_referencedEntity.getName();
		}
    }

    private static class VersionPanelReferenceTracker extends PanelReferenceTracker<VersionPanel,Version>
	{
    	VersionPanelReferenceTracker()
    	{
    		super(VersionPanel.class, Version.class);
    	}
    	
		@Override
		protected Version getReferencedEntity(VersionPanel p_panel) {
			return p_panel.getVersion();
		}

		@Override
		protected String getT24Name(Version p_referencedEntity) {
			return p_referencedEntity.getT24Name();
		}    		
	}
}
