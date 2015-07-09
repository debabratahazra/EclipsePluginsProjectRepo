package com.odcgroup.t24.version.editor.ui.controls;

import java.util.Arrays;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.RoutineSelectionDialog;
import com.odcgroup.t24.version.editor.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.JBCRoutine;
import com.odcgroup.t24.version.versionDSL.JavaRoutine;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

public class FieldAPITabControl extends AbstractFieldTabControl {
	
	private Field field;
	private List list;
	private Button btnDown;
	private Button btnUp;
	private Button btnRemove;
	

	private final EStructuralFeature feature = Literals.FIELD__VALIDATION_ROUTINES;
	private int rselIndex = 0;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FieldAPITabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context,TreeViewer viewer) {
    	super(parent, editor, context, viewer);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.t24.version.editor.ui.controls.AbstractVersionTabControl#createTabControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createTabControls(Composite body) {

	    Composite parent = new Composite(body, SWT.NONE);
	    parent.setLayout(new GridLayout(1, true));
	    parent.setLayoutData(new GridData(GridData.FILL_BOTH));
	    toolkit.adapt(parent);
	    toolkit.paintBordersFor(parent);

	    Label lblValidationRoutines = new Label(parent, SWT.NONE);
	    lblValidationRoutines.setText("Validation Routines:");
		toolkit.adapt(lblValidationRoutines, false, false);


		Composite listComposite = new Composite(parent, SWT.NONE);
		listComposite.setLayout(new GridLayout(2, false));
		listComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
	    toolkit.adapt(listComposite);

	    list = new List(listComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 50;
		list.setLayoutData(gd);
		list.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_ValidationRtn"));
		
		list.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateButtons();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
		});
		
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				EList<Routine> routineList = field.getValidationRoutines();
				if(routineList.size() > 0) {
					Routine routine = routineList.get(list.getSelectionIndex());
					IFile file = null;
					if (routine instanceof JBCRoutine) {
						file = VersionUtils.getFile(routine.getName() + ".b");
					} else if (routine instanceof JavaRoutine) {
						file = VersionUtils.getFile(routine.getName() + ".java");
					}
					VersionUtils.openEditor(file,routine.getName(),1);
				}
			}
		});
	   
		Composite btnComposite = new Composite(listComposite, SWT.NONE);
		btnComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		btnComposite.setLayout(new GridLayout(1, true));
	    toolkit.adapt(btnComposite);
		
	    Button addRoutineButton = new Button(btnComposite, SWT.NONE);
	    addRoutineButton.setLayoutData(new GridData(GridData.FILL_BOTH));
	    addRoutineButton.setText("Add");
	    toolkit.adapt(addRoutineButton, true, true);
	    final Shell shellApi = parent.getShell();
	    addRoutineButton.addSelectionListener(new SelectionAdapter() {
	    	private Routine routine = null;

			@Override
			public void widgetSelected(SelectionEvent e) {
				RoutineSelectionDialog dialog = new RoutineSelectionDialog(
						shellApi, true);
				dialog.setInitialPattern("?");
				if (dialog.open() == Dialog.OK) {
					String name = null;
					Object[] result = dialog.getResult();
					if(result != null){
					java.util.List<Object> asList = Arrays.asList(result);
					if(asList.size() > 0){
					for (Object res : asList) {
						if (res instanceof IResource) {
							IResource resource = (IResource) res;
							name = resource.getFullPath().toString();
							String fileName = name.substring(name.lastIndexOf("/") + 1);
							addToList(fileName);
							}
						}
					
					}
					}
					else{
						String pattern = dialog.getPattern();
						if(pattern.endsWith("java")){
						    routine   = VersionDSLFactoryImpl.eINSTANCE.createJavaRoutine();
						    ((JavaRoutine)routine).setName(pattern);
						}else {
						    routine = VersionDSLFactoryImpl.eINSTANCE.createJBCRoutine();
						    ((JBCRoutine)routine).setName(pattern);
						}
						addToList(routine.getName());
					}
				}
			}
		});

	    btnRemove = new Button(btnComposite, SWT.NONE);
	    btnRemove.setLayoutData(new GridData(GridData.FILL_BOTH));
	    toolkit.adapt(btnRemove, true, true);
	    btnRemove.setText("Remove");
	    btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list.getSelectionIndex();
			    if(index > -1){
			    	Routine routine = field.getValidationRoutines().get(index);
					Command cmd = RemoveCommand.create(getEditingDomain(), field, feature, routine);
					executeCommand(cmd);
					rselIndex = index-1;
			    }
			}
	    }); 

	    btnUp = new Button(btnComposite, SWT.NONE);
	    btnUp.setLayoutData(new GridData(GridData.FILL_BOTH));
	    btnUp.setText("Up");
	    toolkit.adapt(btnUp, true, true);
	    btnUp.addSelectionListener(new SelectionAdapter() {
	    	@Override
			public void widgetSelected(SelectionEvent e) {
				int selind = list.getSelectionIndex();
				if (selind != -1 && selind != 0) {
					EList<Routine> routines = field.getValidationRoutines();
					Routine rout = routines.get(selind);
					Command cmd = MoveCommand.create(getEditingDomain(), field, feature, rout, selind-1);
					executeCommand(cmd);
					rselIndex = selind - 1;
				}
				updateButtons();
			}
	    });

	    btnDown = new Button(btnComposite, SWT.NONE);
	    btnDown.setLayoutData(new GridData(GridData.FILL_BOTH));
	    btnDown.setText("Down");
	    toolkit.adapt(btnDown, true, true);
	    btnDown.addSelectionListener(new SelectionAdapter() {
	    	@Override
			public void widgetSelected(SelectionEvent e) {
				int selind = list.getSelectionIndex();
				if (selind != -1
						&& selind + 1 != list.getItems().length) {
					EList<Routine> routines = field.getValidationRoutines();
					Routine rout = routines.get(selind);
					Command cmd = MoveCommand.create(getEditingDomain(), field, feature, rout, selind+1);
					executeCommand(cmd);
					rselIndex = selind + 1;
				}
				updateButtons();
			}
	    });

	}

	/**add Routine from the List.
	 * @param name
	 */
	private void addToList(String name) {
	    if(isRountineSet(name)){
			MessageDialog mDialog = new MessageDialog(null,
					"Adding Duplicate Routine", null,
					"Routine already exists in the List.", MessageDialog.ERROR,
					new String[] { "OK" }, 0);
			mDialog.open();
			updateButtons();
			return;
	    }
	    Routine routine = getRoutine(name);
	    executeCommand(AddCommand.create(getEditingDomain(), field, feature, routine));
		rselIndex = field.getValidationRoutines().indexOf(routine);
	    updateButtons();
	}	

	public void updateButtons() {
		if (list.getItems().length == 0) {
			btnRemove.setEnabled(false);
			btnUp.setEnabled(false);
			btnDown.setEnabled(false);
		} else {
			btnRemove.setEnabled(true);
			if (list.getSelectionIndex() == -1) {
				btnUp.setEnabled(false);
				btnDown.setEnabled(false);
				btnRemove.setEnabled(false);
			} else {
				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnRemove.setEnabled(true);
			}
		}
	}
	
	@Override
	public void setTabInput(Field input) {
		super.setTabInput(input);
		this.field = input;
		if (!getContent().isDisposed()) {
			refreshControls();
		}
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.t24.version.editor.ui.controls.AbstractVersionTabControl#refreshControls()
	 */
	public void refreshControls() {
		if (field != null) {
			field = getTabInput();
			EList<Routine> routines = field.getValidationRoutines();
			list.removeAll();
			for(Routine routine : routines) {
				if (routine instanceof JavaRoutine) {
					list.add(routine.getName()+".java");
				}
				if (routine instanceof JBCRoutine) {
					list.add(routine.getName()+".b");
				}
			}
			list.select(rselIndex);
			updateButtons();
		}
	}
	
	/**
	 * @param routine
	 * @return
	 */
	private boolean isRountineSet(String routine) {
		String[] items = list.getItems();
		for (String string : items) {
			if (routine.equals(string)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param routineName
	 * @return
	 */
	private Routine getRoutine(String routineName) {
		String name = null;
		Routine routine = null;
		if (routineName.endsWith(".b")) {
			name = routineName.substring(0,routineName.indexOf(".b"));
			routine = VersionDSLFactoryImpl.eINSTANCE.createJBCRoutine();
		}
		if (((String) routineName).endsWith(".java")) {
			name = routineName.substring(0,routineName.indexOf(".java"));
			routine = VersionDSLFactoryImpl.eINSTANCE.createJavaRoutine();
		}
		
		if (!name.isEmpty()) {
			routine.setName(name);
		}
		return routine;
	}

	@Override
	protected void bindData() {	
		refreshControls();	
	}
    
}
