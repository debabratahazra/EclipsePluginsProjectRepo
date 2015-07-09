package com.odcgroup.mdf.editor.ui.providers.decorators;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;


public class PrimaryKeyDecorator implements ILightweightLabelDecorator {

    private static final ImageDescriptor KEY;

    static {
        KEY = MdfPlugin.getImageDescriptor(MdfCore.ICON_KEY);
    }

    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof MdfAttribute) {
            MdfAttribute model = (MdfAttribute) element;

            if (model.isPrimaryKey()) {
                decoration.addOverlay(KEY, IDecoration.TOP_LEFT);
            }
        }
        if (element instanceof MdfAssociation) {
        	MdfAssociation model = (MdfAssociation) element;

            if (model.isPrimaryKey()) {
                decoration.addOverlay(KEY, IDecoration.TOP_LEFT);
            }
        }
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }

}
