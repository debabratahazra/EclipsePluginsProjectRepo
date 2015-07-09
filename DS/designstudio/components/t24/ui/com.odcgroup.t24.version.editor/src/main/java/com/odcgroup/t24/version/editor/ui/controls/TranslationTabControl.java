package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class TranslationTabControl extends AbstractFieldTabControl {
    
	/** Translation viewer */
    private ITranslationViewer translationViewer;
    
    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public TranslationTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context,TreeViewer viewer) {
    	super(parent, editor, context, viewer);
    }

    private Composite subcomp;

    /**
     * @return
     */
    public Composite getHeaderGroup() {
    	return subcomp;
    }

    @Override
    protected void createTabControls(Composite body) {	
		subcomp = new Composite(body, SWT.NONE);
		subcomp.setLayout(new GridLayout(1, true));
		subcomp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.adapt(subcomp);
		toolkit.paintBordersFor(subcomp);
		createTranslation(subcomp);
    }

    @Override
    protected void bindData() {
    	UpdateTranslation();
    }	

    /**create the Translation control.  
     * @param parent
     */
    private void createTranslation(Composite parent) {
		Version editedVersion = getEditedVersion();
		IProject project = OfsResourceHelper.getProject(editedVersion.eResource());
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		translationViewer = TranslationUICore.getTranslationViewer(project, parent);
		if (editedVersion.getFields().size() > 0 && !(editedVersion.getFields().isEmpty())) {
		    ITranslation translation = manager.getTranslation(editedVersion.getFields().get(0));
		    if (translation != null) {
		    	ITranslationPreferences prefs = manager.getPreferences();
		    	ITranslationModel model = new TranslationModel(prefs, translation);
		    	translationViewer.showOrigin();
		    	translationViewer.setTranslationModel(model);
		    }
		}else {
		    // display something in the UI that indicates the translation is not
		    // available
		}
    }

    /**
     * update the Transaltion for input changes. 
     */
    protected void UpdateTranslation() {
		if(translationViewer !=null && !translationViewer.getControl().isDisposed()){
		    ITranslationModel model = getTranslationModel();
		    if (model != null) {
		    	translationViewer.setTranslationModel(model);
		    }
		}
    }

    /**
     * @return
     */
    private ITranslationModel getTranslationModel() {
		IProject project = OfsResourceHelper.getProject(getEditedVersion().eResource());
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		if (getTabInput() != null) {
		    ITranslation translation = manager.getTranslation(getTabInput());
		    if (translation != null) {
		    	ITranslationPreferences prefs = manager.getPreferences();
		    	return new TranslationModel(prefs, translation);
		    }
		}
		return null;
    }	

    @Override
    public void setTabInput(Field input) {
		super.setTabInput(input);
		if(!getContent().isDisposed()){
		    UpdateTranslation();
		}	
    }
 }
