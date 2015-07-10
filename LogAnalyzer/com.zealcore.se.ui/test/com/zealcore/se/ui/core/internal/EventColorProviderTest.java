package com.zealcore.se.ui.core.internal;

import java.io.File;

import junit.framework.Assert;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IMemento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.ui.graphics.ColorAdapter;
import com.zealcore.se.ui.graphics.IColor;

public class EventColorProviderTest {
    // constants
    private static final Path INVALID_FILEPATH = new Path("nofile");

    private static final String E_FILE_PATH_EXISTS = "Error - file exists";

    // subject
    private EventColorProvider subjectEventColorProvider;

    // mocks
    private IMementoService mockMementoService;

    private IMementoService mockNiceMementoService;

    private IMemento2 mockMemento2In;

    private IMemento2 mockNiceMemento2In;

    private IMemento mockMementoIn;

    private IMemento mockNiceMementoIn;

    private IChangeListener mockChangeListener;

    private IMocksControl mockControl;

    private IMocksControl mockNiceControl;

    private IPath mockPath;

    private File mockFile;

    @Before
    public void setUp() throws Exception {
        // perform setup assertions
        Assert.assertFalse(EventColorProviderTest.E_FILE_PATH_EXISTS,
                EventColorProviderTest.INVALID_FILEPATH.toFile().exists());

        // create the mock controls
        this.mockControl = EasyMock.createControl();
        this.mockNiceControl = EasyMock.createNiceControl();

        // create mock objects
        this.mockMementoService = this.mockControl
                .createMock(IMementoService.class);
        this.mockNiceMementoService = this.mockNiceControl
                .createMock(IMementoService.class);
        this.mockChangeListener = this.mockControl
                .createMock(IChangeListener.class);
        this.mockPath = this.mockControl.createMock(IPath.class);
        this.mockFile = this.mockControl.createMock(File.class);
        this.mockMemento2In = this.mockControl.createMock(IMemento2.class);
        this.mockNiceMemento2In = this.mockNiceControl
                .createMock(IMemento2.class);
        this.mockMementoIn = this.mockControl.createMock(IMemento.class);
        this.mockNiceMementoIn = this.mockNiceControl
                .createMock(IMemento.class);
    }

    @After
    public void tearDown() throws Exception {
        // verify calls
        this.mockControl.verify();
        this.mockNiceControl.verify();
    }

    @Test
    public final void testConstructors() throws Exception {
        /*
         * Test the constructor with and without a valid file path NiceMock
         * memento stuff, this is tested in its own testcase
         * 
         */

        // do uncontrolled (unexpected) stuff
        // setup expects
        org.easymock.EasyMock.expect(this.mockPath.toFile()).andReturn(
                this.mockFile);
        org.easymock.EasyMock.expect(this.mockFile.exists()).andReturn(true);
        org.easymock.EasyMock.expect(
                this.mockNiceMementoService.createReadRoot(this.mockPath))
                .andReturn(this.mockMemento2In);
        org.easymock.EasyMock.expect(
                this.mockMemento2In
                        .getChildren(EventColorProvider.MAPPED_COLOR_NODE))
                .andReturn(new IMemento[0]);

        this.mockControl.replay();
        this.mockNiceControl.replay();

        // do controlled (expected) stuff
        this.subjectEventColorProvider = new EventColorProvider(
                this.mockNiceMementoService, this.mockPath);
        this.subjectEventColorProvider = new EventColorProvider(
                this.mockNiceMementoService,
                EventColorProviderTest.INVALID_FILEPATH);

    }

    @Test
    public final void testChangeListenerNotifications() {
        /*
         * Test that add and remove change listeners works and that the listener
         * gets notified at setColor NiceMock memento stuff, this is tested in
         * its own testcase
         */

        // do uncontrolled (unexpected) stuff
        this.subjectEventColorProvider = new EventColorProvider(
                this.mockNiceMementoService,
                EventColorProviderTest.INVALID_FILEPATH);

        // setup expects
        this.mockChangeListener.update(false);
        org.easymock.EasyMock.expect(
                this.mockNiceMementoService.createWriteRoot(
                        EventColorProvider.EVENT_COLOR_MAPPING,
                        EventColorProviderTest.INVALID_FILEPATH)).andReturn(
                this.mockNiceMemento2In);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(
                this.mockNiceMemento2In
                        .createChild(EventColorProvider.MAPPED_COLOR_NODE))
                .andReturn(this.mockNiceMementoIn);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        this.mockChangeListener.update(true);

        this.mockControl.replay();
        this.mockNiceControl.replay();

        // do controlled (expected) stuff
        this.subjectEventColorProvider
                .addChangeListener(this.mockChangeListener);
        final IColor colorIn = ColorAdapter.valueOf(new RGB(1, 1, 1));
        Assert.assertNotNull(colorIn);
        this.subjectEventColorProvider.setColor(ReflectiveType
                .valueOf(ILogEvent.class), colorIn);
        this.subjectEventColorProvider
                .removeChangeListener(this.mockChangeListener);
        this.subjectEventColorProvider.setColor(ReflectiveType
                .valueOf(ILogEvent.class), colorIn);
    }

    @Test
    public final void testColorHandling() {
        /*
         * Test get/set/clear color works as expected NiceMock memento stuff,
         * this is tested in its own testcase
         * 
         */

        // setup expects
        org.easymock.EasyMock.expect(
                this.mockNiceMementoService.createWriteRoot(
                        EventColorProvider.EVENT_COLOR_MAPPING,
                        EventColorProviderTest.INVALID_FILEPATH)).andReturn(
                this.mockNiceMemento2In);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(
                this.mockNiceMemento2In
                        .createChild(EventColorProvider.MAPPED_COLOR_NODE))
                .andReturn(this.mockNiceMementoIn);
        org.easymock.EasyMock.expect(
                this.mockNiceMemento2In
                        .createChild(EventColorProvider.MAPPED_COLOR_NODE))
                .andReturn(this.mockNiceMementoIn);

        this.mockControl.replay();
        this.mockNiceControl.replay();

        // do uncontrolled (unexpected) stuff
        this.subjectEventColorProvider = new EventColorProvider(
                this.mockNiceMementoService,
                EventColorProviderTest.INVALID_FILEPATH);
        // do controlled (expected) stuff
        final IColor colorIn = ColorAdapter.valueOf(new RGB(1, 1, 1));
        Assert.assertNotNull(colorIn);
        ReflectiveType reflectiveType = ReflectiveType.valueOf(ILogEvent.class);
        this.subjectEventColorProvider.setColor(reflectiveType, colorIn);
        IColor colorOut1 = this.subjectEventColorProvider
                .getColor(reflectiveType);
        Assert.assertEquals(colorIn, colorOut1);
        this.subjectEventColorProvider.clearColor(reflectiveType);
        final IColor colorOut2 = this.subjectEventColorProvider
                .getColor(reflectiveType);
        Assert.assertNotNull(colorOut2);
        Assert.assertNotSame(colorOut1, colorOut2);
        colorOut1 = this.subjectEventColorProvider.getColor(reflectiveType);
        Assert.assertSame(colorOut1, colorOut2);
    }

    @Test
    public final void testPersistance() throws Exception {
        /*
         * Tests that save/restore instance works by creating the subject and
         * setting a color (save) Then creating a new subject (restore) and
         * verifying that the correct color is present
         * 
         */
        // do uncontrolled (unexpected) stuff
        this.subjectEventColorProvider = new EventColorProvider(
                this.mockMementoService,
                EventColorProviderTest.INVALID_FILEPATH);

        // setup expects
        org.easymock.EasyMock.expect(
                this.mockMementoService.createWriteRoot(
                        EventColorProvider.EVENT_COLOR_MAPPING,
                        EventColorProviderTest.INVALID_FILEPATH)).andReturn(
                this.mockMemento2In);
        this.mockMemento2In.save();
        org.easymock.EasyMock.expect(
                this.mockMemento2In
                        .createChild(EventColorProvider.MAPPED_COLOR_NODE))
                .andReturn(this.mockMementoIn);
        this.mockMementoIn.putString(EventColorProvider.EVENT_TYPE_ID,
                ReflectiveType.valueOf(ILogEvent.class).getId());
        this.mockMementoIn.putInteger(EventColorProvider.COLOR_RED_NODE, 1);
        this.mockMementoIn.putInteger(EventColorProvider.COLOR_GREEN_NODE, 1);
        this.mockMementoIn.putInteger(EventColorProvider.COLOR_BLUE_NODE, 1);

        org.easymock.EasyMock.expect(this.mockPath.toFile()).andReturn(
                this.mockFile);
        org.easymock.EasyMock.expect(this.mockFile.exists()).andReturn(true);
        org.easymock.EasyMock.expect(
                this.mockMementoService.createReadRoot(this.mockPath))
                .andReturn(this.mockMemento2In);
        org.easymock.EasyMock.expect(
                this.mockMemento2In
                        .getChildren(EventColorProvider.MAPPED_COLOR_NODE))
                .andReturn(new IMemento[] { this.mockMementoIn });
        org.easymock.EasyMock.expect(
                this.mockMementoIn.getString(EventColorProvider.EVENT_TYPE_ID))
                .andReturn(ReflectiveType.valueOf(ILogEvent.class).getId());
        org.easymock.EasyMock.expect(
                this.mockMementoIn
                        .getInteger(EventColorProvider.COLOR_RED_NODE))
                .andReturn(1);
        org.easymock.EasyMock.expect(
                this.mockMementoIn
                        .getInteger(EventColorProvider.COLOR_GREEN_NODE))
                .andReturn(1);
        org.easymock.EasyMock.expect(
                this.mockMementoIn
                        .getInteger(EventColorProvider.COLOR_BLUE_NODE))
                .andReturn(1);
        this.mockControl.replay();
        this.mockNiceControl.replay();

        // do controlled (expected) stuff
        final IColor colorIn = ColorAdapter.valueOf(new RGB(1, 1, 1));
        Assert.assertNotNull(colorIn);
        this.subjectEventColorProvider.setColor(ReflectiveType
                .valueOf(ILogEvent.class), colorIn);
        this.subjectEventColorProvider = null;
        this.subjectEventColorProvider = new EventColorProvider(
                this.mockMementoService, this.mockPath);
        final IColor colorOut = this.subjectEventColorProvider
                .getColor(ReflectiveType.valueOf(ILogEvent.class));
        Assert.assertEquals(colorIn.r(), colorOut.r());
        Assert.assertEquals(colorIn.g(), colorOut.g());
        Assert.assertEquals(colorIn.b(), colorOut.b());
    }
}
