package com.odcgroup.edge.t24.generation.version.util;

import gen.com.acquire.intelligentforms.entities.presentation.PresentationSectionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;

import java.util.ArrayList;
import java.util.List;

import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreak;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.RichHelper.ESectionDisplay;
import com.odcgroup.edge.t24.generation.version.IVersionFieldMapper;

/**
 * Creates columns and new sections for each row 
 */
public class RichFieldColumnLayoutManager extends RichFieldLayoutManager
{
    
    /** The column breaks. */
    private final List<RichHTMLPresentationColumnBreakWrapper> m_columnBreaks = new ArrayList<RichHTMLPresentationColumnBreakWrapper>();
    
    /** The number of colums */
    private final int m_maxNumColumnsPopulatedAcrossAllRows;

    /** The current row break. */
    private int     m_currentRowBreak = -1; // Start at -1 so first call to createColumnBreaks increments to 0

    /** The current col break. */
    private int     m_currentColBreak = 0;
    
    /** The number of columns populate din each row  */
    private final List<Integer> m_colsPopulatedPerRow = new ArrayList<Integer>();
    
    /**
     * Instantiates a new rich column field layout manager.
     *
     * @param p_formContext the form context
     * @param p_fields the fields
     * @param p_presentationContainer the presentation container
     * @param p_languaguageMapHelper the language map helper
     */
    public RichFieldColumnLayoutManager(RichLayoutManager p_containerLayout, List<IVersionFieldMapper> p_fields)
    {
        super( p_containerLayout, p_fields );
        
        int maxNumColumnsPopulatedAcrossAllRows = 1;

        // Work out the column breaks we need (i.e. max possible populated)
        //
        for (int i = 0; i < p_fields.size(); i++)
        {
            IVersionFieldMapper field = p_fields.get( i );
            
            if  ( field.isDisplayed() )
            {
                if  ( m_colsPopulatedPerRow.isEmpty() || ! field.displayOnSameLine() )
                {
                    m_colsPopulatedPerRow.add( 1 );
                }
                else
                {
                    final int colCountIndexForCurrentRow = m_colsPopulatedPerRow.size() - 1;
                    final int updatedColumnCountForCurrentRow = m_colsPopulatedPerRow.get( colCountIndexForCurrentRow ).intValue() + 1;
                    m_colsPopulatedPerRow.set(colCountIndexForCurrentRow, updatedColumnCountForCurrentRow);
                    maxNumColumnsPopulatedAcrossAllRows = Math.max(maxNumColumnsPopulatedAcrossAllRows, updatedColumnCountForCurrentRow);
                }
            }
        }

        m_maxNumColumnsPopulatedAcrossAllRows = maxNumColumnsPopulatedAcrossAllRows;
    }
    
    /**
     * @return the number of columns (deduced on construction) used to lay out the questions for the displayable version fields
     */
    public int getNumberOfColumns()
    {
        return m_maxNumColumnsPopulatedAcrossAllRows;
    }
    
    /**
     * Sets the current field.
     *
     * @param p_field the new current field
     */
    @Override
    public void setCurrentField(IVersionFieldMapper p_field)
    {
        super.setCurrentField( p_field );
        
        if  ( p_field.isDisplayed() )
        {
            if  ( m_columnBreaks.size() == 0 )
            {
                // First time, so create column breaks
                //
                createColumnBreaks();
            }
            else if  ( getCurrentPresentationContainer() == m_columnBreaks.get( m_currentColBreak ) )
            {
                if  ( p_field.displayOnSameLine() )
                {
                    // Move on to the next column break
                    //
                    m_currentColBreak ++;
                }
                else
                {
                    // Always force a break as even questions vary in height, never mind more complex tables
                    //
                    createColumnBreaks();
                }
            }
            
            // Start adding new pres items within the column break .. allow for the last field actually being dis
            //
            setCurrentPresentationContainer( m_columnBreaks.get( m_currentColBreak ) );
        }
    }

    /**
     * Creates the column breaks with a new parent format break and append to the initial container
     */
    private void createColumnBreaks()
    {
        RichHTMLPresentationFormatBreakWrapper parentColumnBreaks = RichHelper.createSection(getFormContext(), ESectionDisplay.STANDARD_SECTION );
        
        getInitialPresentationContainer().addChild( parentColumnBreaks );
        
        m_currentColBreak = 0;
        m_currentRowBreak ++;

        m_columnBreaks.clear();
        
        for (int col=0; col < m_maxNumColumnsPopulatedAcrossAllRows; col++)
        {
            RichHTMLPresentationColumnBreakWrapper colBreak = RichHelper.createColumn( getFormContext() );
            
            parentColumnBreaks.addChild( colBreak );
            
            m_columnBreaks.add( colBreak );
        }
    }
    
    @Override
    public void revertToInitialState()
    {
        m_columnBreaks.clear();
        
        super.revertToInitialState();
    }
    
    @Override
    public void addAcrossSection(PresentationSectionWrapper p_section)
    {
        super.addAcrossSection( p_section );
        
        createColumnBreaks();
    }
    
    @Override
    public void expandFieldSpace()
    {
        super.expandFieldSpace();
        
        // Only zap an end column break, otherwise things may not align correctly with varying columns .. although maybe do this only
        // if we're on the first column break only?
        //
        if  ( m_currentRowBreak != -1 && m_colsPopulatedPerRow.get(m_currentRowBreak) == m_currentColBreak + 1 )
        {
            // Zap any further column breaks (currently only the last one .. but could handle all if second check above was eliminated
            //
            //
            for (int i=m_currentColBreak + 1; i < m_maxNumColumnsPopulatedAcrossAllRows; i++ )
            {
                RichHTMLPresentationColumnBreak columnBreak = m_columnBreaks.get( i ).unwrap();
                
                columnBreak.getParent().removeChild( columnBreak );
            }
        }
    }
}
