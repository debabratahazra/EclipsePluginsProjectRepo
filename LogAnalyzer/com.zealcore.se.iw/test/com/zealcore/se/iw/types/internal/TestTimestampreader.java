package com.zealcore.se.iw.types.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampUtil;
import com.zealcore.se.iw.FieldDescriptor;

public final class TestTimestampreader {
    public static final String NEWLINE = System.getProperty("line.separator");

    private static final String TS_DATE = "ts date";

    private static final String FILE = "File";

    private static final String ROW = "Row";

    private static final String TASK_ID = "Task-id";

    private static final String DATA = "data";

    private static final String SPACE = " ";

    private static final String LINE = "-";

    private static final String COLON = ":";

    private static final String TIME = "2007-03-29 11:37:20:617073";

    private FieldDescriptor newField(final String name, final IFieldType type,
            final String delim) {
        final FieldDescriptor field = new FieldDescriptor();
        field.setDelimiter(delim);
        field.setName(name);
        field.setType(type);
        return field;
    }

    @Test
    public void testParseDateAndTime() /* throws ImportException */{

        final Scanner scanner = new Scanner(TestTimestampreader.TIME
                + " (null) 6 0xd667e72 Status 0 from iomgrinstall rccfbc");

        final List<FieldDescriptor> descs = new ArrayList<FieldDescriptor>();

        descs.add(newField(TestTimestampreader.TS_DATE,
                new TimestampTypeYear(), TestTimestampreader.LINE));
        descs.add(newField(TestTimestampreader.TS_DATE,
                new TimestampTypeMonth(), TestTimestampreader.LINE));
        descs.add(newField(TestTimestampreader.TS_DATE, new TimestampTypeDay(),
                TestTimestampreader.SPACE));
        descs.add(newField(TestTimestampreader.TS_DATE,
                new TimestampTypeHour(), TestTimestampreader.COLON));
        descs.add(newField(TestTimestampreader.TS_DATE,
                new TimestampTypeMinute(), TestTimestampreader.COLON));
        descs.add(newField(TestTimestampreader.TS_DATE,
                new TimestampTypeSecond(), TestTimestampreader.COLON));
        descs.add(newField(TestTimestampreader.TS_DATE,
                new TimestampTypeMicrosecond(), TestTimestampreader.SPACE));

        descs.add(newField(TestTimestampreader.FILE, new StringType(),
                TestTimestampreader.SPACE));
        descs.add(newField(TestTimestampreader.ROW, new StringType(),
                TestTimestampreader.SPACE));
        descs.add(newField(TestTimestampreader.TASK_ID, new NameType(),
                TestTimestampreader.SPACE));
        descs.add(newField(TestTimestampreader.DATA, new StringType(),
                TestTimestampreader.NEWLINE));

        final GenericLogEvent event = new GenericLogEvent();

        final TimestampUtil timestamp = new TimestampUtil();
        for (final FieldDescriptor descriptor : descs) {
            scanner.useDelimiter(descriptor.getDelimiter());
            String next;
            if (scanner.hasNext()) {
                next = scanner.next().trim();
                if (scanner.hasNext()) {
                    scanner.skip(descriptor.getDelimiter());
                }
            } else {
                break;
            }

            final IFieldType type = descriptor.getType();
            if (type instanceof IModifyingFieldType) {
                final IModifyingFieldType modifier = (IModifyingFieldType) type;
                modifier.modify(event, next);
            } else if (type instanceof ITimestampType) {
                final ITimestampType modifier = (ITimestampType) type;
                modifier.modify(event, timestamp, next);
                // //System.out.println("Event.ts: " + event.getTs()
                // + " Event.date: " + event.getDate() + " timestamp: "
                // + timestamp.getTs() + " Next: " + next);
            } else {
                event.addProperty(descriptor.getName(), type.valueOf(next));
            }
        }
        // //System.out.println(event);
        Assert.assertEquals(TestTimestampreader.TIME + "000", event.getDate());

    }

}
