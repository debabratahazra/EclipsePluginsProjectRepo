package com.zealcore.se.ui.search;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.ISearchAdapter;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.SearchQuery;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.internal.assertions.IModifyListener;
import com.zealcore.se.ui.internal.assertions.ModifyEvent;
import com.zealcore.se.ui.views.NavigatorLabelProvider;

public class DebuggerSearch extends DialogPage implements ISearchPage {

	public static final String PAGE_ID = "com.zealcore.se.ui.search.formSearch";

	private static final int MIN_SEARCH_DIALOG_WIDTH = 450;

	private static final int MIN_SEARCH_DIALOG_HEIGHT = 630;
	
	private static final int SEARCH_DIALOG_WIDTH = 560;

	private static final int SEARCH_DIALOG_HEIGHT = 460;

	private Text text;

	private Composite container;

	private static ISearchPageContainer searchContainer;

	private static Logset logset;

	private static SearchFilterInput searchInput;

	private Label errorMessage;

	private static ISearchAdapter adapter;

    private ScrolledComposite scrolledComposite;
    
    private Composite composite;
    
	public DebuggerSearch() {
	}

	public DebuggerSearch(final String title) {
		super(title);
	}

	public DebuggerSearch(final String title, final ImageDescriptor image) {
		super(title, image);
	}

	public boolean performAction() {
		if (logset != null && searchInput.isOk()) {
			setErrorMessage(null);
			searchInput.buildAdapterRegExp();
			DebuggerSearch.adapter = searchInput.getAdapter();
			final boolean retval = performSearch(searchInput.getAdapter(), null);
			searchInput.deBuildAdapterRegExp();
			return retval;
		}
		updateErrorMessage();

		return false;
	}

	public void performSearchAction(final SearchQuery.SearchInfo searchInfo) {

		if (logset != null && searchInput.isOk()) {
			setErrorMessage(null);
			searchInput.buildAdapterRegExp();
			DebuggerSearch.adapter = searchInput.getAdapter();
			performSearch(searchInput.getAdapter(), searchInfo);
			searchInput.deBuildAdapterRegExp();
			return;
		}
		updateErrorMessage();
		return;
	}

	private void updateErrorMessage() {
		if (logset == null) {
			setErrorMessage("Select a valid logset");
		} else if (!searchInput.isOk()) {
			setErrorMessage("There is a problem in the input");
		} else {
			setErrorMessage(null);
		}
		errorMessage
				.setText(getErrorMessage() == null ? "" : getErrorMessage());
	}

	private boolean performSearch(final ISearchAdapter adapter,
			final SearchQuery.SearchInfo searchInfo) {
		final UISearchQuery query = new UISearchQuery(logset, adapter,
				searchInfo);
		NewSearchUI.runQueryInBackground(query);
		return true;
	}

	public void setContainer(final ISearchPageContainer container) {
		searchContainer = container;
	}

	@Override
	public void setVisible(boolean visible) {
		if(composite != null) {
			composite.getShell().setSize(SEARCH_DIALOG_WIDTH, SEARCH_DIALOG_HEIGHT);
		}
		super.setVisible(visible);
	}
	
	public void createControl(final Composite parent) {
		this.composite = parent;
		container = new MyComposite(parent, SWT.NONE);
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		container.setLayoutData(gridData);
		final GridLayout gridLayout = new GridLayout();
        gridLayout.marginBottom = 0;
        gridLayout.marginTop = 0;
        gridLayout.marginLeft = 0;
        gridLayout.marginRight = 0;
        container.setLayout(gridLayout);
		setControl(container);

        scrolledComposite = new ScrolledComposite(container, SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setLayout(gridLayout);
        scrolledComposite.setLayoutData(new GridData(GridData.FILL,
                GridData.FILL, true, true));
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setMinSize(MIN_SEARCH_DIALOG_WIDTH, MIN_SEARCH_DIALOG_HEIGHT);

        Composite groupComposite = new Composite(scrolledComposite, SWT.NONE);
        groupComposite.setLayout(new GridLayout());
        groupComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        errorMessage = new Label(groupComposite, SWT.NONE);
        errorMessage
                .setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        errorMessage.setForeground(SWTResourceManager.getColor(255, 0, 0));

        Group group = new Group(groupComposite, SWT.NONE);
        group.setText("Scope");


		Button browseButton = new Button(group, SWT.NONE);
		browseButton.setText("Browse...");

		Label label = new Label(group, SWT.RIGHT);
		label.setText("Logset");

		text = new Text(group, SWT.READ_ONLY | SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(255, 255, 255));
		final GroupLayout groupLayout1 = new GroupLayout(group);
		groupLayout1.setHorizontalGroup(groupLayout1.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout1.createSequentialGroup().addContainerGap().add(
						label, GroupLayout.PREFERRED_SIZE, 40,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(text,
						GroupLayout.PREFERRED_SIZE, 333, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.RELATED).add(browseButton,
								GroupLayout.PREFERRED_SIZE, 67,
								GroupLayout.PREFERRED_SIZE).add(18, 18, 18)));
		groupLayout1.setVerticalGroup(groupLayout1.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout1.createSequentialGroup().addContainerGap().add(
						groupLayout1.createParallelGroup(GroupLayout.BASELINE)
								.add(text, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										browseButton).add(label))
						.addContainerGap(22, Short.MAX_VALUE)));
		group.setLayout(groupLayout1);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		

		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				openSelectLogset();
			}
		});

        scrolledComposite.setContent(groupComposite);
        container.getParent().layout(true);
        scrolledComposite.getParent().layout(true);
        groupComposite.layout(true);
        makeContent(groupComposite);
        updateErrorMessage();
    }

	protected void openSelectLogset() {

		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(), new NavigatorLabelProvider(),
				new BaseWorkbenchContentProvider());

		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer,
					final Object parentElement, final Object element) {
				if (CaseFileManager.isCaseFile(element)) {
					return true;
				}
				if (element instanceof IAdaptable) {
					return null != ((IAdaptable) element)
							.getAdapter(ILogSessionWrapper.class);
				}
				return false;
			}
		});

		dialog.setValidator(new ISelectionStatusValidator() {

			public IStatus validate(final Object[] selection) {
				final Status error = new Status(IStatus.ERROR,
						SeUiPlugin.PLUGIN_ID, 0, "Failed to select logset",
						new IllegalStateException(
								"Exactly one logset must be selected"));
				if (selection.length != 1) {
					return error;
				}
				final Object element = selection[0];
				if (element instanceof IAdaptable) {
					if (null != toLogset(element)) {
						return Status.OK_STATUS;
					}
				}
				return error;
			}
		});

		if (logset != null) {
			dialog.setInitialSelection(logset);
		}

		if (dialog.open() == Window.OK) {
			setLogset(toLogset(dialog.getFirstResult()));
			updateErrorMessage();
		}

	}

	private ILogSessionWrapper toLogset(final Object element) {
		if (element instanceof IAdaptable) {
			final IAdaptable adapt = (IAdaptable) element;
			return (ILogSessionWrapper) adapt
					.getAdapter(ILogSessionWrapper.class);

		}
		return null;
	}

	private void makeContent(final Composite inputContainer) {
		searchInput = new SearchFilterInput(SearchFilterInput.SEARCHABLE_TYPES);
		if (DebuggerSearch.adapter != null) {
			searchInput.setInitialSearchAdapter(DebuggerSearch.adapter);
		}
		searchInput.setAdapter(DebuggerSearch.adapter);
		final Composite contents = searchInput.createContents(inputContainer);

		searchInput
				.addModifyListener(new SizeHandler(inputContainer, contents));
		if (logset == null) {
			final IEditorPart editor = SWTUtil.getActiveEditor();
			if (editor instanceof LogsetEditor) {
				final LogsetEditor browser = (LogsetEditor) editor;
				setLogset(browser.getInput().getLog());
			}
		}
		final ISelection selection = searchContainer.getSelection();
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection sel = (IStructuredSelection) selection;
			final Object first = sel.getFirstElement();
			final ILogSessionWrapper selectedLogset = toLogset(first);
			if (selectedLogset != null) {
				setLogset(selectedLogset);
			}
		}

	}

	private void setLogset(final ILogSessionWrapper selectedLogset) {
		logset = Logset.valueOf(selectedLogset.getId());
		text.setText(selectedLogset.getLabel(null));
	}

	private final class SizeHandler implements IModifyListener {
		private final Composite inputContainer;

		private final Composite contents;

		private SizeHandler(final Composite inputContainer,
				final Composite contents) {
			this.inputContainer = inputContainer;
			this.contents = contents;
		}

		public void componentModified(final ModifyEvent event) {
			contents.layout(true);
			inputContainer.layout(true);
			container.getShell().layout(true);
            scrolledComposite.getShell().layout(true);

			setErrorMessage(event.getErrorMessage());
			updateErrorMessage();
		}
	}

	static class MyComposite extends Composite {

		public MyComposite(final Composite parent, final int style) {
			super(parent, style);
		}

		@Override
		public void setLayoutData(final Object layoutData) {
			if (getLayoutData() == null) {
				super.setLayoutData(layoutData);
			}
		}

	}
}
