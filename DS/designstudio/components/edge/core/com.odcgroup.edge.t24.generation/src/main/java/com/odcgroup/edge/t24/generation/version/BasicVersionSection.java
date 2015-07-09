package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationMenuWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper.EColumnSizing;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.acquire.intelligentforms.data.util.DataUtility;
import com.acquire.intelligentforms.entities.FormList;
import com.acquire.intelligentforms.entities.GenericLeafNode;
import com.acquire.intelligentforms.entities.GenericNode;
import com.acquire.intelligentforms.entities.LinkedEntity;
import com.acquire.intelligentforms.entities.PropertyGroup;
import com.acquire.intelligentforms.entities.Question;
import com.acquire.intelligentforms.entities.presentation.PresentationButton;
import com.acquire.intelligentforms.entities.presentation.PresentationQuestion;
import com.acquire.intelligentforms.entities.presentation.PresentationTable;
import com.acquire.intelligentforms.entities.presentation.html.meta.HTMLAnswer;
import com.acquire.intelligentforms.entities.presentation.html.meta.HTMLTable;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreak;
import com.acquire.intelligentforms.entities.theme.Design;
import com.acquire.intelligentforms.entities.types.ListType;
import com.acquire.intelligentforms.entities.util.EntityUtility;
import com.acquire.intelligentforms.entities.util.IGenericNodeProcessor;
import com.acquire.intelligentforms.rules.Rule;
import com.acquire.util.ConvertUtility;
import com.acquire.util.IDebuggable.DebugLevel;
import com.acquire.util.StringUtils;
import com.edgeipk.builder.GenericEntityWrapper;
import com.edgeipk.builder.GenericLeafNodeWrapper;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.pattern.container.BasicSection;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.IFieldMapper.EFieldType;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.ApplicationUtility;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.IRISMvPropertyGroupWrapper;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.RichHelper.ESectionDisplay;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.util.RichFieldColumnLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.RichFieldLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.iris.rim.generation.ParameterParserResult;
import com.odcgroup.t24.version.versionDSL.Version;
import com.temenos.connect.T24Browser.BrowserUtility;
import com.temenos.connect.T24Browser.utils.IRISMvGroup;
import com.temenos.connect.T24Browser.utils.IRISMvGroup.EGroupType;
import com.temenos.connect.attributeHooks.ClassAttributeHook;
import com.temenos.connect.odata.serialiser.IRISSerialiser;
import com.temenos.connect.odata.utils.VersionServiceData;

/**
 * Used to build a section of a basic version screen for the fields specified.
 *
 * @author sakbar
 *
 */
public class BasicVersionSection<BasicProcessType extends AbstractBasicVersionScreen<?>> extends BasicSection<BasicProcessType>
{
    
    /** The Constant SERVICE_ROOT_URL with value of {@value}. */
    private static final String                                     SERVICE_ROOT_URL                      = "$$!IRIS_URL$";

    private static final Logger                                     LOGGER                                = GenLogger.getLogger( BasicVersionSection.class );

    private static final String                                     MULTI_VALUE_GROUP_TABLE_DESIGN        = "Multi Value Group Table";
    private static final String                                     MULTI_VALUE_GROUP_LINEAR_TABLE_DESIGN = "Multi Value Group Linear Table";

    /** The number of pixels to allocate per character .. increased to 10 so that nested tables have more room to expand (see AA payment schedules) */
    private static final int                                        PIXELS_PER_CHAR                       = 10;

    private static final int                                        NUMBER_OF_LANGUAGE_INSTANCES          = 25;

    /** The Constant VERSION_DATA_GROUP_CONTAINER with value of {@value}. */
    private static final String                                     VERSION_DATA_GROUP_CONTAINER          = BasicVersionScreen.class.getName() + ":VersionDataGroup";

    /** Name of the rule to nag (next action goad) to perform field validation */
    private static final String                                     NAG_FOR_HOT_FIELD                     = "Invoke Version-Field-Validate";

    /** Name of the rule to nag to perform full version validation */
    private static final String                                     NAG_FOR_HOT_VALIDATE                  = "Invoke Version-Validate";

    /** Name of the rule to nag for an enrichment update of a single property */
    private static final String                                     NAG_FOR_ENRICHMENT                    = "Invoke Version-Field-Enrichment";

    private final Version m_version;
    private final RichLayoutManager m_containerLayoutManager;
    private final String m_versionName;
    
    // TODO: Ideally variables in GenericEntityWrapper
    //
    private final Map<GenericEntityWrapper<?>, IVersionFieldMapper> m_wrapperToMapper = new HashMap<GenericEntityWrapper<?>, IVersionFieldMapper>();

    public BasicVersionSection(BasicProcessType p_process, Version p_version, RichLayoutManager p_containerLayoutManager)
    {
        super(p_process);

        VersionUtility.validateVersionObject( p_version );

        m_version = p_version;
        m_versionName = VersionUtility.getVersionName( p_version );
        m_containerLayoutManager = p_containerLayoutManager;
    }

    @Override
    public void build() throws Exception
    {
        buildReturningNumberOfColumns();
    }
    
    public int buildReturningNumberOfColumns() throws Exception
    {
        super.build();
        
        // Store version details for runtime use
        //
        VersionUtility.storeVersionDetails( getBasicProcess().getProject().unwrap(), getVersion(), getBasicProcess().getMapper().getMapperData() );
        
        // Add the version data group
        //
        PropertyGroupWrapper versionGroup = addVersionDataGroup();

        // Add all of the fields in a column format
        //
        List<IVersionFieldMapper> fields = buildVersionFields();

        RichFieldColumnLayoutManager flcm = new RichFieldColumnLayoutManager( getContainerLayoutManager(), fields );
        
        addFields( fields, versionGroup, flcm );
        
        return flcm.getNumberOfColumns();
    }

    protected List<IVersionFieldMapper> buildVersionFields()
    {
        List<IVersionFieldMapper> fields = new ArrayList<IVersionFieldMapper>(); 

        VersionUtility.getPrimaryKeyAndSortedFields( getBasicProcess().getMapper(), getVersion(), fields );
        
        return fields;
    }

    /**
     * Add the missing override group .. this should be invoked at the end after all possible data groups have been generated.
     */
    public void addOverrideGroup()
    {
        VersionUtility.addMissingOverridesDataGroup(getFormContext(), getVersionDataGroup() );
    }
    
    /**
     * Gets the version.
     *
     * @return the version
     */
    public Version getVersion()
    {
        return m_version;
    }
    
    /**
     * Gets the version name.
     *
     * @return the version name
     */
    public String getVersionName()
    {
        return m_versionName;
    }

    /**
     * Gets the language map helper.
     *
     * @return the language map helper
     */
    public LanguageMapHelper getLanguageMapHelper()
    {
        return getBasicProcess().getLanguageMapHelper();
    }

    /**
     * Gets the working elements.
     *
     * @return the working elements
     */
    public PropertyGroupWrapper getWorkingElements()
    {
        return getBasicProcess().getWorkingElements();
    }

    /**
     * Gets the version data group.
     *
     * @return the version data group
     */
    public PropertyGroupWrapper getVersionDataGroup()
    {
        return( (PropertyGroupWrapper) getContainer( VERSION_DATA_GROUP_CONTAINER ) );
    }

    /**
     * Adds the version data group, including the nested error group.
     *
     * @return the version group
     */
    protected PropertyGroupWrapper addVersionDataGroup()
    {
        PropertyGroupWrapper versionGroup = createVersionDataGroup();

        // Setup any attributes and stick it in as a container
        //
        BrowserUtility.setBasicBrowserAttributes(versionGroup.unwrap(), getVersion().getT24Name(), getVersion().getShortName() );
        
        //BrowserUtility.setBasicBrowserAttributes(versionGroup.unwrap(), getVersion().getT24Name(), getVersion().getShortName(),SHOW_NULL_FIELDS_ATTR,VersionUtility.hasShowNullFieldsAttribute(getVersion()) );
        addContainer( VERSION_DATA_GROUP_CONTAINER, versionGroup );
        
        return versionGroup;
    }
    

    /**
     * Creates the version data group at the top, with default error group.
     *
     * @return the version group
     */
    protected PropertyGroupWrapper createVersionDataGroup()
    {
        PropertyGroupWrapper versionGroup = PropertyGroupWrapper.create( getFormContext(), getVersionName() );

        getBasicProcess().getProject().getDataStoreRoot().addChild( versionGroup );
        
        VersionUtility.addErrorDataGroup( getFormContext(), versionGroup );
        
        return versionGroup;
    }

    /**
     * Add the version fields.
     *
     * @param p_dataContainer the data container
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     */
    protected void addFields(List<IVersionFieldMapper> p_versionFields, PropertyGroupWrapper p_dataContainer, RichFieldLayoutManager p_fieldLayoutManager)
    {
        for (IVersionFieldMapper field : p_versionFields)
        {
            try
            {
                if  ( field.isProcessable() )
                {
                    addField( field, p_dataContainer, p_fieldLayoutManager );
                }
            }
            catch (Exception p_ex)
            {
                String details = p_ex + " -> " + field.getDetails( DebugLevel.HIGH );
                
                LOGGER.error( "Error processing field: {}", details, p_ex );
            }
        }
    }
    
    /**
     * Add the field to the screen.
     * 
     * @param p_field the field
     * @param p_dataContainer the data container
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     */
    protected void addField(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, RichFieldLayoutManager p_fieldLayoutManager)
    {
        LOGGER.info( "VersionField = {} ProcessField = {}", new Object[] { p_field.getName(), p_field.getProcessedName() } );

        // Let the layout manager know we're about to process this field
        //
        p_fieldLayoutManager.setCurrentField(p_field);

        processPropertyOrGroup( p_field,
                                p_dataContainer,
                                p_fieldLayoutManager,
                                p_field.getName() );
    }
    

    /**
     * Adds a heading or blank line.
     *
     * @param p_field the field
     * @param p_fieldLayoutManager the field layout manager
     * @param p_logFieldDetails the log field details
     */
    protected void addAsteriskField(IVersionFieldMapper p_field, RichFieldLayoutManager p_fieldLayoutManager, String p_logFieldDetails)
    {
        TextTranslations headingText = p_field.getLabelText(getBasicProcess().getMapper());
        
        if  ( headingText != null && StringUtils.isNotBlank( headingText.getText() ) )
        {
            // NB: Text asterisk are being ignored now as they contain "junk" that's not appropriate for u to use,
            // such as heading for character columns below them (expected to have spaced exactly)
            //
            // See com.odcgroup.edge.t24.generation.version.util.VersionUtility.getPrimaryKeyAndSortedFields(Version, List<IVersionFieldMapper>)
            //
            RichHTMLPresentationQuestionWrapper heading = p_fieldLayoutManager.addHeading( headingText );
            
            heading.setHeaderLevel( RichHTMLPresentationQuestionWrapper.EHeaderLevel.HEADER_LEVEL6 );
            
            heading.setDesignToUseFromEntityKey( "Asterisk Heading" );
        }
        else
        {
            p_fieldLayoutManager.addBlankLine();
        }
    }

    /**
     * Process property or group fields that are {@link IVersionFieldMapper#isProcessable()}
     *
     * @param p_field the property or group field
     * @param p_dataContainer the data container
     * @param p_workingDataContainer the working data container
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_logFieldDetails the log field details
     */
    private void processPropertyOrGroup(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, RichFieldLayoutManager p_fieldLayoutManager,
            String p_logFieldDetails)
    {
        if  ( ! p_field.isProcessable() )
        {
            LOGGER.error( "Internal error .. should not be processing this field: \"{}\"", p_logFieldDetails );
        }
        else if ( p_field instanceof AsteriskFieldMapper )
        {
            addAsteriskField(p_field, p_fieldLayoutManager, p_field.getProcessedName() );
        }
        else if ( p_field.isField() )
        {
            // Create Data Item & Question
            //
            processProperty( p_field,
                             p_dataContainer,
                             p_fieldLayoutManager,
                             p_logFieldDetails);
        }
        else if ( p_field.isGroup() )
        {
            // Create a group
            //
            processGroup( p_field,
                          p_dataContainer,
                          p_fieldLayoutManager,
                          p_logFieldDetails );
        }
        else
        {
            LOGGER.error( "Could not get mapping type for \"{}\"", p_logFieldDetails );
        }
    }

    /**
     * Process a group field, which may involve generating data groups/tables etc.
     *
     * @param p_field the field
     * @param p_dataContainer the data container
     * @param p_workingDataContainer the working data container
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_logFieldDetails the log field details
     */
    private void processGroup(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, RichFieldLayoutManager p_fieldLayoutManager, String p_logFieldDetails)
    {
        List<IVersionFieldMapper> groupInputFields = p_field.getGroupInputFields();
        
        if  ( groupInputFields.size() == 0 )
        {
            throw new RuntimeException("Group has no fields: " + p_field.getDetails());
        }
        
        IVersionFieldMapper firstField = groupInputFields.get( 0 );
        
    	if  (  firstField.getFieldType() == EFieldType.MULTI_LANGUAGE_GROUP 
    	    || firstField.getFieldType() == EFieldType.TEXT_AREA_GROUP
    	    )
        {
    		// For single Multi-Language fields generate a single data item and NOW also do the same for multi-value multi-language fields
    	    // as that simplifies the serialiser logic greatly and can't think of a single reason not to!
    	    // 
    	    // Also do the same for MV/SV groups that need to display as text areas
    	    //
            processProperty( firstField, p_dataContainer, p_fieldLayoutManager, p_field.getName() );
        } 
    	else 
    	{
            // Create the data group, currently this will either be an MV Group under the version data group or a sub value group
            //
            EGroupType groupType = BrowserUtility.isDataMVGroup( (PropertyGroup) p_dataContainer.unwrap().getParent() ) ? IRISMvGroup.EGroupType.SV_GROUP : IRISMvGroup.EGroupType.MV_GROUP;
                    
            String baseGroupName = getIRISGroupName( p_field, groupType );
    
            IRISMvPropertyGroupWrapper irisDataItemGroup = IRISMvPropertyGroupWrapper.create( getFormContext(), baseGroupName, p_field.getT24Name(), p_field.getName(), groupType );
            
            p_dataContainer.addChild( irisDataItemGroup );
    
            BrowserUtility.setFullAppName(irisDataItemGroup.unwrap());
            
            // The repeating group for an mv group is the element group .. also the place where all properties
            // are to be added, so use that from now on
            //
            PropertyGroupWrapper dataItemGroup = irisDataItemGroup.getElementGroup();
            
            // Set the multiplicity
            //
            int multiplicity = p_field.getGroupMultiplicity();
            
            if ( multiplicity < 1 )
            {
                dataItemGroup.setFixedSize( false );
            }
            else
            {
                dataItemGroup.setMaxInstances( multiplicity );
            }
   
            // Generate a table or sub-table for the group 
            //
            addGroupTable( p_field,
                           dataItemGroup,
                           p_fieldLayoutManager,
                           p_logFieldDetails );
        }
    }

    /**
     * Gets the group name for an MV/SV version as needed by IRIS.
     *
     * @param p_field the field
     * @param p_groupType the group type
     * @return the version group name
     */
    protected String getIRISGroupName(IVersionFieldMapper p_field, EGroupType p_groupType)
    {
        return getVersionName() + "_" + p_field.getProcessedName();
    }

    private enum MVTableType 
    {
        GRID(MULTI_VALUE_GROUP_TABLE_DESIGN, true, true),                       // Standard table
        COLLAPSIBLE_SUB_GRID("Custom", true, false),                            // Collapsible table for SV (within a grid table for the MV)
        SIMPLE_LINEAR(MULTI_VALUE_GROUP_LINEAR_TABLE_DESIGN, false, false),     // Linear table for single question with text/answer/buttons on same 'row', looking like a question
        COMPLEX_LINEAR(MULTI_VALUE_GROUP_LINEAR_TABLE_DESIGN, false, true);     // Linear table containing multiple questions/SV groups, with overall add new row button and add/ins BEFORE each row
        
        private final String m_tableDesign;
        private final boolean m_useOptionsMenu;
        private final boolean m_addSpacing;

        private MVTableType(String p_tableDesign, boolean p_useOptionsMenu, boolean p_addSpacing)
        {
            m_tableDesign = p_tableDesign;
            m_useOptionsMenu = p_useOptionsMenu;
            m_addSpacing = p_addSpacing;
        }

        /**
         * Gets the table design.
         *
         * @return the tableDesign
         */
        public String getTableDesign()
        {
            return m_tableDesign;
        }

        /**
         * Indicates if we're using an options menu, instead of buttons.
         *
         * @return true, if should
         */
        public boolean useOptionsMenu()
        {
            return m_useOptionsMenu;
        }

        /**
         * Indicates if we should adds spacing (blank lines above and below table).
         *
         * @return true, if should add spacing
         */
        public boolean addSpacing()
        {
            return m_addSpacing;
        }
    }

    /**
     * Adds the group table.
     *
     * @param p_field the field
     * @param p_dataContainer the data container
     * @param p_workingDataContainer the working data container
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_logFieldDetails the log field details
     */
    protected void addGroupTable(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, RichFieldLayoutManager p_fieldLayoutManager, String p_logFieldDetails)
    {
        FormTableWrapper table = null;
        RichHTMLPresentationTableWrapper presTable = null;
        RichHTMLPresentationButtonWrapper insertAfterRowButton = null, deleteRowButton = null;
        RichHTMLPresentationMenuWrapper optionsMenu = null;
        
        List<IVersionFieldMapper> groupInputFields = p_field.getGroupInputFields();

        boolean hasEditableField            = false;
        int displayedFields                 = 0;
        int processableSubGroups            = 0;
        int rowNumber                       = -1;
        boolean sameRow                     = true;
        Boolean hasSingleSubMultiLangField  = null;
        boolean canBeExpanded               = false;
        
        List<IVersionFieldMapper> subGroupInputFields = null;
        
        for (IVersionFieldMapper field : groupInputFields)
        {
            if  ( field.isProcessable() )
            {
                if  ( field.isField() && field.isDisplayed() )
                {
                    canBeExpanded |= field.isExpandable();
                    
                    displayedFields++;
                    
                    hasEditableField |= !field.isReadOnly();
                    
                    if  ( sameRow )
                    {
                        if  ( rowNumber == -1 )
                        {
                            rowNumber = field.getRow();
                        }
                        else if ( rowNumber != field.getRow() )
                        {
                            sameRow = false;
                        }
                    }
                }
                else if ( field.isGroup() )
                {
                    processableSubGroups++;
                    
                    if  ( sameRow )
                    {
                        subGroupInputFields = field.getGroupInputFields();
                        
                        for (IVersionFieldMapper subField : subGroupInputFields)
                        {
                            if  ( subField.isProcessable() && subField.isDisplayed() )
                            {
                                if  ( subField.getFieldType() == EFieldType.MULTI_LANGUAGE_GROUP && hasSingleSubMultiLangField == null )
                                {
                                    hasSingleSubMultiLangField = Boolean.TRUE;
                                }
                                else
                                {
                                    hasSingleSubMultiLangField = Boolean.FALSE;
                                }
                                
                                if  ( rowNumber == -1 )
                                {
                                    rowNumber = subField.getRow();
                                }
                                else if ( rowNumber != subField.getRow() )
                                {
                                    sameRow = false;
                                    break;
                                }  
                            }
                        }
                    }
                }
                else
                {
                    LOGGER.error( "Field is neither a field or group: {} for parent {}", field.getDetails(), p_field.getDetails() );
                }
            }
        }
        
        if  ( displayedFields == 0 && processableSubGroups == 0 )
        {
            LOGGER.warn( "addGroupTable: No displayed items for {}", p_logFieldDetails );
            return;
        }
        
        PresentationObjectWrapper<?> currentPresentationContainer = p_fieldLayoutManager.getCurrentPresentationContainer();
        GenericNodeWrapper<?> currentProcessContainer = p_fieldLayoutManager.getCurrentProcessContainer();

        boolean isWithinGridTable =     currentPresentationContainer instanceof PresentationTableWrapper 
                                        &&  MVTableType.GRID.getTableDesign().equals( currentPresentationContainer.unwrap().getAttribute(Design.ATTR_DESIGN_TO_USE) )
                                        ;
        
        // Work out the table type we'll need
        //
        MVTableType tableType = isWithinGridTable   ? MVTableType.COLLAPSIBLE_SUB_GRID
                                                    : processableSubGroups > 0  ? sameRow               ? MVTableType.GRID 
                                                                                                        : MVTableType.COMPLEX_LINEAR 
                                                                                : displayedFields == 1      ? MVTableType.SIMPLE_LINEAR 
                                                                                                            : sameRow  ? MVTableType.GRID
                                                                                                                       : MVTableType.COMPLEX_LINEAR
                                                                                                            ;
        
        
        if  ( displayedFields == 0 )
        {
            if  ( hasSingleSubMultiLangField == Boolean.TRUE )
            {
                tableType        = MVTableType.SIMPLE_LINEAR;
                canBeExpanded    = true;
                hasEditableField = true;
                displayedFields  = 1;
                groupInputFields = subGroupInputFields;
            }
            else
            {
                // Just generate the SV table as otherwise we would end up with an empty MV table
                //
                for (IVersionFieldMapper field : groupInputFields)
                {
                    if  ( field.isProcessable() )
                    {
                        processPropertyOrGroup( field,
                                                p_dataContainer,
                                                p_fieldLayoutManager,
                                                p_field.getName() + "->" + field.getName() );
                    }
                }
                
                return;
            }
        }
        
        TextTranslations labelText = VersionUtility.getLabel( getBasicProcess().getMapper(), p_field, true );

        RichHTMLPresentationFormatBreakWrapper overflowSection = null;
        
        if ( tableType == MVTableType.GRID )
        {
            overflowSection = RichHelper.createSection( getFormContext(), ESectionDisplay.STANDARD_SECTION );

            // Set No Layout so that read-only tables expand across all of the way. 
            //
            overflowSection.setSetQuestionGrid( true );

            overflowSection.setGridSize( RichHTMLPresentationFormatBreakWrapper.EGridSize.NO_LAYOUT );

            RichHelper.setHorizontalOverflow(overflowSection);
            
            p_fieldLayoutManager.addPresentationChild( overflowSection );
            
            p_fieldLayoutManager.setCurrentPresentationContainer( overflowSection );
        }
        
        if  ( tableType.addSpacing() && p_field.getPreviousField() != null )
        {
            p_fieldLayoutManager.addBlankLine();
        }

        try
        {
            presTable       = p_fieldLayoutManager.addTable(labelText);
            table           = presTable.getEntityNodeWrapper();
   
            setupProcessTable       ( p_field, p_dataContainer, table, tableType );
            setupPresentationTable  ( p_field, presTable, tableType );

            ContainerRuleWrapper insertRowAfterCurrentRow   = null;
            ContainerRuleWrapper deleteCurrentRow           = null;
            
            if  ( canBeExpanded )
            {
                insertRowAfterCurrentRow = VersionUtility.createInsertAfterCurrentRowRule( getFormContext(), table, p_dataContainer );
                deleteCurrentRow         = VersionUtility.createDeleteRowRule( getFormContext(), table, p_dataContainer );
                
                // Use root rules and then link them in, to avoid the Copy suffix
                //
                getBasicProcess().getRulesRoot().addChild( insertRowAfterCurrentRow );
                getBasicProcess().getRulesRoot().addChild( deleteCurrentRow );
                
                optionsMenu             = VersionUtility.createOptionsMenu( getFormContext(), table, p_dataContainer, insertRowAfterCurrentRow, deleteCurrentRow);
                insertAfterRowButton    = VersionUtility.createInsertAfterCurrentRowButton( getFormContext(), insertRowAfterCurrentRow, getLanguageMapHelper(), labelText );
                deleteRowButton         = VersionUtility.createDeleteRowButton( getFormContext(), table, p_dataContainer, deleteCurrentRow, getLanguageMapHelper(), labelText, false, true );
            }
   
            // Make the +- MV buttons hidden when in read only mode
            //optionsMenu.setHideField( true );
            //optionsMenu.setConditionExpression( "$$" + VersionServiceData.VERSION_READ_ONLY_MODE_DATA_ITEM + "$ != 'YES'" );
            
            // Don't allow manipulation of MV groups that have no editable fields
            //
            if  ( ! hasEditableField )
            {
                // TODO: If will have dynamic read-only, then create a data item to show/hide the buttons (or use the DI values)
                // otherwise delete these buttons
                //
                // Connect BUG if hidden .. if ( optionsMenu != null ) optionsMenu.setHideField( true );
                if ( insertAfterRowButton != null )  insertAfterRowButton.setHideField( true );
                if ( deleteRowButton != null )  deleteRowButton.setHideField( true );
            }
   
            // Use child layout managers, mainly for complex linear tables so they can produce multi-column layouts
            //
            RichFieldLayoutManager tableFieldLayoutManager = null;
            
            switch (tableType)
            {
                case SIMPLE_LINEAR:
                    
                    tableFieldLayoutManager = preProcessSimpleLinearTable( presTable, p_fieldLayoutManager, groupInputFields );
                    
                    break;
                    
                case COMPLEX_LINEAR:
                    
                    tableFieldLayoutManager = preProcessComplexLinearTable( tableType, p_dataContainer, table, presTable, optionsMenu, insertAfterRowButton, deleteRowButton, labelText, p_fieldLayoutManager, groupInputFields );

                    break;
                    
                case GRID:
                case COLLAPSIBLE_SUB_GRID:

                    tableFieldLayoutManager = preProcessGridTable( presTable, p_fieldLayoutManager, groupInputFields );
                    
                    break;
            }
                    
            for (IVersionFieldMapper field : groupInputFields)
            {
                if  ( field.isProcessable() )
                {
                	// Let the layout manager know we're about to process this field
                    //
                    tableFieldLayoutManager.setCurrentField(field);

                    processPropertyOrGroup( field,
                                            p_dataContainer,
                                            tableFieldLayoutManager,
                                            p_field.getName() + "->" + field.getName() );
                }
            }
            
            switch (tableType)
            {
                case SIMPLE_LINEAR:
                    
                    postProcessSimpleLinearTable( tableType, p_dataContainer, table, presTable, tableFieldLayoutManager, optionsMenu, insertAfterRowButton, deleteRowButton );
                    
                    break;

                case COMPLEX_LINEAR:
                    
                    postProcessComplexLinearTable( tableType, p_dataContainer, table, presTable, tableFieldLayoutManager, optionsMenu, insertAfterRowButton, deleteRowButton, labelText );

                    break;

                case GRID:
                case COLLAPSIBLE_SUB_GRID:
                    
                    // So the columns are not too spaced out, lets use fixed pixel widths columns
                    //
                    // NB: This assumes the user has sensibly put the same mv or sv fields on the same row
                    //
                    // Perhaps though, each 'table' field needs to be treated separately as if it was a simple linear table?
                    //
                    List<Integer> charLengths = estimateColumnCharLengths( tableType, presTable, new ArrayList<Integer>() );

                    String colWidths = VersionUtility.calcColWidthsInPixels(charLengths, PIXELS_PER_CHAR, -1);
       
                    postProcessGridTable( tableType, tableFieldLayoutManager, presTable, colWidths, optionsMenu, insertAfterRowButton, deleteRowButton );
                    
                    break;
            }
            
        }
        finally
        {
            p_fieldLayoutManager.setCurrentProcessContainer( currentProcessContainer );
            p_fieldLayoutManager.setCurrentPresentationContainer( currentPresentationContainer );
        }
        
        if  ( tableType.addSpacing() )
        {
            p_fieldLayoutManager.addBlankLine();
        }
        
        if  ( tableType == MVTableType.GRID || tableType == MVTableType.COMPLEX_LINEAR )
        {
            p_fieldLayoutManager.expandFieldSpace();
        }
        
    }

    protected RichFieldLayoutManager preProcessSimpleLinearTable(RichHTMLPresentationTableWrapper p_presTable, RichFieldLayoutManager p_fieldLayoutManager, List<IVersionFieldMapper> p_groupInputFields)
    {
        // Add a section so we can align the question with questions outside the linear table .. also
        // seems to fix some issues with ajax/rows added (e.g. if Phone Number Off / Fax on same row)
        //
        RichHTMLPresentationFormatBreakWrapper presSection = RichHelper.createSection(getFormContext(), ESectionDisplay.STANDARD_SECTION);
        
// DON'T set question grid now as we need to inherit as we're relying on pixels to align the text and fields closer. Also, we don't seem to have the adverse effect
// as before where it wasn't aligning correctly
        
//        presSection.setSetQuestionGrid( true );
//
//        // Same as normal padding .. otherwise without this section, we'd get 4px padding against the extra div we have
//        //
//        presSection.setCellPadding( new BigDecimal(2) ); 
//        
        p_presTable.addChild( presSection );
        
        p_fieldLayoutManager.setCurrentPresentationContainer( presSection );
        
        return new RichFieldLayoutManager( p_fieldLayoutManager, p_groupInputFields );
    }
    
    protected void postProcessSimpleLinearTable(MVTableType p_tableType, PropertyGroupWrapper p_dataContainer, FormTableWrapper p_table,
            RichHTMLPresentationTableWrapper p_presTable, RichFieldLayoutManager p_fieldLayoutManager, RichHTMLPresentationMenuWrapper p_optionsMenu, RichHTMLPresentationButtonWrapper p_insertRowButton,
            RichHTMLPresentationButtonWrapper p_deleteRowButton)
    {
        if  ( p_optionsMenu == null && p_insertRowButton == null )
        {
            // Cannot be expanded
            //
            return;
        }
        
        // We have a question followed by 2 buttons (ins/del) so force a linear table and group the buttons with the question
        //
        if  ( p_tableType.useOptionsMenu() && p_optionsMenu != null )
        {
            p_fieldLayoutManager.addProcessChild( p_optionsMenu.getEntityNodeWrapper() );
            p_fieldLayoutManager.addPresentationChild( p_optionsMenu );
        }
        else if ( p_insertRowButton != null && p_deleteRowButton != null )
        {
            p_fieldLayoutManager.addProcessChild( p_insertRowButton.getEntityNodeWrapper() );
            p_fieldLayoutManager.addPresentationChild( p_insertRowButton );
    
            p_fieldLayoutManager.addProcessChild( p_deleteRowButton.getEntityNodeWrapper() );
            p_fieldLayoutManager.addPresentationChild( p_deleteRowButton );
    
            p_insertRowButton.setGroupWithPrevious( true );
            p_insertRowButton.setSpacingToPrevious( 0 );
    
            p_deleteRowButton.setGroupWithPrevious( true );
            p_deleteRowButton.setSpacingToPrevious( 0 );
        }

        // Update the question to reflect the instances (same as browser)
        //
        Question firstQuestion = (Question) p_table.unwrap().getFirstChildOfType( Question.class, false );
        
        if  ( firstQuestion != null  )
        {
            QuestionWrapper question = QuestionWrapper.wrap( firstQuestion );
    
            StringBuilder questionText = new StringBuilder(question.unwrap().getQuestionText());
            
            questionText.append(".$$").append(p_dataContainer.getEntityKeyName()).append(".instance()$");
            
            question.setQuestionText( questionText.toString() );
        }
        else
        {
            LOGGER.error( "Empty table (no questions) generated {} ", p_table.getDetails( DebugLevel.HIGH ) );
        }
    }

    protected RichFieldLayoutManager preProcessComplexLinearTable(MVTableType p_tableType, PropertyGroupWrapper p_dataContainer, FormTableWrapper p_table,
            RichHTMLPresentationTableWrapper p_presTable, RichHTMLPresentationMenuWrapper p_optionsMenu, RichHTMLPresentationButtonWrapper p_insertAfterRowButton, RichHTMLPresentationButtonWrapper p_deleteRowButton, TextTranslations p_title, RichFieldLayoutManager p_fieldLayoutManager, List<IVersionFieldMapper> p_groupInputFields)
    {
        // Added another outer section to contain all other sections as a linear table should not have multiple child sections
        //
        RichHTMLPresentationFormatBreakWrapper tableSection = RichHelper.createSection( getFormContext(), ESectionDisplay.STANDARD_SECTION );

        RichHTMLPresentationFormatBreakWrapper contentBlock = RichHelper.createSection( getFormContext(), ESectionDisplay.CONTENT_BLOCK_SECTION );
        
        tableSection.addChild( contentBlock );
        
        p_presTable.addChild(tableSection);
        
        if  ( p_tableType.useOptionsMenu() && p_optionsMenu != null )
        {
            // Insert options at the top
            //
            p_table.addChild( p_optionsMenu );
            tableSection.addChild( p_optionsMenu );
        }
        else if ( p_insertAfterRowButton != null && p_deleteRowButton != null )
        {
            p_table.addChild( (GenericLeafNode) p_insertAfterRowButton.unwrap().getEntityNode() );
            contentBlock.addChild( p_insertAfterRowButton );
            
            p_table.addChild( (GenericLeafNode) p_deleteRowButton.unwrap().getEntityNode() );
            contentBlock.addChild( p_deleteRowButton );
    
            // Group the buttons next to the heading (Q. What heading .. has that disappeared now?)
            //
            p_insertAfterRowButton.setGroupWithPrevious( true );
            p_insertAfterRowButton.setSpacingToPrevious( 1 );
            
            p_deleteRowButton.setGroupWithPrevious( true );
            p_deleteRowButton.setSpacingToPrevious( 1 );
        }
        
        p_fieldLayoutManager.setCurrentPresentationContainer( contentBlock );

        return new RichFieldColumnLayoutManager( p_fieldLayoutManager, p_groupInputFields );
    }
    
    protected void postProcessComplexLinearTable(MVTableType p_tableType, PropertyGroupWrapper p_dataContainer, FormTableWrapper p_table, RichHTMLPresentationTableWrapper p_presTable, RichFieldLayoutManager p_fieldLayoutManager, RichHTMLPresentationMenuWrapper p_optionsMenu, RichHTMLPresentationButtonWrapper p_insertAfterRowButton, RichHTMLPresentationButtonWrapper p_deleteRowButton, TextTranslations p_labelText)
    {
        if  ( p_presTable.unwrap().getParent() instanceof RichHTMLPresentationColumnBreak )
        {
            // Look for any multi column children, if there aren't any, then we can make the parent column break set the width automatically so we can collapse
            // unnecessary space
            //
            Boolean foundMultiSiblingColumns = (Boolean) EntityUtility.processChildrenInTreeOrder( p_presTable.unwrap(), new IGenericNodeProcessor()
            {
                private boolean m_foundMultiSiblingColumns = false;
                
                @Override
                public int process(GenericLeafNode p_child, LinkedEntity p_linkedEntity, GenericNode p_parent, Object p_userObject)
                {
                    if  ( p_child instanceof RichHTMLPresentationColumnBreak && p_parent.getNextSibling( p_child ) instanceof RichHTMLPresentationColumnBreak )
                    {
                        m_foundMultiSiblingColumns = true;
                        
                        return IGenericNodeProcessor.FINISHED_PROCESSING;
                    }
                    
                    return IGenericNodeProcessor.CONTINUE_PROCESSING;
                }
                
                @Override
                public Object getResult()
                {
                    return Boolean.valueOf( m_foundMultiSiblingColumns );
                }
            } );
            
            if  ( ! foundMultiSiblingColumns )
            {
                RichHTMLPresentationColumnBreakWrapper colBreak = RichHTMLPresentationColumnBreakWrapper.wrap( (RichHTMLPresentationColumnBreak) p_presTable.unwrap().getParent() );
                
                colBreak.setSectionStyle( "{ width: auto !important }" );
            }
        }
        
    }

    protected RichFieldLayoutManager preProcessGridTable(RichHTMLPresentationTableWrapper p_presTable, RichFieldLayoutManager p_fieldLayoutManager,
            List<IVersionFieldMapper> p_groupInputFields)
    {
        RichFieldLayoutManager tableFieldLayoutManager;
        p_fieldLayoutManager.setCurrentPresentationContainer( p_presTable );
        
        tableFieldLayoutManager = new RichFieldLayoutManager( p_fieldLayoutManager, p_groupInputFields );
        return tableFieldLayoutManager;
    }

    protected void postProcessGridTable(MVTableType p_tableType, RichFieldLayoutManager p_fieldLayoutManager, RichHTMLPresentationTableWrapper p_presTable,
            String p_colWidths, RichHTMLPresentationMenuWrapper p_optionsMenu, RichHTMLPresentationButtonWrapper p_insertAfterRowButton, RichHTMLPresentationButtonWrapper p_deleteRowButton)
    {
        // We now need to adjust those question that need to be right adjusted. 
        //
        List<GenericLeafNode> childrenList = p_presTable.unwrap().getChildrenList();
        
        for (GenericLeafNode child : childrenList)
        {
            GenericEntityWrapper<?> wrapper = GenericEntityWrapper.getWrapper( child );
           
            // Currently only generating questions .. maybe later we'll need to handle headings & buttons with column headings
            //
            if  ( wrapper instanceof RichHTMLPresentationQuestionWrapper )
            {
                IVersionFieldMapper mapper = m_wrapperToMapper.get( wrapper );

                if  ( mapper != null && mapper.getRightAdjust() )
                {
                    // Unfortunately there doesn't seem a way to inherit the design and just change the justification,
                    // so we'll have to change them to custom and set the justification
                    //
                    RichHelper.switchToCustomMVTableQuestion((RichHTMLPresentationQuestionWrapper)wrapper);
                    
                    ((RichHTMLPresentationQuestionWrapper)wrapper).setJustification( RichHTMLPresentationQuestionWrapper.EJustification.RIGHT );

                    ((RichHTMLPresentationQuestionWrapper)wrapper).setAnswerJustification( RichHTMLPresentationQuestionWrapper.EAnswerJustification.RIGHT );
                }
            }
        }
        
        // Always specify fixed columns so that nested tables will expand correctly
        //
        p_presTable.setColumnSizing( EColumnSizing.SPECIFY_IN_PIXELS_PX_ );
        
        p_presTable.setColumnWidths( p_colWidths );
        
        if  ( p_tableType.useOptionsMenu() && p_optionsMenu != null )
        {
            // Insert options at the top
            //
            p_presTable.getEntityNodeWrapper().unwrap().addChild( (GenericLeafNode) p_optionsMenu.unwrap().getEntityNode(), 0 );
            p_presTable.unwrap().addChild( p_optionsMenu.unwrap(), 0 );
        }
        else if ( p_insertAfterRowButton != null && p_deleteRowButton != null )
        {
            // Add buttons to the end
            //
            p_fieldLayoutManager.addProcessChild( p_insertAfterRowButton.getEntityNodeWrapper() );
            p_fieldLayoutManager.addPresentationChild( p_insertAfterRowButton );
    
            p_fieldLayoutManager.addProcessChild( p_deleteRowButton.getEntityNodeWrapper() );
            p_fieldLayoutManager.addPresentationChild( p_deleteRowButton );
        }
        
        if  ( p_tableType == MVTableType.COLLAPSIBLE_SUB_GRID )
        {
            p_presTable.setColumnHeading(p_presTable.unwrap().getTableSummary());
            
            p_presTable.setNestedColumnHeadingsIntoParent( true );

            RichHelper.switchToCustomMVCollapsibleTable( p_presTable );
        }
    }

    /**
     * Setup process table.
     *
     * @param p_field the field
     * @param p_dataContainer the data container
     * @param p_table the table
     * @param p_tableType 
     */
    protected void setupProcessTable(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, FormTableWrapper p_table, MVTableType p_tableType)
    {
        if  ( p_tableType != MVTableType.SIMPLE_LINEAR )
        {
            // Only display the table it is NOT in read only mode OR if there are rows
            //
            p_table.setNotApplicable( Boolean.TRUE );
    
            p_table.setConditionExpression( "$$" + VersionServiceData.VERSION_READ_ONLY_MODE_DATA_ITEM + "$ != 'YES' OR $$" + p_dataContainer.getEntityKeyName() + ".lastInstance()$ > '0'" );
        }
    }

    
    /**
     * Setup presentation table.
     *
     * @param p_field the field
     * @param p_presTable the presentation table
     * @param p_tableType the table type
     */
    protected void setupPresentationTable(IVersionFieldMapper p_field, RichHTMLPresentationTableWrapper p_presTable, MVTableType p_tableType)
    {
        p_presTable.setDesignToUseFromEntityKey( p_tableType.getTableDesign() );
    }

    /**
     * Estimate column char lengths.
     * @param p_tableType 
     *
     * @param p_presTable the presentation table
     * @param p_charLengths the char lengths per column
     * @return p_charLengths
     */
    private List<Integer> estimateColumnCharLengths(MVTableType p_tableType, RichHTMLPresentationTableWrapper p_presTable, List<Integer> p_charLengths)
    {
        List children = p_presTable.unwrap().getChildrenList();
     
        if  ( p_tableType.useOptionsMenu() )
        {
            // Add space for the options menu before the table items
            //
            p_charLengths.add( 3 );
        }
        
        for (Object child : children)
        {
            if  ( child instanceof PresentationButton )
            {
                p_charLengths.add(5);
            }
            else if ( child instanceof PresentationQuestion )
            {
                int fieldSize = ConvertUtility.convertToInt( ((PresentationQuestion)child).getFieldSize(), 0);
                int labelSize = ((PresentationQuestion)child).getQuestionText().length();
                
                if  ( ! HTMLAnswer.DISPLAY_TYPE_TEXT_INPUT_FIELD.equals( ((PresentationQuestion)child).getDisplayType() ) )
                {
                    if  ( MVTableType.GRID == p_tableType && ((PresentationQuestion)child).getDisplayType().indexOf( "WidgetDialog|DropDown") > -1 )
                    {
                        // FIXME: Temporary hack to get around the Customer Comms table which is putting its drop down widget icon
                        // into the previous column unless we give it a massive amount of space (which then affects the table length)
                        //
                        fieldSize += 30;
                    }
                    else
                    {
                        // Assume widget attached, so give a few of chars of space
                        //
                        fieldSize += 3;
                    }
                }
                
                int size = Math.max(fieldSize, labelSize);
                
                p_charLengths.add( size );
            }
            else if (child instanceof PresentationTable )
            {
                String colWidths = (String) ( (PresentationTable) child ).getAttribute( HTMLTable.ATT_COLUMN_WIDTHS );
                
                if ( StringUtils.isNotBlank( colWidths ) )
                {
                    String[] colWidthSplit = StringUtils.split( colWidths, ",", false );
                    
                    int totalTableColWidth = 0;
                    
                    for (int i = 0; i < colWidthSplit.length; i++)
                    {
                        totalTableColWidth += Integer.parseInt( colWidthSplit[i] );
                    }
                    
                    p_charLengths.add( -1 * totalTableColWidth );
                }
            }
        }
        
        if  ( !p_tableType.useOptionsMenu() )
        {
            // Add lengths for buttons on the end of the standard row (of still a standard table)
            //
            p_charLengths.add( 3 );
            p_charLengths.add( 3 );
        }

        return p_charLengths;
    }

    /**
     * Process property.
     *
     * @param p_field the field
     * @param p_dataContainer the data container
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_logFieldDetails the log field details
     */
    private void processProperty(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, RichFieldLayoutManager p_fieldLayoutManager,
            String p_logFieldDetails)
    {
        PropertyWrapper dataItem = addFieldDataItem( p_field, p_dataContainer, p_field.getProcessedName(), p_logFieldDetails );
        
        if  ( p_field.isDisplayed() )
        {
            QuestionWrapper question = null;
            RichHTMLPresentationQuestionWrapper presQuestion = null;
            
            try
            {
                question = addFieldQuestion( p_field, dataItem, p_fieldLayoutManager, p_logFieldDetails );
                presQuestion = addFieldPresentationQuestion( p_field, question, dataItem, p_fieldLayoutManager, p_logFieldDetails );
            }
            catch(RuntimeException p_ex)
            {
                // Don't leave in an inconsistent state as we're currently ignoring errors
                //
                abortNode( question );
                abortNode( presQuestion );
                
                throw p_ex;
            }
        }
    }

    private void abortNode(GenericLeafNodeWrapper<?> p_node)
    {
        if  ( p_node != null && p_node.unwrap() != null && p_node.unwrap().getParent() != null )
        {
            p_node.unwrap().getParent().removeChild( p_node.unwrap() );
        }
    }

    /**
     * Adds the data item for the field.
     *
     * @param p_field the field
     * @param p_dataContainer the data container
     * @param p_dataItemName the data item name
     * @param p_logFieldDetails the log field details
     * @return the property wrapper
     */
    protected PropertyWrapper addFieldDataItem(IVersionFieldMapper p_field, PropertyGroupWrapper p_dataContainer, String p_dataItemName, String p_logFieldDetails)
    {
        PropertyWrapper dataItem = PropertyWrapper.create( getFormContext(), p_dataItemName );
        
        // Add the data item to its parent 
        //
        p_dataContainer.addChild( dataItem );
        
        // Expose data items for multi lang fields, ideally we should be able to specify [A] but cannot for now due to a bug,
        // so expose a fixed number of elements.
        //
        // Also assume the incrementing part is the last data group (as does the widget)
        //
        // For the future, perhaps we need a flag on the question to indicate it's multi-entry and therefore handle this all automatically in the product by
        // looking for the last [A] and auto expose them?
        //
        // FIXME: Use [A] when fixed
        //
        // Q. Is this needed now?
        //set the enrichment length if defined.
        dataItem.setAttribute( "EnriLength", new Integer(p_field.getEnriLength()).toString());
        
        if  ( p_field.getFieldType() == EFieldType.MULTI_LANGUAGE_GROUP )
        { 
            String dataGroupKey = DataUtility.removeEndInstance(p_dataContainer.getPropertyGroupKey());
            
            for (int i=1; i <= NUMBER_OF_LANGUAGE_INSTANCES; i++)
            {
                getBasicProcess().addExposedDataItem( dataGroupKey + '[' + i + "]." + p_dataItemName );
            }
        }

        // Store some info for runtime use
        //
        BrowserUtility.setBasicBrowserAttributes(dataItem.unwrap(), p_field.getT24Name(), p_field.getName() );
        BrowserUtility.setFullAppName( dataItem.unwrap() );
        
        if  ( p_field instanceof ApplicationPrimaryKeyFieldMapper )
        {
            VersionUtility.storePrimaryKeyDetails( getBasicProcess().getMapper(), dataItem, getVersion() );
        }
        
        // Now update the Data Item & Question based on the meta data
        //
        EFieldType fieldType = p_field.getFieldType(); 

        dataItem.setType( fieldType.getConnectType() );
        
        if  ( fieldType.getIrisType() != null )
        {
            BrowserUtility.setIrisType(dataItem.unwrap(), fieldType.getIrisType() );
        }
        
        if  ( fieldType == EFieldType.STRING )
        {
            int maxLength = p_field.getMaxInputLength();
            
            if ( maxLength > 0 )
            {
                dataItem.setMaxFieldLength( maxLength );
            }
        }
        else if ( fieldType == EFieldType.LIST )
        {
            FormList formList = VersionUtility.getOrCreateDIFormListFromDomain(getFormContext(), p_field);
            
            if  ( formList != null )
            {
                dataItem.setType( formList.getFullName() );
            }
            else
            {
                LOGGER.error( "Could not get form list for {} of {}", p_logFieldDetails, p_field.getFieldTypeName() );
            }
        }
        
        return dataItem;
    }
    
    /**
     * Adds the question for the field.
     *
     * @param p_field the field
     * @param p_dataItem the data item
     * @param p_processContainer the process container
     * @param p_logFieldDetails the log field details
     * @return the question wrapper
     */
    protected QuestionWrapper addFieldQuestion(IVersionFieldMapper p_field, PropertyWrapper p_dataItem, RichFieldLayoutManager p_fieldLayoutManager, String p_logFieldDetails)
    {
        QuestionWrapper question = QuestionWrapper.create( getFormContext() );

        p_fieldLayoutManager.addProcessChild( question );
        
        // Set basic question settings
        //
        question.setDataItemName( p_dataItem.getPropertyKey( null ) );
        
        question.setMandatory( p_field.isMandatory() );
        question.setReadOnly( p_field.isReadOnly() );
        
        // Set the label
        //
        TextTranslations questionText = VersionUtility.getLabel( getBasicProcess().getMapper(), p_field, true );
        
        // Perform some tidying up first .. we'll leave the translated text alone to fix mnaully if needed
        //
        String text = questionText.getText().trim();
        
        if  ( text.startsWith( "# " ) )
        {
            text = text.substring( 2 );
        }
        
        question.setQuestionText( text );
        getLanguageMapHelper().registerT24TextTranslations( question, questionText );
        
        // Commented out because of Vanity .... (i.e. makes the screen crowded and ugly and the hint text contains
        // the same stuff
        //
        // See "Option to display the help popup when hovering over the question label instead of having a separate help icon [IN:010072]"
        //
//        if  (  p_field.getHelpText() != null )
//        {
//            question.setHelpText( p_field.getHelpText().getText() );
//        }
        //
        //getProject().getLanguageMapHelper().registerT24HelpTextTranslations( question, p_field.getHelpTextTranslations() );

        if  ( p_field.getHintText(getBasicProcess().getMapper()) != null )
        {
            TextTranslations hintText = p_field.getHintText(getBasicProcess().getMapper());
            
            question.setHintText( hintText.getText() );
            
            getLanguageMapHelper().registerT24HintTextTranslations( question, hintText );
        }

        // Currently in Browser, HotValidate is performed if both HotValidate+HotField are specified (see deal.js in BrowserWeb.war), so we will follow suit.
        //
        // As both hot* actions also perform a meta data call (which contain enrichment's), we only should only bother with the enrichment call when neither is being performed (and field has enrichment's)
        //
        if  ( p_field.getHotValidate() )
        {
            addHotValidateNAG( question );
        }
        else if  ( p_field.getHotField() )
        {
            addHotFieldNAG( question );
        }
        else if  ( p_field.hasEnrichment() || p_field.hasCalendarPopupBehaviour() || p_field.hasRecurrencePopupBehaviour() || p_field.isEnrichmentOnly() || p_field.getFieldType() == EFieldType.DATE || p_field.getFieldType() == EFieldType.FREQUENCY)
        {
        	// for dropdowns, calendar popup behavior , recurrence popup behavior, frequency fields and enrichment only fields add a QLR
            addEnrichmentNAG( question );
        }
        
        return question;
    }

    /**
     * Adds a QLR to nag for an enrichment
     * 
     * @param p_question
     */
    private void addEnrichmentNAG(QuestionWrapper p_question)
    {
        enableQLR( p_question );
        
        GenericLeafNodeWrapper<Rule> nagForEnrichment = getBasicProcess().getProject().getRulesRoot().getChildWithName( NAG_FOR_ENRICHMENT, Rule.class );
        
        p_question.addLink( nagForEnrichment );
    }

    /**
     * Adds a QLR to nag for a hot validate
     * 
     * @param p_question the question
     */
    private void addHotValidateNAG(QuestionWrapper p_question)
    {
        enableQLR( p_question );

        GenericLeafNodeWrapper<Rule> nagForHotValidate = getBasicProcess().getProject().getRulesRoot().getChildWithName( NAG_FOR_HOT_VALIDATE, Rule.class );
        
        p_question.addLink( nagForHotValidate );
    }

    /**
     * Adds a QLR to nag for a hot field
     * 
     * @param p_question the question
     */
    private void addHotFieldNAG(QuestionWrapper p_question)
    {
        enableQLR( p_question );

        GenericLeafNodeWrapper<Rule> nagForHotField = getBasicProcess().getProject().getRulesRoot().getChildWithName( NAG_FOR_HOT_FIELD, Rule.class );
        
        p_question.addLink( nagForHotField );
    }

    private void enableQLR(QuestionWrapper p_question)
    {
        p_question.setPostQuestionRules( Boolean.TRUE );
        
        p_question.setDisableInput( Boolean.TRUE );
    }

    /**
     * Adds the presentation question for the field.
     *
     * @param p_field the field
     * @param p_question the question
     * @param p_dataItem the data item
     * @param p_fieldLayoutManager the field layout manager
     * @param p_logFieldDetails the log field details
     * @return the rich html presentation question wrapper
     */
    protected RichHTMLPresentationQuestionWrapper addFieldPresentationQuestion(IVersionFieldMapper p_field, QuestionWrapper p_question, PropertyWrapper p_dataItem, RichFieldLayoutManager p_fieldLayoutManager, String p_logFieldDetails)
    {
        RichHTMLPresentationQuestionWrapper presQuestion = RichHTMLPresentationQuestionWrapper.create( getFormContext(), p_question.unwrap() );

        m_wrapperToMapper.put( presQuestion, p_field );
        
        p_fieldLayoutManager.addPresentationChild( presQuestion );

        // Check if any EB attributes are present. If so, use only the first one
        
        EList<String> attributes = p_field.getAttributes();
        
        if(attributes != null && !attributes.isEmpty())
        {
        	presQuestion.setAttribute(ClassAttributeHook.EB_CUSTOM_ATTRIBUTE, attributes.get(0));
        }
        
        if  ( p_field.isReKeyRequired() )
        {
            BrowserUtility.setReKey( p_dataItem.unwrap() );
        }
        
//        if  ( VersionUtility.getLabel( p_field, false ) == null )
//        {
//            // We don't have a label, so we need to hide the question part (but continue to display on new line as before .. maybe not exactly needed but still keep it the same)
//            //
//            presQuestion.setHideQuestion( true );
//
//            presQuestion.setAnswerNewLine( true );
//        }
        
        int fieldSize = p_field.getMaxDisplayLength();
        
        int enrichmentLength =p_field.getEnriLength();
       
        //simply set all the entries for now. IrisSerializer uses the Property and VersionFieldAction uses Question to set enrichment text
        presQuestion.unwrap().setAttribute("EnriLength", new Integer(enrichmentLength));
        p_question.unwrap().setAttribute("EnriLength", new Integer(enrichmentLength)); 
        
        if  ( fieldSize > 0 )        presQuestion.setFieldSize( fieldSize );
        
        presQuestion.setDesignToUseFromEntityKey("Custom");
        
        StringBuilder buff = new StringBuilder();
        
        buff.append( "FontGrey QQues" )
            .append( VersionUtility.getShowNullAttribute(getVersion()) ? " ShowNullFields" : " HideNullFields");
        
        if ( p_field.getHotValidate() )
        {
            buff.append( " hotValidate" );
        }
        else if ( p_field.getHotField() )
        {
            buff.append( " hotField" );
        }
        
        presQuestion.setQuestionStyle(buff.toString());
        presQuestion.setRowStyle("QRow");
        presQuestion.setMandatoryStyle("ErrorColor QMand");
        presQuestion.setPrefixStyle("QPref");
        
        buff.setLength( 0 );
        if  ( p_field.isReadOnly() )
        {
            presQuestion.setReadOnlyFormat( p_field.getReadOnlyFormat());
            buff.append("noInput ");
        }
        
        buff.append("QAns");

// Use this for icons if needed instead of the above for coloring to orange
//        
//        if  ( p_field.getHotField() )
//        {
//            buff.append( " hotField" );
//        }
        
        presQuestion.setAnswerStyle(buff.toString());
                
// Right-adjust is now only being applied for standard tables, otherwise chaos reigns!
//        
//        if  ( p_field.getRightAdjust() )
//        {
//            presQuestion.unwrap().setAttribute( "TMP_RIGHT_ADJUST", "Y" );
//            
//            // Q. Should this be limited to grid tables only?
//            //
//            presQuestion.setDesignToUseFromEntityKey( "Custom" );
//            
//            presQuestion.setJustification( RichHTMLPresentationQuestionWrapper.EJustification.RIGHT );
//
//            presQuestion.setAnswerJustification( RichHTMLPresentationQuestionWrapper.EAnswerJustification.RIGHT );
//        }

        final String targetT24DomainNameForForeignKeyField = ApplicationUtility.getDomainNameReferencedByForeignKey(p_field.getMdfProperty());
        final String irisDomainResourceNameForForeignKeyField = (targetT24DomainNameForForeignKeyField == null) ? null : MapperUtility.processT24NameToIRISName(targetT24DomainNameForForeignKeyField);
        
         /*Add the frequency control if it is frequency field or the version field has popup control defined as recurrence.
         Qn: Does iris enrichment return enrichment for frequency fields? Where should tsdk jars for web validation be held---edge or iris?
		 Check with temenosag abt checkfilevalidator.java and frequency enrichment... Is there any iris link to read the records of EB.FREQUENCY file for enrichment as well as to read DYNAMIC.TEXT file? Or should a new enquiry be designed?
		 uses the web validate mechanism for enrichments.. Flow to be implemented checkfilevalidator.java. Refer fieldevents.xsl for setting freq enrichment which calls OS.CHECKFILE and EB.BUILD.RECURRENCE.MASK. 
		 Maybe the types "recurrence" and "frequency" will be introduced later and then the need to check the business types to arrive at a decision for recurrence and frequency fields will be removed.
        */
        
        if ( p_field.getFieldType() == EFieldType.FREQUENCY || p_field.hasRecurrencePopupBehaviour())
        {
            RichHelper.setFrequencyControlWidget( presQuestion, p_field.getBusinessType(), p_field.getTypeModifiers(), irisDomainResourceNameForForeignKeyField,p_field.hasRecurrencePopupBehaviour(), p_field.isEnrichmentOnly(),p_question.unwrap().getName());
            
//            // Give some space to it for table calcs
//            //
//            presQuestion.setFieldSize( 26 );

        }

        // Changing the Display type of the Date fields to calendarUI
        // Maybe the types "date" will be introduced later and then the need to check the business types to arrive at a decision for date type fields will be removed.       
        else if ( p_field.getFieldType() == EFieldType.DATE || p_field.hasCalendarPopupBehaviour())
        {
            RichHelper.setCalendarWidget( presQuestion, irisDomainResourceNameForForeignKeyField, p_field.isEnrichmentOnly(),p_question.unwrap().getName());
        }
        
        
        else if ( p_field.getBusinessType().equals("DP"))
        {
            RichHelper.setRelativeCalendarWidget( presQuestion, p_field.getTypeModifiers(), irisDomainResourceNameForForeignKeyField, p_field.isEnrichmentOnly(),p_question.unwrap().getName());
        }
        
        // Let's do this check before the displayDropDown as it will override anything we 
        // could have in the domain definition. Test this
        //
        else if ( p_field.getSelectionEnquiry() != null )
        {
        	String sEnquiry = p_field.getSelectionEnquiry();
        	String sParameters = p_field.getEnquiryParameter();
        	ParameterParserResult result = ParameterParser.getResourceName(RESOURCE_TYPE.enquiry, sEnquiry, sParameters, false);
        	String enquiryURL= result.getResourceName() + "()";
        	if (result.getParameters() != null)
        	{
        		enquiryURL += "?" + result.getParameters(); 
        	}

        	RichHelper.setEnquiryDropDownWidgetForEnquiry( presQuestion, irisDomainResourceNameForForeignKeyField, enquiryURL, p_field.isEnrichmentOnly());
         }
        
        // Setting the type of the field as dropdown if the field has any check file attached to it-
        //
        else if ( p_field.displayDropDown() )
        {
            String irisResourceURL = p_field.getFieldTypeName();
            String domainPrimaryKey = ApplicationUtility.getDomainPrimaryKey( p_field.getMdfProperty() );
            
            if ( StringUtils.isNotBlank( irisResourceURL ) && StringUtils.isNotBlank( domainPrimaryKey ) )
            {
                RichHelper.setEnquiryDropDownWidget( presQuestion, irisDomainResourceNameForForeignKeyField, irisResourceURL, p_field.isEnrichmentOnly());
            }
        }
        
        else if  ( p_field.getFieldType() == EFieldType.MULTI_LANGUAGE_GROUP )
        {
            RichHelper.setMultiLanguageQuestionWidget( presQuestion, irisDomainResourceNameForForeignKeyField );
        }
        
        else if ( p_field.getFieldType() == EFieldType.TEXT_AREA_GROUP )
        {
            RichHelper.setTextArea( presQuestion, 35, 5, true );
        }
        else if ( p_field.getFieldType() == EFieldType.LIST )
        {
    		setListType( presQuestion, p_dataItem, p_field);
        }
        
        else if (irisDomainResourceNameForForeignKeyField != null)
        {
            final String taggingCSSStyleNames = p_field.displayPopupDropdown() ? irisDomainResourceNameForForeignKeyField + " FKSelectionEnquiry" : irisDomainResourceNameForForeignKeyField;
            presQuestion.setSpecificAnswerControlStyle( taggingCSSStyleNames );
            
            //Handles the case for fields that do not have any related file dropdowns(ie no widgets) but are defined in version as enrichment only fields.
            final boolean enrichmentOnly = p_field.isEnrichmentOnly();
            //TODO: Use the method in RichHelper and should be moved out of this if case. This solves arrangement overlapping for now.
            if(enrichmentOnly)
            {
            	presQuestion.setInfoStyle( "EnrichmentOnly");
            	presQuestion.setAttribute( "DisplayTypeEnrichmentOnly", "Y");      
            }
        }
        
        // set the runtimeID (advanced tab) - formatted as QUE_--<app>--<field>--, it is the key into HelpTextMap.properties lookup
        // single hyphens and underscore have been used in the past, but found problems with duplicate fields
        String app_field = p_field.getForApplication().getName().replace('_', '-');
        app_field += "--" + p_field.getName().replace('_', '-');
        presQuestion.setRuntimeId("QUE_--" + app_field + "--");
        //Log.info("HelptextMap: " + app_field);
        return presQuestion;
    }

    private void setListType(RichHTMLPresentationQuestionWrapper p_presQuestion, PropertyWrapper p_dataItem, final IVersionFieldMapper p_field)
    {
        FormList formList = ((ListType)p_dataItem.unwrap().getDataType()).getList();

        // Default to combo box
        //
        p_presQuestion.setDisplayType( HTMLAnswer.DISPLAY_DROPDOWN );
        
        if ( p_field.displayAsComboBox() )
        {
            // Nothing else to do
        }
        else if  ( p_field.displayAsVerticalRadioButtons() )
        {
            p_presQuestion.setDisplayType( HTMLAnswer.DISPLAY_RADIO );
            p_presQuestion.setLayoutDirection( "Vertical" );
        }
        else if  ( p_field.displayAsToggle() )
        {
            // Q. Is this safe as we're relying on the user to correctly set this for two items?
            //
            p_presQuestion.setDisplayType( HTMLAnswer.DISPLAY_CHECKBOX );
        }
        else if (p_field.displayAsDropDownNoInput())
        {
        	p_presQuestion.setAnswerStyle("noinput_dropdown");
        	//p_presQuestion.setReadOnlyFormat( RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER );
        }
        else
        {
	        String keyList = (String) formList.getAttribute( IRISSerialiser.DI_KEY_LIST );
	        
	        if ( StringUtils.isNotBlank( keyList ) )
	        {
	            int noOfItems = StringUtils.count( keyList, "|" ) + 1;
                
                if ( noOfItems >=2 && noOfItems <= 5 )
                {
                    p_presQuestion.setDisplayType( HTMLAnswer.DISPLAY_RADIO );
                    
                    if  ( noOfItems >= 4 )
                    {
                        p_presQuestion.setLayoutDirection( "Vertical" );
                    }
                }
	        }
	    }
    }

    /**
     * Gets the container layout manager.
     *
     * @return the containerLayoutManager
     */
    public RichLayoutManager getContainerLayoutManager()
    {
        return m_containerLayoutManager;
    }
    
}
