package com.odcgroup.page.external.mdf;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
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
import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;


/**
 * @author atr
 * @version 1.0
 * @since DS 1.40.0
 */
public class AnnotationWidgetPropertyEditDialog extends Dialog {
    private static final String DEFAULT_WINDOW_TITLE = MdfPlugin
            .getResourceString("annotationsPro.editDialog.defaultTitle");
    private String annotationName = "";
    private String annotationValue = "";
    private String windowTitle = DEFAULT_WINDOW_TITLE;
    
    private Text propertyNameText = null;
    private Combo propertyNamesCombo = null;
    private Combo propertyValuesCombo = null;
    private Text propertyValueText = null;
    
    private Composite body = null;
    
    private WidgetType widgetType;
    private List excludedProperties = null;
    private List<PropertyType> propertyTypes = null;
    private int propertyTypeIndex = 0;
    private int ordinalValue = 0;
    private List<DataValue> propertyValues = null;
    private int propertyValueIndex = -1;
    
    private void init(WidgetType widgetType, List<MdfAnnotationProperty> excludedProperties) {
        this.widgetType = widgetType;
        Set<String> excludedNames = new HashSet<String>();
        if (excludedProperties != null) {
	        for (MdfAnnotationProperty ap : excludedProperties) {
	        	excludedNames.add(ap.getName());
	        }
        }
        propertyTypes = AnnotationWidgetPropertyHelper.fetchDerivablePropertyTypes(widgetType, excludedNames);
    }

	/**
     * Constructor for AnnotationEditDialog
     */
    public AnnotationWidgetPropertyEditDialog(Shell parentShell, WidgetType widgetType) {
        this(parentShell, widgetType, null);
    }    
    
	/**
     * Constructor for AnnotationEditDialog
     */
    public AnnotationWidgetPropertyEditDialog(Shell parentShell, WidgetType widgetType, List<MdfAnnotationProperty> excludedProperties) {
        super(parentShell);
        init(widgetType, excludedProperties);
    }
    
    public void setAnnotationName(String annotationName) {
		if (annotationName.equals(GUIAspect.DISPLAY_FORMAT)) {
			this.annotationName = PropertyTypeConstants.FORMAT;
		} else {
			this.annotationName = annotationName;
		}
    }

    public String getAnnotationName() {
		if (annotationName.equals(PropertyTypeConstants.FORMAT)) {
			return GUIAspect.DISPLAY_FORMAT;
		} 
        return annotationName;
    }

    public void setAnnotationValue(String annotationValue) {
        this.annotationValue = annotationValue;
    }

    public String getAnnotationValue() {
        return annotationValue;
    }

    public void setWindowTitle(String title) {
        this.windowTitle = title;
        updateWindowTitle();
    }

    protected Point getInitialSize() {
        return new Point(400, 350);
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

        factory.createLabel(body, MdfPlugin
                .getResourceString("annotationsPro.editDialog.nameLabel.text"));
		
		String[] names = new String[propertyTypes.size()];
		int index = 0;
		propertyTypeIndex = 0;
		for (PropertyType type : propertyTypes) {
			names[index] = type.getDisplayName();
			if (type.getName().equals(annotationName)) {
				propertyTypeIndex = index;
			}
			index++;
		}
		
		if (StringUtils.isEmpty(annotationName)) {
			propertyNamesCombo = factory.createCombo(body, names);
			propertyNamesCombo.select(propertyTypeIndex);
	        propertyNamesCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        propertyNamesCombo.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	propertyTypeIndex = propertyNamesCombo.getSelectionIndex();
	                update();
	            }
	        });
		} else {
	    	propertyNameText = new Text(body, SWT.SINGLE | SWT.BORDER);
	    	GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
	    	propertyNameText.setLayoutData(layoutData);
	    	propertyNameText.setText(names[propertyTypeIndex]);
	    	propertyNameText.setEditable(false);
		}

        Label label = factory.createLabel(body, MdfPlugin
                .getResourceString("annotationsPro.editDialog.valueLabel.text"));
        label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        
        factory.dispose();
        updateWindowTitle();
        update();
        return body;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {

    	if (propertyNameText != null) {
	    	PropertyType pType = propertyTypes.get(propertyTypeIndex);
	        annotationName = pType.getName();
	    } else {
	    	int index = propertyNamesCombo.getSelectionIndex();
	    	if (index<0){
	        	MessageDialog.openError(getParentShell(), "Error",
				"Property Name should be specified");
	        	return;    
	    	}
	    	PropertyType pType = propertyTypes.get(index);
	        annotationName = pType.getName();
    	}
        if (propertyValueText != null) {
        	annotationValue = propertyValueText.getText();
        } else {
        	int index = propertyValuesCombo.getSelectionIndex();
        	if (index < 0) {
        		MessageDialog.openError(getParentShell(), "Error",
        		"Property Value should be specified");
        		return;
        	}
        	DataValue value = propertyValues.get(index);
        	annotationValue = value.getValue();
        }

        super.okPressed();
    }

    /**
     * 
     */
    protected void update() {
    	
    	PropertyType type = propertyTypes.get(propertyTypeIndex);
    	propertyValues = type.getDataType().getValues();
    	boolean visible = propertyValues != null && propertyValues.size() > 1;
    	
		if (propertyValuesCombo != null) {
			propertyValuesCombo.removeAll();
			propertyValuesCombo.dispose();
			propertyValuesCombo = null;
		}
		if (propertyValueText != null) {
			propertyValueText.dispose();
			propertyValueText = null;
		}

		// create appropriate widget
    	if (visible) {
			propertyValuesCombo = new Combo(body, SWT.READ_ONLY);
			propertyValuesCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			propertyValuesCombo.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	propertyValueIndex = propertyValuesCombo.getSelectionIndex();
	            }
	        });
			int index = 0;
			for (DataValue value : propertyValues) {
				String dn = value.getDisplayName();
				if (annotationValue.equals(value.getValue())) {
					propertyValueIndex = index;
				}
				if (dn == null) {
					dn ="";
				}
				propertyValuesCombo.add(dn);
				index++;
			}
			
    	} else {
	    	propertyValueText = new Text(body, SWT.SINGLE | SWT.BORDER);
	    	GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
	    	propertyValueText.setLayoutData(layoutData);
        	propertyValueText.setText(annotationValue);
    	}
    	if (propertyValuesCombo != null) {
			propertyValuesCombo.select(propertyValueIndex);
    	}
    	body.layout(true, true);
    	
        Button okButton = getButton(OK);
        if (okButton != null) {
            okButton.setEnabled(true);
        }
    }

    /**
     * 
     */
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
	
}
