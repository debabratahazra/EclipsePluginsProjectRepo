package com.odcgroup.edge.t24.generation.enquiry.model.dsl;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationChartWrapper;

import com.acquire.util.AssertionUtils;
import com.odcgroup.t24.enquiry.enquiry.Graph;


/**
 * Each constant of the <code>DSLGraphType</code> enumeration relates to one of the possible values for the <code>type</code> attribute of a <code>graph { ... }</code> element in
 * the DSL description of a "graph" Enquiry (i.e. an enquiry whose results are to be presented in graph - as well as grid - form).<p>
 * 
 * Each <code>enum</code> constant "knows" the equivalent "chart type" in the edgeConnect world, and how to {@link #applyTypeSpecificSettingsTo(RichHTMLPresentationChartWrapper) apply}
 * this (and any other type-specific settings) to a supplied {@link RichHTMLPresentationChartWrapper}.<p>
 *
 * @author Simon Hayes
 */
public enum DSLGraphType
{
    BAR_ORDINAL("BAR.ORDINAL", RichHTMLPresentationChartWrapper.EChartType.COLUMN_CHART),
    LINE_ORDINAL("LINE.ORDINAL", RichHTMLPresentationChartWrapper.EChartType.LINE_CHART)
    {
        @Override
        public void applyTypeSpecificSettingsTo(RichHTMLPresentationChartWrapper p_chart)
        {
            AssertionUtils.requireNonNull(p_chart, "p_chart");

            super.applyTypeSpecificSettingsTo(p_chart);
            p_chart.setAttribute("DisplayTypeSHOW_X_AXIS_GRID_LINES", "N");
        }
    },
    PIE("PIE.CHART", RichHTMLPresentationChartWrapper.EChartType.PIE_CHART);
    
    private static final DSLGraphType[] VALUES = values();
    private static final int NUM_VALUES = VALUES.length;
    
    private final String m_nameInDsl;
    private final RichHTMLPresentationChartWrapper.EChartType m_edgeConnectChartType;
    
    public static DSLGraphType getDSLGraphType(Graph p_graph)
    {
        AssertionUtils.requireNonNull(p_graph, "p_graph");
        
        final String dslGraphTypeName = p_graph.getType();
        DSLGraphType result = null;
        
        for (int i = 0; i < NUM_VALUES; ++i)
        {
            final DSLGraphType instance = VALUES[i];
            
            if (instance.m_nameInDsl.equals(dslGraphTypeName))
            {
                result = instance;
                break;
            }
        }
        
        return result;
    }
    
    public void applyTypeSpecificSettingsTo(RichHTMLPresentationChartWrapper p_chart)
    {
        AssertionUtils.requireNonNull(p_chart, "p_chart");
        
        p_chart.setChartType(m_edgeConnectChartType);
    }
    
    private DSLGraphType(String p_nameInDsl, RichHTMLPresentationChartWrapper.EChartType p_edgeConnectChartType)
    {
        m_nameInDsl = AssertionUtils.requireNonNullAndNonEmpty(p_nameInDsl, "p_nameInDsl");
        m_edgeConnectChartType = AssertionUtils.requireNonNull(p_edgeConnectChartType, "p_edgeConnectChartType");
    }
}
