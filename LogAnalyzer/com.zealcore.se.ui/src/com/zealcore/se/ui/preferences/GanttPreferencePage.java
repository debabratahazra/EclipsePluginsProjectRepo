package com.zealcore.se.ui.preferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.TypePackageLabelProvider;
import com.zealcore.se.ui.core.TypePackageTreeContentProvider;

public final class GanttPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    public static final String SEPARATOR = ";";

    public static final String TAG_SHOW_EVENTS_IDS = "com.zealcore.sd.gantt.show.events.ids";

    public static final String TAG_SHOW_EVENTS = "com.zealcore.sd.gantt.show.events";

    public static final String ID = "com.zealcore.se.ui.preferences.Gantt";

    private Tree eventsToShow;

    private CheckboxTreeViewer eventstoShowViewer;

    private Label eventsToShowLabel;

    private Set<IType> showEventsTypes = new LinkedHashSet<IType>();

    private static final Set<IChangeListener> CHANGELISTENERS = new HashSet<IChangeListener>();

    /**
     * Create the preference page
     */
    public GanttPreferencePage() {
        super();
        // Initialize the preference page
        setPreferenceStore(SeUiPlugin.getDefault().getPreferenceStore());
        String idz = getPreferenceStore().getString(TAG_SHOW_EVENTS_IDS);
        ITypeRegistry types = TypeRegistry.getInstance();
        if (types != null && idz.length() > 0) {
            String[] typeArray = idz.split(SEPARATOR);
            Arrays.sort(typeArray);
            for (int i = 0; i < typeArray.length; i++) {
                String type = typeArray[i];
                try {
                    if (types.getType(type) != null) {
                        showEventsTypes.add(types.getType(type));
                    }
                } catch (IllegalArgumentException e) {
                    // Ignore type, does not exist any more.
                }
            }
        }
    }

    public static final IFilter<IType> SEARCHABLE_TYPES = new IFilter<IType>() {
        public boolean filter(final IType type) {
            return type.isSearchable();
        }
    };

    /**
     * Create contents of the preference page
     * 
     * @param parent
     */
    @Override
    public Control createContents(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());

        final Group eventsGroup = new Group(container, SWT.NONE);
        eventsGroup.setText("Events");
        final FormData fdeventsGroup = new FormData();
        fdeventsGroup.bottom = new FormAttachment(80, 0);
        fdeventsGroup.right = new FormAttachment(100, -5);
        fdeventsGroup.top = new FormAttachment(0, 15);
        fdeventsGroup.left = new FormAttachment(0, 5);
        eventsGroup.setLayoutData(fdeventsGroup);
        eventsGroup.setLayout(new FormLayout());

        // showEventsInButton = new Button(eventsGroup, SWT.CHECK);
        eventsToShowLabel = new Label(eventsGroup, SWT.CENTER);
        eventsToShowLabel.setText("Select the events to show in Gantt:");
        final FormData fdshowEventsInButton = new FormData();
        fdshowEventsInButton.top = new FormAttachment(0, 0);
        fdshowEventsInButton.left = new FormAttachment(0, 5);

        eventstoShowViewer = new CheckboxTreeViewer(eventsGroup,
                SWT.FULL_SELECTION | SWT.BORDER);
        eventstoShowViewer
                .setContentProvider(new TypePackageTreeContentProvider());
        eventstoShowViewer.setLabelProvider(new TypePackageLabelProvider());
        eventstoShowViewer.setInput(SeUiPlugin.getDefault()
                .getServiceProvider().getService(ITypeRegistry.class));
        eventstoShowViewer.addFilter(new EventsOnly());
        eventsToShow = eventstoShowViewer.getTree();
        final FormData fdEventsToShow = new FormData();
        fdEventsToShow.bottom = new FormAttachment(100, -5);
        fdEventsToShow.right = new FormAttachment(100, -5);
        fdEventsToShow.top = new FormAttachment(eventsToShowLabel, 0,
                SWT.BOTTOM);
        fdEventsToShow.left = new FormAttachment(eventsToShowLabel, 0, SWT.LEFT);
        eventsToShow.setLayoutData(fdEventsToShow);

        eventstoShowViewer.addCheckStateListener(new ICheckStateListener() {

            public void checkStateChanged(final CheckStateChangedEvent event) {
                if (event.getChecked()) {
                    eventstoShowViewer.setSubtreeChecked(event.getElement(),
                            true);
                } else {
                    eventstoShowViewer.setSubtreeChecked(event.getElement(),
                            false);
                }
            }
        });
        eventstoShowViewer.expandAll();
        eventstoShowViewer.setCheckedElements(showEventsTypes.toArray());
        return container;
    }

    @Override
    protected void performApply() {
        super.performApply();
        apply();
    }

    @Override
    public boolean performOk() {
        apply();
        return super.performOk();
    }

    private void apply() {
        final StringBuilder idz = new StringBuilder();
        showEventsTypes.clear();
        for (final Object object : eventstoShowViewer.getCheckedElements()) {
            if (object instanceof IType) {
                final IType type = (IType) object;
                idz.append(type.getId());
                idz.append(SEPARATOR);
                showEventsTypes.add(type);
            }
        }
        if (idz.length() > 0) {
            getPreferenceStore().setValue(TAG_SHOW_EVENTS, true);
        } else {
            getPreferenceStore().setValue(TAG_SHOW_EVENTS, false);
        }
        getPreferenceStore().setValue(TAG_SHOW_EVENTS_IDS, idz.toString());
        notifyChangeListeners();
    }

    /**
     * Initialize the preference page
     */
    public void init(final IWorkbench workbench) {}

    public boolean isVisible(final ILogEvent event) {
        return showEventsTypes.contains(event.getType());
    }

    public static void addChangeListener(final IChangeListener listener) {
        CHANGELISTENERS.add(listener);
        listener.update(false);
    }

    public static void removeChangeListener(final IChangeListener listener) {
        CHANGELISTENERS.remove(listener);
    }

    private void notifyChangeListeners() {
        for (final IChangeListener listener : CHANGELISTENERS) {
            listener.update(true);
        }
    }

    private static class EventsOnly extends ViewerFilter {

        @Override
        public boolean select(final Viewer viewer, final Object parentElement,
                final Object element) {
            if (element instanceof IType) {
                final IType type = (IType) element;
                final ReflectiveType pureEvent = ReflectiveType
                        .valueOf(ILogEvent.class);
                boolean v = type.isA(pureEvent);
                v &= !type.isA(ReflectiveType.valueOf(IProcessSwitch.class));
                v &= type != pureEvent;

                return v;
            }
            return true;
        }
    }
}
