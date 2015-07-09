package com.odcgroup.t24.version.editor.ui.dialogs;

import java.util.Collection;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenSelectionDialog extends FilteredItemsSelectionDialog {

	private IProject project = null;
	private Object[] screens = null;
	private final Logger logger = LoggerFactory.getLogger(ScreenSelectionDialog.class);

	public ScreenSelectionDialog(Shell shell, boolean multi,
			IProject project) {
		super(shell, multi);
		setTitle("Select Screens...");
		setMessage("Enter name prefix or pattern (? = any character)");
		this.project = project;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		try {
			screens = listFilesAsArray((IResource) project, true);
		} catch (CoreException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		super.setDetailsLabelProvider(new ScreensStatusLabelProvider());
		super.setListLabelProvider(new ScreensListLabelProvider());
		return super.createDialogArea(parent);
	}
	
	public IResource[] listFilesAsArray(IResource directory,
			boolean recurse) throws CoreException {
		Collection<IResource> files = listFiles(directory, recurse);
		IResource[] arr = new IResource[files.size()];
		return files.toArray(arr);
	}

	public static Collection<IResource> listFiles(IResource directory,
			boolean recurse) throws CoreException {
		String ext = "version";
		Vector<IResource> files = new Vector<IResource>();
		IResource[] resources = null;
		if(directory instanceof IProject){
			resources = ((IProject) directory).members();
		}
		 if(directory instanceof IFolder) {
			 resources = ((IFolder) directory).members();
		 }
		for (IResource entry : resources) {
			if(entry instanceof IFile) {
				IFile file = (IFile)entry;
				if(file.getFileExtension().equals(ext)) {
					files.add(file);
				}
			}
			if (recurse && entry instanceof IFolder) {
				files.addAll(listFiles(entry, recurse));
			}
		}
		return files;
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
		return new ScreensFilter();
	}

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
		if (screens != null) {
			progressMonitor.beginTask("Searching", screens.length); //$NON-NLS-1$
			for (Object rule : screens) {
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
	protected class ScreensFilter extends ItemsFilter {

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object object) {
			String item = object.toString();
				return (matches(item.substring(item.lastIndexOf("/") + 1, item.lastIndexOf("."))))						
						&& item.endsWith(".version");
		}

		@Override
		public boolean equalsFilter(ItemsFilter filter) {
			return false;
		}
	}
	
	protected class ScreensStatusLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if(element instanceof IResource) {
				return ((IResource) element).getFullPath().toOSString();
			}
			return "";
		}
	}
	
	protected class ScreensListLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if(element instanceof IResource) {
				String fileName = ((IResource) element).getName();
				return fileName.substring(0,  fileName.lastIndexOf("."));
			}
			return "";			
		}
	}
	
}
