package com.zealcore.se.core.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.annotation.ZSearchable;

@SuppressWarnings("unchecked")
public final class ReflectiveType implements IType {

    private static class Property implements IProperty {

        private static final Object[] NO_ARGUMENTS = new Object[0];

        private final Method method;

        private final ZCProperty annotation;

        public Property(final Method m) {
            this.method = m;
            this.annotation = m.getAnnotation(ZCProperty.class);
        }

        public String getName() {
            return this.annotation.name();
        }

        public Object getValue(final IObject object) {
            try {
                return this.method.invoke(object, Property.NO_ARGUMENTS);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean isPlotable() {
            return this.annotation.plottable();
        }

        public boolean isSearchable() {
            return this.annotation.searchable();
        }

        public String getDescription() {
            return this.annotation.description();
        }

        public Class getReturnType() {
            return this.method.getReturnType();
        }

        @Override
        public int hashCode() {
            return this.method.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof IProperty) {
                final IProperty property = (IProperty) obj;
                return property.getName().equals(getName());

            }

            return super.equals(obj);
        }

        public boolean isGeneric() {
            return false;
        }

    }

    private final Class<? extends Object> clazz;

    private String id;

    private String name;

    /**
     * Use valueOf instead
     * 
     * @param clazz
     */
    @Deprecated
    private ReflectiveType(final Class clazz) {
        this.clazz = clazz;
    }

    private static Map<Class<?>, ReflectiveType> typeByClass = new IdentityHashMap<Class<?>, ReflectiveType>();

    public static ReflectiveType valueOf(final Class<?> clazz) {
        ReflectiveType type = ReflectiveType.typeByClass.get(clazz);
        if (type == null) {
            type = new ReflectiveType(clazz);
            ReflectiveType.typeByClass.put(clazz, type);
        }
        return type;
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        if (this.name == null) {
            final Class<ZSearchable> searchableAnnotation = ZSearchable.class;
            if (this.clazz.isAnnotationPresent(searchableAnnotation)) {
                final ZSearchable annotation = this.clazz
                        .getAnnotation(searchableAnnotation);
                this.name = annotation.name();
            }
        }

        if (this.name == null || this.name.length() < 1) {
            this.name = this.clazz.getSimpleName();
        }
        return this.name;
    }

    public Collection<IProperty> getProperties() {
        final Collection<IProperty> properties = new ArrayList<IProperty>();

        this.clazz.getMethods();

        for (final Method m : this.clazz.getMethods()) {
            /*
             * If found the property annotation
             */
            if (m.isAnnotationPresent(ZCProperty.class)) {

                final IProperty property = new Property(m);
                properties.add(property);
            }
        }
        return properties;
    }

    @Override
    public int hashCode() {
        return this.clazz.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass() == ReflectiveType.class) {
            return ((ReflectiveType) obj).clazz.equals(this.clazz);
        }
        return false;
    }

    public String getId() {
        if (this.id == null) {
            this.id = this.clazz.getName();
        }
        return this.id;
    }

    public static Set<IType> valueOf(final Set<Class<?>> clazzes) {
        final Set<IType> types = new HashSet<IType>();
        for (final Class clazz : clazzes) {
            types.add(ReflectiveType.valueOf(clazz));
        }
        return types;
    }

    public boolean isA(final IType type) {
        if (type instanceof ReflectiveType) {
            final ReflectiveType other = (ReflectiveType) type;
            return other.clazz.isAssignableFrom(this.clazz);

        }
        return false;
    }

    public boolean isPlottable() {
        for(IProperty p : getProperties()) {
            if(p.isPlotable()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSearchable() {
        for (final IProperty iProperty : getProperties()) {
            if (iProperty.isSearchable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getName() + ",Id=" + getId();
    }
}
