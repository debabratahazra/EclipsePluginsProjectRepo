package com.odcgroup.mdf.editor.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * DS-1698 - contextual menu-action to copy the base-class properties to the
 * dataset
 * 
 * @author pkk
 */
public class DeriveFromBaseClassAction extends BaseSelectionListenerAction {

	private MdfDataset owner;
	private DomainModelEditor editor;

	protected EditingDomain domain;

	private static final Logger LOGGER = Logger.getLogger(DeriveFromBaseClassAction.class);

	/**
	 * @param workbenchPart
	 * @param dataset
	 */
	public DeriveFromBaseClassAction(IWorkbenchPart workbenchPart, EditingDomain domain, ISelection selection) {
		super("Copy &Base-Class");
		this.editor = (DomainModelEditor) workbenchPart;
		this.domain = domain;
		setText("Copy &Base-Class");
		setImageDescriptor(MdfPlugin.getImageDescriptor(MdfCore.ICON_DATASET));
		if (selection instanceof IStructuredSelection) {
			updateSelection((IStructuredSelection) selection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
	 * .eclipse.jface.viewers.IStructuredSelection)
	 */
	public boolean updateSelection(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof MdfDataset) {
			this.owner = (MdfDataset) selection.getFirstElement();
			if (owner != null && owner.getBaseClass() != null) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		boolean confirm = true;
		Shell shell = editor.getSite().getShell();
		if (owner.getProperties().size() > 0) {
			confirm = MessageDialog.openConfirm(shell, "Warning", "Copying base-class '"
					+ owner.getBaseClass().getName() + "' of dataset \"" + owner.getName()
					+ "\" will result in flushing of all its mapped properties. "
					+ "Are you sure you want to continue copying?");
		}
		if (confirm) {
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				public void execute(IProgressMonitor monitor) throws CoreException {
					monitor.setTaskName("Copying base-class properties ....");
					monitor.beginTask("Copying base-class properties", 1);
					domain.getCommandStack().execute(getCopyBaseClassCommands(domain, (MdfDataset) owner));
					monitor.worked(1);
				}
			};
			try {
				new ProgressMonitorDialog(editor.getEditorSite().getShell()).run(false, false, operation);
			} catch (Exception exception) {
				LOGGER.error(exception, exception);
			}
		}
	}

	/**
	 * @param domain
	 * @param dataset
	 * @return
	 */
	protected Command getCopyBaseClassCommands(EditingDomain domain, MdfDataset dataset) throws CoreException {
		if (dataset.getBaseClass() == null) {
			return UnexecutableCommand.INSTANCE;
		}		
		IProject project = getCurrentProject();
		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		List<MdfDatasetMappedProperty> mProperties = DeriveBaseClassUtil.getDatasetMappedProperties(ofsProject, dataset);
		return CopyDatasetPropertiesCommand.create(dataset, mProperties);
	}

	/**
	 * @param domainList
	 * @return
	 */
	@SuppressWarnings("unused")
	private MdfDomain getShortDatasetsDomain(Collection domainList) {
		for (Object object : domainList) {
			MdfDomain domain = (MdfDomain) object;
			if (domain.getNamespace().equals("http://www.odcgroup.com/aaa-short-datasets")) {
				return domain;
			}
		}
		return null;
	}

	/**
	 * @param datasets
	 * @return
	 */
	@SuppressWarnings("unused")
	private MdfDataset fetchShortDataset(List<MdfDataset> datasets, MdfEntity entity) {
		for (MdfDataset ds : datasets) {
			if (ds.getBaseClass() != null 
					&& ds.getBaseClass().getQualifiedName().equals(entity.getQualifiedName())) {
				return ds;
			}
		}
		return null;
	}

	/**
	 * @param domain
	 * @return
	 */
	private IProject getCurrentProject() {
		IResource resource = (IResource) this.editor.getEditorInput().getAdapter(IResource.class);
		return resource.getProject();
	}

	static class CopyDatasetPropertiesCommand extends ChangeCommand {

		private final List<MdfDatasetMappedProperty> collection;
		private MdfDataset dataset;

		/**
		 * @param notifiers
		 * @param collection
		 * @param owner
		 */
		protected CopyDatasetPropertiesCommand(Collection notifiers, List<MdfDatasetMappedProperty> collection, MdfDataset owner) {
			super(notifiers);
			this.setLabel("Copy From BaseClass Command");
			this.collection = collection;
			this.dataset = owner;
		}

		/**
		 * @param owner
		 * @param collection
		 * @return
		 */
		public static final CopyDatasetPropertiesCommand create(MdfDataset owner, List<MdfDatasetMappedProperty> collection) {
			List notifiers = new ArrayList(1);
			notifiers.add(owner);
			return new CopyDatasetPropertiesCommand(notifiers, collection, owner);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.edit.command.ChangeCommand#doExecute()
		 */
		protected void doExecute() {
			DatasetFacility.copyBaseClass(dataset, collection);
		}

	}

}
