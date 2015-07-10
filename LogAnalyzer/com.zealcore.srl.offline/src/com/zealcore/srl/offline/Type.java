package com.zealcore.srl.offline;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Type {
    private static final long UINT_MASK = 0xFFFFFFFFL;

    static final int ID = 1001;

    private final int id;

    private final int size;

    private final String name;

    private static Set<ISrlEntityEnum> entities = new HashSet<ISrlEntityEnum>();

    private ISrlEntityEnum srlEntity;

    interface ISrlEntityEnum {
        int getId();

        void setId(int id);

        Object parse(final int size, final ByteBuffer blob);
    }

    public String getName() {
        return name;
    }

    enum Struct implements ISrlEntityEnum {
        TypeDesc, StructDesc, FieldDesc, RoseSend, RoseReceive;

        private static final int IDX_BEGIN = 1000;

        private int id;

        public int getId() {
            return id;
        }

        public void setId(final int id) {
            this.id = id;
        }

        public Object parse(final int size, final ByteBuffer blob) {
            throw new UnsupportedOperationException();
        }

        static {
            int idx = IDX_BEGIN + 1;
            for (final ISrlEntityEnum entity : values()) {
                entity.setId(idx++);
            }
        }
    }

    enum Field implements ISrlEntityEnum {
        TypeDesc_id, TypeDesc_size, TypeDesc_name, StructDesc_id, StructDesc_name, FieldDesc_id, FieldDesc_idMsg, FieldDesc_idType, FieldDesc_name;

        private static final int IDX_BEGIN = 2000;

        private int id;

        public int getId() {
            return id;
        }

        public void setId(final int id) {
            this.id = id;
        }

        static {
            int idx = IDX_BEGIN + 1;
            for (final ISrlEntityEnum entity : values()) {
                entity.setId(idx++);
            }
        }

        public Object parse(final int size, final ByteBuffer blob) {
            throw new UnsupportedOperationException();
        }
    }

    public enum Srl implements ISrlEntityEnum {
        t_int, t_float, t_unsigned_int, t_srl_U32, t_srl_I32, t_char, t_ptr_void, t_unsigned_long, t_average_int, t_max_int, t_min_int;

        private static final int IDX_BEGIN = 6000;
        private static final Map<Integer, Srl> VALUE_MAP = new HashMap<Integer, Srl>();

        private int id;

        public int getId() {
            return id;
        }

        public void setId(final int id) {
            this.id = id;
        }

        static {
            int idx = IDX_BEGIN + 1;
            
            for (final Srl entity : values()) {
                entity.setId(idx);
                VALUE_MAP.put(idx, entity);
                ++idx;
            }
        }

        public static Srl valueOf(final int id) {
            Srl entityEnum = VALUE_MAP.get(id);
            if(entityEnum == null) {
                throw new IllegalArgumentException("No value exists for id " + String.valueOf(id));
            }
            return entityEnum;
        }
        
        public Object parse(final int size, final ByteBuffer data) {
            switch (this) {
            case t_int:
                return parseInt(size, data);
            case t_float:
                return parseFloat(size, data);
            case t_unsigned_int:
                return parseInt(size, data) & UINT_MASK;
            case t_srl_U32:
                return parseInt(size, data) & UINT_MASK;
            case t_srl_I32:
                return parseInt(size, data);
            case t_char:
                return parseChar(size, data);
            case t_ptr_void:
                return parseExternalPointer(size, id, data);
            case t_unsigned_long:
                return parseInt(size, data) & UINT_MASK;
            default:
                throw new IllegalStateException();
            }
        }

    }

    enum External implements ISrlEntityEnum {
        e_string, e_signal;

        private static final int IDX_BEGIN = 7000;

        private int id;

        public int getId() {
            return id;
        }

        public void setId(final int id) {
            this.id = id;
        }

        static {
            int idx = IDX_BEGIN + 1;
            for (final ISrlEntityEnum entity : values()) {
                entity.setId(idx++);
            }
        }

        public Object parse(final int size, final ByteBuffer data) {
            switch (this) {
            case e_string:
                return parseExternalPointer(size, id, data);
            case e_signal:
                return parseExternalPointer(size, id, data);
                // throw new UnsupportedOperationException();
            default:
                throw new IllegalStateException();
            }
        }
    }

    public Type(final ByteBuffer data) {
        id = data.getInt();
        size = data.getInt();
        name = BuffUtil.getNullTermString(data);
        addEntities();
    }

    public Type(final int id, final int size, final String name) {
        this.id = id;
        this.size = size;
        this.name = name;
        addEntities();
    }

    private void addEntities() {
        entities.addAll(Arrays.asList(Struct.values()));
        entities.addAll(Arrays.asList(Field.values()));
        entities.addAll(Arrays.asList(Srl.values()));
        entities.addAll(Arrays.asList(External.values()));
        for (final ISrlEntityEnum entityEnum : entities) {
            if (entityEnum.getId() == id) {
                srlEntity = entityEnum;
                return;
            }
        }
        if (srlEntity == null) {
            throw new IllegalStateException();
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append("(").append(size).append(")");
        return sb.toString();
    }

    public Object parse(final ByteBuffer data) {
        return srlEntity.parse(size, data);
    }

    private static Object parseExternalPointer(final int size, final int id,
            final ByteBuffer data) {
        final long p = parseInt(size, data);
        return new Pointer(p, id, null);
    }

    private static Object parseFloat(final int size, final ByteBuffer data) {
        if (size != 4) {
            throw new IllegalStateException("Float size must be 4");
        }
        return data.getFloat();
    }

    private static char parseChar(final int size, final ByteBuffer data) {
        if (size != 1) {
            throw new IllegalStateException("Char size must be 1");
        }
        return (char) data.get();
    }

    private static long parseInt(final int size, final ByteBuffer data) {
        switch (size) {
        case 1:
            return data.get();
        case 2:
            return data.getShort();
        case 4:
            return data.getInt();
        case 8:
            return data.getLong();
        default:
            throw new IllegalStateException(
                    "Integer sizes must be 1,2,4 or 8 but was " + size);
        }
    }

    public Object parseNullTerm(final ByteBuffer data) {
        switch (size) {
        case 1:
            return BuffUtil.getNullTermString(data);
        default:
            throw new UnsupportedOperationException(
                    "Null terminated is currenttly only implemented for data size 1 (null term strings)");
        }

    }
}
