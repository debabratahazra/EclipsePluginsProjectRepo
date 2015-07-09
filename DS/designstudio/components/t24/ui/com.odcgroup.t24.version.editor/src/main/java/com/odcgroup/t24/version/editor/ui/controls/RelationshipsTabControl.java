package com.odcgroup.t24.version.editor.ui.controls;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.databinding.converters.StringToVersionConverter;
import com.odcgroup.t24.version.editor.databinding.converters.VersionToStringConverter;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.ScreenSelectionDialog;
import com.odcgroup.t24.version.editor.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.impl.VersionImpl;
import com.odcgroup.workbench.core.di.InjectUtil;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;

public class RelationshipsTabControl extends AbstractVersionTabControl {
	
	private ComboViewer comboViewer;
	private Text textTransaction;
	private Text textNextScreen;
	
	private Link nextScreenlink;
	
	private List list;
	private Button btnMoveDown;
	private Button btnMoveUp;
	private Button btnRemove;
	private UpdateValueStrategy modelToTargetStrategy;
	private UpdateValueStrategy targetToModelStrategy;
	private UpdateListStrategy targetToModelListStrategy;
	private UpdateListStrategy modelToTargetListStrategy;
	private Button btnClear;
	
	private int assocVerSelection = 0;
	private Label scrErrlbl;
	private ComboViewer presentPatternComboViewer;
    private InjectUtil injectUtil = null;
    private XtextProxyUtil xtextProxyUtil = null;

	/**
	 * @param parent
	 */
	public RelationshipsTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context) {
    	super(parent, editor, context);
		this.editor = editor;
	}

	@Override
	protected void createTabControls(Composite body) {
		
		Composite composite_14 = new Composite(body, SWT.NONE);
		composite_14.setLayout(new GridLayout(2, true));
		composite_14.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(composite_14);
		toolkit.paintBordersFor(composite_14);	
		
		Composite composite = new Composite(composite_14, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);

		Group group_3 = new Group(composite, SWT.NONE);
		GridData gd_group_3 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		group_3.setLayoutData(gd_group_3);
		group_3.setText("Target");
		toolkit.adapt(group_3);
		toolkit.paintBordersFor(group_3);
		group_3.setLayout(new GridLayout(4, false));

		
		nextScreenlink = new Link(group_3, SWT.NONE);
		nextScreenlink.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		nextScreenlink.setText("<a>Next Screen:</a>");
		toolkit.adapt(nextScreenlink, true, true);
		nextScreenlink.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_NextVersion"));
		
		nextScreenlink.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!(textNextScreen.getText() == null || textNextScreen.getText().isEmpty())) {
					Version version = getEditedVersion().getNextVersion();
					Resource res = ((EObject) version).eResource();
					IFile file = OfsResourceHelper.getFile(res);
					VersionUtils.openEditor(file,textNextScreen.getText(),0);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		
		textNextScreen = new Text(group_3, SWT.BORDER);
		textNextScreen.setEditable(false);
		textNextScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		textNextScreen.setToolTipText("Text is read-only");		
		toolkit.adapt(textNextScreen, true, true);
		
		Button nxtScrBtn = new Button(group_3, SWT.NONE);
		nxtScrBtn.addSelectionListener(new SelectionAdapter() {
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
							Resource resourc = getEditingDomain().getResourceSet().getResource(resourceURI, true);
							VersionImpl version = null;
							if(resourc.getContents().size() != 0){
								version = (VersionImpl)resourc.getContents().get(0);
								if(version != null) {
									executeCommand(getSetCommand(Literals.VERSION__NEXT_VERSION, version));
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
						enableComboAndClearBtn();
					}
				}
			}
		});
		nxtScrBtn.setText("   ...   ");
		toolkit.adapt(nxtScrBtn, true, true);
		
		btnClear = new Button(group_3, SWT.NONE);
		btnClear.setText("Clear");

		Label lblScreenFunction = new Label(group_3, SWT.NONE);
		lblScreenFunction.setText("Screen Function:");
		toolkit.adapt(lblScreenFunction, true, true);

		comboViewer = new ComboViewer(group_3,  SWT.READ_ONLY);
		Combo combo_2 = comboViewer.getCombo();
		combo_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1));
		combo_2.setText("Input");
		combo_2.setListVisible(true);
		combo_2.setItems(new String[] { "None", "Input", "Authorise",
				"Reverse", "See", "Copy", "Delete", "History Restore", "Verify", "Auditor Review" });
		toolkit.adapt(combo_2);
		toolkit.paintBordersFor(combo_2);
		new Label(group_3, SWT.NONE);
		new Label(group_3, SWT.NONE);
		
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CompoundCommand cc = new CompoundCommand();
				cc.append(getSetCommand(Literals.VERSION__NEXT_VERSION, null));
				cc.append(getSetCommand(Literals.VERSION__NEXT_VERSION_FUNCTION, null));
				executeCommand(cc);
			}
		});
		
		Group group_4 = new Group(group_3, SWT.NONE);
		GridData gdGroup = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gdGroup.horizontalSpan = 2;
		group_4.setLayoutData(gdGroup);
		group_4.setText("Transaction");
		toolkit.adapt(group_4);
		toolkit.paintBordersFor(group_4);
		group_4.setLayout(new GridLayout(2, false));
		
		textTransaction = new Text(group_4, SWT.BORDER);
		textTransaction.setText("");
		textTransaction.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		toolkit.adapt(textTransaction, true, true);
		new Label(composite, SWT.NONE);

		Group scrTabGroup = new Group(composite_14, SWT.NONE);
		GridData gd_group_5 = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		scrTabGroup.setLayoutData(gd_group_5);
		scrTabGroup.setText("Screen Tab");
		toolkit.adapt(scrTabGroup);
		toolkit.paintBordersFor(scrTabGroup);
		scrTabGroup.setLayout(new GridLayout(2, false));
		scrTabGroup.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_AssociatedVersions"));

		scrErrlbl = new Label(scrTabGroup, SWT.NONE);
		scrErrlbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,1));
		toolkit.adapt(scrErrlbl, true, true);
		
		// Added for DS-6666 : Begin
		Group presentPatternGrp = new Group(scrTabGroup, SWT.NONE);
		GridData presentPatternGdGrp = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gdGroup.horizontalSpan = 2;
		presentPatternGrp.setLayoutData(presentPatternGdGrp);
		toolkit.adapt(presentPatternGrp);
		presentPatternGrp.setLayout(new GridLayout(2, false));
		new Label(scrTabGroup, SWT.NONE);
		
		Label lblPresentationPattern = new Label(presentPatternGrp, SWT.NONE);
		lblPresentationPattern.setText("Presentation Pattern:");
		lblPresentationPattern.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_PresentationPattern"));
		toolkit.adapt(lblPresentationPattern, true, true);
		
		presentPatternComboViewer = new ComboViewer(presentPatternGrp,  SWT.READ_ONLY);
		Combo presentationPatternCombo = presentPatternComboViewer.getCombo();
		presentationPatternCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1));
		presentationPatternCombo.setItems(new String[] { "None", "Tabs", "Vertical", "Accordions" });
		toolkit.adapt(presentationPatternCombo);
		toolkit.paintBordersFor(presentationPatternCombo);
		// Added for DS-6666 : End
		
		list = new List(scrTabGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list_3 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		list.setLayoutData(gd_list_3);
		toolkit.adapt(list, true, true);
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {				
				Version version = getEditedVersion().getAssociatedVersions().get(list.getSelectionIndex());
				Resource res = ((EObject) version).eResource();
				IFile file = OfsResourceHelper.getFile(res);
				VersionUtils.openEditor(file,version.getT24Name(),0);
			}
		});

		Button addBtn = new Button(scrTabGroup, SWT.NONE);
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ScreenSelectionDialog dialog = new ScreenSelectionDialog(getShell(), false, getEditor().getProject());
				dialog.setInitialPattern("?");
				if(Dialog.OK == dialog.open()) {
					Object[] result = dialog.getResult();
					for(Object res : result) {
						if(res instanceof IResource) {
							IResource resource = (IResource)res;
							if(list.indexOf(resource.getName()) == -1){
								addToList(resource, list);
							}
									
						}
					}
				}
			}
		});
		addBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addBtn.setText("Add");
		toolkit.adapt(addBtn, true, true);

		btnRemove = new Button(scrTabGroup, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list.getSelectionIndex();
				if(index != -1){
					Version ver = getEditedVersion().getAssociatedVersions()
							.get(index);
					Command cmd = RemoveCommand.create(getEditingDomain(),
							getEditedVersion(), Literals.VERSION__ASSOCIATED_VERSIONS, ver);
					executeCommand(cmd);
					assocVerSelection = index-1;
				}
			}
		});
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemove.setText("Remove");
		toolkit.adapt(btnRemove, true, true);

		btnMoveUp = new Button(scrTabGroup, SWT.NONE);
		btnMoveUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selind = list.getSelectionIndex();
				if (selind != -1) {
					int index = selind - 1;
					if (index != -1 && index < list.getItems().length) {
						EList<Version> assocVersions = getEditedVersion().getAssociatedVersions();
						Version ver = assocVersions.get(selind);
						if(ver.eIsProxy()){
							errorDialogForMissingVersion(selind);
							return;
						}
						else if(assocVersions.get(index).eIsProxy()){
							errorDialogForMissingVersion(index);
							return;
						}
						Command cmd = MoveCommand.create(getEditingDomain(), getEditedVersion(), Literals.VERSION__ASSOCIATED_VERSIONS, ver, index);
						executeCommand(cmd);
						assocVerSelection = index;
					}
				}
			}
		});
		
		btnMoveUp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnMoveUp.setText("Up");
		toolkit.adapt(btnMoveUp, true, true);

		btnMoveDown = new Button(scrTabGroup, SWT.NONE);
		btnMoveDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selind = list.getSelectionIndex();
				if(selind != -1 && selind < list.getItems().length){
					int index = selind + 1;
					if(index != -1 && index != list.getItems().length){
						EList<Version> assocVersions = getEditedVersion().getAssociatedVersions();
						Version ver = assocVersions.get(selind);
						if(ver.eIsProxy()){
							errorDialogForMissingVersion(selind);
							return;
						}
						else if(assocVersions.get(index).eIsProxy()){
							errorDialogForMissingVersion(index);
							return;
						}
						Command cmd = MoveCommand.create(getEditingDomain(), getEditedVersion(), Literals.VERSION__ASSOCIATED_VERSIONS, ver, index);
						executeCommand(cmd);
						assocVerSelection = index;
					}
				}
			}
		});
		btnMoveDown.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false,	1, 1));
		btnMoveDown.setText("Down");
		toolkit.adapt(btnMoveDown, true, true);
	}

	/**
	 * @param res
	 * @param list2
	 */
	private void addToList(final IResource res, List list2) {
		URI uri = ModelURIConverter.createModelURI((IResource) res);
		Resource resource = getEditor().getEditingDomain().getResourceSet().getResource(uri, true);
		VersionImpl version = null;
		if(resource.getContents().size() != 0){
			version = (VersionImpl)resource.getContents().get(0);
			if (version != null) {
				executeCommand(getAddCommand(Literals.VERSION__ASSOCIATED_VERSIONS, version));
				assocVerSelection = getEditedVersion().getAssociatedVersions().indexOf(version);
			}
		} else {
			MessageDialog mDialog = new MessageDialog(
					null,
					"Screen file is empty.",
					null,
					"Cannot retrieve screen.\n\n Screen must have application name.",
					MessageDialog.ERROR, new String[] { "OK" }, 0);
			mDialog.open();
			return;
		}
	}
	
	/**
	 * @param index
	 * @param assocVersions
	 * @param ver
	 */
	public void errorDialogForMissingVersion(int index) {
			MessageDialog mDialog = new MessageDialog(
					null,
					"Screen file is missing in workspace",
					null,
					"The Version " + list.getItem(index) + " is referenced here but is missing in the workspace.\n\n Moves are allowed if the Version file is present. \n Create or import this Version file to make the moves",
					MessageDialog.ERROR,
					new String[] { "OK" }, 0);
			mDialog.open();
			return;
	}

	@SuppressWarnings("static-access")
	@Override
	protected void bindData() {
		EditingDomain edomain = getEditingDomain();	
		Version version = getEditedVersion();
		
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
		
		//model  to target strategy.
		modelToTargetStrategy = new UpdateValueStrategy();
		modelToTargetStrategy.setConverter(new VersionToStringConverter(comboViewer));
		//tagrget to model strategy.
		targetToModelStrategy = new UpdateValueStrategy();
		targetToModelStrategy.setConverter(new StringToVersionConverter(comboViewer));
		
		//model  to target Liststrategy.
		modelToTargetListStrategy = new UpdateListStrategy();
		modelToTargetListStrategy.setConverter(new VersionToStringConverter(null));
		//tagrget to model Liststrategy.
		targetToModelListStrategy = new UpdateListStrategy();
		targetToModelListStrategy.setConverter(new StringToVersionConverter(null));
		
		//screen function
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setLabelProvider(new LabelProvider());
		comboViewer.setInput(version.getNextVersionFunction().values());
		IObservableValue functionValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__NEXT_VERSION_FUNCTION);
		IObservableValue functionWidget = ViewerProperties.singleSelection().observe(comboViewer);
		getBindingContext().bindValue(functionWidget, functionValue, null, null);
		
		//next transaction ref
		IObservableValue transactionWidget = WidgetProperties.text(SWT.Modify).observe(textTransaction);
		IObservableValue transactionValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__NEXT_TRANSACTION_REFERENCE);
		getBindingContext().bindValue(transactionWidget, transactionValue, strat, strat);
		
		//presentation pattern combo viewer
		presentPatternComboViewer.setContentProvider(new ArrayContentProvider());
		presentPatternComboViewer.setLabelProvider(new LabelProvider());
		presentPatternComboViewer.setInput(version.getAssociatedVersionsPresentationPattern().values());
		IObservableValue presentationPatternValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN);
		IObservableValue presentationPatternWidget = ViewerProperties.singleSelection().observe(presentPatternComboViewer);
		getBindingContext().bindValue(presentationPatternWidget, presentationPatternValue, null, null);
		
		enableComboAndClearBtn();
		refreshControls();
		
	}

	/**
	 * enables screen function combo and clear button
	 */
	public void enableComboAndClearBtn() {
		if(list.getItems().length != 0){
			btnRemove.setEnabled(true);
		}
		
		if(textNextScreen.getText().isEmpty()){
			comboViewer.getCombo().setEnabled(false);
			btnClear.setEnabled(false);
		}else{
			comboViewer.getCombo().setEnabled(true);
			btnClear.setEnabled(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.t24.version.editor.ui.controls.AbstractVersionTabControl#refreshControls()
	 */
	public void refreshControls() {
		// refresh nextscreen textfield
		final Color resetColor = new Color(getDisplay(), 0, 0, 0);
		if(getEditedVersion().getNextVersion() != null) {
			Version version = getEditedVersion().getNextVersion();
			String proxyVersionName = StringUtils.EMPTY;
			textNextScreen.setForeground(resetColor);
			if(version.eIsProxy()){
				proxyVersionName = com.odcgroup.t24.version.utils.VersionUtils.getNextVersionName(getEditedVersion());
				final Color myColor = new Color(getDisplay(), 204, 0, 0);
				textNextScreen.setForeground(myColor);
			} 
			textNextScreen.setText(version.eIsProxy() ? proxyVersionName : getDisplayName(version));
			btnClear.setEnabled(true);
			comboViewer.getCombo().setEnabled(true);
		}
		else {
			textNextScreen.setText("");
			btnClear.setEnabled(false);
			comboViewer.getCombo().setEnabled(false);
		}
		
		StringBuffer errorMessage = new StringBuffer();
		// refresh associated versions
		if (!getEditedVersion().getAssociatedVersions().isEmpty()) {
			list.removeAll();
			EList<Version> associatedVersions = getEditedVersion().getAssociatedVersions();
			injectUtil = InjectUtil.givenURI(getEditedVersion().eResource().getURI());
			xtextProxyUtil = injectUtil.getInstance(XtextProxyUtil.class);
			int index = 0;
			for (Version associatedVersion : associatedVersions) {
				if (associatedVersion.eIsProxy()) {
					String displayName = xtextProxyUtil.getProxyCrossRefAsString(getEditedVersion(), associatedVersion);
					list.add(displayName);
					index++;
					errorMessage.append(displayName);
					if (index < associatedVersions.size()) {
						errorMessage.append("\n");
					}
				} else {
					list.add(getDisplayName(associatedVersion));
				}
			}
			if (!errorMessage.toString().isEmpty()) {
				final Color myColor = new Color(getDisplay(), 204, 0, 0);
				scrErrlbl.setForeground(myColor);
				scrErrlbl.setText("Cannot resolve: " + errorMessage.toString()); 
			} else {
				clearErrorLabel(resetColor);
			}
			
			list.select(assocVerSelection);
			btnRemove.setEnabled(true);
			btnMoveUp.setEnabled(true);
			btnMoveDown.setEnabled(true);
		} else {
			list.removeAll();
			btnRemove.setEnabled(false);
			btnMoveUp.setEnabled(false);
			btnMoveDown.setEnabled(false);
			clearErrorLabel(resetColor);
		}
	}

	/**
	 * reset to empty
	 * @param resetColor background color
	 */
	private void clearErrorLabel(final Color resetColor) {
		scrErrlbl.setText("");
		scrErrlbl.setForeground(resetColor);
	}
}
