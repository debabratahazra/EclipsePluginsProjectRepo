package com.odcgroup.integrationfwk.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.integrationfwk.ui.natures.TWSConsumerNature;

/**
 * Listener class to listen the TWSConsumer plug-in re-factoring.
 * <p>
 * Please note that any of the resource related events/changes should be handled
 * via this class only. Since this has been registered with Activator of
 * consumer plug-in, any of the other classes should not implement
 * IResourceChangeListener.
 * 
 * @author sbharathraja
 * 
 */
public class ConsumerPluginRefactoringListener implements
		IResourceChangeListener, IResourceDeltaVisitor {

	/** TWS Consumer Project */
	private IProject consumerProject;
	/** instance of {@link ConsumerPluginRefactoringHelper} */
	private final ConsumerPluginRefactoringHelper refactoringHelper;

	public ConsumerPluginRefactoringListener() {
		refactoringHelper = new ConsumerPluginRefactoringHelper();
	}

	/**
	 * Checking whether the given project is TWSConsumer Project or not.
	 * 
	 * @param consumerProject
	 *            - instance of {@link IProject}
	 * @return true if the given project is TWSConsumer project, false
	 *         otherwise.
	 */
	// TODO to be moved to project related util class
	private boolean isTWSConsumerProject(IProject consumerProject) {
		if (consumerProject == null) {
			return false;
		}
		try {
			if (consumerProject.hasNature(TWSConsumerNature.NATURE_ID)) {
				return true;
			}
		} catch (CoreException e) {
			TWSConsumerLog.logError(e);
		}
		return false;
	}

	/**
	 * @see IResourceChangeListener#resourceChanged(IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getResource() != null) {
			consumerProject = event.getResource().getProject();
		}
		if (event.getResource() != null) {
			if (!isTWSConsumerProject(consumerProject)) {
				return;
			}
		}

		try {
			switch (event.getType()) {
			case IResourceChangeEvent.PRE_CLOSE:
				if (event.getDelta() != null) {
					event.getDelta().accept(this);
				}
				break;
			case IResourceChangeEvent.PRE_DELETE:
				if (event.getDelta() != null) {
					event.getDelta().accept(this);
				}
			case IResourceChangeEvent.POST_CHANGE:
				if (event.getDelta() != null) {
					event.getDelta().accept(this);
				}
			}
		} catch (CoreException e) {
			TWSConsumerLog.logError(e);
		}
	}

	/**
	 * @see IResourceDeltaVisitor#visit(IResourceDelta)
	 */
	public boolean visit(IResourceDelta delta) throws CoreException {
		final IResource resource = delta.getResource();
		final int resourcetype = resource.getType();
		if (resourcetype == IResource.ROOT) {
			// delta is at the workspace root so keep going
			return true;
		} else if (resourcetype == IResource.PROJECT) {
			if (consumerProject == null) {
				return true;
			}
			return refactoringHelper
					.processProjectDelta(consumerProject, delta);
		} else if (resourcetype == IResource.FOLDER) {
			// we are not interesting in folder operations now.
			return true;
		} else if (resourcetype == IResource.FILE) {
			return refactoringHelper.processProjectDelta(resource, delta);
		}
		return resourcetype == IResource.ROOT;
	}

}
