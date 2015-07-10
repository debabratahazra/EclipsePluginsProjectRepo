package com.zealcore.srl.offline;

import java.io.File;
import java.io.IOException;
import java.util.List;

class StringResolver implements IResolver {

    public void resolve(final Blackbox box, final AbstractTypedMessage message,
            final File externalResource) {

        final List<Field> fields = message.getStruct().getFields();
        for (final Field field : fields) {
            final Object value = message.getValue(field.getName());
            if (value instanceof IPointer) {
                final IPointer p = (IPointer) value;
                if (p.getId() == Type.External.e_string.getId()) {
                    resolve(p, box, externalResource);
                }
            }
        }
    }

    private void resolve(final IPointer p, final Blackbox box,
            final File resource) {
        try {
            String loadString = ResolveUtil.loadString(p.getPointer(), resource, box
                    .getMagicString(), box.getMagicPhys());
            p.setValue(loadString);
        } catch (final IOException e) {}

    }
}
