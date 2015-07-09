package com.odcgroup.t24.version.editor.ui.controls;

import java.util.Arrays;
import java.util.TreeMap;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.databinding.converters.RoutineToStringConverter;
import com.odcgroup.t24.version.editor.databinding.converters.StringToRoutineConverter;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.RoutineSelectionDialog;
import com.odcgroup.t24.version.editor.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.JBCRoutine;
import com.odcgroup.t24.version.versionDSL.JavaRoutine;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

public class APITabControl extends AbstractVersionTabControl {

	private Button button;
	private Button btnUp;
	private Button btnDown;
	private Button btnRemove;
	private EditingDomain edomain = null;
	private UpdateListStrategy modelToTargetStrategy;
	private UpdateListStrategy targetToModelStrategy;
	private TreeMap<String, ISWTObservableList> featureWidgetMap;

	/**
	 * @param parent
	 */
	public APITabControl(Composite parent, VersionDesignerEditor editor,
			DataBindingContext context) {
		super(parent, editor, context);
	}

	@Override
	protected void createTabControls(Composite body) {

		final Composite rootComp = new Composite(body, SWT.NONE);
		rootComp.setLayout(new GridLayout(2, true));
		rootComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		toolkit.adapt(rootComp);
		toolkit.paintBordersFor(rootComp);

		Composite scrCtrlComp = new Composite(rootComp, SWT.NONE);
		scrCtrlComp.setLayout(new RowLayout(SWT.HORIZONTAL));
		toolkit.adapt(scrCtrlComp);
		toolkit.paintBordersFor(scrCtrlComp);

		button = new Button(scrCtrlComp, SWT.CHECK);
		button.setSelection(true);
		toolkit.adapt(button, true, true);

		Label scrCtrlLbl = new Label(scrCtrlComp, SWT.NONE);
		scrCtrlLbl.setText("Include Screen Control");
		toolkit.adapt(scrCtrlLbl, true, true);
		scrCtrlLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_IncludeVersionCtrl"));
		new Label(rootComp, SWT.NONE);
		
		featureWidgetMap = new TreeMap<String, ISWTObservableList>();

		Composite rtnLeftComp = new Composite(rootComp, SWT.NONE);
		rtnLeftComp.setLayout(new GridLayout(1, true));
		rtnLeftComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		toolkit.adapt(rtnLeftComp);
		toolkit.paintBordersFor(rtnLeftComp);
		createRoutinesSections(rtnLeftComp, "Input Routines", Literals.VERSION__INPUT_ROUTINES, "_HelpText_InputRtn");
		createRoutinesSections(rtnLeftComp, "Authorization Routine (After Commit)", Literals.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT, "_HelpText_AuthRtnAfterCommit");
		createRoutinesSections(rtnLeftComp, "Input Routine (After Commit)", Literals.VERSION__INPUT_ROUTINES_AFTER_COMMIT, "_HelpText_InputRtnAfterCommit");
		createRoutinesSections(rtnLeftComp, "Web Validation", Literals.VERSION__WEB_VALIDATION_ROUTINES, "_HelpText_WebValidationRtn");
		
		Composite rtnRightComp = new Composite(rootComp, SWT.NONE);
		rtnRightComp.setLayout(new GridLayout(1, false));
		rtnRightComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		toolkit.adapt(rtnRightComp);
		toolkit.paintBordersFor(rtnRightComp);
		createRoutinesSections(rtnRightComp, "Authorization Routines", Literals.VERSION__AUTHORIZATION_ROUTINES, "_HelpText_AuthRtn");
		createRoutinesSections(rtnRightComp, "ID Validation", Literals.VERSION__KEY_VALIDATION_ROUTINES, "_HelpText_IDValidationRtn");
		createRoutinesSections(rtnRightComp,	"Pre Process API", Literals.VERSION__PRE_PROCESS_VALIDATION_ROUTINES, "_HelpText_PreProcessRtn");

	}

	/**
	 * @param helpText 
	 * @param gridData
	 * @param composite_17
	 */
	public void createRoutinesSections(Composite composite, String name, final EStructuralFeature feature, String helpText) {
		
		final Section section = toolkit.createSection(composite, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText(name);	
		section.setToolTipText(VersionEditorActivator.getDefault().getString(helpText));

		Composite comp = toolkit.createComposite(section, SWT.NONE);
		comp.setData(FormToolkit.TEXT_BORDER);
		comp.setLayout(new GridLayout(2, false));
		toolkit.paintBordersFor(comp);
		section.setClient(comp);		

		final List list = new List(comp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
				Object featureList = getEditedVersion().eGet(feature);
				Routine routine = null;
				if(featureList instanceof java.util.List<?>) {
					@SuppressWarnings("unchecked")
					java.util.List<Routine> routineList = (java.util.List<Routine>)featureList;
					routine = routineList.get(list.getSelectionIndex());
				}

				IFile file = null;
				if (routine instanceof JBCRoutine) {
					file = VersionUtils.getFile(routine.getName() + ".b");
				} else {
					file = VersionUtils.getFile(routine.getName() + ".java");
				}
				VersionUtils.openEditor(file,routine.getName(),1);
			}
		});

		Composite btnComp = new Composite(comp, SWT.NONE);
		RowLayout btnLayout = new RowLayout(SWT.VERTICAL);
		btnLayout.fill = true;
		btnLayout.justify = false;
		btnLayout.center = false;
		btnLayout.wrap = false;
		btnComp.setLayout(btnLayout);
		toolkit.adapt(btnComp);
		toolkit.paintBordersFor(btnComp);
		
		createButtonControls(section, btnComp, list, feature);
		
		bindLists(getEditedVersion(), list, feature, getEditingDomain());
	}
	
	/**
	 * @param sctnInputRoutines
	 * @param composite1
	 * @param composite_16
	 * @param feature
	 */
	private void createButtonControls(Section sctnInputRoutines,
			Composite composite_16, final List list,
			final EStructuralFeature feature) {

		// Uncommenting this enable the user to create New Routines, creating of
		// new Resource(IFile - java/b) is not handled
		/*
		 * Button btnNew = new Button(composite_16, SWT.NONE);
		 * btnNew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
		 * 1, 1)); toolkit.adapt(btnNew, true, true); btnNew.setText("New");
		 * btnNew.addSelectionListener(new SelectionAdapter() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent e) {
		 * CreateNewRoutinesDialog dialog = new CreateNewRoutinesDialog(new
		 * Shell()); int ret = dialog.open(); if(ret == Dialog.OK) { String
		 * result = dialog.getRoutine(); addToList(result, list, ref); } } });
		 */

		Button btnAdd = new Button(composite_16, SWT.NONE);
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add");
		final Shell shellApi = composite_16.getShell();
		btnAdd.addSelectionListener(new SelectionAdapter() {
			private Routine routine = null;

			@Override
			public void widgetSelected(SelectionEvent e) {
				RoutineSelectionDialog dialog = new RoutineSelectionDialog(shellApi, true);
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
							addToList(fileName, list, feature);
							}
						}
					
					}
					}
					else{
						String pattern = dialog.getPattern();
						if(pattern.endsWith("java")){
						    routine  = VersionDSLFactoryImpl.eINSTANCE.createJavaRoutine();
						    ((JavaRoutine)routine).setName(pattern);
						}else {
						    routine = VersionDSLFactoryImpl.eINSTANCE.createJBCRoutine();
						    ((JBCRoutine)routine).setName(pattern);
						}
						addToList(routine.getName(), list, feature);
					}
				}
			}
		});

		btnRemove = new Button(composite_16, SWT.NONE);
		toolkit.adapt(btnRemove, true, true);
		btnRemove.setText("Remove");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionIndex() != -1) {
					list.getItems();
					removeFromList(list.getSelectionIndex(), list, feature);
				}
			}
		});

		btnUp = new Button(composite_16, SWT.NONE);
		toolkit.adapt(btnUp, true, true);
		btnUp.setText("Up");
		btnUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = list.getSelectionIndex();
				int index = selectionIndex - 1;
				if (selectionIndex != -1 && index != -1) {
					ISWTObservableList listWidget = featureWidgetMap.get(feature.getName());
					listWidget.move(selectionIndex, selectionIndex-1);
					list.setSelection(selectionIndex-1);
				}
			}
		});

		btnDown = new Button(composite_16, SWT.NONE);
		toolkit.adapt(btnDown, true, true);
		btnDown.setText("Down");
		toolkit.paintBordersFor(sctnInputRoutines);

		btnDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = list.getSelectionIndex();
				if (selectionIndex != -1
						&& selectionIndex + 1 != list.getItems().length) {
					ISWTObservableList listWidget = featureWidgetMap.get(feature.getName());
					listWidget.move(selectionIndex, selectionIndex+1);
					list.setSelection(selectionIndex+1);
				}
			}
		});
		
		//model  to target strategy.
		modelToTargetStrategy =new UpdateListStrategy();
		modelToTargetStrategy.setConverter(new RoutineToStringConverter());
		//tagrget to model strategy.
		targetToModelStrategy =new UpdateListStrategy();
		targetToModelStrategy.setConverter(new StringToRoutineConverter());

	}

	private void removeFromList(final int index, final List list,
			EStructuralFeature feature) {
		ISWTObservableList listWidget = featureWidgetMap.get(feature.getName());
		listWidget.remove(index);
		updateButtons(list);
	}

	private void addToList(final String name, final List list,
			final EStructuralFeature feature) {
		ISWTObservableList listWidget = featureWidgetMap.get(feature.getName());
	    if(listWidget.contains(name)){
			MessageDialog mDialog = new MessageDialog(null,
					"Adding Duplicate Routine", null,
					"Routine already exists in the List.", MessageDialog.ERROR,
					new String[] { "OK" }, 0);
			mDialog.open();
			return;
	    }
	    listWidget.add(name);
	    updateButtons(list);
	}

	@Override
	protected void bindData() {
		edomain = getEditingDomain();
		Version version = getEditedVersion();

		// include version control
		UpdateValueStrategy booleanToScript = new UpdateValueStrategy().setConverter(new Converter(Boolean.class, String.class) {
					@Override
					public Object convert(Object value) {
						if (Boolean.parseBoolean(value.toString())) {
							return (Object) YesNo.YES;
						}
						return (Object) YesNo.NO;
					}
				});

		UpdateValueStrategy scriptToBoolean = new UpdateValueStrategy().setConverter(new Converter(String.class, Boolean.class) {

					public Object convert(Object fromObject) {
						if (YesNo.YES.equals(fromObject)) {
							return Boolean.TRUE;
						}
						return Boolean.FALSE;
					}
				});
		
		//version control
		IObservableValue includeVersionContrlWidget = WidgetProperties.selection().observe(button);
		IObservableValue includeVersionContrlValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__INCLUDE_VERSION_CONTROL);
		getBindingContext().bindValue(includeVersionContrlWidget, includeVersionContrlValue, booleanToScript, scriptToBoolean);
	}

	/**
	 * @param version
	 */
	private void bindLists(Version version, List list,
			EStructuralFeature feature, EditingDomain edomain) {
		ISWTObservableList listWidget = WidgetProperties.items().observe(list);
		IObservableList listValue = EMFEditObservables.observeList(edomain, version, feature);
		getBindingContext().bindList(listWidget, listValue, targetToModelStrategy, modelToTargetStrategy);
		
		featureWidgetMap.put(feature.getName(), listWidget);

		// this update buttons is not working, need to check
		//updateButtons(list);
	}

	public void updateButtons(List list) {
		if (list.getItems().length == 0) {
			btnDown.setEnabled(false);
			btnUp.setEnabled(false);
			btnRemove.setEnabled(false);
		} else {
			btnDown.setEnabled(true);
			btnUp.setEnabled(true);
			btnRemove.setEnabled(true);
		}
	}
}
