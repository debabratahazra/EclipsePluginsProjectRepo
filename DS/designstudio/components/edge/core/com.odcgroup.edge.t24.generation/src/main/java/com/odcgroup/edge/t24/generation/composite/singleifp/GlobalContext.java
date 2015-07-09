package com.odcgroup.edge.t24.generation.composite.singleifp;

import gen.com.acquire.intelligentforms.entities.DataStructureWrapper;
import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;

import java.io.File;

import com.acquire.intelligentforms.rules.ContainerRule;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.BasicEdgeMapperProject;
import com.odcgroup.edge.t24.generation.composite.singleifp.BasicSingleIFPCompositeTemplateConstants.Lists;
import com.odcgroup.edge.t24.generation.composite.singleifp.BasicSingleIFPCompositeTemplateConstants.Processes;
import com.odcgroup.edge.t24.generation.composite.singleifp.BasicSingleIFPCompositeTemplateConstants.Structures;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryTemplateIFPConstants.DataStore;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryTemplateIFPConstants.TopLevelRuleNames;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.BaseGeneratorContext;
import com.odcgroup.edge.t24.generation.util.FileSystemUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class GlobalContext extends BaseGeneratorContext
{
	private final File m_templatesDir;
	private final LanguageMapHelper m_languageMapHelper;
	
	private final FormListWrapper m_displayedEnquiryItemGroupListType;
	private final FormListWrapper m_searchOutcomeListType;
	private final FormListWrapper m_showOrHideListType;
	private final FormListWrapper m_sortOrderListType;
	private final FormListWrapper m_trueFalseListType;
	
	private final DataStructureWrapper m_versionSetupParamsStruct;

	private final PropertyGroupWrapper m_workingElementsGroup;
	private final PropertyGroupWrapper m_topLevelVersionSetupParamsGroup;
	
	private final PropertyWrapper m_scratchItem;
	
	private final PhaseWrapper m_compositeScreenStartPhase;
	private final PhaseWrapper m_compositeScreenPhase;
	
	private final PresentationPhaseWrapper m_compositeScreenPresPhase;
	
	private final ContainerRuleWrapper m_startupRulesContainerRule;
	
	public GlobalContext(BasicEdgeMapperProject p_project, TemplateProject p_templateProject, File p_templatesDir) throws Exception
	{
		super(p_project, p_templateProject);
		
		m_templatesDir = FileSystemUtils.findOrCreateMandatoryDirectory(p_templatesDir);
		m_languageMapHelper = new LanguageMapHelper(getTemplateProject(), p_templatesDir.getParentFile());

		m_displayedEnquiryItemGroupListType = getMandatoryTemplateDefinedListType(Lists.DisplayedEnquiryItemGroup.FULLNAME);
		m_searchOutcomeListType = getMandatoryTemplateDefinedListType(Lists.SearchOutcome.FULLNAME);
		m_showOrHideListType = getMandatoryTemplateDefinedListType(Lists.ShowOrHide.FULLNAME);
		m_sortOrderListType = getMandatoryTemplateDefinedListType(Lists.SortOrder.FULLNAME);
		m_trueFalseListType = getMandatoryTemplateDefinedListType(Lists.TrueOrFalse.FULLNAME);
		
    	m_versionSetupParamsStruct = getMandatoryTemplateDefinedStructure(Structures.VersionSetupParamsType.NAME);

		m_workingElementsGroup = getMandatoryTemplateDefinedPropertyGroup(DataStore.WorkingElementsGroup.PATH);
		m_topLevelVersionSetupParamsGroup = getMandatoryTemplateDefinedPropertyGroup(DataStore.WorkingElementsGroup.VersionSetupParamsGroup.PATH);
		
		m_scratchItem = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SCRATCH);

		m_compositeScreenStartPhase = getMandatoryTemplateDefinedPhase(Processes.CompositeScreenProcess.StartPhase.FULLNAME);
		
		m_compositeScreenPhase = getMandatoryTemplateDefinedPhase(Processes.CompositeScreenProcess.CompositeScreenPhase.FULLNAME);
		m_compositeScreenPresPhase = getMandatoryTemplateDefinedPresentationPhase(m_compositeScreenPhase);
		
    	m_startupRulesContainerRule = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.STARTUP_RULES_CONTAINER_RULE, ContainerRule.class));
	}
	
	protected GlobalContext(GlobalContext p_other)
	{
		super(AssertionUtils.requireNonNull(p_other, "p_other"));
		
		m_templatesDir = p_other.m_templatesDir;
		m_languageMapHelper = p_other.m_languageMapHelper;
		
		m_displayedEnquiryItemGroupListType = p_other.m_displayedEnquiryItemGroupListType;
		m_searchOutcomeListType = p_other.m_searchOutcomeListType;
		m_showOrHideListType = p_other.m_showOrHideListType;
		m_sortOrderListType = p_other.m_sortOrderListType;
		m_trueFalseListType = p_other.m_trueFalseListType;
		
		m_versionSetupParamsStruct = p_other.m_versionSetupParamsStruct;
		
		m_workingElementsGroup = p_other.m_workingElementsGroup;
		m_topLevelVersionSetupParamsGroup = p_other.m_topLevelVersionSetupParamsGroup;
		
		m_scratchItem = p_other.m_scratchItem;
		
		m_compositeScreenStartPhase = p_other.m_compositeScreenStartPhase;
		m_compositeScreenPhase = p_other.m_compositeScreenPhase;
		m_compositeScreenPresPhase = p_other.m_compositeScreenPresPhase;
		m_startupRulesContainerRule = p_other.m_startupRulesContainerRule;
	}
	
	public File getTemplatesDir()
	{
		return m_templatesDir;
	}
	
	public LanguageMapHelper getLanguageMapHelper()
	{
		return m_languageMapHelper;
	}
	
	public FormListWrapper getDisplayedEnquiryItemGroupListType()
	{
		return m_displayedEnquiryItemGroupListType;
	}
	
	public FormListWrapper getSearchOutcomeListType()
	{
		return m_searchOutcomeListType;
	}
	
	public FormListWrapper getShowOrHideListType()
	{
		return m_showOrHideListType;
	}
	
	public FormListWrapper getSortOrderListType()
	{
		return m_sortOrderListType;
	}
	
	public FormListWrapper getTrueFalseListType()
	{
		return m_trueFalseListType;
	}
	
	public DataStructureWrapper getVersionSetupParamsStructure()
	{
		return m_versionSetupParamsStruct;
	}
	
	public PropertyGroupWrapper getWorkingElementsGroup()
	{
		return m_workingElementsGroup;
	}
	
	public PropertyGroupWrapper getTopLevelVersionSetupParamsGroup()
	{
		return m_topLevelVersionSetupParamsGroup;
	}

	public PropertyWrapper getScratchItem()
	{
		return m_scratchItem;
	}
	
	public PhaseWrapper getCompositeScreenStartPhase()
	{
		return m_compositeScreenStartPhase;
	}
	
	public PhaseWrapper getCompositeScreenPhase()
	{
		return m_compositeScreenPhase;
	}
	
	public PresentationPhaseWrapper getCompositeScreenPresPhase()
	{
		return m_compositeScreenPresPhase;
	}
	
	public void generateLanguageMapsAndRules() throws Exception
	{
		m_languageMapHelper.generateLanguageMapsAndRules(m_startupRulesContainerRule, m_compositeScreenStartPhase);
	}
}
