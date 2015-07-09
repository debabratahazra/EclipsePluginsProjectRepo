package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.ProductWrapper;
import gen.com.acquire.intelligentforms.entities.RulesOnlyPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationProductWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.GotoRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;

import org.slf4j.Logger;

import com.acquire.intelligentforms.rules.ContainerRule;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.pattern.container.ProcessWithSetupRules;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.VersionMapper;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.CachedTemplateProject;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.t24.version.versionDSL.Version;
import com.temenos.connect.odata.utils.VersionServiceData;

/**
 * TODO: Document me!
 *
 * @author sakbar
 *
 */
public class BasicVersionScreenIn3D extends BasicVersionScreen
{
    public static final String _IN3D                                  = "_In3D";

    /** The Constant LOGGER with value of {@value}. */
    private static final Logger LOGGER                                = GenLogger.getLogger( BasicVersionScreenIn3D.class );

    /** The Constant PRODUCT_CONTAINER with value of {@value}. */
    private static final String PROCESS_CONTAINER_IN3D                = BasicVersionScreenIn3D.class.getName() + ":Process" + _IN3D;

    /** The Constant PRES_PRODUCT_CONTAINER with value of {@value}. */
    private static final String PRESENTATION_PROCESS_CONTAINER_IN3D   = BasicVersionScreenIn3D.class.getName() + ":PresentationProcess" + _IN3D;

    /** The Constant PHASE_CONTAINER with value of {@value}. */
    private static final String PHASE_CONTAINER_IN3D                  = BasicVersionScreenIn3D.class.getName() + ":Phase" + _IN3D;

    /** The Constant PRES_PHASE_CONTAINER with value of {@value}. */
    private static final String PRESENTATION_PHASE_CONTAINER_IN3D     = BasicVersionScreenIn3D.class.getName() + ":PresentationPhase" + _IN3D;

    /** The Constant SETUP_RULES_ONLY_PHASE_CONTAINER_IN3D with value of {@value}. */
    private static final String SETUP_RULES_ONLY_PHASE_CONTAINER_IN3D = BasicVersionScreenIn3D.class.getName() + ":SetupRulesOnlyPhase" + _IN3D;

    /** The Constant SETUP_RULES_CONTAINER_IN3D with value of {@value}. */
    private static final String SETUP_RULES_CONTAINER_IN3D            = BasicVersionScreenIn3D.class.getName() + ":SetupRules" + _IN3D;
    
    /**
     * Instantiates a new basic version screen and associated screens in 3D!
     *
     * @param p_versionName the version name
     * @param p_version the version
     * @param p_mapper the mapper
     * @param p_templateProject the template project
     * @param p_languageMapHelper the language map helper
     * @throws Exception the exception
     */
    public BasicVersionScreenIn3D(String p_versionName, Version p_version, VersionMapper p_mapper,
            TemplateProject p_templateProject, LanguageMapHelper p_languageMapHelper) throws Exception
    {
        super( p_versionName, p_version, p_mapper, p_templateProject, p_languageMapHelper );
    }

    @Override
    protected void buildBody(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer)
            throws Exception
    {
        // TextTranslations mainHeader = VersionUtility.getMainHeading( getMapper(), getVersion(), true /* needed for tabs */ );
        
        // Now set up the presentation
        //
        PresentationObjectWrapper<?> mainPresContainer = p_presentationContainer;

        // Commented out to reduce waste!
        //
        // addMainHeading(mainHeader, p_processContainer, mainPresContainer);
        
        RichLayoutManager layMan = new RichLayoutManager(getFormContext(), p_processContainer, mainPresContainer, m_languageMapHelper);
        
        @SuppressWarnings("unchecked")
        final BasicVersionSection<BasicVersionScreen> vsection = newVersionSection( getVersion(), layMan );
        
        final int columnCount = vsection.buildReturningNumberOfColumns();
        vsection.addOverrideGroup();
        
        // Create the Set Value rule to populate WorkingElements[1].ColumnCount with columnCount and add it to Rules Root as a top-level rule
        final SetValueRuleWrapper setColumnCountItemRule = createSetColumnCountItemRule(columnCount);
        getRulesRoot().addChild(setColumnCountItemRule);

        final ContainerRuleWrapper setupRulesContainerIn3D = getSetupRulesContainerIn3D();
        
        // Inject a link to setColumnCountItemRule as the 1st of setupRulesContainerIn3D's 'true' rule list
        setupRulesContainerIn3D.unwrap().addTrueRule(setColumnCountItemRule.unwrap(), 0, true /* p_linkedObject */);
        
        final ContainerRuleWrapper setupRule = VersionUtility.createSetupRuleIn3D(getMapper(), getFormContext(), getVersionName(), getVersion());
        setupRulesContainerIn3D.addTrueRule( setupRule );
        
        // Inject a link to setColumnCountItemRule as the 1st of the "Setup Rules" container's 'true' rule list
        getSetupRulesContainer().unwrap().addTrueRule(setColumnCountItemRule.unwrap(), 0, true /* p_linkedObject */);
    }
    
    /**
     * Creates a Set Value rule that populates Number-typed data item <code>WorkingElements[1].ColumnCount</code> (defined by BasicVersionScreenIn3DTemplate.ifp)
     * with the specified value.
     * 
     * @param   p_numberOfColumns   The number of presentation columns used by the IFP being generated for the Version.
     * 
     * @return  <code>SetValueRuleWrapper</code>
     */
    private SetValueRuleWrapper createSetColumnCountItemRule(int p_numberOfColumns)
    {
        final SetValueRuleWrapper setNumberOfColumnsItemRule = SetValueRuleWrapper.create(getFormContext());
        final String numberOfColumnsItemName = "ColumnCount";
        setNumberOfColumnsItemRule.setName("Set " + numberOfColumnsItemName);
        setNumberOfColumnsItemRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
        setNumberOfColumnsItemRule.setPropertyNameFromEntityKey("WorkingElements[1]." + numberOfColumnsItemName);
        setNumberOfColumnsItemRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
        setNumberOfColumnsItemRule.setFromValue(String.valueOf(p_numberOfColumns));
        
        return setNumberOfColumnsItemRule;
    }
    
    @Override
    protected BasicVersionSection newVersionSection(Version p_version, RichLayoutManager p_containerLayoutManager)
    {
        return new VersionSectionWithPrimaryKeyIn3D( this, p_version, p_containerLayoutManager );
    }
    
    @Override
    protected void addSetupRulesContainer()
    {
        ContainerRuleWrapper setupRulesIn3D = getSetupRulesOnlyPhaseIn3D().getChildWithName( SETUP_RULES_NAME + _IN3D, ContainerRule.class );
        
        if  ( setupRulesIn3D == null )
            throw new RuntimeException("Could not locate setup rules within setup phase");
        
        addContainer( SETUP_RULES_CONTAINER_IN3D, setupRulesIn3D );
        
        super.addSetupRulesContainer();
    }
    
    @Override
    protected void addDefaultSetupRules()
    {
        PhaseWrapper phaseIn3D = getPhaseIn3D();
        
        // Create the "Set Version Name" Set Value Rule.
        //
        SetValueRuleWrapper setVersionName = SetValueRuleWrapper.create( getFormContext() );

        setVersionName.setName( "Set Version Name" );

        setVersionName.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setVersionName.setPropertyNameFromEntityKey( VersionServiceData.VERSION_NAME_DATA_ITEM );

        setVersionName.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setVersionName.setFromValue( getVersionName() );

        getSetupRulesContainerIn3D().addTrueRule( setVersionName );
        
        GotoRuleWrapper gotoRule = GotoRuleWrapper.create( getFormContext()
                                                         , "Go to the main 3D phase: " + phaseIn3D.getName()
                                                         , GotoRuleWrapper.EOperationType.GO_FORWARD_TO_A_NAMED_PHASE
                                                         , phaseIn3D 
                                                         );
        
        getSetupRulesContainerIn3D().addTrueRule( gotoRule );
        
        super.addDefaultSetupRules();
    }
    
    @Override
    protected void addProcess(String p_productName)
    {
        ProductWrapper processIn3D = getTemplateProject().getProcess( BASIC_PROCESS_NAME + _IN3D );
        
        processIn3D.setName( p_productName + _IN3D );
        
        addContainer( PROCESS_CONTAINER_IN3D, processIn3D );
        
        // Now add the non 3D process
        //
        super.addProcess( p_productName );
    }
    
    @Override
    protected void addPhase(String p_phaseName)
    {
        PhaseWrapper phaseIn3D = getTemplateProject().getPhase( getProcessIn3D().getName() + "." + BASIC_PHASE_NAME + _IN3D );
        
        phaseIn3D.setName( p_phaseName + _IN3D );
        
        getProcessIn3D().setInitialPhase( phaseIn3D );
        
        addContainer( PHASE_CONTAINER_IN3D, phaseIn3D );
        
        // Now add the non 3D phase
        //
        super.addPhase( p_phaseName );
    }
    
    @Override
    protected void addPresentationProcess()
    {
        RichHTMLPresentationProductWrapper presProcessIn3D = (RichHTMLPresentationProductWrapper) getTemplateProject().getPresentationProcess( getPresentationType(), getProcessIn3D() ) ;
        
        addContainer( PRESENTATION_PROCESS_CONTAINER_IN3D, presProcessIn3D );
        
        super.addPresentationProcess();
    }
    
    @Override
    protected void addPresentationPhase()
    {
        RichHTMLPresentationPhaseWrapper presPhaseIn3D = (RichHTMLPresentationPhaseWrapper) getTemplateProject().getPresentationPhase( getPresentationType(), getPhaseIn3D() );
        
        addContainer( PRESENTATION_PHASE_CONTAINER_IN3D, presPhaseIn3D );
        
        super.addPresentationPhase();
    }
    
    @Override
    protected void addSetupRulesOnlyPhase()
    {
        RulesOnlyPhaseWrapper setupPhaseIn3D = (RulesOnlyPhaseWrapper) getTemplateProject().getPhase( getProcessIn3D().getName() + "." + ProcessWithSetupRules.SETUP_RULES_ONLY_PHASE_NAME + _IN3D );
        
        addContainer( SETUP_RULES_ONLY_PHASE_CONTAINER_IN3D, setupPhaseIn3D );
        
        getProcessIn3D().setInitialPhase( setupPhaseIn3D );
        
        super.addSetupRulesOnlyPhase();
    }
    
    public ProductWrapper getProcessIn3D()
    {
        return (ProductWrapper) ( getContainer( PROCESS_CONTAINER_IN3D ) );
    }

    public RichHTMLPresentationProductWrapper getPresentationProcessIn3D()
    {
        return (RichHTMLPresentationProductWrapper) getContainer( PRESENTATION_PROCESS_CONTAINER_IN3D );
    }

    public PhaseWrapper getPhaseIn3D()
    {
        return (PhaseWrapper) getContainer( PHASE_CONTAINER_IN3D );
    }

    public RichHTMLPresentationPhaseWrapper getPresentationPhaseIn3D()
    {
        return (RichHTMLPresentationPhaseWrapper) getContainer( PRESENTATION_PHASE_CONTAINER_IN3D );
    }

    public RulesOnlyPhaseWrapper getSetupRulesOnlyPhaseIn3D()
    {
        return (RulesOnlyPhaseWrapper) getContainer( SETUP_RULES_ONLY_PHASE_CONTAINER_IN3D );
    }
    
    public ContainerRuleWrapper getSetupRulesContainerIn3D()
    {
        return (ContainerRuleWrapper) getContainer( SETUP_RULES_CONTAINER_IN3D );
    }
    
    /**
     * Gets the default basic version template.
     *
     * @return the default template for a basic version
     */
    public static TemplateProject getDefaultBasicVersionTemplate()
    {
        return CachedTemplateProject.getTemplateProject ( "/templates/BasicVersionScreenIn3DTemplate.ifp"
                                                        , "/templates/Brand.ect" 
                                                        );
    }
}
