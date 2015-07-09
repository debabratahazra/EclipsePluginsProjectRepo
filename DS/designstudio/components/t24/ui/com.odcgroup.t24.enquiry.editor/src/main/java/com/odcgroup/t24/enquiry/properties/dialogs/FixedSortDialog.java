package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.SortOrder;

/**
 * @author satishnangi
 * 
 */
public class FixedSortDialog extends Dialog {
	private Combo sortOderCombo = null;
	private Text fieldText = null;
    private Enquiry enquiry = null;
    private FixedSort sort  =null;
	/**
	 * @param parentShell
	 */
	public FixedSortDialog(Shell shell , Enquiry enquiry) {
		super(shell);
		this.enquiry = enquiry;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		container.setLayout(gridLayout);
		GridData containerData = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerData);
		Label fieldLable = new Label(container, SWT.NONE);
		fieldLable.setText("Field:");
		
		fieldText = new Text(container, SWT.BORDER);
		fieldText.setEditable(false);
		GridData fieldTextData = new GridData(GridData.GRAB_HORIZONTAL);
		fieldTextData.widthHint =400;
		fieldText.setLayoutData(fieldTextData);
		Button fieldBrowseButton = new Button(container, SWT.NONE);
		fieldBrowseButton.setText("...");
		fieldBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object value = EnquiryFieldSelectionDialog.openDialog(container.getShell(), enquiry, null, false);
				if(value !=null && value instanceof String){
					fieldText.setText((String) value);		
				}
			}
		});
		Label directionLable = new Label(container, SWT.NONE);
		directionLable.setText("Sorting direction:");
		sortOderCombo = new Combo(container, SWT.NONE);
        GridData sortOrderComboData = new GridData(GridData.GRAB_HORIZONTAL);
        sortOrderComboData.horizontalSpan =2;
        sortOrderComboData.widthHint = 305;
		sortOderCombo.setLayoutData(sortOrderComboData);
		for (SortOrder order : SortOrder.VALUES) {
			sortOderCombo.add(order.getName());
		}
		sortOderCombo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				enableOkButton();
			}
		}); 
		fieldText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				enableOkButton();
			}
		});
		
		return container;
	}
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		getButton(0).setEnabled(false);
		return control;
	}
	@Override
	protected void okPressed() {
		sort = EnquiryFactory.eINSTANCE.createFixedSort();
		sort.setField(fieldText.getText());
		sort.setOrder(SortOrder.valueOf(sortOderCombo.getText().toUpperCase().trim()));
		super.okPressed();
	}

	public FixedSort openDilaog() {
		if (super.open() == Dialog.OK) {
			return sort;
		}
		return null;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 200);
	}
	
	private void enableOkButton() {
		if ((fieldText.getText() != null && !fieldText.getText().isEmpty())
				&& (sortOderCombo.getText() != null && !sortOderCombo.getText().isEmpty())) {

			getButton(0).setEnabled(true);

		} else {
			getButton(0).setEnabled(false);
		}
	}
}
