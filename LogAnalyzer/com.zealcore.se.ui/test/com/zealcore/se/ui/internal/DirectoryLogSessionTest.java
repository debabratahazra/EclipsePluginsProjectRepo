package com.zealcore.se.ui.internal;

import org.easymock.classextension.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.ui.SystemExplorerNature;

public class DirectoryLogSessionTest {

    private static final String NOT_YET_IMPLEMENTED = "Not yet implemented";

    @Before
    public void setUp() throws Exception {
        MockUtil.reset();

    }

    @After
    public void tearDown() throws Exception {}

    /**
     * Test if file exist is false.
     */
    @Test
    public final void testIsLogSessionExistFalse() {

        final IFile notExist = MockUtil.niceMock(IFile.class);
        EasyMock.expect(notExist.exists()).andReturn(Boolean.FALSE).anyTimes();
        MockUtil.replayAll();
        Assert.assertFalse(DirectoryLogSession.isLogSession(notExist));
        MockUtil.verifyAll();
    }

    /**
     * Test it is not a log session.
     * 
     * @throws CoreException
     *                 the core exception
     */
    @Test
    public final void testIsLogSessionNotLogSession() throws CoreException {
        final IFolder notDLS = MockUtil.newMock(IFolder.class);

        EasyMock.expect(notDLS.exists()).andReturn(Boolean.TRUE);
        EasyMock.expect(
                notDLS.getSessionProperty(DirectoryLogSession.IS_LOGSESSION))
                .andReturn(null).anyTimes();

        final IFolder casefile = MockUtil.niceMock(IFolder.class);
        EasyMock.expect(notDLS.getParent()).andReturn(casefile).anyTimes();
        MockUtil.replayAll();
        Assert.assertFalse("Should not be DLS", DirectoryLogSession
                .isLogSession(notDLS));
        MockUtil.verifyAll();

    }

    /**
     * Test that isDLS actually is log session.
     * 
     * @throws CoreException
     *                 the core exception
     */
    @Test
    public final void testIsLogSessionYes() throws CoreException {
        final IFolder isDLS = MockUtil.newMock(IFolder.class);

        EasyMock.expect(isDLS.exists()).andReturn(Boolean.TRUE);
        EasyMock.expect(
                isDLS.getSessionProperty(DirectoryLogSession.IS_LOGSESSION))
                .andReturn("").anyTimes();

        final IProject casefile = MockUtil.niceMock(IProject.class);
        EasyMock.expect(casefile.exists()).andReturn(Boolean.TRUE).anyTimes();
        EasyMock.expect(casefile.isOpen()).andReturn(Boolean.TRUE).anyTimes();
        EasyMock.expect(casefile.getNature(SystemExplorerNature.NATURE_ID))
                .andReturn(new SystemExplorerNature()).anyTimes();

        EasyMock.expect(isDLS.getParent()).andReturn(casefile).anyTimes();

        final IFile cfgfile = MockUtil.niceMock(IFile.class);
        EasyMock.expect(cfgfile.exists()).andReturn(Boolean.TRUE).anyTimes();

        EasyMock.expect(isDLS.getFile(DirectoryLogSession.CONFIG_FILE))
                .andReturn(cfgfile);

        MockUtil.replayAll();

        Assert.assertTrue("Mock-setup not correct", DirectoryCaseFile
                .isCaseFile(casefile));
        Assert.assertTrue("Should be DLS", DirectoryLogSession
                .isLogSession(isDLS));
        MockUtil.verifyAll();

    }

    /**
     * Test that isDLS is not log session because parent is not case file.
     * 
     * @throws CoreException
     *                 the core exception
     */
    @Test
    public final void testIsLogSessionParentIsNotCaseFile()
            throws CoreException {
        final IFolder isDLS = MockUtil.newMock(IFolder.class);

        EasyMock.expect(isDLS.exists()).andReturn(Boolean.TRUE);
        EasyMock.expect(
                isDLS.getSessionProperty(DirectoryLogSession.IS_LOGSESSION))
                .andReturn("").anyTimes();

        final IFolder casefile = MockUtil.niceMock(IFolder.class);
        EasyMock.expect(isDLS.getParent()).andReturn(casefile).anyTimes();
        final IFile casefileCfg = MockUtil.niceMock(IFile.class);
        EasyMock.expect(casefileCfg.exists()).andReturn(Boolean.FALSE)
                .anyTimes();

        EasyMock.expect(casefile.getFile(DirectoryCaseFile.CASE_FILE_CONFIG))
                .andReturn(casefileCfg).anyTimes();
        EasyMock.expect(isDLS.getFile(DirectoryLogSession.CONFIG_FILE))
                .andReturn(casefileCfg).anyTimes();

        MockUtil.replayAll();
        Assert.assertFalse(DirectoryCaseFile.isCaseFile(casefile));
        Assert.assertFalse("Should not be DLS, parent is not casefile",
                DirectoryLogSession.isLogSession(isDLS));
        MockUtil.verifyAll();

    }

    @Ignore
    @Test
    public final void testCreateTimeCluster() throws CoreException {

        final IFolder isDLS = MockUtil.niceMock(IFolder.class);
        final IFile configFile = MockUtil.niceMock(IFile.class);
        // The DLS shall try to write its configuration to a file named by the
        // constant CONFIG_FILE
        org.easymock.EasyMock.expect(
                isDLS.getFile(DirectoryLogSession.CONFIG_FILE)).andReturn(
                configFile);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(configFile.exists()).andReturn(true);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();

        final String value = Boolean.TRUE.toString();
        // The valueOf shall check that it actually is a DLS
        org.easymock.EasyMock.expect(
                isDLS.getSessionProperty(DirectoryLogSession.IS_LOGSESSION))
                .andReturn(value);
        org.easymock.EasyMock.expectLastCall().atLeastOnce();

        // The valueOf will also check if this folder already been configured
        // with an instance of DLS, if so, that instance is returned instead
        // Returning null on the session property prevents this.
        org.easymock.EasyMock.expect(
                isDLS.getSessionProperty(DirectoryLogSession.IS_LOGSESSION))
                .andReturn(null);

        // ArrayList container = new ArrayList();
        // Creating timeclusters requires getting a session property
        // EasyMock.expect(
        // isDLS.getSessionProperty(DirectoryLogSession.QN_TIME_CLUSTERS)).andReturn(
        // container);

        MockUtil.replayAll();

        Assert.assertTrue(DirectoryLogSession.isLogSession(isDLS));

        DirectoryLogSession.valueOf(isDLS);

        MockUtil.verifyAll();
        // TODO Must mock up the inputstream reader - this is tiresome
        // just be to able to test - should try find a nother path.
        Assert.fail(DirectoryLogSessionTest.NOT_YET_IMPLEMENTED);
    }
}
