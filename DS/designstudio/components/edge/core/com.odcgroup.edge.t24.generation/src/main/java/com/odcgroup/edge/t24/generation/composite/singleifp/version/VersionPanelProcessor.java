package com.odcgroup.edge.t24.generation.composite.singleifp.version;

import gen.com.acquire.intelligentforms.entities.ComponentItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.FormButtonWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.ProductWrapper;
import gen.com.acquire.intelligentforms.entities.ProjectWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.RulesOnlyPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationTabPaneWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.HTMLPresentationTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationProductWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.IncrementorRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;

import java.util.ArrayList;

import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.GenericAttributeNode;
import com.acquire.intelligentforms.entities.IDisplayElement;
import com.acquire.intelligentforms.entities.ItemGroup;
import com.acquire.intelligentforms.entities.Phase;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroup;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationPhase;
import com.acquire.util.AssertionUtils;
import com.acquire.util.IndexList;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.composite.singleifp.BasicSingleIFPCompositeTemplateConstants;
import com.odcgroup.edge.t24.generation.composite.singleifp.GlobalContext;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.BasicVersionScreen;
import com.odcgroup.edge.t24.generation.version.BasicVersionScreenIn3D;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class VersionPanelProcessor implements BasicSingleIFPCompositeTemplateConstants
{
    private static final Logger LOGGER = GenLogger.getLogger(VersionPanelProcessor.class);

    private final Version m_version;
	private final GlobalContext m_globalContext;
	private final FormContext m_formContext;

    public VersionPanelProcessor(VersionPanel p_versionPanel, GlobalContext p_globalContext)
    {
    	m_version = AssertionUtils.requireNonNull(p_versionPanel, "p_versionPanel").getVersion();
    	m_globalContext = AssertionUtils.requireNonNull(p_globalContext, "p_globalContext");
    	m_formContext = m_globalContext.getFormContext();
    	
    	AssertionUtils.requireConditionTrue((m_version != null), "p_versionPanel.getVersion() != null");
    }
    
    public void go() throws Exception
    {
    	final String t24VersionName = m_version.getT24Name();
    	final String ifpFriendlyVersionName = VersionUtility.getVersionName( m_version );
    	
    	LOGGER.info("Referenced Version: " + t24VersionName);

    	TempBasicVersionScreenContainerElems tempContext = addTemporaryBasicVersionScreenTemplateElements();
    	
    	BasicVersionScreen basicVersionScreen = new BasicVersionScreenIn3D(
    		ifpFriendlyVersionName,
    		m_version,
    		null, /* VersionMapper p_mapper */
    		m_globalContext.getTemplateProject(),
    		new LanguageMapHelperWrapper(m_globalContext.getLanguageMapHelper()) 
    	);
    	
    	basicVersionScreen.build();
    	
    	final ItemGroupWrapper versionItemGroup = createAndPopulateNewVersionItemGroupFromTempContext(ifpFriendlyVersionName, tempContext);
    	
    	final PropertyWrapper versionItemGroupHideShowStateItem = addVersionItemGroupVisibilityStateItem(ifpFriendlyVersionName);
    	addApplicabilityConditionToVersionItemGroup(versionItemGroup, versionItemGroupHideShowStateItem);
    	
    	final PropertyGroupWrapper versionSetupParamsSubGroup = addVersionSetupParamsSubGroup(ifpFriendlyVersionName);
    	
    	addTopLevelVersionSetupRule(versionSetupParamsSubGroup, ifpFriendlyVersionName, versionItemGroupHideShowStateItem);
    	addStartPhaseRuleToMakeVersionItemGroupInitiallyVisible(ifpFriendlyVersionName, versionItemGroupHideShowStateItem);
    }
    
    private void addStartPhaseRuleToMakeVersionItemGroupInitiallyVisible(String p_ifpFriendlyVersionName, PropertyWrapper p_versionItemGroupHideShowStateItem)
    {
    	final String listKeyValueToSet = Lists.ShowOrHide.Keys.SHOW;
    	
    	final SetValueRuleWrapper setEnquirySubItemGroupVisibleRule = SetValueRuleWrapper.create(m_formContext);
    	setEnquirySubItemGroupVisibleRule.setName("Set version item group " + p_ifpFriendlyVersionName + " to be initially visible");
    	setEnquirySubItemGroupVisibleRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	setEnquirySubItemGroupVisibleRule.setPropertyName(p_versionItemGroupHideShowStateItem);
    	setEnquirySubItemGroupVisibleRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
    	setEnquirySubItemGroupVisibleRule.setFromValue(listKeyValueToSet);
    	setEnquirySubItemGroupVisibleRule.setFromValueList(listKeyValueToSet);
    	
    	m_globalContext.getCompositeScreenStartPhase().addPostPhaseRule(setEnquirySubItemGroupVisibleRule.unwrap());
    }

    private void addApplicabilityConditionToVersionItemGroup(ItemGroupWrapper p_versionItemGroup, PropertyWrapper p_versionItemGroupHideShowStateItem)
    {
    	p_versionItemGroup.setNotApplicable(Boolean.TRUE);
    	p_versionItemGroup.setConditionExpression("$$" + p_versionItemGroupHideShowStateItem.getEntityKeyName() + "$ == '" + Lists.ShowOrHide.Keys.SHOW + "'");
    }
    
    private PropertyWrapper addVersionItemGroupVisibilityStateItem(String p_ifpFriendlyVersionName) throws Exception
    {
    	final PropertyGroupWrapper versionSpecificWorkingElemsSubGroup = m_globalContext.getMandatoryTemplateDefinedPropertyGroup(DataStore.WorkingElementsGroup.PATH + '.' + p_ifpFriendlyVersionName + "[1]");
    	
    	final PropertyWrapper versionItemGroupVisibilityStateItem = PropertyWrapper.create(m_formContext, "VersionItemGroupVisibilityState");
    	versionItemGroupVisibilityStateItem.setType(m_globalContext.getShowOrHideListType().getEntityKeyName());
    	versionSpecificWorkingElemsSubGroup.addChild(versionItemGroupVisibilityStateItem);
    	
		return versionItemGroupVisibilityStateItem;
    }
    
    private void addTopLevelVersionSetupRule(PropertyGroupWrapper p_versionSetupParamsSubGroup, String ifpFriendlyVersionName, PropertyWrapper p_versionItemGroupVisibilityStateItem)
    {
    	final ContainerRuleWrapper versionSpecificSetupRulesContainer = ContainerRuleWrapper.create(m_formContext, "Version Setup Rules - " + ifpFriendlyVersionName);
    	m_globalContext.getRulesRoot().addChild(versionSpecificSetupRulesContainer.unwrap());
    	
    	final IncrementorRuleWrapper versionSetupRequestIncrementorRule = IncrementorRuleWrapper.create(m_formContext);
    	versionSetupRequestIncrementorRule.setName("Increment " + Structures.VersionSetupParamsType.ItemNames.VERSION_SETUP_REQUEST_NUMBER);
    	versionSetupRequestIncrementorRule.setOperator(IncrementorRuleWrapper.EOperator.INCREMENT);
    	versionSetupRequestIncrementorRule.setType(IncrementorRuleWrapper.EType.DATA_ITEM);
    	versionSetupRequestIncrementorRule.setPropertyNameFromEntityKey(p_versionSetupParamsSubGroup.getEntityKeyName() + '.' + Structures.VersionSetupParamsType.ItemNames.VERSION_SETUP_REQUEST_NUMBER);
    	versionSpecificSetupRulesContainer.addTrueRule(versionSetupRequestIncrementorRule.unwrap());
    	
    	final SetValueRuleWrapper setCustomerIfpFriendlyVersionNameRule = SetValueRuleWrapper.create(m_formContext);
    	setCustomerIfpFriendlyVersionNameRule.setName( "Set " + Structures.VersionSetupParamsType.ItemNames.IFP_FRIENDLY_VERSION_NAME + " to " + ifpFriendlyVersionName);
    	setCustomerIfpFriendlyVersionNameRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
    	setCustomerIfpFriendlyVersionNameRule.setPropertyNameFromEntityKey(p_versionSetupParamsSubGroup.getEntityKeyName() + '.' + Structures.VersionSetupParamsType.ItemNames.IFP_FRIENDLY_VERSION_NAME);
    	setCustomerIfpFriendlyVersionNameRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
    	setCustomerIfpFriendlyVersionNameRule.setFromValue( "Customer_Input" );
    	versionSpecificSetupRulesContainer.addTrueRule(setCustomerIfpFriendlyVersionNameRule.unwrap());
    	
    	final SetValueRuleWrapper setVersionItemGroupVisibilityStateToShow = SetValueRuleWrapper.create(m_formContext);
    	setVersionItemGroupVisibilityStateToShow.setName("Set VersionItemGroupVisibilityState to SHOW");
    	setVersionItemGroupVisibilityStateToShow.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	setVersionItemGroupVisibilityStateToShow.setPropertyName(p_versionItemGroupVisibilityStateItem);
    	setVersionItemGroupVisibilityStateToShow.setFromType(SetValueRuleWrapper.EFromType.VALUE);
    	setVersionItemGroupVisibilityStateToShow.setFromValue(Lists.ShowOrHide.Keys.SHOW);
    	setVersionItemGroupVisibilityStateToShow.setFromValueList(Lists.ShowOrHide.Keys.SHOW);
    	versionSpecificSetupRulesContainer.addTrueRule(setVersionItemGroupVisibilityStateToShow.unwrap());
    }
    
    private PropertyGroupWrapper addVersionSetupParamsSubGroup(String p_ifpFriendlyVersionName) throws Exception
    {
    	final PropertyGroupWrapper versionSetupParamsSubGroup = PropertyGroupWrapper.create(m_formContext, p_ifpFriendlyVersionName);
    	versionSetupParamsSubGroup.setLinkToStructure(Boolean.TRUE);
    	versionSetupParamsSubGroup.setLinkedStructure(m_globalContext.getVersionSetupParamsStructure());
    	
    	m_globalContext.getTopLevelVersionSetupParamsGroup().addChild(versionSetupParamsSubGroup);
    	return versionSetupParamsSubGroup;
    }
    
    private TempBasicVersionScreenContainerElems addTemporaryBasicVersionScreenTemplateElements()
    {
    	final TemplateProject templateProject = m_globalContext.getTemplateProject();
    	final ProjectWrapper project = m_globalContext.getTemplateProject().getProject();

    	final ProductWrapper tempBasicProcess = ProductWrapper.create(m_formContext, "BasicProcess");
    	project.getProcessRoot().addChild(tempBasicProcess.unwrap());
    	
    	final RulesOnlyPhaseWrapper tempSetupPhase = RulesOnlyPhaseWrapper.create(m_formContext, "Setup");
    	tempBasicProcess.addChild(tempSetupPhase);
    	
    	final ContainerRuleWrapper setupContainerRule = ContainerRuleWrapper.create(m_formContext, "Setup Rules");
    	tempSetupPhase.addChild(setupContainerRule);

    	final PhaseWrapper tempBasicPhase = PhaseWrapper.create(m_formContext, "BasicPhase");
    	tempBasicProcess.addChild(tempBasicPhase);

    	final RichHTMLPresentationProductWrapper tempBasicPresProcess = RichHTMLPresentationProductWrapper.create(m_formContext, tempBasicProcess.unwrap());
    	templateProject.getRichPresentationType().addChild(tempBasicPresProcess);
    	
    	final RichHTMLPresentationPhaseWrapper tempBasicPresPhase = RichHTMLPresentationPhaseWrapper.create(m_formContext, tempBasicPhase.unwrap());
    	tempBasicPresProcess.addChild(tempBasicPresPhase);
    	
    	return new TempBasicVersionScreenContainerElems(tempBasicProcess, tempSetupPhase, tempBasicPhase, tempBasicPresProcess, tempBasicPresPhase);
    }
    
    private ItemGroupWrapper createAndPopulateNewVersionItemGroupFromTempContext(String p_ifpFriendlyVersionName, TempBasicVersionScreenContainerElems p_tempContext) throws Exception
    {
    	final ItemGroupWrapper versionItemGroup = ItemGroupWrapper.create(m_formContext, p_ifpFriendlyVersionName);
    	final ComponentItemGroupWrapper versionButtonBarComponentItemGroup = VersionButtonBarComponentRefHelper.createVersionButtonBarComponentItemGroup(p_ifpFriendlyVersionName, m_formContext);
    	versionItemGroup.addChild(versionButtonBarComponentItemGroup);
    	m_globalContext.getCompositeScreenPhase().addChild(versionItemGroup);
    	
    	final RichHTMLPresentationItemGroupWrapper versionPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(m_formContext, versionItemGroup.unwrap());
    	final RichHTMLPresentationItemGroupWrapper versionButtonBarComponentPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(m_formContext, versionButtonBarComponentItemGroup.unwrap());
    	versionPresItemGroup.addChild(versionButtonBarComponentPresItemGroup);
    	m_globalContext.getCompositeScreenPresPhase().addChild(versionPresItemGroup);
    	
    	p_tempContext.relocateChildrenTo(versionItemGroup, versionPresItemGroup, m_globalContext);
    	
    	return versionItemGroup;
    }
    
	private static class LanguageMapHelperWrapper extends LanguageMapHelper
	{
		private LanguageMapHelper m_delegate;

		LanguageMapHelperWrapper(LanguageMapHelper p_delegate)
		{
			m_delegate = AssertionUtils.requireNonNull(p_delegate, "p_delegate");
		}
		
		@Override
		public void registerT24TextTranslations(QuestionWrapper p_question, TextTranslations p_translations)
		{
			m_delegate.registerT24TextTranslations(p_question, p_translations);
		}

		@Override
		public void registerT24TextTranslations(PresentationQuestionWrapper p_presQuestion, TextTranslations p_translations)
		{
		    m_delegate.registerT24TextTranslations( p_presQuestion, p_translations );
		}
		
		@Override
		public void registerT24TextTranslations(HTMLPresentationTableWrapper p_table, TextTranslations p_translations) {
			m_delegate.registerT24TextTranslations(p_table, p_translations);
		}

		@Override
		public void registerT24TextTranslations(HeadingWrapper p_heading, TextTranslations p_translations)
		{
			m_delegate.registerT24TextTranslations(p_heading, p_translations);
		}

		@Override
		public void registerT24TextTranslations(PresentationTabPaneWrapper p_tabPane, TextTranslations p_translations)
		{
			m_delegate.registerT24TextTranslations(p_tabPane, p_translations);
		}

		@Override
		public void registerT24HintTextTranslations(QuestionWrapper p_question, TextTranslations p_translations)
		{
			m_delegate.registerT24HintTextTranslations(p_question, p_translations);
		}

		@Override
		public void registerT24HintTextTranslations(HeadingWrapper p_heading, TextTranslations p_translations)
		{
			m_delegate.registerT24HintTextTranslations(p_heading, p_translations);
		}

		@Override
		public void registerT24HelpTextTranslations(QuestionWrapper p_question, TextTranslations p_translations)
		{
			m_delegate.registerT24HelpTextTranslations(p_question, p_translations);
		}

		@Override
		public void registerT24HelpTextTranslations(HeadingWrapper p_heading, TextTranslations p_translations)
		{
			m_delegate.registerT24HelpTextTranslations(p_heading, p_translations);
		}

		@Override
		public void registerT24HintTextTranslations(FormButtonWrapper p_button, TextTranslations p_translations)
		{
			m_delegate.registerT24HintTextTranslations(p_button, p_translations);
		}

		@Override
		public void generateLanguageMapsAndRules(ContainerRuleWrapper p_startupRulesContainer, PhaseWrapper p_initialPhase)
		{
			// Does nothing, since we want to generate a single set of language maps for the composite a whole, and that can only be done once ALL of the entitities referenced
			// in the ESON definition the Composite have been processed.
		}

		@Override
		public String getT24Translation(GenericAttributeNode p_node, String p_attrName, String p_englishAttrValue, String p_languageCode) {
			// Given that we've overridden our generateLanguageMapsAndRules() to be a no-op, this should never actually get called in practice.
			return null;
		}
	}

	private static class TempBasicVersionScreenContainerElems
    {
    	final ProductWrapper tempProcess;
    	final PhaseWrapper tempSetupPhase;
    	final PhaseWrapper tempBasicPhase;
    	final RichHTMLPresentationProductWrapper tempPresProcess;
    	final RichHTMLPresentationPhaseWrapper tempPresPhase;
    	
    	TempBasicVersionScreenContainerElems(
    		ProductWrapper p_tempProcess,
    		PhaseWrapper p_tempSetupPhase,
    		PhaseWrapper p_tempBasicPhase,
    		RichHTMLPresentationProductWrapper p_tempPresProcess,
    		RichHTMLPresentationPhaseWrapper p_tempPresPhase
    	) {
    		tempProcess = p_tempProcess;
    		tempSetupPhase = p_tempSetupPhase;
    		tempBasicPhase = p_tempBasicPhase;
    		tempPresProcess = p_tempPresProcess;
    		tempPresPhase = p_tempPresPhase;
    	}
    	
    	void relocateChildrenTo(ItemGroupWrapper p_targetItemGroup, RichHTMLPresentationItemGroupWrapper p_targetPresItemGroup, GlobalContext p_globalContext)
    	{
    		relocateTempPhaseItemsTo(p_targetItemGroup, p_globalContext);
    		relocateTempPresPhaseItemsTo(p_targetPresItemGroup);
    		
    		tempProcess.unwrap().getParent().removeChild(tempProcess.unwrap());
    		tempPresProcess.unwrap().getParent().removeChild(tempPresProcess.unwrap());
    	}
    	
    	private void relocateTempPhaseItemsTo(ItemGroupWrapper p_targetItemGroup, GlobalContext p_globalContext)
    	{
    		final Phase sourcePhase = tempBasicPhase.unwrap();
    		final String exposedDataItemList = (String) sourcePhase.getAttribute(Phase.ATT_UPDATABLE_PROPERTIES);
    		final ItemGroup targetItemGroup = p_targetItemGroup.unwrap();
    		final IndexList childrenIndexList = (IndexList) sourcePhase.getChildrenIndexList().clone();
    		
    		final ArrayList<?> childNodes = childrenIndexList.getKeys();
    		
    		for (int i = childNodes.size() - 1; i >= 0 ; --i)
    		{
    			final Object childNode = childNodes.get(i);
    			
    			if (! (childNode instanceof IDisplayElement)) // get rid of any pre/post-phase rules that won't be valid as children of p_targetItemGroup
    				childrenIndexList.remove(i);

    			/*!!
    			else if (childNode instanceof Heading)
    			{
    				final Heading heading = (Heading) childNode;
    				System.out.println("STH: found heading " + heading.getDisplayString());
    			}
    			!!*/
    		}
    		
    		sourcePhase.removeAllChildren();
    		targetItemGroup.addChildren(childrenIndexList);
    		p_globalContext.getCompositeScreenPhase().unwrap().setAttribute(Phase.ATT_UPDATABLE_PROPERTIES, exposedDataItemList);
    		sourcePhase.getParent().removeChild(sourcePhase);
    	}
    	
    	private void relocateTempPresPhaseItemsTo(RichHTMLPresentationItemGroupWrapper p_targetPresItemGroup)
    	{
    		final RichHTMLPresentationPhase sourcePresPhase = tempPresPhase.unwrap();
    		final RichHTMLPresentationItemGroup targetPresItemGroup = p_targetPresItemGroup.unwrap();
    		final IndexList childrenIndexList = (IndexList) sourcePresPhase.getChildrenIndexList().clone();
    		
    		sourcePresPhase.removeAllChildren();
    		targetPresItemGroup.addChildren(childrenIndexList);
    		sourcePresPhase.getParent().removeChild(sourcePresPhase);
    	}
    }
}
