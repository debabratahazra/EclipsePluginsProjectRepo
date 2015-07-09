package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.odcgroup.mdf.editor.ui.editors.providers.MdfContentProvider;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 *
 * @author phanikumark
 *
 */
public class MdfPropertySelectionDialog extends SelectionDialog {
	
	
	private ModelElementMultiTreeSelectionUI elementUI;
	private Object input = null;
	private boolean multipleSelection = false;
	private MdfContentProvider contentProvider = null;
	private MdfTreeLabelProvider labelProvider = null;
	private List<MdfProperty> selectedProperties = Collections.EMPTY_LIST;
	
	private String newFieldName = null;

	public static final int ADD = 3;
   
	/**
	 * @param parent
	 * @param elementRenderer
	 * @param tableLableProvider
	 * @param decorator
	 * @param multi
	 */
	public MdfPropertySelectionDialog(Shell parent, boolean multipleSelection) {
		super(parent);
		setTitle("Field Selection Dialog");
		setMessage("Choose a Field:");
		this.multipleSelection = multipleSelection;
	}
	
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gridData);
		createMessageArea(composite);	
		elementUI = new ModelElementMultiTreeSelectionUI(multipleSelection) {
			@Override
			protected IStructuredSelection getUpdatedSelection(IStructuredSelection selection) {
				return super.getUpdatedSelection(selection);
			}
			
		};
		if (contentProvider != null) {
			elementUI.setContentProvider(contentProvider);
		}
		if (labelProvider != null) {
			elementUI.setLabelProvider(labelProvider);
		}
		// create UI
		elementUI.createUI(composite);
		if (input != null) {
			elementUI.setInput(input);
		}
		if (selectedProperties != null && !selectedProperties.isEmpty()) {
			elementUI.setSelectedFields(selectedProperties);
		}	
		return composite;
	}
	
	
    private IStructuredSelection getSelection(){
    	return elementUI.getSelection();
    }
	
	protected Object[] getSelectedItem() {
		IStructuredSelection selection = getSelection();
		return selection.toArray();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void okPressed() {
		IStructuredSelection selection = elementUI.getSelection();
		if (selection.size() == 1) {
			Object input = getSelection().getFirstElement();
			if (input instanceof MdfProperty) {
				Object[] arr = {input};
				setResult(Arrays.asList(arr));
			}
		} else if (selection.size() > 1) {
			Iterator iter = selection.iterator();
			List<Object> fields = new ArrayList<Object>();
			while (iter.hasNext()) {
				Object input = iter.next();
				if (input instanceof MdfProperty) {
					fields.add(input);
				}
			}
			setResult(fields);
		}
		setReturnCode(OK);
		close();
	}
	
	public List<MdfProperty> getSelectedProperties() {
		return getSelection().toList();
	}

	public void setInput(Object input){
		this.input = input;
	}
	
	protected Point getInitialSize() {
		if (multipleSelection) {
			return new Point(700, 655);
		} else {
			return new Point(538, 625);			
		}
	}
	
	public void setContentProvider(MdfContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	public void setLabelProvider(MdfTreeLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}
	
	public void setSelectedFields(List<MdfProperty> fields) {
		this.selectedProperties = fields;
	}
	
	public String getNewFieldName() {
		return newFieldName;
	}

}
