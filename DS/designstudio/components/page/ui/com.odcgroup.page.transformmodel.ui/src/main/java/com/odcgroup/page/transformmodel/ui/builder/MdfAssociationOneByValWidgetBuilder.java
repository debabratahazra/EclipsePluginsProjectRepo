package com.odcgroup.page.transformmodel.ui.builder;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.BEAN_PROPERTY_PREFIX;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ASSOCIATION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.INCLUDE_SOURCE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.INCLUDE_TYPE;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.INCLUDE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.page.metamodel.InclusionType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;

/**
 * Builds the Widgets for an MdfAssociation.
 * 
 * @author atr
 */
public class MdfAssociationOneByValWidgetBuilder extends AbstractMdfAssociationWidgetBuilder {

	/**
	 * Creates a new MdfAssociationOneByValWidgetBuilder.
	 * 
	 * @param modelElement The MdfModelElement to create
	 * @param parentWidget The parent Widget to which the child Widgets will be added
	 * @param builderContext The context
	 */
	public MdfAssociationOneByValWidgetBuilder(MdfAssociation modelElement, Widget parentWidget, WidgetBuilderContext builderContext) {
		super(modelElement, parentWidget, builderContext);
	}
	
    /**
     * Creates the Widgets. These Widgets may contain child Widgets.
     * 
     * @return List The newly created Widgets. These Widgets may contain child Widgets
     */
    public List<Widget> buildWidgets() {
    	
        List<Widget> widgets = new ArrayList<Widget>();
    	
		// Don't allow Associations to be added to Tables
		if (! shouldCreateColumn(getParentWidget())) {
        
	        MdfClass clazz = getAssociatedClass();
	        MdfName qName = clazz.getQualifiedName();
	        
	        Model model = new FragmentFinder(getOfsProject()).findFragment(qName, getCardinality());
	        if (model != null) {
		        Widget include = createWidget(INCLUDE);
		        include.findProperty(INCLUDE_SOURCE).setModel(model);
		        include.setPropertyValue(INCLUDE_TYPE, InclusionType.SOURCE_INCLUDE_LITERAL.toString());
		        include.setPropertyValue(DOMAIN_ASSOCIATION, getMdfAssociation().getName());
		        include.setPropertyValue(BEAN_PROPERTY_PREFIX, getBeanPropertyPrefix());
		        widgets.add(include);
	        } else {
	            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	            String msg = "No default fragment has been defined for this Domain Entity '{0}' with cardinality '{1}'"; //$NON-NLS-1$
	            Dialog dialog = new MessageDialog(shell, "Error", null, //$NON-NLS-1$
	            		MessageFormat.format(msg, new Object[]{qName.getQualifiedName(), getCardinality()}), 
	            				0, new String[]{"OK"}, 0); //$NON-NLS-1$
	            dialog.open();
	        }
		}
	        
        return widgets;         
    }	
	
    /**
     * Gets the value to use for the Property BeanPropertyPrefix.
     * 
     * @return String The value to use for the Property BeanPropertyPrefix
     */
    protected String getBeanPropertyPrefix() {
        return getMdfAssociation().getName() + "."; //$NON-NLS-1$
    }	
	
	/**
	 * Gets the cardinality of the association.
	 * 
	 * @return String Either PropertyTypeConstants.CARDINALITY_ONE or PropertyTypeConstants.CARDINALITY_MANY
	 */
	protected final String getCardinality() {
		return PropertyTypeConstants.CARDINALITY_ONE;
	}
}
