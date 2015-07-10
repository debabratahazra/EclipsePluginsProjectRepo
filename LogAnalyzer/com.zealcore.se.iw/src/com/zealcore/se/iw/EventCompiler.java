/**
 * 
 */
package com.zealcore.se.iw;

import java.util.ArrayList;
import java.util.List;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.core.util.TimestampUtil;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.IModifyingFieldType;
import com.zealcore.se.iw.types.internal.ITimestampType;
import com.zealcore.se.iw.wizard.internal.AbstractTextTreeVisitor;
import com.zealcore.se.iw.wizard.internal.Field;
import com.zealcore.se.iw.wizard.internal.Header;
import com.zealcore.se.iw.wizard.internal.Message;

final class EventCompiler extends AbstractTextTreeVisitor {
    private final List<ILogEvent> events = new ArrayList<ILogEvent>();

    private GenericLogEvent current;

    private final String defaultName;

    private final GenericLogFile logFile;

    private TimestampUtil timestamp;

    private static long sequenceNumber;

    EventCompiler(final String defaultName, final GenericLogFile logFile) {
        this.defaultName = defaultName;
        this.logFile = logFile;
        EventCompiler.sequenceNumber = 0L;
    }

    public List<ILogEvent> getEvents() {
        if (this.current != null) {
            this.events.add(this.current);
            this.current = null;
        }
        final ArrayList<ILogEvent> copy = new ArrayList<ILogEvent>(this.events
                .size());
        copy.addAll(this.events);
        return copy;
    }

    @Override
    public void visitHeader(final Header header) {
        this.logFile.addProperty("Text", header.toString());
    }

    @Override
    public void visitMessage(final Message message) {
        if (this.current != null) {
            this.events.add(this.current);
        }
        this.current = new GenericLogEvent();
        this.timestamp = new TimestampUtil();
        this.current.setLogFile(this.logFile);
        this.current.setTypeName(this.defaultName);
        this.current.setTs(EventCompiler.sequenceNumber++);
    }

    @Override
    public void visitField(final Field field) {

        final FieldDescriptor desc = field.getDescriptor();
        final IFieldType type = desc.getType();
        final String trim = field.toString().trim();

        if (type instanceof IModifyingFieldType) {
            final IModifyingFieldType modifier = (IModifyingFieldType) type;
            modifier.modify(this.current, trim);
        } else if (type instanceof ITimestampType) {
            final ITimestampType modifier = (ITimestampType) type;
            modifier.modify(this.current, this.timestamp, trim);
        } else {
            this.current.addProperty(desc.getName(), type.valueOf(trim));
        }

    }
}
