package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeHour implements ITimestampType {

    private static final String TIMESTAMP_LABEL = "Timestamp (h - Hour)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_HOUR";

    public String getId() {
        return TimestampTypeHour.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeHour.TIMESTAMP_LABEL;
    }

    public Long valueOf(final String time) {
        return Long.parseLong(time) * TimestampManager.NANOS_PER_HOUR;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String hour) {
        if (canMatch(hour)) {
            timestamp.setHour(hour);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing hour: " + hour
                    + " on pattern " + "hh");
        }
    }

    public boolean canMatch(final String proposal) {
        int hour;
        try {
            hour = Integer.valueOf(proposal);
            if (hour < 0 || hour > 23) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
