package com.odcgroup.mdf.editor.ui.providers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public abstract class MdfBaseProvider implements ILabelProvider,
        IPropertyChangeListener {
    private static final String EMPTY_VALUE = "";
    private static final DescriptorRegistry registry = new DescriptorRegistry();

    static {
        registry.add(MdfModelElement.class, new PropertyDescriptor("name", "Name"));
        registry.add(MdfModelElement.class, new PropertyDescriptor("documentation", "Documentation"));

        registry.add(MdfDomain.class, new PropertyDescriptor("namespace", "Namespace"));
        registry.add(MdfDomain.class, new PropertyDescriptor("version", "Version"));

        registry.add(MdfClass.class, new PropertyDescriptor("abstract", "Abstract"));
        registry.add(MdfClass.class, new PropertyDescriptor("baseClass", "Superclass"));
        registry.add(MdfClass.class, new PropertyDescriptor("referenceable", "Referenceable"));

        registry.add(MdfProperty.class, new PropertyDescriptor("type", "Type"));
        registry.add(MdfProperty.class, new PropertyDescriptor("required", "Required"));
        registry.add(MdfProperty.class, new PropertyDescriptor("multiplicity", "Cardinality"));
        registry.add(MdfAttribute.class, new PropertyDescriptor("businessKey", "Business key"));

        registry.add(MdfAssociation.class, new PropertyDescriptor("containment", "Containment"));

        registry.add(MdfAttribute.class, new PropertyDescriptor("default", "Default"));

        registry.add(MdfEnumValue.class, new PropertyDescriptor("value", "Value"));

        registry.add(MdfDataset.class, new PropertyDescriptor("baseClass", "Base class"));

        registry.add(MdfDatasetProperty.class, new PropertyDescriptor("path", "Path"));
        registry.add(MdfDatasetProperty.class, new PropertyDescriptor("multiplicity", "Cardinality"));
        registry.add(MdfDatasetProperty.class, new PropertyDescriptor("type", "Type"));
        registry.add(MdfDatasetProperty.class, new PropertyDescriptor("unique", "Unique"));
    }

    private Vector listeners = new Vector();
    private boolean showElementsCardinality = true;
    private boolean showElementsCardinalityForced = false;
    private boolean showElementsType = true;
    private boolean showElementsTypeForced = false;

    public MdfBaseProvider() {
        IPreferenceStore store = MdfPlugin.getDefault().getPreferenceStore();

        showElementsCardinality = store
                .getBoolean(MdfPreferencePage.P_SHOW_CARDINALITIES);
        showElementsType = store.getBoolean(MdfPreferencePage.P_SHOW_TYPE);

        store.addPropertyChangeListener(this);
    }

    public String getColumnText(Object item, int columnIndex) {
        List descs = registry.getPropertyDescriptors(item.getClass());

        if (descs != null) {
            String pname = (String) ((IPropertyDescriptor) descs
                    .get(columnIndex)).getId();
            Object value = registry.getProperty(item, pname);
            return ((value == null) ? EMPTY_VALUE : value.toString());
        } else {
            return EMPTY_VALUE;
        }
    }

    public Image getImage(Object item) {
        return MdfUtility.getImage(item);
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public IPropertyDescriptor[] getPropertyDescriptors(Class klass) {
        Collection descs = registry.getPropertyDescriptors(klass);

        if (descs != null) {
            return (IPropertyDescriptor[]) descs
                    .toArray(new IPropertyDescriptor[descs.size()]);
        } else {
            return new IPropertyDescriptor[0];
        }
    }

    public void setPropertyValue(Object obj, String property, Object value) {
    }

    public Object getPropertyValue(Object obj, String pname) {
        if ((obj instanceof MdfModelElement) && (pname.equals("documentation"))) {
            return ((MdfModelElement) obj).getDocumentation();
        }

        Object value = registry.getProperty(obj, pname);

        if (value == null) {
            value = EMPTY_VALUE;
        } else if (value instanceof MdfPrimitive) {
            value = ((MdfPrimitive) value).getQualifiedName();
        } else if (pname.equals("containment")) {
            value = MdfContainment.get(((Integer) value).intValue());
        } else if (pname.equals("multiplicity")) {
            value = MdfMultiplicity.get(((Integer) value).intValue());
        }

        return value;
    }

    /**
     * Turns on/off the display of the elements' cardinality where applicable
     *
     * @param showElementsCardinality <code>true</code> to turn on cardinality
     *            display
     */
    public void setShowElementsCardinality(boolean showElementsCardinality) {
        showElementsCardinalityForced = true;
        this.showElementsCardinality = showElementsCardinality;
    }

    /**
     * @return Tells wether elements cardinality will be shown or not
     */
    public boolean getShowElementsCardinality() {
        return showElementsCardinality;
    }

    /**
     * Turns on/off the display of the elements' type where applicable
     *
     * @param showElementsType <code>true</code> to turn on type display
     */
    public void setShowElementsType(boolean showElementsType) {
        showElementsTypeForced = true;
        this.showElementsType = showElementsType;
    }

    /**
     * @return Tells wether elements type will be shown or not
     */
    public boolean getShowElementsType() {
        return showElementsType;
    }

    public String getText(Object item) {
    	if(item == null){
    		return new String();
    	}
        if (item instanceof MdfModelElement) {
            return ((MdfModelElement) item).getName();
        } else if (item instanceof MdfProject) {
            return ((MdfProject) item).getName();
        } else if (item instanceof Resource) {
            URI uri = ((Resource) item).getURI();
            return uri.isFile() ? uri.lastSegment() : uri.toString();
        }
        return item.toString();
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
        listeners.add(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getProperty().equals(MdfPreferencePage.P_SHOW_CARDINALITIES)
                && !showElementsCardinalityForced) {
            showElementsCardinality = ((Boolean) event.getNewValue())
                    .booleanValue();
            notifyListeners();
        }

        if (event.getProperty().equals(MdfPreferencePage.P_SHOW_TYPE)
                && !showElementsTypeForced) {
            showElementsType = ((Boolean) event.getNewValue()).booleanValue();
            notifyListeners();
        }
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        Iterator it = listeners.iterator();

        while (it.hasNext()) {
            ILabelProviderListener listener = (ILabelProviderListener) it
                    .next();
            listener.labelProviderChanged(new LabelProviderChangedEvent(this));
        }
    }
}
