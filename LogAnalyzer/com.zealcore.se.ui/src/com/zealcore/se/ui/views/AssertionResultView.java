/*
 * 
 */
package com.zealcore.se.ui.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.assertions.AssertionRunner;
import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.services.IAsserionReportListener;
import com.zealcore.se.core.services.IAssertionReportEvent;
import com.zealcore.se.core.services.IAssertionReportService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.actions.RunAssertions;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.TreeReportItem;
import com.zealcore.se.ui.core.report.TreeReportItem.TreeNode;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.search.NamedItemContentProvider;
import com.zealcore.se.ui.search.NamedItemLabelProvider;

public class AssertionResultView

extends ViewPart implements IReportContributor {

	private static final int AUTO_EXPAND_LEVEL = 2;

	public static final String VIEW_ID = "com.zealcore.se.ui.views.AssertionResultView";

	private TreeViewer viewer;

	private static IAsserionReportListener listener;

	private IAssertionReportEvent lastEvent;

	private Label assertionInfoLabel;

	private AssertionRunner.AssertionInfo info;

	private Action prevAction;

	private Action nextAction;

	private IChangeListener importListener;

	public AssertionResultView() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		/**
		 * NamedItemContentProvider NamedItemLabelProvider
		 */

		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.verticalAlignment = GridData.FILL;

		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.verticalAlignment = GridData.FILL;

		assertionInfoLabel = new Label(comp, SWT.NONE);
		assertionInfoLabel.setText("Assertion Results");
		assertionInfoLabel.setLayoutData(gridData);

		final TreeViewer treeViewer = new TreeViewer(comp, SWT.BORDER);
		treeViewer.getTree().setLayoutData(gridData1);
		treeViewer.setAutoExpandLevel(AssertionResultView.AUTO_EXPAND_LEVEL);

		viewer = treeViewer;

		final NamedItemLabelProvider concreteLabelProvider = new NamedItemLabelProvider();

		final DecoratingLabelProvider provider = new DecoratingLabelProvider(
				concreteLabelProvider, new Decorator());
		viewer.setLabelProvider(provider);
		viewer.setContentProvider(new NamedItemContentProvider());

		getViewSite().setSelectionProvider(viewer);

		makeActions();

		comp.layout(true);

		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager mgr = bars.getToolBarManager();

		fillToolbar(mgr, parent);
		importListener = new IChangeListener() {
			public void update(final boolean changed) {
				if (changed) {
					refresh();
				}
			}
		};
		IFWFacade.addChangeListener(importListener);

	}

	private void refresh() {
		viewer.setInput(null);
		nextAction.setEnabled(false);
		prevAction.setEnabled(false);
		assertionInfoLabel.setText("No assertion results available.");
	}

	private void fillToolbar(final IToolBarManager mgr,
			final Composite composite) {
		prevAction = new Action("Previous", SWT.PUSH) {
			@Override
			public void run() {
				info.setCurrentHitIndex(info.getCurrentHitIndex() - 1);
				execute(this, info);
				setChecked(false);
			}
		};
		prevAction.setImageDescriptor(SeUiPlugin.imageDescriptorFromPlugin(
				SeUiPlugin.PLUGIN_ID, "icons/search/search_next.gif"));
		prevAction.setEnabled(false);

		nextAction = new Action("Next", SWT.PUSH) {
			@Override
			public void run() {
				info.setCurrentHitIndex(info.getCurrentHitIndex() + 1);
				execute(this, info);
				setChecked(false);
			}
		};
		nextAction.setImageDescriptor(SeUiPlugin.imageDescriptorFromPlugin(
				SeUiPlugin.PLUGIN_ID, "icons/search/search_prev.gif"));
		nextAction.setEnabled(false);

		mgr.add(prevAction);
		mgr.add(nextAction);
	}

	private void execute(final IAction action,
			final AssertionRunner.AssertionInfo info) {
		new RunAssertions().runSafe(action, info);
		checkButtons();
	}

	private void checkButtons() {
		if (prevAction != null) {
			prevAction.setEnabled(checkPreviousButtonStatus());
		}
		if (nextAction != null) {
			nextAction.setEnabled(checkNextButtonStatus());
		}
	}

	private boolean checkPreviousButtonStatus() {
		if ((info == null) || (info.getCurrentHitIndex() == 0)) {
			return false;
		} else {
			return true;
		}
	}

	private boolean checkNextButtonStatus() {
		if ((info != null)
				&& (info.getCurrentHitIndex() + 1 < info.getHitsSize())) {
			return true;
		} else {
			return false;
		}
	}

	private void makeActions() {

		getViewSite().getActionBars().getToolBarManager().add(
				new Action("Collapse All") {
					@Override
					public void run() {
						viewer.getTree().setRedraw(false);
						viewer.collapseAll();
						viewer.getTree().setRedraw(true);
					}

					@Override
					public ImageDescriptor getImageDescriptor() {
						return IconManager
								.getImageDescriptor(IconManager.COLLAPSE_ALL);
					}
				});

		/*
		 * This double click action will set the time of the associated viewset
		 * to the time of the double clicked element (if it is a IObject and
		 * implements the ITimed interface
		 */
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(final DoubleClickEvent event) {
				final IStructuredSelection sel = (IStructuredSelection) event
						.getSelection();
				IObject item = null;

				if (sel.getFirstElement() instanceof IObject) {
					item = (IObject) sel.getFirstElement();
				}

				if (sel.getFirstElement() instanceof SEProperty) {
					final SEProperty prop = (SEProperty) sel.getFirstElement();
					if (prop.getData() instanceof IObject) {
						item = (IObject) prop.getData();

					}
				}

				if (item instanceof ITimed) {
					final ITimed timed = (ITimed) item;
					fireTimeEvent(timed);
				}
			}

		});
	}

	private void fireTimeEvent(final ITimed timed) {
		Assert.isNotNull(timed);
		final UUID id = lastEvent.getLogSession().getId();
		Assert.isNotNull(id);
		final ILogViewInput input = CaseFileManager
				.getInputByUID(id.toString());
		Assert.isNotNull(input);
		input.getTimeCluster().setCurrentTime(timed.getTimeReference());
	}

	@Override
	public void setFocus() {
	}

	public void setAssertionResults(final IAssertionReportEvent event) {
		lastEvent = event;
		final List<IAssertionSetResult> sortedResults = new ArrayList<IAssertionSetResult>();

		for (final IAssertionSetResult result : event.getReport()
				.getAssertionSetResults()) {
			sortedResults.add(result);
		}
		Collections.sort(sortedResults, new Comparator<IAssertionSetResult>() {
			public int compare(final IAssertionSetResult o1,
					final IAssertionSetResult o2) {
				if (o1.hasFailed() || o2.hasFailed()) {
					if (o1.hasFailed() && o2.hasFailed()) {
						return o1.getAssertionSet().getName().compareTo(
								o2.getAssertionSet().getName());
					} else if (o1.hasFailed()) {
						return -1;
					} else if (o2.hasFailed()) {
						return 1;
					}
				}
				return o1.getAssertionSet().getName().compareTo(
						o2.getAssertionSet().getName());
			}
		});

		viewer.setInput(sortedResults);
		updateLabel();
	}

	public static IAsserionReportListener getReportListener() {
		// Running in Non-UI Thread
		if (AssertionResultView.listener == null) {
			AssertionResultView.listener = new IAsserionReportListener() {
				public void reportEvent(final IAssertionReportEvent event) {

					Display.getDefault().asyncExec(new Runnable() {
						// Running in UI Thread.
						public void run() {
							final IWorkbenchPage activePage = SeUiPlugin
									.getDefault().getWorkbench()
									.getActiveWorkbenchWindow().getActivePage();
							try {
								final IWorkbenchPart activePart = activePage
										.getActivePart();
								final IViewPart part = activePage
										.showView(AssertionResultView.VIEW_ID);
								activePage.activate(activePart);
								if (part instanceof AssertionResultView) {
									final AssertionResultView view = (AssertionResultView) part;
									view.setAssertionResults(event);
								}
							} catch (final PartInitException e) {
								throw new RuntimeException(e);
							}
						}
					});
				}

				public void clearEvent() {
				}
			};
		}
		return AssertionResultView.listener;

	}

	private static class Decorator extends LabelProvider implements
			ILabelDecorator {

		private static final int ICON_SIZE = 16;

		public Image decorateImage(final Image image, final Object element) {

			ImageDescriptor descriptor = IconManager
					.getImageDescriptor(IconManager.ERROR_OVERLAY);
			if (element instanceof IAssertionSetResult) {
				final IAssertionSetResult set = (IAssertionSetResult) element;
				if (!set.hasFailed()) {
					descriptor = IconManager
							.getImageDescriptor(IconManager.SUCCESS_OVERLAY);
				}
			} else if (element instanceof IAssertionResult) {
				final IAssertionResult set = (IAssertionResult) element;
				if (!set.hasFailed()) {
					descriptor = IconManager
							.getImageDescriptor(IconManager.SUCCESS_OVERLAY);
				}
			} else {
				return image;
			}
			final ImageDescriptor overlayImageDescriptor = descriptor;

			final CompositeImageDescriptor desc = new CompositeImageDescriptor() {

				@Override
				protected void drawCompositeImage(final int width,
						final int height) {

					drawImage(image.getImageData(), 0, 0);

					drawImage(
							overlayImageDescriptor.getImageData(),
							0,
							Decorator.ICON_SIZE
									- overlayImageDescriptor.getImageData().width
									- 1);
				}

				@Override
				protected Point getSize() {
					return new Point(Decorator.ICON_SIZE, Decorator.ICON_SIZE);
				}

			};

			final Image createImage = desc.createImage(true);
			return createImage;
		}

		public String decorateText(final String text, final Object element) {
			return text;
		}

	}

	public void fillReport(final AbstractReport report) {

		final String topNodeTitle = "Assertions";
		final TreeNode node = new TreeNode(topNodeTitle);
		final TreeItem[] items = viewer.getTree().getItems();
		for (final TreeItem item : items) {
			final TreeNode myNode = new TreeNode(item.getText());
			node.addNode(myNode);
			fillNode(myNode, item);
		}

		final TreeReportItem reportItem = new TreeReportItem();
		reportItem.addNode(node);
		reportItem.setName(topNodeTitle);
		reportItem.setDescription("Expanded Assertions");
		report.addReportData(reportItem);
	}

	private void fillNode(final TreeNode parent, final TreeItem item) {
		for (final TreeItem treeItem : item.getItems()) {

			String text = treeItem.getText();
			if (treeItem.getData() instanceof IAssertionResult) {
				final IAssertionResult ass = (IAssertionResult) treeItem
						.getData();
				if (ass.hasFailed()) {
					text += " - failed";
				}

			}
			final TreeNode node = new TreeNode(text);
			parent.addNode(node);
			if (treeItem.getExpanded()) {
				fillNode(node, treeItem);
			}
		}
	}

	@Override
	public void dispose() {
		final IServiceProvider serviceProvider = SeCorePlugin.getDefault();
		final IAssertionReportService resultConsumer = serviceProvider
				.getService(IAssertionReportService.class);
		resultConsumer.clearAssertionSetResults();
		super.dispose();
		if (importListener != null) {
			IFWFacade.removeChangeListener(importListener);
		}
	}

	public void setInfo(AssertionRunner.AssertionInfo info) {
		this.info = info;
	}

	public AssertionRunner.AssertionInfo getInfo() {
		return info;
	}

	private void updateLabel() {
		String str = "Assertion Results for the events ";

		if (info.getHitsSize() == 0) {
			str += " (Not Found)";
		} else {
			AssertionRunner.Hit hit = info.getCurrentHit();
			str += String
					.valueOf((hit.getFirstLogEventIndex() < info
							.getTotalLogEventsCount()) ? (hit
							.getFirstLogEventIndex() + 1) : hit
							.getFirstLogEventIndex())
					+ " to "
					+ String.valueOf(hit.getLastLogEventIndex())
					+ " out of "
					+ String.valueOf(info.getTotalLogEventsCount());
		}
		assertionInfoLabel.setText(str);
		checkButtons();
	}
}
