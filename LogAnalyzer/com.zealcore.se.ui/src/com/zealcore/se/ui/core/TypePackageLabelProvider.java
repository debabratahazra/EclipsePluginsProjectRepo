package com.zealcore.se.ui.core;

import org.eclipse.jface.viewers.LabelProvider;

import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.model.IType;

public class TypePackageLabelProvider extends LabelProvider {
    @Override
    public String getText(final Object element) {
        if (element instanceof ITypePackage) {
            final ITypePackage typePackage = (ITypePackage) element;
            return typePackage.toString();
        } else if (element instanceof IType) {
            final IType type = (IType) element;
            return type.getName();
        }
        return super.getText(element);
    }
}
