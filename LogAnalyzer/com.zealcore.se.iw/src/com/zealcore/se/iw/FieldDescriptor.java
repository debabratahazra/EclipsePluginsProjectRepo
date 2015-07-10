/**
 * 
 */
package com.zealcore.se.iw;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.ui.IMemento;

import com.zealcore.se.iw.types.internal.FieldTypeFactory;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.StringType;

public class FieldDescriptor {
    static final String KEY_NAME = "name";

    static final String KEY_DELIMITER = "delimiter";

    private static final String TAG_EDIT_ME = "";

    static final String KEY_TYPE = "type";

    private String name = FieldDescriptor.TAG_EDIT_ME;

    private IFieldType type = StringType.getInstance();

    private String delimiter = FieldDescriptor.TAG_EDIT_ME;

    public FieldDescriptor(final IMemento memento) {
        this.name = memento.getString(FieldDescriptor.KEY_NAME);
        this.delimiter = memento.getString(FieldDescriptor.KEY_DELIMITER);
        this.type = FieldTypeFactory.getInstance().getFieldType(
                memento.getString(FieldDescriptor.KEY_TYPE));
    }

    public FieldDescriptor() {}

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    public String getName() {
        return this.name.trim();
    }

    public void setName(final String name) {
        this.name = name;
    }

    public IFieldType getType() {
        return this.type;
    }

    public void setType(final IFieldType type) {
        this.type = type;
    }

    public boolean isFull() {
        try {
            Pattern.compile(getDelimiter());
        } catch (final PatternSyntaxException ex) {
            return false;
        }
        return !getName().equals(FieldDescriptor.TAG_EDIT_ME)
                && getType() != null
                && !getDelimiter().equals(FieldDescriptor.TAG_EDIT_ME);
    }

    public void save(final IMemento fieldMemento) {
        fieldMemento.putString(FieldDescriptor.KEY_NAME, getName());
        fieldMemento.putString(FieldDescriptor.KEY_DELIMITER, getDelimiter());
        fieldMemento.putString(FieldDescriptor.KEY_TYPE, getType().getId());
    }

    public FieldDescriptor copy() {
        final FieldDescriptor cpy = new FieldDescriptor();
        cpy.setDelimiter(getDelimiter());
        cpy.setName(getName());
        cpy.setType(getType());
        return cpy;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof FieldDescriptor)) {
            return false;
        }
        final FieldDescriptor other = (FieldDescriptor) obj;

        if (!getDelimiter().equals(other.getDelimiter())) {
            return false;
        }
        if (!getName().equals(other.getName())) {
            return false;
        }
        if (!getType().equals(other.getType())) {
            return false;
        }
        return true;
    }
}
