package com.zealcore.se.iw;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.core.tests.AbstractImporterTest;
import com.zealcore.se.iw.types.internal.IgnoreType;
import com.zealcore.se.iw.types.internal.ImportBehaviour;
import com.zealcore.se.iw.types.internal.NameType;
import com.zealcore.se.iw.types.internal.StringType;
import com.zealcore.se.iw.types.internal.TimestampTypeTimeMicrosec;
import com.zealcore.se.iw.types.internal.TimestampTypeYMD;

public class GenericImporterTest extends AbstractImporterTest {
    private static final String FILE = "File";

    private static final String ROW = "Row";

    private static final String TASK_ID = "Task-id";

    private static final String TS = "ts";

    public static final String NEWLINE = System.getProperty("line.separator");

    private static final String SPACE = " ";

    private static final String PRINT_SPOOL_EASY = "printspooleasy.log";

    private com.zealcore.se.iw.MemoryRegistry registry;


    @Test
    public void dummy() {

    }

    @Override
    protected void noExceptionDuringCorruptFile() {
        System.out
                .println("WARNING: This importer does not throw any exceptions on corrupt files");
    }

    @Override
    protected void endArtifacts(final int numArtifacts) {
    }

    @Override
    protected void endEvents(final int numEvents) {
    }

    @Override
    protected File getFileInput() {
        return new File(GenericImporterTest.PRINT_SPOOL_EASY);
    }

    @Override
    protected IImporter getSubject() {
        final List<FieldDescriptor> descs = new ArrayList<FieldDescriptor>();
        descs.add(TestUtil.newField(GenericImporterTest.TS,
                new TimestampTypeYMD(), GenericImporterTest.SPACE));
        descs.add(TestUtil.newField(GenericImporterTest.TS,
                new TimestampTypeTimeMicrosec(), GenericImporterTest.SPACE));
        descs.add(TestUtil.newField(GenericImporterTest.FILE, new IgnoreType(),
                GenericImporterTest.SPACE));
        descs.add(TestUtil.newField(GenericImporterTest.ROW, new StringType(),
                GenericImporterTest.SPACE));
        descs.add(TestUtil.newField(GenericImporterTest.TASK_ID,
                new NameType(), GenericImporterTest.SPACE));
        descs.add(TestUtil.newField("Data", new StringType(),
                GenericImporterTest.NEWLINE));
        final ImportBehaviour impBehaviour = new ImportBehaviour(true, 0);

        final File file = new File(GenericImporterTest.PRINT_SPOOL_EASY);
        final GenericTextImportData data = new GenericTextImportData(
                "Printspool Header", descs, null, impBehaviour, file);
        this.registry.addImportData(data);
        final GenericTextImporter subject = new GenericTextImporter();
        return subject;
    }

    @Override
    protected String validate(final int i, final IArtifact artifact) {
        return null;
    }

    @Override
    protected String validate(final int index, final ILogEvent event) {
        final Set<String> proprs = new HashSet<String>();
        final Map<Object, String> data = new HashMap<Object, String>();
        for (final SEProperty property : event.getZPropertyAnnotations()) {
            Assert.assertFalse("The property " + property.getName()
                    + " may only exist once", proprs.contains(property
                    .getName()));
            proprs.add(property.getName());

            if (data.containsKey(property.getData())) {
                System.err.println("The data on property '"
                        + property.getName() + "' already exist as '"
                        + data.get(property.getData()) + "'");
            }
            data.put(property.getData(), property.getName());
        }
        return null;
    }

    @Before
    public void createServiceProvider() {
        final IMocksControl control = EasyMock.createControl();
        final IServiceProvider provider = control
                .createMock(IServiceProvider.class);
        this.registry = TestUtil.newMemoryRegistry();
        EasyMock.expect(provider.getService(GenericImportRegistry.class))
                .andReturn(this.registry).anyTimes();
        control.replay();

        GenericTextImporter.setServiceProvider(provider);
    }
}
