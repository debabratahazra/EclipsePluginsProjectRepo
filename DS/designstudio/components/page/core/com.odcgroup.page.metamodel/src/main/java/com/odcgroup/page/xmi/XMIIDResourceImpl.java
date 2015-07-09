package com.odcgroup.page.xmi;

import org.eclipse.emf.common.notify.impl.NotifyingListImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.FeatureNotFoundException;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;

/**
 * The XMIIDResourceImpl overrides the XMLResourceImpl#useUUIDs() method to return true. This is because by default the
 * XMIResourceImpl uses indexed values instead of unique id's. The problem is that, if we add a new element before an
 * existing one or delete an element this potentially breaks all the existing links.
 * 
 * @author Gary Hayes
 */
public class XMIIDResourceImpl extends XMIResourceImpl {

    /**
     * Creates a new XMIIDResourceImpl.
     */
    public XMIIDResourceImpl() {
        super();
    }

    /**
     * Creates a new XMIIDResourceImpl.
     * 
     * @param uri The URI
     */
    public XMIIDResourceImpl(URI uri) {
        super(uri);
    }

    /**
     * Overridden to return true.
     * 
     * @return booleantrue
     */
    protected boolean useUUIDs() {
        return true;
    }

    /** 
     * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl#createXMLHelper()
     */
    @Override
	protected XMLHelper createXMLHelper() {
		return new MyXMLHelper(this);
	}

    /**
     * Gets the errors.
     * 
     * @return EList of Diagnostic's
     */
    public EList<Diagnostic> getErrors() {
        if (errors == null) {
            errors = new FilteredNotifyingListImpl<Diagnostic>();
        }
        return errors;
    }

	/**
     * A List which filters out errors and warnings which are not important. These errors occur because old Page
     * Designer files are being used in a newer version of the Page Designer.
     * 
     * @param <E>
     * 
     * @author Gary Hayes
     */
    private class FilteredNotifyingListImpl<E> extends NotifyingListImpl {

        /** The serial version id. */
        private static final long serialVersionUID = 1L;

        /**
         * Returns true if notification is required.
         * 
         * @return boolean
         */
        protected boolean isNotificationRequired() {
            return XMIIDResourceImpl.this.eNotificationRequired();
        }

        /**
         * Gets the notifier.
         * 
         * @return Object
         */
        public Object getNotifier() {
            return XMIIDResourceImpl.this;
        }

        /**
         * Gets the feature id.
         * 
         * @return int
         */
        public int getFeatureID() {
            return RESOURCE__ERRORS;
        }

        /**
         * Adds an error / warning to the List.
         * 
         * @param object The object to add
         */
        public void addUnique(Object object) {
            if (object instanceof FeatureNotFoundException) {
                FeatureNotFoundException fnfe = (FeatureNotFoundException) object;
                if (filterI18nProperty(fnfe)) {
                    return;
                }
            }
            super.addUnique(object);
        }
        
        /**
         * Filters the property i18n from the EObject of type Property.
         * 
         * @param fnfe The FeatureNotFoundException
         * @return boolean True if this property should be filtered
         */
        private boolean filterI18nProperty(FeatureNotFoundException fnfe) {
            EObject obj = fnfe.getObject();
            if (obj.eClass().getName().equals("Property")) {
                if (fnfe.getName().equals("i18n")) {
                    return true;
                }
            }
            
            return false;
        }

    }
    
    /**
     * @author atr
     *
     */
    private class MyXMLHelper extends XMLHelperImpl {
    	
		/**
		 * @see org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl#setValue(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int)
		 */
		@Override
  	    public void setValue(EObject object, EStructuralFeature feature, Object value, int position) {
			EClass cls = object.eClass();
			
			if ("Property".equals(cls.getName())) {
				EStructuralFeature sf = cls.getEStructuralFeature("typeName");
				if (sf != null) {
					Object obj = object.eGet(sf);
					if (obj != null && ("horizontalAlignment".equals(obj) || "horizontalTextPosition".equals(obj))) {
						sf = cls.getEStructuralFeature("value");
						if (sf != null) {
							obj = object.eGet(sf);
							if (obj instanceof String) {
								String align = (String)obj;
								if ("left".equals(align)) {
									object.eSet(sf, "lead");
								} else if ("right".equals(align)) {
									object.eSet(sf, "trail");
								} else {
									int i = 0;
								}
							}
						}
					} else if (obj != null && ("verticalAlignment".equals(obj) || "verticalTextPosition".equals(obj))) {
						sf = cls.getEStructuralFeature("value");
						if (sf != null) {
							obj = object.eGet(sf);
							if (obj instanceof String) {
								String align = (String)obj;
								if ("top".equals(align)) {
									object.eSet(sf, "lead");
								} else if ("bottom".equals(align)) {
									object.eSet(sf, "trail");
								} else {
									int i = 0;
								}
							}
						}
					}
				}
			}
			super.setValue(object, feature, value, position);
		}

		/**
		 * 
		 */
		public MyXMLHelper() {
			super();
		}

		/**
		 * @param resource
		 */
		public MyXMLHelper(XMLResource resource) {
			super(resource);
		}
		
    }
}