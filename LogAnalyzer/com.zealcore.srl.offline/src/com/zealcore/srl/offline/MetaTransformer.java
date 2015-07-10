package com.zealcore.srl.offline;

import java.util.Collection;
import java.util.Iterator;

public class MetaTransformer implements ITransformer {

    private static final String WARNING_MISSING_TYPE_DEFINITION_FOR_MESSAGE_ID = "WARNING: Missing type definition for message id ";

    // Begin temp fix for #663
    private static final int CPU_TICK_FREQ_STRUCT_ID = 1019;
    // End temp fix for #663
    
    private static final String HASHES = "#######################";

    public void transform(final Blackbox blackbox) {
        buildStructs(blackbox);
        if (blackbox.isVerboseMode()) {
            Logger.log(HASHES);
            Logger.log(blackbox);
        }
        buildTypedMessages(blackbox);
    }

    public void transform(final Blackbox blackbox, final boolean verbose) {
        buildStructs(blackbox);
        if (verbose) {
            Logger.log(HASHES);
            Logger.log(blackbox);
        }
        buildTypedMessages(blackbox);
    }

    private void buildTypedMessages(final Blackbox blackbox) {
        final Collection<LinearMessage> linearMessages = blackbox
                .getLinearMessages();
        for (final LinearMessage message : linearMessages) {
            final Struct struct = blackbox.getStruct(message.getTypeId());
            if (struct != null) {
                final TypedLinearMessage typedMessage = new TypedLinearMessage(
                        message, struct);
                blackbox.addTypedLinearMessage(typedMessage);
            }
        }
        final Collection<CircularMessage> circularMessages = blackbox
                .getCircularMessages();
        for (final CircularMessage message : circularMessages) {
            final Struct struct = blackbox.getStruct(message.getTypeId());
            if (struct != null) {
                final TypedCircularMessage typedMessage = new TypedCircularMessage(
                        message, struct);
                blackbox.addTypedCircularMessage(typedMessage);
            } else {
                Logger.err(WARNING_MISSING_TYPE_DEFINITION_FOR_MESSAGE_ID
                        + message.getTypeId());
                throw new IllegalStateException(WARNING_MISSING_TYPE_DEFINITION_FOR_MESSAGE_ID
                        + message.getTypeId());
            }
        }
    }

    private void buildStructs(final Blackbox blackbox) {

        // Begin temp fix for #663
        LinearMessage lastMessage = null;
        LinearMessage failedMessage = null;
        // End temp fix for #663

        for (final Iterator<LinearMessage> iter = blackbox.getLinearMessages()
                .iterator(); iter.hasNext();) {
            final LinearMessage message = iter.next();
            switch (message.getTypeId()) {
            case Struct.ID:
                final Struct s = new Struct(message.getData());
                blackbox.addStruct(s.getId(), s);

                // Begin temp fix for #663
                if (s.getId() == CPU_TICK_FREQ_STRUCT_ID) {
                    if (failedMessage != null) {
                        throw new IllegalStateException(
                                "multible struct definitions for CpuTickFreq");
                    }
                    failedMessage = lastMessage;
                }
                // End temp fix for #663

                iter.remove();
                break;

            case Type.ID:
                final Type t = new Type(message.getData());
                blackbox.addType(t.getId(), t);
                iter.remove();
                break;
            default:
                // Begin temp fix for #663
                lastMessage = message;
                // End temp fix for #663
                break;
            }
        }
        for (final Iterator<LinearMessage> iter = blackbox.getLinearMessages()
                .iterator(); iter.hasNext();) {
            final LinearMessage message = iter.next();
            switch (message.getTypeId()) {
            case Field.ID:
                final Field f = new Field(message.getData());
                final Type t = blackbox.getType(f.getIdType());
                f.setType(t);

                // Begin temp fix for #663
                if (message == failedMessage) {
                    f.setIdStruct(CPU_TICK_FREQ_STRUCT_ID);
                }
                // End temp fix for #663

                final Struct s = blackbox.getStruct(f.getIdStruct());
                s.addField(f);
                iter.remove();
                break;
            default:
                // throw new IllegalStateException();
            }
        }
    }
}
