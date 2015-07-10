package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeSecond implements ITimestampType {

    private static final long TIMESTAMP_MULTIPLICATOR = TimestampManager.NANOS_PER_SECOND;

    private static final String TIMESTAMP_LABEL = "Timestamp (s - Second)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_SECOND";

    public String getId() {
        return TimestampTypeSecond.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeSecond.TIMESTAMP_LABEL;
    }

    public Long valueOf(final String time) {
        return Long.parseLong(time)
                * TimestampTypeSecond.TIMESTAMP_MULTIPLICATOR;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String second) {
        if (canMatch(second)) {
            timestamp.setSecond(second);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing second: " + second
                    + " on pattern " + "ss");
        }
    }

    public boolean canMatch(final String proposal) {
        int second;
        try {
            second = Integer.valueOf(proposal);
            if (second < 0 || second > 59) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
