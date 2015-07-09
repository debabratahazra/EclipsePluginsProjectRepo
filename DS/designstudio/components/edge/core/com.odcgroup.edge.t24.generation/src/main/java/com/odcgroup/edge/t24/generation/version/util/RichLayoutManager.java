package com.odcgroup.edge.t24.generation.version.util;

import gen.com.acquire.intelligentforms.entities.HeadingWrapper.EHeaderLevel;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationSectionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTabPaneWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.GenericLeafNode;
import com.acquire.intelligentforms.entities.GenericNode;
import com.acquire.intelligentforms.entities.LinkedEntity;
import com.acquire.intelligentforms.entities.presentation.PresentationObject;
import com.acquire.intelligentforms.entities.util.AbstractGenericNodeProcessor;
import com.acquire.intelligentforms.entities.util.EntityUtility;
import com.acquire.intelligentforms.entities.util.IGenericNodeProcessor;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;

/**
 * RichHTML version of the layout manager.
 */
public class RichLayoutManager extends AbstractLayoutManager<RichHTMLPresentationItemGroupWrapper, RichHTMLPresentationTableWrapper, RichHTMLPresentationQuestionWrapper, RichHTMLPresentationSpacingWrapper> 
{
    /**
     * Instantiates a new rich layout manager.
     *
     * @param p_formContext the form context
     * @param p_fields the fields
     * @param p_presentationContainer the presentation container
     * @param p_languageMapHelper the language map helper
     */
    public RichLayoutManager( FormContext p_formContext, GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer, LanguageMapHelper p_languageMapHelper )
    {
        super( p_formContext, p_processContainer, p_presentationContainer, p_languageMapHelper );
    }
    
    @Override
    public void revertToInitialState()
    {
        // Revert back to the original containers
        //
        setCurrentProcessContainer( getInitialProcessContainer() );
        setCurrentPresentationContainer( getInitialPresentationContainer() );
        
        super.revertToInitialState();
    }


    @Override
    protected RichHTMLPresentationSpacingWrapper createBlankLine()
    {
        return RichHelper.createBlankLine(getFormContext());
    }
   
    @Override
    protected RichHTMLPresentationSpacingWrapper createLine()
    {
        return RichHelper.createLine(getFormContext());
    }
    
    @Override
    protected RichHTMLPresentationQuestionWrapper createHeading(TextTranslations p_heading)
    {
        return RichHelper.createHeading(getFormContext(), p_heading, EHeaderLevel.HEADER_LEVEL2, getLanguageMapHelper() );
    }

    @Override
    protected RichHTMLPresentationTableWrapper createTable(TextTranslations p_tableSummary)
    {
        return RichHelper.createTable(getFormContext(), p_tableSummary, getLanguageMapHelper());
    }

    @Override
    protected RichHTMLPresentationItemGroupWrapper createItemGroup(String p_groupName)
    {
        return RichHelper.createItemGroup(getFormContext(), p_groupName );
    }
    
    /**
     * Creates and adds the tab and sets it as the current container.
     *
     * @param p_title the title
     * @param p_tabDisplay the tab display
     * @return the presentation tab pane type
     */
    public RichHTMLPresentationTabPaneWrapper addTab(TextTranslations p_title, RichHelper.ETabDisplay p_tabDisplay)
    {
        RichHTMLPresentationTabPaneWrapper tab = createTab( p_title, p_tabDisplay );
        
        addPresentationChild( tab );
        
        setCurrentPresentationContainer( tab );
        
        return tab;
    }
    
    /**
     * Creates the tab.
     *
     * @param p_title the title
     * @param p_tabDisplay the tab display
     * @return the rich html presentation tab pane wrapper
     */
    protected RichHTMLPresentationTabPaneWrapper createTab(TextTranslations p_title, RichHelper.ETabDisplay p_tabDisplay)
    {
        return RichHelper.createTab( getFormContext(), p_tabDisplay, p_title, getLanguageMapHelper() );
    }

    @Override
    public void addAcrossSection(PresentationSectionWrapper p_section)
    {
        getInitialPresentationContainer().addChild( p_section );
        
        EntityUtility.processChildrenInTreeOrder( p_section.unwrap(), new AbstractGenericNodeProcessor()
        {
            @Override
            public int process(GenericLeafNode p_child, LinkedEntity p_linkedEntity, GenericNode p_parent, Object p_userObject)
            {
                GenericLeafNode entityNode = (GenericLeafNode) ((PresentationObject)p_child).getEntityNode();
                
                if  ( entityNode != null )
                {
                    getInitialProcessContainer().addChild( entityNode );
                    
                    return IGenericNodeProcessor.CONTINUE_PROCESSING_DO_NOT_RECURSE;
                }
                
                return IGenericNodeProcessor.CONTINUE_PROCESSING;
            }
            
        } );
        
    }
}
