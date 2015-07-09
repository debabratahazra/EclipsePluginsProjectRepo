package com.odcgroup.page.domain.ui.view;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.page.ui.PageUIConstants;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * @author atr
 */
public class DomainLabelProvider extends LabelProvider {


	/** The mdf domain image. */
	private static final String mdfDomain = "icons/obj16/mdf/domain.gif";

	/** The mdf association image. */
	private static final String mdfAssociation = "icons/obj16/mdf/assoc.gif";

	/** The mdf dataset image. */
	private static final String mdfDataset = "icons/obj16/mdf/dataset.gif";

	/** The mdf dataset property image */
	private static final String mdfDatasetProperty = "icons/obj16/mdf/attr.gif";

	/** The mdf dataset calculated property image */
	private static final String mdfDatasetDerivedProperty = "icons/obj16/mdf/sum.gif";
	private static final String PRIMITIVE_OVERLAY_IMAGE = "Primitive_Overlay_Image";
	private static final String ASSOCIATION_OVERLAY_IMAGE = "Association_Overlay_Image";
        static {
            ImageRegistry imageRegistry = PageUIPlugin.getDefault()
        	    .getImageRegistry();
            imageRegistry.put(
        	    PRIMITIVE_OVERLAY_IMAGE,
        	    overlayImage(PageUIPlugin.getImage(mdfDatasetProperty),
        		    PageUIPlugin.getImage(mdfDatasetDerivedProperty)));
            imageRegistry.put(
        	    ASSOCIATION_OVERLAY_IMAGE,
        	    overlayImage(PageUIPlugin.getImage(mdfAssociation),
        		    PageUIPlugin.getImage(mdfDatasetDerivedProperty)));
	}
	/**
	 * Gets the image descriptor. If the image name passed in as a parameter is
	 * null or empty returns the default icon's ImageDescriptor.
	 * 
	 * @param imageName
	 *            The name of the image
	 * @return ImageDescriptor The ImageDescriptor
	 */
	private static ImageDescriptor getImageDescriptor(String imageName) {
		if (StringUtils.isEmpty(imageName)) {
			imageName = PageUIConstants.DEFAULT_ICON;
		}
		return PageUIPlugin.getImageDescriptor(imageName);
	}

	

	/**
	 * @param base
	 * @param overlay
	 * @return image
	 */
	private static Image overlayImage(final Image base , final Image overlay ) {
		
	      CompositeImageDescriptor cid = new CompositeImageDescriptor() {
			@Override
			protected void drawCompositeImage(int width, int height) {
				drawImage(base.getImageData(), 0, 0);
				drawImage(overlay.getImageData(), 0, 0);
			}

			@Override
			protected Point getSize() {
				return new Point(base.getImageData().width, base.getImageData().height);
			}

		};
		return cid.createImage();
	}

	/**
	 * Returns the image for the label of the given element. The image is owned
	 * by the label provider and must not be disposed directly. Instead, dispose
	 * the label provider when no longer needed.
	 * 
	 * @param element
	 *            The element for which to provide the label image
	 * 
	 * @return the image used to label the element, or <code>null</code> if
	 *         there is no image for the given object
	 */
	public Image getImage(Object element) {
		Image image = null;
		if (element instanceof MdfDomain) {
			image = PageUIPlugin.getImage(mdfDomain);
		} else if (element instanceof MdfDataset) {
			image = PageUIPlugin.getImage(mdfDataset);
		} else if (element instanceof MdfDatasetProperty) {
			MdfEntity type = ((MdfDatasetProperty) element).getType();
			if (type instanceof MdfPrimitive) {
				image = PageUIPlugin.getImage(mdfDatasetProperty);
			} else {
				image =PageUIPlugin.getImage(mdfAssociation);
			}
			if (element instanceof MdfDatasetDerivedProperty) {
			    image = getOverlayImage(type);
			}
		}
		return image;
	}
        /**
         * Get the Overly Image for the Given Type
         * @param type
         * @return Image
         */
	private Image getOverlayImage(MdfEntity type) {
	    if(type instanceof MdfPrimitive) {
		return PageUIPlugin.getImage(PRIMITIVE_OVERLAY_IMAGE);
	    }else {
		return PageUIPlugin.getImage(ASSOCIATION_OVERLAY_IMAGE);
	    }
	}



	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		
        StringBuffer text = new StringBuffer();
        
        MdfModelElement mme = (MdfModelElement)element;

        if (mme == null) {
            return text.toString();
        }

        if (mme instanceof MdfDatasetProperty) {
            MdfDatasetProperty prop = (MdfDatasetProperty) mme;
            if (prop.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
                text.append("[1-1] ");
            } else {
                text.append("[0-n] ");
            }
        } else if (mme instanceof MdfProperty) {
            MdfProperty prop = (MdfProperty) mme;
            if (prop.isRequired()) {
                text.append("[1");
            } else {
                text.append("[0");
            }
            text.append("..");
            boolean many = prop.getMultiplicity() > 0;
            if (many) {
                text.append("n] ");
            } else {
                text.append("1] ");
            }
        }

        text.append(mme.getName());

        if (mme instanceof MdfAttribute) {
            MdfAttribute attribute = (MdfAttribute) mme;
            text.append(" (");
            text.append(attribute.getType().getQualifiedName().toString());
            text.append(")");
        } else if (mme instanceof MdfAssociation) {
            MdfEntity entity = ((MdfAssociation) mme).getType();
            text.append(" (");
            text.append(entity.getQualifiedName());
            text.append(" )");
        } else if (mme instanceof MdfDatasetProperty) {
            MdfDatasetProperty dp = (MdfDatasetProperty) mme;
            MdfEntity entity = dp.getType();
            if (entity != null) {
            	text.append(" (");
            	text.append(entity.getQualifiedName());
            	text.append(" )");
        	} else {
            	text.append(" ( ??? )");
        	}
        }

        return text.toString();

	}

	/**
	 */
	public DomainLabelProvider() {
	}
	
	@Override
	public void dispose() {
	    // TODO Auto-generated method stub
	    super.dispose();
	}

}