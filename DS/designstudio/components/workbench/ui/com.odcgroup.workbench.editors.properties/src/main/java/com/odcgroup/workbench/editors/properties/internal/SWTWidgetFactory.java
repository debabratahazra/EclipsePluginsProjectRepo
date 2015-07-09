package com.odcgroup.workbench.editors.properties.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 *
 * @author pkk
 *
 */
public class SWTWidgetFactory {
	
	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	public static CLabel createLabel(Composite parent, String text, String tooltip, boolean required) {
		CLabel label = new CLabel(parent, SWT.LEFT);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		String colon = " :  ";
		if (required){
			colon = "* :  ";
		}
		label.setText(text == null ? "" : text+colon);
		if (tooltip != null)	
			label.setToolTipText(tooltip);
		GridData data = new GridData();
		data.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(data);
		return label;
	}
	
	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	public static CLabel createSimpleLabel(Composite parent, String text) {
		CLabel label = new CLabel(parent, SWT.LEFT);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		label.setText(text);
		GridData data = new GridData();
		data.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(data);
		return label;
	}
	
	/**
	 * @param parent
	 * @param textWidth
	 * @return
	 */
	public static Text createTextField(Composite parent, boolean multiLine){
		Text text = null;
		if (multiLine) {
			text = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		} else {
			text = new Text(parent, SWT.BORDER);
		}
		GridData data = new GridData();
		if (multiLine) {
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
		}
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		text.setLayoutData(data);
		return text;		
	}
	
	/**
	 * @param parent
	 * @return
	 */
	public static Group createGroup(Composite parent,
			String groupName, boolean fillBoth) {
		return createGroup(parent, 2, groupName, fillBoth);
	}
	
	/**
	 * @param parent
	 * @return
	 */
	public static Group createGroup(Composite parent,
			String groupName, boolean fillBoth, int columns) {
		return createGroup(parent, columns, groupName, fillBoth);
	}
	
	/**
	 * @param parent
	 * @param groupName
	 * @return
	 */
	public static Composite createRowComposite(Composite parent, boolean fillHorizontal) {
		Composite group = new Composite(parent, SWT.NONE);
		group.setBackground(parent.getBackground());
		RowLayout layout = new RowLayout();
		layout.marginLeft = 1;
		layout.marginTop = 1;
		layout.marginBottom = 1;
		group.setLayout(new RowLayout());
		if (fillHorizontal) {
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		return group;
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @param label
	 * @param fillBoth
	 * @return
	 */
	public static Group createGroup(Composite parent, int columns, String label, boolean fillBoth) {
		Group group = new Group(parent, SWT.NONE);
		group.setBackground(parent.getBackground());
		if (label != null)
			group.setText(label);
		GridLayout gridLayout = new GridLayout(columns, false);
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 1;
		group.setLayout(gridLayout);
		GridData data = null;
		if (fillBoth) {
			data = new GridData(GridData.FILL_BOTH);
		} else {
			data = new GridData(GridData.FILL_HORIZONTAL);
		}
		//data.verticalAlignment = GridData.BEGINNING;
		group.setLayoutData(data);
		return group;
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @param label
	 * @param fillBoth
	 * @return
	 */
	public static Group createGroup(Composite parent, int columns, String label, boolean fillBoth, boolean equalCols) {
		Group group = new Group(parent, SWT.NONE);
		group.setBackground(parent.getBackground());
		if (label != null)
			group.setText(label);
		GridLayout gridLayout = new GridLayout(columns, equalCols);
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 1;
		group.setLayout(gridLayout);
		GridData data = null;
		if (fillBoth) {
			data = new GridData(GridData.FILL_BOTH);
		} else {
			data = new GridData(GridData.FILL_HORIZONTAL);
		}
		//data.verticalAlignment = GridData.BEGINNING;
		group.setLayoutData(data);
		return group;
	}
	
	/**
	 * @param parent
	 * @param fillBoth
	 * @return
	 */
	public static Composite createComposite(Composite parent, boolean fillBoth) {
		return createComposite(parent, fillBoth, 2);		
	}
	
	/**
	 * @param parent
	 * @param fillBoth
	 * @param columns
	 * @return
	 */
	public static Composite createComposite(Composite parent, boolean fillBoth, int columns) {
		Composite group = new Composite(parent, SWT.NONE);
		group.setBackground(parent.getBackground());
		GridLayout gridLayout = new GridLayout(columns, false);
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 1;
		group.setLayout(gridLayout);
		GridData data = null;
		if (fillBoth) {
			data = new GridData(GridData.FILL_BOTH);
		} else {
			data = new GridData(GridData.FILL_HORIZONTAL);
		}
		data.verticalAlignment = GridData.BEGINNING;
		group.setLayoutData(data);
		return group;		
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @return
	 */
	public static Composite createComposite(Composite parent, int columns) {
		return createComposite(parent, columns, true);
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @return
	 */
	public static Composite createComposite(Composite parent, int columns, boolean zeroSpacing) {
		Composite body = new Composite(parent, SWT.NONE);
		if (columns <= 0) { 
			columns = 1;
		}
		GridLayout gridLayout = new GridLayout(columns, false);
		if (zeroSpacing) {
			gridLayout.horizontalSpacing = 0;
			gridLayout.verticalSpacing = 0;
		}
		gridLayout.marginHeight = 1;
		gridLayout.marginWidth = 1;
		body.setLayout(gridLayout);
		body.setBackground(parent.getBackground());
		body.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return body;
	}
	
	/**
	 * @param parent
	 * @param label
	 * @return
	 */
	public static Button createButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.NONE);
		GridData data = new GridData();
		data.widthHint = 60;
		button.setLayoutData(data);
		button.setText(label);
		return button;
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @return
	 */
	public static Label createFiller(Composite parent){
		Label label = new Label(parent, SWT.NONE);
		label.setBackground(parent.getBackground());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(data);
		return label;		
	}

}
