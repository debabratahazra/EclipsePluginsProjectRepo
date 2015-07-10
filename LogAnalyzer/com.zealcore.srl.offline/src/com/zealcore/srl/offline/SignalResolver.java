package com.zealcore.srl.offline;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class SignalResolver implements IResolver {

    public void resolve(final Blackbox box, final AbstractTypedMessage message,
            final File externalResource) {

        final Iterator<Field> fieldItr = message.getStruct().getFields().iterator();
        while (fieldItr.hasNext()) {
            final Field field = fieldItr.next();
            final Object value = message.getValue(field.getName());
            if (value instanceof IPointer) {
                final IPointer p = (IPointer) value;
                if (p.getId() == Type.External.e_signal.getId()) {
                    final Field signalId = fieldItr.next();
                    final Object signalIdValue = message.getValue(signalId.getName());
                    if (signalIdValue instanceof Long) {
                        resolve(p, box, (Long) signalIdValue, externalResource);
                    }
                }
            }
        }
    }

    private void resolve(final IPointer p, final Blackbox box,
            final long signalId, final File resource) {
        try {

            Object value = null;
            if (p.getPointer() == 0L) {
                value = getInternalSignal(signalId);
            } else {
                value = ResolveUtil.loadSignalName(p.getPointer(), signalId,
                        resource, box, box.getByteOrder());
            }
            p.setValue(value);
        } catch (final IOException e) {}
    }

    private Object getInternalSignal(final long signalId) {
        if (signalId == 1) {
            return "RTInitSignal";
        } else if (signalId == 2) {
            return "RTStartSignal";
        } else if (signalId == 3) {
            return "RTStopSignal";
        } else if (signalId == 4) {
            return "RTDestroySignal";
        } else if (signalId == 5) {
            return "RTInterruptsignal";
        } else if (signalId == 6) {
            return "RTTimeoutSignal";
        } else if (signalId == 7) {
            return "RTPendingTimeoutSignal";
        } else if (signalId == 8) {
            return "RTAckSignal";
        } else if (signalId == 9) {
            return "RTWakeupSignal";
        } else if (signalId == 10) {
            return "RTProbeSignal";
        } else {
            return null;
        }
    }
}
