package com.zealcore.se.ui.mock;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.zealcore.se.core.dl.AbstractTypePackage;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;

public class MockTypePackage extends AbstractTypePackage {

    public Collection<IType> getTypes() {
        final Set<Class<?>> clazzes = new LinkedHashSet<Class<?>>();
        clazzes.add(ArtifactRoot.class);
        clazzes.add(Signal.class);
        clazzes.add(StackTrace.class);
        return ReflectiveType.valueOf(clazzes);
    }

    public void processLogEvent(final ILogEvent abstractLogEvent) {}
}
