/**
 * 
 */
package com.zealcore.se.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.TaskQuery;
import com.zealcore.se.core.model.AbstractTaskRoot;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.services.IAsserionReportListener;
import com.zealcore.se.core.services.IAssertionReport;
import com.zealcore.se.core.services.IAssertionReportEvent;
import com.zealcore.se.core.services.IAssertionReportService;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.TreeReportItem;
import com.zealcore.se.ui.core.report.TreeReportItem.TreeNode;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.search.NamedItemContentProvider;
import com.zealcore.se.ui.search.NamedItemLabelProvider;
import com.zealcore.se.ui.util.ArtifactSelection;

/**
 * @author stch
 * 
 */
public class ResourceNavigator extends ViewPart implements ISelectionListener,
        IAsserionReportListener, IReportContributor {

    public static final String HELP_ID = "com.zealcore.se.ui.views_ResourceNavigator";

    private ContentViewer viewer;

    private Logset wrapped;

    private IAssertionReportEvent lastReportEvent;

    private final IAssertionReportService reportService;

    private final Map<Object, Boolean> objectFailedMap = new HashMap<Object, Boolean>();

    /**
     * 
     */
    public ResourceNavigator() {
        reportService = SeCorePlugin.getDefault().getService(
                IAssertionReportService.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(final Composite parent) {

        final TreeViewer treeViewer = new TreeViewer(parent, SWT.NULL);
        treeViewer.addFilter(new LogFileViewFilter());

        final IWorkbenchHelpSystem helpSystem = getSite().getWorkbenchWindow()
                .getWorkbench().getHelpSystem();
        helpSystem.setHelp(parent, ResourceNavigator.HELP_ID);

        viewer = treeViewer;

        final NamedItemLabelProvider concreteProvider = new NamedItemLabelProvider();
        // Use the private static class Decorator as the decorator
        final DecoratingLabelProvider labelProvider = new DecoratingLabelProvider(
                concreteProvider, new Decorator());
        viewer.setLabelProvider(labelProvider);

        viewer.setContentProvider(new FilteredObjectContentProvider());

        createContextMenu();
        register();
    }

    void createContextMenu() {}

    void register() {
        final ISelectionService selectionService = getViewSite()
                .getWorkbenchWindow().getSelectionService();
        selectionService.addPostSelectionListener(this);

        getViewSite().setSelectionProvider(getViewer());
        selectionChanged(null, selectionService
                .getSelection(SystemNavigator.VIEW_ID));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {}

    /**
     * Tries to find out which logsession and dataprovider to use to use for
     * building the resource tree
     */
    public void selectionChanged(final IWorkbenchPart part,
            final ISelection selection) {
        // pre condititions
        if (part == this) {
            return;
        }
        if (!(selection instanceof IStructuredSelection)) {
            return;
        }

        final IStructuredSelection struct = (IStructuredSelection) selection;

        Object firstElement = struct.getFirstElement();
        if (firstElement == null) {
            return;
        }

        if (firstElement instanceof ILogViewInput) {
            final ILogViewInput input = (ILogViewInput) firstElement;
            buildFromInput(input);
            return;
        }

        // Check ArtifactSelection
        if (firstElement instanceof ArtifactSelection) {
            final ArtifactSelection art = (ArtifactSelection) firstElement;
            buildFromInput(art.getSource());
            return;
        }

        if (firstElement instanceof IAdaptable) {
            final IAdaptable adaptable = (IAdaptable) firstElement;
            final Object adapter = adaptable
                    .getAdapter(ILogSessionWrapper.class);
            if (adapter != null) {
                firstElement = adapter;
            }
        }

        // Check ILogSessionWrapper (System Navigator)
        if (firstElement instanceof ILogSessionWrapper) {
            final ILogSessionWrapper wrapper = (ILogSessionWrapper) firstElement;

            buildResourceTree(Logset.valueOf(wrapper.getId()));
            return;
        }

        // Check the adaptable
        if (firstElement instanceof IAdaptable) {
            final IAdaptable adapt = (IAdaptable) firstElement;
            final Object adapter = adapt.getAdapter(ILogViewInput.class);
            if (adapter instanceof ILogViewInput) {
                final ILogViewInput input = (ILogViewInput) adapter;
                buildFromInput(input);
            }
            return;
        }

        // Check adapter manager
        final Object adapter = Platform.getAdapterManager().getAdapter(
                firstElement, ILogViewInput.class);
        if (adapter instanceof ILogViewInput) {
            final ILogViewInput input = (ILogViewInput) adapter;
            buildFromInput(input);
            return;
        }
    }

    private void buildFromInput(final ILogViewInput adapter) {

        final ILogViewInput input = adapter;

        buildResourceTree(input.getLogset());

    }

    /**
     * Builds the resource tree from the dataprovider and the selected
     * logsession
     * 
     * @param log
     */
    private void buildResourceTree(final Logset log) {
        if (wrapped != log) {

            wrapped = log;
            final Collection<IArtifact> roots = getArtifactRoots(log);
            final Collection<IObject> artifactRoots = new ArrayList<IObject>();
            artifactRoots.addAll(roots);

            // Remove and add - to get the latest report for the this logsession
            reportService.removeAssertionReportListener(this);
            reportService.addAssertionReportListener(this);

            recomputeAssertionStatus(artifactRoots);
            viewer.setInput(artifactRoots);
        }

    }

    class TaskRoot extends AbstractTaskRoot {
        public TaskRoot() {
            super();
            this.setRoot(true);
        }

        private void clear() {
            setTaskList(null);
            setTaskNonExecList(null);
        }

        @Override
        public LogFile getLogFile() {
            return super.getLogFile();
        }
    }

    private final TaskRoot taskRoot = new TaskRoot();

    private Collection<IArtifact> getArtifactRoots(final Logset log) {
        final Set<IArtifact> roots = new LinkedHashSet<IArtifact>();

        final TaskQuery taskQ = TaskQuery.valueOf(log);
        taskRoot.clear();
        taskRoot.setName("Task Exec lists");
        taskRoot.setTaskList(taskQ.getExecutedTasks());
        taskRoot.setTaskNonExecList(taskQ.getNotExecutedTasks());
        roots.add(taskRoot);
        for (final IArtifact a : log.getArtifacts()) {
            if (a.isRoot()) {
                roots.add(a);
            }
        }
        return roots;
    }

    private void recomputeAssertionStatus(final Object artifactRoots) {
        objectFailedMap.clear();
        if (lastReportEvent != null) {
            ResourceUtil.compute(objectFailedMap, lastReportEvent.getReport(),
                    artifactRoots, (ITreeContentProvider) viewer
                            .getContentProvider());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        getViewSite().getWorkbenchWindow().getSelectionService()
                .removePostSelectionListener(this);
        getViewSite().setSelectionProvider(null);
        reportService.removeAssertionReportListener(this);

    }

    ContentViewer getViewer() {
        return viewer;
    }

    void setViewer(final ContentViewer viewer) {
        this.viewer = viewer;
    }

    private static final class FilteredObjectContentProvider extends
            NamedItemContentProvider {
        @Override
        public Object[] getChildren(final Object parentElement) {
            final Object[] objects = super.getChildren(parentElement);
            final List<Object> filtered = new ArrayList<Object>();
            for (final Object child : objects) {
                if (child instanceof SEProperty) {
                    final SEProperty property = (SEProperty) child;
                    if (!(property.getData() instanceof LogFile)) {
                        filtered.add(property);
                    }

                } else {
                    filtered.add(child);
                }
            }
            return filtered.toArray();
        }
    }

    /**
     * The LogFileViewFilter will filter out {@link IObject} items.
     */
    private final class LogFileViewFilter extends ViewerFilter {
        @Override
        public boolean select(final Viewer viewer, final Object parentElement,
                final Object element) {
            Object data = element;
            if (element instanceof SEProperty) {
                final SEProperty property = (SEProperty) element;
                data = property.getData();
                if (property.getName().equals("Name")) {
                    return false;
                } else if (property.getName().equals("state")) {
                    return false;
                }
            }
            if (data instanceof LogFile) {
                return false;
            }

            return true;
        }
    }

    private final class Decorator extends LabelProvider implements
            ILabelDecorator {

        private static final int ICON_SIZE = 16;

        public Image decorateImage(final Image image, final Object element) {
            if (lastReportEvent == null) {
                return null;
            }

            final ImageDescriptor imageDescriptor = IconManager
                    .getImageDescriptor(IconManager.ERROR_OVERLAY);

            final CompositeImageDescriptor desc = new CompositeImageDescriptor() {

                @Override
                protected void drawCompositeImage(final int width,
                        final int height) {

                    drawImage(image.getImageData(), 0, 0);
                    drawImage(imageDescriptor.getImageData(), 0, 0);
                }

                @Override
                protected Point getSize() {
                    return new Point(Decorator.ICON_SIZE, Decorator.ICON_SIZE);
                }
            };
            if (element instanceof SEProperty) {
                final SEProperty property = (SEProperty) element;
                if (objectFailedMap.containsKey(property.getData())) {
                    return desc.createImage(true);
                }
            }
            if (objectFailedMap.containsKey(element)) {
                return desc.createImage(true);
            }
            return null;
        }

        public String decorateText(final String text, final Object element) {
            return null;
        }
    }

    public void reportEvent(final IAssertionReportEvent event) {
        if (event.getLogSession().equals(wrapped)) {
            lastReportEvent = event;
            recomputeAssertionStatus(viewer.getInput());
            viewer.refresh();
        }
    }

    static final class ResourceUtil {

        private ResourceUtil() {

        }

        static boolean compute(final Map<Object, Boolean> map,
                final IAssertionReport report, final Object input,
                final ITreeContentProvider provider) {

            // Use to the content provider to get all children - non lazily
            // TODO Check for infinite recurse
            final Object[] elements = provider.getChildren(input);

            boolean failed = false;
            for (final Object element : elements) {
                if (ResourceUtil.compute(map, report, element, provider)) {
                    map.put(input, Boolean.TRUE);
                    failed = true;
                }
            }
            Object data = input;
            if (input instanceof SEProperty) {
                final SEProperty property = (SEProperty) input;
                data = property.getData();
            }

            if (data instanceof IObject) {
                final IObject obj = (IObject) data;
                if (report.hasFailed(obj)) {

                    failed = true;
                }
            }

            if (failed) {
                map.put(data, Boolean.TRUE);
            }

            return failed;
        }

    }

    public void fillReport(final AbstractReport report) {

        final String topNodeTitle = "Resources";
        final TreeNode node = new TreeNode(topNodeTitle);
        final TreeItem[] items = ((TreeViewer) viewer).getTree().getItems();
        for (final TreeItem item : items) {
            final TreeNode myNode = new TreeNode(item.getText());
            node.addNode(myNode);
            fillNode(myNode, item);
        }

        final TreeReportItem reportItem = new TreeReportItem();
        reportItem.addNode(node);
        reportItem.setName(getTitle());
        reportItem.setDescription("Expanded Resources");
        report.addReportData(reportItem);
    }

    private void fillNode(final TreeNode parent, final TreeItem item) {
        for (final TreeItem treeItem : item.getItems()) {
            final TreeNode node = new TreeNode(treeItem.getText());
            parent.addNode(node);
            if (treeItem.getExpanded()) {
                fillNode(node, treeItem);
            }
        }
    }

    public void clearEvent() {
        lastReportEvent = null;
    }
}
