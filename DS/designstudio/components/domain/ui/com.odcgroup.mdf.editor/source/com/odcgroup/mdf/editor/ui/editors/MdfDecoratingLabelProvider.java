package com.odcgroup.mdf.editor.ui.editors;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelDecorator;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.mdf.metamodel.MdfReverseAssociationWrapper;

/**
 * @author pkk
 *
 */
public class MdfDecoratingLabelProvider extends DecoratingLabelProvider {
	
	private ILabelDecorator decorator;

	/**
	 * @param provider
	 * @param decorator
	 */
	public MdfDecoratingLabelProvider(ILabelProvider provider,
			ILabelDecorator decorator) {
		super(provider, decorator);
		this.decorator = decorator;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DecoratingLabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof MdfReverseAssociationWrapper) {
			String label = ((MdfReverseAssociationWrapper)element).getName();
			if (decorator instanceof LabelDecorator) {
				LabelDecorator ld2 = (LabelDecorator) decorator;
	            String decorated = ld2.decorateText(label, element, getDecorationContext());
	            if (decorated != null) {
	                return decorated;
	            }
			}
			return label;
		}
		return super.getText(element);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DecoratingLabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
        if (element instanceof MdfReverseAssociationWrapper) {
        	return super.getImage(((MdfReverseAssociationWrapper)element).getInnerReverse());
        }
        return super.getImage(element);
    }

}
