package com.zealcore.srl.offline;

import java.util.Collection;

public class TimestampTransformer implements ITransformer {

    private static final class CpuTickFreqMessage {

        private final AbstractTypedMessage typedMessage;

        private CpuTickFreqMessage(final AbstractTypedMessage typedMessage) {
            this.typedMessage = typedMessage;
        }

        private Long getFreq() {
            Long value = (Long) typedMessage.getValue("freq");
            if (value == null) {
                throw new IllegalStateException(
                        "Message misses property 'freq'");
            }
            return value;
        }

    }

    public void transform(final Blackbox blackbox) {
        CpuTickFreqMessage cpuTickFreqMessage = getCpuTickFreqMessage(blackbox);
        if (cpuTickFreqMessage != null) {
            blackbox.setCpuTickFreq(cpuTickFreqMessage.getFreq());
            Long frequency = cpuTickFreqMessage.getFreq();
            updateTimestamps(blackbox, frequency);
        }
    }

    private void updateTimestamps(final Blackbox blackbox, final Long frequency) {
        if (frequency <= 0) {
            return;
        }

        Iterable<CircularMessage> circularMessages = blackbox
                .getCircularMessages();
        for (CircularMessage circularMessage : circularMessages) {
            long oldTs = circularMessage.getTs();
            double k = 1000000000.0 / frequency;
            long newTs = (long) (oldTs * k);
            circularMessage.setTs(newTs);
        }
    }

    private CpuTickFreqMessage getCpuTickFreqMessage(final Blackbox blackbox) {
        Collection<TypedLinearMessage> typedLinearMessages = blackbox
                .getTypedLinearMessages();

        CpuTickFreqMessage cpuTickFreqMessage = null;

        for (TypedLinearMessage typedLinearMessage : typedLinearMessages) {
            if (typedLinearMessage.getMessage().getTypeId() == 1019) {
                cpuTickFreqMessage = new CpuTickFreqMessage(typedLinearMessage);
                break;
            }
        }
        return cpuTickFreqMessage;
    }

}
