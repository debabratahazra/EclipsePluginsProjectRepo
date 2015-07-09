package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangeListener;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.ext.constraints.ConstraintsAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;


/**
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 * @version 1.0
 */
public class MdfBusinessTypePage extends MdfModelPage implements
        ModelChangeListener {

    private Map cdefs = new HashMap();
    private EntitySelector typeSelector;
    private final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {

        public MdfModelElement[] getCandidates() {
            return getCandidateTypes();
        }

        public String getDefaultDomainName() {
            return MdfUtility.getDomain(initialType).getName();
        }
    };

    private final MdfBusinessType initialType;
    private Text typeNameText;

    public MdfBusinessTypePage(MdfBusinessType type) {
        super(type);
        setTitle("Business Type");
        setDescription("Edits the core properties of the type.");
        this.initialType = type;

        setWorkingCopy(new WorkingBusinessType());
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        factory.createLabel(container, "&Name:");

        typeNameText = factory.createText(container, null);
        typeNameText.setEnabled(getEditMode() != MODE_EDIT);
        typeNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        factory.createLabel(container, "Base &Type:");
        typeSelector = new EntitySelector(container, factory,
                contentAssistProvider,
                MdfUtility.getDomain(initialType).getName());
        typeSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        {
            Composite constraints = factory.createComposite(container);
            GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.horizontalSpan = 2;
            constraints.setLayoutData(gridData);
            constraints.setLayout(new GridLayout(2, true));

            Group lengthConstraints = factory.createGroup(constraints,
                    "Constraints on &String");
            lengthConstraints.setLayoutData(new GridData(GridData.FILL_BOTH
                    | GridData.VERTICAL_ALIGN_BEGINNING));
            lengthConstraints.setLayout(new GridLayout(2, false));

            Group valueConstraints = factory.createGroup(constraints, "Constraints on &Decimal");
            valueConstraints.setLayoutData(new GridData(GridData.FILL_BOTH
                    | GridData.VERTICAL_ALIGN_BEGINNING));
            valueConstraints.setLayout(new GridLayout(2, false));

            cdefs.put(ConstraintsAspect.MAX_LENGTH, new ConstraintDefinition(
                    lengthConstraints, "Maximum Characters:") {

                public IStatus getStatus() {
                    try {
                        Integer.parseInt(getValue());
                    } catch (NumberFormatException e) {
                        return MdfCore.newStatus(
                                "The maximum length must be a valid integer",
                                IStatus.ERROR);
                    }

                    return Status.OK_STATUS;
                }
            });
            cdefs.put(ConstraintsAspect.PATTERN, new ConstraintDefinition(
                    lengthConstraints, "Pattern:") {

                public IStatus getStatus() {
                    try {
                        Pattern.compile(getValue());
                    } catch (PatternSyntaxException e) {
                        return MdfCore.newStatus(
                                "The value pattern must be a valid regular expression",
                                IStatus.ERROR);
                    }

                    return Status.OK_STATUS;
                }
            });
            
            cdefs.put(ConstraintsAspect.PRECISION, 
            		new ConstraintDefinition(valueConstraints,
                        "Total Digits:") {

                public IStatus getStatus() { 
                	try {
	                    Integer.parseInt(getValue());
	                } catch (NumberFormatException e) {
	                    return MdfCore.newStatus(
	                            "Total Digists must be a valid integer",
	                            IStatus.ERROR);
	                }/*
                    if (!Pattern.matches("[0-9]+(,[0-9]+)?", getValue())) {
                        return MdfCore.newStatus(
                                "Precision and scale must have the form p,s",
                                IStatus.ERROR);
                    }*/

                    return Status.OK_STATUS;
                }
            });
            
            cdefs.put(ConstraintsAspect.SCALE, 
            		new ConstraintDefinition(valueConstraints,
                        "Fractional Digits:") {
            	 public IStatus getStatus() {
            		 try {
 	                    Integer.parseInt(getValue());
 	                } catch (NumberFormatException e) {
 	                    return MdfCore.newStatus(
 	                            "Fractional Digists must be a valid integer",
 	                            IStatus.ERROR);
 	                }
 	                return Status.OK_STATUS;
                 }
            });
        }

        initialize();
        updateStatus();
        registerListeners();

        setControl(container);
        MdfProjectRepository.addModelChangeListener(this);
    }

    public void dispose() {
        MdfProjectRepository.removeModelChangeListener(this);
    }

    public void doSave(MdfModelElement model) {
    	if (!(model instanceof MdfBusinessType)) {
    		return;
    	}
        MdfBusinessTypeImpl type = (MdfBusinessTypeImpl) model;

        ModelFactory.INSTANCE.copy((MdfBusinessType) workingCopy, type);

        if (getEditMode() == MODE_CREATE) {
            MdfDomainImpl domain = (MdfDomainImpl) type.getParentDomain();
            domain.getBusinessTypes().add(type);
        }

        ModelFactory.INSTANCE.removeAnnotations(type,
                ConstraintsAspect.NAMESPACE_URI);

        boolean hasConstraints = false;
        MdfAnnotation annotation = ModelFactory.INSTANCE.createMdfAnnotation(
                ConstraintsAspect.NAMESPACE_URI, ConstraintsAspect.CONSTRAINTS);

        Iterator it = cdefs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ConstraintDefinition cdef = (ConstraintDefinition) entry.getValue();

            if (cdef.isChecked()) {
                MdfAnnotationProperty property = ModelFactory.INSTANCE.createMdfAnnotationProperty(
                        annotation, (String) entry.getKey(), cdef.getValue());
                ModelFactory.INSTANCE.addAnnotationProperty(annotation,
                        property);
                hasConstraints = true;
            }
        }

        if (hasConstraints) {
            ModelFactory.INSTANCE.addAnnotation(type, annotation);
        }

    }
    
    /**
     * (non-Javadoc)
     * @see com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfModelPage#updateStatus(boolean)
     */
    protected void updateStatus(boolean copy) {
        Iterator it = cdefs.values().iterator();
        while (it.hasNext()) {
            ConstraintDefinition cdef = (ConstraintDefinition) it.next();
            if (cdef.isChecked()) {
                IStatus status = cdef.getStatus();
                if (!status.isOK()) {
                    setStatus(status);
                    return;
                }
            }
        }

        super.updateStatus(copy);
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelChangeListener#onModelChanged(com.odcgroup.mdf.editor.model.ModelChangedEvent)
     */
    public void onModelChanged(ModelChangedEvent event) {
    }

    protected void registerListeners() {
        typeNameText.addModifyListener(this);
        typeSelector.addModifyListener(this);

        Iterator it = cdefs.values().iterator();
        while (it.hasNext()) {
            ConstraintDefinition cdef = (ConstraintDefinition) it.next();
            cdef.addModifyListener(this);
        }
    }

    private MdfModelElement[] getCandidateTypes() {
        // -- Add primitives and business types only, not enumerations
        List candidates = new ArrayList();

        Iterator it = PrimitivesDomain.DOMAIN.getPrimitives().iterator();

        while (it.hasNext()) {
            MdfEntity entity = (MdfEntity) it.next();
            if (entity instanceof MdfPrimitive) {
                candidates.add(entity);
            }
        }

        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }

    /**
     * Tests if the current workbench selection is a suitable projectCombo to
     * use.
     */
    private void initialize() {
        setText(typeNameText, initialType.getName());

        if (initialType.getType() == null) {
            typeSelector.setSelection(MdfUtility.DEFAULT_BUSSTYPE_TYPE.getQualifiedName());
        } else {
            typeSelector.setSelection(initialType.getType());
        }

        MdfAnnotation annot = ConstraintsAspect.getConstraints(initialType);
        Iterator it = cdefs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ConstraintDefinition cdef = (ConstraintDefinition) entry.getValue();

            MdfAnnotationProperty p = null;
            if (annot != null) p = annot.getProperty((String) entry.getKey());

            if (p != null) {
                cdef.setValue(p.getValue());
            } else {
                cdef.setValue("");
            }
        }
    }

    private class WorkingBusinessType extends WorkingModelElement implements
            MdfBusinessType {

        public WorkingBusinessType() {
            super(initialType);
        }

        public String getName() {
            return typeNameText.getText().trim();
        }

        public MdfDomain getParentDomain() {
            return initialType.getParentDomain();
        }

        public MdfPrimitive getType() {
            return MdfEcoreUtil.getMdfPrimitiveProxy(typeSelector.getSelection());
        }

        public boolean isValidValue(String value) {
            return initialType.isValidValue(value);
        }

        public boolean isPrimitiveType() {
            return initialType.isPrimitiveType();
        }

		// TODO to be removed at the end of the mdf-api/core split
		public boolean isDeprecated() { return false; }
		public EObject getDeprecationInfo() { return null; }
    }

    public abstract class ConstraintDefinition {

        private Text value;
        private Vector listeners = new Vector();

        public ConstraintDefinition(Composite parent, String label) {
            WidgetFactory factory = getWidgetFactory();
            factory.createLabel(parent, label);
            value = factory.createText(parent, "");
            value.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            value.addModifyListener(new ModifyListener() {

                public void modifyText(ModifyEvent e) {
                    notifyListeners(e);
                }
            });
        }

        public boolean isChecked() {
            return value.getText().trim().length() > 0;
        }

        public String getValue() {
            return value.getText();
        }

        public void setValue(String value) {
            this.value.setText(value);
        }

        public abstract IStatus getStatus();

        protected void notifyListeners(TypedEvent orig) {
            Event e = new Event();
            e.widget = orig.widget;
            e.display = orig.display;
            e.time = orig.time;
            ModifyEvent mod = new ModifyEvent(e);

            Iterator it = listeners.iterator();
            while (it.hasNext()) {
                ModifyListener listener = (ModifyListener) it.next();
                listener.modifyText(mod);
            }
        }

        public boolean addModifyListener(ModifyListener listener) {
            return listeners.add(listener);
        }

        public boolean removeModifyListener(ModifyListener listener) {
            return listeners.remove(listener);
        }
    }
}
