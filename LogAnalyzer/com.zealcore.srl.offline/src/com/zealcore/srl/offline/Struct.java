package com.zealcore.srl.offline;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Struct {
    private static final String NEWLINE = "\n";

    private final List<Field> fields = new LinkedList<Field>();

    static final int ID = 1002;

    public Struct(final ByteBuffer data) {
        id = data.getInt();
        name = BuffUtil.getNullTermString(data);
    }

    public Struct(final int structId, final String structName) {
        id = structId;
        name = structName;
    }

    private int id;

    private final String name;

    public int getId() {
        return id;
    }

    public void setId(final int srlTypeId) {
        id = srlTypeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Struct ").append(name);
        sb.append(" [").append(id).append("] {").append(NEWLINE);
        for (final Field f : fields) {
            sb.append("    ").append(f).append(NEWLINE);
        }
        sb.append("}").append(NEWLINE);
        return sb.toString();
    }

    public void addField(final Field f) {
        fields.add(0, f);
    }

    public List<Field> getFields() {
        return Collections.unmodifiableList(fields);
    }

}
