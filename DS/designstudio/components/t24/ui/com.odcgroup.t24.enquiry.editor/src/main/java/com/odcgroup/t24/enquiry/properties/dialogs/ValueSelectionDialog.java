package com.odcgroup.t24.enquiry.properties.dialogs;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;


/**
 *
 * @author satishnangi
 *
 */
public class ValueSelectionDialog extends Dialog {
	protected List toList = null;
	protected  List fromList =null;
	private ArrayList<String> selectedValues;
	private Label warningLabel;
	private Button addButton;
	private Button deletedButton;

	/**
	 * @param parentShell
	 */
	protected ValueSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(3, false);
		container.setLayout(gridLayout);
		GridData containerdata = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(containerdata);
		
		warningLabel = new Label(container, SWT.NONE);
		warningLabel.setText("");
		warningLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		warningLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		// add the label and fromList
		Composite fromListCompoiste = new Composite(container, SWT.NONE);
		GridLayout fromCompoisteLayout = new GridLayout(1, true);
		GridData fromCompositeData = new GridData(GridData.FILL_BOTH);
		fromCompositeData.widthHint =70;

		fromListCompoiste.setLayout(fromCompoisteLayout);
		fromListCompoiste.setLayoutData(fromCompositeData);
		Label fromListLabel = new Label(fromListCompoiste, SWT.NONE);
		fromListLabel.setText("From List:");
		
		
		fromList = new List(fromListCompoiste, SWT.BORDER|SWT.V_SCROLL);
		fromList.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite buttonsBar = new Composite(container, SWT.NONE);
		buttonsBar.setLayout(new GridLayout(1, true));
		GridData buttonsBarData = new GridData(GridData.FILL_VERTICAL);
		buttonsBarData.widthHint =70;
		buttonsBar.setLayoutData(buttonsBarData);
		addButton = new Button(buttonsBar, SWT.NONE);
		GridData buttonData = new GridData();
		buttonData.verticalIndent = 25;
		
		addButton.setLayoutData(buttonData);
		addButton.setText("Add >>");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addValuetoList();
			}
		});
		deletedButton = new Button(buttonsBar, SWT.NONE);
		deletedButton.setText("Remove");
        deletedButton.addSelectionListener(new SelectionAdapter() {
        	@Override
			public void widgetSelected(SelectionEvent e) {
        		String[] selectedList = toList.getSelection();
        		for(String options : selectedList){
				   toList.remove(options);
        		}
			}
		});
		// selected FileVersionOption list
		Composite toListCompoiste = new Composite(container, SWT.NONE);
		toListCompoiste.setLayout(new GridLayout(1, true));
		GridData toCompositeData = new GridData(GridData.FILL_BOTH);
		toListCompoiste.setLayoutData(toCompositeData);
		Label toListLabel = new Label(toListCompoiste, SWT.NONE);
		toListLabel.setText("To List:");
		toList = new List(toListCompoiste, SWT.BORDER|SWT.V_SCROLL);
		GridData toListData = new GridData(GridData.FILL_BOTH);
		toList.setLayoutData(toListData);
      
		return container;
	}
	
	public void setToListValues(String[] toListValues){
	        toList.setItems(toListValues);
	      
	}
	
	public void setFromListInput(String[] fromListValues){
		if(fromListValues.length > 0){
			fromList.setItems(fromListValues);
		}else{
			warningLabel.setText("EB_Reports domain is not available,Please Introspect.");
			this.addButton.setEnabled(false);
			this.deletedButton.setEnabled(false);
		}
	}

	
	@Override
	protected Point getInitialSize() {
		return new Point(600, 300);
	}
	@Override
	protected void okPressed() {
		selectedValues = new ArrayList<String>();
		for (String seletedItems : toList.getItems()) {
			selectedValues.add(seletedItems);
		}
		super.okPressed();
	}

	public ArrayList<String> getSelectedValues() {
		return selectedValues;
	}
	
	/**
	 * 
	 */
	protected void addValuetoList() {
		String[] selectedFrom = fromList.getSelection();
		java.util.List<String> selectedToList = Arrays.asList(toList.getItems());
		java.util.List<String> selectionList = Arrays.asList(selectedFrom);
		for (String selectionItem : selectionList) {
			if (!selectedToList.contains(selectionItem)) {
				toList.add(selectionItem);
			}
		}
	}

}
