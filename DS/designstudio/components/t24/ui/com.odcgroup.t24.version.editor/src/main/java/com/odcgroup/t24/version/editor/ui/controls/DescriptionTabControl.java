package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class DescriptionTabControl extends AbstractVersionTabControl {
	
	private ITranslationViewer descriptionViewer;

	public DescriptionTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context) {
		super(parent, editor, context);
	}

	@Override
	protected void createTabControls(Composite body) {
		
		Group grpDescription = new Group(body, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		grpDescription.setLayoutData(gd);
		grpDescription.setText("Description");
		toolkit.adapt(grpDescription);
		toolkit.paintBordersFor(grpDescription);
		grpDescription.setLayout(new GridLayout(1, false));
		grpDescription.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_VersionDescription"));
		
		createDescriptionControl(grpDescription);	
		
	}

	@Override
	protected void bindData() {
		
		Version version = getEditedVersion();	

		// update translations
		if (version != null) {
			IProject project = OfsResourceHelper.getProject(version.eResource());
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			ITranslation translation = manager.getTranslation(version);
			if (translation != null) {
				ITranslationPreferences prefs = manager.getPreferences();
				ITranslationModel model = new TranslationModel(prefs, translation);
				descriptionViewer.setTranslationModel(model);
				model.selectKind(ITranslationKind.NAME);
			} 
		}		
	}
	
	/**
	 * @param parent
	 * @param version
	 * @param project
	 */
	private void createDescriptionControl(Composite parent) {
		Version editedVersion = getEditedVersion();
		if (editedVersion != null) {
			IProject project = OfsResourceHelper.getProject(editedVersion.eResource());
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			ITranslation translation = manager.getTranslation(editedVersion);
			if (translation != null) {
				descriptionViewer = TranslationUICore.getTranslationViewer(project, parent);
				descriptionViewer.hideKindSelector();
				descriptionViewer.hideOrigin();
				descriptionViewer.hide();
			} else {
				// display something in the UI that indicates the translation is not
				// available
			}
		}
	}

}
