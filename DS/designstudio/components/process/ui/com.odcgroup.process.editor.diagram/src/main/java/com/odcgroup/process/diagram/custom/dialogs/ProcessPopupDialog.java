package com.odcgroup.process.diagram.custom.dialogs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProcessPopupDialog extends Dialog {

	private class DefaultValue {

		protected Method method;

		protected String label;

		protected Object value;

		protected Object widget;

		protected DefaultValue(Method method) {
			this.method = method;
			label = method.getName().substring(3) + ":";//$NON-NLS-1$
		}

		public String toString() {
			return method.toString();
		}
	}

	private EObject owner = null;

	private EObject child = null;

	private ArrayList defaultValues = null;

	/**
	 * @param parentShell
	 * @param addCommand
	 */
	public ProcessPopupDialog(Shell parentShell, AddCommand addCommand) {
		super(parentShell);
		Collection collection = addCommand.getCollection();
		this.owner = addCommand.getOwner();
		this.child = (EObject) collection.iterator().next();
		this.defaultValues = getDefaultValues(child);
	}

	/**
	 * @param parentShell
	 * @param setCommand
	 */
	public ProcessPopupDialog(Shell parentShell, SetCommand setCommand) {
		super(parentShell);
		this.owner = setCommand.getOwner();
		this.child = (EObject) setCommand.getValue();
		this.defaultValues = getDefaultValues(child);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Add New " + child.eClass().getName()); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Group group = createGroup(composite, child.eClass().getName(), 2);
		for (Iterator i = defaultValues.iterator(); i.hasNext();) {
			DefaultValue defaultValue = (DefaultValue) i.next();
			Label label = createLabel(group, defaultValue.label);
			label.setToolTipText(""); //$NON-NLS-1$
			Class setType = defaultValue.method.getParameterTypes()[0];
			if (setType.equals(String.class)) {
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText(defaultValue.method
						.getName().substring(3));
			} else if (setType.equals(int.class)) {
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText("0");//$NON-NLS-1$
			} else if (setType.equals(float.class)) {
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText("0.0F");//$NON-NLS-1$
			} else if (setType.equals(Boolean.class)) {
				defaultValue.widget = createCheckBox(group, "Yes"); //$NON-NLS-1$
			} else if (setType.equals(boolean.class)) {
				createLabel(group, defaultValue.label);
				defaultValue.widget = createCheckBox(group, "Yes"); //$NON-NLS-1$				
			}
		}
		return composite;
	}

	/**
	 * @param parent
	 * @param text
	 * @param numColumns
	 * @return
	 */
	protected Group createGroup(Composite parent, String text, int numColumns) {
		Group composite = new Group(parent, SWT.NONE);
		composite.setText(text);
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		layout.makeColumnsEqualWidth = false;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(data);
		return composite;
	}

	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Text createTextField(Composite parent) {
		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = GridData.CENTER;
		data.grabExcessVerticalSpace = false;
		data.widthHint = 250;
		text.setLayoutData(data);
		return text;
	}

	/**
	 * @param group
	 * @param label
	 * @return
	 */
	protected Button createCheckBox(Composite group, String label) {
		Button button = new Button(group, SWT.CHECK | SWT.LEFT);
		button.setText(label);
		GridData data = new GridData();
		button.setLayoutData(data);
		return button;
	}

	/**
	 * @param parent
	 * @param items
	 * @return
	 */
	protected Combo createCombo(Composite parent, String[] items) {
		Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setFont(parent.getFont());
		combo.setItems(items);
		combo.select(0);
		return combo;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		for (Iterator i = defaultValues.iterator(); i.hasNext();) {
			DefaultValue defaultValue = (DefaultValue) i.next();
			Class setType = defaultValue.method.getParameterTypes()[0];
			if (setType.equals(String.class)) {
				defaultValue.value = ((Text) defaultValue.widget).getText();
			} else if (setType.equals(int.class)) {
				String text = ((Text) defaultValue.widget).getText();
				defaultValue.value = Integer.valueOf(text);
			} else if (setType.equals(float.class)) {
				String text = ((Text) defaultValue.widget).getText();
				defaultValue.value = Float.valueOf(text);
			} else if (setType.equals(Boolean.class)) {
				defaultValue.value = ((Button) defaultValue.widget)
						.getSelection() ? Boolean.TRUE : Boolean.FALSE;
			}
		}
		setDefaultValues(defaultValues);
		super.okPressed();
	}

	/**
	 * @param aChild
	 * @return
	 */
	private ArrayList getDefaultValues(EObject aChild) {
		ArrayList ret = new ArrayList();
		Class childClassImpl = aChild.getClass();
		Method[] methods = childClassImpl.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("set")) {//$NON-NLS-1$
				Class setType = methods[i].getParameterTypes()[0];
				if (!setType.equals(Class.class)
						&& !setType.equals(EList.class)) {
					ret.add(new DefaultValue(methods[i]));
				}
			}
		}
		return ret;
	}

	/**
	 * @param defaultValues
	 */
	private void setDefaultValues(ArrayList defaultValues) {
		for (Iterator i = defaultValues.iterator(); i.hasNext();) {
			DefaultValue defaultValue = (DefaultValue) i.next();
			try {
				defaultValue.method.invoke(child, new Object[] { defaultValue.value });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
