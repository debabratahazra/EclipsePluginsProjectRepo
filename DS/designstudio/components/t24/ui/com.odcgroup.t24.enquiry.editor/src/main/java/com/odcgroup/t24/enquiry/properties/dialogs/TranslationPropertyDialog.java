package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class TranslationPropertyDialog extends Dialog {


	/** Translation viewer */
	private ITranslationViewer viewer;

	/** the project that owns the edited model */
	private IProject project = null;

	/** The MenuItems translations, can be null if not supported */
	private ITranslation translation;

	private EObject fieldModel;
	
	private EObject operationModel;
	/**
	 * @param parentShell
	 */
	protected TranslationPropertyDialog(Shell parentShell,EObject parentModel,EObject operationModel) {
		super(parentShell);
		this.fieldModel = parentModel;
		this.operationModel = operationModel;
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		container.setLayout(gridLayout);
		GridData containerdata = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerdata);
		
		ITranslationModel transationalModel = null;
		transationalModel = getTranslationModel(operationModel);
		if (transationalModel != null) {
			if (viewer == null) {
				viewer = TranslationUICore.getTranslationViewer(project, container);
			}
			viewer.setTranslationModel(transationalModel);
		}
		return container;
	}

	/**
	 * @param item
	 * @return
	 */
	private ITranslationModel getTranslationModel(EObject item) {
		ITranslationModel model = null;
		project = OfsResourceHelper.getProject((fieldModel).eResource());
		if(project != null){
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			translation = manager.getTranslation(item);
			if (translation != null) {
				model = new TranslationModel(manager.getPreferences(), translation);
			}
		}
		return model;
	}

	/**
	 * @return
	 */
	public Object getLabel() {
		return operationModel;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Label Operation Translation Dialog");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 500);
	}

	@Override
	protected void okPressed() {
		viewer.dispose();
		viewer=null;
		super.okPressed();
	}
}
