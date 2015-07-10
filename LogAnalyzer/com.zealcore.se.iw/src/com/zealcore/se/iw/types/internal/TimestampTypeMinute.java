package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeMinute implements ITimestampType {

    private static final long TIMESTAMP_MULTIPLICATOR = TimestampManager.NANOS_PER_MINUTE;

    private static final String TIMESTAMP_LABEL = "Timestamp (m - Minute)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_MINUTE";

    public String getId() {
        return TimestampTypeMinute.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeMinute.TIMESTAMP_LABEL;
    }

    public Long valueOf(final String time) {
        return Long.parseLong(time)
                * TimestampTypeMinute.TIMESTAMP_MULTIPLICATOR;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String minute) {
        if (canMatch(minute)) {
            timestamp.setMinute(minute);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing minute: " + minute
                    + " on pattern " + "mm");
        }
    }

    public boolean canMatch(final String proposal) {
        int minute;
        try {
            minute = Integer.valueOf(proposal);
            if (minute < 0 || minute > 59) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
