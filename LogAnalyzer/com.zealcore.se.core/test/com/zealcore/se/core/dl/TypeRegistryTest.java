package com.zealcore.se.core.dl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IType;

public class TypeRegistryTest {

    @Test
    public void testGetTypePackage() {

        final TypeRegistry subject = new TypeRegistry("TestMode");
        final DummyTP testTp = new DummyTP();
        subject.add(testTp);

        final ITypePackage typePackage = subject.getTypePackage(new DummyType()
                .getType());
        Assert.assertSame(testTp, typePackage);

    }

    private static class DummyTP extends AbstractTypePackage {

        public Collection<IType> getTypes() {
            final List<IType> types = new ArrayList<IType>();
            types.add(new DummyType().getType());
            return types;
        }

        public void processLogEvent(final ILogEvent abstractLogEvent) {}

    }

    private static class DummyType extends AbstractLogEvent {

    }

}
