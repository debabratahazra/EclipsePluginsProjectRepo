package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;
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
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;

public class FieldAttributesTabControl extends AbstractFieldTabControl {

	private ListViewer fromListViewer;
	private List fromList;
	private ListViewer toListViewer;
	private List toList;
	private Field field;
	private Button btnRemove;
	private IObservableList input;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FieldAttributesTabControl(Composite parent,
			VersionDesignerEditor editor, DataBindingContext context,TreeViewer viewer) {
		super(parent, editor, context, viewer);
	}

	@Override
	protected void createTabControls(Composite body) {

		Composite listComposite = new Composite(body, SWT.NONE);
		listComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		listComposite.setLayout(new GridLayout(3, false));
		toolkit.adapt(listComposite);

		Label lblFromList = new Label(listComposite, SWT.NONE);
		lblFromList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		toolkit.adapt(lblFromList, true, true);
		lblFromList.setText("From list:");

		Label lblToList = new Label(listComposite, SWT.NONE);
		toolkit.adapt(lblToList, true, true);
		lblToList.setText("To list:");

		fromListViewer = new ListViewer(listComposite, SWT.BORDER | SWT.V_SCROLL );
		fromList = fromListViewer.getList();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 50;
		fromList.setLayoutData(gd);
		//get the Field Attribute from the T24FieldAttribute Enumeration class.
		fromList.setItems(T24FieldAttributes.getValues());
		toolkit.adapt(fromList, true, true);
		fromList.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Attributes"));

		Composite btnComposite = new Composite(listComposite, SWT.NONE);
		RowLayout btnLayout = new RowLayout(SWT.VERTICAL);
		btnLayout.wrap = false;
		btnLayout.fill = true;
		btnLayout.justify = false;
		btnLayout.pack = true;
		btnComposite.setLayout(btnLayout);
		btnComposite.setLayoutData(new GridData(/*GridData.FILL_HORIZONTAL |*/ GridData.VERTICAL_ALIGN_BEGINNING));

		// attribute list viewer
		toListViewer = new ListViewer(listComposite, SWT.BORDER | SWT.V_SCROLL);
		toList = toListViewer.getList();
		toList.setLayoutData(gd);
		toolkit.adapt(toList, true, true);
		
		// content provider
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		toListViewer.setContentProvider(listContentProvider);
		// label provider
		toListViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((String)element).trim();
			}
		});
		if (getTabInput() != null) {
			input = EMFEditObservables.observeList(getEditor()
					.getEditingDomain(), getTabInput(),
					Literals.FIELD__ATTRIBUTES);
			toListViewer.setInput(input);
		}

		// add button
		final Button btnAdd = new Button(btnComposite, SWT.NONE);
		btnAdd.setText("Add >>");
		toolkit.adapt(btnAdd, true, true);

		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRemove.setEnabled(false);
				int selectionIndex = fromList.getSelectionIndex();
				String item = fromList.getItem(selectionIndex);
				if (!input.contains(item))
				     input.add(item);
			}
		});
		
		// remove attribute button
		btnRemove = new Button(btnComposite, SWT.NONE);
		btnRemove.setText("Remove");
		btnRemove.setEnabled(false);
		toolkit.adapt(btnRemove, true, true);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectedIndex = toList.getSelectionIndex();
				if (selectedIndex != -1) {
					input.remove(selectedIndex);
					if (toList.getItemCount() == 0)
						btnRemove.setEnabled(false);
				}
			}
		});

		
		fromList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = fromList.getSelectionIndex();
				String item = fromList.getItem(selectionIndex);
				String[] items = toList.getItems();
				for (String string : items) {
					// item = item.replaceAll("_", ".");
					if (item.equals(string)) {
						btnAdd.setEnabled(false);
						break;
					} else {
						btnAdd.setEnabled(true);
					}
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		toList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRemove.setEnabled(true);
			}
		});


	}

	@Override
	protected void bindData() {
		if (getTabInput() != null) {
			this.field = getTabInput();
			input = EMFEditObservables.observeList(Realm.getDefault(),
					getEditor().getEditingDomain(), field,
					Literals.FIELD__ATTRIBUTES);
			toListViewer.setInput(input);
		} else {
			toListViewer.setInput(null);
		}
	}

	@Override
	public void setTabInput(Field input) {
		super.setTabInput(input);
		if (!getContent().isDisposed()) {
			bindData();
		}
	}


}
