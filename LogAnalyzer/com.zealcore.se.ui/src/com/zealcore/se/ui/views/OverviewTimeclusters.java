package com.zealcore.se.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.ifw.StatisticQuery;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ISynchable;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.dialogs.EditSynchOffsetDialog;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.util.ColumnLayout;
import com.zealcore.se.ui.util.PartListenerAdapter;

public class OverviewTimeclusters extends ViewPart implements IChangeListener {

    private static final String SYNCHRONIZATION_OFFSET_S = "Synchronization Offset (s): ";

    private static final String CURRENT_EVENT = "Current: ";

	private final class InputListener extends PartListenerAdapter {
		@Override
		public void partActivated(final IWorkbenchPartReference partRef) {
			final ILogViewInput clusterInput = getNewInput(partRef);
			if (clusterInput != null) {
				final UIJob trySet = new AbstractSafeUIJob("Updating Overview") {
					@Override
					public IStatus runInUIThreadSafe(
							final IProgressMonitor monitor) {
						if (clusterInput != null
								&& !clusterInput.getLogset().isLocked()) {
							updateViewer(clusterInput);
							input = clusterInput;
						}
						return Status.OK_STATUS;
					}
				};
				trySet.setSystem(true);
				trySet.schedule();
			}
		}

		private ILogViewInput getNewInput(final IWorkbenchPartReference partRef) {
			ILogViewInput ci = null;
			final IWorkbenchPart part = partRef.getPart(false);
			if (part instanceof LogsetEditor) {
				final LogsetEditor ed = (LogsetEditor) part;
				ci = ed.getInput();
			}
			final ILogViewInput clusterInput = ci;
			return clusterInput;
		}
	}

	public static final String VIEW_ID = "com.zealcore.se.ui.views.OverviewTimeclusters";

	private final Collection<Slider> sliders = new ArrayList<Slider>();

	private Composite container;

	private ScrolledComposite middle;

	private ILogViewInput input;

	private Label casefilename;

	private PartListenerAdapter partListenerAdapter;

	private OverviewTimeclusterQuery query;

	private Logset logset;

	private List<Long> results;

	private final UIJob refresher;

	private Composite eventLabelsArea;

	private Label currentEventValue;

	private Slider slider;

        private IAction synchAction;

	public OverviewTimeclusters() {
		super();
		refresher = new AbstractSafeUIJob("Overview Logset UIJob") {
			@Override
			public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
				updateViewer(input);
				return Status.OK_STATUS;
			}
		};
		refresher.setSystem(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {

		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				container.layout(false);
			}
		});
		middle = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
		middle.setLayout(new FillLayout());
		middle.setExpandHorizontal(true);
		middle.setExpandVertical(true);
		container = new Composite(middle, SWT.NULL);
		IFWFacade.addChangeListener(this);
		container.setLayout(ColumnLayout.getInstance());
		casefilename = new Label(container, SWT.NULL);
		casefilename.setText("Not set");
		partListenerAdapter = new InputListener();
		getViewSite().getPage().addPartListener(partListenerAdapter);
		final IEditorPart editor = getViewSite().getPage().getActiveEditor();
		if (editor instanceof LogsetEditor) {
			final LogsetEditor logEditor = (LogsetEditor) editor;
			updateViewer(logEditor.getInput());
		}		
		
		createActions();
		
		fillActionBars();
	}
	
	private void createActions() {
	    synchAction = new SynchAction();	    	    
	}
	
	
	private void fillActionBars() {
	    IActionBars bars = getViewSite().getActionBars();
	    fillLocalToolBar(bars.getToolBarManager());
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
	    manager.add(synchAction);
	}
	
	class SynchAction extends Action {
	    
	    public SynchAction() {
                super("Synchronize...");
                setToolTipText("Set Synchronization Offset");
                setImageDescriptor(IconManager.getImageDescriptor("cluster_chain_medium"));
                setEnabled(false);
            }
	    
	    public void run() {
                EditSynchOffsetDialog offsetDialog = new EditSynchOffsetDialog(
                    getSite().getShell(), input.getTimeCluster(), null);
                if (offsetDialog.open() == Window.OK) {
                    update(true);
                }
	    }	        
	}
	
	private List<ITimeCluster> getSynchedTimeClusters() {
	    List<ITimeCluster> clusterList = new ArrayList<ITimeCluster>();
	    
	    if (input != null) {
	        ICaseFile casefile = input.getCaseFile();
                if (casefile != null) {
                    for (ILogSessionWrapper session : casefile.getLogs()) {
                        ITimeCluster cluster = session.getDefaultViewSet();
                        if (cluster.isChained()) {
                            clusterList.add(cluster);
                        }
                    }
                }
	    }
	    
	    return clusterList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		container.setFocus();
		
		if(synchAction != null) {
		    Control[] controls = container.getChildren();
                    if(input != null && getSynchedTimeClusters().contains(input.getLog().getDefaultViewSet())) {
                        synchAction.setEnabled(true);                        
                    } else {
                        synchAction.setEnabled(false);                        
                    }
		}
		container.getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {

		getViewSite().getPage().removePartListener(partListenerAdapter);
		// Not yet initialized
		if (getViewSite() == null) {
			return;
		}

		super.dispose();
		container.dispose();
		if (eventLabelsArea != null) {
			eventLabelsArea.dispose();
		}
		for (final Slider slid : sliders) {
			slid.setData(null);
			slid.dispose();
			// The removal of slider as synchable is handled by a
			// disposeListener
		}
		middle.dispose();

	}

	private void updateViewer(final ILogViewInput selectedInput) {
		int numberOfEvents;

		if (middle == null || container == null || middle.isDisposed()) {
			return;
		}

		input = selectedInput;
		
		if(synchAction != null) {		    
		    if(getSynchedTimeClusters().contains(input.getLog().getDefaultViewSet())) {
		        synchAction.setEnabled(true);
		    } else {
		        synchAction.setEnabled(false);
		    }
		}
		
		container.dispose();

		container = new Composite(middle, SWT.None);
		container.setLayout(ColumnLayout.getInstance());
		

		// If input somehow got null ( deleted etc) - leave blank
		if (input == null || !input.exists()) {
			return;
		}

		logset = Logset.valueOf(input.getLog().getId());

		switch (logset.getImporterVersionType()) {
		case IFWFacade.IIMPORTER_VERSION:

			// IImporter logic
			query = OverviewTimeclusterQuery.valueOf(logset);
			numberOfEvents = (int) StatisticQuery.valueOf(logset).getNumberOfEvents();
			if (numberOfEvents <= 0) {
				numberOfEvents = query.getResults().size();
			}

			results = query.getResults();
			if (results.size() == 0 || numberOfEvents == 0) {
				// Reinitialize the query
				OverviewTimeclusterQuery.queries.remove(logset);
				query = OverviewTimeclusterQuery.valueOf(logset);
				results = query.getResults();
				numberOfEvents = results.size();
			}
			break;
		case IFWFacade.IEXTENDEDIMPORTER_VERSION:
                // Fall through
		default:
			if (logset.isLocked()) {
				return;
			}
			numberOfEvents = logset.getNumberOfEvents();

		}
		
		casefilename = new Label(container, SWT.NULL);
		final ICaseFile caseFile = input.getCaseFile();

		if (numberOfEvents > 0) {
			casefilename.setText(caseFile.toString());
			for (final ILogSessionWrapper logitem : caseFile.getLogs()) {
				Logset logset2 = Logset.valueOf(logitem.getId());
				if (logset.getId() == logset2.getId()) {
					createSliders(container, logitem, numberOfEvents);
				}
			}
		}

		final Point size = middle.getSize();
		final Point point = container.computeSize(size.x, SWT.DEFAULT);
		point.x = SWT.DEFAULT;
		middle.setContent(container);
		middle.setMinSize(point);

		container.getParent().layout(true);
		if (slider != null && !slider.isDisposed()) {
			currentEventValue.setText(CURRENT_EVENT
					+ (slider.getSelection() + 1));
		}
	}

	private void createSliders(final Composite parent,
			final ILogSessionWrapper logitem, final int nrOfEvents) {
		final Group group = new Group(parent, SWT.SHADOW_IN);
		group.setText(logitem.getLabel(null));
		group.setLayout(ColumnLayout.getInstance());

		final ITimeCluster cluster = logitem.getDefaultViewSet();

		if (cluster.getMax() < cluster.getMin()) {
			//return;
		}
		eventLabelsArea = new Composite(group, SWT.NO_FOCUS);

		eventLabelsArea.setLayout(new FillLayout(SWT.HORIZONTAL));
		Label firstEventLabel = new Label(eventLabelsArea, SWT.LEFT);
		firstEventLabel.setText("1");
		Label lastEventLabel = new Label(eventLabelsArea, SWT.RIGHT);
		lastEventLabel.setText("" + nrOfEvents);

		slider = new Slider(group, SWT.BORDER | SWT.DOUBLE_BUFFERED);

		currentEventValue = new Label(group, SWT.CENTER);
		currentEventValue.setText("");

		slider.setThumb(1);
		slider.setMaximum(nrOfEvents);
		slider.setMinimum(0);

		slider.setData(cluster);
		

		switch (logset.getImporterVersionType()) {
		case IFWFacade.IIMPORTER_VERSION:
			LAOverviewEventListener laEventListener = new LAOverviewEventListener(nrOfEvents);
			slider.addSelectionListener(laEventListener);
			parent.addKeyListener(laEventListener);

			// Attach a synchronizer between the scaler and the cluster
			new Synchronizer(slider, cluster);
			break;
		
		case IFWFacade.IEXTENDEDIMPORTER_VERSION:
                // Fall through
		default:
		    LAExtendedOverviewEventListener laExtendedEventListener = new LAExtendedOverviewEventListener(nrOfEvents);
			slider.addSelectionListener(laExtendedEventListener);
			parent.addKeyListener(laExtendedEventListener);
			new ExtendedSynchronizer(slider, cluster);
		}
		
	}

	class LAOverviewEventListener implements SelectionListener, KeyListener {

		private int nrOfEvents;

		public LAOverviewEventListener(final int noOfEvents) {
			nrOfEvents = noOfEvents;
		}

		public void widgetDefaultSelected(final SelectionEvent e) {
		}

		public void widgetSelected(final SelectionEvent e) {
			final ITimeCluster dataCluster = (ITimeCluster) slider.getData();
			final long value = slider.getSelection();
			if (value < results.size()) {
				// slider.setToolTipText("" + (value + 1));
				long ts = results.get((int) value);
				dataCluster.setCurrentTime(ts);
				currentEventValue.setText(CURRENT_EVENT
						+ (slider.getSelection() + 1));
			}
		}

		public void keyPressed(final KeyEvent e) {
			long ts = -1;
			int index;
			switch (e.keyCode) {
			case SWT.HOME:
				ts = results.get(0);
				break;
			case SWT.END:
				ts = results.get(nrOfEvents - 1);
				break;
			case SWT.ARROW_RIGHT:
				/*
				 * This is a fix that handles if the next event have the same
				 * time stamp as current. We need to jump to the next that have
				 * a bigger time, otherwise will we get stuck at this
				 * position/ts.
				 */
				long currentTs = results.get(slider.getSelection());
				index = slider.getSelection();
				do {
					ts = results.get(index);
					index++;
				} while ((index < nrOfEvents) && (currentTs == ts));
				break;
			case SWT.ARROW_LEFT:
				index = slider.getSelection() - 1;
				if (index >= 0) {
					ts = results.get(index);
				}
				break;
			case SWT.PAGE_DOWN:
				e.keyCode = SWT.ARROW_RIGHT;
				for (int i = 0; i < 10; i++) {
					keyPressed(e);
				}
				e.keyCode = SWT.PAGE_DOWN;
				break;
			case SWT.PAGE_UP:
				e.keyCode = SWT.ARROW_LEFT;
				for (int i = 0; i < 10; i++) {
					keyPressed(e);
				}
				e.keyCode = SWT.PAGE_UP;
				break;
			default:
				break;
			}
			if (ts != -1) {
				final ITimeCluster dataCluster = (ITimeCluster) slider
						.getData();
				dataCluster.setCurrentTime(ts);
			}
			e.doit = true;
		}

		public void keyReleased(final KeyEvent e) {
		}
	}

	class LAExtendedOverviewEventListener implements SelectionListener,
			KeyListener {

		private int nrOfEvents;
		private long oldSelection;

		public LAExtendedOverviewEventListener(final int noOfEvents) {
			nrOfEvents = noOfEvents;
		}

		public void widgetDefaultSelected(final SelectionEvent e) {
		}

		public void widgetSelected(final SelectionEvent e) {
			if ((logset != null) && (logset.isLocked())) {
				e.doit = false;
				slider.setSelection((int) oldSelection);
				return;
			}
			final ITimeCluster dataCluster = (ITimeCluster) slider.getData();
			final long value = slider.getSelection();
			if (value < logset.getNumberOfEvents()) {
				// slider.setToolTipText("" + (value + 1));
				long ts = logset.getEventAtIndex((int) value).getTs();
				dataCluster.setCurrentTime(ts);
				currentEventValue.setText(CURRENT_EVENT
						+ (slider.getSelection() + 1));
			}
			oldSelection = value + 1;
		}

		public void keyPressed(final KeyEvent e) {
			if ((logset != null) && (logset.isLocked())) {
				e.doit = false;
				return;
			}
			long ts = -1;
			int index;
			switch (e.keyCode) {
			case SWT.HOME:
				ts = ((ILogEvent) logset.getEventAtIndex(0)).getTs();
				break;
			case SWT.END:
				ts = ((ILogEvent) logset.getEventAtIndex(nrOfEvents - 1))
						.getTs();
				break;
			case SWT.ARROW_RIGHT:
				/*
				 * This is a fix that handles if the next event have the same
				 * time stamp as current. We need to jump to the next that have
				 * a bigger time, otherwise will we get stuck at this
				 * position/ts.
				 */
				index = slider.getSelection();
				if (((index + 1) < nrOfEvents)) {
					ts = ((ILogEvent) logset.getEventAtIndex(index + 1))
							.getTs();
				}
				break;
			case SWT.ARROW_LEFT:
				index = slider.getSelection() - 1;
				if (index - 1 >= 0) {
					ts = ((ILogEvent) logset.getEventAtIndex(index - 1))
							.getTs();
				}
				break;
			case SWT.PAGE_DOWN:
				e.keyCode = SWT.ARROW_RIGHT;
				for (int i = 0; i < 10; i++) {
					keyPressed(e);
				}
				e.keyCode = SWT.PAGE_DOWN;
				break;
			case SWT.PAGE_UP:
				e.keyCode = SWT.ARROW_LEFT;
				for (int i = 0; i < 10; i++) {
					keyPressed(e);
				}
				e.keyCode = SWT.PAGE_UP;
				break;
			default:
				break;
			}
			if (ts != -1) {
				final ITimeCluster dataCluster = (ITimeCluster) slider
						.getData();
				dataCluster.setCurrentTime(ts);
			}
			e.doit = true;
		}

		public void keyReleased(final KeyEvent e) {
		}

	}

	public void update(final boolean changed) {
		if ((logset != null) && (!logset.isLocked())) {
			refresher.schedule(100);
		}
	}

	static class OverviewTimeclusterQuery extends AbstractQuery {

		private static Map<Logset, OverviewTimeclusterQuery> queries = new HashMap<Logset, OverviewTimeclusterQuery>();

		private List<Long> results = new ArrayList<Long>(1000);

		public List<Long> getResults() {
			return results;
		}

		public static OverviewTimeclusterQuery valueOf(final Logset logset) {
			OverviewTimeclusterQuery query = queries.get(logset);
			if (query == null) {
				query = new OverviewTimeclusterQuery();
				queries.put(logset, query);
				logset.addQuery(query);
			}
			return query;
		}

		/**
		 * IQuery implementation
		 */
		public void initialize(final IDataSource dataSource) {
		}

		public boolean visitBegin(final Reason reason) {
			results.clear();
			return true;
		}

		public boolean visit(final IObject item) {
			if (item instanceof ILogEvent) {
				ILogEvent event = (ILogEvent) item;
				results.add(event.getTs());
			}
			return true;
		}

		public void visitEnd(final boolean atEnd) {
			Collections.sort(results);
		}
	}

	class ExtendedSynchronizer implements ISynchable {
		private final Slider scaler;

		ExtendedSynchronizer(final Slider scaler, final ITimeCluster cluster) {
			this.scaler = scaler;
			scaler.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(final DisposeEvent e) {
					cluster.removeSynchable(ExtendedSynchronizer.this);
				}
			});

			cluster.addSynchable(this);
			scaler.setSelection(logset.getIndexAtTimestamp(cluster
					.getCurrentTime()));
		}

		public void synch(final TimeEvent event) {
			if ((logset != null) && (logset.isLocked())) {
				return;
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					final long currentTime = event.getSource().getCurrentTime();
					if(scaler.isDisposed()) {
					      return;
					}
					// May cause synchronizing loop - ignore if value
					// is the same
					ILogEvent logEvent = logset.getEventAtIndex((int) scaler
							.getSelection());

					if (logEvent != null) {
						if (currentTime != logEvent.getTs()) {
							scaler.setSelection(logset
									.getIndexAtTimestamp(currentTime));
							currentEventValue.setText(CURRENT_EVENT
									+ (scaler.getSelection() + 1));
						}
					} else {
						scaler.setSelection(0);
						currentEventValue.setText(CURRENT_EVENT + 0);
					}
				}
			});
		}
	}

	class Synchronizer implements ISynchable {
		private final Slider scaler;

		Synchronizer(final Slider scaler, final ITimeCluster cluster) {
			this.scaler = scaler;
			scaler.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(final DisposeEvent e) {
					cluster.removeSynchable(Synchronizer.this);
				}
			});

			cluster.addSynchable(this);
			for (int i = 0; i < results.size(); i++) {
				long ts = results.get(i);
				if (ts >= cluster.getCurrentTime()) {
					scaler.setSelection(i);
					return;
				}
			}
			scaler.setSelection(results.size());
		}

		public void synch(final TimeEvent event) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					final long currentTime = event.getSource().getCurrentTime();

					// May cause synchronizing loop - ignore if value
					// is the sameo
					if (currentTime == 0 && results.size() == 0) {
						return;
					}
					if ((!scaler.isDisposed())
							&& ((int) scaler.getSelection() < results.size())) {
						if (currentTime != results.get((int) scaler
								.getSelection())) {
							for (int i = (results.size() - 1); i > -1; i--) {
								long ts = results.get(i);
								if (currentTime >= ts) {
									scaler.setSelection(i);
									currentEventValue.setText(CURRENT_EVENT
											+ (scaler.getSelection() + 1));
									return;
								}
							}
						}
					}
				}
			});
		}
	}
}
