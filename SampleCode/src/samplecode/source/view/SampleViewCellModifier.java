package samplecode.source.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

public class SampleViewCellModifier implements ICellModifier {

	@Override
	public boolean canModify(Object element, String property) {
		return true;
	}

	@Override
	public Object getValue(Object element, String property) {
		if (element instanceof IResource) {
			return ((IResource)element).getName();
		}
		return null;
	}

	@Override
	public void modify(Object element, String property, Object value) {

		if (((TreeItem) element).getData() instanceof IFile
				&& ((String) value).trim() != "") {
			IProject project = ((IResource) ((TreeItem) element).getData())
					.getProject();

			String filePath = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toOSString()
					+ ((IResource) ((TreeItem) element).getData())
							.getFullPath();
			File javaFile = new File(filePath);

			IFile file = project.getFile(value.toString());
			ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();
			System.out.println(value);
			if (!file.exists()) {
				try {
					file.create(new FileInputStream(javaFile), true,
							new NullProgressMonitor());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}

			try {
				((IResource) ((TreeItem) element).getData()).delete(true,
						new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}
	}

}
