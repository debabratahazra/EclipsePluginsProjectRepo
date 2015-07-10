package com.zealcore.se.ui.views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.model.generic.GenericTask;
import com.zealcore.se.core.util.Statistics;
import com.zealcore.se.ui.views.PlottableViewer.PlotType;

public class PlotSearchQuery extends AbstractQuery {

    private KeyedValues2D keyValues2D;

    private DefaultCategoryDataset dataset;

    private Map<String, com.zealcore.se.ui.views.XYSeries> serieMap;

    private XYSeriesCollection series;

    private PlottableViewer plottViewer;

    private PlotType plotType;

    private final IFilter<IObject> filter;

    private int hits;

    private static final int MAX_HITS = 10000;
    
    private boolean updateCompleted;
    
    private IProgressMonitor monitor;
    
    private Map<String, Statistics> values;
    
	public PlotSearchQuery(final IFilter<IObject> filter,
            final PlotType plotType) {
        hits = 0;
        this.filter = filter;
        this.plotType = plotType;
        switch (this.plotType) {
        case BAR:
            keyValues2D = new KeyedValues2D();
            break;
        default:
            serieMap = new HashMap<String, com.zealcore.se.ui.views.XYSeries>();
        }
    }

    public void initialize(final IDataSource data) {}

    public boolean visit(final IObject item) {
    	if (this.monitor.isCanceled()) {
    		return false;
    	}
        if (filter.filter(item)) {
            hits++;
            switch (this.plotType) {
            case BAR:
                addToDataset(item);
                break;
            default:
                // Assumes ITimed...
                addToXYSeries(item);
            }
        }
        return true;
    }

    public boolean visitBegin(final Reason reason) {
    	values = new HashMap<String, Statistics>();
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        switch (this.plotType) {
        case BAR:
            updateCompleted = false;
            PlatformUI.getWorkbench().getDisplay().asyncExec(
                    new UpdateBarChartSeries());
            while(!updateCompleted) {
                try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
            }
            hits = 0;
            keyValues2D.clear();
            break;
        default:
            updateCompleted = false;
            PlatformUI.getWorkbench().getDisplay().asyncExec(
                    new UpdateChartSeries());
            while(!updateCompleted) {
                try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
            }
            hits = 0;
            serieMap.clear();
        }
        plottViewer.setChartUpdateCompleted(values);
    }

    public KeyedValues2D getKeyedValues2D() {
        return keyValues2D;
    }

    public Map<String, com.zealcore.se.ui.views.XYSeries> getSerieMap() {
        return serieMap;
    }

    public Map<String, Statistics> getValues() {
		return values;
	}

	private void addToDataset(final IObject object) {
        for (SEProperty property : object.getZPropertyAnnotations()) {
            if (property.isPlotable()) {
            	String plotAttribute = property.getName();
                if (object instanceof GenericTask) {
                    GenericTask newobject = new GenericTask();
                    String processName = ((GenericTask) object).getName();
                    if (processName.length() > 20) {
                        processName = processName.substring(0, 20)
                                .concat("...");
                    }
                    newobject.setName(processName);
                    newobject.setTaskId(((GenericTask) object).getTaskId());
                    keyValues2D.addValue(((Number) property.getData())
                            .doubleValue(), plotAttribute,
                            (IArtifact) newobject);
                } else {
                    keyValues2D.addValue(((Number) property.getData())
                            .doubleValue(), plotAttribute,
                            (IArtifact) object);
                }
                if(!values.containsKey(plotAttribute)){
                	values.put(plotAttribute, new Statistics());
                }
                calculatePlotStatistic(property);
            }
        }
        if (hits >= MAX_HITS) {
            updateCompleted = false;
            PlatformUI.getWorkbench().getDisplay().asyncExec(
                    new UpdateBarChartSeries());
            while(!updateCompleted) {
            	try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
            }
            hits = 0;
            keyValues2D.clear();
        }
    }

    private void addToXYSeries(final IObject object) {
        for (final SEProperty property : object.getZPropertyAnnotations()) {
            if (property.isPlotable()
                    && !serieMap.containsKey(property.getName())) {
                serieMap.put(property.getName(),
                        new com.zealcore.se.ui.views.XYSeries(property
                                .getName()));
            }
        }
        // TODO: Check with latest JFreeChart code if below lines are required.
        /* Below lines added for fixing TOOLSCR-2062 */
        if (serieMap.size() == 1) {
            serieMap.put("Ts.", new com.zealcore.se.ui.views.XYSeries("Ts."));
        }
        /* End TOOLSCR-2062 */
        
        for (Iterator<String> it = serieMap.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            if(!values.containsKey(key) && !key.equals("Ts.")) {
            	values.put(key, new Statistics());
            }
        }

        for (final SEProperty property : object.getZPropertyAnnotations()) {
            if (property.isPlotable()) {
                if (object instanceof ITimed) {
                    serieMap.get(property.getName()).addValue(
                            ((ITimed) object).getTimeReference(),
                            Double.valueOf(property.getData().toString()));
                    calculatePlotStatistic(property);
                }
            }
        }
        if (hits >= MAX_HITS) {
            updateCompleted = false;
            PlatformUI.getWorkbench().getDisplay().asyncExec(
                    new UpdateChartSeries());
            while(!updateCompleted) {
            	try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
            }
            hits = 0;
            serieMap.clear();
        }
    }

    private void calculatePlotStatistic(final SEProperty property) {
		
    	String attribute = property.getName();
    	double data = 0;
    	switch (this.plotType) {
		case BAR:
			data = ((Number)property.getData()).doubleValue();
			break;
		default:
			data = Double.valueOf(property.getData().toString());
			break;
		}
    	Statistics info = values.get(attribute);
    	info.update(data);
    	values.put(attribute, info);
	}


    class UpdateBarChartSeries implements Runnable {

        public void run() {
            updateBarChart();
        }
        
        private void updateBarChart() {
            plottViewer.setChartNotify(false);
            int i = 0;
            for (Iterator iter = keyValues2D.getRows().iterator(); iter.hasNext();) {
                KeyedValues type = (KeyedValues) iter.next();
                for (Iterator elements = type.getData().iterator(); elements
                        .hasNext();) {
                    i++;
                    KeyedValue data = (KeyedValue) elements.next();
                    dataset.addValue(data.getValue(), type.getRowKey(), data
                            .getKey());
                }
            }
            updateCompleted = true;
            plottViewer.setDatasetUpdateCompleted();
        }
    }

    class UpdateChartSeries implements Runnable {

        public void run() {
            updateChart();
        }

        private void updateChart() {
            plottViewer.setChartNotify(false);
            for (Iterator it = serieMap.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                XYSeries xySeries = null;
                try {
                    xySeries = series.getSeries(key);
                } catch (UnknownKeyException ex) {
                    xySeries = new XYSeries(key);
                    series.addSeries(xySeries);
                }
                xySeries.setNotify(false);
                com.zealcore.se.ui.views.XYSeries xyDataSeries = serieMap.get(key);
                for (Iterator iterator = xyDataSeries.getData().iterator(); iterator
                        .hasNext();) {
                    XYDataItem item = (XYDataItem) iterator.next();
                    xySeries.add(item.getX(), item.getY());
                }
            }
            updateCompleted = true;
            plottViewer.setDatasetUpdateCompleted();
        }
    }

    public XYSeriesCollection getSeries() {
        return series;
    }

    public DefaultCategoryDataset getDataset() {
        return dataset;
    }

    public void setPlottViewer(final PlottableViewer plottViewer) {
        this.plottViewer = plottViewer;
    }

    public void setDataset(final DefaultCategoryDataset dataset) {
        this.dataset = dataset;
    }

    public void setSeries(final XYSeriesCollection series) {
        this.series = series;
    }
    
    public void setMonitor(final IProgressMonitor monitor) {
		this.monitor = monitor;
	}
}
