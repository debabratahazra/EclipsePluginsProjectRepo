package com.zealcore.se.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.internal.LogViewInput;

public abstract class AbstractOpenBrowserAction extends AbstractObjectDelegate {

	private static final IArtifact NO_DATA = new AbstractArtifact() {
	};

	@Override
	public final void runSafe(final IAction action) {
		if (this.guardFail()) {
			return;
		}
		final StructuredSelection struct = (StructuredSelection) this
				.getSelection();
		final Object o = struct.getFirstElement();
		if (o instanceof ITimeCluster) {
			final ITimeCluster cluster = (ITimeCluster) o;
			ILogSessionWrapper logset = cluster.getParent();
			String browserId = this.getBrowserId();
			final IArtifactID data = this.getData();

			openBrowser(logset, browserId, data);
		}
	}

	protected void openBrowser(final ILogSessionWrapper logset,
			final String browserId, final IArtifactID data) {
		try {
			final IWorkbenchPage page = AbstractOpenBrowserAction
					.getActivePage();
			final ILogViewInput input = LogViewInput.valueOf(logset, browserId);

			if (data == AbstractOpenBrowserAction.NO_DATA) {
				input.setData(null);
				page.openEditor(input, LogsetEditor.EDITOR_ID);
			} else if (data != null) {
				input.setData(data);
				page.openEditor(input, LogsetEditor.EDITOR_ID);
			}

		} catch (RuntimeException e) {
			handleException(e);
		} catch (PartInitException e) {
			handleException(e);
		}
	}

	protected IArtifactID getData() {
		return AbstractOpenBrowserAction.NO_DATA;
	}

	protected abstract String getBrowserId();

	private static IWorkbenchPage getActivePage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		if (this.guardFail()) {
			return;
		}
		final IStructuredSelection struct = (IStructuredSelection) selection;
		if (struct.getFirstElement() instanceof ITimeCluster) {
			final ITimeCluster logSession = (ITimeCluster) struct
					.getFirstElement();
			ILogSessionWrapper lWarapper = logSession.getParent();

			Logset logset = Logset.valueOf(lWarapper.getId());			
			if (logset.isLocked()) {
				action.setEnabled(false);
			} else {
				action.setEnabled(true);
			}
		}
	}
}
