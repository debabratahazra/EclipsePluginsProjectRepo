package com.odcgroup.edge.t24.generation.composite.singleifp;

import gen.com.acquire.intelligentforms.rules.GotoRuleWrapper;
import gen.com.acquire.intelligentforms.rules.GotoRuleWrapper.EOperationType;
import gen.com.acquire.intelligentforms.rules.GotoRuleWrapper.ERemainingRules;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.BasicEdgeMapperProject;
import com.odcgroup.edge.t24.generation.CompositeScreenMapper;
import com.odcgroup.edge.t24.generation.composite.ESONProcessorState;
import com.odcgroup.edge.t24.generation.composite.singleifp.enquiry.EnquiryPanelProcessor;
import com.odcgroup.edge.t24.generation.composite.singleifp.version.VersionPanelProcessor;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.CachedTemplateProject;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel;
import com.odcgroup.edge.t24ui.cos.bespoke.Panel;
import com.odcgroup.edge.t24ui.cos.bespoke.Section;
import com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class SingleIFPCompositeProject extends BasicEdgeMapperProject {
    private static final Logger LOGGER = GenLogger.getLogger(SingleIFPCompositeProject.class);

    private final TemplateProject m_templateProject;
    private final BespokeCompositeScreen m_bespokeCOS;
	private final DomainRepository m_domainRepository;
	private final ESONProcessorState m_esonProcessorState = new ESONProcessorState();

	private GlobalContext m_globalContext;
	private LanguageMapHelper m_languageMapHelper;

    private final CompositeScreenMapper m_compositeScreenMapper;

	public static SingleIFPCompositeProject create(CompositeScreenMapper p_compositeScreenMapper, BespokeCompositeScreen p_bespokeCOS)
	{
		AssertionUtils.requireNonNull(p_bespokeCOS, "p_compositeScreen");
		
        final TemplateProject templateProject = CachedTemplateProject.getTemplateProject( "/templates/BasicSingleIfpCompositeTemplate.ifp"
                                                                                        , "/templates/Brand.ect"
                                                                                        );
        
        templateProject.getRichPresentationType().setTemplate("Brand.tpl");
        
		return new SingleIFPCompositeProject(p_compositeScreenMapper, templateProject, p_bespokeCOS);
	}
	
	public void go(File p_templatesDir) throws Exception
	{
		AssertionUtils.requireNonNull(p_templatesDir, "p_templatesDir");
		
		m_globalContext = new GlobalContext(this, m_templateProject, p_templatesDir);
		
		processSections();
		
        m_globalContext.generateLanguageMapsAndRules();
        
        final GotoRuleWrapper goForwardToCompositeScreenPhaseRule = GotoRuleWrapper.create(
        	getFormContext(),
        	"Go forward to " + m_globalContext.getCompositeScreenPhase().getName() + " phase",
        	null, /* p_debugLevel */
        	null, /* p_ruleDisabled */
        	EOperationType.GO_FORWARD_TO_A_NAMED_PHASE,
        	m_globalContext.getCompositeScreenPhase(),
        	ERemainingRules.NEVER
        );
        
        m_globalContext.getCompositeScreenStartPhase().addPostPhaseRule(goForwardToCompositeScreenPhaseRule.unwrap());
	}
	
	private void processSections() throws Exception
	{
		final EList<Section> sectionList = m_bespokeCOS.getSections();
		final int numSections = (sectionList == null) ? 0 : sectionList.size();
		
		for (int i = 0; i < numSections; ++i)
		{
			final Section section = sectionList.get(i);
			
			if (section == null)
				LOGGER.error("(CompositeScreen).getSections().get(" + i + ") -> null !");

			else
			{
				if (LOGGER.isInfoEnabled())
					LOGGER.info("Processing Section " + (i + 1) + "/" + numSections + ": " + section.getName());

				processSection(section, i);
			}
		}		
	}
	
	private void processSection(Section p_section, int p_sectionIndexWithinESON) throws Exception
	{
		AssertionUtils.requireNonNull(p_section, "p_section");
		
		final EList<Panel> panelList = p_section.getResources();
		final int numPanels = (panelList == null) ? 0 : panelList.size();
		
		int processedEnquiryPanelCount = 0;
		
		for (int i = 0; i < numPanels; ++i)
		{
			final Panel panel = panelList.get(i);
			
			if (! m_esonProcessorState.updateForPanel(panel, i, p_sectionIndexWithinESON))
				continue;
			
			boolean isFirstPanel = (i == 0);
			
			if (panel instanceof ApplicationPanel)
			{
				processApplicationPanel((ApplicationPanel) panel);
			}
			
			else if (panel instanceof EnquiryPanel)
			{
				new EnquiryPanelProcessor((EnquiryPanel) panel, isFirstPanel, m_globalContext).go();
				++processedEnquiryPanelCount;
			}
			
			else if (panel instanceof VersionPanel)
			{
				new VersionPanelProcessor((VersionPanel) panel, m_globalContext).go();
			}
			
			else
			{
				LOGGER.error("(Section).getResources().get(" + i + ") returned unhandled Panel sub-type: " + panel.getClass().getName() + " for section: " + p_section.getName());
			}
		}
	}
	
	private void processApplicationPanel(ApplicationPanel p_applicationPanel)
	{
		AssertionUtils.requireNonNull(p_applicationPanel, "p_applicationPanel");
		final MdfClass appMdfClass = p_applicationPanel.getApplication();
		LOGGER.info("(ApplicationPanel).getApplication() -> " + appMdfClass);
	}
	
	private SingleIFPCompositeProject(CompositeScreenMapper p_compositeScreenMapper, TemplateProject p_templateProject, BespokeCompositeScreen p_bespokeCOS)
	{
		super(AssertionUtils.requireNonNull(p_templateProject, "p_templateProject").getProject(), p_compositeScreenMapper);
        m_compositeScreenMapper = p_compositeScreenMapper;
		m_templateProject = p_templateProject;
		m_bespokeCOS = AssertionUtils.requireNonNull(p_bespokeCOS, "p_bespokeCOS");
		m_domainRepository = DomainRepository.getInstance(OfsResourceHelper.getOfsProject(p_bespokeCOS.eResource()));
	}

    /**
     * @return the compositeScreenMapper
     */
    public CompositeScreenMapper getCompositeScreenMapper()
    {
        return m_compositeScreenMapper;
    }
}
