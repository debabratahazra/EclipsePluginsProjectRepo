package com.odcgroup.aaa.ui.internal.page;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.page.bindings.IUDEPropertiesBinding;
import com.odcgroup.aaa.ui.internal.page.bindings.IUDEPropertiesViewAdapter;
import com.odcgroup.aaa.ui.internal.page.bindings.UDEPropertiesFactory;
import com.odcgroup.aaa.ui.internal.page.validation.UDEModelValidator;
import com.odcgroup.aaa.ui.internal.page.validation.UDEModelValidatorFactory;
import com.odcgroup.mdf.ecore.MdfFactoryImpl;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

public class UDETripleAPage extends DialogPage implements ModifyListener, SelectionListener, IUDEPropertiesViewAdapter {
	
	private MdfModelElement model;
	private MdfModelElement workingCopy;
	private Composite container;
	private GridData fieldGridData;
	
	private Text entitySQLNameField;
	private Text entityNameField;
	private Text attributeSQLNameField;
	private Text attributeNameField;
	private Text attributeTypeField;
	private Text parentTypeAttributeField;
	private Text attributeOwnerOfEnumField;
	private Text permittedValueNameField;
	private Text permittedValueRankField;
	
	private Button securedCheckbox;
	
	IUDEPropertiesBinding bindings;
	IUDEPropertiesBinding workingCopyBindings;
	
	private final static UDEModelValidator UDENTITIES_VALIDATOR = new UDEModelValidatorFactory().createUDEModelValidator();

	public UDETripleAPage(MdfModelElement model) {
		setTitle(Messages.getString("aaa.mdf.page.ude.title"));
		this.model = model;
		this.workingCopy = createWorkingCopy(model);
	}
	
	/**
	 * The working copy will be updated with each user modification to allow to run validation as
	 * the user types. This method create a mdf model (i.e. a MdfClass) for an existing mdf model. 
	 * This new model will have no field initialized as it will be only used to set annotation on the element
	 * @param mdfModelElement
	 * @return a new mdf model element of the same type than mdfModelElement
	 */
	private MdfModelElement createWorkingCopy(MdfModelElement mdfModelElement) {
		return (MdfModelElement)MdfFactoryImpl.eINSTANCE.create(((EObject)mdfModelElement).eClass());
	}


	@Override
	public void createControl(Composite parent) {
		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		fieldGridData = new GridData(gridDataStyle);
		
		container = new Composite(parent, SWT.NULL);
		container.setBackground(parent.getBackground());
		container.setForeground(parent.getForeground());
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(fieldGridData);
		
		if (model instanceof MdfClass) {
			entitySQLNameField = createTextField("Entity SQL Name (*)");
			entityNameField = createTextField("Entity Name (*)");  
			{
	            Group modifiers = createGroup(container, "Modifiers");
	            GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
	            layoutData.horizontalSpan = 2;

	            modifiers.setLayoutData(layoutData);
	            modifiers.setLayout(new GridLayout(1, false));

	            securedCheckbox = createButton(modifiers, "&Secured",
	                    SWT.CHECK);
	        }
		} else if (model instanceof MdfAttribute) {
			attributeSQLNameField = createTextField("Attribute SQL Name (*)");
			attributeNameField = createTextField("Attribute Name (*)");
		} else if (model instanceof MdfAssociation) {
			attributeSQLNameField = createTextField("Attribute SQL Name (*)");
			attributeNameField = createTextField("Attribute Name (*)");
			attributeTypeField = createTextField("Attribute Data Type (*)");
			parentTypeAttributeField = createMdfClassAssociationSelector("Parent Type Attribute");
		} else if (model instanceof MdfReverseAssociation) {
			attributeSQLNameField = createTextField("Attribute SQL Name (*)");
			attributeNameField = createTextField("Attribute Name (*)");
		} else if ((model instanceof MdfEnumeration)) {
			attributeOwnerOfEnumField = createMdfAttributeSelector("Related Attribute (*)");
		} else if ((model instanceof MdfEnumValue)) {
			permittedValueNameField = createTextField("Permitted Value Name (*)");
			permittedValueRankField = createTextField("Permitted Value Rank (*)");
		}
		bindings = new UDEPropertiesFactory().createTripleAPropertiesBinding(this, model);
		workingCopyBindings = new UDEPropertiesFactory().createTripleAPropertiesBinding(this, workingCopy);
		setDescription(bindings.getDescription());
		bindings.updateModelToText();
		setControl(container);
		setStatus(UDENTITIES_VALIDATOR.validate(model));
	}

	private Text createTextField(String labelForText) {
		Label label = new Label(container, SWT.NULL);
		label.setText(labelForText);
		label.setBackground(container.getBackground());
		Text text = getWidgetFactory().createText(container, null);
		text.setLayoutData(fieldGridData);
		text.setEditable(true);
		text.addModifyListener(this);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.horizontalSpan = 2;
        text.setLayoutData(layoutData);
		return text;
	}
	
	private Button createButton(Composite parent, String text, int style) {
		Button button = new Button(parent, style | SWT.FLAT);
		button.setBackground(container.getBackground());
		button.setForeground(container.getForeground());
		if (text != null) {
			button.setText(text);
		}
		button.addSelectionListener(this);
		return button;
	}
	
	private Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.NONE);
		group.setBackground(container.getBackground());
		group.setForeground(container.getForeground());
		if (text != null) {
			group.setText(text);
		}
		return group;
	}
	
	private Text createMdfClassAssociationSelector(String labelForSelector) {
		getWidgetFactory().createLabel(container, labelForSelector);
		
		final Text text = getWidgetFactory().createText(container, null);
		text.setLayoutData(fieldGridData);
		text.addModifyListener(this);

		Button button = getWidgetFactory().createButton(container, "&Browse...",
                SWT.NULL);		
		

		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MdfAssociationSelectionDialog dialog = new MdfAssociationSelectionDialog(getShell(), (MdfAssociation)model);
		        String path = text.getText().trim();
		        MdfClass selectedMdfClass = UDEHelper.getInstance().findMdfClass(model, path);
		        dialog.setSelectedMdfClass(selectedMdfClass);
		        dialog.setSelectedMdfAssociation(UDEHelper.getInstance().findMdfAssociationInClass(selectedMdfClass, path));
		        if (dialog.open() == Window.OK) {
		        	if (dialog.getSelectedMdfAssociation() != null) {
		        		String val = dialog.getSelectedMdfAssociation().getQualifiedName().getQualifiedName();
		        		text.setText(val.replaceAll("#", "."));
		        	} else if (dialog.getSelectedMdfClass() != null) {
		        		String val = dialog.getSelectedMdfClass().getQualifiedName().getQualifiedName();
		        		text.setText(val.replaceAll("#", "."));
		        	} else {
		        		text.setText("");
		        	}
		        }
			}
		});		
		
		return text;
	}

	private Text createMdfAttributeSelector(String labelForSelector) {
		getWidgetFactory().createLabel(container, labelForSelector);
		
		final Text text = getWidgetFactory().createText(container, null);
		text.setLayoutData(fieldGridData);
		text.setEditable(true);
		text.addModifyListener(this);
		
		Button button = getWidgetFactory().createButton(container, "&Browse...",
                SWT.NULL);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MdfAttributeSelectionDialog dialog = new MdfAttributeSelectionDialog(getShell(), (MdfEnumeration)model);
		        String path = text.getText().trim();
		        dialog.setSelectedMdfAttribute(UDEHelper.getInstance().findMdfAttribute(model, path));
		        if (dialog.open() == Window.OK) {
		        	MdfAttribute mdfSelectedAttribute = dialog.getSelectedMdfAttribute();
		        	if (mdfSelectedAttribute != null) {
		        		String val = mdfSelectedAttribute.getQualifiedName().getQualifiedName();
		        		text.setText(val.replaceAll("#", "."));
		        	} else {
		        		text.setText("");
		        	}
		        }
			}
		});
		return text;
	}
	
	@Override
	public void modifyText(ModifyEvent e) {
		if (!bindings.isInitializing()) {
			workingCopyBindings.updateTextToModel(true);
			setStatus(UDENTITIES_VALIDATOR.validate(model, workingCopy));
			setDirty(true);
		}
	}

	@Override
	public void doSave(MdfModelElement element) {
		bindings.updateTextToModel(false);
		setStatus(UDENTITIES_VALIDATOR.validate(model));
	}

	@Override
	public Text getEntitySQLNameField() {
		return entitySQLNameField;
	}

	@Override
	public Text getEntityNameField() {
		return entityNameField;
	}

	@Override
	public Text getAttributeSQLNameField() {
		return attributeSQLNameField;
	}

	@Override
	public Text getAttributeNameField() {
		return attributeNameField;
	}

	@Override
	public Text getPermittedValueNameField() {
		return permittedValueNameField;
	}

	@Override
	public Text getPermittedValueRankField() {
		return permittedValueRankField;
	}

	@Override
	public void setStatus(IStatus status) {
		super.setStatus(status);
		if (isCurrentPage()) {
			// Resize the title area in case it has changed
			container.getParent().layout();
		}
	}

	@Override
	public Text getParentTypeAttribute() {
		return parentTypeAttributeField;
	}

	@Override
	public Text getAttributeOwnerOfEnum() {
		return attributeOwnerOfEnumField;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (!bindings.isInitializing()) {
			workingCopyBindings.updateTextToModel(true);
			setStatus(UDENTITIES_VALIDATOR.validate(model, workingCopy));
			setDirty(true);
		}		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public Button getSecuredButton() {
		return securedCheckbox;
	}

	@Override
	public Text getAttributeDataTypeField() {
		return attributeTypeField;
	}

}
