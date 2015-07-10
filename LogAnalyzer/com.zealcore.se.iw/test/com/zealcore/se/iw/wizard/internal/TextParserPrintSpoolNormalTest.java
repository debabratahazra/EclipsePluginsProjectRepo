package com.zealcore.se.iw.wizard.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericTextImportData;

public class TextParserPrintSpoolNormalTest {

	// private static final String SEP = " , ";

	@Test
	public void testPrintSpoolNormalParse() throws IOException {

		final GenericTextImportData data = new GenericTextImportData();
		data.setDefaultEventName("");
		data.setNoOfHeaderLines(3);
		data.setSkipEmptyLines(true);

		final TextParserTest.FieldType dummy = new TextParserTest.FieldType();

		final FieldDescriptor d1 = new FieldDescriptor();
		d1.setType(dummy);
		d1.setDelimiter("\n");
		final List<FieldDescriptor> desc = new ArrayList<FieldDescriptor>();
		desc.add(d1);
		data.setDescriptors(desc);

		final TextParser subject = new TextParser();

		//final long start = System.currentTimeMillis();
		final MessageTree tree = subject.parse(
				new File("printspoolNormal.log"), data);
		//System.out.println("Took: " + (System.currentTimeMillis() - start)
				//+ " ms to parse");

		tree.accept(new AbstractTextTreeVisitor() {

			private int oldStart;

			private int oldFieldEnd;

			private int fieldCounter;

			@Override
			public void visitField(final Field field) {

				final String string = "At Index "
						+ this.fieldCounter
						+ ") The Field "
						+ field
						+ " has illegal position. All Fields must come in incremental position (Last Position: "
						+ this.oldStart + ",OldEnd, " + this.oldFieldEnd
						+ " NewField (Start,End): " + field.start() + ", "
						+ (field.start() + field.length());
				// //System.out.println("Field " + fieldCounter
				// + ": Start,End, Length " + field.start() + SEP
				// + (field.start() + field.length()) + SEP
				// + field.length());
				Assert.assertTrue(string, field.start() >= this.oldStart);

				if (this.fieldCounter == 11) {
					// Changed from 957 to 941 after some recalculation.
					Assert.assertEquals("Index 11 should begin at 941", 941,
							field.start());
				}
				this.fieldCounter++;
				this.oldStart = field.start();
				this.oldFieldEnd = this.oldStart + field.length();

			}
		});

	}
}
