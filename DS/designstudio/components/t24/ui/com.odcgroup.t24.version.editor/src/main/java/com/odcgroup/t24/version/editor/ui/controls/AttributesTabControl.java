package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;

public class AttributesTabControl extends AbstractVersionTabControl {

	private List fromList;
	private List toList;
	private Button btnRemove;
	private ISWTObservableList listWidget;
	

	/**
	 * @param parent
	 * @param editor
	 * @param editedVersion
	 */
	public AttributesTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context) {
    	super(parent, editor, context);
	}

	@Override
	protected void createTabControls(Composite body) {
		
		Composite listComposite = new Composite(body, SWT.NONE);
		listComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		listComposite.setLayout(new GridLayout(3, false));
		toolkit.adapt(listComposite);
		toolkit.paintBordersFor(listComposite);

		Label lblSelectFromList = new Label(listComposite, SWT.NONE);
		lblSelectFromList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		toolkit.adapt(lblSelectFromList, true, true);
		lblSelectFromList.setText("From List:");

		Label lblAddedList = new Label(listComposite, SWT.NONE);
		toolkit.adapt(lblAddedList, true, true);
		lblAddedList.setText("To List:");

		fromList = new List(listComposite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		fromList.setLayoutData(gd);
		//Set the Screen Attributes.
		fromList.setItems(T24ScreenAttributes.getValues());
		toolkit.adapt(fromList, true, true);
		fromList.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_VersionAttr"));

		Composite btnComposite = new Composite(listComposite, SWT.NONE);
		RowLayout btnLayout = new RowLayout(SWT.VERTICAL);
		btnLayout.wrap = false;
		btnLayout.fill = true;
		btnLayout.justify = false;
		btnLayout.pack = true;
		btnComposite.setLayout(btnLayout);
		btnComposite.setLayoutData(new GridData(/*GridData.FILL_HORIZONTAL |*/ GridData.VERTICAL_ALIGN_BEGINNING));

		ListViewer listViewer_4 = new ListViewer(listComposite, SWT.BORDER | SWT.V_SCROLL);
		toList = listViewer_4.getList();
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		toList.setLayoutData(gd2);
		toList.setItems(new String[] {});
		
		final Button btnAdd = new Button(btnComposite, SWT.NONE);
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add>>");
		
		btnRemove = new Button(btnComposite, SWT.NONE);
		btnRemove.setText("Remove");
		btnRemove.setEnabled(false);
		toolkit.adapt(btnRemove, true, true);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] selectionIndices = toList.getSelectionIndices();
				for (int i : selectionIndices) {
					listWidget.remove(i);	
				}
				btnRemove.setEnabled(false);
			}
		});
		
		fromList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = fromList.getSelectionIndex();
				String item = fromList.getItem(selectionIndex);
				String[] items = toList.getItems();
				if(items.length == 0){
					btnAdd.setEnabled(true);
				}
				for (String string : items) {
					//item = item.replaceAll("_", ".");
					if(item.equals(string)){
						btnAdd.setEnabled(false);
						break;
					}else{
						btnAdd.setEnabled(true);
					}
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRemove.setEnabled(false);
				boolean present = false;
				int focusIndex = fromList.getFocusIndex();
				String item = fromList.getItem(focusIndex);
				//String ele = item.toString().replaceAll("_", ".");
				if (item != null && focusIndex != -1) {
					String[] items = toList.getItems();
					for (String string : items) {
						if(string.equals(item)){
							present = true;
						}
					}
					if(present  == false){
						listWidget.add(item);
					}
					btnAdd.setEnabled(false);
				}
			}
		});
		
		toList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRemove.setEnabled(true);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	@Override
	protected void bindData() {
		EditingDomain edomain = getEditingDomain();	
		Version version = getEditedVersion();
		//atttributs toList
		listWidget = WidgetProperties.items().observe(toList);
		IObservableList listValue = EMFEditObservables.observeList(edomain, version, Literals.VERSION__ATTRIBUTES);
		getBindingContext().bindList(listWidget, listValue, null , null);
	}
}
