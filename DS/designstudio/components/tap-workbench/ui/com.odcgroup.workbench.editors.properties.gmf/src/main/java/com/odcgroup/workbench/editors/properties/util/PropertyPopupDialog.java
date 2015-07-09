package com.odcgroup.workbench.editors.properties.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class PropertyPopupDialog extends AbstractTitleAreaDialog {
	
	
	private EObject child = null;
	private ArrayList defaultValues = null;	

	/**
	 * @param parentShell
	 * @param addCommand
	 */
	public PropertyPopupDialog(Shell parentShell, AddCommand addCommand) {
		super(parentShell);
		Collection collection = addCommand.getCollection();
		this.child = (EObject) collection.iterator().next();
		this.domain = addCommand.getDomain();
		this.defaultValues = getDefaultValues(child);
	}
	
	/**
	 * @param parentShell
	 */
	public PropertyPopupDialog(Shell parentShell){
		super(parentShell);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.core.ui.dialog.AbstractTitleAreaDialog#setCommand(org.eclipse.emf.edit.command.AddCommand)
	 */
	public void setCommand(AddCommand command) {
		Collection collection = command.getCollection();
		this.child = (EObject) collection.iterator().next();
		this.domain = command.getDomain();
		this.defaultValues = getDefaultValues(child);
	}
	
	/**
	 * @param parentShell
	 * @param addCommand
	 * @param update
	 */
	public PropertyPopupDialog(Shell parentShell, AddCommand addCommand, boolean update){
		this(parentShell, addCommand);
		this.update = update;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(child.eClass().getName());
		if (update){
			setMessage("Update "+child.eClass().getName());
		} else {
			setMessage("Create new "+child.eClass().getName());
		}
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);		
		shell.setText(child.eClass().getName()); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite body = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(body, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 15;
		layout.marginHeight = 10;
		layout.numColumns = 2;
		Object val = null;			
		composite.setLayout(layout);
		try {		
			for (Iterator i = defaultValues.iterator(); i.hasNext();) {
				DialogHelper defaultValue = (DialogHelper) i.next();
				Label label = createLabel(composite, defaultValue.label);
				label.setToolTipText(""); //$NON-NLS-1$
				Class setType = defaultValue.setMethod.getParameterTypes()[0];
				if (update){
					val = defaultValue.getMethod.invoke(child, new Object[]{});
				}
				if (setType.equals(String.class)) {	
					defaultValue.widget = createTextField(composite);
					if (!update || val == null) {
						val = defaultValue.setMethod.getName().substring(3); 					
					}
					((Text) defaultValue.widget).setText((String)val);				
				} else if (setType.equals(int.class)) {
					defaultValue.widget = createTextField(composite);
					if (!update || val == null) {
						val = "0"; //$NON-NLS-1$		
					}
					((Text) defaultValue.widget).setText((String)val);	
				} else if (setType.equals(float.class)) {
					defaultValue.widget = createTextField(composite);
					if (!update || val == null) {
						val = "0.0F"; //$NON-NLS-1$		
					}
					((Text) defaultValue.widget).setText((String)val);	
				} else if (setType.equals(Boolean.class)) {
					defaultValue.widget = createCheckBox(composite, "Yes"); //$NON-NLS-1$
				} else if (setType.equals(boolean.class)) {
					defaultValue.widget = createCheckBox(composite, "Yes"); //$NON-NLS-1$				
				} 
			}
		} catch (Exception e){
			e.printStackTrace();
		}
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
		data.widthHint = 100;
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
		data.widthHint = 300;
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
			DialogHelper defaultValue = (DialogHelper) i.next();
			Class setType = defaultValue.setMethod.getParameterTypes()[0];
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
			} else if (setType.equals(boolean.class)) {
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
		ArrayList<DialogHelper> ret = new ArrayList<DialogHelper>();
		DialogHelper dialoghelper = null;
		Class childClassImpl = aChild.getClass();
		Method[] methods = childClassImpl.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("set")) {//$NON-NLS-1$
				Class setType = methods[i].getParameterTypes()[0];
				if (!setType.equals(Class.class)
						&& !setType.equals(EList.class)) {// && !setType.equals(Transition.class)
					dialoghelper = new DialogHelper(methods[i]);
					dialoghelper.setGetMethod(getGetterMethod(childClassImpl, setType, methods[i].getName().substring(3)));
					ret.add(dialoghelper);
				}
			}
		}
		return ret;
	}
	
	/**
	 * @param childClass
	 * @param setType
	 * @return
	 */
	private Method getGetterMethod(Class childClass, Class setType, String attr){
		Method[] methods = childClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get")) {
				if (setType.equals(methods[i].getReturnType()) && methods[i].getName().substring(3).equals(attr)) {
					return methods[i];
				}
			}
		}
		return null;
	}	
	

	/**
	 * @param defaultValues
	 */
	private void setDefaultValues(ArrayList defaultValues) {
		final ArrayList defaultVal = defaultValues;
		TransactionalEditingDomain tdomain = (TransactionalEditingDomain) domain;							
		tdomain.getCommandStack().execute(
				new RecordingCommand(tdomain) {
					protected void doExecute() {
						for (Iterator i = defaultVal.iterator(); i.hasNext();) {
							DialogHelper defaultValue = (DialogHelper) i.next();
							try {
								defaultValue.setMethod.invoke(child, new Object[] { defaultValue.value });
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
		
	}

}
