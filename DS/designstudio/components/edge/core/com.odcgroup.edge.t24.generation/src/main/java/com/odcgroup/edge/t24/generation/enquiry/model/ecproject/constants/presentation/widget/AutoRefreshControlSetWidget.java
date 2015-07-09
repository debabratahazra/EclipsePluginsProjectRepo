package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.intelligentforms.entities.presentation.html.meta.HTMLRuntimeId;
import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class AutoRefreshControlSetWidget extends AbstractWidgetWithParameters<RichHTMLPresentationColumnBreakWrapper,AutoRefreshControlSetWidget.Params>
{
    public static final String WIDGET_NAME = "Enquiry Results Auto Refresh Controls";
    
    public static final String ATTR_RUNTIME_ID_MANUAL_REFRESH_BUTTON = "RUNTIME_ID_MANUAL_REFRESH_BUTTON";
    public static final String ATTR_RUNTIME_ID_AUTO_REFRESH_TOGGLE_BUTTON = "RUNTIME_ID_AUTO_REFRESH_TOGGLE_BUTTON";
    public static final String ATTR_RUNTIME_ID_INTERVAL_COUNTDOWN_VALUE_INPUT = "RUNTIME_ID_INTERVAL_COUNTDOWN_VALUE_INPUT";
    
    public static final AutoRefreshControlSetWidget INSTANCE = new AutoRefreshControlSetWidget();
    
    @Override
    public void applyTo(RichHTMLPresentationColumnBreakWrapper p_targetPresObjectWrapper, Params p_widgetParams)
    {
        super.applyTo(p_targetPresObjectWrapper, p_widgetParams);
    }
    
    public static class Params extends WidgetParamValues<RichHTMLPresentationColumnBreakWrapper>
    {
        public void setManualRefreshButton(RichHTMLPresentationButtonWrapper p_manualRefreshButton)
        {
            AssertionUtils.requireNonNull(p_manualRefreshButton, "p_manualRefreshButton");
            setWidgetParamValue(ATTR_RUNTIME_ID_MANUAL_REFRESH_BUTTON, (String) p_manualRefreshButton.unwrap().getAttribute(HTMLRuntimeId.ATT_RUNTIME_ID));
        }
        
        public void setAutoRefreshToggleButton(RichHTMLPresentationButtonWrapper p_autoRefreshToggleButton)
        {
            AssertionUtils.requireNonNull(p_autoRefreshToggleButton, "p_autoRefreshToggleButton");
            setWidgetParamValue(ATTR_RUNTIME_ID_AUTO_REFRESH_TOGGLE_BUTTON, (String) p_autoRefreshToggleButton.unwrap().getAttribute(HTMLRuntimeId.ATT_RUNTIME_ID));
        }
        
        public void setAutoRefreshIntervalCountdownQuestion(RichHTMLPresentationQuestionWrapper p_autoRefreshIntervalCountdownQuestion)
        {
            AssertionUtils.requireNonNull(p_autoRefreshIntervalCountdownQuestion, "p_autoRefreshIntervalCountdownQuestion");
            setWidgetParamValue(ATTR_RUNTIME_ID_INTERVAL_COUNTDOWN_VALUE_INPUT, (String) p_autoRefreshIntervalCountdownQuestion.unwrap().getAttribute(HTMLRuntimeId.ATT_RUNTIME_ID));
        }
    }

    private AutoRefreshControlSetWidget()
    {
        super(WIDGET_NAME);
    }
}
