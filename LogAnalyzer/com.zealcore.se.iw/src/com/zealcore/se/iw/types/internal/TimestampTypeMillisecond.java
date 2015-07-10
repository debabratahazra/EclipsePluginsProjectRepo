package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeMillisecond implements ITimestampType {

    private static final long TIMESTAMP_MULTIPLICATOR = TimestampManager.NANOS_PER_MILLI;

    private static final String TIMESTAMP_LABEL = "Timestamp (ms - Millisecond)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_MILLISECOND";

    public String getId() {
        return TimestampTypeMillisecond.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeMillisecond.TIMESTAMP_LABEL;
    }

    public Long valueOf(final String time) {
        return Long.parseLong(time)
                * TimestampTypeMillisecond.TIMESTAMP_MULTIPLICATOR;
    }

    public void modify(final GenericLogEvent event, final TimestampUtil timestamp,
            final String ms) /* throws ImportException */{
        if (canMatch(ms)) {
            timestamp.setNs(ms + "000000");
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing millisecond: " + ms
                    + " on pattern " + "mmm");
        }
    }

    public boolean canMatch(final String proposal) {
        int millisecond;
        try {
            millisecond = Integer.valueOf(proposal);
            if (millisecond < 0 || millisecond > 999) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
