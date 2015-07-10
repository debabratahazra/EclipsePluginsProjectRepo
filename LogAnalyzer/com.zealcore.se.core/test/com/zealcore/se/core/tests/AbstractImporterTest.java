package com.zealcore.se.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.dl.ILogSessionItemListener;
import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.TestTypeRegistry;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IType;

public abstract class AbstractImporterTest {
    private static final String IT_MUST_BE_POSSIBLE_TO_DELETE_THE_FILE_AFTER_A_FULL_IMPORT = "It must be possible to delete the file after a full import";

    private static final String NEWLINE = System.getProperty("line.separator");

    private final class Validator implements ILogSessionItemListener {
        public void logFileItemCreated(final IObject abstractLogFileItem) {
            try {
                Util.checkElementIntegrity(abstractLogFileItem, false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final int HEX_255 = 0xFF;

    private static final String CONCRETE_TEST_MUST_SUPPLY = "Concrete Test Implementation must supply a subject";

    private static final String FILE_MUST_BE_READABLE = "File must be readable ";

    private static final int ONE_KB = 1000;

    private static final long NANOS_AT_Y2K = 946080000000000000L;

    private static final long THIRTY_YEAR = 946080000000000000L;

    private static final long S_TO_US_FACTOR = 100000;

    @Before
    public void setUpTest() {
        TestTypeRegistry.register();
    }

    @Test
    public void basicImportTest() throws Exception {
        final IImporter subject = getSubject();
        Assert.assertNotNull(AbstractImporterTest.CONCRETE_TEST_MUST_SUPPLY,
                subject);
        final File tempFile = getFileSubject();
        tempFile.deleteOnExit();

        Assert.assertTrue(AbstractImporterTest.FILE_MUST_BE_READABLE, tempFile
                .canRead());

        Assert.assertTrue(String.format(
                "The importer  %1$s must be able to read the file ", subject)
                + tempFile, subject.canRead(tempFile));

        Assert.assertTrue(subject.canRead(tempFile));
        setContext(subject, tempFile);
        runEventImport(subject, true);
        runArtifactImport(subject, true);
        Assert.assertTrue(String.format(
                IT_MUST_BE_POSSIBLE_TO_DELETE_THE_FILE_AFTER_A_FULL_IMPORT
                        + NEWLINE + "The file %1$s is now locked", tempFile
                        .getAbsolutePath()), tempFile.delete());

    }

    @Test
    public void testCanReadEmptyFile() throws IOException {
        final IImporter subject = getSubject();
        final File file = File.createTempFile("EmptyFile", "tst");
        file.deleteOnExit();

        Assert.assertTrue("Temporary file creation failed", file.exists());
        Assert.assertFalse("The importer    " + subject
                + " should not be able to read empty", subject.canRead(file));
        Assert.assertTrue("The canRead method of " + subject
                + " left the empty file in a locked state", file.delete());
    }

    @Test
    public void testReadCorruptFile() throws Exception {
        final IImporter subject = getSubject();

        Assert.assertNotNull(AbstractImporterTest.CONCRETE_TEST_MUST_SUPPLY,
                subject);
        final File tempFile = getCorruptFile();
        tempFile.deleteOnExit();

        Assert.assertTrue(AbstractImporterTest.FILE_MUST_BE_READABLE, tempFile
                .canRead());

        if (subject.canRead(tempFile)) {
            try {
                Assert.assertTrue("The importer  " + subject
                        + " must be able to read the file " + tempFile, subject
                        .canRead(tempFile));

                setContext(subject, tempFile);
                runEventImport(subject, false);
                runArtifactImport(subject, false);
                noExceptionDuringCorruptFile();
            } catch (final ImportException e) {
                Assert
                        .fail("Importers Should no longer throw ImportException: Importer="
                                + subject.toString());
            } catch (final RuntimeException e) {
                // This is expected behaviour
            } finally {
                if (subject instanceof Closeable) {
                    Closeable closable = (Closeable) subject;
                    closable.close();

                }
            }
        }
        Assert
                .assertTrue(
                        IT_MUST_BE_POSSIBLE_TO_DELETE_THE_FILE_AFTER_A_FULL_IMPORT
                                + NEWLINE
                                + "The file "
                                + tempFile.getAbsolutePath()
                                + " is now locked. Maybe you forgot to try a try { [[code] } finally { fileIn.close(); } ",
                        tempFile.delete());

    }

    protected void noExceptionDuringCorruptFile() {
        System.err
                .println("WARNING: The importer does not throw any exception reading the corrupt file");
    }

    /**
     * Creates a temporary file subject from the contents of the submitted
     * subject file from the child implementation.
     * 
     * @return the file subject
     * 
     * @throws Exception
     *                 the exception
     */

    private File getFileSubject() throws Exception {
        final File fileInput = getFileInput();
        Assert.assertTrue("The supplied file " + fileInput + " does not exist",
                fileInput.exists());
        File tempFile = null;
        if (fileInput.getName().contains(".")) {
            final int suffixIndex = fileInput.getName().lastIndexOf('.');
            final String suffix = fileInput.getName().substring(suffixIndex);
            tempFile = File.createTempFile(fileInput.getName(), suffix);
        } else {
            tempFile = File.createTempFile(fileInput.getName(), ".test");
        }

        copyFile(fileInput, tempFile, -1);

        Assert.assertEquals(fileInput.length(), tempFile.length());
        Assert.assertTrue("FileSize must be larger than 0 ",
                fileInput.length() > 0);
        return tempFile;
    }

    private File getCorruptFile() throws Exception {
        final File tempFile = getFileSubject();
        final FileOutputStream append = new FileOutputStream(tempFile, true);
        append.getChannel().truncate(tempFile.length() / 2);
        for (int i = 0; i < AbstractImporterTest.ONE_KB; i++) {
            append.write(AbstractImporterTest.HEX_255);
        }
        append.close();
        return tempFile;
    }

    public void copyFile(final File in, final File out, final int numBytes)
            throws Exception {
        final FileChannel sourceChannel = new FileInputStream(in).getChannel();
        final FileChannel destinationChannel = new FileOutputStream(out)
                .getChannel();
        if (numBytes < 0) {
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } else {
            Assert.assertEquals("Failed to copy file", numBytes, sourceChannel
                    .transferTo(0, numBytes, destinationChannel));
        }
        sourceChannel.close();
        destinationChannel.close();
    }

    private void runArtifactImport(final IImporter subject,
            final boolean validate) throws IllegalAccessException,
            InvocationTargetException {
        int artifactIndex = 0;
        for (final IArtifact artifact : subject.getArtifacts()) {
            Util.checkElementIntegrity(artifact, false);
            final String out = validate(artifactIndex++, artifact);
            if (out != null) {
                System.out.println(out);
            }
        }
        if (validate) {
            endArtifacts(artifactIndex);
        }
    }

    protected void endArtifacts(final int numArtifacts) {}

    protected String validate(final int i, final IArtifact artifact) {
        return null;
    }

    private void runEventImport(final IImporter subject, final boolean validate)
            throws IllegalAccessException, InvocationTargetException {
        int eventIndex = 0;
        long previos = Long.MIN_VALUE;

        ITypePackage tp = getTypePackage();
        tp.beginFull();
        ILogSessionItemListener validator = new Validator();
        tp.addLogsessionItemListener(validator);
        final Iterable<ILogEvent> logEvents = subject.getLogEvents();
        for (final ILogEvent event : logEvents) {
            checkTimestamp(eventIndex, event, previos);
            previos = event.getTs();
            Util.checkElementIntegrity(event, false);

            final String out = validate(eventIndex++, event);
            if (out != null) {
                System.out.println(out);
            }
            tp.processLogEvent(event);
        }
        if (validate) {
            endEvents(eventIndex);
        }
        tp.endFull();
        tp.removeLogsessionItemListener(validator);

        if (subject instanceof Closeable) {
            final Closeable closable = (Closeable) subject;
            try {
                closable.close();
            } catch (final IOException e) {
                fail(e);
                e.printStackTrace();
            }

        }
    }

    private void fail(final IOException e) {
        e.printStackTrace();
        Assert.fail("Unexpected exception: " + e);

    }

    protected void endEvents(final int numEvents) {}

    protected abstract IImporter getSubject();

    protected abstract File getFileInput();

    protected int getStartYear() {
        return 2000;
    }

    protected void checkTimestamp(final int eventIndex, final ILogEvent event,
            final long previous) {

        Assert
                .assertTrue(
                        String
                                .format(
                                        "The timestamp at event-index %1$d must be in chronological order, current %1$d previous %1$d",
                                        eventIndex, event.getTs(), previous),
                        event.getTs() >= previous);

        final Calendar calendar = Calendar.getInstance();
        final long timeInMs = event.getTs()
                / AbstractImporterTest.S_TO_US_FACTOR;
        calendar.setTimeInMillis(timeInMs);

        Assert.assertTrue(String.format(
                "The timestamp of event %1$s should be larger than Year "
                        + getStartYear() + ", but was %2$s", event, calendar
                        .get(Calendar.YEAR)),
                calendar.get(Calendar.YEAR) >= getStartYear());
        Assert
                .assertTrue(
                        String
                                .format(
                                        "The timestamp of event %1$s should be less than Year 2030, but was %2$s",
                                        event, calendar.get(Calendar.YEAR)),
                        event.getTs() < AbstractImporterTest.NANOS_AT_Y2K
                                + AbstractImporterTest.THIRTY_YEAR);
    }

    protected String validate(final int index, final ILogEvent event) {
        return null;
    }

    protected ITypePackage getTypePackage() {

        return new ITypePackage() {
            public void addLogsessionItemListener(
                    final ILogSessionItemListener listener) {}

            public void begin() {}

            public void beginFull() {}

            public void end() {}

            public void endFull() {}

            @SuppressWarnings("unchecked")
            public Collection<IType> getTypes() {
                return Collections.EMPTY_LIST;
            }

            public void processLogEvent(final ILogEvent abstractLogEvent) {}

            public void removeLogsessionItemListener(
                    final ILogSessionItemListener listener) {}
        };
    }

    @Test(expected = IllegalStateException.class)
    public void testSetLogFileTwice() {
        IImporter subject = getSubject();
        File file = getFileInput();
        Assert.assertTrue(subject.canRead(file));
        setContext(subject, file);
        setContext(subject, file);

    }

    private void setContext(final IImporter subject, final File file) {
        Logset logset = Logset.valueOf(UUID.randomUUID());
        logset.setProgressMonitor(new NullProgressMonitor());
        subject.canRead(file);
        subject.setContext(ImportContext.valueOf(logset, file));
    }

    @Test
    public final void testResolvedArticats() {

        Logset logset = Logset.valueOf(UUID.randomUUID());
        File file = getFileInput();

        IImporter first = getSubject();
        ImportContext contextA = ImportContext.valueOf(logset, file);
        Assert.assertTrue(first.canRead(file));
        first.setContext(contextA);

        for (ILogEvent iLogEvent : first.getLogEvents()) {
            assertNotNull(iLogEvent);
        }
        for (IArtifact iArtifact : first.getArtifacts()) {
            assertNotNull(iArtifact);
        }

        IImporter second = getSubject();
        Assert.assertTrue(second.canRead(file));
        second.setContext(contextA);

        for (ILogEvent iLogEvent : second.getLogEvents()) {
            assertResolved(contextA, iLogEvent);
        }

    }

    @Test
    public final void testGetLogEventsTwice() {
        IImporter subject = getSubject();
        File file = getFileInput();
        subject.canRead(file);
        setContext(subject, file);
        int count = 0;
        for (ILogEvent event : subject.getLogEvents()) {
            assertNotNull(event);
            count++;
        }
        // The second time
        int secondCount = 0;
        for (ILogEvent event : subject.getLogEvents()) {
            assertNotNull(event);
            secondCount++;
        }
        assertEquals(count, secondCount);

    }

    private void assertResolved(final ImportContext contextA, final IObject obj) {
        for (IArtifact a : obj.referencedArtifacts()) {
            System.out.println(a.getClassName());
            if (!a
                    .getClassName()
                    .equals(
                            "com.zealcore.se.external.ericsson.typepackages.rosert.RoseStateMachine")
                    && a
                            .getClassName()
                            .equals(
                                    "com.zealcore.se.external.ericsson.typepackages.rosert.RoseState")) {

                String assertString = "The Artifact \r\n "
                        + a
                        + ":"
                        + a.getClass().getName()
                        + "\r\n must have been previously resolved into the import context";
                assertNotNull(assertString, contextA.getArtifact(a));
                assertSame(assertString, contextA.resolveArtifact(a), a);
                assertResolved(contextA, a);
            }
        }
    }
}
