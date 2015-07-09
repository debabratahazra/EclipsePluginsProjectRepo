package com.odcgroup.t24.mdf.editor.ui.dialogs.page;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class T24MdfTranslationDialogPage extends DialogPage {
	
	private IProject project;
	
	/**
	 * The presentation model to be linked with the translation viewer
	 */
	private ITranslationModel model;
	
	/**
	 * The translation viewer
	 */
	private ITranslationViewer viewer;
	
	@Override
    public void dispose() {
		
		if (model != null) {
			model.release();
			model = null;
		}
		
		if (viewer != null) {
			viewer.dispose();
			viewer = null;
		}
		
		super.dispose();
    }

	@Override
	public void doSave(MdfModelElement element) {
		// nothing to do
	}

	@Override
	public void createControl(Composite parent) {
    	parent.setLayoutData(new GridData(GridData.FILL_BOTH));
    	
    	Composite compEditor = getWidgetFactory().createComposite(parent);
    	compEditor.setLayout(new GridLayout(1, false));
    	int style = GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING;
    	compEditor.setLayoutData(new GridData(style));
    	
        viewer = TranslationUICore.getTranslationViewer(project, compEditor);
        if (viewer != null) {
        	model.reject(new ITranslationKind[] { ITranslationKind.DOCUMENTATION } );
        	viewer.setTranslationModel(model);
        }
    	setControl(compEditor);
	}
	
    @Override
	public void setEditMode(int editMode) {
    	super.setEditMode(editMode);
    	if (editMode == MODE_READ_ONLY) {
    		// the translation viewer manages read-only mode
			getControl().setEnabled(true);
			
    	}
	}
    
	/**
	 * @param project
	 * @param model
	 */
	public T24MdfTranslationDialogPage(IProject project, ITranslationModel model) {
		super();
		this.project = project;
		this.model = model;
        setTitle("Translation");
        setDescription("Edit translation data");

	}

}
