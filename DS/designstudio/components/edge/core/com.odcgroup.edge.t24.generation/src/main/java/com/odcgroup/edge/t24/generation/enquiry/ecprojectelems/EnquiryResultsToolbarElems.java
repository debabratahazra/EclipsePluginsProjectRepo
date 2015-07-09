package com.odcgroup.edge.t24.generation.enquiry.ecprojectelems;

import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTextWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.presentation.PresentationObject;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.pattern.container.BasicProject;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryTemplateIFPConstants;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.ButtonWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.ItemGroupWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.QuestionWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.VisualItemContainerWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.VisualWrapperObjectPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryInlineStyleBlocks;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsToolbarColumnDesignOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsToolbarContentDesignOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsToolbarSectionDesignOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.AutoRefreshControlSetWidget;


/**
 * <code>EnquiryResultsToolbarElems</code> simplifies managing the layout of the edgeConnect elements comprising the "Search Results" screen's toolbar by solving the following
 * problems:
 * 
 * <ol>
 *     <li>
 *         at the point where it is known that a toolbar needs to be added (and exactly WHERE it needs to be added relative to other elements), we haven't yet discovered enough
 *         information to know all of the elements that might need to added to it
 *     </li>
 *     <li>
 *         for an Enquiry with the "NO.TOOLBAR" option set, we actually need to provide some of the Enquiry-specific actions (that would otherwise have been provided in the
 *         standard toolbar) in a different container
 *     </li>
 *     <li>
 *         but - regardless of the container to which applicable actions are being added, and the subset of actions that actually turn out to be applicable - we ALWAYS want those
 *         actions be laid out in a standard, pre-defined order relative to each other
 *     </li>
 * </ol><p>
 * 
 * This class helps by:
 * 
 * <ol>
 *     <li>
 *         automatically adding the full set of presentation column break elements necessary for a fully populated toolbar on
 *         {@link #EnquiryResultsToolbarElems(BasicProject, VisualItemContainerWrapperPair) construction}, so allowing the main generation flow to continue without having to
 *         worry about how to "come back" to do this later
 *     </li>
 *     <li>
 *         toolbar action elements can then be added incrementally (and in any order) via the variious <code>addXXXXX</code> methods of this class as and when convenient in the main
 *         generation flow. Note that the various <code>addXXXXX</code> method defer the actual addition, merely "enqueing" the item to be added to the list of pending items for the
 *         appropriate toolbar column
 *     </li>
 *     <li>
 *         once all of the applicable sub-elements have been added, the main generation flow invokes the {@link #flushAllDeferredAddsAndRemoveAnyEmptyColumns()}, which (i) deletes any empty
 *         presentation columns, and (ii) populates the non-empty ones with their pending children (adding the process counterpart of each presentation item - in the same order - as it
 *         goes).
 *     </li>
 * </ol> 
 *
 * @author  Simon Hayes
 */
public class EnquiryResultsToolbarElems implements BasicEnquiryTemplateIFPConstants
{
    private final FormContext m_formContext;
    private final ItemGroupWrapperPair m_toolbarItemGroupWrapperPair;
    private final VisualItemContainerWrapperPair m_toolbarContainerWrapperPair;
    private final boolean m_isPseudoEnquiryResultsToolbar;
    
    private final SortedMap<ToolbarColumnId,ToolbarColumn> m_toolbarColumnById = new TreeMap<ToolbarColumnId,ToolbarColumn>();
    
    public EnquiryResultsToolbarElems(
        BasicProject p_project,
        String p_toolbarItemGroupName,
        VisualItemContainerWrapperPair p_toolbarParentContainerWrapperPair,
        PropertyWrapper p_searchOutcomeItem,
        boolean p_isPseudoEnquiryResultsToolbar
    ) {
        AssertionUtils.requireNonNullAndNonEmpty(p_toolbarItemGroupName, "p_toolbarItemGroupName");
        AssertionUtils.requireNonNull(p_project, "p_project");
        AssertionUtils.requireNonNull(p_toolbarParentContainerWrapperPair, "p_toolbarContainer");
        AssertionUtils.requireNonNull(p_searchOutcomeItem, "p_searchOutcomeItem");
        
        m_formContext = p_project.getFormContext();
        m_isPseudoEnquiryResultsToolbar = p_isPseudoEnquiryResultsToolbar;
        
        final ItemGroupWrapper toolbarItemGroup = ItemGroupWrapper.create(m_formContext, p_toolbarItemGroupName);
        final RichHTMLPresentationItemGroupWrapper toolbarPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(m_formContext, toolbarItemGroup.unwrap());
        m_toolbarItemGroupWrapperPair = new ItemGroupWrapperPair(toolbarItemGroup, toolbarPresItemGroup);
        
        p_toolbarParentContainerWrapperPair.addChild(m_toolbarItemGroupWrapperPair);
        
        final RichHTMLPresentationFormatBreakWrapper toolbarPresSection = addToolbarPresentationSectionTo(toolbarPresItemGroup);
        final ResultsToolbarSectionDesignOption toolbarPresSectionDesignOption = m_isPseudoEnquiryResultsToolbar ? ResultsToolbarSectionDesignOption.PSEUDO_RESULTS_TOOLBAR_SECTION : ResultsToolbarSectionDesignOption.STANDARD_RESULTS_TOOLBAR_SECTION;
        
        toolbarPresSectionDesignOption.applyTo(toolbarPresSection);
       
        final RichHTMLPresentationFormatBreakWrapper toolbarPresContentSection =  RichHTMLPresentationFormatBreakWrapper.create(m_formContext);
        final ResultsToolbarContentDesignOption toolbarContentPresSectionDesignOption = m_isPseudoEnquiryResultsToolbar ? ResultsToolbarContentDesignOption.PSEUDO_RESULTS_TOOLBAR_CONTENT_SECTION : ResultsToolbarContentDesignOption.STANDARD_RESULTS_TOOLBAR_CONTENT_SECTION;
        
        toolbarContentPresSectionDesignOption.applyTo(toolbarPresContentSection);
        toolbarPresSection.addChild(toolbarPresContentSection);

        m_toolbarContainerWrapperPair = new VisualItemContainerWrapperPair(toolbarItemGroup, toolbarPresContentSection);
        
        final String searchResultsFoundConditionExpression = "$$" + p_searchOutcomeItem.getEntityKeyName() + "$ == '" + Lists.SearchOutcome.Keys.RESULTS_FOUND + "'";
        addAllColumnsToToolbarContainer(searchResultsFoundConditionExpression);
    }
    
    public ItemGroupWrapperPair getToolbarItemGroupWrapperPair()
    {
        return m_toolbarItemGroupWrapperPair;
    }
    
    public void addSelectionScreenButton(ButtonWrapperPair p_selectionScreenImageButton)
    {
        AssertionUtils.requireNonNull(p_selectionScreenImageButton, "p_selectionScreenImageButton");
        getMandatoryToolbarColumn(ToolbarColumnId.SELECTION_SCREEN_IMAGE_BUTTON_COLUMN).enqueChildForDeferredAdd(p_selectionScreenImageButton);
    }
    
    public void addRefreshEnquiryResultsButton(ButtonWrapperPair p_refreshEnquiryResultsImageButton)
    {
        AssertionUtils.requireNonNull(p_refreshEnquiryResultsImageButton, "p_refreshEnquiryResultsImageButton");
        getMandatoryToolbarColumn(ToolbarColumnId.REFRESH_RESULTS_IMAGE_BUTTON_COLUMN).enqueChildForDeferredAdd(p_refreshEnquiryResultsImageButton);
    }
    
    public void addEnquiryInfoImageWidget(RichHTMLPresentationTextWrapper p_enquiryInfoImagePresentationText)
    {
        AssertionUtils.requireNonNull(p_enquiryInfoImagePresentationText, "p_enquiryInfoImagePresentationText");
        getMandatoryToolbarColumn(ToolbarColumnId.ENQUIRY_INFO_IMAGE_WIDGET_COLUMN).enqueChildForDeferredAdd(p_enquiryInfoImagePresentationText);
    }
    
    public void addAutoRefreshControls(ButtonWrapperPair p_manualRefreshButton, ButtonWrapperPair p_autoRefreshToggleButton, QuestionWrapperPair p_autoRefreshIntervalCountdownQuestion)
    {
        AssertionUtils.requireNonNull(p_manualRefreshButton, "p_manualRefreshButton");
        AssertionUtils.requireNonNull(p_autoRefreshToggleButton, "p_autoRefreshToggleButton");
        AssertionUtils.requireNonNull(p_autoRefreshIntervalCountdownQuestion, "p_autoRefreshIntervalCountdownQuestion");
        
        final ToolbarColumn autoRefreshControlsColumn = getMandatoryToolbarColumn(ToolbarColumnId.AUTO_REFRESH_CONTROLS_COLUMN);
        
        final AutoRefreshControlSetWidget.Params autoRefreshControlsWidgetParams = new AutoRefreshControlSetWidget.Params();
        autoRefreshControlsWidgetParams.setManualRefreshButton(p_manualRefreshButton.presWrapperObject);
        autoRefreshControlsWidgetParams.setAutoRefreshToggleButton(p_autoRefreshToggleButton.presWrapperObject);
        autoRefreshControlsWidgetParams.setAutoRefreshIntervalCountdownQuestion(p_autoRefreshIntervalCountdownQuestion.presWrapperObject);
        
        AutoRefreshControlSetWidget.INSTANCE.applyTo(autoRefreshControlsColumn.getColumnBreakWrapper(), autoRefreshControlsWidgetParams);
        autoRefreshControlsColumn.enqueChildForDeferredAdd(p_autoRefreshToggleButton);
        autoRefreshControlsColumn.enqueChildForDeferredAdd(p_autoRefreshIntervalCountdownQuestion);
    }
    
    public void addEnquiryBreakChangeQuestion(QuestionWrapperPair p_enqBreakChangeQuestion)
    {
        AssertionUtils.requireNonNull(p_enqBreakChangeQuestion, "p_enqBreakChangeQuestion");
        getMandatoryToolbarColumn(ToolbarColumnId.BREAK_CHANGE_DROPDOWN_QUESTION_COLUMN).enqueChildForDeferredAdd(p_enqBreakChangeQuestion);
    }
    
    public void addEnquiryResultsTopLinksQuestion(QuestionWrapperPair p_enqResultsTopLevelLinksQuestion)
    {
        AssertionUtils.requireNonNull(p_enqResultsTopLevelLinksQuestion, "p_enqResultsTopLevelLinksQuestion");
        getMandatoryToolbarColumn(ToolbarColumnId.ENQUIRY_TOP_LINKS_WIDGET_COLUMN).enqueChildForDeferredAdd(p_enqResultsTopLevelLinksQuestion);
    }
    
    public void addGenericResultScreenActionsQuestion(QuestionWrapperPair p_genericResultScreenActionsQuestion)
    {
        AssertionUtils.requireNonNull(p_genericResultScreenActionsQuestion, "p_genericResultScreenActionsQuestion");
        getMandatoryToolbarColumn(ToolbarColumnId.GENERIC_RESULTSET_ACTIONS_QUESTION_COLUMN).enqueChildForDeferredAdd(p_genericResultScreenActionsQuestion);
    }
    
    public void flushAllDeferredAddsAndRemoveAnyEmptyColumns()
    {
        final PresentationObject toolbarContainerNode = m_toolbarContainerWrapperPair.presWrapperObject.unwrap();

        ResultsToolbarColumnDesignOption columnDesignOption = ResultsToolbarColumnDesignOption.FIRST_TOOLBAR_COLUMN;
        
        for (ToolbarColumn toolbarColumn: m_toolbarColumnById.values())
        {
            if (toolbarColumn.isEmpty())
                toolbarContainerNode.removeChild(toolbarColumn.getColumnBreakNode());
            
            else
            {
                if (toolbarColumn.isWidgetBased())
                    columnDesignOption = ResultsToolbarColumnDesignOption.CUSTOM;
                
                toolbarColumn.applyColumnDesign(columnDesignOption);
                toolbarColumn.addEnquedChildren();
                
                columnDesignOption = m_isPseudoEnquiryResultsToolbar ? ResultsToolbarColumnDesignOption.SUBSEQUENT_PSEUDO_TOOLBAR_COLUMN : ResultsToolbarColumnDesignOption.SUBSEQUENT_STANDARD_TOOLBAR_COLUMN;
            }
        }
        
        m_toolbarColumnById.clear();
    }
    
    public boolean isPseudoEnquiryResultsToolbar()
    {
        return m_isPseudoEnquiryResultsToolbar;
    }
    
    private ToolbarColumn getMandatoryToolbarColumn(ToolbarColumnId p_columnId)
    {
        AssertionUtils.requireNonNull(p_columnId, "p_columnId");
        
        if (m_toolbarColumnById.isEmpty())
            throw new IllegalStateException("flushAllDeferredAddsAndRemoveAnyEmptyColumns() has already been called - no more adds can be performed !");
        
        final ToolbarColumn result = m_toolbarColumnById.get(p_columnId);

        AssertionUtils.requireConditionTrue(result != null, "m_toolbarColumnById.get(" + p_columnId + ") != null");
        
        return result;
    }
    
    private void addAllColumnsToToolbarContainer(String p_searchResultsFoundCondition)
    {
        final PresentationObjectWrapper<?> toolbarPresContainerObj = m_toolbarContainerWrapperPair.presWrapperObject;
        
        for(final ToolbarColumnId columnId: ToolbarColumnId.VALUES)
        {
            final RichHTMLPresentationColumnBreakWrapper columnBreak = RichHTMLPresentationColumnBreakWrapper.create(m_formContext);
            
            if (columnId.isColumnVisibilitySubjectToResultsFound())
            {
                columnBreak.setHideField(Boolean.TRUE);
                columnBreak.setConditionExpression(p_searchResultsFoundCondition);
            }
            
            toolbarPresContainerObj.addChild(columnBreak);
            
            final VisualItemContainerWrapperPair columnContainerWrapperPair = new VisualItemContainerWrapperPair(m_toolbarContainerWrapperPair.wrapperObject, columnBreak);
            m_toolbarColumnById.put(columnId, new ToolbarColumn(columnId, columnContainerWrapperPair));
        }
    }
    
    private RichHTMLPresentationFormatBreakWrapper addToolbarPresentationSectionTo(PresentationObjectWrapper<?> p_parentContainerPresentationObject)
    {
        final RichHTMLPresentationFormatBreakWrapper toolbarPresentationSection = RichHTMLPresentationFormatBreakWrapper.create(m_formContext);
        
        toolbarPresentationSection.setSectionStyle(EnquiryInlineStyleBlocks.WIDTH_AUTO);
        p_parentContainerPresentationObject.addChild(toolbarPresentationSection);
        
        return toolbarPresentationSection;
    }

    private enum ToolbarColumnId
    {
        SELECTION_SCREEN_IMAGE_BUTTON_COLUMN("Selection Screen Image Button", false, false),
        REFRESH_RESULTS_IMAGE_BUTTON_COLUMN("Refresh Results Image Button", true, false),
        ENQUIRY_INFO_IMAGE_WIDGET_COLUMN("Enquiry Image Widget Column", false, false),
        AUTO_REFRESH_CONTROLS_COLUMN("Auto Refresh Controls", false, true),
        BREAK_CHANGE_DROPDOWN_QUESTION_COLUMN("Break-change Dropdown Question", true, false),
        ENQUIRY_TOP_LINKS_WIDGET_COLUMN("Enquiry Top-links Widget Question", true, false),
        GENERIC_RESULTSET_ACTIONS_QUESTION_COLUMN("Generic Result-set Actions", true, false);
        
        public static final ToolbarColumnId[] VALUES = values();
        
        private final String m_columnDescription;
        private final boolean m_isColumnVisibilitySubjectToResultsFound;
        private final boolean m_isForWidgetBasedColumn;
        
        @Override
        public String toString()
        {
            return m_columnDescription;
        }
        
        public boolean isColumnVisibilitySubjectToResultsFound()
        {
            return m_isColumnVisibilitySubjectToResultsFound;
        }
        
        boolean isForWidgetBasedToolbarColumn()
        {
            return m_isForWidgetBasedColumn;
        }
        
        private ToolbarColumnId(String p_columnDescription, boolean p_isColumnVisibilitySubjectToResultsFound, boolean p_isForWidgetBasedColumn)
        {
            m_columnDescription = AssertionUtils.requireNonNullAndNonEmpty(p_columnDescription, "p_columnDescription");
            m_isColumnVisibilitySubjectToResultsFound = p_isColumnVisibilitySubjectToResultsFound;
            m_isForWidgetBasedColumn = p_isForWidgetBasedColumn;
        }
    }
    
    private static interface AddableColumnChild
    {
        void addSelfTo(VisualItemContainerWrapperPair p_container);
    }
    
    private static class AddablePresentationOnlyChild implements AddableColumnChild
    {
        private final PresentationObjectWrapper<?> m_presOnlyChildWrapper;
        
        AddablePresentationOnlyChild(PresentationObjectWrapper<?> p_presOnlyChildWrapper)
        {
            m_presOnlyChildWrapper = AssertionUtils.requireNonNull(p_presOnlyChildWrapper, "p_presOnlyChildWrapper");
        }
        
        @Override
        public void addSelfTo(VisualItemContainerWrapperPair p_container)
        {
            AssertionUtils.requireNonNull(p_container, "p_container");
            p_container.presWrapperObject.addChild(m_presOnlyChildWrapper);
        }
    }
    
    private static class AddableChildWrapperObjectPair implements AddableColumnChild
    {
        final VisualWrapperObjectPair<?,?> m_childWrapperObjectPair;
        
        AddableChildWrapperObjectPair(VisualWrapperObjectPair<?,?> p_childWrapperObjectPair)
        {
            m_childWrapperObjectPair = AssertionUtils.requireNonNull(p_childWrapperObjectPair, "p_childWrapperObjectPair");
        }
        
        @Override
        public void addSelfTo(VisualItemContainerWrapperPair p_container)
        {
            AssertionUtils.requireNonNull(p_container, "p_container");
            p_container.addChild(m_childWrapperObjectPair);
        }
    }
  
    private static class ToolbarColumn
    {
        private final ToolbarColumnId m_id;
        private final VisualItemContainerWrapperPair m_columnContainerWrapperPair;
        private final List<AddableColumnChild> m_pendingChildren = new ArrayList<AddableColumnChild>();
        
        ToolbarColumn(ToolbarColumnId p_id, VisualItemContainerWrapperPair p_columnContainerWrapperPair)
        {
            m_id = AssertionUtils.requireNonNull(p_id, "p_id");
            m_columnContainerWrapperPair = AssertionUtils.requireNonNull(p_columnContainerWrapperPair, "p_columnContainerWrapperPair");
        }
        
        boolean isWidgetBased()
        {
            return m_id.isForWidgetBasedToolbarColumn();
        }
        
        RichHTMLPresentationColumnBreakWrapper getColumnBreakWrapper()
        {
            return (RichHTMLPresentationColumnBreakWrapper) m_columnContainerWrapperPair.presWrapperObject;
        }
        
        PresentationObject getColumnBreakNode()
        {
            return getColumnBreakWrapper().unwrap();
        }
        
        void enqueChildForDeferredAdd(VisualWrapperObjectPair<?,?> p_newChildWrapperObjectPair)
        {
            AssertionUtils.requireNonNull(p_newChildWrapperObjectPair, "p_newChildWrapperObjectPair");
            m_pendingChildren.add(new AddableChildWrapperObjectPair(p_newChildWrapperObjectPair));
        }
        
        void enqueChildForDeferredAdd(RichHTMLPresentationTextWrapper p_newPresOnlyChildWrapper)
        {
            AssertionUtils.requireNonNull(p_newPresOnlyChildWrapper, "p_newPresOnlyChildWrapper");
            m_pendingChildren.add(new AddablePresentationOnlyChild(p_newPresOnlyChildWrapper));
        }
        
        boolean isEmpty()
        {
            return m_pendingChildren.isEmpty();
        }
        
        void applyColumnDesign(ResultsToolbarColumnDesignOption p_columnDesignOption)
        {
            AssertionUtils.requireNonNull(p_columnDesignOption, "p_columnDesignOption");
            p_columnDesignOption.applyTo((RichHTMLPresentationColumnBreakWrapper) m_columnContainerWrapperPair.presWrapperObject);
        }
        
        void addEnquedChildren()
        {
            for (final AddableColumnChild pendingChild: m_pendingChildren)
                pendingChild.addSelfTo(m_columnContainerWrapperPair);
        }
    }
}
