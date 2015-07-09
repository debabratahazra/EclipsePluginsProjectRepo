package com.odcgroup.page.transformmodel.ui.builder;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.model.Widget;

/**
 * Builds the Widgets for an MdfAssociation.
 * 
 * @author atr
 */
abstract public class AbstractMdfAssociationWidgetBuilder extends AbstractWidgetBuilder {

	/**
	 * Creates a new MdfAssociationWidgetBuilder.
	 * 
	 * @param modelElement The MdfModelElement to create
	 * @param parentWidget The parent Widget to which the child Widgets will be added
	 * @param builderContext The context
	 */
	public AbstractMdfAssociationWidgetBuilder(MdfAssociation modelElement, Widget parentWidget, WidgetBuilderContext builderContext) {
		super(modelElement, parentWidget, builderContext);
		
		MdfEntity type = getMdfAssociation().getType();
		if (type == null) {
			StringBuffer msg = new StringBuffer();
			msg.append("The association type cannot be null: see definitions of ["); //$NON-NLS-1$
			msg.append(modelElement.getQualifiedName().getQualifiedName());
			msg.append("]"); //$NON-NLS-1$
			throw new PageException(msg.toString());			
		}
	}
	
	/**
	 * Gets the MdfModelElement as an MdfAssociation.
	 * 
	 * @return MdfAssociation
	 */ 	
	protected MdfAssociation getMdfAssociation() {
		return (MdfAssociation) getModelElement();
	}
	
	/**
	 * Gets the MdfClass at the other end of the association.
	 * 
	 * @return MdfClass
	 */ 	
	protected MdfClass getAssociatedClass() {
		return (MdfClass) getMdfAssociation().getType();
	}	
}