package com.odcgroup.process.diagram.custom.dialogs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProcessSetDefaultsDialog extends Dialog {

	private EObject newElement;

	private ArrayList defaultValues = null;

	public ProcessSetDefaultsDialog(Shell shell, EObject newElement) {
		super(shell);
		this.newElement = newElement;
		defaultValues = getDefaultValues(newElement);
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Add New " + newElement.eClass().getName()); //$NON-NLS-1$
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Group group = createGroup(composite, newElement.eClass().getName(), 2);
		for (Iterator i = defaultValues.iterator(); i.hasNext();) {
			DefaultValue defaultValue = (DefaultValue) i.next();
			Class setType = defaultValue.method.getParameterTypes()[0];
			if (setType.equals(String.class)) {
				createLabel(group, defaultValue.label);
				defaultValue.widget = createTextField(group);
				if (getDefaultValue(defaultValue.label) == null) {
					((Text) defaultValue.widget).setText(defaultValue.method
							.getName().substring(3));
				} else {
					((Text) defaultValue.widget)
							.setText((String) getDefaultValue(defaultValue.label));
					((Text) defaultValue.widget).setEditable(false);
				}
			} else if (setType.equals(int.class)) {
				createLabel(group, defaultValue.label);
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText("0");//$NON-NLS-1$
			} else if (setType.equals(float.class)) {
				createLabel(group, defaultValue.label);
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText("0.0F");//$NON-NLS-1$
			} else if (setType.equals(Boolean.class)) {
				createLabel(group, defaultValue.label);
				defaultValue.widget = createCheckBox(group, "Yes"); //$NON-NLS-1$
			} else if (setType.equals(boolean.class)) {
				createLabel(group, defaultValue.label);
				defaultValue.widget = createCheckBox(group, "Yes"); //$NON-NLS-1$				
			}
		}
		return composite;
	}

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

	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text+":");
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

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

	protected Button createCheckBox(Composite group, String label) {
		Button button = new Button(group, SWT.CHECK | SWT.LEFT);
		button.setText(label);
		GridData data = new GridData();
		button.setLayoutData(data);
		return button;
	}

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
		setDefaultValues(defaultValues, newElement);
		super.okPressed();
	}

	public EObject getNewElement() {
		return newElement;
	}

	private ArrayList getDefaultValues(EObject aChild) {
		ArrayList ret = new ArrayList();
		Class childClassImpl = aChild.getClass();
		Method[] methods = childClassImpl.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("set")) {//$NON-NLS-1$
				Class setType = methods[i].getParameterTypes()[0];
				if (!setType.equals(Class.class)) {
					ret.add(new DefaultValue(methods[i]));
				}
			}
		}
		return ret;
	}

	private void setDefaultValues(ArrayList defaultValues, EObject child) {
		for (Iterator i = defaultValues.iterator(); i.hasNext();) {
			DefaultValue defaultValue = (DefaultValue) i.next();
			try {
				if (!defaultValue.method.getParameterTypes()[0]
						.equals(boolean.class)) {
					defaultValue.method.invoke(child,
							new Object[] { defaultValue.value });
				} else {
					if (defaultValue.value == null) {
						defaultValue.method.invoke(child, Boolean.TRUE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Object getDefaultValue(String label) {
		ArrayList ret = new ArrayList();
		Class childClassImpl = newElement.getClass();
		Method[] methods = childClassImpl.getMethods();
		for (int i = 0; i < methods.length; i++) {
			try {
				if (methods[i].getName().startsWith("get")) {
					if (methods[i].getName().substring(3).equals(label)) {
						return methods[i].invoke(newElement, new Object[] {});
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private class DefaultValue {

		protected Method method;

		protected String label;

		protected Object value;

		protected Object widget;

		protected DefaultValue(Method method) {
			this.method = method;
			label = method.getName().substring(3);//$NON-NLS-1$
		}

		public String toString() {
			return method.toString();
		}
	}

}
