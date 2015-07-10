package com.enea.sd.example.importer;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.After;
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

public class SimpleImporterTest extends AbstractImporterTest {
    private SimpleImporter imp;

    private GenericTypePackage gtp;

    private static final long S_TO_US_FACTOR = 100000;

    private static final String TESTFILE_TEXT_URL = "logs/simpleImporter.log";

    @Override
    protected void checkTimestamp(final int eventIndex, final ILogEvent event,
            final long previous) {

        assertTrue(
                String
                        .format(
                                "The timestamp at event-index %1$d must be in chronological order, current %1$d previous %1$d",
                                eventIndex, event.getTs(), previous), event
                        .getTs() >= previous);

        final Calendar calendar = Calendar.getInstance();
        final long timeInMs = event.getTs() / S_TO_US_FACTOR;
        calendar.setTimeInMillis(timeInMs);

    }

    @Before
    public void setUp() throws Exception {
        imp = new SimpleImporter();
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

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testCanRead() {
        File testfile = new File(TESTFILE_TEXT_URL);
        assertTrue(imp.canRead(testfile));
    }

    @Override
    protected void endArtifacts(final int numArtifacts) {}

    @Override
    protected void endEvents(final int numEvents) {
        Assert.assertEquals("Incorrect number of events read in logfile.",
                17, numEvents);
    }

    @Override
    protected File getFileInput() {
        return new File(TESTFILE_TEXT_URL);
    }

    @Override
    protected IImporter getSubject() {
        return new SimpleImporter();
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
    public void testReadCorruptFile() throws Exception {

    }

}
