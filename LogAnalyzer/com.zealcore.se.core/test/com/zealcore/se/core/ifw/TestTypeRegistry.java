package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.AbstractTypePackage;
import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IType;

public class TestTypeRegistry implements ITypeRegistry {

    private final List<ITypePackage> packages = new ArrayList<ITypePackage>();

    private final TestTypePackage dummy = new TestTypePackage();

    public IType getType(final String qualifiedName) {
        for (final ITypePackage pkg : packages) {
            for (final IType iType : pkg.getTypes()) {
                if (iType.getId().equals(qualifiedName)) {
                    return iType;
                }
            }
        }
        return null;
    }

    public ITypePackage getTypePackage(final IType type) {
        for (final ITypePackage pkg : packages) {
            if (pkg.getTypes().contains(type)) {
                return pkg;
            }
        }
        return dummy;
    }

    public ITypePackage[] getTypePackages() {
        return packages.toArray(new ITypePackage[packages.size()]);
    }

    public void addPackage(final ITypePackage pkg) {
        packages.add(pkg);
    }

    public static class TestTypePackage extends AbstractTypePackage {

        private final List<IType> types = new ArrayList<IType>();

        public Collection<IType> getTypes() {
            return types;
        }

        public void addType(final IType type) {
            types.add(type);
        }

        public void processLogEvent(final ILogEvent abstractLogEvent) {}

    }

    public static void register() {
        if (SeCorePlugin.getDefault() == null) {
            final SeCorePlugin core = new SeCorePlugin();
            core.registerService(ITypeRegistry.class, new TestTypeRegistry());
        }
    }

}
