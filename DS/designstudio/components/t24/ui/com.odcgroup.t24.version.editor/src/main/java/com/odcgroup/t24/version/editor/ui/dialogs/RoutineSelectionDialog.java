package com.odcgroup.t24.version.editor.ui.dialogs;

import java.util.Comparator;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.version.editor.utils.VersionUtils;

public class RoutineSelectionDialog extends FilteredItemsSelectionDialog {

	private Object[] routines = null;
	private static int currentSelected = VersionUtils.JBC_ROUTINE;
	private Logger logger = LoggerFactory
			.getLogger(RoutineSelectionDialog.class);
	private String pattern;

	public RoutineSelectionDialog(Shell shell, boolean multi) {
		super(shell, multi);
		setTitle("Select Routines...");
		setMessage("Enter name prefix or pattern (? = any character)");
		setInitialPattern("?");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));

		Label label = new Label(container, SWT.NONE);
		label.setText("Type : ");

		final Button jBC = new Button(container, SWT.RADIO);
		jBC.setText("jBC (Basic)");
		jBC.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (jBC.getSelection()) {
					currentSelected = VersionUtils.JBC_ROUTINE;
					overwriteRoutines(currentSelected);
					applyFilter();
				}
			}
		});

		final Button java = new Button(container, SWT.RADIO);
		java.setText("Java");
		java.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (java.getSelection()) {
					currentSelected = VersionUtils.JAVA_ROUTINE;
					overwriteRoutines(currentSelected);
					applyFilter();
				}
			}
		});

		switch (currentSelected) {
		case VersionUtils.JBC_ROUTINE:
			jBC.setSelection(true);
			break;
		case VersionUtils.JAVA_ROUTINE:
			java.setSelection(true);
			break;
		default:
			jBC.setSelection(true);
			break;
		}

		overwriteRoutines(currentSelected);

		super.setDetailsLabelProvider(new RoutinesStatusLabelProvider());
		super.setListLabelProvider(new RoutinesListLabelProvider());
		return super.createDialogArea(parent);
	}

	private void overwriteRoutines(int type) {
		try {
			routines = null;
			Vector<IResource> resources = new Vector<IResource>();
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			for (IProject project : projects) {
				// if(project.isOpen() && project.hasNature(OfsCore.NATURE_ID))
				// {
				resources.addAll(VersionUtils.listFilesAsArray(
						(IResource) project.getProject(), type, true));
				// }
			}
			routines = resources.toArray();
		} catch (CoreException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		return new DialogSettings("");
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new RoutinesFilter();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Comparator getItemsComparator() {
		return new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo(arg1.toString());
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		if (routines != null) {
			progressMonitor.beginTask("Searching", routines.length); //$NON-NLS-1$
			for (Object rule : routines) {
				contentProvider.add(rule, itemsFilter);
				progressMonitor.worked(1);
			}
			progressMonitor.done();
		}
	}

	@Override
	public String getElementName(Object item) {
		return item.toString();
	}

	/**
	 * Filters types using pattern, scope, element kind and filter extension.
	 */
	protected class RoutinesFilter extends ItemsFilter {

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}
		
		@Override
		public String getPattern() {
			pattern = super.getPattern();
			return pattern;
		}

		@Override
		public boolean matchItem(Object object) {
			String item = object.toString();
			switch (currentSelected) {
			case VersionUtils.JBC_ROUTINE:
				return (matches(item.substring(item.lastIndexOf("/") + 1,
						item.lastIndexOf("."))))
						&& item.endsWith(".b");
			case VersionUtils.JAVA_ROUTINE:
				return (matches(item.substring(item.lastIndexOf("/") + 1,
						item.lastIndexOf("."))))
						&& item.endsWith(".java");
			}
			return false;
		}

		@Override
		public boolean equalsFilter(ItemsFilter filter) {
			return false;
		}
	}

	protected class RoutinesStatusLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof IResource) {
				return ((IResource) element).getFullPath().toOSString();
			}
			return "";
		}
	}

	protected class RoutinesListLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof IResource) {
				String fileName = ((IResource) element).getName();
				return fileName.substring(0, fileName.lastIndexOf("."));
			}
			return "";
		}
	}
	
	@Override
	protected void okPressed() {
		super.okPressed();
		setReturnCode(OK);
		close();
	}
	
	public String getPattern() {
		if(!pattern.isEmpty()){
			if (currentSelected == 1)
				return pattern + ".b";
			return pattern + ".java";
		}
		return null;
	}
	
	@Override
	protected void updateButtonsEnableState(IStatus status) {
		super.updateButtonsEnableState(status);
		if(getPattern() != null && !getPattern().isEmpty())
			getOkButton().setEnabled(true);
	}
}
