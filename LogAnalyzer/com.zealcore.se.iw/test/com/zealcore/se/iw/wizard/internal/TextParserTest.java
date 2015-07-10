package com.zealcore.se.iw.wizard.internal;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericTextImportData;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.ImportBehaviour;
import com.zealcore.se.iw.types.internal.TimestampTypeHour;
import com.zealcore.se.iw.types.internal.TimestampTypeTimeMicrosec;

public class TextParserTest {

	private static final String FIELD_4_2 = "NOTCOMPLETE";

	private static final String FIELD_4_1 = "4";

	private static final String FIELD_3_3 = "KAZ";

	private static final String FIELD_3_2 = "BAZ";

	private static final String FIELD_3_1 = "3";

	private static final String FIELD_2_3 = "BAR";

	private static final String FIELD_2_2 = "FOO";

	private static final String FIELD_2_1 = "2";

	private static final String FIELD_1_3 = "HEJ";

	private static final String FIELD_1_2 = "ABC";

	private static final String FIELD_DELIM = " ";

	private static final String ONE = "1";

	private static final String FIELD_1_1 = TextParserTest.ONE;

	private static final String LINE_DELIM = "\n";

	private static final String HEADER_TEXT = "HeaderText";

	private static final String SUBJECT_INPUT = TextParserTest.HEADER_TEXT
			+ TextParserTest.LINE_DELIM + TextParserTest.FIELD_1_1
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_1_2
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_1_3
			+ TextParserTest.LINE_DELIM + TextParserTest.FIELD_2_1
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_2_2
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_2_3
			+ TextParserTest.LINE_DELIM + TextParserTest.FIELD_3_1
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_3_2
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_3_3
			+ TextParserTest.LINE_DELIM + TextParserTest.FIELD_4_1
			+ TextParserTest.FIELD_DELIM + TextParserTest.FIELD_4_2;

	@Test
	public void testcanMatch() {

		final TimestampTypeHour h = new TimestampTypeHour();
		Assert.assertFalse(h.canMatch("asdf fsda 123"));
		Assert.assertFalse(h.canMatch(" 12"));
		Assert.assertFalse(h.canMatch("12 "));
		Assert.assertFalse(h.canMatch("-1"));
		Assert.assertFalse(h.canMatch("24"));
		Assert.assertTrue(h.canMatch("0"));
		Assert.assertTrue(h.canMatch(TextParserTest.ONE));
		Assert.assertTrue(h.canMatch("23"));

		final TimestampTypeTimeMicrosec t = new TimestampTypeTimeMicrosec();
		Assert.assertTrue(t.canMatch("12:12:12:123456"));
		Assert.assertTrue(t.canMatch("12:12:12:000000"));
		Assert.assertTrue(t.canMatch("12:12:12:999999"));
		Assert.assertTrue(t.canMatch("12:12:12:0"));
		Assert.assertFalse(t.canMatch("banan"));
		Assert.assertFalse(t.canMatch("12:12:12:1000000"));
		Assert.assertFalse(t.canMatch("2:12:12:1000000"));

	}

	@Test
	public void testParse() throws IOException {
		final TextParser parser = new TextParser();
		final ImportBehaviour importBehaviour = new ImportBehaviour(false, 1);
		final ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
		final FieldType dummyType = new FieldType();

		final FieldDescriptor d1 = new FieldDescriptor();
		d1.setDelimiter(TextParserTest.FIELD_DELIM);
		d1.setType(dummyType);

		final FieldDescriptor d2 = new FieldDescriptor();
		d2.setDelimiter(TextParserTest.FIELD_DELIM);
		d2.setType(dummyType);

		final FieldDescriptor d3 = new FieldDescriptor();
		d3.setDelimiter(TextParserTest.LINE_DELIM);
		d3.setType(dummyType);

		fields.add(d1);
		fields.add(d2);
		fields.add(d3);

		final GenericTextImportData config = new GenericTextImportData(
				"FooEvent", fields, null, importBehaviour, new File("NO-FILE"));
		config.getNoOfHeaderLines();

		final MessageTree tree = parser.parse(TextParserTest.SUBJECT_INPUT,
				config);

		Assert.assertEquals(1 + fields.size(), tree.getChildren().size());

		final TreeValidator validator = new TreeValidator(1, 3, 3);

		tree.accept(validator);
		validator.verify();

		final TreeFieldValidator treeFieldValidator = new TreeFieldValidator();
		tree.accept(treeFieldValidator);
		treeFieldValidator.verify();
	}

	private static final class TreeFieldValidator extends
			AbstractTextTreeVisitor {
		private final ArrayList<Integer> positions = new ArrayList<Integer>();

		private final String[] strings = { TextParserTest.FIELD_1_1,
				TextParserTest.FIELD_1_2, TextParserTest.FIELD_1_3,
				TextParserTest.FIELD_2_1, TextParserTest.FIELD_2_2,
				TextParserTest.FIELD_2_3, TextParserTest.FIELD_3_1,
				TextParserTest.FIELD_3_2, TextParserTest.FIELD_3_3, };

		private int fieldCount;

		TreeFieldValidator() {
			Integer position = new Integer(0);

			position += TextParserTest.HEADER_TEXT.length()
					+ TextParserTest.LINE_DELIM.length();
			this.positions.add(position);

			position += TextParserTest.FIELD_1_1.length()
					+ TextParserTest.FIELD_DELIM.length();
			this.positions.add(position);
			position += TextParserTest.FIELD_1_2.length()
					+ TextParserTest.FIELD_DELIM.length();
			this.positions.add(position);
			position += TextParserTest.FIELD_1_3.length()
					+ TextParserTest.LINE_DELIM.length();
			this.positions.add(position);

			position += TextParserTest.FIELD_2_1.length()
					+ TextParserTest.FIELD_DELIM.length();
			this.positions.add(position);
			position += TextParserTest.FIELD_2_2.length()
					+ TextParserTest.FIELD_DELIM.length();
			this.positions.add(position);
			position += TextParserTest.FIELD_2_3.length()
					+ TextParserTest.LINE_DELIM.length();
			this.positions.add(position);

			position += TextParserTest.FIELD_3_1.length()
					+ TextParserTest.FIELD_DELIM.length();
			this.positions.add(position);
			position += TextParserTest.FIELD_3_2.length()
					+ TextParserTest.FIELD_DELIM.length();
			this.positions.add(position);
			position += TextParserTest.FIELD_3_3.length()
					+ TextParserTest.LINE_DELIM.length();
			this.positions.add(position);
		}

		@Override
		public void visitField(final Field field) {
			Assert.assertEquals("Field " + this.fieldCount
					+ " (0 based) has wrong position", this.positions
					.get(this.fieldCount), Integer.valueOf(field.start()));
			Assert
					.assertEquals(this.strings[this.fieldCount], field
							.toString());
			this.fieldCount++;
		}

		private void verify() {
			Assert.assertEquals(this.strings.length, this.fieldCount);
		}
	}

	static class FieldType implements IFieldType {

		public String getId() {
			return null;
		}

		public String getLabel() {
			return null;
		}

		public Object valueOf(final String text) {
			return text;
		}

		public boolean canMatch(final String proposal) {
			return false;
		}

	}

	@Test
	public void testReadLine() throws UnsupportedEncodingException {
		final String TEST_CR = "apa\r";
		final String TEST_LF = "apa\n";
		final String TEST_CRLF = "apa\r\n";

		final TextParser parser = new TextParser();
		String line = parser.readLine(new DataInputStream(
				new ByteArrayInputStream(TEST_CR.getBytes())));
		Assert.assertEquals(4, line.length());

		line = parser.readLine(new DataInputStream(new ByteArrayInputStream(
				TEST_LF.getBytes())));
		Assert.assertEquals(4, line.length());

		line = parser.readLine(new DataInputStream(new ByteArrayInputStream(
				TEST_CRLF.getBytes())));
		Assert.assertEquals(5, line.length());

		// Read from file
		try {
			line = parser.readLine(new RandomAccessFile(new File(
					"include_backslash_n.txt"), "r"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Changed from 16 to 15 after some recalculation.
		Assert.assertEquals(15, line.length());

		try {
			line = parser.readLine(new RandomAccessFile(new File(
					"not_include_backslash_n.txt"), "r"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(15, line.length());

		// read the whole file, including the EOF
		int lineCnt = 0;
		try {
			RandomAccessFile file = new RandomAccessFile(new File(
					"include_backslash_n.txt"), "r");
			do {
				line = parser.readLine(file);
				lineCnt++;
			} while (line != null && line.length() > 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(4, lineCnt);

	}
}
