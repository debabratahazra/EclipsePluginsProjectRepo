package com.zealcore.se.ui.editors;

import java.util.UUID;

import junit.framework.Assert;

import org.easymock.classextension.EasyMock;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.StatisticQuery;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.ITimeCluster;

public final class TimingAnalyzerTest  {

    // subject
    private TimingStatisticsAnalyzer timingStatistics;

    // mock controls

    private ILogViewInput logViewInput;

    private ITimeCluster timeCluster;

    private ILogsetBrowser chart;

    private Logset logset;
    
    private Display display;

    private Canvas drawingArea;
    
    private IDrawableRuler ruler;
    
    
    /*
     * enum values for getting the width, zoom levels and distance  
     */

    private enum Width {

        W_300(300), W_560(560), W_760(760), W_828(828);

        private int value;

        Width(int value) {
            this.value = value;
        }
    };
    
    private enum Height {
        H_508(508);
        
        private int value;

        Height(int value) {
            this.value = value;
        }
        
    }

    private enum Zoom {
        ZOOM_MIN(0.1073741824), 
        ZOOM_DEFAULT(1), 
        ZOOM_1dot5625(1.5625), 
        ZOOM_4000(4000), 
        ZOOM_MAX(2640000);

        private double value;

        Zoom(double value) {
            this.value = value;
        }
    };

    private enum Distance {
        
        DISTANCE_FOR_ZOOM_4000(250000), 
        DISTANCE_FOR_ZOOM_DEFAULT(1000000000), 
        DISTANCE_FOR_ZOOM_1dot5625(new Long("640000000").longValue()), 
        DISTANCE_FOR_ZOOM_MIN(new Long("9313225747").longValue()),

        // least distance expected for the specified zoom levels
        LEAST_DISTANCE_FOR_ZOOM_4000(329), 
        LEAST_DISTANCE_FOR_ZOOM_MAX(0), 
        LEAST_DISTANCE_FOR_ZOOM_MIN(12254244), 
        LEAST_DISTANCE_FOR_ZOOM_DEFAULT(1315789) ;

        private long value;

        Distance(long value) {
            this.value = value;
        }
    };
    

    private enum TimeValues {

        DEFAULT_START_TIME("1522288105000"), 
        DEFAULT_END_TIME("1523288105000"), 
        DEFAULT_MID_TIME("1522788105000");

        private String value;

        TimeValues(String value) {
            this.value = value;
        }
    }
    
    private void initMockExpectations() {
        
        EasyMock.expect(chart.getName()).andReturn(
                GanttChart.NAME);
        EasyMock.expect(chart.getInput()).andReturn(logViewInput)
                .anyTimes();

        EasyMock.expect(logViewInput.getLogset())
                .andReturn(logset).anyTimes();

        EasyMock.expect(logViewInput.getTimeCluster())
                .andReturn(timeCluster).anyTimes();
    }
    
    /**
     * It returns a ruler based on zoomLevel and the current time. Note: It has
     * to be called after configuring/mocking a logset and initializing the
     * drawing area.
     * 
     * @param zoomLevel
     *            zoomLevel
     * @param midPointValue
     *            currentTime
     * @return
     */
    private IDrawableRuler getRuler(double zoomLevel, long midPointValue) {
        StatisticQuery statsQuery = StatisticQuery.valueOf(logset);
        long TIME_RANGE = 1000000000;

        long firstDuration = statsQuery.getMinTs();
        long lastDuration = 0;

        if (midPointValue <= 0) {
            lastDuration = (long) (firstDuration + ((TIME_RANGE / zoomLevel) / 2));
        } else {
            firstDuration = (long) (midPointValue - ((TIME_RANGE / zoomLevel) / 2));
            if (firstDuration < 0) {
                firstDuration = statsQuery.getMinTs();
            }
            lastDuration = (long) (midPointValue + ((TIME_RANGE / zoomLevel) / 2));
        }
        return new SimpleLinearScaleRuler(midPointValue, Span.valueOf(
                firstDuration, lastDuration), Span.valueOf(
                drawingArea.getClientArea().x,
                drawingArea.getClientArea().width));
    }
        

    @Before
    public void setUp() throws Exception {

        // change to constants to 300, 500 to constants
        chart = MockUtil.newMock(ILogsetBrowser.class);

        logViewInput = MockUtil.newMock(ILogViewInput.class);

        logset = Logset.valueOf(UUID.randomUUID());
        logset.setProgressMonitor(new NullProgressMonitor());

        timeCluster = MockUtil.newMock(ITimeCluster.class);

        display = new Display();
        
        Shell shell = new Shell(display);
        
        Composite parent = new Composite(shell, SWT.NONE);
        
        drawingArea = new Canvas(parent, SWT.DOUBLE_BUFFERED);
    }
    
    @Test(expected = RuntimeException.class)
    public void testNotSupportedCharts() {
        ILogsetBrowser chart = MockUtil.newMock(ILogsetBrowser.class);

        EasyMock.expect(chart.getName())
                .andReturn(TextBrowser.NAME)
                .andThrow(
                        new RuntimeException("Not supported on non-time charts"));

        MockUtil.replayAll();

        TimingStatisticsAnalyzer analyzer = new TimingStatisticsAnalyzer(chart,
                null);
    }
    
    @Test
    public void testSupportedCharts() {

        EasyMock.expect(chart.getName()).andReturn(GanttChart.NAME).times(3);

        EasyMock.expect(chart.getName()).andReturn(EventTimelineBrowser.NAME)
                .times(2);

        MockUtil.replayAll();

        TimingStatisticsAnalyzer analyzer = new TimingStatisticsAnalyzer(chart,
                null);

        Assert.assertEquals(GanttChart.NAME, analyzer.chart.getName());

        Assert.assertEquals(GanttChart.NAME, analyzer.chart.getName());

        Assert.assertEquals(EventTimelineBrowser.NAME, analyzer.chart.getName());

        Assert.assertEquals(EventTimelineBrowser.NAME, analyzer.chart.getName());
    }
    

    @Test
    public void testSetSelection() {
        
        initMockExpectations();
        
        Long currentTime = Long.valueOf(TimeValues.DEFAULT_MID_TIME.value);

        EasyMock.expect(timeCluster.getCurrentTime()).andReturn(currentTime).anyTimes();

        // replay
        MockUtil.replayAll();

        // do stuff
        timingStatistics = new TimingStatisticsAnalyzer(chart, drawingArea);
        timingStatistics.createTimingAnalysisControls(drawingArea.getParent());
        timingStatistics.setEnabled(true);

        // run test

        timingStatistics.initialize(0, Width.W_300.value); // passes for any width size
        this.timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_4000.value, currentTime)); // fails for any
                                                              // other zoom
        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.DISTANCE_FOR_ZOOM_4000.value));

        this.timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_DEFAULT.value, currentTime));
        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.DISTANCE_FOR_ZOOM_DEFAULT.value));

        timingStatistics.initialize(0, Width.W_760.value);
        this.timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_1dot5625.value, currentTime));
        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.DISTANCE_FOR_ZOOM_1dot5625.value));

        this.timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_MIN.value, currentTime));
        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.DISTANCE_FOR_ZOOM_MIN.value));

    }

    @Test
    public void testStartEndDistance() throws NumberFormatException {
        
        initMockExpectations();
        
        Long currentTime = Long.valueOf(TimeValues.DEFAULT_MID_TIME.value);

        EasyMock.expect(timeCluster.getCurrentTime())
                .andReturn(currentTime).anyTimes();

        MockUtil.replayAll();

        // do stuff
        timingStatistics = new TimingStatisticsAnalyzer(chart, drawingArea);
        drawingArea.setBounds(0, 0, Width.W_760.value, 10);
        timingStatistics.createTimingAnalysisControls(drawingArea.getParent());
        timingStatistics.setEnabled(true);

        // run test
        timingStatistics.initialize(0, Width.W_760.value);
        this.timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_DEFAULT.value, currentTime));
        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.DISTANCE_FOR_ZOOM_DEFAULT.value));

        this.timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_4000.value, currentTime));

        Long time1 = ((Long.parseLong(timingStatistics.startTime.getText())) + 
                (Distance.DISTANCE_FOR_ZOOM_4000.value));
        Assert.assertEquals(time1.toString(), timingStatistics.endTime.getText());

        Long time2 = (Long.parseLong(timingStatistics.startTime.getText()))
                + (Long.parseLong(timingStatistics.distance.getText()));

        Assert.assertEquals(time2.toString(), timingStatistics.endTime.getText());
    }

    @Test
    public void testDifferentEvents() {
        
        initMockExpectations();
        
        Long currentTime = Long.valueOf(TimeValues.DEFAULT_MID_TIME.value);

        EasyMock.expect(timeCluster.getCurrentTime())
                .andReturn(currentTime).anyTimes();

        // replay
        MockUtil.replayAll();

        // do stuff
        timingStatistics = new TimingStatisticsAnalyzer(chart, drawingArea);
        drawingArea.setBounds(0, 0, Width.W_760.value, 10);
        timingStatistics.createTimingAnalysisControls(drawingArea.getParent());
        timingStatistics.setEnabled(true);

        // run test
        timingStatistics.initialize(0, Width.W_760.value);

        // this is to set the left slider on the right slider so
        // distance is the least after this zoom is increased to level
        // 4000 for gantt chart and the distance is measured

        // in the same position of right and left sliders the
        // zoom is increased till the distance is 0 ( as max zoom
        // is not limited for timeline browser)
        timingStatistics.itemSelector.setSelection(759, 0);
        timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_4000.value, currentTime));

        timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_DEFAULT.value, currentTime));

        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.LEAST_DISTANCE_FOR_ZOOM_DEFAULT.value));

        timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_MIN.value, currentTime));

        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.LEAST_DISTANCE_FOR_ZOOM_MIN.value));

        timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_4000.value, currentTime));
        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.LEAST_DISTANCE_FOR_ZOOM_4000.value));

        timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_MAX.value, currentTime));

        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.LEAST_DISTANCE_FOR_ZOOM_MAX.value));

    }

    @Test
    public void testRightLeftSliders() {
        
        initMockExpectations();
        
        Long currentTime = Long.valueOf(TimeValues.DEFAULT_MID_TIME.value);

        EasyMock.expect(timeCluster.getCurrentTime()).andReturn(currentTime).anyTimes();

        // replay
        MockUtil.replayAll();

        // do stuff
        timingStatistics = new TimingStatisticsAnalyzer(chart, drawingArea);
        drawingArea.setBounds(0, 0, Width.W_760.value, 10);
        timingStatistics.createTimingAnalysisControls(drawingArea.getParent());
        timingStatistics.setEnabled(true);

        // run test
        timingStatistics.initialize(0, Width.W_760.value);

        timingStatistics.itemSelector.setSelection(50, 0);
        timingStatistics.updateStatistics(getRuler(Zoom.ZOOM_4000.value, currentTime));

        Assert.assertEquals(timingStatistics.distance.getText(),
                Long.toString(Distance.LEAST_DISTANCE_FOR_ZOOM_4000.value));
    }
    
    @Test
    public void testRangeSelection() {
        
        initMockExpectations();
        
        drawingArea.setBounds(0, 0, Width.W_828.value, Height.H_508.value);

        Long currentTime = Long.valueOf(TimeValues.DEFAULT_MID_TIME.value);

        EasyMock.expect(timeCluster.getCurrentTime()).andReturn(currentTime)
                .anyTimes();

        MockUtil.replayAll();

        TimingStatisticsAnalyzer analyzer = new TimingStatisticsAnalyzer(chart,
                drawingArea);

        analyzer.createTimingAnalysisControls(drawingArea.getParent());

        analyzer.setEnabled(true);

        analyzer.initialize(0, Width.W_828.value);

        Assert.assertEquals(0, analyzer.itemSelector.getSelectionOffset());

        Assert.assertEquals(Width.W_828.value,
                analyzer.itemSelector.getSelectionLength());

        analyzer.updateStatistics(getRuler(Zoom.ZOOM_DEFAULT.value, currentTime));

        Assert.assertEquals(TimeValues.DEFAULT_START_TIME.value,
                analyzer.startTime.getText());// 1522288105000
        Assert.assertEquals(TimeValues.DEFAULT_END_TIME.value,
                analyzer.endTime.getText());

        Assert.assertEquals(Long.toString((Long
                .parseLong(TimeValues.DEFAULT_END_TIME.value) - Long
                .parseLong(TimeValues.DEFAULT_START_TIME.value))),
                analyzer.distance.getText());

        analyzer.itemSelector.setSelection(Width.W_828.value / 2,
                Width.W_828.value / 2);

        Assert.assertEquals(Width.W_828.value / 2,
                analyzer.itemSelector.getSelectionOffset());

        analyzer.updateStatistics(getRuler(Zoom.ZOOM_DEFAULT.value, currentTime));

        String midPoint = Long.toString((Long
                .parseLong(TimeValues.DEFAULT_END_TIME.value) + Long
                .parseLong(TimeValues.DEFAULT_START_TIME.value)) / 2);

        Assert.assertEquals(midPoint, analyzer.startTime.getText());
        Assert.assertEquals(TimeValues.DEFAULT_END_TIME.value,
                analyzer.endTime.getText());
        Assert.assertEquals(Long.toString((Long
                .parseLong(TimeValues.DEFAULT_END_TIME.value) - Long
                .parseLong(midPoint))), analyzer.distance.getText());

    }

    @Test
    public void testRangeSelectionDisabled() {
        
        initMockExpectations();
        
        drawingArea.setBounds(0, 0, Width.W_828.value, Height.H_508.value);

        Long currentTime = Long.valueOf(TimeValues.DEFAULT_MID_TIME.value);

        EasyMock.expect(timeCluster.getCurrentTime()).andReturn(currentTime)
                .anyTimes();

        MockUtil.replayAll();

        TimingStatisticsAnalyzer analyzer = new TimingStatisticsAnalyzer(chart,
                drawingArea);

        analyzer.createTimingAnalysisControls(drawingArea.getParent());

        analyzer.setEnabled(false);

        analyzer.initialize(0, Width.W_828.value);

        Assert.assertFalse("Invalid result", analyzer.itemSelector.getEnabled());

        Assert.assertTrue("Invalid result", new Integer(0)
                .equals(analyzer.itemSelector.getSelectionOffset()));

        Assert.assertTrue("Invalid result", new Integer(2)
                .equals(analyzer.itemSelector.getSelectionLength()));

        analyzer.updateStatistics(getRuler(Zoom.ZOOM_DEFAULT.value, currentTime));

        Assert.assertEquals("", analyzer.startTime.getText());// 1522288105000
        Assert.assertEquals("", analyzer.endTime.getText());
        Assert.assertEquals("", analyzer.distance.getText());

        if (!display.isDisposed()) {
            display.dispose();
        }
    }

    @After
    public void tearDown() {
        
        if(!display.isDisposed()) {
            display.dispose();
        }

        MockUtil.verifyAll();
    }
}
