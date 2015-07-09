package com.odcgroup.t24.version.editor.ui.controls;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.odcgroup.mdf.editor.ui.dialogs.MdfAttributeWithRef;
import com.odcgroup.mdf.editor.ui.dialogs.ModelElementMultiTreeSelectionUI;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.util.ApplicationFieldHelper;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeContentProvider;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeLabelProvider;
import com.odcgroup.t24.swt.util.SWTResourceManager;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.VersionApplicationLabelProvider;
import com.odcgroup.t24.version.editor.utils.VersionFieldHelper;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 *
 */
public class VersionFieldsControl extends Composite implements IVersionDataBindingControl {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private VersionDesignerEditor editor;
	
	private List<AbstractFieldTabControl> tabControls = new ArrayList<AbstractFieldTabControl>();
	
	private TableViewer annotationPropertyViewer;
	private Text documentationText;
	
	private Resource resource;
	private DataBindingContext context;
	private TabFolder tabFolder;	
	private FieldAPITabControl apiControl;
	
	private TranslationTabControl translationTabControl;
	private ModelElementMultiTreeSelectionUI fieldUI;
	private VersionFieldHelper helper;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public VersionFieldsControl(final Composite parent, VersionDesignerEditor editor, DataBindingContext context) {		
		super(parent, SWT.BORDER);
		
		this.editor = editor;		
		this.resource = editor.getEditedResource();
		this.context = context;
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = layout.marginHeight = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);		
		
		helper = new VersionFieldHelper(getVersion(), getEditingDomain());
		final SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
		GridData sashFormGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		// sashFormGD.minimumWidth = 780;
		sashForm.setLayoutData(sashFormGD);


		final Composite composite1 = createVersionFieldControls(sashForm);
		final Composite composite2 = createFieldTabs(sashForm);
		sashForm.setWeights(new int[] { 2, 3 });
		
		// Minimum Widths for Components of the SashForm
		// and the sum should be less than the parent: ScrollForm.minwidth(~723)
		final int COMP1_MIN_WIDTH = 300;
		final int COMP2_MIN_WIDTH = 422;
		
		composite2.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				
				boolean handleControl1 = composite1.getSize().x > 0 && composite1.getSize().x < COMP1_MIN_WIDTH;
				boolean handleControl2 = composite2.getSize().x > 0 && composite2.getSize().x < COMP2_MIN_WIDTH;
				
				if (handleControl1 || handleControl2) {

					int[] weights = sashForm.getWeights();
					int totalweights = 0;
					for (int weight : weights) {
						totalweights += weight;
					}
					
					int totalwidth = composite1.getSize().x + composite2.getSize().x;

					int w1, w2;
					if (handleControl1) {
						w1 = (int) (1.0 * totalweights * COMP1_MIN_WIDTH / totalwidth);
						w2 = totalweights - w1;
					} else {
						w2 = (int) (1.0 * totalweights * COMP2_MIN_WIDTH / totalwidth);
						w1 = totalweights - w2;
					}
					sashForm.setWeights(new int[] { w1, w2 });
					sashForm.layout(true, true);
					
				}
			}
		});
		initDataBindings(context);
	}

	private Composite createFieldTabs(Composite body) {
		
		Composite composite = new Composite(body, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = layout.marginHeight = 0;
		composite.setLayout(layout);
		toolkit.adapt(composite);
		//toolkit.paintBordersFor(composite);
		
		tabFolder = new TabFolder(composite, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tabFolder.setLayoutData(layoutData);
		toolkit.adapt(tabFolder);
		tabFolder.setBackground(composite.getBackground());
		toolkit.paintBordersFor(tabFolder);			
		
		TabItem presTab = new TabItem(tabFolder, SWT.NONE);
		presTab.setText("Presentation");
		
		FieldPresentationTabControl prescontrol = new FieldPresentationTabControl(tabFolder, editor, context, fieldUI.getSelectionViewer());
		presTab.setControl(prescontrol);
		tabControls.add(prescontrol);
		TabItem defTab = new TabItem(tabFolder, SWT.NONE);
		defTab.setText("Defaulting");
		
		DefaultingTabControl defcontrol = new DefaultingTabControl(tabFolder, editor, context , fieldUI.getSelectionViewer());		
		defTab.setControl(defcontrol);
		tabControls.add(defcontrol);
		
		TabItem transTab = new TabItem(tabFolder, SWT.NONE);
		transTab.setText("Translations");
		
		translationTabControl = new TranslationTabControl(tabFolder, editor, context, fieldUI.getSelectionViewer());
		transTab.setControl(translationTabControl);
		tabControls.add(translationTabControl);		
		
		TabItem apiTab = new TabItem(tabFolder, SWT.NONE);
		apiTab.setText("API");
		
		apiControl = new FieldAPITabControl(tabFolder, editor, context, fieldUI.getSelectionViewer());
		apiTab.setControl(apiControl);
		tabControls.add(apiControl);
		
		TabItem attrTab = new TabItem(tabFolder, SWT.NONE);
		attrTab.setText("Attributes");
		
		FieldAttributesTabControl attrControl = new FieldAttributesTabControl(tabFolder, editor, context, fieldUI.getSelectionViewer());
		attrTab.setControl(attrControl);
		tabControls.add(attrControl);	
		
		tabFolder.setEnabled(false);
		
		return composite;
		
	}
	
	/**
	 * @return
	 */
	protected EditingDomain getEditingDomain() {
		return ((VersionDesignerEditor) getEditor()).getEditingDomain();
	}

	private Composite createVersionFieldControls(Composite body) {
		
		Composite composite = new Composite(body, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = layout.marginHeight = 0;
		composite.setLayout(layout);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		fieldUI = new ModelElementMultiTreeSelectionUI(true, true) {
			@SuppressWarnings("unchecked")
			@Override
			public void manageSelection(List<MdfProperty> properties) {
				IStructuredSelection sel = (IStructuredSelection) fieldUI.getSelection();
				List<MdfProperty> list = sel.toList();
				helper.manageFields(list, properties);
				getSelectionViewer().refresh();
			}

			@Override
			protected void createCustomButtons(Composite composite) {
				Button starFieldButton = new Button(composite, SWT.NONE);
				starFieldButton.setText("*");
				GridData starButtonData = new GridData(GridData.FILL_HORIZONTAL);
				starFieldButton.setLayoutData(starButtonData);
				toolkit.adapt(starFieldButton, true, true);
		               
				starFieldButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						addCustomField("*");
					}
				});
			}
			
			protected void handleSelctionChaged(ISelection selection) {
				super.handleSelctionChaged(selection);
				Object input = ((IStructuredSelection) selection).getFirstElement();
				if (input != null) {
					tabFolder.setEnabled(true);
				} else {
					tabFolder.setEnabled(false);
				}
			}
		};
		fieldUI.setAddRequired(false);
		fieldUI.setContentProvider(new ApplicationTreeContentProvider());
		MdfClass mdfClass = getVersion().getForApplication();
		fieldUI.setLabelProvider(new ApplicationTreeLabelProvider(mdfClass));
		fieldUI.setSelectionLabelProvider(new VersionApplicationLabelProvider(mdfClass, getVersion()));
		fieldUI.createUI(composite);
		
		TreeViewer viewer = fieldUI.getSelectionViewer();
		getEditor().getSite().setSelectionProvider(viewer);		

		return composite;
	}

	
	/**
	 * refresh the field tabs when the selected Field changed in the field
	 * viewer.
	 * 
	 * @param field
	 */
	protected void refreshTabs(Field field) {
		if (field != null) {
			for (AbstractFieldTabControl control : tabControls) {
				control.setTabInput(field);
			}
		}
	}
	
   /**
    * get the selected Field from the Field Viewer.
    * @return
    */
	protected Field getSelectedField() {
		ISelection selction = fieldUI.getSelectionViewer().getSelection();
		if (selction != null && selction instanceof IStructuredSelection) {
			MdfModelElement property = (MdfModelElement) ((IStructuredSelection) selction).getFirstElement();
			return helper.getField(property);
		}
		return null;
	}

	/**
	 * @return
	 */
	public FormEditor getEditor() {
		return editor;
	}
	
	/**
	 * @return
	 */
	private Version getVersion() {
		return (Version) resource.getContents().get(0);
	}

	
	
	@Override
	public void initDataBindings(DataBindingContext context) {
	    //get the observer list of fields
	    MdfClass application = getVersion().getForApplication();
	    ((ApplicationTreeLabelProvider)fieldUI.getLabelProvider()).setExtendedApplication(application);
	    fieldUI.setInput(application);
	    fieldUI.setSelectedFields(getFieldProperties(application));

		for (AbstractVersionTabControl tc : tabControls) {
			tc.bindData();
		}
	}
	
	private List<MdfProperty> getFieldProperties(MdfClass application) {
		List<MdfProperty> properties = new ArrayList<MdfProperty>();
		List<Field> fields = getVersion().getFields();
		List<MdfProperty> propertyList = ApplicationFieldHelper.getAllProperties(application);
		for (Field field : fields) {
			MdfProperty prop = getMdfProperty(field, application, propertyList);
			if (prop != null) {
				properties.add(prop);
			} else {
				MdfAttributeWithRef attr =  new MdfAttributeWithRef();
				attr.setName(field.getName());
				attr.setRefObj(field); 
				properties.add(attr);
			}
		}
		return properties;
	}
	
	public MdfProperty getMdfProperty(Field field, MdfClass clazz, List<MdfProperty> propertyList) {
    	String fieldName = field.getName();
    	fieldName = fieldName.replace("_", ".");
    	for (MdfProperty property : propertyList) {
    		String name = T24Aspect.getT24Name(property);
    		if (name != null && name.equals(fieldName)) {
    			return property;
    		}
    	}
    	return null;
    } 

	/**
	 * update the Annotation Properties and Documentation Section.
	 * 
	 * @param field
	 */
	@SuppressWarnings("unused")
	private void updateSections(Field field) {
	    // clear the existing content before setting the new input.
	    annotationPropertyViewer.setInput(null);
	    documentationText.setText(StringUtils.EMPTY);
	    if (field != null) {
			MdfClass klass = getVersion().getForApplication();
			MdfProperty property = null;
			if (klass != null ) {
			    property = VersionUtils.getMdfProperty(field,klass);
			}
			if (property != null) {
			    List<?> annotationList = property.getAnnotations();
			    annotationPropertyViewer.setInput(annotationList);
			    String documentText = property.getDocumentation();
			    if (documentText != null) {
			    	documentationText.setText(documentText);
			    }
			}
	    }

	}	
	
	public Composite getControl() {
		return this;
	}
	
	/**
	 * 
	 */
	public void refreshControls() {		
		if (apiControl != null) {
			apiControl.refreshControls();
		}
	}

	public void clearFieldSelection() {
		fieldUI.getSelectionViewer().setSelection(null);
	}
}
