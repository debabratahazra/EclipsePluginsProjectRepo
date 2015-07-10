package com.zealcore.se.ui.core.internal;

import junit.framework.Assert;

import org.easymock.classextension.EasyMock;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IMemento;
import org.junit.Test;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.ui.graphics.ColorAdapter;
import com.zealcore.se.ui.graphics.IColor;

public class EventColorProviderTest2 {

    @Test
    public final void testBasicSetGet() {

        final IMementoService ser = new IMementoService() {
            public IMemento createReadRoot(final IPath path) {
                return null;
            }

            public IMemento2 createWriteRoot(final String root, final IPath path) {
                IMemento2 createNiceMock = EasyMock
                        .createNiceMock(IMemento2.class);
                org.easymock.EasyMock
                        .expect(
                                createNiceMock
                                        .createChild(EventColorProvider.MAPPED_COLOR_NODE))
                        .andReturn(createNiceMock).anyTimes();
                EasyMock.replay(createNiceMock);
                return createNiceMock;
            }

        };

        final EventColorProvider subject = new EventColorProvider(ser,
                new Path("FOOOO"));

        final ColorAdapter color = new ColorAdapter();
        subject.setColor(ReflectiveType.valueOf(ILogEvent.class), color);
        final IColor color2 = subject.getColor(ReflectiveType
                .valueOf(ILogEvent.class));
        Assert.assertSame(color, color2);

    }
}
