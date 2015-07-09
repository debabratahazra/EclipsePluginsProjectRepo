package com.odcgroup.t24.mdf.editor.ui.dialogs.doc;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfModelElement;
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

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class T24MdfDocPage extends DialogPage {
	private final MdfModelElement element;

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
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public T24MdfDocPage(MdfModelElement element) {
		super();
		setTitle("Documentation");
		setDescription("Edits the documentation for this element.");
		this.element = element;
		getTranslationModel();
	}
	
	private void getTranslationModel() {
	    EObject eObj = (EObject) element;
		Resource resource = eObj.eResource(); 
		if ( resource != null) {
			project = OfsResourceHelper.getProject(eObj.eResource());
			if(project!=null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(project);
				if(manager!=null) {
					ITranslation translation = manager.getTranslation(element);
					if (translation != null) {
						ITranslationPreferences prefs = manager.getPreferences();
						model = new TranslationModel(prefs, translation);
					}
				}
			}
		}
	
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
    	Composite container = getWidgetFactory().createComposite(parent);
    	container.setLayout(new GridLayout(1, false));
    	int style = GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING;
    	container.setLayoutData(new GridData(style));
		
        viewer = TranslationUICore.getTranslationViewer(project, container);
        if (viewer != null) {
			model.accept(new ITranslationKind[] { ITranslationKind.DOCUMENTATION });
			model.setMultiLinesEditor(ITranslationKind.DOCUMENTATION, true);
			model.selectKind(ITranslationKind.DOCUMENTATION);
			viewer.hideButtons();
			viewer.hideOrigin();
			viewer.hideKindSelector();	
			viewer.setTranslationModel(model);
        }
    	setControl(container);
	}
	

	@Override
	public void doSave(MdfModelElement element) {
		// nothing to do
	}
	
    @Override
	public void setEditMode(int editMode) {
    	super.setEditMode(editMode);
    	if (editMode == MODE_READ_ONLY) {
    		// the translation viewer manages read-only mode
			getControl().setEnabled(true);
			
    	}
	}
}