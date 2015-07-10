package com.zealcore.se.iw;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.IModifyingFieldType;

final class TestUtil {
    private TestUtil() {}

    static FieldDescriptor newField(final String name, final IFieldType type,
            final String delim) {
        final FieldDescriptor field = new FieldDescriptor();
        field.setDelimiter(delim);
        field.setName(name);
        field.setType(type);
        return field;
    }

    static MemoryRegistry newMemoryRegistry() {
        return new MemoryRegistry();
    }

    static FieldDescriptor newDummyField(final String name, final String delim) {
        return TestUtil.newField(name, DummyFieldType.instance, delim);
    }

    private static class DummyFieldType implements IModifyingFieldType {

        private static final String DUMMY_FIELD_TYPE = "DummyFieldType";

        private static DummyFieldType instance = new DummyFieldType();

        public void modify(final GenericLogEvent event, final String value) {
            // Do nothing.
        }

        public String getId() {
            return DummyFieldType.DUMMY_FIELD_TYPE;
        }

        public String getLabel() {
            return DummyFieldType.DUMMY_FIELD_TYPE;
        }

        public Object valueOf(final String text) {
            return text;
        }

        public boolean canMatch(final String proposal) {
            return false;
        }
    }
}
