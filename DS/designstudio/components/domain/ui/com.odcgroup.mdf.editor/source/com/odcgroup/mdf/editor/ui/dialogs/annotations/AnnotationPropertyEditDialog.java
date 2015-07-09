package com.odcgroup.mdf.editor.ui.dialogs.annotations;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.FormWidgetFactory;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class AnnotationPropertyEditDialog extends Dialog {
    private static final String DEFAULT_WINDOW_TITLE = MdfPlugin
            .getResourceString("annotationsPro.editDialog.defaultTitle");
    private String description = "";
    private String defaultName = "";
    private String annotationName = "";
    private String annotationValue = "";
    private boolean cDataEnabled = false;
    private String windowTitle = DEFAULT_WINDOW_TITLE;
    private Text annotationNameText = null;
    private Text annotationValueText = null;
    private Button cData = null;
    private Composite body = null;
    private Combo booleanCombo = null;
    
    private boolean nameEditable = true;
    private boolean startWithName = false;
    private boolean valueBoolean = false;
    private boolean cDataCheck = true;

    private Combo possibleValuesCombo = null;
    private String[] possibleValues = null;
    
	/**
     * Constructor for AnnotationEditDialog
     */
    public AnnotationPropertyEditDialog(Shell parentShell) {
        super(parentShell);
    }
    
    public AnnotationPropertyEditDialog(Shell parentShell, String defaultName, boolean nameEditable, boolean valueBoolean) {
    	super(parentShell);
    	this.defaultName = defaultName;
    	this.nameEditable = nameEditable;
    	this.valueBoolean = valueBoolean;
    }
    
    public AnnotationPropertyEditDialog(Shell parentShell, String defaultName, boolean nameEditable, String[] possibleValues) {
    	super(parentShell);
    	this.defaultName = defaultName;
    	this.nameEditable = nameEditable;
    	this.possibleValues = possibleValues; 
    }
    
    public AnnotationPropertyEditDialog(Shell parentShell, boolean startsWithName, String defaultName) {
    	this(parentShell, defaultName, true, false);
    	this.startWithName = startsWithName;
    }

    public void setDescription(String description) {
    	this.description = description;
    }
    
    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationValue(String annotationValue) {
        this.annotationValue = annotationValue;
    }

    public String getAnnotationValue() {
        return annotationValue;
    }

	public boolean isCDataEnabled() {
		return cDataEnabled;
	}

	public void setCDataEnabled(boolean dataEnabled) {
		cDataEnabled = dataEnabled;
	}

    public void setWindowTitle(String title) {
        this.windowTitle = title;
        updateWindowTitle();
    }

    protected Point getInitialSize() {
        return new Point(400, 350);
    }

    protected Control createContents(Composite parent) {
        Control ctrl = super.createContents(parent);
        update();
        return ctrl;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    protected Control createDialogArea(Composite parent) {
        final WidgetFactory factory = new FormWidgetFactory();

        body = factory.createComposite(parent);
        body.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 10;
		layout.marginWidth = 15;
		body.setLayout(layout);
        if (!isValueBoolean() && isCDataCheck()) {
	        cData =  factory.createButton(body, MdfPlugin
	                .getResourceString("annotationsPro.editDialog.cDataLabel.text"), SWT.CHECK);
	        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
	        gData.horizontalSpan = 2;
	        cData.setLayoutData(gData);
	        cData.addSelectionListener(new SelectionAdapter() {
				
				public void widgetSelected(SelectionEvent e) {
					if (cData.getSelection()) {
						annotationValueText.dispose();
						createTextArea(factory);
						body.layout();
						setCDataEnabled(true);
					} else {
						annotationValueText.dispose();
						createTextField(factory);
						body.layout();
						setCDataEnabled(false);
					}
				}
	        	
	        });
        }

        Label descriptionLabel = factory.createLabel(body, "Description: ");
        GridData descriptionGridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        descriptionLabel.setLayoutData(descriptionGridData);
        
        Text descriptionText = new Text(body,  
        		SWT.WRAP | SWT.MULTI | SWT.READ_ONLY);
        descriptionText.setText(description);
        GridData descriptionTextGridData = new GridData(SWT.FILL, SWT.NONE, true, false);
        descriptionText.setEditable(false);
        descriptionText.setLayoutData(descriptionTextGridData);

        // Hide the description line if no description is given
        if (description.trim().length() == 0) {
        	descriptionGridData.heightHint = 0;
        	descriptionTextGridData.heightHint = 0;
        }
        
        Label annotationNameLabel = factory.createLabel(body, MdfPlugin
                .getResourceString("annotationsPro.editDialog.nameLabel.text"));
       
        annotationNameText = factory.createText(body, null);

        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        annotationNameText.setLayoutData(layoutData);
        annotationNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                update();
            }
        });
        if (annotationName.trim().length()==0) {
        	annotationName = defaultName;
        }
        annotationNameText.setText(annotationName);
        
        Label label = factory.createLabel(body, MdfPlugin
                .getResourceString("annotationsPro.editDialog.valueLabel.text"));
        label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        
        // create appropriate widget
        if (isValueEnum()) {
        	createAnnotationValueEnumField(factory);
        } else if (isValueBoolean()){
        	createAnnotationValueBooleanField(factory);
        } else {
        	createAnnotationValueField(factory);   	
        }

        
        if (nameEditable){
        	annotationNameText.setFocus();
        } else {
            // If the name is not editable, hides it
        	layoutData.heightHint = 0;
        	annotationNameLabel.setLayoutData(layoutData);
        }


        updateWindowTitle();
        factory.dispose();
        return body;
    }
    
    /**
     * @param factory
     */
    private void createAnnotationValueEnumField(WidgetFactory factory) {
    	possibleValuesCombo = new Combo(body, SWT.SIMPLE|SWT.DROP_DOWN|SWT.READ_ONLY);
    	for (int i=0; i<possibleValues.length; i++) {
        	possibleValuesCombo.add(possibleValues[i]);
    		if (possibleValues[i].equals(annotationValue)) {
    			possibleValuesCombo.select(i);
    		}
    	}
    	possibleValuesCombo.setFocus();
    }
    
    /**
     * @param factory
     */
    private void createAnnotationValueBooleanField(WidgetFactory factory) {
    	booleanCombo = new Combo(body, SWT.SIMPLE|SWT.DROP_DOWN|SWT.READ_ONLY);
    	booleanCombo.add("true");
    	booleanCombo.add("false");
    	setComboSelection();  
    	booleanCombo.setFocus();
    }
    
    /**
     * @param factory
     */
    private void createAnnotationValueField(WidgetFactory factory) {
    	if (isCDataEnabled()){
        	cData.setSelection(true);
        	createTextArea(factory);
        } else {
        	if (cData != null)
        	cData.setSelection(false);
            createTextField(factory);
        }    
    }
    
    /**
     * 
     */
    private void setComboSelection() {
    	if (annotationValue.trim().length()>0) {
    		if("true".equalsIgnoreCase(annotationValue)) {
    			booleanCombo.select(0);
    		} else if ("false".equalsIgnoreCase(annotationValue)) {
    			booleanCombo.select(1);
    		}
    	}
    }
    
    /**
     * @param factory
     */
    private void createTextField(WidgetFactory factory) {
    	annotationValueText = new Text(body, SWT.SINGLE | SWT.BORDER);
    	GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        annotationValueText.setLayoutData(layoutData);
        if (annotationValue != null) {
        	annotationValueText.setText(annotationValue);
        }
        annotationValueText.setFocus();
    }
    
    /**
     * @param factory
     */
    private void createTextArea(WidgetFactory factory) {
		//annotationValueText =  factory.createText(body, null, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		annotationValueText = new Text(body, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.heightHint = 200;
		annotationValueText.setLayoutData(gridData);
		if (annotationValue != null){
	        annotationValueText.setText(annotationValue);			
		}
		annotationValueText.setFocus();
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {
        if (isStartWithName() && !annotationNameText.getText().trim().startsWith(defaultName)){
        	MessageDialog.openError(getParentShell(), "Error",
					"Property Name should start with \"" + defaultName + "\"");
        	annotationNameText.setText(annotationName);
        	annotationNameText.forceFocus();
        	return;
        }
        annotationName = annotationNameText.getText().trim();
        if (isValueEnum()) {
        	int index = possibleValuesCombo.getSelectionIndex();
        	if (index<0){
            	MessageDialog.openError(getParentShell(), "Error",
				"Property Value should be specified");
            	return;
        	}
        	annotationValue = possibleValuesCombo.getItem(possibleValuesCombo.getSelectionIndex());
        } else if (isValueBoolean()) {
        	int index = booleanCombo.getSelectionIndex();
        	if (index<0){
            	MessageDialog.openError(getParentShell(), "Error",
				"Property Value should be specified");
            	return;    
        	}
        	annotationValue = booleanCombo.getItem(booleanCombo.getSelectionIndex());
        } else {
        	annotationValue = annotationValueText.getText();
        }

        super.okPressed();
    }

	protected void update() {
        Button okButton = getButton(OK);

        if (okButton != null) {
            String name = annotationNameText.getText().trim();
            okButton.setEnabled(!name.equals(""));
        }
    }

    protected void updateWindowTitle() {
        if (getShell() == null) {
            // Not created yet
            return;
        }

        if (windowTitle == null) {
            windowTitle = DEFAULT_WINDOW_TITLE;
        }

        getShell().setText(windowTitle);
    }

	public boolean isNameEditable() {
		return nameEditable;
	}

    public boolean isStartWithName() {
		return startWithName;
	}

	public boolean isValueBoolean() {
		return valueBoolean;
	}

	private boolean isValueEnum() {
		return possibleValues != null;
	}

	public boolean isCDataCheck() {
		return cDataCheck;
	}

	public void setCDataCheck(boolean dataCheck) {
		cDataCheck = dataCheck;
	}
}
