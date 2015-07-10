package com.zealcore.se.ui.util;

import org.easymock.classextension.EasyMock;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IMemento;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.internal.ArtifactID;

public class ArtifactColorMapTest {

    private static final String NAME = "name";

    // private static final String SESSION = "session";

    private static final String CLAZZ = "clazz";

    // private static final String ARTIFACT_ID = "artifactId";

    private static final int THREE = 3;

    private static final int TWO = 2;

    private static final int ONE = 1;

    private static final String ARTIFACTNAME = "DummyArtifact";

    // private static final String SESSIONNAME = "DummySession";

    private static final String CLASSNAME = "com.zealcore.mock.dummy";

    private IArtifactID mock2;

    private IArtifactID mock;

    @Before
    public void setUp() throws Exception {

        this.mock = ArtifactID.valueOf(new IArtifactID() {

            public String getClassName() {
                return ArtifactColorMapTest.CLASSNAME;
            }

            public String getId() {
                return ArtifactColorMapTest.ARTIFACTNAME;
            }

        });
        this.mock2 = ArtifactID.valueOf(new IArtifactID() {

            public String getClassName() {
                return ArtifactColorMapTest.CLASSNAME;
            }

            public String getId() {
                return ArtifactColorMapTest.ARTIFACTNAME;
            }

        });
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    // @Ignore
    public void testSetColor() {

        final ArtifactColorMap colorMap = new ArtifactColorMap();

        RGB black = new RGB(0, 0, 0);
        colorMap.setColor(this.mock, black);

        final RGB color = colorMap.getColor(this.mock2);

        Assert.assertEquals(black, color);

    }

    @Test
    // @Ignore
    public void testSaveState() {
        final ArtifactColorMap colorMap = new ArtifactColorMap();

        colorMap.setColor(this.mock, getColor());

        final IMemento parent = EasyMock.createMock(IMemento.class);
        final IMemento instance = EasyMock.createMock(IMemento.class);

        org.easymock.EasyMock.expect(
                parent.createChild(ArtifactColorMap.MAPPED_COLOR_NODE))
                .andReturn(instance);
        // EasyMock.expect(instance.createChild(ARTIFACT_ID))
        // .andReturn(instance);

        instance.putString(ArtifactColorMapTest.CLAZZ,
                ArtifactColorMapTest.CLASSNAME);
        // instance.putString(ArtifactColorMapTest.SESSION,
        // ArtifactColorMapTest.SESSIONNAME);
        instance.putString(ArtifactColorMapTest.NAME,
                ArtifactColorMapTest.ARTIFACTNAME);

        instance.putInteger(ArtifactColorMap.COLOR_RED_NODE,
                ArtifactColorMapTest.ONE);
        instance.putInteger(ArtifactColorMap.COLOR_GREEN_NODE,
                ArtifactColorMapTest.TWO);
        instance.putInteger(ArtifactColorMap.COLOR_BLUE_NODE,
                ArtifactColorMapTest.THREE);

        EasyMock.replay(parent);
        EasyMock.replay(instance);
        // EasyMock.replay(instance);

        colorMap.saveState(parent);

        EasyMock.verify(parent);
        EasyMock.verify(instance);
        // EasyMock.verify(instance);

    }

    private RGB getColor() {
        return new RGB(ArtifactColorMapTest.ONE, ArtifactColorMapTest.TWO,
                ArtifactColorMapTest.THREE);
    }

    @Test
    public void testInit() {

        final ArtifactColorMap map = new ArtifactColorMap();

        final IMemento parent = EasyMock.createMock(IMemento.class);
        final IMemento instance = EasyMock.createMock(IMemento.class);
        // IMemento artifactId = EasyMock.createMock(IMemento.class);

        org.easymock.EasyMock.expect(
                parent.getChildren(ArtifactColorMap.MAPPED_COLOR_NODE))
                .andReturn(new IMemento[] { instance });

        org.easymock.EasyMock.expect(
                instance.getInteger(ArtifactColorMap.COLOR_RED_NODE))
                .andReturn(ArtifactColorMapTest.ONE);
        org.easymock.EasyMock.expect(
                instance.getInteger(ArtifactColorMap.COLOR_GREEN_NODE))
                .andReturn(ArtifactColorMapTest.TWO);
        org.easymock.EasyMock.expect(
                instance.getInteger(ArtifactColorMap.COLOR_BLUE_NODE))
                .andReturn(ArtifactColorMapTest.THREE);

        // EasyMock.expect(instance.getChild(ARTIFACT_ID)).andReturn(artifactId);

        org.easymock.EasyMock.expect(
                instance.getString(ArtifactColorMapTest.CLAZZ)).andReturn(
                ArtifactColorMapTest.CLASSNAME);
        // org.easymock.EasyMock.expect(
        // instance.getString(ArtifactColorMapTest.SESSION)).andReturn(
        // ArtifactColorMapTest.SESSIONNAME);
        org.easymock.EasyMock.expect(
                instance.getString(ArtifactColorMapTest.NAME)).andReturn(
                ArtifactColorMapTest.ARTIFACTNAME);

        EasyMock.replay(parent);
        EasyMock.replay(instance);
        // EasyMock.replay(artifactId);
        try {
            map.init(parent);
        } catch (final NoClassDefFoundError cannotGetColor) {
            System.out.println("It's not nice that this is untestable..");
            // FIXME It's not nice that this is untestable. The color map could
            // contain ColorDescriptors, which knows how to create colors
            // Makes it easier to auto-test the ColorMap. And the
            // ColorDescriptor is trivial
        }

        EasyMock.verify(parent);
        EasyMock.verify(instance);
        // EasyMock.verify(artifactId);

    }

    @Test
    public void testResetColor() {
        ArtifactColorMap subject = new ArtifactColorMap();
        ChangeListener changeChecker = new ChangeListener();
        subject.addChangeListener(changeChecker);
        changeChecker.setChanged(false);

        subject.setColor(mock, getColor());

        Assert.assertTrue(changeChecker.isChanged());
        changeChecker.setChanged(false);

        subject.resetColor(mock);
        Assert.assertTrue("No change detected", changeChecker.isChanged());

    }

    static class ChangeListener implements IChangeListener {

        private boolean changed;

        public void update(final boolean changed) {
            this.setChanged(changed);

        }

        private void setChanged(final boolean changed) {
            this.changed = changed;
        }

        private boolean isChanged() {
            return changed;
        }

    }

    @Test
    public void testGetDefaultColor() {
        ArtifactColorMap subject = new ArtifactColorMap();
        RGB data = subject.getDefaultColor();
        Assert.assertNotNull(data);
    }
}
