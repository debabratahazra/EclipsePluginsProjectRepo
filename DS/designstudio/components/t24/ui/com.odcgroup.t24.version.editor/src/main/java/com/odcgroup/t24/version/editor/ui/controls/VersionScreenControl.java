package com.odcgroup.t24.version.editor.ui.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.t24.swt.util.SWTResourceManager;
import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.pattern.FieldLayoutPatternHelper;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.utils.VersionNameUtils;
import com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class VersionScreenControl extends Composite implements IVersionDataBindingControl {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private Logger logger = LoggerFactory.getLogger(VersionScreenControl.class);
	
	private VersionDesignerEditor editor;
		
	private Text scrGrp;
	
	private Combo combo;
	private Link link;
	private QualifiedName appName;
	private List<Button> patternButtons = new ArrayList<Button>();
	
	private Resource resource;
	
	private boolean errorShown = false;
	
	private VersionFieldsControl versionFieldsControl;

	/**
	 * Create the composite.
	 * @param parent
	 * @param context
	 */
	public VersionScreenControl(final Composite parent, VersionDesignerEditor editor, DataBindingContext context) {		
		super(parent, SWT.NONE);
		this.editor = editor;
		this.resource = editor.getEditedResource();
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		
		setLayout(new GridLayout(1, true));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);	
		
		createVersionControls(this);
		
		versionFieldsControl = new VersionFieldsControl(this, editor, context);
		
		initDataBindings(context);
	}	

	
	/**
	 * @return
	 */
	private Version getVersion() {
		if(resource.getContents().size() != 0)
			return (Version) resource.getContents().get(0);
		else if(errorShown == false){
			MessageDialog mDialog = new MessageDialog(
					null,
					"Screen file is empty.",
					null,
					"Cannot retrieve screen.\n\n Screen must have application name.",
					MessageDialog.ERROR, new String[] { "OK" }, 0);
			errorShown = true;
			mDialog.open();			
			return null;
		}
		return null;
	}

	/**
	 * @param versionScreenControl
	 */
	private void createVersionControls(Composite body) {
		
		Composite group1 = new Composite(body, SWT.BORDER);
		group1.setLayout(new GridLayout(2, false));
		group1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.adapt(group1);
		toolkit.paintBordersFor(group1);
		
		createBasicVersionControls(group1);	
		

	}
	
	/**
	 * basic version controls
	 * @param group
	 */
	private void createBasicVersionControls(Composite group) {
		final Version editedVersion = getVersion();
		if (editedVersion != null) {
		Composite comp = new Composite(group, SWT.NONE);
		comp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		comp.setLayoutData(gd);
		comp.setLayout(new GridLayout(2, false));
		toolkit.adapt(comp);
		toolkit.paintBordersFor(comp);

		Label label = new Label(comp, SWT.NONE);
		GridData gd_label = new GridData(GridData.FILL_BOTH);
		gd_label.heightHint = 24;
		label.setLayoutData(gd_label);
		label.setText("Application:");
		toolkit.adapt(label, true, true);
		label.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Application"));

		link = new Link(comp, SWT.NONE);
		
		QualifiedName fullyQualifiedName = VersionNameUtils.getVersionForApplication(editedVersion);		
		String application = null;
		

		
		if(fullyQualifiedName != null){
			appName = fullyQualifiedName;
			application = appName.toString();
			if(application.contains(".")){
				application = application.replace(".", ":");
			}
			link.setText("<a>"+application+"</a>");
		}
		toolkit.adapt(link, true, true);
		
		link.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MdfClass klass = editedVersion.getForApplication();
				Resource res = ((EObject) klass).eResource();
				IFile file = OfsResourceHelper.getFile(res);
					try {
						if(file != null && file.exists()) {
							IWorkbenchPage activePage = PlatformUI
									.getWorkbench()
									.getActiveWorkbenchWindow()
									.getActivePage();
							activePage
									.openEditor(new FileEditorInput(file),
											"com.odcgroup.domain.Domain");
							IEditorPart editorPart = activePage.getActiveEditor();
							if (editorPart instanceof DomainModelEditor) {
								//selecting the class which this application is using
								DomainModelEditor editor = (DomainModelEditor) editorPart;
								TreeViewer treeViewer = (TreeViewer) editor.getViewer();
								treeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
								TreeItem root = treeViewer.getTree().getItems()[0];
								TreeItem matchItem = getMatchingTreeItem(((EObject) klass), root, treeViewer);
								if (matchItem == null) {
									matchItem = getMatchingTreeItem(((EObject) klass).eContainer(), root, treeViewer);
									if (matchItem != null) {
										treeViewer.setExpandedState(matchItem.getData(), true);
										matchItem = getMatchingTreeItem(((EObject) klass), matchItem, treeViewer);
									}
								}
								if (matchItem != null){
									treeViewer.setSelection(new StructuredSelection(matchItem.getData()), true);
									treeViewer.setExpandedState(matchItem.getData(), true);
								}
							}
						} else{
							MessageDialog mDialog = new MessageDialog(
									null,
									"Missing Application for this Version",
									null,
									"The Application for the version does not exists!",
									MessageDialog.ERROR, new String[] { "OK" },
									0);
							mDialog.open();
						}
					} catch (PartInitException ex) {
						logger.error(ex.getLocalizedMessage(), ex);
					}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
		});

		Label label_2 = new Label(comp, SWT.NONE);
		GridData gd_label_2 = new GridData(GridData.FILL_BOTH);
		gd_label_2.heightHint = 22;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("Screen Name:");
		toolkit.adapt(label_2, true, true);
		label_2.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_ShortName"));

		Label lblCustodyinput = new Label(comp, SWT.NONE);
		toolkit.adapt(lblCustodyinput, true, true);
		//set the screen name.
		if(editedVersion.getShortName() != null)
		lblCustodyinput.setText(editedVersion.getShortName());
		//T24 Name Label 
		Label t24NameLabel = new Label(comp, SWT.NONE);
		GridData t24NameLabelGridData = new GridData(GridData.FILL_BOTH);
		t24NameLabelGridData.heightHint = 24;
		t24NameLabel.setLayoutData(t24NameLabelGridData);
		t24NameLabel.setText("T24 Name:");
		toolkit.adapt(t24NameLabel, true, true);
		t24NameLabel.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_T24Name"));
		//T24 Name Value Link
		Composite composite = new Composite(comp, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		final Text t24NameText = new Text(composite, SWT.NONE);
		t24NameText.setEditable(false);
		toolkit.adapt(t24NameText, true, true);
		
		Listener ctrlListener = new Listener() {
			public void handleEvent(Event event) {
				if ((event.keyCode == 'c' || event.keyCode == 'C') && (event.stateMask & SWT.CTRL) != 0) {
					((Text) event.widget).copy();
				}
			}
		};
		
		Button copyToClipBtn = new Button(composite, SWT.NONE);
		GridData gd_canvas = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_canvas.widthHint = 30;
		gd_canvas.heightHint = 30;
		
		copyToClipBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				t24NameText.selectAll();
				t24NameText.copy();
			}
		});
		
		
		Image img = VersionEditorActivator.getImage("copyToClipboardIcon.png", true);
		copyToClipBtn.setImage(img);
		copyToClipBtn.setLayoutData(gd_canvas);
		
		t24NameText.addListener(SWT.KeyUp, ctrlListener);
		copyToClipBtn.setToolTipText("Copy to Clipboard");
		
		if(editedVersion.getT24Name()!=null) {
			t24NameText.setText(editedVersion.getT24Name());
		}
		Label lblScreenGroup = new Label(comp, SWT.NONE);
		GridData gd_lblScreenGroup = new GridData(GridData.FILL_BOTH);
		gd_lblScreenGroup.heightHint = 24;
		lblScreenGroup.setLayoutData(gd_lblScreenGroup);
		lblScreenGroup.setText("Screen Group:");
		toolkit.adapt(lblScreenGroup, true, true);
		lblScreenGroup.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_ScreenGroup"));

		scrGrp = new Text(comp, SWT.BORDER);
		GridData gdScrGrp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gdScrGrp.widthHint = 35;
		scrGrp.setLayoutData(gdScrGrp);
		scrGrp.setTextLimit(3);
		scrGrp.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.doit = true;
			}
		});
		toolkit.adapt(scrGrp, true, true);

		Label label_12 = new Label(comp, SWT.NONE);
		label_12.setLayoutData(new GridData(GridData.FILL_BOTH));
		label_12.setText("Number of Approvers:");
		toolkit.adapt(label_12, true, true);
		label_12.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_NoOfAuth"));

		combo = new Combo(comp, SWT.BORDER | SWT.READ_ONLY);
		combo.setItems(new String[] {"0", "1", "2"});
		GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_combo.widthHint = 21;
		combo.setLayoutData(gd_combo);
		//combo.setSelection(1);
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);
		}
		
		FieldsLayoutPattern[] patterns = FieldsLayoutPattern.values();
		
		Composite comp = new Composite(group, SWT.NONE);
		comp.setBackgroundMode(SWT.INHERIT_DEFAULT);
		comp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		toolkit.adapt(comp);
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.heightHint = 240;
		comp.setLayoutData(gd);
		comp.setLayout(new GridLayout((int) Math.ceil((patterns.length)/2.0), false));
		if (!patternButtons.isEmpty()) {
			patternButtons.clear();
		}
		for (FieldsLayoutPattern pattern : patterns) {
			Composite composite = new Composite(comp, SWT.NONE);
			GridLayout layout = new GridLayout(1, true);
			composite.setLayout(layout);
			GridData gdata = new GridData(SWT.CENTER, SWT.FILL, false, true);
			composite.setLayoutData(gdata);
			
			String name = pattern.getName();
			String literal = pattern.getLiteral();
			
			if(pattern.equals(FieldsLayoutPattern.NONE)){
				literal = name = "Manual";
			}
			
			Label image = new Label(composite, SWT.NONE);
			image.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			image.setImage(getPatternImage(pattern.getName()));
			image.setToolTipText(literal);

			Label caption = new Label(composite, SWT.NONE);
			caption.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			caption.setText(name);
			
			Button btn = new Button(composite, SWT.RADIO);
			btn.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			btn.setToolTipText(literal);
			btn.setData(pattern);
			btn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					
					Button button = ((Button) e.widget);
					manageSelection(button);
					final FieldsLayoutPattern pattern = (FieldsLayoutPattern) button.getData();
					TransactionalEditingDomain tdomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();
					RecordingCommand cmd = new RecordingCommand(tdomain) {						
						@Override
						protected void doExecute() {
							getVersion().setFieldsLayoutPattern(pattern);
							FieldLayoutPatternHelper.applyFieldLayout(getVersion());
						}
					};
					editor.getEditingDomain().getCommandStack().execute(cmd);
					versionFieldsControl.clearFieldSelection();
				}	
				
				private void manageSelection(Button button) {
					for(Button btn : patternButtons) {
						if (btn.equals(button)) {
							button.setSelection(true);
						} else {
							btn.setSelection(false);
						}
					}
				}
			});
			patternButtons.add(btn);
		}
		

	    
		
		
	}

	@Override
	public void initDataBindings(DataBindingContext context) {	

		Version version = getVersion();	
		if(version != null){
			UpdateValueStrategy targetToModelForcombo = new UpdateValueStrategy() {
	
				@Override
				protected IStatus doSet(IObservableValue observableValue,
						Object value) {
					int val = ((Integer) value).intValue();
					IStatus doSet = super.doSet(observableValue, val);
					return doSet;
				}
			};
			
			EditingDomain edomain = editor.getEditingDomain();
			
			IObservableValue scrGrpTxtValue = WidgetProperties.text(SWT.Modify).observe(scrGrp);
			IObservableValue vGrpValue = EMFEditObservables.observeValue(edomain, version, VersionDSLPackage.Literals.VERSION__GROUP);
			context.bindValue(scrGrpTxtValue, vGrpValue, null, null);
			
			IObservableValue observeSelectioncomboObserveWidget = WidgetProperties.singleSelectionIndex().observe(combo);
			IObservableValue rnoa = EMFEditObservables.observeValue(edomain, version, VersionDSLPackage.Literals.VERSION__NUMBER_OF_AUTHORISERS);
			context.bindValue(observeSelectioncomboObserveWidget, rnoa, targetToModelForcombo, null);
			FieldsLayoutPattern vPattern = version.getFieldsLayoutPattern();
			if (vPattern != null) {
				for(Button btn: patternButtons) {
					if (vPattern.equals((FieldsLayoutPattern) btn.getData())) {
						btn.setSelection(true);
					} else {
						btn.setSelection(false);
					}
				}
			}
				

		}
	}	
	
	private Image getPatternImage(String key) {
		return VersionEditorActivator.getImage(key+".png", true, "patterns/");
	}
	
	/**
     * @param obj
     * @return
     */
    private TreeItem getMatchingTreeItem(EObject obj, TreeItem item, TreeViewer viewer) {
    	MdfModelElementImpl owner = (MdfModelElementImpl) obj;
    	TreeItem retItem = null;
    	if (areEqual(owner, (MdfModelElement) item.getData())) {
    		return item;
    	}
    	TreeItem[] rItems = item.getItems();
		for (TreeItem treeItem : rItems) {
			MdfModelElement model = (MdfModelElement) treeItem.getData();
			if(areEqual(owner, model)){
				retItem = treeItem;
			} 
		}
		return retItem;
    }
    
    /**
     * @param model1
     * @param model2
     * @return
     */
    private boolean areEqual(MdfModelElement model1, MdfModelElement model2) {
    	String qName1 = model1.getQualifiedName().getQualifiedName();
    	String qName2 = model2.getQualifiedName().getQualifiedName();
    	if(qName1.equals(qName2)){
    		return true;
    	}
    	return false;
    }
    
    public Composite getControl() {
    	return this;
    }
	
	/**
	 * 
	 */
	public void refreshControls() {
		if(versionFieldsControl!=null) {
			versionFieldsControl.refreshControls();
		}
	}

}
