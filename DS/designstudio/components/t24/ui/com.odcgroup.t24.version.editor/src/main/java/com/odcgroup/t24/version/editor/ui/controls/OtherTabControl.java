package com.odcgroup.t24.version.editor.ui.controls;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.ScreenSelectionDialog;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.impl.VersionImpl;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class OtherTabControl extends AbstractVersionTabControl {

	private Text challengeResptext;
	private Text textConfirmScreen;
	private Link confirmScreenlink;
	
	private Text textPreviewScreen;
	private Link previewScreenlink;
	
	private Button btnConfirmtxtClear;
	private Button btnPrevtxtClear;

	private static Logger logger = LoggerFactory.getLogger(OtherTabControl.class);
	/**
	 * @param parent
	 */
	public OtherTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context) {
    	super(parent, editor, context);
	}

	@Override
	protected void createTabControls(Composite body) {
		
		Composite rootComposite = new Composite(body, SWT.NONE);
		rootComposite.setLayout(new GridLayout(2, true));
		rootComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		toolkit.adapt(rootComposite);
		
		Composite composite = new Composite(rootComposite, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		composite.setLayoutData(layoutData);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		Group group = new Group(composite, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		group.setLayoutData(gd_group);
		group.setText("ARC IB");
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);
		group.setLayout(new GridLayout(4, false));
		
		confirmScreenlink = new Link(group, SWT.NONE);
		confirmScreenlink.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		confirmScreenlink.setText("<a>Confirm Screen:</a>");
		toolkit.adapt(confirmScreenlink, true, true);
		confirmScreenlink.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_ConfirmVersion"));
		
		confirmScreenlink.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!(textConfirmScreen.getText() == null || textConfirmScreen.getText().isEmpty())) {
					Version version = getEditedVersion().getConfirmVersion();
					Resource res = ((EObject) version).eResource();
					IFile file = OfsResourceHelper.getFile(res);
					try {
						if(file != null && file.exists())
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.openEditor(new FileEditorInput(file),
										"com.odcgroup.t24.version.VersionDSL");
					} catch (PartInitException ex) {
						logger.error(ex.getLocalizedMessage(), ex);
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		textConfirmScreen = new Text(group, SWT.BORDER);
		textConfirmScreen.setEditable(false);
		textConfirmScreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textConfirmScreen.setTextLimit(100);
		textConfirmScreen.setToolTipText("Text is read-only");
		toolkit.adapt(textConfirmScreen, true, true);		
		
		Button btnNew = new Button(group, SWT.NONE);
		toolkit.adapt(btnNew, true, true);
		btnNew.setText("   ...   ");
		
		btnConfirmtxtClear = new Button(group, SWT.NONE);
		btnConfirmtxtClear.setText("Clear");
		btnConfirmtxtClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				executeCommand(getSetCommand(Literals.VERSION__CONFIRM_VERSION, null));
			}
		});
		
		btnNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ScreenSelectionDialog dialog = new ScreenSelectionDialog(getShell(), false, getEditor().getProject());
				dialog.setInitialPattern("?");
				int ret = dialog.open();
				if(ret == Dialog.OK) {
					Object[] result = dialog.getResult();
					for(Object res : result) {
						if(res instanceof IResource) {
							IResource resource = (IResource)res;
							URI resourceURI = ModelURIConverter.createModelURI((IResource) resource);
							Resource resourc = getEditor().getEditingDomain().getResourceSet().getResource(resourceURI, true);
							VersionImpl version = null;
							if(resourc.getContents().size() != 0){
								version = (VersionImpl)resourc.getContents().get(0);
								if(version != null) {
									executeCommand(getSetCommand(Literals.VERSION__CONFIRM_VERSION, version));
								}
							}
							else{
								MessageDialog mDialog = new MessageDialog(
										null,
										"Screen file is empty.",
										null,
										"Cannot retrieve screen.\n\n Screen must have application name.",
										MessageDialog.ERROR,
										new String[] { "OK" }, 0);
								mDialog.open();
								return;
							}
						}
					}
				}
			}
		});
		
		previewScreenlink = new Link(group, SWT.NONE);
		previewScreenlink.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		previewScreenlink.setText("<a>Preview Screen:</a>");
		toolkit.adapt(previewScreenlink, true, true);
		previewScreenlink.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_PreviewVersion"));
		
		previewScreenlink.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!(textPreviewScreen.getText() == null || textPreviewScreen.getText().isEmpty())) {
					Version version = getEditedVersion().getPreviewVersion();					
					Resource res = ((EObject) version).eResource();
					IFile file = OfsResourceHelper.getFile(res);
					try {
						if(file != null && file.exists())
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.openEditor(new FileEditorInput(file),
										"com.odcgroup.t24.version.VersionDSL");
					} catch (PartInitException ex) {
						logger.error(ex.getLocalizedMessage(), ex);
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		textPreviewScreen = new Text(group, SWT.BORDER);
		textPreviewScreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		textPreviewScreen.setToolTipText("Text is read-only");
		textPreviewScreen.setBackground(com.odcgroup.t24.swt.util.SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textPreviewScreen.setEditable(false);		
		
		Button btnOpen = new Button(group, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ScreenSelectionDialog dialog = new ScreenSelectionDialog(getShell(), false, getEditor().getProject());
				dialog.setInitialPattern("?");
				int ret = dialog.open();
				if(ret == Dialog.OK) {
					Object[] result = dialog.getResult();
					for(Object res : result) {
						if(res instanceof IResource) {
							IResource resource = (IResource)res;
							URI resourceURI = ModelURIConverter.createModelURI((IResource) resource);
							Resource resourc = getEditor().getEditingDomain().getResourceSet().getResource(resourceURI, true);
							VersionImpl version = null;
							if(resourc.getContents().size() != 0){
								version = (VersionImpl)resourc.getContents().get(0);
								if(version != null) {
									executeCommand(getSetCommand(Literals.VERSION__PREVIEW_VERSION, version));
								}
							}
							else{
								MessageDialog mDialog = new MessageDialog(
										null,
										"Screen file is empty.",
										null,
										"Cannot retrieve screen.\n\n Screen must have application name.",
										MessageDialog.ERROR,
										new String[] { "OK" }, 0);
								mDialog.open();
								return;
							}
						}
					}
				}
			}
		});
		toolkit.adapt(btnOpen, true, true);
		btnOpen.setText("   ...   ");

		btnPrevtxtClear = new Button(group, SWT.NONE);
		btnPrevtxtClear.setText("Clear");
		btnPrevtxtClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				executeCommand(getSetCommand(Literals.VERSION__PREVIEW_VERSION, null));
			}
		});

		Label lblNewLabel_4 = new Label(group, SWT.NONE);
		toolkit.adapt(lblNewLabel_4, true, true);
		lblNewLabel_4.setText("Challenge Response:");
		lblNewLabel_4.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_ChallResp"));

		challengeResptext = new Text(group, SWT.BORDER);
		challengeResptext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		challengeResptext.setTextLimit(100);
		challengeResptext.setText("");
		toolkit.adapt(challengeResptext, true, true);
	}

	@Override
	public void refreshControls() {
		final Color resetColor = new Color(getDisplay(), 0, 0, 0);
		if (getEditedVersion().getConfirmVersion() != null) {
			Version version = getEditedVersion().getConfirmVersion();
			String proxyVersionName = StringUtils.EMPTY;
			textConfirmScreen.setForeground(resetColor);
			if(version.eIsProxy()){
				proxyVersionName = com.odcgroup.t24.version.utils.VersionUtils.getConfirmVersionName(getEditedVersion());
				final Color myColor = new Color(getDisplay(), 204, 0, 0);
				textConfirmScreen.setForeground(myColor);
			} 
			textConfirmScreen.setText(version.eIsProxy() ? proxyVersionName : getDisplayName(version));
			btnConfirmtxtClear.setEnabled(true);
		} else {
			textConfirmScreen.setText("");
			btnConfirmtxtClear.setEnabled(false);
		}
		if (getEditedVersion().getPreviewVersion() != null) {
			Version version = getEditedVersion().getPreviewVersion();
			String proxyVersionName = StringUtils.EMPTY;
			textPreviewScreen.setForeground(resetColor);
			if(version.eIsProxy()){
				proxyVersionName = com.odcgroup.t24.version.utils.VersionUtils.getPreviewVersionName(getEditedVersion());
				final Color myColor = new Color(getDisplay(), 204, 0, 0);
				textPreviewScreen.setForeground(myColor);
			} 
			textPreviewScreen.setText(version.eIsProxy() ? proxyVersionName : getDisplayName(version));
			btnPrevtxtClear.setEnabled(true);
		} else {
			textPreviewScreen.setText("");
			btnPrevtxtClear.setEnabled(false);
		}
	}

	@Override
	protected void bindData() {
		EditingDomain edomain = getEditingDomain();	
		final Version version = getEditedVersion();
		UpdateValueStrategy strat = new UpdateValueStrategy(){
			@Override
			protected IStatus doSet(IObservableValue observableValue,
					Object value) {
				if(value != null){
				value = ((String)value).trim();
				}
				return super.doSet(observableValue, value);
			}
		};
		//challenge response
		IObservableValue creWidget = WidgetProperties.text(SWT.Modify).observe(challengeResptext);
		IObservableValue crValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__CHALLENGE_RESPONSE);
		getBindingContext().bindValue(creWidget, crValue, strat, strat);
		
		refreshControls();
	}
	
	
}
