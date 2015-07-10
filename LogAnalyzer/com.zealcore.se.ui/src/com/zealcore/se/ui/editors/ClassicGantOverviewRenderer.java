package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.ITimeProvider;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.generic.GenericTaskExecution;
import com.zealcore.se.core.util.SimpleScaler;
import com.zealcore.se.ui.graphics.figures.ObjectFigure;
import com.zealcore.se.ui.graphics.figures.TaskExecution;
import com.zealcore.se.ui.graphics.figures.TaskLaneEntry;
import com.zealcore.se.ui.internal.SWTUtil;

class ClassicGantOverviewRenderer {

	private static final GanttOverviewDraw EMPTY_GANTT_DRAW = new GanttOverviewDraw(
			null);

	private static final int FONT_SIZE = 9;

	public static final int LANE_START = 10;

	private static final int DURATION_HEIGHT = 2;

	private static final int DURATION_MARGIN = 1;

	public static final int LANE_HEIGHT = ClassicGantOverviewRenderer.DURATION_HEIGHT
			+ ClassicGantOverviewRenderer.DURATION_MARGIN
			+ ClassicGantOverviewRenderer.DURATION_MARGIN;

	private static final int COLUMN_WIDTH = 0;

	private final Map<String, TaskLaneEntry> lanes = new HashMap<String, TaskLaneEntry>();

	private final Font font;

	private SimpleScaler ruler;

	ClassicGantOverviewRenderer() {
		font = new Font(Display.getDefault(), "Arial",
				ClassicGantOverviewRenderer.FONT_SIZE, SWT.NORMAL);
	}

	protected GanttOverviewDraw draw(final Drawable canvas, Display display,
			final GanttRenderingData data, final Logset logset,
			final SimpleScaler sRuler) {

		// Check pre-conditions
		if (logset.getNumberOfTaskSwitchEvents() == 0) {
			// if (data.getDurations().size() < 1) {
			drawEmpty(data);
			return ClassicGantOverviewRenderer.EMPTY_GANTT_DRAW;
		}

		// Collections.sort(data.getDurations());

		// if (data.getWidth() < 1) {
		// drawEmpty(data);
		// return ClassicGantOverviewRenderer.EMPTY_GANTT_DRAW;
		// }

		// Graphics Setup
		data.getGraphics().setAntialias(SWT.OFF);
		data.getGraphics().setFont(font);

		final GanttOverviewDraw ganttOverviewDrawData = new GanttOverviewDraw(
				data.getGraphics());
		lanes.clear();

		int laneIndex = 0;
		for (final IArtifact user : data.getTasks()) {
			final TaskLaneEntry entry = new TaskLaneEntry(user);
			entry.setLane(laneIndex++);
			lanes.put(user.getName(), entry);
		}

		setColorsOnEntries(data, lanes, ganttOverviewDrawData);
		drawGrid(data, laneIndex);
		final Rectangle clipping = data.getGraphics().getClipping();
		data.getGraphics().setClipping(
				new Rectangle(ClassicGantOverviewRenderer.COLUMN_WIDTH, 0, data
						.getBounds().width
						- ClassicGantOverviewRenderer.COLUMN_WIDTH, data
						.getBounds().height));
		// drawDurations(data, ganttOverviewDrawData);

		// Paint the Gantt chart events

		long currentTime = logset.getCurrentTime();
		logset.setCurrentTime(0);
		GanttChartDataReader query = new GanttChartDataReader(null,
				sRuler, data, ganttOverviewDrawData);
		logset.addQuery(query);
		logset.removeQuery(query);
		logset.setCurrentTime(currentTime);

		data.getGraphics().setClipping(clipping);

		data.setDrawnSize(new Point(data.getBounds().width,
				ClassicGantOverviewRenderer.LANE_START + lanes.size()
						* ClassicGantOverviewRenderer.LANE_HEIGHT));

		ganttOverviewDrawData.setBounds(SWTUtil.convert(data.getBounds()));

		Image image = new Image(display, new Rectangle(
				ClassicGantOverviewRenderer.COLUMN_WIDTH, 0,
				data.getBounds().width
						- ClassicGantOverviewRenderer.COLUMN_WIDTH, (data
						.getBounds().height)));
		GC gc = new GC(canvas);
		gc.copyArea(image, 0, 0);
		return ganttOverviewDrawData;
	}

	private void drawEmpty(final GanttRenderingData data) {
		final Color pushed = data.getGraphics().getBackground();
		data.getGraphics().setBackground(
				data.getGraphics().getDevice().getSystemColor(
						SWT.COLOR_WIDGET_BACKGROUND));
		data.getGraphics().fillRectangle(data.getGraphics().getClipping());
		data.getGraphics().drawText("No Gantt data available", 1, 1);
		data.getGraphics().setBackground(pushed);
	}

	public void drawMarker(final GC gc, final GanttRenderingData data,
			final double imageScale, final int paintWidth) {
		int markerX = (int) (ruler.toScreen(data.getMarker(), imageScale));
		if (markerX > paintWidth - 1) {
			markerX = paintWidth - 1;
		}
		if (markerX >= 0) {
			// gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));
			gc.setBackground(Display.getDefault()
					.getSystemColor(SWT.COLOR_BLUE));
			gc.drawLine(markerX, LANE_START, markerX, LANE_START + lanes.size()
					* LANE_HEIGHT);

			markerX += 1;
			final int arrowWidth = 5;
			final int arrowHeadY = LANE_START;
			final int[] upperArrow = new int[] { markerX, arrowHeadY,
					markerX + arrowWidth, arrowHeadY - arrowWidth,
					markerX - arrowWidth, arrowHeadY - arrowWidth, };
			gc.fillPolygon(upperArrow);
			final int[] lowerArrow = new int[] { markerX,
					lanes.size() * LANE_HEIGHT + LANE_START,
					markerX + arrowWidth,
					lanes.size() * LANE_HEIGHT + LANE_START + arrowWidth,
					markerX - arrowWidth,
					lanes.size() * LANE_HEIGHT + LANE_START + arrowWidth, };
			gc.fillPolygon(lowerArrow);
		}
	}

	private void drawDurations(final GanttRenderingData data, final GC gc,
			final Graphics graphics, final List<ITaskDuration> durations) {

		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));

		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));

		for (final IDuration d : durations) {
			drawDurationItem(data, d, graphics);
			if (d.contains(data.getMarker())) {
				data.setMarkerAt(d);
			}
		}
	}

	private void drawDurationItem(final GanttRenderingData data,
			final IDuration d, final Graphics graphics) {
		final TaskLaneEntry taskFigure = lanes.get(d.getOwner().getName());

		// if task is filtered out
		if (taskFigure == null) {
			return;
		}
		final int y = LANE_START + taskFigure.getLane() * LANE_HEIGHT
				+ DURATION_MARGIN;

		int xStart = ruler.toScreen(d.getStartTime());

		final Rectangle dataArea = getDataArea(data);
		if (xStart < dataArea.x) {
			xStart = dataArea.x;
		}

		int xEnd = ruler.toScreen(d.getStartTime() + d.getDurationTime());
		if (xEnd - xStart < 1) {
			xEnd = xStart + 1;
		}

		final Rectangle rect = new Rectangle(xStart, y, (xEnd - xStart),
				DURATION_HEIGHT);

		final ObjectFigure exec = new TaskExecution(d, rect);
		exec.setBackgroundColor(taskFigure.getBackgroundColor());
		exec.paint(graphics);
	}

	private Rectangle getDataArea(final GanttRenderingData data) {
		final int dataScreenX = data.getBounds().x + COLUMN_WIDTH;
		final int dataScreenWidth = data.getBounds().width - dataScreenX;

		final Rectangle dataScreenArea = new Rectangle(dataScreenX, 0,
				dataScreenWidth, LANE_START + LANE_HEIGHT * lanes.size());
		return dataScreenArea;
	}

	private void setColorsOnEntries(final GanttRenderingData data,
			final Map<String, TaskLaneEntry> lanes, final GanttOverviewDraw draw) {

		for (final Entry<String, TaskLaneEntry> entry : lanes.entrySet()) {
			final TaskLaneEntry taskEntry = entry.getValue();

			final RGB rgb = data.getColorMap().getColor(taskEntry.getUser());
			final Color resourceUserColor = new Color(data.getGraphics()
					.getDevice(), rgb);
			taskEntry.setBackgroundColor(resourceUserColor);
			final int laneYStart = LANE_START + taskEntry.getLane()
					* LANE_HEIGHT;
			final Rectangle resourceBoundingBox = new Rectangle(0, laneYStart,
					COLUMN_WIDTH, LANE_HEIGHT);
			taskEntry.setBounds(resourceBoundingBox);
			// draw.add(taskEntry);
		}
	}

	private void drawGrid(final GanttRenderingData data, final int laneIndex) {
		final Rectangle clipping = data.getGraphics().getClipping();
		data.getGraphics().setClipping(getDataArea(data));
		// ruler.draw(data.getDurations(), data.getGraphics());
		data.getGraphics().setClipping(clipping);

		data.getGraphics().setForeground(
				Display.getDefault().getSystemColor(SWT.COLOR_GRAY));

		final int x2 = data.getBounds().x + data.getBounds().width;
		int y = LANE_START;
		data.getGraphics().drawLine(0, y, x2, y);

		for (int i = 1; i <= laneIndex; i++) {
			y += LANE_HEIGHT;
			data.getGraphics().drawLine(0, y, x2, y);
		}
		data.getGraphics().drawLine(COLUMN_WIDTH, LANE_START, COLUMN_WIDTH,
				LANE_START + laneIndex * LANE_HEIGHT);
	}

	public void setRuler(final SimpleScaler ruler) {
		this.ruler = ruler;
	}

	class GanttChartDataReader extends AbstractQuery {

		private IDataSource dataSource;

		private List<ITaskDuration> executions = new LinkedList<ITaskDuration>();

		private Collection<IFilter<IObject>> filterOutThese = new ArrayList<IFilter<IObject>>();

		private SimpleScaler ruler;

		private int index;

		private GanttRenderingData ganttData;

		private GanttOverviewDraw ganttDraw;

		public GanttChartDataReader(final ITimeProvider time, final SimpleScaler ruler,
				final GanttRenderingData data,
				final GanttOverviewDraw ganttOverviewDrawData) {
			this.ruler = ruler;
			this.ganttData = data;
			this.ganttDraw = ganttOverviewDrawData;
		}

		public void initialize(final IDataSource data) {
			dataSource = data;
		}

		public boolean visitBegin(final Reason reason) {
			executions.clear();
			index = 0;
			return true;
		}

		public boolean visit(final IObject item) {
			if (!(item instanceof ITaskDuration)) {
				return true;
			}

			index++;
			ITaskDuration duration = (ITaskDuration) item;

			for (final IFilter<IObject> filter : filterOutThese) {
				if (!filter.filter(duration.getOwner())) {
					return true;
				}
			}

			ProcessedTaskDuration processedTaskDuration = new ProcessedTaskDuration(
					duration, ruler.toScreen(duration.getEndTime()));
			executions.add(processedTaskDuration);
			if (index > 10000) {
				drawDurations(ganttData, ganttDraw.getGc(), ganttDraw
						.getGraphics(), executions);
				executions.clear();
				index = 0;
			}

			return true;
		}

		public void visitEnd(final boolean atEnd) {
			drawDurations(ganttData, ganttDraw.getGc(),
					ganttDraw.getGraphics(), executions);
			executions.clear();

		}

		private void refresh() {
			// TODO
		}

		public Collection<IFilter<IObject>> getFilter() {
			return filterOutThese;
		}

		public void setFilter(final Collection<IFilter<IObject>> filter) {
			filterOutThese = filter;
			refresh();
		}

		public List<ITask> getTasks() {
			return dataSource.getArtifacts(ITask.class);
		}

		public List<ITaskDuration> getExecutions() {
			if (executions == null) {
				return new ArrayList<ITaskDuration>();
			}
			return new ArrayList<ITaskDuration>(executions);
		}

		public void clearData() {
			executions.clear();
		}

		class ProcessedTaskDuration extends GenericTaskExecution {

			private int xposEnd;

			public ProcessedTaskDuration(final ITaskDuration dur,
					final int xposEnd) {
				super(dur.getStartEvent(), (IProcessSwitch) dur.getStopEvent());
				this.xposEnd = xposEnd;
			}

			public int getXposEnd() {
				return xposEnd;
			}

		}
	}

}
