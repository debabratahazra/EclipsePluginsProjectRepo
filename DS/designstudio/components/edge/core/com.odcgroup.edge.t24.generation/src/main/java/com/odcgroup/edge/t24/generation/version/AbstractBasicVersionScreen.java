package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper.EHeaderLevel;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.ProductWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.RulesOnlyPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationProductWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;

import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.acquire.intelligentforms.entities.GenericLeafNode;
import com.acquire.intelligentforms.entities.ItemGroup;
import com.acquire.intelligentforms.rules.ContainerRule;
import com.acquire.util.StringUtils;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.pattern.container.BasicProcess;
import com.edgeipk.builder.pattern.container.ProcessWithSetupRules;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.ProjectMapper;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.t24.version.versionDSL.Version;
import com.temenos.connect.odata.utils.VersionServiceData;

/**
 * TODO: Document me!.
 *
 * @param <MapperType> the generic type
 * @author sakbar
 */
public abstract class AbstractBasicVersionScreen<MapperType extends ProjectMapper<?>> extends ProcessWithSetupRules<RichHTMLPresentationProductWrapper, RichHTMLPresentationPhaseWrapper>
{
    private static final Logger       LOGGER                 = GenLogger.getLogger( AbstractBasicVersionScreen.class );

    /** The Constant BASIC_PHASE_NAME with value of {@value}. */
    public static final String        BASIC_PHASE_NAME       = "BasicPhase";

    /** The Constant BASIC_PROCESS_NAME with value of {@value}. */
    public static final String        BASIC_PROCESS_NAME     = "BasicProcess";

    private static final String       HEADER_ITEM_GROUP_NAME = "Header";
    private static final String       BODY_ITEM_GROUP_NAME   = "Body";
    private static final String       FOOTER_ITEM_GROUP_NAME = "Footer";

    /** The template project. */
    private final TemplateProject     m_templateProject;

    /** The mapper. */
    private final MapperType          m_mapper;

    /** The helper object to manage generation of language maps and related rules. */
    protected final LanguageMapHelper m_languageMapHelper;

    /** The exposed data items. */
    protected Set<String>             m_exposedDataItems     = null;

    /**
     * Instantiates a new abstract basic version screen.
     *
     * @param p_mapper the mapper
     * @param p_templateProject the template project
     * @param p_productName the product name
     * @param p_phaseName the phase name
     * @param p_languageMapHelper the language map helper
     */
    public AbstractBasicVersionScreen(MapperType p_mapper, TemplateProject p_templateProject, String p_productName, String p_phaseName, LanguageMapHelper p_languageMapHelper)
    {
        super( p_templateProject.getProject(), p_productName, p_phaseName, p_templateProject.getRichPresentationType() );
        
        m_mapper = p_mapper;
        m_templateProject = p_templateProject;
        m_languageMapHelper = p_languageMapHelper;
    }
    
    /**
     * Gets the template project.
     *
     * @return the templateProject
     */
    public TemplateProject getTemplateProject()
    {
        return m_templateProject;
    }
    
    /**
     * Gets the helper object to manage generation of language maps and related rules.
     *
     * @return the languageMapHelper
     */
    public LanguageMapHelper getLanguageMapHelper()
    {
        return m_languageMapHelper;
    }

    /**
     * Builds the basic version screen, consisting of the basic process (see {@link BasicProcess#build()}<p>
     * and then the following items:
     * 
     * <ul>
     * <li>Version data group
     * <li>Invoke Iris from Setup Rules
     * <li>Version header
     * <li>Version fields
     * <li>Associated versions and fields
     * </ul>.
     *
     * @throws Exception the exception
     */
    @Override
    public void build() throws Exception
    {
        super.build();

        m_exposedDataItems = new TreeSet<String>();

        if  ( getPhase().unwrap().getChildWithAttribute( ItemGroup.ATTR_ITEM_GROUP_NAME, HEADER_ITEM_GROUP_NAME ) instanceof ItemGroup )
        {
            ItemGroupWrapper ig = getTemplateProject().getPhaseItemGroup( getPhase().getFullName(), HEADER_ITEM_GROUP_NAME );
            PresentationItemGroupWrapper pig = getTemplateProject().getPresentationPhaseItemGroup(getPresentationType(), ig);

            buildHeader(ig, pig);
        }
        else
        {
            buildHeader(getPhase(), getPresentationPhase());
        }

        if  ( getPhase().unwrap().getChildWithAttribute( ItemGroup.ATTR_ITEM_GROUP_NAME, BODY_ITEM_GROUP_NAME ) instanceof ItemGroup )
        {
            ItemGroupWrapper ig = getTemplateProject().getPhaseItemGroup( getPhase().getFullName(), BODY_ITEM_GROUP_NAME );
            PresentationItemGroupWrapper pig = getTemplateProject().getPresentationPhaseItemGroup(getPresentationType(), ig);

            buildBody(ig, pig);
        }
        else
        {
            buildBody(getPhase(), getPresentationPhase());
        }

        if  ( getPhase().unwrap().getChildWithAttribute( ItemGroup.ATTR_ITEM_GROUP_NAME, FOOTER_ITEM_GROUP_NAME ) instanceof ItemGroup )
        {
            ItemGroupWrapper ig = getTemplateProject().getPhaseItemGroup( getPhase().getFullName(), FOOTER_ITEM_GROUP_NAME );
            PresentationItemGroupWrapper pig = getTemplateProject().getPresentationPhaseItemGroup(getPresentationType(), ig);

            buildFooter(ig, pig);
        }
        else
        {
            buildFooter(getPhase(), getPresentationPhase());
        }
        
        // Language map generation
        //
        m_languageMapHelper.generateLanguageMapsAndRules(getStartupRulesContainer(), getPhase());
        
        // Finally setup any exposed data items
        //
        if ( m_exposedDataItems.size() > 0 )
        {
            getPhase().setUpdatablePropertiesFromEntityKey( StringUtils.buildSeparatedList( new StringBuilder(), m_exposedDataItems, "," ).toString() );
        }
    }

    /**
     * Builds the header.
     *
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @throws Exception the exception
     */
    protected abstract void buildHeader(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer) throws Exception;

    /**
     * Builds the body.
     *
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @throws Exception the exception
     */
    protected abstract void buildBody(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer) throws Exception;

    /**
     * Builds the footer.
     *
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @throws Exception the exception
     */
    protected abstract void buildFooter(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer) throws Exception;
    
    /**
     * Adds the exposed data item.
     *
     * @param p_dataItem the data item
     */
    public void addExposedDataItem(String p_dataItem)
    {
        m_exposedDataItems.add( p_dataItem );
    }

    @Override
    protected RulesOnlyPhaseWrapper createSetupRulesOnlyPhase()
    {
        RulesOnlyPhaseWrapper rop = (RulesOnlyPhaseWrapper) m_templateProject.getPhase( getProcess().getName() + "." + ProcessWithSetupRules.SETUP_RULES_ONLY_PHASE_NAME );
        
        getProcess().setInitialPhase( rop );
        
        return  rop;
    }
    
    @Override
    protected ContainerRuleWrapper createSetupRulesContainer()
    {
        ContainerRuleWrapper setupRules = getSetupRulesOnlyPhase().getChildWithName( SETUP_RULES_NAME, ContainerRule.class );
        
        if  ( setupRules == null )
            throw new RuntimeException("Could not locate setup rules within setup phase");
        
        return setupRules;
    }
    
    @Override
    protected ContainerRuleWrapper createStartupRulesContainer()
    {
        return m_templateProject.<ContainerRule, ContainerRuleWrapper>getRule( BasicProcess.STARTUP_RULES_NAME );
    }
    
    @Override
    protected PropertyGroupWrapper createWorkingElements()
    {
        return m_templateProject.getDataGroup( BasicProcess.WORKING_ELEMENTS_NAME );
    }
    
    @Override
    protected ProductWrapper createProcess(String p_productName)
    {
        // We want to re-use the same product from the template
        //
        ProductWrapper product = m_templateProject.getProcess( BASIC_PROCESS_NAME );
        
        product.setName( p_productName );
        
        return product;
    }

    @Override
    protected PhaseWrapper createPhase(String p_phaseName)
    {
        PhaseWrapper phase = m_templateProject.getPhase( getProcess().getName() + "." + BASIC_PHASE_NAME );
        
        phase.setName( p_phaseName );
        
        getProcess().setInitialPhase( phase );
        
        return phase;
    }
    
    @Override
    protected RichHTMLPresentationProductWrapper createPresentationProcess()
    {
        return( (RichHTMLPresentationProductWrapper) m_templateProject.getPresentationProcess( getPresentationType(), getProcess() ) );
    }
    
    @Override
    protected RichHTMLPresentationPhaseWrapper createPresentationPhase()
    {
        return( (RichHTMLPresentationPhaseWrapper) m_templateProject.getPresentationPhase( getPresentationType(), getPhase() ) );    
    }

    /**
     * Adds the main heading.
     *
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_mainHeaderTranslations 
     */
    protected void addMainHeading( TextTranslations p_mainHeading, GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer )
    {
        RichHTMLPresentationQuestionWrapper presHeading = RichHelper.createHeading( getFormContext(), p_mainHeading, EHeaderLevel.HEADER_LEVEL1, m_languageMapHelper );
        
        // FIXME: Switch back when have changed builder to GenericLeafNodeWrapper
        //
        p_processContainer.addChild( (GenericLeafNode) presHeading.unwrap().getEntityNode() );

        p_presentationContainer.addChild( presHeading );
        
        presHeading.setDesignToUseFromEntityKey( "Version Heading" );
        
        p_presentationContainer.addChild( RichHelper.createBlankLine( getFormContext() ) );
    }
    
    
    protected void addMessageHeading(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer)
    {
        // Add a heading to display any message (such as committed status)
        //
        PropertyWrapper message = getTemplateProject().getDataItem( VersionServiceData.MESSAGE_DATA_ITEM );
        
        addHeading( TextTranslations.getUntranslatableText( "$$" + message.getPropertyKey( null ) + "$" ), p_processContainer, p_presentationContainer);
    }
    
    
    protected void addReadOnlyHeading(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer)
    {
        // Add a heading to display any message (such as committed status)
        //
        PropertyWrapper readOnly = getTemplateProject().getDataItem(VersionServiceData.VERSION_READ_ONLY_MODE_DATA_ITEM);
        addHeading( TextTranslations.getUntranslatableText( "ReadOnly_$$" + readOnly.getPropertyKey( null ) + "$" ), p_processContainer, p_presentationContainer);
    }

    /**
     * Adds a level 2 heading with T24 Translations.
     *
     * @param p_headingText the heading text
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_t24Translations the T24 Translations (nullable)
     * @return the presentation wrapper for the heading
     */
    protected RichHTMLPresentationQuestionWrapper addHeading(TextTranslations p_heading, GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer)
    {
        // FIXME: Add a pres heading wrapper
        //
        HeadingWrapper heading = HeadingWrapper.create( getFormContext(), p_heading.getText() ); 
        m_languageMapHelper.registerT24TextTranslations(heading, p_heading);
         
        heading.setHeaderLevel( EHeaderLevel.HEADER_LEVEL2 );
        
        p_processContainer.addChild( heading );

        RichHTMLPresentationQuestionWrapper presHeading = RichHTMLPresentationQuestionWrapper.create( getFormContext(), heading.unwrap() );
        
        if(p_heading.getText().startsWith("ReadOnly_")) {	
        	presHeading.setDesignToUseFromEntityKey( "Custom" );
        	presHeading.setHeadingStyle( "LargeFont FontDarkGrey ReadOnlyMode {padding-bottom:6px;display:none}" );
        }
        p_presentationContainer.addChild( presHeading );
        
        return(presHeading);
    }
    

    /**
     * Adds a blank line in the presentation.
     *
     * @param p_presentationContainer the presentation container
     * @return the presentation wrapper for the heading
     */
    protected PresentationSpacingWrapper addBlankLine(PresentationObjectWrapper<?> p_presentationContainer)
    {
        // Create the "Spacing with 1 Blank Line" Rich Presentation Spacing.
        //
        RichHTMLPresentationSpacingWrapper presSpacingWith1blankLine = RichHTMLPresentationSpacingWrapper.create( getFormContext() );

        p_presentationContainer.addChild( presSpacingWith1blankLine );
        
        return(presSpacingWith1blankLine);
    }

    /**
     * Add associated versions.
     *
     * @param p_mainVersionSection the main version section
     * @param p_associatedVersions the associated versions
     * @param p_tabDisplay the tab display
     * @param p_displayCondition if specified, then hides the associated version item group with the specified display condition (p_withinItemGroup must be true)
     * @param p_versionNumber the number of versions created so far, increment for each new associated version
     * @param p_withinItemGroup if true, will create each associated version within an item group
     * @return the latest p_versionNumber
     * @throws Exception the exception
     */
    protected int addAssociatedVersions(BasicVersionSection<?> p_mainVersionSection, EList<Version> p_associatedVersions, RichHelper.ETabDisplay p_tabDisplay, String p_displayCondition, int p_versionNumber, boolean p_withinItemGroup) throws Exception
    {
        if  ( p_associatedVersions == null || p_associatedVersions.size() == 0 )
            return p_versionNumber;
        
        if  ( StringUtils.isNotBlank( p_displayCondition ) && ! p_withinItemGroup )
            throw new IllegalArgumentException("You must use an item group with the display condition");
        
        RichLayoutManager layMan = p_mainVersionSection.getContainerLayoutManager();
        
        for (Version associatedVersion : p_associatedVersions)
        {
            String assocVersionName = VersionUtility.getVersionName( associatedVersion );
            
            if  ( assocVersionName != null )
            {
                ++p_versionNumber;
                
                LOGGER.info( "Associated Version = {} - number {}", assocVersionName, p_versionNumber );
    
                // Associated versions are currently referring to the same application, so use the same data store
                // as the version
                //
                TextTranslations assocVersionHeading = VersionUtility.getMainHeading( getMapper(), associatedVersion, true /* Needed for tab title, so compulsory */ );
                
                if  ( p_tabDisplay != null )
                {
                    // Create a tab for the associated version using the main layout manager as we maybe continuing another
                    // set of tabs set by the caller
                    //
                    layMan.addTab(assocVersionHeading, p_tabDisplay );
                }
                else
                {
                    layMan.addBlankLine();
                    
                    layMan.addLine();
                    
                    RichHTMLPresentationQuestionWrapper presHeading = layMan.addHeading( assocVersionHeading );
                    
                    HeadingWrapper heading = (HeadingWrapper) presHeading.getEntityNodeWrapper();
                    
                    heading.setHeaderLevel( EHeaderLevel.HEADER_LEVEL1 );
                    
                    layMan.addBlankLine();
                }
    
                if  ( p_withinItemGroup )
                {
                    // Create an item group to stuff the version in for readability and add a heading
                    //
                    RichHTMLPresentationItemGroupWrapper pig = layMan.addItemGroup(assocVersionName + " (" + (p_versionNumber) + ")" );
                    
                    if  ( p_displayCondition != null )
                    {
                        ItemGroupWrapper ig = pig.getEntityNodeWrapper();
                        
                        ig.setNotApplicable( true );
                        ig.setConditionExpression( p_displayCondition );
                    }
                }
                
                // Build the associated version 
                //
                buildAssociatedVersion( associatedVersion, p_mainVersionSection, p_versionNumber );
                
                // Set things up for the next set of fields
                //
                layMan.revertToInitialState();
            }
            else
            {
                LOGGER.error( "Associated Version is null!" );
            }
        }
        
        return p_versionNumber;
    }

    /**
     * Builds the associated version.
     *
     * @param p_associatedVersion the associated version
     * @param p_mainVersionSection the main version section
     * @param p_versionNumber the version number
     * @throws Exception the exception
     */
    protected void buildAssociatedVersion(Version p_associatedVersion, BasicVersionSection<?> p_mainVersionSection, int p_versionNumber ) throws Exception
    {
        BasicVersionSection<?> section = new AssociatedVersionSection( p_mainVersionSection, p_associatedVersion, p_mainVersionSection.getContainerLayoutManager() );
        
        section.build();
    }

    /**
     * @return the mapper
     */
    public MapperType getMapper()
    {
        return m_mapper;
    }
}
