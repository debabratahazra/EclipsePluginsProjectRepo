package com.tel.autosysframework.reports;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class SWTChartViewer extends Canvas
{
    private IDeviceRenderer idr = null;
    private Chart chart = null;

    public void setChart(Chart chart)
    {
        this.chart = chart;
    }

    public SWTChartViewer(Composite parent, String path)
    {
        super(parent, SWT.NONE);
        
        /*if(BarChart)
        {
        	this.chart = ObjectSizesChart.createStackedBar();
        }
        else
        {*/
        	this.chart = LineChart.createStackedLine();
//       / }
        
        	if(this.chart == null) {
        		parent.getShell().close();
        		return;
        	}
        
        this.addPaintListener(new SWTPaintListener());
        // INITIALIZE THE SWT RENDERING DEVICE
        final PluginSettings ps = PluginSettings.instance();
        try
        {
            idr = ps.getDevice("dv.SWT");
        }
        catch (ChartException pex)
        {
            pex.printStackTrace();
        }
    }

    private class SWTPaintListener implements PaintListener
    {
        /**
         *
         * The SWT paint callback
         *
         */
        public void paintControl(PaintEvent pe)
        {
            idr.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, pe.gc);
            final Composite co = (Composite) pe.getSource();
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    Rectangle re = co.getClientArea();
                    Bounds bo = BoundsImpl.create(re.x, re.y, re.width, re.height);
                    bo.scale(72d /idr.getDisplayServer().getDpiResolution());
                    // BOUNDS MUST BE SPECIFIED IN POINTS
                    // BUILD AND RENDER THE CHART
                    Generator gr = Generator.instance();
                    try
                    {
                        GeneratedChartState state = gr.build(idr.getDisplayServer(), chart, bo, null);
                        gr.render(idr, state);
                    }
                    catch (ChartException gex)
                    {
                        gex.printStackTrace();
                    }
                }
            };
            BusyIndicator.showWhile(co.getDisplay(), runnable);
        }
    }
}
