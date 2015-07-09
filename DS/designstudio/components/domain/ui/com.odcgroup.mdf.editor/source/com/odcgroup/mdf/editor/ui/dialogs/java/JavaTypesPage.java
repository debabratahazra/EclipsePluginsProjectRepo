package com.odcgroup.mdf.editor.ui.dialogs.java;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author pkk
 *
 */
public class JavaTypesPage extends DialogPage {

	private MdfModelElement model;
	private Text javaTypeText;

	/**
	 * @param businessType
	 */
	public JavaTypesPage(MdfModelElement model) {
		super();
		setTitle("Java");
		setDescription("The Java Type Mapping");
		this.model = model;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		WidgetFactory factory = getWidgetFactory();

		Composite container = factory.createComposite(parent);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new GridLayout(2, false));

		Label javaTypeLabel = factory.createLabel(container, "Java &type:");
		javaTypeText = factory.createText(container, null);
		javaTypeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		javaTypeLabel.setEnabled(false);
		javaTypeText.setEditable(false);			
		javaTypeText.setEnabled(false);

		initialize();

		setControl(container);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#doSave(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public void doSave(MdfModelElement element) {
        ModelFactory.INSTANCE.removeAnnotations(element, JavaAspect.NAMESPACE_URI);
        addAnnotation(element, JavaAspect.JAVA_TYPE, javaTypeText.getText().trim());

	}

    /**
     * @param model
     * @param name
     * @param value
     */
    private void addAnnotation(MdfModelElement model, String name, String value) {
        if (value.length() > 0) {
            MdfAnnotation annot = ModelFactory.INSTANCE.createMdfAnnotation(
                    JavaAspect.NAMESPACE_URI, name, value);
            ModelFactory.INSTANCE.addAnnotation(model, annot);
        }
    }
	
	/**
	 * 
	 */
	private void initialize() {
		setText(javaTypeText, JavaAspect.getJavaType(model));
	}

}
