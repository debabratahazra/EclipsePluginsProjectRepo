/**
 * 
 */
package com.zealcore.se.iw.wizard;

import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.IModifyingFieldType;
import com.zealcore.se.iw.types.internal.ITimestampType;
import com.zealcore.se.iw.types.internal.IgnoreType;

class FieldDescriptorCellModifier implements ICellModifier {

    private final TableViewer viewer;

    private final List<IFieldType> types;

    public FieldDescriptorCellModifier(final TableViewer viewer,
            final List<IFieldType> types) {
        this.viewer = viewer;
        this.types = types;

    }

    public boolean canModify(final Object element, final String property) {
        Object data = null;
        if (element instanceof TableItem) {
            final TableItem item = (TableItem) element;
            data = item.getData();

        } else {
            data = element;
        }
        if (data instanceof FieldDescriptor) {
            final FieldDescriptor desc = (FieldDescriptor) data;
            if (wrap(property).equals(TextFieldConfigurePage.TAG_NAME)) {
                final IFieldType fieldType = desc.getType();
                if (fieldType instanceof ITimestampType) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Object getValue(final Object element, final String property) {
        if (element instanceof FieldDescriptor) {
            final FieldDescriptor field = (FieldDescriptor) element;
            if (wrap(property).equals(TextFieldConfigurePage.TAG_NAME)) {
                final String value = field.getName();
                return wrap(value);
            }
            if (wrap(property).equals(TextFieldConfigurePage.TAG_TYPE)) {
                for (int i = 0; i < this.types.size(); i++) {
                    if (field.getType().getLabel().equals(
                            this.types.get(i).getLabel())) {
                        return i;
                    }
                }
                return 0;
            }
            if (wrap(property).equals(TextFieldConfigurePage.TAG_DELIMITER)) {
                return wrap(field.getDelimiter());
            }

        }
        return null;
    }

    private String wrap(final String value) {
        return value == null ? "" : value;
    }

    public void modify(final Object element, final String property,
            final Object value) {
        Object data = null;
        if (element instanceof TableItem) {
            final TableItem item = (TableItem) element;
            data = item.getData();

        } else {
            data = element;
        }
        if (!(data instanceof FieldDescriptor)) {
            return;
        }

        final FieldDescriptor field = (FieldDescriptor) data;
        if (wrap(property).equals(TextFieldConfigurePage.TAG_NAME)) {
            field.setName((String) value);
        }
        if (wrap(property).equals(TextFieldConfigurePage.TAG_TYPE)) {
            final IFieldType fieldType = this.types.get((Integer) value);
            field.setType(fieldType);
            if (fieldType instanceof IModifyingFieldType) {
                field.setName("name-contribution");
            } else if (fieldType instanceof ITimestampType) {
                field.setName("ts-contribution");
            } else if (fieldType instanceof IgnoreType) {
                field.setName("ignored");
            }
        }
        if (wrap(property).equals(TextFieldConfigurePage.TAG_DELIMITER)) {
            field.setDelimiter(((String) value));
            // field.setDelimiter(((String) value).replace(
            // TextFieldConfigurePage.getCharToVisializeSpaceChar(), " "));
        }
        this.viewer.refresh(true);
    }
}
