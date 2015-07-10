package com.zealcore.se.iw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.ImportBehaviour;
import com.zealcore.se.iw.types.internal.NameType;
import com.zealcore.se.iw.types.internal.StringType;
import com.zealcore.se.iw.types.internal.TimestampTypeTimeMicrosec;
import com.zealcore.se.iw.types.internal.TimestampTypeYMD;

public class GenericTextImporterTest {

	private static final long L1175164640609288000 = 1175164640609288000L;

	private static final int CASE_0 = 0;

	private static final int CASE_1 = 1;

	private static final int CASE_2 = 2;

	private static final int CASE_3 = 3;

	private static final int CASE_4 = 4;

	private static final int CASE_5 = 5;

	private static final int CASE_6 = 6;

	private static final int CASE_7 = 7;

	private static final String TESTLOGS_ONEPRINTSPOOLEVENT_LOG = "testlogs\\oneprintspoolevent.log";

	private static final String TS_DATE = "ts date";

	private static final String TS_TIME = "ts time";

	private static final String FILE = "File";

	private static final String ROW = "Row";

	private static final String TASK_ID = "Task-id";

	// private static final String TO_STRING = "ToString: ";

	private static final String DATA = "data";

	private static final String NEWLINE = "\n";

	// private static final String SPACE_COLON_SPACE = " : ";

	// private static final String LINES = "------------------------------------------";

	private static final String SPACE = " ";

	private MemoryRegistry registry;

	private FieldDescriptor newField(final String name, final IFieldType type,
			final String delim) {
		final FieldDescriptor field = new FieldDescriptor();
		field.setDelimiter(delim);
		field.setName(name);
		field.setType(type);
		return field;
	}

	@Test
	public void testPrintspoolEasy() {

		final File file = new File("printspooleasy.log");
		final List<FieldDescriptor> descs = new ArrayList<FieldDescriptor>();
		descs.add(newField(GenericTextImporterTest.TS_DATE,
				new TimestampTypeYMD(), GenericTextImporterTest.SPACE));
		descs
				.add(newField(GenericTextImporterTest.TS_TIME,
						new TimestampTypeTimeMicrosec(),
						GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.FILE, new StringType(),
				GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.ROW, new StringType(),
				GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.TASK_ID, new NameType(),
				GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.DATA, new StringType(),
				GenericTextImporterTest.NEWLINE));
		final ImportBehaviour impBehaviour = new ImportBehaviour(true, 0);

		final GenericTextImportData data = new GenericTextImportData(
				"Printspool Easy", descs, null, impBehaviour, file);

		this.registry.addImportData(data);

		final GenericTextImporter subject = new GenericTextImporter();
		ImportContext context = ImportContext.valueOf(null, file);
		subject.setContext(context);

		// for (final ILogEvent event : subject.getLogEvents()) {
		// //System.out.println(GenericTextImporterTest.TO_STRING + event);
		// for (final SEProperty p : event.getZPropertyAnnotations()) {
		// //System.out.println(p.getName()
		// + GenericTextImporterTest.SPACE_COLON_SPACE
		// + p.getData()
		// + GenericTextImporterTest.SPACE_COLON_SPACE
		// + p.getData().getClass());
		// }
		// //System.out.println(GenericTextImporterTest.LINES);
		// }
	}

	// @Test
	// Eftersom ordningen p� properties kan �ndras m�ste detta test
	// skrivas
	// om...
	public void testOnePrintspoolEvent() {

		final File file = new File(
				GenericTextImporterTest.TESTLOGS_ONEPRINTSPOOLEVENT_LOG);
		final List<FieldDescriptor> descs = new ArrayList<FieldDescriptor>();
		descs.add(newField(GenericTextImporterTest.TS_DATE,
				new TimestampTypeYMD(), GenericTextImporterTest.SPACE));
		descs
				.add(newField(GenericTextImporterTest.TS_TIME,
						new TimestampTypeTimeMicrosec(),
						GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.FILE, new StringType(),
				GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.ROW, new StringType(),
				GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.TASK_ID, new NameType(),
				GenericTextImporterTest.SPACE));
		descs.add(newField(GenericTextImporterTest.DATA, new StringType(),
				GenericTextImporterTest.NEWLINE));
		final ImportBehaviour impBehaviour = new ImportBehaviour(true, 0);

		final GenericTextImportData data = new GenericTextImportData(
				"Printspool event", descs, null, impBehaviour, file);
		this.registry.addImportData(data);

		// GenericTextImporter.setImportData(data);
		final GenericTextImporter subject = new GenericTextImporter();
		ImportContext context = ImportContext.valueOf(null, file);
		subject.setContext(context);

		for (final ILogEvent event : subject.getLogEvents()) {
			// //System.out.println(GenericTextImporterTest.TO_STRING + event);
			int i = 0;
			for (final SEProperty p : event.getZPropertyAnnotations()) {
				switch (i++) {
				case CASE_0:
					Assert.assertEquals("2007-03-29 11:37:20:609288000", p
							.getData());
					break;

				case CASE_1:
					Assert.assertEquals("GenericLogFile", p.getData()
							.toString());
					break;
				case CASE_2:
					Assert.assertEquals("", p.getData().toString());
					break;
				case CASE_3:
					// //System.out.println("2: " + p.getData()
					// + GenericTextImporterTest.SPACE_COLON_SPACE
					// + p.getData().getClass());
					Assert.assertEquals(
							GenericTextImporterTest.L1175164640609288000, p
									.getData());
					break;
				case CASE_4:
					Assert.assertEquals("3", p.getData());
					break;
				case CASE_5:
					Assert.assertEquals("(null)", p.getData());
					break;
				case CASE_6:
					Assert.assertEquals("0xd667e78", p.getData());
					break;
				case CASE_7:
					Assert.assertEquals("Status 0 from iomgrinstall simFBC", p
							.getData());
					break;
				default:
					throw new RuntimeException("Unknown data: i=" + 1);
				}
				// //System.out.println(p.getName()
				// + GenericTextImporterTest.SPACE_COLON_SPACE
				// + p.getData()
				// + GenericTextImporterTest.SPACE_COLON_SPACE
				// + p.getData().getClass());
			}
			// Assert.assertEquals(1175164640609288000L,
			// event.getTs());
			// Assert.assertEquals("200 7-03-29 11:37:20:609288",
			// event.getDate());
			// Assert.assertEquals(TESTLOGS_ONEPRINTSPOOLEVENT_LOG,
			// event.getLogFile().getFileName());
			// Assert.assertEquals("20 07-03-29 11:37:20:609288",
			// event.getZPropertyAnnotations().get(0));

			// System.out.println(GenericTextImporterTest.LINES);
		}
	}

	@Before
	public void createServiceProvider() {
		final IMocksControl control = EasyMock.createControl();
		final IServiceProvider provider = control
				.createMock(IServiceProvider.class);
		this.registry = new MemoryRegistry();
		EasyMock.expect(provider.getService(GenericImportRegistry.class))
				.andReturn(this.registry).anyTimes();
		control.replay();

		GenericTextImporter.setServiceProvider(provider);
	}
}
