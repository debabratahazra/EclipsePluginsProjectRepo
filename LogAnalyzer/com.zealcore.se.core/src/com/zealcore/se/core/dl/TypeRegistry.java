package com.zealcore.se.core.dl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.zealcore.se.core.AbstractExtensionVisitor;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;

public final class TypeRegistry implements ITypeRegistry {

    private static final String A_REGISTRED_TYPE_CLASS_MUST_BE_SPECIFIED = "A registred type-class must be specified";

    public static final String POINT_ID = "com.zealcore.se.core.typepackages";

    /* maps a qualified class name to a MapEntry(ITypePackage,Class) */
    private final Map<String, MapEntry> typeMap = new HashMap<String, MapEntry>();

    /* the set of registrered type packages */
    private final Set<ITypePackage> packages = new HashSet<ITypePackage>();

    /* singleton Typeregistry */
    private static ITypeRegistry instance;

    /**
     * Creats a new empty TypeRegistry for use in test mode.
     * 
     * @param testMode
     *                the test mode
     */
    TypeRegistry(final String testMode) {

    }

    private TypeRegistry() {

        final String pointId = "typepackages";

        try {
            new AbstractExtensionVisitor(pointId) {
                @Override
                protected void visit(final IConfigurationElement element)
                        throws CoreException {

                    final Object object = element
                            .createExecutableExtension("class");
                    if (object instanceof ITypePackage) {
                        final ITypePackage typePackage = (ITypePackage) object;
                        TypeRegistry.this.add(typePackage);
                    } else {
                        // TODO Check if there is a more logical exception to
                        // throw
                        throw new IllegalArgumentException("The element "
                                + element + " does not implement "
                                + ITypePackage.class.getName());
                    }

                }

            }.run();
        } catch (final CoreException e) {
            // TODO Consider logging exception rather than wrapping it
            SeCorePlugin.logError(e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.ITypeRegistry#getType(java.lang.String)
     */
    public IType getType(final String qualifiedName) {
        final MapEntry mapEntry = getMapEntry(qualifiedName);

        if (mapEntry == null || mapEntry.type == null) {
            throw new IllegalArgumentException(qualifiedName + " "
                    + TypeRegistry.A_REGISTRED_TYPE_CLASS_MUST_BE_SPECIFIED);

        }
        return mapEntry.type;
    }

    private MapEntry getMapEntry(final String typeId) {
        MapEntry mapEntry = this.typeMap.get(typeId);
        if (mapEntry == null) {
            for (final ITypePackage pkg : getTypePackages()) {
                for (final IType newType : pkg.getTypes()) {
                    addType(pkg, newType);
                }
            }
            mapEntry = this.typeMap.get(typeId);
        }
        return mapEntry;
    }

    /**
     * Clients should NOT call this method
     * 
     * @param typePackage
     *                the type package to add
     * @return true if successfully added
     */
    boolean add(final ITypePackage typePackage) {
        if (typePackage == null) {
            throw new NullPointerException();
        }

        if (!this.packages.add(typePackage)) {
            return false;
        }

        for (final IType type : typePackage.getTypes()) {

            if (!type.isA(ReflectiveType.valueOf(IObject.class))) {
                throw new IllegalArgumentException(
                        "The type "
                                + type
                                + " is not any of the model CLASSES (Maybe it is a Type.isA implementation error?)");
            }
            addType(typePackage, type);
        }

        return true;
    }

    private void addType(final ITypePackage typePackage, final IType type) {
        if (this.typeMap.containsKey(type.getId())) {
            return;
        }
        final MapEntry entry = new MapEntry(typePackage, type);
        this.typeMap.put(type.getId(), entry);
    }

    public static ITypeRegistry getInstance() {
        if (TypeRegistry.instance == null) {
            TypeRegistry.instance = new TypeRegistry();
        }
        return TypeRegistry.instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.ITypeRegistry#getTypePackage(java.lang.Class)
     */
    public ITypePackage getTypePackage(final IType type) {

        final MapEntry entry = getMapEntry(type.getId());
        if (entry == null) {
            throw new IllegalArgumentException(
                    TypeRegistry.A_REGISTRED_TYPE_CLASS_MUST_BE_SPECIFIED
                            + ", Type=" + type);
        }
        return entry.provider;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.ITypeRegistry#getTypePackages()
     */
    public ITypePackage[] getTypePackages() {
        final Set<ITypePackage> temp = new HashSet<ITypePackage>();
        try {
            final String pointId = "typepackages";
            new AbstractExtensionVisitor(pointId) {
                @Override
                protected void visit(final IConfigurationElement element)
                        throws CoreException {

                    final Object object = element
                            .createExecutableExtension("class");
                    if (object instanceof ITypePackage) {
                        final ITypePackage typePackage = (ITypePackage) object;
                        temp.add(typePackage);
                    }

                }

            }.run();
        } catch (final CoreException e) {
            // TODO Consider logging exception rather than wrapping it
            SeCorePlugin.logError(e);
        }
        return temp.toArray(new ITypePackage[0]);
    }

    private static final class MapEntry {
        private final ITypePackage provider;

        private final IType type;

        private MapEntry(final ITypePackage provider, final IType type) {
            super();
            this.provider = provider;
            this.type = type;
        }
    }
}
