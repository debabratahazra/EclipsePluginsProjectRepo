package com.odcgroup.edge.t24.generation.version.util;

import gen.com.acquire.intelligentforms.entities.FormButtonWrapper;
import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.navigation.MenuItemWrapper;
import gen.com.acquire.intelligentforms.entities.navigation.MenuWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationMenuItemWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationMenuWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;
import gen.com.edgeipk.custom.rules.InvokeRemoteRuleBuilder;
import gen.com.edgeipk.custom.rules.InvokeRemoteRuleWrapper.EScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.FormList;
import com.acquire.intelligentforms.entities.Project;
import com.acquire.util.AssertionUtils;
import com.acquire.util.IDebuggable.DebugLevel;
import com.acquire.util.IntegerFactory;
import com.acquire.util.StringUtils;
import com.edgeipk.builder.rules.RuleWrapper;
import com.edgeipk.custom.rules.EventContainerRule;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.IFieldMapper;
import com.odcgroup.edge.t24.generation.IFieldMapper.EFieldType;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.AbstractVersionFieldMapper;
import com.odcgroup.edge.t24.generation.version.ApplicationFieldMapper;
import com.odcgroup.edge.t24.generation.version.ApplicationGroupVersionFieldMapper;
import com.odcgroup.edge.t24.generation.version.AsteriskFieldMapper;
import com.odcgroup.edge.t24.generation.version.IVersionFieldMapper;
import com.odcgroup.edge.t24.generation.version.VersionApplicationFieldMapper;
import com.odcgroup.edge.t24.generation.version.VersionFieldMapper;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern;
import com.odcgroup.t24.version.versionDSL.DisplayType;
import com.odcgroup.t24.version.versionDSL.Expansion;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.InputBehaviour;
import com.odcgroup.t24.version.versionDSL.PopupBehaviour;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.temenos.connect.T24Browser.BrowserUtility;
import com.temenos.connect.T24Browser.IRISMapper;
import com.temenos.connect.T24Browser.utils.IRISErrorsMvGroup;
import com.temenos.connect.T24Browser.utils.IRISMvGroup;
import com.temenos.connect.T24Browser.utils.IRISOverrideMvGroup;
import com.temenos.connect.odata.InvokeVersionRowActionRule;
import com.temenos.connect.odata.InvokeVersionRowActionRule.EVersionRowAction;
import com.temenos.connect.odata.Utils;
import com.temenos.connect.odata.serialiser.IRISSerialiser;
import com.temenos.connect.odata.utils.MDComponentModel;
import com.temenos.connect.odata.utils.MDComponentModel.NestedComponents;
import com.temenos.connect.odata.utils.MDComponentModel.NestedComponents.ComponentType;
import com.temenos.connect.odata.utils.VersionServiceData;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public final class VersionUtility
{
    private static final Logger LOGGER                                 = GenLogger.getLogger( VersionUtility.class );

    /** Used to track current version being used so remote rules can target it */
    private static final String CURRENT_VERSION_HTTP_SESSION_VAR       = "CurrentVersion";

    private VersionUtility() {}
    
    /**
     * Gets the fields from the version decorated via VersionFieldMapper objects.<p>
     * 
     * It will also sort them into their correct display order and return the primary key.
     *
     * @param p_version the version
     * @param p_mappedFields the mapped fields
     * @return the primary key, null if not found.
     */
    public static IVersionFieldMapper getPrimaryKeyAndSortedFields(EdgeMapper<?> p_edgeMapper, Version p_version, List<IVersionFieldMapper> p_mappedFields)
    {
        IVersionFieldMapper primaryKeyFieldMapper = null;

        Map<Field, Integer> possibleGroupFields = new LinkedHashMap<Field, Integer>();

        EList<Field> versionFields = p_version.getFields();
        
        List<IVersionFieldMapper> mappedGroupFields = new ArrayList<IVersionFieldMapper>();
        
        Map<MdfProperty, MdfClass> forApplicationProperties = null; 
        
        for (int i = 0; i < versionFields.size(); i++)
        {
            Field field = versionFields.get( i );

            // Avoid generating fields without explicit columns, see https://github.com/temenostech/Hothouse/issues/266
            // 
            // Comment from temenosag:
            //
            //        In T24 VERSION there is
            //        1) FIELD.NO multivalue group, which defines which fields are displayed and some properties for them.
            //        2) AUTOM.FIELD.NO multivalue group, which defines default values for fields, whether they are displayed or not.
            //
            //        (eg the CUSTOMER,INPUT version actually defines the LANGUAGE field in both places)
            //
            //        When these are introspected in the .domain however, it seems to treat them both as 'field' objects. The difference is that ones derived from AUTOM.FIELD.NO seem not to have a Presentation or Column element.
            //
            //        In the IRIS metadata cartridge we have
            //        if (field.getColumn() == null) {
            //        continue; // Skip fields with no column. These are likely AUTOM.NEW.CONTENT fields and should not be displayed.
            //        }
            
            if  ( field.getColumn() == null )
            {
                LOGGER.warn( "Field has no column specified, so will skip it: {}", field );
                
                continue;
            }
            
            if  ( AsteriskFieldMapper.ASTERISK_FIELD_NAME.equals( field.getName() ) )
            {
                IVersionFieldMapper versionFieldMapper = new AsteriskFieldMapper( p_version, field, i );

                // For now, we're only handling those with blank labels so they only generate blank lines
                //
                TextTranslations headingText = versionFieldMapper.getLabelText(p_edgeMapper);
                
                if  ( headingText == null || StringUtils.isBlank( headingText.getText() ) )
                {
                    p_mappedFields.add( versionFieldMapper );
                }
            }
            else
            {
                MdfProperty property = VersionUtility.getApplicationProperty( p_version, field );
                
                if  ( property == null )
                {
                    possibleGroupFields.put(field, i);
                }
                else
                {
                    VersionFieldMapper versionFieldMapper = new VersionFieldMapper( p_version, field, property, i );
    
                    if  ( property.isPrimaryKey() )
                    {
                        if  ( primaryKeyFieldMapper != null )
                        {
                            LOGGER.error( "Already have primary key as \"{}\" .. but also have \"{}\"", primaryKeyFieldMapper.getName(), property.getName());
                        }
                        else
                        {
                            primaryKeyFieldMapper = versionFieldMapper;
                        }
                    }
                    
                    p_mappedFields.add( versionFieldMapper );
                    
                    if  ( versionFieldMapper.isGroup() )
                    {
                        mappedGroupFields.add(versionFieldMapper);
                        
                        if  ( field.getMV() != null && field.getMV().intValue() > 0 )
                        {
                            possibleGroupFields.put(field, i);
                        }
                    }
                }
            }
        }
        
        // Fix up any fields that are actually MV/SV group properties (as the names can be the same) and convert them to version fields
        //
        Set<IVersionFieldMapper> updatedGroups = new TreeSet<IVersionFieldMapper>();
        
        for (Entry<Field, Integer> entry : possibleGroupFields.entrySet())
        {
            Field field = entry.getKey();
            Integer order = entry.getValue();

            if  ( ! convertedApplicationFieldToVersionField(p_version, field, order, mappedGroupFields, updatedGroups) )
            {
                // Ok, we might have a group field, whose parent MV/SV group field hasn't been used, so we need to add one now
                // 
                if  ( forApplicationProperties == null )
                {
                    forApplicationProperties = VersionUtility.getProperties(p_version.getForApplication(), true);
                }
                
                Entry<MdfProperty, MdfClass> groupEntry = searchForPropertyUsingName( field.getName(), forApplicationProperties );
                
                if  ( groupEntry == null )
                {
                    LOGGER.error( "Could not obtain application property for field \"{}\" on version \"{}\"", field.getName(), p_version.getT24Name());
                }
                else
                {
                    ApplicationGroupVersionFieldMapper groupFieldMapper = new ApplicationGroupVersionFieldMapper(groupEntry.getKey(), groupEntry.getValue(), p_version.getForApplication(), field);
                    
                    p_mappedFields.add( groupFieldMapper );
                    mappedGroupFields.add( groupFieldMapper );
                    
                    if  ( ! convertedApplicationFieldToVersionField(p_version, field, order, mappedGroupFields, updatedGroups) )
                    {
                        LOGGER.error( "Could not obtain application property for field \"{}\" on version \"{}\"", field.getName(), p_version.getT24Name());
                    }
                }
            }
        }
        
        // Refresh any updated groups (e.g sort)
        //
        for (IVersionFieldMapper updatedGroup : updatedGroups)
        {
            if  ( updatedGroup instanceof AbstractVersionFieldMapper )
            {
                ((AbstractVersionFieldMapper)updatedGroup).refresh();
            }
        }
        
        MapperUtility.sortMappedFields( p_mappedFields );
        
        return(primaryKeyFieldMapper);
    }

    /**
     * Locates a matching application field for the version field and converts it internally to use both.  
     * 
     * @param p_version
     * @param p_field
     * @param p_order
     * @param p_mappedGroupFields
     * @param p_updateGroups
     * 
     * @return true, if a match was found and converted
     */
    private static boolean convertedApplicationFieldToVersionField(Version p_version, Field p_field, int p_order, List<IVersionFieldMapper> p_mappedGroupFields, Set<IVersionFieldMapper> p_updateGroups)
    {
        String appName = convertVersionNameToApplication( p_field.getName() );
        
        for (IVersionFieldMapper mappedGroup : p_mappedGroupFields)
        {
            if  ( mappedGroup.isMV() )
            {
                List<IVersionFieldMapper> mvGroupInputFields = mappedGroup.getGroupInputFields();
                
                for (int mvIndex=0; mvIndex < mvGroupInputFields.size(); mvIndex++)
                {
                    IVersionFieldMapper mvField = mvGroupInputFields.get( mvIndex );
                    
                    if  ( !mvField.isGroup() && appName.equals( mvField.getName() ) )
                    {
                        if  ( mvField instanceof ApplicationFieldMapper )
                        {
                            VersionApplicationFieldMapper convertedField = new VersionApplicationFieldMapper( p_version, p_field, (ApplicationFieldMapper) mvField, p_order ); 
                            
                            mvGroupInputFields.set( mvIndex, convertedField );
                            
                            p_updateGroups.add(mappedGroup);
                            
                            return( true );
                        }
                        else
                        {
                            LOGGER.error( "Unexpectedly found another match to the same app mv field \"{}\"", mvField.getDetails( DebugLevel.HIGH )  );
                        }
                    }
                    else if ( /* p_field.getSV() != null && p_field.getSV().intValue() > 0 && -- Some versions don't set SV properly, so skip for now and hope the fields are unique! */ 
                            mvField.isGroup() 
                            )
                    {
                        List<IVersionFieldMapper> subGroupInputFields = mvField.getGroupInputFields();
                        
                        for (int svIndex=0; svIndex < subGroupInputFields.size(); svIndex++)
                        {
                            IVersionFieldMapper svField = subGroupInputFields.get( svIndex );
                            
                            if  ( appName.equals( svField.getName() ) )
                            {
                                if  ( svField instanceof ApplicationFieldMapper )
                                {
                                    VersionApplicationFieldMapper convertedField = new VersionApplicationFieldMapper( p_version, p_field, (ApplicationFieldMapper) svField, p_order ); 
                                    
                                    subGroupInputFields.set(svIndex, convertedField );
                                    
                                    p_updateGroups.add(mappedGroup);
                                    p_updateGroups.add(mvField);
                                    
                                    return( true );
                                }
                                else
                                {
                                    LOGGER.error( "Unexpectedly found another match to the same app sv field \"{}\"", svField.getDetails( DebugLevel.HIGH )  );
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Creates the rule to set the current version in the http session.
     *
     * @param p_formContext the form context
     * @param p_versionName the version name
     * @return the rule wrapper
     */
    public static RuleWrapper<?> createSetCurrentVersion(FormContext p_formContext, String p_versionName)
    {
        // Create the "Set Current Version Being Used" Set Value Rule.
        //
        SetValueRuleWrapper setCurrentVersionBeingUsed = SetValueRuleWrapper.create( p_formContext );

        setCurrentVersionBeingUsed.setName( "Set Current Version Being Used" );

        setCurrentVersionBeingUsed.setType( SetValueRuleWrapper.EType.HTTP_SESSION_VARIABLE );

        setCurrentVersionBeingUsed.setHttpSessionVariableName( CURRENT_VERSION_HTTP_SESSION_VAR );

        setCurrentVersionBeingUsed.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setCurrentVersionBeingUsed.setFromValue( p_versionName );

        return(setCurrentVersionBeingUsed);
    }
    
    /**
     * Creates the version commit event rule.
     *
     * @param p_formContext the form context
     * @param p_sourceDataGroup the source data group
     * @param p_targetDataGroup the target data group
     * @param p_versionName the version name
     * @return the event container rule
     */
    public static EventContainerRule createVersionCommitEventRule(FormContext p_formContext, String p_sourceDataGroup, String p_targetDataGroup, String p_versionName)
    {
        EventContainerRule eventRule = new EventContainerRule( p_formContext );
        
        eventRule.setName( p_versionName + "VersionCommitEvent" );

        return(eventRule);
    }

    /**
     * Convert version name to application.
     *
     * @param p_versionName the version name
     * @return the converted name
     */
    public static String convertVersionNameToApplication(String p_versionName)
    {
        // p_versionName = p_versionName.replace( '.', '_' );
        
        int mvPostfixCharPos = p_versionName.indexOf( '-' );
    
        if ( mvPostfixCharPos == -1 )
            return p_versionName;
    
        return p_versionName.substring( 0, mvPostfixCharPos );
    }
    
    public static RichHTMLPresentationMenuWrapper createOptionsMenu(FormContext p_formContext, FormTableWrapper p_table, PropertyGroupWrapper p_dataItemGroup, ContainerRuleWrapper p_insertRowAfterCurrentRow, ContainerRuleWrapper p_deleteCurrentRow)
    {
        // Create the "Options" Menu.
        //
        MenuWrapper options = MenuWrapper.create( p_formContext );

        options.setNavigationDisplayName( "Options" );

        // Create the "?" Menu Item.
        //
        MenuItemWrapper qMenuItem = MenuItemWrapper.create( p_formContext );

        qMenuItem.setNavigationText( "?" );

        qMenuItem.setHintText( "$%SLANG Version.TableOptionsMenuHint Options$" );

        // Add the "?" Menu Item to the "Options" Menu.
        //
        options.addChild(qMenuItem);

        
        // Create the "Options" Rich Presentation Menu.
        //
        RichHTMLPresentationMenuWrapper presOptions = RichHTMLPresentationMenuWrapper.create( p_formContext, options.unwrap() );

        presOptions.setDisplayType( "Horizontal" );

        // Create the "?" Rich Presentation Menu Item.
        //
        RichHTMLPresentationMenuItemWrapper presQMenuItem = RichHTMLPresentationMenuItemWrapper.create( p_formContext, qMenuItem.unwrap() );

        // Add the "?" Rich Presentation Menu Item to the "Options" Rich Presentation Menu.
        //
        presOptions.addChild(presQMenuItem);

        createAddRowOptionsMenu( p_formContext, p_insertRowAfterCurrentRow, qMenuItem, presQMenuItem );

        createDelRowOptionsMenuItem( p_formContext, p_deleteCurrentRow, qMenuItem, presQMenuItem );
        
        return presOptions;
    }

    private static void createDelRowOptionsMenuItem(FormContext p_formContext, ContainerRuleWrapper p_deleteCurrentRow, MenuItemWrapper p_qMenuItem,
            RichHTMLPresentationMenuItemWrapper p_presQMenuItem)
    {
        // Create the "-" Menu Item.
        //
        MenuItemWrapper delRowMenuItem = MenuItemWrapper.create( p_formContext );

        delRowMenuItem.setNavigationText( "-" );

        delRowMenuItem.setHintText( "$%SLANG System.DeleteRowHint Delete row$" );

        // Add the "-" Menu Item to the "?" Menu Item.
        //
        p_qMenuItem.addChild(delRowMenuItem);
        
        // Add the "Delete current row" Container Rule to the "-" Menu Item.
        //
        delRowMenuItem.unwrap().addChild(p_deleteCurrentRow.unwrap(), true);

        // Create the "-" Rich Presentation Menu Item.
        //
        RichHTMLPresentationMenuItemWrapper presDelRowMenuItem = RichHTMLPresentationMenuItemWrapper.create( p_formContext, delRowMenuItem.unwrap() );

        // Add the "-" Rich Presentation Menu Item to the "?" Rich Presentation Menu Item.
        //
        p_presQMenuItem.addChild(presDelRowMenuItem);
        
        // Make the +- MV buttons hidden when in read only mode
        // was previously done at options level, but there was a bug in connect, so we are doing each button instead
        presDelRowMenuItem.setHideField( true );
        presDelRowMenuItem.setConditionExpression( "$$" + VersionServiceData.VERSION_READ_ONLY_MODE_DATA_ITEM + "$ != 'YES'" );
    }

    private static void createAddRowOptionsMenu(FormContext p_formContext, ContainerRuleWrapper p_insertRowAfterCurrentRow, MenuItemWrapper p_qMenuItem,
            RichHTMLPresentationMenuItemWrapper p_presQMenuItem)
    {
        // Create the "+" Menu Item.
        //
        MenuItemWrapper addRowMenuItem = MenuItemWrapper.create( p_formContext );

        addRowMenuItem.setNavigationText( "+" );
        
        addRowMenuItem.setHintText( "$%SLANG System.AddRowHint Add row$" );

        // Add the "+" Menu Item to the "?" Menu Item.
        //
        p_qMenuItem.addChild(addRowMenuItem);

        // Add the "Insert row after current row" Container Rule to the "+" Menu Item.
        //
        // FIXME: Why no addLink?
        addRowMenuItem.unwrap().addChild( p_insertRowAfterCurrentRow.unwrap(), true );

        // Create the "+" Rich Presentation Menu Item.
        //
        RichHTMLPresentationMenuItemWrapper presAddRowMenuItem = RichHTMLPresentationMenuItemWrapper.create( p_formContext, addRowMenuItem.unwrap() );

        // Add the "+" Rich Presentation Menu Item to the "?" Rich Presentation Menu Item.
        //
        p_presQMenuItem.addChild(presAddRowMenuItem);
        
        // Make the +- MV buttons hidden when in read only mode
        // was previously done at options level, but there was a bug in connect, so we are doing each button instead
        presAddRowMenuItem.setHideField( true );
        presAddRowMenuItem.setConditionExpression( "$$" + VersionServiceData.VERSION_READ_ONLY_MODE_DATA_ITEM + "$ != 'YES'" );
    }
    
    
    /**
     * Creates the insert after current row button.
     *
     * @param p_formContext the form context
     * @param p_table the table
     * @param p_dataItemGroup the data item group
     * @param p_languageMapHelper the language map helper
     * @param p_hintText the hint text
     * @return the rich html presentation button wrapper
     */
    public static RichHTMLPresentationButtonWrapper createInsertAfterCurrentRowButton(FormContext p_formContext, ContainerRuleWrapper p_insertRowAfterCurrentRow, LanguageMapHelper p_languageMapHelper, TextTranslations p_hintText)
    {
        // Create the "Ins" Button.
        //
        FormButtonWrapper insButton = FormButtonWrapper.create( p_formContext );

        insButton.setActionCommand( "Ins" );

        insButton.setHintText(p_hintText.getText());
        p_languageMapHelper.registerT24HintTextTranslations(insButton, p_hintText);
         
        insButton.setDependencyType( FormButtonWrapper.EDependencyType.NONE );
        insButton.setDisableInput( true );
        
        // Add the rule to the insert button
        //
        insButton.addLink(p_insertRowAfterCurrentRow);
        
        // Create the "Ins" Rich Presentation Button.
        //
        RichHTMLPresentationButtonWrapper presIns = RichHTMLPresentationButtonWrapper.create( p_formContext, insButton.unwrap() );

        presIns.setDesignToUseFromEntityKey( "Version Plus Button" );

        return(presIns);
    }

    public static ContainerRuleWrapper createInsertAfterCurrentRowRule(FormContext p_formContext, FormTableWrapper p_table, PropertyGroupWrapper p_dataItemGroup)
    {
        ContainerRuleWrapper insertRowAfterCurrentRow = ContainerRuleWrapper.create( p_formContext );

        String fullAppName = BrowserUtility.getFullAppName( p_dataItemGroup.unwrap().getParent() );
        
        insertRowAfterCurrentRow.setName( "Insert row after current row for " + fullAppName );

        InvokeVersionRowActionRule invokeRowActionRule = createInvokeRowActionRule( p_formContext, EVersionRowAction.INSERT_ROW, p_dataItemGroup );
        
        insertRowAfterCurrentRow.addTrueRule(invokeRowActionRule);
        
        return insertRowAfterCurrentRow;
    }

    public static InvokeVersionRowActionRule createInvokeRowActionRule(FormContext p_formContext, EVersionRowAction p_rowAction, PropertyGroupWrapper p_dataItemGroup)
    {
        InvokeVersionRowActionRule invokeRowActionRule = new InvokeVersionRowActionRule(p_formContext);
        
        InvokeRemoteRuleBuilder.setRemoteRuleName( invokeRowActionRule, "Version-Row-Action" );
        
        InvokeRemoteRuleBuilder.setScope( invokeRowActionRule, EScope.PARENT_TREE );
        
        InvokeRemoteRuleBuilder.setIncludeCurrent( invokeRowActionRule, false );
        
        InvokeRemoteRuleBuilder.setExecuteFirstMatchingComponentOnly( invokeRowActionRule, true );
        
        InvokeRemoteRuleBuilder.setRefreshComponents( invokeRowActionRule, true );
        
        invokeRowActionRule.setAction( p_rowAction );
        
        invokeRowActionRule.setDataGroupName( p_dataItemGroup.getEntityKeyName() );
        
        return invokeRowActionRule;
    }
    
    /**
     * Creates the delete row button.
     *
     * @param p_formContext the form context
     * @param p_table the table
     * @param p_dataItemGroup the data item group
     * @param p_languageMapHelper the language map helper
     * @param p_hintText the hint text
     * @param p_hideForLastRow if true, hides the button for last row
     * @param p_clearForLastRow if true, then just clears the row of data for the last row
     * @return the rich html presentation button wrapper
     */
    public static RichHTMLPresentationButtonWrapper createDeleteRowButton(FormContext p_formContext, FormTableWrapper p_table, PropertyGroupWrapper p_dataItemGroup, ContainerRuleWrapper p_deleteCurrentRow, LanguageMapHelper p_languageMapHelper, TextTranslations p_hintText, boolean p_hideForLastRow, boolean p_clearForLastRow)
    {
        if ( p_hideForLastRow && p_clearForLastRow ) throw new IllegalArgumentException("You cannot have both p_hideForLastRow and p_clearForLastRow");
        
        // Create the "Del" Button.
        //
        FormButtonWrapper del = FormButtonWrapper.create( p_formContext );

        del.setActionCommand( "Del" ); 
        del.setHintText(p_hintText.getText());
        
        p_languageMapHelper.registerT24HintTextTranslations(del, p_hintText);
        
        del.setDependencyType( FormButtonWrapper.EDependencyType.NONE );
        del.setDisableInput( true );
        
        if  ( p_hideForLastRow )
        {
            del.setNotApplicable( true );
    
            del.setConditionExpression( "$$" + p_dataItemGroup.getEntityKeyName() + ".lastInstance()$ > '1'" );
        }

        // Add the "Delete current row" Container Rule to the "Del" Button.
        //
        del.addLink(p_deleteCurrentRow);

        // Create the "Del" Rich Presentation Button.
        //
        RichHTMLPresentationButtonWrapper presDel = RichHTMLPresentationButtonWrapper.create( p_formContext, del.unwrap() );

        presDel.setDesignToUseFromEntityKey( "Version Minus Button" );
        
        return(presDel);
    }

    public static ContainerRuleWrapper createDeleteRowRule(FormContext p_formContext, FormTableWrapper p_table, PropertyGroupWrapper p_dataItemGroup)
    {
        // Create the "Delete current row" Container Rule.
        //
        ContainerRuleWrapper deleteCurrentRow = ContainerRuleWrapper.create( p_formContext );

        deleteCurrentRow.setName( "Delete current row for " + BrowserUtility.getFullAppName(p_dataItemGroup.unwrap().getParent()));

        InvokeVersionRowActionRule invokeRowActionRule = createInvokeRowActionRule( p_formContext, EVersionRowAction.DELETE_ROW, p_dataItemGroup );
        
        deleteCurrentRow.addTrueRule(invokeRowActionRule);
        
        return deleteCurrentRow;
    }

    /**
     * Adds the error data group to the specified container.
     *
     * @param p_formContext the form context
     * @param p_dataGroupParent the data group parent
     * @return the property group wrapper
     */
    public static PropertyGroupWrapper addErrorDataGroup(FormContext p_formContext, PropertyGroupWrapper p_dataGroupParent)
    {
        // Create the "Errors_ErrorsMvGroup (1)" Data Group.
        //
        PropertyGroupWrapper errorsErrorsMvGroup1 = PropertyGroupWrapper.create( p_formContext );

        IRISErrorsMvGroup.setErrorGroupName( errorsErrorsMvGroup1.unwrap() );

        // Create the "element ()" Data Group.
        //
        PropertyGroupWrapper element = PropertyGroupWrapper.create( p_formContext );

        element.setName( IRISMvGroup.ELEMENT_GROUP_NAME );

        element.setFixedSize( Boolean.FALSE );

        // Add the "element ()" Data Group to the "Errors_ErrorsMvGroup (1)" Data Group.
        //
        errorsErrorsMvGroup1.addChild(element);

        // Create the "Code" Data Item.
        //
        PropertyWrapper code = PropertyWrapper.create( p_formContext );

        code.setName( IRISErrorsMvGroup.ERRORS_CODE_NAME );

        // Add the "Code" Data Item to the "element ()" Data Group.
        //
        element.addChild(code);

        // Create the "Info" Data Item.
        //
        PropertyWrapper info = PropertyWrapper.create( p_formContext );

        info.setName( IRISErrorsMvGroup.ERRORS_INFO_NAME );

        // Add the "Info" Data Item to the "element ()" Data Group.
        //
        element.addChild(info);

        // Create the "Text" Data Item.
        //
        PropertyWrapper text = PropertyWrapper.create( p_formContext );

        text.setName( IRISErrorsMvGroup.ERRORS_TEXT_NAME );

        // Add the "Text" Data Item to the "element ()" Data Group.
        //
        element.addChild(text);

        // Create the "Type" Data Item.
        //
        PropertyWrapper type = PropertyWrapper.create( p_formContext );

        type.setName( IRISErrorsMvGroup.ERRORS_TYPE_NAME );

        // Add the "Type" Data Item to the "element ()" Data Group.
        //
        element.addChild(type);
     
        // Add it to the parent
        //
        p_dataGroupParent.addChild( errorsErrorsMvGroup1 );

        return(errorsErrorsMvGroup1);
    }

    public static PropertyGroupWrapper addMDComponentModelGroup(FormContext p_formContext, PropertyGroupWrapper p_dataGroupParent)
    {
        // Create the "MDComponentModel (1)" Data Group.
        //
        PropertyGroupWrapper mdComponentModel = PropertyGroupWrapper.create( p_formContext );
    
        mdComponentModel.setName( "MDComponentModel" );
    
        mdComponentModel.setLinkToStructure( true );
    
        mdComponentModel.setLinkedStructureFromEntityKey( "MDComponentModel[1]" );
        
        p_dataGroupParent.addChild( mdComponentModel );
        
        return mdComponentModel;
    }
    
    
    /**
     * Gets the application property for the field from the version's forApplication class (first class)
     *
     * @param p_version the version
     * @param p_field the field
     * @return the application property, null if not found in the forApplication
     */
    public static MdfProperty getApplicationProperty(Version p_version, Field p_field)
    {
        String appFieldName = convertVersionNameToApplication( p_field.getName() );
        
        MdfClass forApplication = p_version.getForApplication();
        
        MdfProperty prop = forApplication.getProperty( appFieldName );

        return prop;
    }

    public static MdfProperty searchForPropertyUsingQName(String p_qualifiedName, List<MdfProperty> p_propertyList)
    {
        for (MdfProperty property : p_propertyList)
        {
            if ( property.getQualifiedName().getQualifiedName().startsWith( p_qualifiedName ) )
            {
                return property;
            }
        }
        
        return null;
    }
    
    
    public static Entry<MdfProperty, MdfClass> searchForPropertyUsingName(String p_name, Map<MdfProperty, MdfClass> p_forApplicationProperties)
    {
        for (Entry<MdfProperty, MdfClass> entry : p_forApplicationProperties.entrySet())
        {
            MdfProperty property = entry.getKey();
            
            if ( p_name.equals( property.getName() ) )
            {
                return entry;
            }
        }
        
        return null;
    }
    
    /**
     * Checks if field requires enrichments.
     * 
     * @param p_field
     * @return true, if does need enriching.
     */
    public static boolean hasEnrichment(Field p_field)
    {
        return VersionUtility.isYes( p_field.getEnrichment() );
    }
    
    
    /**
     * Checks if field has calendar popup control defined in the version.
     * 
     * @param p_field
     * @return true, if it has a calendar popupcontrol behaviour.
     */
    public static boolean hasCalendarPopupBehaviour(Field p_field)
    {
        return p_field.getPopupBehaviour() == PopupBehaviour.CALENDAR;
    }
    
    /**
     * @param p_field
     * @return true if it has vertical option display type
     */
    public static boolean displayAsVerticalRadioButtons(Field p_field)
    {
        return p_field.getDisplayType() == DisplayType.VERTICAL_OPTIONS;
    }

    /**
     * @param p_field
     * @return true if it has combo box display type
     */
    public static boolean displayAsComboBox(Field p_field)
    {
        return p_field.getDisplayType() == DisplayType.COMBOBOX;
    }

    /**
     * @param p_field
     * @return true if it has toggle display type
     */
    public static boolean displayAsToggle(Field p_field)
    {
        return p_field.getDisplayType() == DisplayType.TOGGLE;
    }
    
    
    /**
     * @param p_field
     * @return true if it has drop down no input display type
     */
    public static boolean displayAsDropDownNoInput(Field p_field)
    {
        return p_field.getDisplayType() == DisplayType.DROP_DOWN_NO_INPUT;
    }
    
    
    /**
     * Checks if field has recurrence control defined as popup control behaviour in version.
     * 
     * @param p_field
     * @return true, if it has a recurrence pop up control behaviour.
     */
    public static boolean hasRecurrencePopupBehaviour(Field p_field)
    {
        return p_field.getPopupBehaviour() == PopupBehaviour.RECURRENCE;
    }
    
    
    /**
     * Checks if the field is mandatory.
     *
     * @param p_field the field
     * @return true, if is mandatory
     */
    public static boolean isMandatory(Field p_field)
    {
        return VersionUtility.isYes( p_field.getMandatory() );
    }

    /**
     * Checks if the field is read only.
     *
     * @param p_field the field
     * @return true, if is read only
     */
    public static boolean isReadOnly(Field p_field)
    {
        InputBehaviour inputBehaviour = p_field.getInputBehaviour();
        
        DisplayType displayType = p_field.getDisplayType();
        
        return ( InputBehaviour.NO_INPUT == inputBehaviour || DisplayType.DROP_DOWN_NO_INPUT == displayType );
    }

    /**
     * @param p_field
     * @return Read-only format for the field to use in the presentation question
     */
    public static RichHTMLPresentationQuestionWrapper.EReadOnlyFormat getReadOnlyFormat(Field p_field)
    {
        DisplayType displayType = p_field.getDisplayType();
        
        // Display read-only as disabled, so that they can be updated via the drop down widget for the case where
        // they are only allowed input from the drop-down
        //
        return ( DisplayType.DROP_DOWN_NO_INPUT == displayType )    ? RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER
                                                                    : RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.USE_DEFAULT
                                                                    ;
    }
    
    /**
     * Checks if the field should be displayed.
     *
     * @param p_field the field
     * @return true, if is displayed
     */
    public static boolean isDisplayed(Field p_field)
    {
         DisplayType displayType = p_field.getDisplayType();
        
        return ( DisplayType.NO_DISPLAY != displayType );
    }

    /**
     * Checks if YES has been explicitly specified.
     *
     * @param p_yesNo the yes no
     * @return true, if is yes
     */
    public static boolean isYes(YesNo p_yesNo)
    {
        return ( p_yesNo == YesNo.YES );
    }

    /**
     * Get the field size.
     * 
     * @param p_field
     * @return Field size, 0 if not available
     */
    public static int getFieldSize(Field p_field)
    {
        Integer fieldSize = p_field.getEnrichmentLength();
        
        return fieldSize != null && fieldSize.intValue() > 0 ? fieldSize.intValue() : 0;
    }

    /**
     * Get the max length
     * 
     * @param p_field
     * @return max length, 0 if not available
     */
    public static int getMaxLength(Field p_field)
    {
        Integer maxLength = p_field.getMaxLength();
        
        return maxLength != null && maxLength.intValue() > 0 ? maxLength.intValue() : 0;
    }

    /**
     * Checks if is a multi language group or field.
     *
     * @param <T> the generic type
     * @param p_field the field
     * 
     * @return true, if is multi language group or field
     */
    public static <T extends IFieldMapper<T>> boolean isMultiLanguageGroupOrField(T p_field)
    {
        return getMultiLanguageField( p_field ) != null;
    }
    
    /**
     * Checks if p_field is a text area group or field
     * 
     * @param p_field
     * @return true if is a text area group or field.
     */
    public static boolean isTextAreaGroupOrField(IVersionFieldMapper p_field)
    {
        return getTextAreaField( p_field ) != null;
    }    
    
    /**
     * Gets the multi language field, which maybe p_field or child field (if p_field is the group).
     *
     * @param <T> the generic type
     * @param p_field the field
     * @return the multi language field, null if not found.
     */
    public static <T extends IFieldMapper<T>> T getMultiLanguageField(T p_field)
    {
        if  ( p_field.isField() )
        {
            return  ( p_field.getFieldType() == EFieldType.MULTI_LANGUAGE_GROUP ) ? p_field : null;
        }
        else if ( p_field.isGroup() )
        {
            List<T> fields = p_field.getGroupInputFields();
            
            if  ( fields.size() > 0 )
            {
                T firstField = fields.get( 0 );
                
                return  ( firstField.getFieldType() == EFieldType.MULTI_LANGUAGE_GROUP ) ? firstField : null;
            }
        }
        
        return null;
    }
    
    /**
     * Gets the text area field, which maybe p_field or the first child field (if p_field is the group).
     *
     * @param <T> the generic type
     * @param p_field the field
     * @return the text area field, null if not found.
     */
    public static <T extends IFieldMapper<T>> T getTextAreaField(T p_field)
    {
        if  ( p_field.isField() )
        {
            return  ( p_field.getFieldType() == EFieldType.TEXT_AREA_GROUP ) ? p_field : null;
        }
        else if ( p_field.isGroup() )
        {
            List<T> fields = p_field.getGroupInputFields();
            
            if  ( fields.size() > 0 )
            {
                T firstField = fields.get( 0 );
                
                return  ( firstField.getFieldType() == EFieldType.TEXT_AREA_GROUP ) ? firstField : null;
            }
        }
        
        return null;
    }
    
    /**
     * Create a special kind of form list from the appropriate domain list so that stores its list values as attributes on the form list
     * rather than as the usual list item children.
     * 
     * This is so that we can switch between 'static' or 'dynamic' lists as connect always appends to static lists.
     * 
     * @param p_formContext
     * @param p_field
     * @return an 'empty' list (with the real list keys/values stored as DI style attributes)
     */
    public static FormList getOrCreateDIFormListFromDomain(FormContext p_formContext, IVersionFieldMapper p_field)
    {
        MdfEnumeration enumType = (MdfEnumeration) p_field.getMdfProperty().getType();

        // Use the fully qualified name (which include domain) so that different fields using the same underlying type
        // will get to use the same list
        //
        String listName = enumType.getQualifiedName().toString();
        
        listName = StringUtils.getJavaIdentifier(listName, true, true );
        
        FormList formList = (FormList) p_formContext.getEntity( listName, FormList.class );
        
        if  ( formList == null )
        {
            formList = new FormList( p_formContext );

// TODO: Add eva notes .. create methods for the 3 types in a utility somewhere
//            
//            EVANoteWrapper evaNote = EVANoteWrapper.create( p_formContext, p_noteType, EStatus.COMPLETE, p_comment )
//            formList.addNoteChild( p_object, p_index, p_linkedObject )
            
            formList.setDynamicList( true );
            
            formList.setName( listName );
            
            p_formContext.getProject().getTypesRoot().getLists().addChild( formList );
            
            StringBuilder keyBuff = new StringBuilder();
            StringBuilder valueBuff = new StringBuilder();
            
            List values = enumType.getValues();
            
            for (Object object : values)
            {
                if  ( object instanceof MdfEnumValue )
                {
                    // This isn't the real name .. just the DSL reference .. 
                    //  String name = ((MdfEnumValue)object).getName();
                    //
                    final String value = ((MdfEnumValue)object).getValue();
                    
                    // Currently these list items are in upper case (same key/value), so make them a bit more readable
                    //
                    String formattedValue = Utils.formatT24ListValue( value );
                    
                    if  ( keyBuff.length() > 0 ) keyBuff.append( '|' );
                    if  ( valueBuff.length() > 0 ) valueBuff.append( '|' );
                    
                    keyBuff.append(value);
                    
                    valueBuff.append(formattedValue);
                }
            }
            
            formList.setAttribute( IRISSerialiser.DI_KEY_LIST,   keyBuff.toString() );
            formList.setAttribute( IRISSerialiser.DI_VALUE_LIST, valueBuff.toString() );
        }
        
        return formList;
    }

    /**
     * Calculate table column widths in pixels, by simply multiplying the char lengths by p_pixelsPerChar.
     * 
     * @param p_charLengths the char lengths .. negative values, indicate fixed pixel widths.
     * @param p_pixelsPerChar the pixels per char
     * @param p_maxWidth the maximum width .. use -1 for unlimited
     *
     * @return the comma separated widths or null if exceeds p_maxWidth
     */
    public static String calcColWidthsInPixels(List<Integer> p_charLengths, int p_pixelsPerChar, final int p_maxWidth)
    {
        StringBuilder colWidths = new StringBuilder();
    
        int remainingWidth = p_maxWidth;
    
        int beforeLast = p_charLengths.size() - 1;
        
        for (int i=0; i < beforeLast; i++)
        {
            int colWidth = p_charLengths.get( i );
            
            colWidth = ( colWidth < 0 ) ? Math.abs( colWidth ) : colWidth * p_pixelsPerChar;
            
            colWidths.append(colWidth).append(',');
            
            remainingWidth -= colWidth;
        }
        
        if  ( p_maxWidth == -1 )
        {
            int colWidth = p_charLengths.get( beforeLast );
            
            colWidth = ( colWidth < 0 ) ? Math.abs( colWidth ) : colWidth * p_pixelsPerChar;
            
            colWidths.append(colWidth);
            
            return colWidths.toString();
        }
        else
        {
            colWidths.append(remainingWidth);
            
            int lastColWidth = p_charLengths.get( beforeLast );
            
            lastColWidth = ( lastColWidth < 0 ) ? Math.abs( lastColWidth ) : lastColWidth * p_pixelsPerChar;
            
            return ( remainingWidth < lastColWidth ) ? null : colWidths.toString();
        }
    }

    /**
     * Gets the main heading from the version
     *
     * @param p_version the version
     * @param p_degrade if true and description not set, then will try header1 and failing that will use a name contrived from the version t24Name.
     * @return the main heading or null if not available and not degraded.
     */
    public static TextTranslations getMainHeading(EdgeMapper<?> p_edgeMapper, Version p_version, boolean p_degrade)
    {
        TextTranslations mainHeader = TextTranslations.getLocalTranslations( p_edgeMapper, p_version.getDescription(), null );
        
        if  ( mainHeader.getText() == null  )
        {
            if  ( p_degrade )
            {
                return getHeader1( p_edgeMapper, p_version, p_degrade );
            }
            
            return null;
        }
        
        return mainHeader;
    }

    /**
     * Gets the header1 from the version
     * 
     * @param p_version
     * @param p_degrade
     * @return the header1 or null if not available and not degraded
     */
    public static TextTranslations getHeader1(EdgeMapper<?> p_edgeMapper, Version p_version, boolean p_degrade)
    {
        TextTranslations header1 = TextTranslations.getLocalTranslations( p_edgeMapper, p_version.getHeader1(), null );

        if  ( header1.getText() == null && p_degrade )
        {
            header1 = TextTranslations.getLocalTranslations( p_edgeMapper, p_version.getDescription(), null );
            
            if  ( header1.getText() == null )
            {
                String versionName = StringUtils.getWords( p_version.getT24Name().toLowerCase(), new char[] { '.', ',' } );
                
                LOGGER.info( "Could not get translation string for version header1 for \"{}\", so will use processed name instead: ", p_version.getT24Name(), versionName );
                
                return TextTranslations.getDefaultText(versionName);
            }
        }
        
        return ( header1.getText() == null ) ? null : header1;
    }
    
    /**
     * Gets the label text heading from the field.
     *
     * @param p_field the field
     * @param p_degrade if true, then if label text has not been set, then will use a name contrived from the version t24Name.
     * @return the label or null if not set or degraded.
     */
    public static TextTranslations getLabel(EdgeMapper<?> p_edgeMapper, IFieldMapper<?> p_field, boolean p_degrade)
    {
        TextTranslations label = p_field.getLabelText(p_edgeMapper);
        
        if  ( ( label == null || label.getText() == null ) )
        {
            label = null;
            
            if  ( p_degrade )
            {
                String labelText = StringUtils.getWords( p_field.getProcessedName(), StringUtils.DEFAULT_WORD_SEPARATORS );
                
                LOGGER.info( "Could not get translation string for field \"{}\", so will use processed name instead: {} ", p_field.getName(), labelText );
                
                label = TextTranslations.getDefaultText(labelText);
            }
        }
        
        return(label);
    }
    

    /**
     * Validates the version object for known required objects/values from it.
     *
     * @param p_version the version
     * @return the string
     */
    public static void validateVersionObject(Version p_version)
    {
        AssertionUtils.requireNonNull( p_version, "p_version" );
        
        String versionName = p_version.getT24Name();
        AssertionUtils.requireNonNullAndNonEmpty( versionName, "p_version.getT24Name()");
        
        MdfClass forApplication = p_version.getForApplication();
        AssertionUtils.requireNonNull( forApplication, "p_version.getForApplication() for p_version: " + versionName );
        
        AssertionUtils.requireNonNullAndNonEmpty( forApplication.getName(), "p_version.getForApplication().getName() for version: " + versionName );
        
        MdfDomain parentDomain = forApplication.getParentDomain();
        AssertionUtils.requireNonNull( parentDomain, "p_version.getForApplication().getParentDomain() for version: " + versionName );
    
        AssertionUtils.requireNonNullAndNonEmpty( parentDomain.getName(), "p_version.getForApplication().getParentDomain().getName() for version: " + versionName );
    }

    /**
     * Compare two version fields.
     *
     * @param p_thisField the this field
     * @param p_otherField the other field
     * @return the compare result
     */
    public static int compare(IVersionFieldMapper p_thisField, IVersionFieldMapper p_otherField)
    {
        // First sort by explicit row number
        //
        int order = p_thisField.getRow() - p_otherField.getRow();
        
        if  ( order != 0 ) return(order);
        
        // Now sort by the column number
        //
        order = p_thisField.getCol() - p_otherField.getCol();
        
        if  ( order != 0 ) return(order);
        
        // Default to sorting with order of the fields
        //
        return(p_thisField.getOrder() - p_otherField.getOrder());
    }

    /**
     * Gets the processed name of the version.
     *
     * @param p_version the version
     * @return the processed version name
     */
    public static String getVersionName(Version p_version)
    {
        AssertionUtils.requireNonNull( p_version, "p_version" );
        
        String name = p_version.getT24Name();
        
        if  ( StringUtils.isNotBlank( name ) )
        {
            return "ver" + MapperUtility.processT24NameToIRISName( name );
        }
        
        MapperUtility.continueOrAbort(LOGGER, "Blank version name for " + p_version, null);
        
        return null;
    }

    /**
     * Adds the Overrides data group to p_versionGroup if its missing.
     * 
     * @param p_formContext
     * @param p_versionGroup
     */
    public static void addMissingOverridesDataGroup(FormContext p_formContext, PropertyGroupWrapper p_versionGroup)
    {
        String overrideGroupName = IRISOverrideMvGroup.getName(p_versionGroup.getName());
        
        if  ( p_versionGroup.unwrap().getChildWithName( overrideGroupName ) != null )
        {
            // The override group has already been specified in the version and created
            //
            // e.g for customer input, it will be part of the customer audit version
            //
            return;
        }

        // Create the "XXXX_OverrideMvGroup ()" Data Group.
        //
        PropertyGroupWrapper overrideMvGroup = PropertyGroupWrapper.create( p_formContext );
    
        overrideMvGroup.setName( overrideGroupName );
    
        overrideMvGroup.setFixedSize( Boolean.FALSE );
    
        overrideMvGroup.setMaxInstances( IntegerFactory.getInteger(0) );

        // Create the "element ()" Data Group.
        //
        PropertyGroupWrapper element = PropertyGroupWrapper.create( p_formContext );

        element.setName( IRISOverrideMvGroup.ELEMENT_GROUP_NAME );

        element.setFixedSize( Boolean.FALSE );

        // Add the "element ()" Data Group to the "XXXX_OverrideMvGroup (1)" Data Group.
        //
        overrideMvGroup.addChild(element);

        // Create the "valuePosition" Data Item.
        //
        PropertyWrapper valuePosition = PropertyWrapper.create( p_formContext );

        valuePosition.setName( IRISMvGroup.MV_VALUE_POSITION );

        // Add the "valuePosition" Data Item to the "element ()" Data Group.
        //
        element.addChild(valuePosition);

        // Create the "subValuePosition" Data Item.
        //
        PropertyWrapper subValuePosition = PropertyWrapper.create( p_formContext );

        subValuePosition.setName( IRISMvGroup.SUB_VALUE_POSITION );

        // Add the "subValuePosition" Data Item to the "element ()" Data Group.
        //
        element.addChild(subValuePosition);

        // Create the "Override" Data Item.
        //
        PropertyWrapper override = PropertyWrapper.create( p_formContext );
    
        override.setName( IRISOverrideMvGroup.OVERRIDE_NAME );
    
        // Add the "Override" Data Item to the "element ()" Data Group.
        //
        element.addChild(override);

        // Now add the override group to the version
        //
        p_versionGroup.addChild( overrideMvGroup );
        
        // Set the necessary run-time attributes we need so that it creates the type information needed
        //
        BrowserUtility.setBasicBrowserAttributes( overrideMvGroup.unwrap(),
                                             null, // p_field.getT24Name() 
                                             IRISErrorsMvGroup.OVERRIDE_ERROR_TYPE);
        
        BrowserUtility.setIrisGroupType( overrideMvGroup.unwrap(), IRISMvGroup.EGroupType.MV_GROUP );
        
        BrowserUtility.setFullAppName( overrideMvGroup.unwrap() );
    }
    
    public static ContainerRuleWrapper createSetupRuleIn3D(EdgeMapper<?> p_edgeMapper, FormContext p_formContext, String p_versionName, Version p_version)
    {
        // FIXME: Needs to be lang mapped at runtime
        //
        TextTranslations mainHeading = VersionUtility.getMainHeading( p_edgeMapper, p_version, true );
        
        String mainHeadingText = MapperUtility.addSlang( p_edgeMapper, p_versionName + ".MainHeading", mainHeading );
        
        AssociatedVersionsPresentationPattern pattern = p_version.getAssociatedVersionsPresentationPattern();
        
        EList<Version> p_associatedVersions = p_version.getAssociatedVersions();
        
        boolean hasAssociatedVersions = ( p_associatedVersions != null && p_associatedVersions.size() > 0 );
        
        if ( pattern == null )
            pattern = AssociatedVersionsPresentationPattern.NONE;
        
        if ( pattern == AssociatedVersionsPresentationPattern.NONE )
        {
            EList<String> attributes = p_version.getAttributes();
            
            if  ( hasAssociatedVersions && attributes != null && attributes.contains( "NO.HEADER.TAB" ) )
            {
                pattern = AssociatedVersionsPresentationPattern.TABS;
            }
        }
        
        switch (pattern)
        {
            case NONE:      
                return VersionUtility.createMainWithContainedAssociatedVersionsSetupRuleIn3D(p_edgeMapper, p_formContext, p_versionName, p_version, NestedComponents.ComponentType.REPEATING_WITHIN_TABS, mainHeadingText); 
                            
            case TABS:      
                return VersionUtility.createContainedMainAndAssociatedVersionsSetupRuleIn3D(p_edgeMapper, p_formContext, p_versionName, p_version, NestedComponents.ComponentType.REPEATING_WITHIN_TABS, mainHeadingText); 
            
            case VERTICAL:  
                return VersionUtility.createContainedMainAndAssociatedVersionsSetupRuleIn3D(p_edgeMapper, p_formContext, p_versionName, p_version, NestedComponents.ComponentType.REPEATING_WITHIN_VERTICAL_NO_HEADING, mainHeadingText);

            case ACCORDIONS:
                return VersionUtility.createMainWithContainedAssociatedVersionsSetupRuleIn3D(p_edgeMapper, p_formContext, p_versionName, p_version, NestedComponents.ComponentType.REPEATING_WITHIN_ACCORDIAN, mainHeadingText); 
                            
            default:        throw new UnsupportedOperationException("Unknown presentation pattern of " + pattern);
        }    
    }
    
    private static ContainerRuleWrapper createMainWithContainedAssociatedVersionsSetupRuleIn3D(EdgeMapper<?> p_edgeMapper, FormContext p_formContext, String p_versionName, Version p_version, ComponentType p_nestedComponentType, String p_mainHeading)
    {
        String versionDataGroup = p_versionName + "[1]";
        
        EList<Version> p_associatedVersions = p_version.getAssociatedVersions();
        
        boolean hasAssociatedVersions = ( p_associatedVersions != null && p_associatedVersions.size() > 0 );
            
        // Create the "Main With Associated Versions in Tabs" Container Rule.
        //
        ContainerRuleWrapper mainWithAssociatedVersionsInTabs = ContainerRuleWrapper.create( p_formContext );

        mainWithAssociatedVersionsInTabs.setName( "Main With Associated Versions in Tabs" );

        // Create the "Set First Dimension" Container Rule.
        //
        ContainerRuleWrapper setFirstDimension = ContainerRuleWrapper.create( p_formContext );

        setFirstDimension.setName( "Set First Dimension" );

        // Add the "Set First Dimension" Container Rule to the "Main With Associated Versions in Tabs" Container Rule.
        //
        mainWithAssociatedVersionsInTabs.addTrueRule(setFirstDimension);

        // Add the "Nested Container" Container Rule to the "Set First Dimension" Container Rule.
        //
        ContainerRuleWrapper setCompositeContainer = createNestedContainer  ( p_formContext 
                                                                            , versionDataGroup + ".MDComponentModel[1].FirstDimension[1]"
                                                                            , NestedComponents.ComponentType.REPEATING_WITHIN_VERTICAL_NO_HEADING + " for " + p_versionName
                                                                            , p_mainHeading
                                                                            , NestedComponents.ComponentType.REPEATING_WITHIN_VERTICAL_NO_HEADING 
                                                                            );

        setFirstDimension.addTrueRule(setCompositeContainer);

        // Create the "Set Second Dimension" Container Rule.
        //
        ContainerRuleWrapper setSecondDimension = ContainerRuleWrapper.create( p_formContext );

        setSecondDimension.setName( "Set Second Dimension" );

        // Add the "Set Second Dimension" Container Rule to the "Nested Container" Container Rule.
        //
        setCompositeContainer.addTrueRule(setSecondDimension);

        // Create the "Set Main Version" Container Rule.
        //
        ContainerRuleWrapper setMainVersion = ContainerRuleWrapper.create( p_formContext );

        setMainVersion.setName( "Set Main Version" );

        // Add the "Set Main Version" Container Rule to the "Set Second Dimension" Container Rule.
        //
        setSecondDimension.addTrueRule(setMainVersion);

        // Create the "Set Top" Set Value Rule.
        //
        SetValueRuleWrapper setTop = SetValueRuleWrapper.create( p_formContext );

        setTop.setName( "Set Top" );

        setTop.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setTop.setPropertyNameFromEntityKey( versionDataGroup + ".MDComponentModel[1].FirstDimension[1].SecondDimension[1]." + MDComponentModel.COMPONENT_MAP_DISPLAY_NAME );

        setTop.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setTop.setFromValue( "Top - " + p_versionName );

        // Add the "Set Top" Set Value Rule to the "Set Main Version" Container Rule.
        //
        setMainVersion.addTrueRule(setTop);

        // Create the "Map Version Location" IRIS oData to Component Mapper v1.0.
        //
        IRISMapper mapVersionLocation = createMapLocationRule( p_formContext, p_versionName, versionDataGroup + ".MDComponentModel[1].FirstDimension[1].SecondDimension[1]" );

        setMainVersion.addTrueRule( mapVersionLocation );
        
        if ( !hasAssociatedVersions )
            return( mainWithAssociatedVersionsInTabs );
        
        // Create the "Set Bottom Composite" Container Rule.
        //
        ContainerRuleWrapper setBottomComposite = createNestedContainer ( p_formContext
                                                                        , versionDataGroup + ".MDComponentModel[1].FirstDimension[1].SecondDimension[2]"
                                                                        , p_nestedComponentType + " for " + p_versionName
                                                                        , p_mainHeading
                                                                        , p_nestedComponentType
                                                                        );                
                
        // Add the "Set Bottom Composite" Container Rule to the "Set Second Dimension" Container Rule.
        //
        setSecondDimension.addTrueRule(setBottomComposite);

        // Create the "Set Third Dimension" Container Rule.
        //
        ContainerRuleWrapper setThirdDimension = ContainerRuleWrapper.create( p_formContext );

        setThirdDimension.setName( "Set Third Dimension" );

        // Add the "Set Third Dimension" Container Rule to the "Set Bottom Composite" Container Rule.
        //
        setBottomComposite.addTrueRule(setThirdDimension);

        // Now start adding associated versions into the third dimension
        //
        int p_versionNumber = 0;

        for (Version associatedVersion : p_associatedVersions)
        {
            String name = associatedVersion.getT24Name();
            
            if  ( name != null )
            {
                ++p_versionNumber;
                
                LOGGER.info( "Associated Version = {} - number {}", name, p_versionNumber );
    
                String componentMapParent = versionDataGroup + ".MDComponentModel[1].FirstDimension[1].SecondDimension[2].ThirdDimension[" + p_versionNumber + "]";
                
                setThirdDimension.addTrueRule( createMappedVersionIn3D( p_edgeMapper, p_formContext, associatedVersion, componentMapParent ) );
            }
            else
            {
                LOGGER.error( "Associated Version is null!" );
            }
        }
        
        return mainWithAssociatedVersionsInTabs;
    }

    private static ContainerRuleWrapper createContainedMainAndAssociatedVersionsSetupRuleIn3D(EdgeMapper<?> p_edgeMapper, FormContext p_formContext, String p_versionName, Version p_version, ComponentType p_nestedComponentType, String p_mainHeading)
    {
        String versionDataGroup = p_versionName + "[1]";
        
        EList<Version> p_associatedVersions = p_version.getAssociatedVersions();
        
        boolean hasAssociatedVersions = ( p_associatedVersions != null && p_associatedVersions.size() > 0 );
            
        // Create the "Main And Associated Versions in ?" Container Rule.
        //
        ContainerRuleWrapper mainWithAssociatedVersionsInTabs = ContainerRuleWrapper.create( p_formContext );

        mainWithAssociatedVersionsInTabs.setName( "Main And Associated Versions in " + p_nestedComponentType );

        // Create the "Set First Dimension" Container Rule.
        //
        ContainerRuleWrapper setFirstDimension = ContainerRuleWrapper.create( p_formContext );

        setFirstDimension.setName( "Set First Dimension" );

        // Add the "Set First Dimension" Container Rule to the "Main And Associated Versions in ?" Container Rule.
        //
        mainWithAssociatedVersionsInTabs.addTrueRule(setFirstDimension);

        // Add the "Nested Container" Container Rule to the "Set First Dimension" Container Rule.
        //
        ContainerRuleWrapper setNestedContainer = createNestedContainer ( p_formContext
                                                                        , versionDataGroup + ".MDComponentModel[1].FirstDimension[1]"
                                                                        , p_nestedComponentType + " for " + p_versionName
                                                                        , p_mainHeading
                                                                        , p_nestedComponentType
                                                                        );

        setFirstDimension.addTrueRule(setNestedContainer);

        // Create the "Set Second Dimension" Container Rule.
        //
        ContainerRuleWrapper setSecondDimension = ContainerRuleWrapper.create( p_formContext );

        setSecondDimension.setName( "Set Second Dimension" );

        // Add the "Set Second Dimension" Container Rule to the "Nested Container" Container Rule.
        //
        setNestedContainer.addTrueRule(setSecondDimension);

        // Now add the main version to the second dimension
        //
        String componentMapParent = versionDataGroup + ".MDComponentModel[1].FirstDimension[1].SecondDimension[1]";
        
        setSecondDimension.addTrueRule( createMappedVersionIn3D( p_edgeMapper, p_formContext, p_version, componentMapParent ) );

        if ( !hasAssociatedVersions )
            return( mainWithAssociatedVersionsInTabs );
        
        // Now start adding associated versions into the second dimension
        //
        int p_versionNumber = 1; // Start from 1 as that slot has been used by the main version above.

        for (Version associatedVersion : p_associatedVersions)
        {
            String name = associatedVersion.getT24Name();
            
            if  ( name != null )
            {
                ++p_versionNumber;
                
                LOGGER.info( "Associated Version = {} - number {}", name, p_versionNumber );
    
                componentMapParent = versionDataGroup + ".MDComponentModel[1].FirstDimension[1].SecondDimension[" + p_versionNumber + "]";
                
                setSecondDimension.addTrueRule( createMappedVersionIn3D( p_edgeMapper, p_formContext, associatedVersion, componentMapParent ) );
            }
            else
            {
                LOGGER.error( "Associated Version is null!" );
            }
        }
        
        return mainWithAssociatedVersionsInTabs;
    }
    
    private static ContainerRuleWrapper createNestedContainer(FormContext p_formContext, String p_componentMapParent, String p_containerName, String p_displayNameText, NestedComponents.ComponentType p_nestedComponent)
    {
        // Create the "Nested Container" Container Rule.
        //
        ContainerRuleWrapper nestedContainer = ContainerRuleWrapper.create( p_formContext );

        nestedContainer.setName( p_containerName );
        
        // Create the "Set Display Name" Set Value Rule.
        //
        SetValueRuleWrapper setDisplayName = SetValueRuleWrapper.create( p_formContext );

        setDisplayName.setName( "Set Display Name" );

        setDisplayName.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setDisplayName.setPropertyNameFromEntityKey( p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_DISPLAY_NAME );

        setDisplayName.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setDisplayName.setFromValue( p_displayNameText );

        // Add the "Set Display Name" Set Value Rule to the "Nested Container" Container Rule.
        //
        nestedContainer.addTrueRule(setDisplayName);

        // Create the "Set Directory" Set Value Rule.
        //
        SetValueRuleWrapper setDirectory = SetValueRuleWrapper.create( p_formContext );

        setDirectory.setName( "Set Directory" );

        setDirectory.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setDirectory.setPropertyNameFromEntityKey( p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_DIRECTORY );

        setDirectory.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setDirectory.setFromValue( NestedComponents.NESTED_COMPONENTS_DIRECTORY );

        // Add the "Set Directory" Set Value Rule to the "Nested Container" Container Rule.
        //
        nestedContainer.addTrueRule(setDirectory);

        // Create the "Set Library" Set Value Rule.
        //
        SetValueRuleWrapper setLibrary = SetValueRuleWrapper.create( p_formContext );

        setLibrary.setName( "Set Library" );

        setLibrary.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setLibrary.setPropertyNameFromEntityKey( p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_LIBRARY_NAME );

        setLibrary.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setLibrary.setFromValue( NestedComponents.NESTED_COMPONENTS_LIBRARY_NAME );

        // Add the "Set Library" Set Value Rule to the "Nested Container" Container Rule.
        //
        nestedContainer.addTrueRule(setLibrary);

        // Create the "Set Component" Set Value Rule.
        //
        SetValueRuleWrapper setComponent = SetValueRuleWrapper.create( p_formContext );

        setComponent.setName( "Set Component" );

        setComponent.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setComponent.setPropertyNameFromEntityKey( p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_COMPONENT_NAME );

        setComponent.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        setComponent.setFromValue( p_nestedComponent.getComponentName() );

        // Add the "Set Component" Set Value Rule to the "Nested Container" Container Rule.
        //
        nestedContainer.addTrueRule(setComponent);
        
        return nestedContainer;
    }

    /**
     * Creates the map location rule.
     *
     * @param p_formContext the form context
     * @param p_versionName the version name
     * @param p_componentMapParent the component map parent
     * @return the IRIS mapper
     */
    private static IRISMapper createMapLocationRule(FormContext p_formContext, String p_versionName, String p_componentMapParent)
    {
        IRISMapper mapVersionLocation = new IRISMapper( p_formContext );

        mapVersionLocation.setAttribute( "Name", "Map " + p_versionName + " Location" );

        mapVersionLocation.setAttribute( "ErrorHandlingType", "Pass Up" );

        mapVersionLocation.setAttribute( "url", p_versionName );

        mapVersionLocation.setAttribute( "tdn", p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_DIRECTORY );

        mapVersionLocation.setAttribute( "tcl", p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_LIBRARY_NAME );

        mapVersionLocation.setAttribute( "tcn", p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_COMPONENT_NAME );
        
        return mapVersionLocation;
    }

    private static ContainerRuleWrapper createMappedVersionIn3D(EdgeMapper<?> p_edgeMapper, FormContext p_formContext, Version p_associatedVersion, String p_componentMapParent)
    {
        String versionName = VersionUtility.getVersionName( p_associatedVersion );
        
        TextTranslations versionHeading = VersionUtility.getMainHeading( p_edgeMapper, p_associatedVersion, true /* Needed for tab title, so compulsory */ );

        // Create the "Add Mapped Version" Container Rule.
        //
        ContainerRuleWrapper addMappedVersion = ContainerRuleWrapper.create( p_formContext );

        addMappedVersion.setName( "Add " + versionName );

        // Create the "Set Display Name" Set Value Rule.
        //
        SetValueRuleWrapper setDisplayName = SetValueRuleWrapper.create( p_formContext );

        setDisplayName.setName( "Set Display Name" );

        setDisplayName.setType( SetValueRuleWrapper.EType.DATA_ITEM );

        setDisplayName.setPropertyNameFromEntityKey(p_componentMapParent + "." + MDComponentModel.COMPONENT_MAP_DISPLAY_NAME );

        setDisplayName.setFromType( SetValueRuleWrapper.EFromType.VALUE );

        String slangText = MapperUtility.addSlang( p_edgeMapper, versionName + ".MainHeading", versionHeading );
        
        setDisplayName.setFromValue( slangText );

        // Add the "Set Display Name" Set Value Rule to the "Add Mapped Version" Container Rule.
        //
        addMappedVersion.addTrueRule(setDisplayName);

        // Create the mapper rule to locate the version
        //
        IRISMapper mapVersionLocation = createMapLocationRule( p_formContext, versionName, p_componentMapParent );

        // Now add it
        //
        addMappedVersion.addTrueRule(mapVersionLocation);
        
        return addMappedVersion;
    }
    
    /**
     * Store version details to be used at runtime.
     *
     * @param p_version the version
     * @param p_mapperData the mapper data
     */
    public static void storeVersionDetails(Project p_project, Version p_version, List<String> p_mapperData)
    {
        MdfClass application = p_version.getForApplication();
        
        String domain = application.getParentDomain().getName();
        
        String applicationName = MapperUtility.processT24NameToIRISName( application.getName() );
        
        MdfProperty idComp6 = application.getProperty( "ID_COMP_6" );
        
        String typeModifiers = null;
        
        if  ( idComp6 != null)
        {
            typeModifiers = T24Aspect.getTypeModifiers(idComp6);
        }
        
        boolean isAAArrangementVersion = VersionUtility.isAAArrangementVersion(p_version);

        BrowserUtility.storeVersionDetails(p_project, domain, applicationName, isAAArrangementVersion, typeModifiers, p_mapperData );
    }

    /**
     * Stores details for the primary key details to be used at runtime.
     *
     * @param p_primaryKeyDataItem the primary key data item
     * @param p_version the version
     * @param p_mapperData the mapper data
     */
    public static void storePrimaryKeyDetails(EdgeMapper<?> p_edgeMapper, PropertyWrapper p_primaryKeyDataItem, Version p_version)
    {
        List<String> mapperData = p_edgeMapper.getMapperData();
        
        p_primaryKeyDataItem.setAttribute( VersionServiceData.IS_PRIMARY_KEY_ATTR, "Y" );
        
        // Store the name of the primary key on the parent group, which will be the version data group
        //
        p_primaryKeyDataItem.unwrap().getParent().setAttribute( VersionServiceData.PRIMARY_KEY_NAME_ATTR, p_primaryKeyDataItem.getName() );

        mapperData.add( VersionServiceData.MD_PRIMARY_KEY_NAME + "=" + p_primaryKeyDataItem.getName() );
        
        TextTranslations primaryKeyQuestionText = VersionUtility.getHeader1( p_edgeMapper, p_version, true );
        
        String versionTextKey = getVersionName( p_version ) + "." + VersionServiceData.MD_PRIMARY_KEY_TEXT;

        MapperUtility.addSlang( p_edgeMapper, versionTextKey, primaryKeyQuestionText );
    }

    /**
     * Checks if p_version is an AA arrangement version.
     *
     * @param p_version the version
     * @return true, if is AA arrangement version
     */
    public static boolean isAAArrangementVersion(Version p_version)
    {
        return  (   p_version.getT24Name().startsWith( "AA.ARRANGEMENT.ACTIVITY" )
                ||  p_version.getT24Name().startsWith( "AA.SIMULATION.CAPTURE" )
                );
    }
    
    /**
     * Creates a map of all properties from this class recursively. 
     * 
     * Copied and modified from {@link VersionUtils#getAllProperties(MdfClass)}
     * 
     * @param p_topClass
     * @param p_recursive
     * @return A map of properties and their top classes (i.e. MV groups or null if not)
     */
    private static Map<MdfProperty, MdfClass> getProperties(MdfClass p_topClass, boolean p_recursive)
    {
        Map<MdfProperty, MdfClass> propertyToTopClass = new LinkedHashMap<MdfProperty, MdfClass>();
        
        List<MdfClass> clazzList = new ArrayList<MdfClass>();
        
        clazzList.add( p_topClass );
        
        getProperties( p_topClass, null, propertyToTopClass, clazzList, p_recursive );
        
        Set<String> propertyNames = new TreeSet<String>();
        
        for (Entry<MdfProperty, MdfClass> entry : propertyToTopClass.entrySet())
        {
            MdfProperty property = entry.getKey();
            
            String propName = property.getName();
            
            if  ( propertyNames.contains( propName ) )
            {
                LOGGER.error( "Duplicate property name \"{}\" in \"{}\"", property, p_topClass );
            }
            else
            {
                propertyNames.add(propName);
            }
        }
        
        return propertyToTopClass;
    }

    @SuppressWarnings("unchecked")
    private static void getProperties(MdfClass p_class, MdfClass p_topClass, Map<MdfProperty, MdfClass> p_propertyToTopClass, List<MdfClass> p_classList, boolean p_recursive)
    {
        if ( p_class != null )
        {
            List<Object> propertiesList = p_class.getProperties();
            
            for (Object property : propertiesList)
            {
                if ( property instanceof MdfAttribute )
                {
                    p_propertyToTopClass.put( (MdfProperty) property, p_topClass );
                }
                
                if ( property instanceof MdfAssociation )
                {
                    MdfAssociation association = (MdfAssociation) property;
                    
                    MdfClass mdfClass = (MdfClass) association.getType();
                    
                    if ( mdfClass == null )
                    {
                        throw new RuntimeException( "Association type was null: " + association.toString(), null );
                    }
                    
                    if ( !p_classList.contains( mdfClass ) && association.getContainment() == MdfContainment.BY_VALUE )
                    {
                        p_classList.add( mdfClass );
                        
                        if  ( p_recursive )
                        {
                            getProperties( (MdfClass) association.getType(), (p_topClass == null) ? mdfClass : p_topClass, p_propertyToTopClass, p_classList, p_recursive );
                        }
                    }
                    else if ( association.getContainment() == MdfContainment.BY_REFERENCE )
                    {
                        p_propertyToTopClass.put( (MdfProperty) property, p_topClass );
                    }
                }
            }
        }
    }

    /**
     * Checks if is right adjusted.
     *
     * @param p_field the field
     * @return true, if is right adjusted
     */
    public static boolean getRightAdjust(Field p_field)
    {
        return VersionUtility.isYes( p_field.getRightAdjust() );
    }
    
    public static boolean isTextArea(MdfProperty p_property)
    {
        return T24Aspect.getTextArea( p_property );
    }
    
    /**
     * @param p_version the version
     * @return true if show null attribute is set
     */
    public static boolean getShowNullAttribute(Version p_version)
    {
    	 EList<String> attributes = p_version.getAttributes();
    	 if  ( attributes != null && attributes.contains( "SHOW.NULL.FIELDS" ) )	
    		 return true;
    	 else
    		 return false;
    }

    /**
     * Checks if p_field contains the specified attribute
     * 
     * @param p_field
     * @param p_attrName
     * @return False p_attrName is null or it doesn't have p_attrName, otherwise true
     */
    public static boolean hasAttribute(Field p_field, String p_attrName)
    {
        EList<String> attrs = ( p_attrName == null ) ? null : p_field.getAttributes();

        if ( attrs != null )
        {
            Iterator<String> attrIter = attrs.iterator();

            while ( attrIter.hasNext() )
            {
                if ( p_attrName.equals( attrIter.next() ) )
                    return true;
            }
        }

    	return false;
    }

    /**
     * Indicates if this field can be expanded when used within a table
     * 
     * @param p_field
     * @return true if could add/delete more values, false otherwise
     */
    public static boolean isExpandable(Field p_field)
    {
        return p_field.getExpansion() != Expansion.NO;
    }

    /**
     * Indicates if re-key is required for this field
     * 
     * @param p_field
     * @return true if needed, false otherwise
     */
    public static boolean isReKeyRequired(Field p_field)
    {
        return VersionUtility.isYes( p_field.getRekeyRequired() );
    }
    
}