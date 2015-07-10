package com.zealcore.se.ui.views;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleEdge;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.util.Statistics;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.ImageReportItem;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.internal.SWTUtil;

public class PlottableViewer extends ViewPart implements IReportContributor {

    private interface IExtendedChartMouseListener extends ChartMouseListener {
        void chartMouseDoubleClicked(org.eclipse.swt.events.MouseEvent event);
    }

    private static final class ExtendedChartComposite extends ChartComposite {

        private Collection<IExtendedChartMouseListener> extendedChartMouseListeners = new ArrayList<IExtendedChartMouseListener>();

        public ExtendedChartComposite(final Composite comp, final int style) {
            super(comp, style);
        }

        public ExtendedChartComposite(final Composite comp, final int style,
                final JFreeChart chart) {
            super(comp, style, chart);
        }

        public ExtendedChartComposite(final Composite comp, final int style,
                final JFreeChart chart, final boolean useBuffer) {
            super(comp, style, chart, useBuffer);
        }

        public ExtendedChartComposite(final Composite comp, final int style,
                final JFreeChart chart, final boolean properties,
                final boolean save, final boolean print, final boolean zoom,
                final boolean tooltips) {
            super(comp, style, chart, properties, save, print, zoom, tooltips);
        }

        public ExtendedChartComposite(final Composite comp, final int style,
                final JFreeChart jfreechart, final int width, final int height,
                final int minimumDrawW, final int minimumDrawH,
                final int maximumDrawW, final int maximumDrawH,
                final boolean usingBuffer, final boolean properties,
                final boolean save, final boolean print, final boolean zoom,
                final boolean tooltips) {
            super(comp, style, jfreechart, width, height, minimumDrawW,
                    minimumDrawH, maximumDrawW, maximumDrawH, usingBuffer,
                    properties, save, print, zoom, tooltips);
        }

        @Override
        public void mouseDoubleClick(
                final org.eclipse.swt.events.MouseEvent event) {
            for (IExtendedChartMouseListener listener : extendedChartMouseListeners) {
                listener.chartMouseDoubleClicked(event);
            }
            super.mouseDoubleClick(event);
        }

        public void addChartMouseListener(
                final IExtendedChartMouseListener listener) {
            this.extendedChartMouseListeners.add(listener);
            super.addChartMouseListener(listener);
        }

    }

    private static final String VALUE = "Value";

    private static final String TIMESTAMP = "ts";

    public enum PlotType {
        PLOT, LINE, BAR
    }

    private static final int REPORT_PLOT_WIDTH = 600;

    private static final int REPORT_PLOT_HEIGHT = 600;

    private static final int DOT_WIDTH = 3;

    public static final String VIEW_ID = "com.zealcore.se.ui.views.PlottableViewer";

    private Composite container;

    private JFreeChart chart;

    private ILogSessionWrapper uiLogset;

	private Text minimumText;

	private Text meanText;

	private Text maximumText;

	private Combo combo;
	
	private static final String PATTERN = "#.#########";
	
	private Map<String, Statistics> attributes;

	private IChangeListener importListener;
	
    public PlottableViewer() {}

    @Override
    public void createPartControl(final Composite parent) {
        container = new Composite(parent, SWT.NULL);
        container.setLayout(new GridLayout(1, false));

        importListener = new IChangeListener() {
            public void update(final boolean changed) {
                if (changed) {
                	for (Control control : container.getChildren()) {
                        control.dispose();
                    }
                }
            }
        };
        IFWFacade.addChangeListener(importListener);
    }

    @Override
    public void setFocus() {
        container.setFocus();
    }

    public void setPlot(final Plot2 plot) {
        this.uiLogset = plot.getLogset();
        switch (plot.getPlotType()) {
        case BAR:
            createBarChart(plot);
            break;
        default:
            createChart(plot);
        }
    }

    private void createBarChart(final Plot2 plot) {

        clearChart();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        plot.getQuery().setDataset(dataset);
        chart = ChartFactory.createBarChart("", "", "", dataset,
                PlotOrientation.HORIZONTAL, true, true, false);
        chart.setAntiAlias(false);
        chart.getCategoryPlot().getRangeAxis().setAutoRange(true);
        createSwt();
    }

    private  void createChart(final Plot2 plot) {

        clearChart();
        final XYSeriesCollection series = new XYSeriesCollection();
        plot.getQuery().setSeries(series);
        switch (plot.getPlotType()) {
        case PLOT:
            createPlotScatter(series, "Chart ");
            break;

        case LINE:
            createLinePlot(series, "Chart");
            break;

        default:
            throw new NotImplementedException();
        }

        createSwt();
    }

    private void createSwt() {

        final ExtendedChartComposite chartFrame = new ExtendedChartComposite(
                container, SWT.NONE, chart, true);
        chartFrame.addChartMouseListener(new IExtendedChartMouseListener() {
            public void chartMouseClicked(final ChartMouseEvent arg0) {}

            public void chartMouseMoved(final ChartMouseEvent arg0) {}

            public void chartMouseDoubleClicked(
                    final org.eclipse.swt.events.MouseEvent event) {
                if (chart.getPlot() instanceof XYPlot) {
                    XYPlot plot = (XYPlot) chart.getPlot();
                    org.eclipse.swt.graphics.Rectangle rectangle = chartFrame
                            .getScreenDataArea();

                    Rectangle2D screenDataArea = new Rectangle2D.Double(
                            rectangle.x, rectangle.y, rectangle.width,
                            rectangle.height);
                    double value = plot.getDomainAxis().java2DToValue(event.x,
                            screenDataArea, RectangleEdge.BOTTOM);
                    long ts = (long) (value + 0.5);
                    gotoFromAwt(ts);
                }
            }
        });
        
        createStatisticViwer(chartFrame);
        
        container.layout();
    }

    private void createStatisticViwer(final ExtendedChartComposite chartFrame) {

    	GridData data = new GridData(GridData.FILL_BOTH);
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        chartFrame.setLayoutData(data);
        
        Group statisticGroup = new Group(container, SWT.NONE);
        statisticGroup.setText("Statistics for event plottable attributes");
        data = new GridData(SWT.NONE);
        data.verticalIndent = 5;
        statisticGroup.setLayoutData(data);
         
        statisticGroup.setLayout(new GridLayout(7, false));
        FormToolkit toolkit = new FormToolkit(container.getDisplay());
        toolkit.setBackground(container.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
      
        Label statLabel = toolkit.createLabel(statisticGroup, "Select attribute");
        data = new GridData();
        data.horizontalSpan = 2;
        statLabel.setLayoutData(data);
        
        combo = new Combo(statisticGroup, SWT.READ_ONLY);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 5;
        data.verticalAlignment = GridData.CENTER;
        data.grabExcessHorizontalSpace = true;
        combo.setLayoutData(data);
        combo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateStatisticsResult();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
        
        toolkit.createLabel(statisticGroup, "Min:");
        minimumText = toolkit.createText(statisticGroup, "", SWT.READ_ONLY);
        minimumText.setBackground(container.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        data = new GridData();
        data.widthHint = 110;
        data.horizontalSpan = 2;
        minimumText.setLayoutData(data);
        
        toolkit.createLabel(statisticGroup, "Mean:");
        meanText = toolkit.createText(statisticGroup, "", SWT.READ_ONLY);
        meanText.setBackground(container.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        data = new GridData();
        data.widthHint = 110;
        meanText.setLayoutData(data);
        
        toolkit.createLabel(statisticGroup, "Max:");
        maximumText = toolkit.createText(statisticGroup, "", SWT.READ_ONLY);
        maximumText.setBackground(container.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        data = new GridData();
        data.widthHint = 110;
        maximumText.setLayoutData(data);
	}

    protected void updateStatisticsResult() {
    	Statistics info = attributes.get(combo.getText());
		if(info == null) return;
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US); 
		DecimalFormat df = new DecimalFormat(PATTERN, symbols);
		minimumText.setText(df.format(info.getMinimum()));
		meanText.setText(df.format(info.getMean()));
		maximumText.setText(df.format(info.getMaximum()));
	}

	protected void gotoFromAwt(final long ts) {
        AbstractSafeUIJob job = new AbstractSafeUIJob("Jump to " + ts) {

            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                uiLogset.getDefaultViewSet().setCurrentTime(ts);
                return Status.OK_STATUS;
            }

        };
        job.setSystem(true);
        job.schedule();
    }

    private void clearChart() {
        for (Control control : container.getChildren()) {
            control.dispose();
        }
    }

    private  void createLinePlot(
            final XYSeriesCollection series, final String chartName) {
        chart = ChartFactory.createScatterPlot(chartName,
                PlottableViewer.TIMESTAMP, PlottableViewer.VALUE, series,
                PlotOrientation.VERTICAL, true, true, false);
        chart.setAntiAlias(false);

        XYLineAndShapeRenderer lineAndShapeRenderer = new XYLineAndShapeRenderer();

        XYPlot plot = chart.getXYPlot();

        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
       // plot.getLegendItems has a side effect, colors are changed and
        // yellow is not used anymore
        plot.getLegendItems();

        chart.getXYPlot().setRenderer(lineAndShapeRenderer);
    }

    private  void createPlotScatter(
            final XYSeriesCollection series, final String chartName) {
        chart = ChartFactory.createScatterPlot(chartName,
                PlottableViewer.TIMESTAMP, PlottableViewer.VALUE, series,
                PlotOrientation.VERTICAL, true, true, false);
        chart.setAntiAlias(false);

        XYDotRenderer xYDotRenderer = new XYDotRenderer();
        // Set color on legend items
        XYPlot plot = chart.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        // plot.getLegendItems has a side effect, colors are changed and
        // yellow is not used anymore
        plot.getLegendItems();

        xYDotRenderer.setDotWidth(PlottableViewer.DOT_WIDTH);
        xYDotRenderer.setDotHeight(PlottableViewer.DOT_WIDTH);
        chart.getXYPlot().setRenderer(xYDotRenderer);
    }

    public void fillReport(final AbstractReport report) {

        if (chart == null) {
            return;
        }
        final BufferedImage buff = new BufferedImage(
                PlottableViewer.REPORT_PLOT_WIDTH,
                PlottableViewer.REPORT_PLOT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        final Rectangle2D rect = new Rectangle2D.Float(0, 0, buff.getWidth(),
                buff.getHeight());
        chart.draw((Graphics2D) buff.getGraphics(), rect);
        final ImageData data = SWTUtil.convertToSWT(buff);
        final ImageReportItem item = new ImageReportItem(data);
        item.setName(getTitle());
        item.setDescription(getTitleToolTip());
        report.addReportData(item);
    }

    class UpdateChart implements Runnable {

        public void run() {
            if (!container.isDisposed()) {
                chart.setNotify(true);
            }
        }
    }
    
    public void setChartNotify(final boolean notify) {
        chart.setNotify(notify);
    }

    public void setDatasetUpdateCompleted() {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new UpdateChart());
    }

    public void setChartUpdateCompleted(final Map<String, Statistics> values) {
    	setPlotStatisticValues(values);
    	PlatformUI.getWorkbench().getDisplay().asyncExec(new UpdateChart());
    }

	private void setPlotStatisticValues(final Map<String, Statistics> values) {
		if (values != null)
			attributes = values;

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (attributes != null) {
					combo.removeAll();
					Set<String> keys = attributes.keySet();
					for (Iterator<String> iterator = keys.iterator(); iterator
							.hasNext();) {
						combo.add((String) iterator.next());
					}
					combo.select(0);
					updateStatisticsResult();
				}
			}
		});
	}
}
