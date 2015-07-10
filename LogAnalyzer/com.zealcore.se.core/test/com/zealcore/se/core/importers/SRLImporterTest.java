package com.zealcore.se.core.importers;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.dl.ILogSessionItemListener;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.generic.GenericTypePackage;
import com.zealcore.se.core.tests.AbstractImporterTest;
import com.zealcore.se.core.tests.Util;

public class SRLImporterTest extends AbstractImporterTest {

    private static final String TESTFILE_BIN_OUT_URL = "logs/srl_test1_normal.bin";

    private static final String TESTFILE_URL = "logs/srl_test1_normal.srl";

    private GenericTypePackage gtp;

    private SRLImporter imp;

    @Override
    protected File getFileInput() {
        File generated = new File(TESTFILE_BIN_OUT_URL);

        if (generated.exists()) {
            generated.delete();
        }
        return new File(TESTFILE_URL);
    }

    @Override
    protected IImporter getSubject() {
        return new SRLImporter();
    }

    @Before
    public void setUp() throws Exception {
        imp = new SRLImporter();
        gtp = new GenericTypePackage();
        gtp.beginFull();
        gtp.addLogsessionItemListener(new ILogSessionItemListener() {
            public void logFileItemCreated(final IObject abstractLogFileItem) {
                try {
                    Util.checkElementIntegrity(abstractLogFileItem, false);
                } catch (final IllegalAccessException e) {
                    e.printStackTrace();
                    Assert.fail(e.getMessage());
                } catch (final InvocationTargetException e) {
                    e.printStackTrace();
                    Assert.fail(e.getMessage());
                }
            }
        });
    }

    @Test
    public void testCanRead() {
        File testfile = new File(TESTFILE_URL);
        assertTrue(imp.canRead(testfile));
    }

    @Override
    protected void endEvents(final int numEvents) {
        Assert.assertEquals("Incorrect number of events read in logfile.",
                6083, numEvents);
    }

    @Override
    protected String validate(final int i, final IArtifact artifact) {
        Assert.assertNotNull(artifact);
        return null;
    }

    @Override
    protected String validate(final int index, final ILogEvent event) {
        Assert.assertNotNull(event);
        gtp.processLogEvent(event);

        return null;
    }

    @Override
    protected void checkTimestamp(final int eventIndex, final ILogEvent event,
            final long previous) {
        //super.checkTimestamp(eventIndex, event, previous);
    }
}
