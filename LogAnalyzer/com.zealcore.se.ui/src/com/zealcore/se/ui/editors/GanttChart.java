package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.layout.CellLayout;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.IQueryFilter;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.GanttQuery;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.StatisticQuery;
import com.zealcore.se.core.ifw.TaskQuery;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.generic.GenericTaskExecution;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.ImageReportItem;
import com.zealcore.se.ui.editors.GanttRenderingData.TaskByPrioritySort;
import com.zealcore.se.ui.graphics.figures.ObjectFigure;
import com.zealcore.se.ui.graphics.figures.TaskLaneEntry;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.preferences.GanttPreferencePage;
import com.zealcore.se.ui.util.ArtifactSelection;
import com.zealcore.se.ui.util.PropertySource;

@SuppressWarnings("restriction")
public final class GanttChart extends AbstractLogsetBrowser implements
		IStepable, IZoomable, IReportContributor, IQueryFilter, IToggleable {

	private static final String GRAPHICS_MAY_NOT_BE_NULL = "Graphics may NOT be null";

	private Composite sashComp;

	private Sash vSash;

	private static final int SASH_WIDTH = 3;

	private static final int SASH_LIMIT = 20;

	private Canvas drawingAreaA;

	private Canvas drawingAreaB;

	private ScrolledComposite scrollA;

	private ScrolledComposite scrollB;

	private int scrollOffset;

	private static final String CHANGES_BETWEEN_LINEAR_AND_LOGARITMIC_TIME_BASE = "Changes between linear and logarithmic time base";

	public static final String NAME = "Gantt Chart";

	private static final int DEFAULT_ZOOM_LEVEL = 1;

	private static final int NR_OF_DURATIONS_IN_DEFAULT_WINDOW = 1000;

	public static final String BROWSER_ID = "com.zealcore.se.ui.views.ZGantChart";

	public static final String HELP_ID = "com.zealcore.se.ui.editors_GanttChart";

	private static final int REPORT_WIDTH = 1024;

	private static final int REPORT_HEIGHT = 400;

	private static final double MAX_ZOOM_LEVEL = CommonConstants.MAX_ZOOM_LEVEL;

	private final ClassicGantRenderer renderer = new ClassicGantRenderer();

	private final GanttRenderingData renderingData = new GanttRenderingData();

	private MenuManager menuManager;

	private Menu contextMenu;

	private final int viewWindow = GanttChart.NR_OF_DURATIONS_IN_DEFAULT_WINDOW;

	private final Set<ILogBrowserContribution> possibleContributors = new LinkedHashSet<ILogBrowserContribution>();

	private GanttDraw ganttDrawA;

	private GanttDraw ganttDrawB;

	private GanttQuery query;

	private final IChangeListener changeListener;

	private TaskQuery taskQuery;

	private boolean isLog;

	private final UIJob refresher;

	private IEventColorProvider colorProvider;

	private boolean resizedInitiated;

	private Button[] radioButtons;
	
	protected Image canvasBImage;
	
	private TimingStatisticsAnalyzer analyzer;
	
	private final Image zoomOutImage;
	private final Image zoomInImage;

	public GanttChart() {
		super();

		refresher = new AbstractSafeUIJob("GanttChart_UIJob") {
			@Override
			public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
				if (drawingAreaA.isDisposed()) {
					// TODO ugly fix
					return Status.OK_STATUS;
				}
				if ((query != null) && (taskQuery != null)) {
					query.setFilter(taskQuery.getFilter());
				}
				drawingAreaA.redraw();
				if (drawingAreaB.isDisposed()) {
					// TODO ugly fix
					return Status.OK_STATUS;
				}
				drawingAreaB.redraw();

				return Status.OK_STATUS;
			}
		};
		refresher.setSystem(true);

		renderingData.setMarkerVisibile(true);
		renderingData.setTaskSorter(new TaskByPrioritySort());
		renderingData.setZoomLevel(GanttChart.DEFAULT_ZOOM_LEVEL);

		colorProvider = SeCorePlugin.getDefault().getService(
				IEventColorProvider.class);

		renderingData.setColorProvider(colorProvider);
		changeListener = new IChangeListener() {
			/*
			 * Updates the editor when logfile is deconfigured and there is no
			 * data to display
			 */
			public void update(final boolean changed) {
				if (changed) {
					if (getLogset().getLogs().size() == 0) {
						query = null;
					} else {
						if (query == null) {
							query = new GanttQuery(getInput().getTimeCluster());
							getLogset().addQuery(query);
						}
					}
				}
				
				refresh();
			}
		};

		GanttPreferencePage.addChangeListener(changeListener);
		colorProvider.addChangeListener(changeListener);

		IFWFacade.addChangeListener(changeListener);
		
		zoomInImage = IconManager.getImageDescriptor(IconManager.ZOOM_IN).createImage();
		zoomOutImage = IconManager.getImageDescriptor(IconManager.ZOOM_OUT).createImage();
	}

	public ImageDescriptor getImageDescriptor() {
		return IconManager.getImageDescriptor(IconManager.GANTT_SMALL);
	}

	void addContributor(final ILogBrowserContribution contribution) {
		possibleContributors.add(contribution);
	}

	private void getContributions(final IFigure figure) {
		final IRuler ruler = ganttDrawB.getScaler();
		for (final ILogBrowserContribution contribution : possibleContributors) {
			for (final IFigure iFigure : contribution
					.getElements(ruler, figure)) {
				ganttDrawB.add(iFigure);
			}
		}
	}

	private void applyDecorations(final GanttDraw figure) {
		for (final ILogBrowserContribution contribution : possibleContributors) {
			contribution.decorateFigures(figure.getGraphics(), figure);
		}
	}

	/**
	 * Tracks and executes logic based on mouse events (click on durations)
	 * 
	 * @author stch
	 * 
	 */
	private final class GantMouseListener extends MouseAdapter implements
			MouseMoveListener, MouseTrackListener {

		private static final int LEFT_MOUSE_BUTTON = 1;

		@Override
		public void mouseDoubleClick(final MouseEvent e) {
			try {
				if (getLogset().isLocked()) {
					return;
				}
			} catch (NullPointerException npe) {
			}
			final Object at = getObjectAt(e, ganttDrawB);
			// Left mouse button selects NamedItems (ie durations, events,
			// sources etc)
			if (e.button == GantMouseListener.LEFT_MOUSE_BUTTON) {

				if (at instanceof IDuration) {
					final IDuration d = (IDuration) at;
					getInput().getTimeCluster()
							.setCurrentTime(d.getStartTime());
					// setSelection(new PropertySource(item));
				}
			}
		}

		@Override
		public void mouseDown(final MouseEvent e) {
			try {
				if (getLogset().isLocked()) {
					return;
				}
			} catch (NullPointerException npe) {
			}
			Object at = null;
			Point p = new Point(0, 0);
			// Gets the object at the mouse-up (click)
			if (e.getSource() instanceof Canvas) {
				Canvas source = (Canvas) e.getSource();
				if (source.equals(drawingAreaA)) {
					at = getObjectAt(e, ganttDrawA);
					p = drawingAreaA.toDisplay(e.x, e.y);
				} else if (source.equals(drawingAreaB)) {
					at = getObjectAt(e, ganttDrawB);
					p = drawingAreaB.toDisplay(e.x, e.y);
				}
			}
			// final Object atB = getObjectAt(e, ganttDrawB);
			// Left mouse button selects NamedItems (ie durations, events,
			// sources etc)
			if (e.button == GantMouseListener.LEFT_MOUSE_BUTTON) {

				if (at instanceof IObject) {
					final IObject item = (IObject) at;
					GanttChart.this.setSelection(item);
					// setSelection(new PropertySource(item));
				}

			} else {

				contextMenu.setLocation(p);

				if (at instanceof IDuration) {
					final IDuration logevent = (IDuration) at;
					final ArtifactSelection artifactSelection = new ArtifactSelection(
							getInput(), logevent);
					GanttChart.this.setSelection(artifactSelection);
					artifactSelection.setVisibleGoto(false);
					artifactSelection.setJumpTo(false);
				}
				if (at instanceof IArtifact) {
					final IArtifact task = (IArtifact) at;

					final ArtifactSelection artifactSelection = new ArtifactSelection(
							getInput(), task);
					// GanttChart.this.setSelection(task);
					GanttChart.this.setSelection(artifactSelection);
					artifactSelection.setVisibleGoto(false);
					artifactSelection.setJumpTo(false);
				} else if (at instanceof GenericTaskExecution) {
					final ArtifactSelection artifactSelection = new ArtifactSelection(
							getInput(), (IObject) at);
					GanttChart.this.setSelection(artifactSelection);
					artifactSelection.setVisibleGoto(true);
					artifactSelection.setJumpTo(false);
				} else {

					GanttChart.this.setSelection(new Object());
				}

			}
		}

		public void mouseMove(final MouseEvent e) {
		}

		public void mouseHover(final MouseEvent e) {
			try {
				if (getLogset().isLocked()) {
					return;
				}
			} catch (NullPointerException npe) {
			}
			final Object at = getObjectAt(e, ganttDrawB);
			if (at != null) {
				if (at instanceof IObject) {
					final IObject item = (IObject) at;
					drawingAreaB.setToolTipText(PropertySource.toString(item));
					resized(false);
				} else {
					drawingAreaB.setToolTipText(null);
				}

			} else {
				drawingAreaB.setToolTipText(null);
			}
		}

		public void mouseEnter(final MouseEvent e) {
		}

		public void mouseExit(final MouseEvent e) {
		}
	}

	public void saveState(final IMemento savedState) {
		savedState.putString(CommonConstants.TAG_FORMAT_STRING, renderingData
				.getFormatString());

		savedState.putString(CommonConstants.ZOOM_LEVEL_LOG, Double
				.toString(renderingData.getZoomLevelLog()));
		savedState.putString(CommonConstants.ZOOM_LEVEL_LIN, Double
				.toString(renderingData.getZoomLevelLin()));

		savedState.putString(CommonConstants.ZOOM_DISTANCE_IS_LOG, Boolean
				.toString(this.isLogarithmic()));
	}

	public void init(final IMemento savedState) {
		final String format = savedState
				.getString(CommonConstants.TAG_FORMAT_STRING);
		if (format != null) {
			renderingData.setFormatString(format);
		}

		final String zoomLevelLog = savedState
				.getString(CommonConstants.ZOOM_LEVEL_LOG);
		if (zoomLevelLog != null) {
			renderingData.setZoomLevelLog(Double.valueOf(zoomLevelLog));
		}

		final String zoomLevelLin = savedState
				.getString(CommonConstants.ZOOM_LEVEL_LIN);
		if (zoomLevelLin != null) {
			renderingData.setZoomLevelLin(Double.valueOf(zoomLevelLin));
		}

		String s = savedState.getString(CommonConstants.ZOOM_DISTANCE_IS_LOG);
		final Boolean zoomIsLog = Boolean.parseBoolean(s);
		if (zoomIsLog != null) {
			renderingData.setLog(zoomIsLog);
			this.setLogarithmic(zoomIsLog);
		}
	}

	void resized(boolean resizeRangeSelector) {
		// /* Get the client area for the shell */
		Rectangle clientArea = sashComp.getClientArea();
		Rectangle sashBounds = vSash.getBounds();
		if (resizedInitiated) {
			if (sashBounds.width == 0) {
				sashBounds.x = clientArea.width / 2;
				sashBounds.height = clientArea.height;
			}
			Rectangle a = new Rectangle(0, 0, 0, 0);
			Rectangle b = new Rectangle(0, 0, 0, 0);

			a.width = sashBounds.x - SASH_WIDTH + 3;
			a.height = clientArea.height;

			b.x = sashBounds.x + SASH_WIDTH - 1;
			b.width = clientArea.width - b.x;
			
			b.width = b.width % 2 == 0 ? b.width : b.width - 1;
			b.height = clientArea.height;
			scrollA.setBounds(a);
			scrollB.setBounds(b);
			vSash.setBounds(a.width, 0, SASH_WIDTH, a.height);
		} else {
			resizedInitiated = true;
			Rectangle list1Bounds = new Rectangle(0, 0, Math.min(180,
					(clientArea.width - SASH_WIDTH) / 3), clientArea.height
					- SASH_WIDTH);
			scrollA.setBounds(list1Bounds);
			
			int width = clientArea.width - (list1Bounds.width + SASH_WIDTH);
			scrollB.setBounds(list1Bounds.width + SASH_WIDTH, 0,
                                (width % 2 == 0) ? width : (width - 1),
                                list1Bounds.height);
			
			vSash.setBounds(list1Bounds.width, 0, SASH_WIDTH,
					list1Bounds.height);
		}
		
		//Disable rangeselector when no executions available.
		if(query == null || getExecutions().size() < 1) {
		    analyzer.setEnabled(false);
		} else {
		    analyzer.setEnabled(true);
		}
		if(resizeRangeSelector) {
		    analyzer.initialize(0, scrollB.getClientArea().width);
		}
		scrollOffset = scrollB.getOrigin().y;
	}

	public void createControl(final Composite parent) {
		sashComp = new Composite(parent, SWT.BORDER);
		vSash = new Sash(sashComp, SWT.VERTICAL | SWT.SMOOTH);
		vSash.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent event) {
				Rectangle rect = vSash.getParent().getClientArea();
				event.x = Math.min(Math.max(event.x, SASH_LIMIT), rect.width
						- SASH_LIMIT);
				if (event.detail != SWT.DRAG) {
					vSash
							.setBounds(event.x, event.y, event.width,
									event.height);
					resized(true);
				}
			}
		});
		sashComp.addControlListener(new ControlAdapter() {
			public void controlResized(final ControlEvent event) {
				resized(true);
			}
		});

		scrollA = new ScrolledComposite(vSash.getParent(), SWT.H_SCROLL
				| SWT.DOUBLE_BUFFERED);
		scrollB = new ScrolledComposite(vSash.getParent(), SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.DOUBLE_BUFFERED);
		scrollA.setLayout(new FillLayout());
		scrollB.setLayout(new FillLayout());

		scrollB.getVerticalBar().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				scrollOffset = scrollB.getOrigin().y;
			}
		});

		drawingAreaA = new Canvas(scrollA, SWT.DOUBLE_BUFFERED);
		drawingAreaB = new Canvas(scrollB, SWT.DOUBLE_BUFFERED);

		setHelp(drawingAreaA, GanttChart.HELP_ID);
		setHelp(drawingAreaB, GanttChart.HELP_ID);

		scrollA.setContent(drawingAreaA);
		scrollB.setContent(drawingAreaB);
		scrollA.setExpandHorizontal(true);
		scrollB.setExpandHorizontal(true);
		scrollA.setExpandVertical(true);
		scrollB.setExpandVertical(true);

		drawingAreaA.setBackground(new Color(Display.getDefault(),
		                                      new RGB(255, 255, 254)));
		drawingAreaB.setBackground(new Color(Display.getDefault(),
		                                      new RGB(255, 255, 254)));

		final GantMouseListener gantMouseListener = new GantMouseListener();
		drawingAreaA.addMouseListener(gantMouseListener);
		drawingAreaB.addMouseListener(gantMouseListener);
		drawingAreaA.addMouseMoveListener(gantMouseListener);
		drawingAreaB.addMouseMoveListener(gantMouseListener);
		drawingAreaA.addMouseTrackListener(gantMouseListener);
		drawingAreaB.addMouseTrackListener(gantMouseListener);

		drawingAreaA.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent e) {
				if (e.count > 0) {
					// Frameskipping - there are pending draws
					return;
				}
				if ((query != null) && (taskQuery != null)) {
					query.setFilter(taskQuery.getFilter());
				}
				drawA(e.gc, drawingAreaA.getClientArea());
			}
		});

		drawingAreaB.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent e) {
                            
				if (e.count > 0) {
					// Frameskipping - there are pending draws
					return;
				}
				if ((query != null) && (taskQuery != null)) {
					query.setFilter(taskQuery.getFilter());
				}
				
				
                                if ((renderingData.getDurations() == null)
                                         || (renderingData.getDurations().size() < 1)) {
                                      analyzer.setEnabled(false);
                                      
                                } else {
                                    analyzer.setEnabled(true);
                                }
                                
                                
                                //Buffering and painting the chart
                                paintBufferedChart(e.gc);
			}
			
			void paintBufferedChart(GC eventGC) {
			    GC gc = null;
			    if(analyzer.isRangeSelectorSelected() == false) {
                                if (canvasBImage != null) {
                                    canvasBImage.dispose();
                                }
                                
                                canvasBImage = new Image(getSite().getShell().getDisplay(),
                                            drawingAreaB.getClientArea());
                                
                                gc = new GC(canvasBImage);
                                
                                drawB(gc, drawingAreaB.getClientArea());
                                drawingAreaA.redraw();
                                gc.dispose();
                            }
                            
                            Image imageToDraw = new Image(getSite().getShell().getDisplay(),
                                    canvasBImage.getImageData());
                            
                            gc = new GC(imageToDraw);
                            
                            analyzer.drawSelectors(gc);
                            
                            eventGC.drawImage(imageToDraw, 1, 0);
                            
                            if (imageToDraw != null) {
                                imageToDraw.dispose();
                            }
                            
                            if(gc != null) {
                                gc.dispose();
                            }
                            
                            analyzer.updateStatistics(renderer.getRuler());                                
                            
                            
                            analyzer.setRangeSelectorSelected(false);
			}
		});

		initContextMenu();

		/**
		 * Step forward/backward on LEFT/RIGHT Keys
		 */
		drawingAreaA.addKeyListener(GanttChartDefaultKeyListener.valueOf(this,
				drawingAreaA));
		drawingAreaB.addKeyListener(GanttChartDefaultKeyListener.valueOf(this,
				drawingAreaB));
		drawingAreaB.addKeyListener(new KeyListener() {

			public void keyPressed(final KeyEvent e) {
				if ((e.keyCode == SWT.ARROW_UP)
						|| (e.keyCode == SWT.ARROW_DOWN)) {
					handleVScrollBar(e.keyCode);
				}
			}

			public void keyReleased(final KeyEvent e) {
				// Nothing to be done.
			}
		});

		getSite().registerContextMenu(menuManager, this);

		new Label(getZealBar(), SWT.NONE).setText("Format: ");
		final Text timeFormat = new Text(getZealBar(), SWT.BORDER);
		timeFormat.setText(renderingData.getFormatString());
		timeFormat.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				renderingData.setFormatString(timeFormat.getText());
				GanttChart.this.refresh();
			}
		});

		timeFormat.setText(renderingData.getFormatString());
		timeFormat.setLayoutData(new RowData(
				CommonConstants.ZEALBAR_FORMAT_WIDTH, SWT.DEFAULT));

		createDataControl();

		Composite zoomComposite = new Composite(getZealBar(), SWT.NONE);
		zoomComposite.setLayout(new CellLayout(2).setMargins(10, 10));

                Label zoomLabel = new Label(getZealBar(), SWT.None);
                zoomLabel.setText("Zoom:");
                
                Button down = new Button(getZealBar(), SWT.PUSH);
                down.setImage(zoomOutImage);
                down.addSelectionListener(new SelectionListener() {

                    public void widgetDefaultSelected(SelectionEvent e) {}

                    public void widgetSelected(SelectionEvent e) {
                        zoomOut();
                    }});
                
                Button up = new Button(getZealBar(), SWT.PUSH);
                up.setImage(zoomInImage);
                up.addSelectionListener(new SelectionListener() {

                    public void widgetDefaultSelected(SelectionEvent e) {}

                    public void widgetSelected(SelectionEvent e) {
                        zoomIn();
                    }});

		Composite myControl = new Composite(getZealBar(), SWT.NONE);
		myControl.setLayout(new CellLayout(2).setMargins(10, 10));

		radioButtons = new Button[2];
		radioButtons[0] = new Button(getZealBar(), SWT.RADIO | SWT.RIGHT);
		radioButtons[0].setText("Linear time scale  ");
		radioButtons[0].setSelection(!isLog);
		radioButtons[0]
				.setToolTipText(CHANGES_BETWEEN_LINEAR_AND_LOGARITMIC_TIME_BASE);
		radioButtons[0].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			public void widgetSelected(final SelectionEvent e) {
				setLogarithmic(radioButtons[1].getSelection());
				refresh();
			}
		});
		radioButtons[1] = new Button(getZealBar(), SWT.RADIO | SWT.RIGHT);
		radioButtons[1].setText("Logarithmic time scale");
		radioButtons[1]
				.setToolTipText(CHANGES_BETWEEN_LINEAR_AND_LOGARITMIC_TIME_BASE);
		radioButtons[1].setSelection(isLog);
		
		
		analyzer = new TimingStatisticsAnalyzer(this, drawingAreaB);
		analyzer.createTimingAnalysisControls(parent);
		
	}
		

	private void handleVScrollBar(final int keyCode) {
		if (keyCode == SWT.ARROW_UP) {
			scrollB.getVerticalBar().setSelection(
					scrollB.getVerticalBar().getSelection() - 10);
		}
		if (keyCode == SWT.ARROW_DOWN) {
			scrollB.getVerticalBar().setSelection(
					scrollB.getVerticalBar().getSelection() + 10);
		}
		drawingAreaB.setLocation(drawingAreaB.getLocation().x, -scrollB
				.getVerticalBar().getSelection());
		scrollOffset = scrollB.getOrigin().y;
	}

	private void createDataControl() {
		query = new GanttQuery(getInput().getTimeCluster());
		getLogset().addQuery(query);
		/*
		 * for (final ILogBrowserContribution contribution :
		 * ViewContributorFactory .createContributors(GanttChart.BROWSER_ID)) {
		 * addContributor(contribution); contribution.init(this); }
		 */
	}

	void drawA(final GC graphics, final Rectangle bound) {
		if (query == null || getInput() == null || drawingAreaA.isDisposed()) {
			renderingData.setDurations(null);
			renderingData.setGraphics(graphics);
			renderer.drawA(renderingData);
			ganttDrawA.paint();
			return;
		}

		if (graphics == null) {
			throw new IllegalArgumentException(GRAPHICS_MAY_NOT_BE_NULL);
		}

		boolean doDrawEvents = SeUiPlugin.getDefault().getPreferenceStore()
				.getBoolean(GanttPreferencePage.TAG_SHOW_EVENTS);

		renderingData.setDurations(getExecutions());
		renderingData.setColorMap(getInput().getLog().getColorMap());
		renderingData.setBounds(bound);
		renderingData.setGraphics(graphics);
		final StatisticQuery stat = StatisticQuery.valueOf(getInput()
				.getLogset());
		taskQuery = TaskQuery.valueOf(getInput().getLogset());
		// renderingData.setTasks(new ArrayList<ITask>(taskQuery
		// .getFilteredTasks()));
		renderingData.setTasks(extractTasks(getExecutions()));
		renderingData.setLogSpan(stat.getMinTs(), stat.getMaxTs());
		renderingData.setLog(this.isLogarithmic());
		renderingData.setDoDrawEvents(doDrawEvents);
		renderingData.setEvents(query.getEvents());
		renderingData.setScrollOffset(scrollOffset);
		renderingData.setSashXPos(vSash.getBounds().x);
		ganttDrawA = renderer.drawA(renderingData);
		if (renderingData.drawnSize() == null) {
			return;
		}
		scrollA.setMinWidth(getMaxTextWidth(graphics));
		final int minHeight = renderingData.drawnSize().y;
		if (minHeight != scrollA.getMinHeight()) {
			scrollA.setMinHeight(minHeight);
			scrollB.setMinHeight(minHeight);
		}
		ganttDrawA.paint();
	}

	void drawB(final GC graphics, final Rectangle bound) {
		if (query == null || getInput() == null || drawingAreaB.isDisposed()) {
			renderingData.setDurations(null);
			renderingData.setGraphics(graphics);
			renderer.drawB(renderingData);
			ganttDrawB.paint();
			return;
		}
		

		if (graphics == null) {
			throw new IllegalArgumentException(GRAPHICS_MAY_NOT_BE_NULL);
		}
		
		long currentTime = getInput().getTimeCluster().getCurrentTime();
		if(getExecutions().size() != 0 && currentTime == 0) {
		    getInput().getTimeCluster().setCurrentTime(Logset.valueOf(getInput().getLog().getId())
                            .getEventAtIndex(0).getTimeReference());	                
	        }
		renderingData.setMarker(currentTime);
		renderingData.setBounds(bound);
		renderingData.setGraphics(graphics);
		renderingData.setScrollOffset(0);
		ganttDrawB = renderer.drawB(renderingData);

		// Hide the Task/Process-names by drawing them outside the visible area.
		// Ugly? Yes very, but it is a quick fix. Something must be done to the
		// ganttDraw.pain(); below instead.
		List<?> children = ganttDrawB.getChildren();
		for (Object object : children) {
			if (object instanceof TaskLaneEntry) {
				TaskLaneEntry tle = (TaskLaneEntry) object;
				org.eclipse.draw2d.geometry.Rectangle rect = tle.getBounds();
				rect.y = -1 * rect.y;
			}
		}
		if (renderingData.drawnSize() == null) {
			return;
		}
		final int minHeight = renderingData.drawnSize().y;
		if (minHeight != scrollA.getMinHeight()) {
			scrollA.setMinHeight(minHeight);
			scrollB.setMinHeight(minHeight);
		}
		getContributions(ganttDrawB);
		ganttDrawB.paint();
		applyDecorations(ganttDrawB);
	}

	/**
	 * Initializes a context menu and registers it for Objectcontributions.
	 * 
	 */

	private void initContextMenu() {
		menuManager = new MenuManager();
		menuManager
				.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(final IMenuManager manager) {
				manager.add(new GroupMarker(
						IWorkbenchActionConstants.MB_ADDITIONS));
				manager.add(new Separator());
				manager.add(new Action("Preferences...") {
					@Override
					public void run() {
						PreferencesUtil.createPreferenceDialogOn(
								drawingAreaB.getShell(),
								GanttPreferencePage.ID, null, null).open();
					}
				});

			}

		});

		contextMenu = menuManager.createContextMenu(drawingAreaB);
		drawingAreaB.setMenu(contextMenu);
		contextMenu = menuManager.createContextMenu(drawingAreaA);
		drawingAreaA.setMenu(contextMenu);

		if (null != getInput()) {
			getInput().getLog().getColorMap().addChangeListener(changeListener);
		}
	}

	@Override
	public void dispose() {
		drawingAreaA.dispose();
		drawingAreaB.dispose();
		colorProvider.removeChangeListener(changeListener);
		getInput().getLog().getColorMap().removeChangeListener(changeListener);
		GanttPreferencePage.removeChangeListener(changeListener);
		IFWFacade.removeChangeListener(changeListener);
		getLogset().removeQuery(query);
		
		if(canvasBImage != null) {
		    canvasBImage.dispose();
		}		
		zoomInImage.dispose();
        zoomOutImage.dispose();
        
		analyzer.dispose();
	}

	public void refresh() {
		refresher.schedule(100);
	}

	public static String getNextViewId() {
		return GanttChart.BROWSER_ID + "_" + System.currentTimeMillis();
	}

	public void synch(final TimeEvent event) {
		try {
			if (getLogset().isLocked()) {
				return;
			}
		} catch (NullPointerException npe) {
		}
		if (drawingAreaA.isDisposed() || drawingAreaB.isDisposed()) {

			event.getSource().removeSynchable(this);
			return;
		}
		if ((query != null) && (taskQuery != null)) {
			query.setFilter(taskQuery.getFilter());
		}
		drawingAreaA.redraw();
		drawingAreaB.redraw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zealcore.se.ui.views.IStepable#stepBack()
	 */
	public void stepBack() {

		if (getSwapEvents() == null || getSwapEvents().size() < 1) {
			return;
		}

		ITimed stepTo = getSwapEvents().get(0);
		final long currentTime = getInput().getTimeCluster().getCurrentTime();

		for (final IProcessSwitch d : getSwapEvents()) {
			if (d.getTimeReference() < currentTime) {
				stepTo = d;
			} else {
				break;
			}
		}

		stepTo(stepTo);
	}

	private void stepTo(final ITimed stepTo) {
		this.setSelection(stepTo);
		getInput().getTimeCluster().setCurrentTime(stepTo.getTimeReference());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zealcore.se.ui.views.IStepable#stepForward()
	 */
	public void stepForward() {

		if (getSwapEvents() == null || getSwapEvents().size() < 1) {
			return;
		}

		ITimed stepTo = getSwapEvents().get(0);
		final long currentTime = getInput().getTimeCluster().getCurrentTime();

		for (final IProcessSwitch d : getSwapEvents()) {

			if (d.getTimeReference() > currentTime) {

				stepTo = d;
				break;
			}
		}

		if (stepTo.getTimeReference() >= currentTime) {
			stepTo(stepTo);
		}
	}

	public void zoomIn() {
		renderingData.setZoomLevel(Math.min(GanttChart.MAX_ZOOM_LEVEL,
				renderingData.getZoomLevel() * 1.25d));
		refresh();
	}

	public void zoomOut() {
		renderingData.setZoomLevel(Math.max(CommonConstants.MIN_ZOOM_LEVEL,
				renderingData.getZoomLevel() / 1.25d));
		refresh();
	}

	private List<ITaskDuration> getExecutions() {
		return query.getExecutions(viewWindow, viewWindow);
	}

	private List<IProcessSwitch> getSwapEvents() {
		return query.getSwap();
	}

	public void fillReport(final AbstractReport report) {
		final Image image = new Image(getSite().getShell().getDisplay(),
				new Rectangle(0, 0, GanttChart.REPORT_WIDTH,
						GanttChart.REPORT_HEIGHT));
		final GC gc = new GC(image);
		final Rectangle rectA = new Rectangle(0, 0, GanttChart.REPORT_WIDTH,
				GanttChart.REPORT_HEIGHT);
		rectA.width = vSash.getBounds().x;
		final Rectangle rectB = new Rectangle(rectA.width, 0,
				GanttChart.REPORT_WIDTH - rectA.width, GanttChart.REPORT_HEIGHT);
		drawA(gc, rectA);
		drawB(gc, rectB);
		gc.dispose();
		final ImageReportItem item = new ImageReportItem(image.getImageData());
		item.setName(getTitle());
		item.setDescription(getTitleToolTip());
		image.dispose();
		report.addReportData(item);
	}

	@SuppressWarnings("unchecked")
	private Object getObjectAt(final MouseEvent e, final GanttDraw ganttDraw) {
		if (ganttDraw == null) {
			return null;
		}
		final List<IFigure> children = new ArrayList<IFigure>(ganttDraw
				.getChildren());
		int z = Integer.MIN_VALUE;

		Object at = ganttDraw.findFigureAt(e.x, e.y);
		for (final IFigure f : children) {
			if (f instanceof ObjectFigure) {
				final ObjectFigure fig = (ObjectFigure) f;
				if (fig.containsPoint(e.x, e.y) && fig.getZ() > z) {
					at = fig.getData();
					z = fig.getZ();
				}
			}
		}
		return at;
	}

	private int getMaxTextWidth(final GC graphics) {
		int x = 0;
		Iterable<ITask> tasks = renderingData.getTasks();
		for (ITask task : tasks) {
			x = Math.max(x, SWTUtil.textLength(graphics, task.getName()));
		}
		return x + 8;
	}

	public static class TaskFilter implements IFilter<IObject> {

		private final IObject out;

		private boolean checked;

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public TaskFilter(final IObject a) {
			super();
			out = a;
		}

		public boolean filter(final IObject x) {
			if (x instanceof ITask) {
				boolean isEq = out.equals(x);
				return !isEq;

			}
			return true;
		}

		@Override
		public String toString() {
			return out.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (out == null ? 0 : out.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TaskFilter other = (TaskFilter) obj;
			if (out == null) {
				if (other.out != null) {
					return false;
				}
			} else if (!out.equals(other.out)) {
				return false;
			}
			return true;
		}
	}

	public void setFocus() {
		drawingAreaB.setFocus();
	}

	public String getName() {
		return GanttChart.NAME;
	}

	public Collection<IFilter<IObject>> getFilter() {
		final List<IFilter<IObject>> tasks = new ArrayList<IFilter<IObject>>();
		for (final ITask iTask : taskQuery.getExecutedTasks()) {
			tasks.add(new TaskFilter(iTask));
		}
		for (final ITask iTask : taskQuery.getNotExecutedTasks()) {
			tasks.add(new TaskFilter(iTask));
		}
		return tasks;
	}

	public Collection<IFilter<IObject>> getFilterSelections() {
		Collection<IFilter<IObject>> filter = taskQuery.getFilter();
		return filter;
	}

	public void setFilter(final Collection<IFilter<IObject>> newFilters) {
		query.setFilter(newFilters);
		taskQuery.setFilter(newFilters);
		GanttChart.this.refresh();
	}

	public boolean isLogarithmic() {
		return this.isLog;
	}

	public void setLogarithmic(final boolean isLog) {
		this.isLog = isLog;
	}

	public void toggleScale() {
		this.isLog = !this.isLog;
	}

	private List<ITask> extractTasks(final List<ITaskDuration> executions) {
		Set<ITask> tasks = new HashSet<ITask>();
		for (final ITaskDuration taskDuration : executions) {
			tasks.add((ITask) ((GenericTaskExecution) taskDuration)
					.getStartEvent().getResourceUserIn());
			tasks.add((ITask) ((GenericTaskExecution) taskDuration)
					.getStartEvent().getResourceUserOut());
		}
		List<ITask> temp = new ArrayList<ITask>();
		for (ITask artifact : tasks) {
			temp.add(artifact);
		}
		
        List<ITask> filteredTasks = new ArrayList<ITask>();
        Set<ITask> taskExecCopy = new HashSet<ITask>();
        taskExecCopy.addAll(temp);
        Collection<IFilter<IObject>> filterOutThese = query.getFilter();
        if (filterOutThese.size() > 0) {
            for (ITask task : temp) {
                for (final IFilter<IObject> f : filterOutThese) {
                    if (!f.filter(task)) {
                        filteredTasks.add(task);
                        break;
                    }
                }
            }
            taskExecCopy.removeAll(filteredTasks);
        }
 
        return new ArrayList<ITask>(taskExecCopy);
	}
	

    public void handleCtrlKeys(final KeyEvent e) {
        analyzer.handleKeyEvents(e);
    }
}
