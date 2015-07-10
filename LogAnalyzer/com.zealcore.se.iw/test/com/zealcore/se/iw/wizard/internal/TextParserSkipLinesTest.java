package com.zealcore.se.iw.wizard.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericTextImportData;
import com.zealcore.se.iw.types.internal.ImportBehaviour;

public class TextParserSkipLinesTest {

	private static final String WILD_CARD = ".*";

	private static final String STR_NO_FILE = "NO-FILE";

	private static final String FIELD_DELIM = " ";

	private static final String LINE_DELIM = "\n";

	public void testSkipLines() throws IOException {
		final TextParser subject = new TextParser();
		final String data = "1a FOOa BARa\n---------\n#########\n2a BAZa KAZa\n";

		final TextParserTest.FieldType dummyType = new TextParserTest.FieldType();

		final FieldDescriptor d1 = new FieldDescriptor();
		d1.setDelimiter(TextParserSkipLinesTest.FIELD_DELIM);
		d1.setType(dummyType);

		final FieldDescriptor d2 = new FieldDescriptor();
		d2.setDelimiter(TextParserSkipLinesTest.FIELD_DELIM);
		d2.setType(dummyType);

		final FieldDescriptor d3 = new FieldDescriptor();
		d3.setDelimiter(TextParserSkipLinesTest.LINE_DELIM);
		d3.setType(dummyType);

		final ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
		fields.add(d1);
		fields.add(d2);
		fields.add(d3);

		final ImportBehaviour importBehaviour = new ImportBehaviour(false, 0);

		final Set<String> filters = new HashSet<String>();
		filters.add("-*");
		filters.add("#*");

		final GenericTextImportData config = new GenericTextImportData(
				"BazEvent", fields, filters, importBehaviour, new File(
						TextParserSkipLinesTest.STR_NO_FILE));

		final MessageTree tree = subject.parse(data, config);

		final TreeValidator validator = new TreeValidator(0, 2, 3);

		tree.accept(validator);

		tree.accept(new TextFieldValidator(new String[] { "1a", "FOOa", "BARa",
				"2a", "BAZa", "KAZa", }));
		validator.verify();
		// //System.out.println(tree);
	}

	@Test
	public void testSkipLinesPrintSpoolNormal() throws IOException {

		final TextParserTest.FieldType dummyType = new TextParserTest.FieldType();

		final FieldDescriptor d3 = new FieldDescriptor();
		d3.setDelimiter(TextParserSkipLinesTest.LINE_DELIM);
		d3.setType(dummyType);

		final ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
		fields.add(d3);

		final ImportBehaviour importBehaviour = new ImportBehaviour(true, 3);

		final Set<String> filters = new HashSet<String>();
		final String filterOutAny = "Status";
		// ".*Status.*"
		// filters.add(".*");
		filters.add(TextParserSkipLinesTest.WILD_CARD + filterOutAny
				+ TextParserSkipLinesTest.WILD_CARD);

		final GenericTextImportData config = new GenericTextImportData(
				"FooEvent", fields, filters, importBehaviour, new File(
						TextParserSkipLinesTest.STR_NO_FILE));

		final TextParser subject = new TextParser();
		final MessageTree tree = subject.parse(new File(
				("printspoolNormal.log")), config);
		tree.accept(new AbstractTextTreeVisitor() {
			@Override
			public void visitMessage(final Message message) {
				Assert.assertFalse(
						"Mesages containing " + filterOutAny
								+ " should be filtered; Message= "
								+ message.toString(), message.toString()
								.contains(filterOutAny));
			}
		});

		final TreeValidator treeValidator = new TreeValidator(1, 103, 1);
		tree.accept(treeValidator);
		treeValidator.verify();

		// //System.out.println(tree);
	}

	@Test
	public void testSkipEmptyLines() throws IOException {
		final TextParser subject = new TextParser();
		final String data = "     \n\n\n1b FOOb BARb\n\n\n\n\n\n2b BAZb KAZb\n\n\n\n3b MANb KABb\n\n";

		final TextParserTest.FieldType dummyType = new TextParserTest.FieldType();

		final FieldDescriptor d1 = new FieldDescriptor();
		d1.setDelimiter(TextParserSkipLinesTest.FIELD_DELIM);
		d1.setType(dummyType);

		final FieldDescriptor d2 = new FieldDescriptor();
		d2.setDelimiter(TextParserSkipLinesTest.FIELD_DELIM);
		d2.setType(dummyType);

		final FieldDescriptor d3 = new FieldDescriptor();
		d3.setDelimiter(TextParserSkipLinesTest.LINE_DELIM);
		d3.setType(dummyType);

		final ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
		fields.add(d1);
		fields.add(d2);
		fields.add(d3);

		final ImportBehaviour importBehaviour = new ImportBehaviour(true, 0);

		final GenericTextImportData config = new GenericTextImportData(
				"BarEvent", fields, null, importBehaviour, new File(
						TextParserSkipLinesTest.STR_NO_FILE));

		final MessageTree tree = subject.parse(data, config);

		final TreeValidator validator = new TreeValidator(0, 3, 3);

		tree.accept(validator);

		tree.accept(new TextFieldValidator(new String[] { "1b", "FOOb", "BARb",
				"2b", "BAZb", "KAZb", "3b", "MANb", "KABb", }));
		validator.verify();
	}

	private static class TextFieldValidator extends AbstractTextTreeVisitor {

		private int fieldCount;

		private final String[] text;

		public TextFieldValidator(final String[] text) {
			super();
			this.text = text;
		}

		@Override
		public void visitField(final Field field) {
			// //System.out.println("Field: " + field.toString());
			Assert.assertEquals("Wrong Field Text Representation",
					this.text[this.fieldCount++], field.toString());
		}
	}
}
