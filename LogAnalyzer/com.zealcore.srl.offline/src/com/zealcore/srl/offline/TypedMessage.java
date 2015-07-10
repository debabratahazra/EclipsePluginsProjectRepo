package com.zealcore.srl.offline;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

abstract class AbstractTypedMessage {

    private static final String RIGHT_CURLYBRACE = "}";

    private static final String NEWLINE = "\n";

    private AbstractMessage message;

    private Struct struct;

    private final Map<String, Object> values = new LinkedHashMap<String, Object>();

    public AbstractTypedMessage(final AbstractMessage message,
            final Struct struct) {
        this.message = message;
        this.struct = struct;
        parse(message);
    }

    abstract String getType();

    public Object getValue(final String key) {
        return values.get(key);
    }

    protected void parse(final AbstractMessage message) {
        message.getData().rewind();
        final List<Field> fields = getStruct().getFields();
        for (final Field field : fields) {

            final Type type = field.getType();
            if (field.getNumElements() > 1) {
                final Object[] arr = new Object[field.getNumElements()];
                for (int c = 0; c < arr.length; c++) {
                    arr[c] = type.parse(message.getData());
                }
                values.put(field.getName(), arr);
            } else if (field.isNullTerminated()) {
                values.put(field.getName(), type.parseNullTerm(message
                        .getData()));
            } else {
                final Object value = type.parse(message.getData());
                values.put(field.getName(), value);
            }

        }
    }

    public AbstractMessage getMessage() {
        return message;
    }

    public Struct getStruct() {
        return struct;
    }

    @Override
    public String toString() {

        final StringBuilder typed = new StringBuilder();
        typed.append("Typed ");
        typed.append(getType());
        typed.append(" " + getStruct().getName() + " { ");
        typed.append(NEWLINE);
        for (final Entry<String, Object> entry : values.entrySet()) {
            typed.append("  ");
            typed.append(entry.getKey());
            typed.append(" = ");
            final Object value = entry.getValue();
            if (value instanceof IPointer) {
                final IPointer p = (IPointer) value;

                typed.append("Pointer {");
                Object val = p.getValue();
                if(val == null) {
                    typed.append(p.getPointer());
                } else {
                    typed.append(val);
                }
                typed.append(RIGHT_CURLYBRACE);
            } else {
                typed.append(value);
            }
            typed.append(NEWLINE);
        }
        typed.append(RIGHT_CURLYBRACE);
        typed.append(NEWLINE);
        return typed.toString();
    }
}
