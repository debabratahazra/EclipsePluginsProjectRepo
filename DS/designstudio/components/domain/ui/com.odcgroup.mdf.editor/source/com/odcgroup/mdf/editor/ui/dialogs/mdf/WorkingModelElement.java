package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;


@SuppressWarnings("serial")
abstract class WorkingModelElement extends  MinimalEObjectImpl.Container implements MdfModelElement {

    private final MdfModelElement initialModel;

    public WorkingModelElement(MdfModelElement initialModel) {
        this.initialModel = initialModel;
    }
    
    public List getAnnotations() {
        return initialModel.getAnnotations();
    }

    public MdfAnnotation getAnnotation(String namespace, String name) {
        return initialModel.getAnnotation(namespace, name);
    }

    public List getAnnotations(String namespace) {
        return initialModel.getAnnotations(namespace);
    }


    public String getDocumentation() {
        return initialModel.getDocumentation();
    }

    public List getComments() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
    public Object getExtension(String namespaceURI) { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
    public Map getExtensions() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }

    public MdfName getQualifiedName() {
        return initialModel.getQualifiedName();
    }
}
