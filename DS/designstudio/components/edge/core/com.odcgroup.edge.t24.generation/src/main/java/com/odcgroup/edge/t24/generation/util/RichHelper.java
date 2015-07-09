package com.odcgroup.edge.t24.generation.util;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper.EHeaderLevel;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTabPaneWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import java.math.BigDecimal;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.presentation.html.HTMLPresentationFormatBreak;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTabPane;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 * Helper class for creating rich presentation wrappers
 *
 * @author sakbar
 */
public class RichHelper
{
    private RichHelper() {}

    /**
     * Section Display options:
     * 
     * <ul>
     *  <li>STANDARD_SECTION</li>
     *  <li>JQUERY_SECTION</li>
     *  <li>ACCORDIAN_SECTION</li>
     * </ul>
     *  
     */
    public enum ESectionDisplay 
    {
        STANDARD_SECTION,
        JQUERY_TABSECTION,
        ACCORDIAN_SECTION,
        CONTENT_BLOCK_SECTION;
    }
    
    /**
     * Tab Display options:
     * 
     * <ul>
     *  <li>STANDARD_TAB</li>
     *  <li>JQUERY_TAB</li>
     *  <li>ACCORDIAN_TAB</li>
     * </ul>
     *  
     */
    public enum ETabDisplay 
    {
        STANDARD_TAB,
        JQUERY_TAB,
        ACCORDIAN_TAB;
    }
    
    /**
     * Creates the section.
     *
     * @param p_formContext the form context
     * @param p_sectionDisplay the section display. See {@link ESectionDisplay}
     * @return non-null section
     */
    public static RichHTMLPresentationFormatBreakWrapper createSection(FormContext p_formContext, ESectionDisplay p_sectionDisplay)
    {
        RichHTMLPresentationFormatBreakWrapper section = RichHTMLPresentationFormatBreakWrapper.create( p_formContext );
    
        switch (p_sectionDisplay)
        {
            case STANDARD_SECTION:
                section.setDisplayType( HTMLPresentationFormatBreak.DISPLAY_TYPE_STANDARD );
                break;

            case JQUERY_TABSECTION:
                section.setDisplayType( "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|jqueryTabSection" );
                section.setAttribute("DisplayTypetabPattern","tabs"); // FIXME: Is this needed?
                break;
                
            case ACCORDIAN_SECTION:
                section.setDisplayType( "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Accordion Section (jquery-ui)" );
                section.setAttribute( "DisplayTypeMAINTAIN_STATE", "Y" );
                section.setAttribute( "DisplayTypeSLIDE_EFFECT", "Y" );
                break;

            case CONTENT_BLOCK_SECTION:
                section.setDisplayType( HTMLPresentationFormatBreak.DISPLAY_TYPE_STANDARD );
                section.setDesignToUseFromEntityKey( "Content Block" );
                section.setSectionStyle( "ContentBlock" );
                break;
                
            default:
                throw new UnsupportedOperationException("Unhandled case: " + p_sectionDisplay);
        }
        
        return section;
    }


    /**
     * Creates the tab.
     *
     * @param p_formContext the form context
     * @param p_tabDisplay the tab appearance. See {@link ETabDisplay}
     * @param p_title the title
     * @param p_languageMapHelper the language map helper (Optional)
     * @return the rich html presentation tab pane wrapper
     */
    public static RichHTMLPresentationTabPaneWrapper createTab(FormContext p_formContext, ETabDisplay p_tabDisplay, TextTranslations p_title, LanguageMapHelper p_languageMapHelper)
    {
        RichHTMLPresentationTabPaneWrapper tab = RichHTMLPresentationTabPaneWrapper.create( p_formContext );
        
        tab.setTabName( p_title.getText() );
        
        if  ( p_languageMapHelper != null && p_title != null )
        {
            p_languageMapHelper.registerT24TextTranslations( tab, p_title );
        }
        
        switch (p_tabDisplay)
        {
            case STANDARD_TAB:
                tab.setDisplayType( RichHTMLPresentationTabPane.DISPLAY_TYPE_STANDARD );
                break;
                
            case JQUERY_TAB:
                tab.setDisplayType( "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|jqueryTab" );

                break;
                
            case ACCORDIAN_TAB:
                tab.setDisplayType( "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Accordion (jquery-ui)" );
                break;

            default:
                throw new UnsupportedOperationException("Unhandled case: " + p_tabDisplay);
        }
    
        return tab;
    }
    
    /**
     * Creates the heading.
     *
     * @param p_formContext the form context
     * @param p_heading the heading
     * @param p_headerLevel the header level
     * @param p_languageMapHelper the language map helper
     * @return the rich html presentation question wrapper
     */
    public static RichHTMLPresentationQuestionWrapper createHeading(FormContext p_formContext, TextTranslations p_heading, EHeaderLevel p_headerLevel, LanguageMapHelper p_languageMapHelper )
    {
        HeadingWrapper heading = HeadingWrapper.create( p_formContext, p_heading.getText() );
        
        p_languageMapHelper.registerT24TextTranslations(heading, p_heading);

        heading.setHeaderLevel( p_headerLevel );
        
        RichHTMLPresentationQuestionWrapper presHeading = RichHTMLPresentationQuestionWrapper.create( p_formContext, heading.unwrap() );

        return(presHeading);
    }
    
    /**
     * Creates the table (with auto selector enabled).
     *
     * @param p_formContext the form context
     * @param p_tableSummary the table summary
     * @param p_languageMapHelper the language map helper
     * @return the rich html presentation table wrapper
     */
    public static RichHTMLPresentationTableWrapper createTable(FormContext p_formContext, TextTranslations p_tableSummary, LanguageMapHelper p_languageMapHelper)
    {
        FormTableWrapper table = FormTableWrapper.create( p_formContext );
        
        table.setTableTitle( p_tableSummary.getText() );
        
        // FIXME: Zap as no longer needed as auto selector will be true for the next build
        //
        table.setAutoSelector( true );
        
        RichHTMLPresentationTableWrapper presTable = RichHTMLPresentationTableWrapper.create( p_formContext, table.unwrap() );
        
        presTable.setTableSummary( p_tableSummary.getText() );
        
        p_languageMapHelper.registerT24TextTranslations( presTable, p_tableSummary );
        
        return presTable;
    }
    
    /**
     * Creates the item group.
     *
     * @param p_formContext the form context
     * @param p_groupName the group name
     * @return the rich html presentation item group wrapper
     */
    public static RichHTMLPresentationItemGroupWrapper createItemGroup(FormContext p_formContext, String p_groupName)
    {
        ItemGroupWrapper ig = ItemGroupWrapper.create( p_formContext, p_groupName );
        
        RichHTMLPresentationItemGroupWrapper pig = RichHTMLPresentationItemGroupWrapper.create( p_formContext, ig.unwrap() );

        return pig;
    }
    
    /**
     * Creates the blank line.
     *
     * @param p_formContext the form context
     * @return the rich html presentation spacing wrapper
     */
    public static RichHTMLPresentationSpacingWrapper createBlankLine(FormContext p_formContext)
    {
        RichHTMLPresentationSpacingWrapper presSpacingWithABlankLine = RichHTMLPresentationSpacingWrapper.create( p_formContext );

        return presSpacingWithABlankLine;
    }

    /**
     * Creates the line.
     *
     * @param p_formContext the form context
     * @return the rich html presentation spacing wrapper
     */
    public static RichHTMLPresentationSpacingWrapper createLine(FormContext p_formContext)
    {
        RichHTMLPresentationSpacingWrapper presSpacingWithALine = RichHTMLPresentationSpacingWrapper.create( p_formContext );

        presSpacingWithALine.setSpacingType( RichHTMLPresentationSpacingWrapper.ESpacingType.LINE );

        return presSpacingWithALine;
    }

    /**
     * Creates the column.
     *
     * @param p_formContext the form context
     * @return the rich html presentation format break wrapper
     */
    public static RichHTMLPresentationColumnBreakWrapper createColumn(FormContext p_formContext)
    {
        RichHTMLPresentationColumnBreakWrapper colBreak = RichHTMLPresentationColumnBreakWrapper.create( p_formContext );

        return colBreak;
    }


    /**
     * Sets the horizontal overflow.
     *
     * @param p_section to set horizontal overflow
     */
    public static void setHorizontalOverflow(RichHTMLPresentationFormatBreakWrapper p_section)
    {
        p_section.setSectionStyle( "{overflow-x: auto; overflow-y: hidden;}" );
    }
    
    /**
     * Switch to a custom MV table question.
     *
     * @param p_question the question
     */
    public static void switchToCustomMVTableQuestion(RichHTMLPresentationQuestionWrapper p_question)
    {
        p_question.setDesignToUseFromEntityKey( "Custom" );

        p_question.setEditDesignToUse( "N" );

        p_question.setRowStyle( "QRow" );

        p_question.setQuestionStyle( "FontGrey QQues" );

        p_question.setMandatoryStyle( "QMand" );

        p_question.setPrefixStyle( "QPref" );

        p_question.setAnswerStyle( "QAns" );

        p_question.setAnswerControlStyle( "FontDarkGrey" );

        p_question.setPostfixStyle( "QPost" );

        p_question.setHelpStyle( "QHelp" );
    }

    /**
     * Switch to custom MV collapsible table.
     *
     * @param p_presTable the presentation table
     */
    public static void switchToCustomMVCollapsibleTable(RichHTMLPresentationTableWrapper p_presTable)
    {
        p_presTable.setDesignToUseFromEntityKey( "Custom" );
    
        p_presTable.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Collapsible Table" );
    
        p_presTable.setAttribute( "DisplayTypeINITIALLY_EXPANDED", "Y" );
    
        p_presTable.setAttribute( "DisplayTypeTRANSITION_DURATION", "5" );
    
        p_presTable.setAttribute( "DisplayTypeTRANSITION_EFFECT", "linearEasing" );
    
        p_presTable.setAttribute( "DisplayTypeTRANSITION_TYPE", "fade" );
    
        p_presTable.setTableStyle( "verMVTable" );
    
        p_presTable.setTableHeaderRowStyle( "verMVTableHeader" );
    
        p_presTable.setOddRowsStyle( "verMVTableOddRows VerticalAlign" );
    
        p_presTable.setEvenRowsStyle( "VerticalAlign" );
    
        p_presTable.setCellPadding( BigDecimal.valueOf(2) );
    
        p_presTable.setSortable( false );
    
        p_presTable.setHideSelector( true );
    
        p_presTable.setSelectedRowStyle( "BgColor5" );
    
        p_presTable.setRollOverStyle( "BgColor5" );
    }
    
    /**
     * Sets the standard date format as dd/mm/yyyy and removes the default icon.
     *
     * @param p_presQuestion the new standard date format
     */
    public static void setStandardDateFormatWithoutIcon(RichHTMLPresentationQuestionWrapper p_presQuestion)
    {
        // Q. Can the date format be the default overall (even for enquiry) or maybe a design, so no need for this?
        //
        p_presQuestion.setDateFormatType( RichHTMLPresentationQuestionWrapper.EDateFormatType.SPECIFY_DATE_FORMAT );
        p_presQuestion.setDateFormat( " True, False, /, Day, Text, , , /, Month, Text, , , /, Year, Text, , , False, False, :, Hours, Text, , , :, Minutes, Text, , , :, Seconds, Text, , , Specify, dd, Specify, mm, Specify, yyyy, Specify, hh, Specify, mm, Specify, ss, True, T, False" );
    }


    /**
     * Sets the multi language question widget.
     *
     * @param p_presQuestion the presentation question
     * @param p_irisDomainResourceNameForForeignKeyField the iris domain resource name for foreign key field
     */
    public static void setMultiLanguageQuestionWidget(RichHTMLPresentationQuestionWrapper p_presQuestion, final String p_irisDomainResourceNameForForeignKeyField)
    {
        p_presQuestion.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|MultiLanguage Question" );
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField);
    }

    /**
     * Sets the enquiry drop down widget.
     *
     * @param p_presQuestion the presentation question
     * @param p_irisDomainResourceNameForForeignKeyField the iris domain resource name for foreign key field
     * @param p_enquiryName the enquiry name, such as ACCOUNT or CUSTOMER
     * @param p_isEnrichmentOnly the field attribute enrichment only 
     */
    public static void setEnquiryDropDownWidget(RichHTMLPresentationQuestionWrapper p_presQuestion, final String p_irisDomainResourceNameForForeignKeyField, String p_enquiryName,boolean p_isEnrichmentOnly)
    {
        String enqListURL = "enqlist" + MapperUtility.processT24NameToIRISName( p_enquiryName );
        
        if  ( "ACCOUNT".equalsIgnoreCase( p_enquiryName ) || "CUSTOMER".equalsIgnoreCase( p_enquiryName ) )
        {
            enqListURL += "Search()";
        }
        else
        {
            enqListURL += "s()";
        }
        
        p_presQuestion.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Dropdown v2" );
        p_presQuestion.setAttribute( "DisplayTypeResourceURL", enqListURL );
        p_presQuestion.setAttribute( "DisplayTypePrimaryKey", "Id" ); // toCamelCase( domainPrimaryKey ) 
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField);
        
        setEnrichmentOnly( p_presQuestion, p_isEnrichmentOnly );
    }


    /**
     * @param p_presQuestion the presentation question
     * @param p_isEnrichmentOnly the field attribute enrichment only
     */
    private static void setEnrichmentOnly(RichHTMLPresentationQuestionWrapper p_presQuestion, boolean p_isEnrichmentOnly)
    {
        p_presQuestion.setAttribute( "DisplayTypeEnrichmentOnly", p_isEnrichmentOnly ? "Y" : "N");      
        
        if  ( p_isEnrichmentOnly )
        {
            p_presQuestion.setInfoStyle("EnrichmentOnly");
        }
    }

    /**
     * Sets the enquiry drop down widget based on already processed enquiryName.
     *
     * @param p_presQuestion the presentation question
     * @param p_irisDomainResourceNameForForeignKeyField the iris domain resource name for foreign key field
     * @param p_enquiryURL the iris resource
     * @param p_isEnrichmentOnly the field attribute enrichment only
     */
    public static void setEnquiryDropDownWidgetForEnquiry(RichHTMLPresentationQuestionWrapper p_presQuestion, final String p_irisDomainResourceNameForForeignKeyField, String p_enquiryURL, boolean p_isEnrichmentOnly)
    {
        p_presQuestion.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Dropdown v2" );
        p_presQuestion.setAttribute( "DisplayTypeResourceURL", p_enquiryURL );
        p_presQuestion.setAttribute( "DisplayTypePrimaryKey", "Id" ); // toCamelCase( domainPrimaryKey ) 
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField);
        
        setEnrichmentOnly( p_presQuestion, p_isEnrichmentOnly );
    }
    
   /**
    * Sets the frequency control widget.
    *
    * @param p_presQuestion the presentation question
    * @param p_businessType the business type
    * @param p_typeModifiers the type modifiers
    * @param p_irisDomainResourceNameForForeignKeyField the iris domain resource name for foreign key field
    * @param p_hasRecurrencePopupControlBehaviour the popup behaviour recurrence
    * @param p_isEnrichmentOnly the field attribute enrichment only
    */
    public static void setFrequencyControlWidget(RichHTMLPresentationQuestionWrapper p_presQuestion, String p_businessType, String p_typeModifiers, final String p_irisDomainResourceNameForForeignKeyField, boolean p_hasRecurrencePopupControlBehaviour, boolean p_isEnrichmentOnly, String p_questionName)
    {
        //TODO:check enrichment only for frequency field in versions.
        
        p_presQuestion.setAttribute( "DisplayTypeImageUrl", "images/calendarUI.gif" );
        p_presQuestion.setAttribute( "DisplayTypeEnableJQueryEnrichment", "N" ); // Not really required. At the moment the date box inside frequency appears as a span if this is set. A checkbox option.
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField); // Not really required. Applies the css to the displayed input box

        
        if(!("YES".equals( p_presQuestion.unwrap().getAttribute( "IsEnquiry"))))
        {
            //Only for versions enrichment only applies... 
            p_presQuestion.setAttribute( "DisplayTypeQuestionName", p_questionName);
            setEnrichmentOnly( p_presQuestion, p_isEnrichmentOnly );
        } 
        // Switch to FrequencyControl .. but rely on calendar specific settings from above
        //
        p_presQuestion.setDisplayType("GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|FrequencyControl");
        
        p_presQuestion.setAttribute("DisplayTypeFrequencyOption", "Frequency with None"); //business type eq FQO
        
        if ( "FQU".equals(p_businessType) || p_hasRecurrencePopupControlBehaviour) 
        {
            if ( "EXTENDED,DATE".equals( p_typeModifiers ) )
                p_presQuestion.setAttribute( "DisplayTypeFrequencyOption", "Extended with Date" );

            else if ( "EXTENDED,DATE,NONE".equals( p_typeModifiers ) )
                p_presQuestion.setAttribute( "DisplayTypeFrequencyOption", "Extended with None" );

            else if ( "EXTENDED".equals( p_typeModifiers ) || p_hasRecurrencePopupControlBehaviour)
                p_presQuestion.setAttribute( "DisplayTypeFrequencyOption", "Extended Frequency" );
            
            else if ("".equals(p_typeModifiers))
                p_presQuestion.setAttribute( "DisplayTypeFrequencyOption", "Frequency" );
        }
        
    }

    /**
     * Sets the calendar widget.
     *
     * @param p_presQuestion the presentation question
     * @param p_irisDomainResourceNameForForeignKeyField the iris domain resource name for foreign key field
     * @param p_enrichmentOnly the field attribute enrichment only.
     */
    public static void setCalendarWidget(RichHTMLPresentationQuestionWrapper p_presQuestion, final String p_irisDomainResourceNameForForeignKeyField, boolean p_enrichmentOnly, String p_questionName)
    {
        //setStandardDateFormatWithoutIcon( p_presQuestion );
        
        // Adding the custom parameters for calendarUI calendarUI widget for versions only
        //
        p_presQuestion.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|calendarUI" );
        p_presQuestion.setAttribute( "DisplayTypeImageUrl", "images/calendarUI.gif" );
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField); // Not really required as this is applied only to the hidden element and not to the visible element
        p_presQuestion.setAttribute( "DisplayTypeQuestionName", p_questionName); // used to trigger QLR for populating enrichments. To be revisited        
        setEnrichmentOnly( p_presQuestion, p_enrichmentOnly );
    }
    
    
    /**
     * Sets the calendar widget.
     *
     * @param p_presQuestion the presentation question
     * @param p_irisDomainResourceNameForForeignKeyField the iris domain resource name for foreign key field
     * @param p_enrichmentOnly the field attribute enrichment only.
     */
    public static void setCalendarWidgetV2(RichHTMLPresentationQuestionWrapper p_presQuestion, final String p_irisDomainResourceNameForForeignKeyField, boolean p_enrichmentOnly, String p_questionName)
    {
        //setStandardDateFormatWithoutIcon( p_presQuestion );
        
        // Adding the custom parameters for calendarUI calendarUI widget for enquiries only
        //
        p_presQuestion.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|calendarUI v2" );
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField);// Not really required as this is applied only to the hidden element and not to the visible element
        p_presQuestion.setAttribute( "DisplayTypeImageUrl", "images/calendarUI.gif" );
        
    }
    
    public static void setRelativeCalendarWidget(RichHTMLPresentationQuestionWrapper p_presQuestion, final String p_typeModifiers, final String p_irisDomainResourceNameForForeignKeyField, boolean p_isEnrichmentOnly, String p_questionName)
    {
        //setStandardDateFormatWithoutIcon( p_presQuestion );
        
        final String[] relDateTypeModifiers = p_typeModifiers.split("\uF8FB" );        
        
        final String relDate = relDateTypeModifiers[0];
        
        final String relDuration = relDateTypeModifiers[3];
        
        final String relDateTypeModifiersAsString = relDateTypeModifiers.toString();
        
        final String relOffSetNeg = (relDateTypeModifiersAsString.contains( "PERIOD" ) && relDateTypeModifiersAsString.contains( "NEGATIVE" ))?"YES":"NO";
        
        p_presQuestion.setAttribute( "DisplayTypeTextInputCSSClassNames", p_irisDomainResourceNameForForeignKeyField);// Not really applied in the widget. Left for future use.
        
        /*if(!("YES".equals( p_presQuestion.unwrap().getAttribute( "IsEnquiry"))))
        {
            //Actually not required as DP business type fields in AA dont have enrichments and only the ID.COMP.6 field has a tab enrichment which is irrelevant to this.
            p_presQuestion.setAttribute( "DisplayTypeQuestionName", p_questionName);
            setEnrichmentOnly( p_presQuestion, p_isEnrichmentOnly );
        }*/
        // Adding the custom parameters for relativeCalendar widget
        //
        p_presQuestion.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|RelativeCalendarControl");
        p_presQuestion.setAttribute( "DisplayTypeRelDate",relDate);
        p_presQuestion.setAttribute( "DisplayTypeRelOffSetNeg",relOffSetNeg);
        p_presQuestion.setAttribute( "DisplayTypeRelDuration",relDuration);
        // not a forward dated widget
        p_presQuestion.setAttribute( "DisplayTypeForwardDatedApplicable","NO");
    }


    /**
     * Sets the text area
     * 
     * @param p_presQuestion
     * @param p_length
     * @param p_rows
     * @param p_enableSpellCheck
     */
    public static void setTextArea(RichHTMLPresentationQuestionWrapper p_presQuestion, int p_length, int p_rows, boolean p_enableSpellCheck)
    {
        p_presQuestion.setDisplayType( "Text Area" );
    
        p_presQuestion.setFieldSize( p_length );
    
        p_presQuestion.setNumberOfRows( p_rows );
    
        p_presQuestion.setReadOnlyFormat( RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER );
        
        p_presQuestion.setSpellCheck( p_enableSpellCheck );
    }
    
    
    public static String getTypeModifiers(MdfProperty p_mdfProperty)
    {
        
        final String typeModifier =T24Aspect.getTypeModifiers(p_mdfProperty);
        return ( typeModifier == null ) ? "" : typeModifier;
    }
    
    public static String getBusinessType(MdfProperty p_mdfProperty)
    {
        MdfProperty mdfProperty = p_mdfProperty;
        MdfEntity mdfEntity = mdfProperty.getType();
        String businessType;
        if(mdfEntity instanceof MdfBusinessType)
        {
            businessType = mdfEntity.getName() ;
        }
        else if(mdfProperty instanceof MdfAttribute || mdfProperty instanceof MdfAssociation)
        {   
            businessType = T24Aspect.getBusinessType(mdfProperty);          
        }
        else
        {
            throw new IllegalArgumentException("Bug in model or code, unclear how to obtain BT from: " + mdfProperty.toString());
        }
        
        final String p_businessType = ( businessType == null ) ? "" : businessType;
        
        return p_businessType;
    }
}
