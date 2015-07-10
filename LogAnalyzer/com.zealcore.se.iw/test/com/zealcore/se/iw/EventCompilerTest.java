package com.zealcore.se.iw;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.iw.wizard.internal.MessageTree;
import com.zealcore.se.iw.wizard.internal.TextParser;

public class EventCompilerTest {

	private static final int EXPECTED_NUM_EVENTS = 4;

	@Test
	public void testGetEvents() throws IOException {

		final GenericLogFile logFile = new GenericLogFile();

		final GenericTextImportData data = new GenericTextImportData();
		data.setDefaultEventName("foo");
		final FieldDescriptor field = TestUtil.newDummyField("foobar", " ");

		data.setDescriptors(Arrays.asList(field));
		final TextParser parser = new TextParser();
		final MessageTree tree = parser.parse("foo bar baz kaz", data);

		final EventCompiler subject = new EventCompiler("Foo", logFile);
		tree.accept(subject);
		final List<ILogEvent> events = subject.getEvents();

		Assert.assertEquals("Unexpected number of events",
				EventCompilerTest.EXPECTED_NUM_EVENTS, events.size());

		for (final ILogEvent event : events) {
			Assert.assertSame("LogFile must be set properly", logFile, event
					.getLogFile());
		}
	}
}
