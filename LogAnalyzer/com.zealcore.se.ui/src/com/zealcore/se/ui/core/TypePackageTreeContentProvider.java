package com.zealcore.se.ui.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.model.IType;

public class TypePackageTreeContentProvider implements ITreeContentProvider {

    private Map<ITypePackage, Collection<IType>> typesByPackage;

    public TypePackageTreeContentProvider(
            final Map<ITypePackage, Collection<IType>> typesByPackage) {
        this.typesByPackage = typesByPackage;
    }

    public TypePackageTreeContentProvider() {
        this.typesByPackage = null;
    }

    @SuppressWarnings("unchecked")
    public Object[] getChildren(final Object parentElement) {
        if (parentElement instanceof ITypeRegistry) {
            ITypeRegistry reg = (ITypeRegistry) parentElement;
            return reg.getTypePackages();

        }
        if (parentElement instanceof Map) {
            final Map<Object, Object> localTypesByPackage = (Map<Object, Object>) parentElement;
            return localTypesByPackage.keySet().toArray();
        }
        if (parentElement instanceof ITypePackage) {
            final ArrayList<IType> searchableTypes = new ArrayList<IType>();
            final ITypePackage typePackage = (ITypePackage) parentElement;
            if (typesByPackage != null) {
                this.typesByPackage.get(typePackage);
                for (Iterator<IType> iterator = this.typesByPackage.get(
                        typePackage).iterator(); iterator.hasNext();) {
                    IType type = (IType) iterator.next();
                    searchableTypes.add(type);
                }

            } else {
                final Collection<IType> types = typePackage.getTypes();
                for (final IType type : types) {
                    if (type.isSearchable()) {
                        searchableTypes.add(type);
                    }
                }
            }
            return searchableTypes.toArray();
        }
        return new Object[0];
    }

    public Object getParent(final Object element) {
        return null;
    }

    public boolean hasChildren(final Object element) {
        return getChildren(element).length > 0;
    }

    public Object[] getElements(final Object inputElement) {
        return getChildren(inputElement);
    }

    public void dispose() {}

    public void inputChanged(final Viewer viewer, final Object oldInput,
            final Object newInput) {}
}
