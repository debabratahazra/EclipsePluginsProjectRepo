package util;

import java.util.ArrayList;
import java.util.Collection;

import org.easymock.classextension.EasyMock;

public final class MockUtil {

    private MockUtil() {
    // No Instances
    }

    private static Collection<Object> mocks = new ArrayList<Object>();

    public static void replay(final Object... mocks) {
        for (final Object object : mocks) {
            EasyMock.replay(object);
        }
    }

    public static void verify(final Object... mocks) {
        for (final Object object : mocks) {
            EasyMock.verify(object);
        }
    }

    public static void replayAll() {
        MockUtil.replay(MockUtil.mocks.toArray());
    }

    /**
     * Verifies all mocks created since last verifyAll call
     */
    public static void verifyAll() {
        MockUtil.verify(MockUtil.mocks.toArray());
        MockUtil.mocks.clear();
    }

    public static <T> T newMock(final Class<T> crt) {
        final T t = EasyMock.createMock(crt);
        MockUtil.mocks.add(t);
        return t;
    }

    public static <T> T niceMock(final Class<T> crt) {
        final T t = EasyMock.createNiceMock(crt);
        MockUtil.mocks.add(t);
        return t;
    }

    public static void reset() {
        MockUtil.mocks.clear();
    }
}
