package com.odcgroup.edge.t24.generation.version.util;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationSectionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationTableWrapper;

import com.acquire.intelligentforms.FormContext;
import com.edgeipk.builder.GenericLeafNodeWrapper;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;

/**
 * Base layout manager which simply adds entities directly to the current container.<p>
 *
 * @author sakbar
 */
public abstract class AbstractLayoutManager<PresItemGroupType extends PresentationItemGroupWrapper, PresTableType extends PresentationTableWrapper, PresQuestionType extends PresentationQuestionWrapper, PresSpacingType extends PresentationSpacingWrapper> 
{
    
    /** The form context. */
    private final FormContext m_formContext;
    
    /** The language map helper. */
    private final LanguageMapHelper m_languageMapHelper;

    /** The initial presentation container. */
    private PresentationObjectWrapper<?> m_initialPresentationContainer;
    
    /** The current presentation container. */
    private PresentationObjectWrapper<?> m_currentPresentationContainer;

    /** The initial process container. */
    private GenericNodeWrapper<?> m_initialProcessContainer;
    
    /** The current process container. */
    private GenericNodeWrapper<?> m_currentProcessContainer;

    /**
     * Instantiates a new abstract field layout manager.
     *
     * @param p_formContext the form context
     * @param p_fields the fields
     * @param p_processContainer the process container
     * @param p_presentationContainer the presentation container
     * @param p_languageMapHelper the languageMapHelper map helper
     */
    public AbstractLayoutManager( FormContext p_formContext, GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer, LanguageMapHelper p_languageMapHelper )
    {
        m_formContext = p_formContext;
        m_initialPresentationContainer = m_currentPresentationContainer = p_presentationContainer;
        m_initialProcessContainer = setCurrentProcessContainer( p_processContainer );
        m_languageMapHelper = p_languageMapHelper;
    }

    /**
     * Gets the form context.
     *
     * @return the form context
     */
    public FormContext getFormContext()
    {
        return m_formContext;
    }

    /**
     * Sets the current presentation container.
     *
     * @param p_presentationContainer the presentation container
     * @return the presentation object wrapper
     */
    public PresentationObjectWrapper<?> setCurrentPresentationContainer(PresentationObjectWrapper<?> p_presentationContainer)
    {
        m_currentPresentationContainer = p_presentationContainer;
        
        return m_currentPresentationContainer;
    }

    /**
     * Gets the initial presentation container.
     *
     * @return the initialPresentationContainer
     */
    public PresentationObjectWrapper<?> getInitialPresentationContainer()
    {
        return m_initialPresentationContainer;
    }

    /**
     * Gets the current presentation container.
     *
     * @return the current presentation container
     */
    public PresentationObjectWrapper<?> getCurrentPresentationContainer()
    {
        return m_currentPresentationContainer;
    }

    /**
     * Gets the initial process container.
     *
     * @return the initial process container
     */
    public GenericNodeWrapper<?> getInitialProcessContainer()
    {
        return m_initialProcessContainer;
    }

    /**
     * Gets the current process container.
     *
     * @return the current process container
     */
    public GenericNodeWrapper<?> getCurrentProcessContainer()
    {
        return m_currentProcessContainer;
    }

    /**
     * Sets the current process container.
     *
     * @param p_currentProcessContainer the current process container
     * @return the generic node wrapper
     */
    public GenericNodeWrapper<?> setCurrentProcessContainer(GenericNodeWrapper<?> p_currentProcessContainer)
    {
        m_currentProcessContainer = p_currentProcessContainer;
        return p_currentProcessContainer;
    }

    /**
     * Reset initial & current process containers to the one specified
     *
     * @param p_processContainer the process container
     * @return the new initial container
     */
    public GenericNodeWrapper<?> resetProcessContainers(GenericNodeWrapper<?> p_processContainer)
    {
        m_initialProcessContainer = m_currentProcessContainer = p_processContainer;
        
        return m_initialProcessContainer;
    }

    /**
     * Reset initial & current presentation containers to the one specified
     *
     * @param p_presentationContainer the presentation container
     * @return the new initial container
     */
    public PresentationObjectWrapper<?> resetPresentationContainers(PresentationObjectWrapper<?> p_presentationContainer)
    {
        m_initialPresentationContainer = m_currentPresentationContainer = p_presentationContainer;
        
        return m_initialPresentationContainer;
    }
   
    /**
     * Indicates we've finished processing, so now revert to a suitable state with the containers as further items maybe added.<p>
     * 
     * NB: Always invoke the super.revertToInitialState() at the end of any overridden version.
     */
    protected void revertToInitialState()
    {
        // Non-abstract for future .. should always be invoked
    }

    /**
     * Gets the language map helper.
     *
     * @return the language map helper
     */
    public LanguageMapHelper getLanguageMapHelper()
    {
        return m_languageMapHelper;
    }

    /**
     * Adds the presentation child to the current presentation container.
     *
     * @param p_child the child
     * @return the string
     */
    public String addPresentationChild(PresentationObjectWrapper<?> p_child)
    {   
        return m_currentPresentationContainer.addChild( p_child );
    }

    /**
     * Adds the child to the current process container.
     *
     * @param p_child the child
     * @return the string
     */
    public String addProcessChild(GenericLeafNodeWrapper<?> p_child)
    {   
        return m_currentProcessContainer.addChild( p_child );
    }
   
    /**
     * Creates and add a blank line.
     *
     * @return the presentation spacing type
     */
    public PresSpacingType addBlankLine()
    {
        PresSpacingType blankLine = createBlankLine();
        
        addPresentationChild( blankLine );
        
        return blankLine;
    }
    
    /**
     * Creates a blank line.
     *
     * @return the presentation spacing type
     */
    protected abstract PresSpacingType createBlankLine();

    
    /**
     * Creates and add a line.
     *
     * @return the presentation spacing type
     */
    public PresSpacingType addLine()
    {
        PresSpacingType blankLine = createLine();
        
        addPresentationChild( blankLine );
        
        return blankLine;
    }
    
    /**
     * Creates a line.
     *
     * @return the presentation spacing type
     */
    protected abstract PresSpacingType createLine();
    
    
    /**
     * Creates and adds a heading.
     *
     * @param p_headingText the heading text
     * @param p_processContainer the process container
     * @param p_t24Translations the t24 translations
     * @return the presentation question type
     */
    public PresQuestionType addHeading(TextTranslations p_heading)
    {
        PresQuestionType presHeading = createHeading( p_heading );
        
        addPresentationChild( presHeading );
        
        addProcessChild( (HeadingWrapper) presHeading.getEntityNodeWrapper() );
        
        return presHeading;
    }

    
    protected abstract PresQuestionType createHeading(TextTranslations p_heading);

    
    /**
     * Creates and adds the table and sets it as current container.
     *
     * @param p_title the title
     * @return the presentation table type
     */
    public PresTableType addTable(TextTranslations p_title)
    {
        PresTableType presTable = createTable( p_title );
        
        addPresentationChild( presTable );
        
        FormTableWrapper table = (FormTableWrapper) presTable.getEntityNodeWrapper();

        addProcessChild( table );
        
        setCurrentProcessContainer( table );
        
        setCurrentPresentationContainer( presTable );
        
        return presTable;
    }
    
    protected abstract PresTableType createTable(TextTranslations p_tableSummary);
    
    /**
     * Creates and adds the item group and sets it as the current container
     *
     * @param p_groupName the group name
     * @return the presentation item group type
     */
    public PresItemGroupType addItemGroup(String p_groupName)
    {
        PresItemGroupType presItemGroup = createItemGroup( p_groupName );
        
        addPresentationChild( presItemGroup );
        
        ItemGroupWrapper itemGroup = (ItemGroupWrapper) presItemGroup.getEntityNodeWrapper();

        addProcessChild( itemGroup );
        
        setCurrentProcessContainer( itemGroup );
        
        setCurrentPresentationContainer( presItemGroup );
        
        return presItemGroup; 
    }

    /**
     * Creates the item group.
     *
     * @param p_groupName the group name
     * @return the presentation table type
     */
    protected abstract PresItemGroupType createItemGroup(String p_groupName);

    /**
     * Adds the specified section, across the entire length of the layout, by adding to the initial presentation container.<p>
     *
     * It will also add any process objects contained to the initial process container.
     * 
     * @param p_section the section
     */
    public abstract void addAcrossSection(PresentationSectionWrapper p_section);
    
}
