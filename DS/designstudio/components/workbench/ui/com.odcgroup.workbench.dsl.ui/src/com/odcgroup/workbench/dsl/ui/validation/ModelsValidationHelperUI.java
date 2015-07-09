package com.odcgroup.workbench.dsl.ui.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.odcgroup.workbench.core.resources.ModelsPartition;
import com.odcgroup.workbench.dsl.validation.ModelsValidationHelper;


/**
 * Helper class that provides helper operations to validate model Resources.
 * 
 * Only XText resources are considered here.
 *
 */
public final class ModelsValidationHelperUI {
	
	/**
	 * @param selection
	 * @return
	 * @throws CoreException
	 */
	public static List<IResource> collectSelectedResources(IStructuredSelection selection) throws CoreException {
		List<Object> selectedObjects = new ArrayList<Object>();
		if (selection != null) {
			@SuppressWarnings("rawtypes")
			Iterator it = selection.iterator();
			while (it.hasNext()) {
				selectedObjects.add(it.next());
			}
		}
		return ModelsValidationHelper.collectModelResources(selectedObjects);
	}
	
	/**
	 * @param selection
	 * @param displayBlockingMessage
	 */
	public static void startValidationJob(final Collection<IResource> resources) {
		Job job = new WorkspaceJob("Models Validation") {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				if (resources != null && !resources.isEmpty()) {
					new ModelsPartitionValidatorUI(
							new ModelsPartition(resources)).validate(monitor);
				}
				return Status.OK_STATUS;
			}

		};

		job.setPriority(Job.DECORATE);
		job.setUser(true);
		job.schedule();
	}
}
