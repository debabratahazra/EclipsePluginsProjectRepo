package com.odcgroup.mdf.compare;

import java.util.Iterator;

import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.MoveModelElement;
import org.eclipse.emf.compare.diff.metamodel.impl.AbstractDiffExtensionImpl;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.metamodel.MdfEnumValue;

/**
 * @author pkk
 *
 */
public class MdfAssociationExtension extends AbstractDiffExtensionImpl {
	
	/**
	 * 
	 */
	public MdfAssociationExtension() {
		super();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.metamodel.impl.AbstractDiffExtensionImpl#visit(org.eclipse.emf.compare.diff.metamodel.DiffModel)
	 */
	public void visit(DiffModel diffModel) {
		final Iterator<EObject> iterator = diffModel.eAllContents();
		while(iterator.hasNext()){
			final DiffElement element = (DiffElement) iterator.next();
			// hide the move elements, as sorting in MDF does tons of moves
			// only consider the move of MdfEnumValue
			if(element instanceof MoveModelElement){
				MoveModelElement mElement = (MoveModelElement) element;
				if(!(mElement.getRightElement() instanceof MdfEnumValue)){
					getHideElements().add(element);
				} else {
					// mdfEnumValue - allowed to change the order, so consider the order changes
					MdfEnumValueImpl valr = (MdfEnumValueImpl) mElement.getRightElement();
					MdfEnumerationImpl enumr = (MdfEnumerationImpl)valr.eContainer();
					MdfEnumValueImpl vall = (MdfEnumValueImpl) mElement.getLeftElement();
					MdfEnumerationImpl enuml = (MdfEnumerationImpl)vall.eContainer();
					if (enumr.getValues().indexOf(valr)== enuml.getValues().indexOf(vall)){
						getHideElements().add(element);
					}
				}
			}
		}
	}
	
	

}
