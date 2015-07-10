package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeNanosecond implements ITimestampType {

    private static final long TIMESTAMP_MULTIPLICATOR = TimestampManager.NANOS_PER_NANO;

    private static final String TIMESTAMP_LABEL = "Timestamp (ns - Nanosecond)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_NANOSECOND";

    public String getId() {
        return TimestampTypeNanosecond.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeNanosecond.TIMESTAMP_LABEL;
    }

    public Long valueOf(final String time) {
        return Long.parseLong(time)
                * TimestampTypeNanosecond.TIMESTAMP_MULTIPLICATOR;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String ns) {
        if (canMatch(ns)) {
            timestamp.setNs(ns);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing nanosecond: " + ns
                    + " on pattern " + "nnnnnnnnn");
        }
    }

    public boolean canMatch(final String proposal) {
        long nanosecond;
        try {
            nanosecond = Long.valueOf(proposal);
            if (nanosecond < 0 || nanosecond > 999999999) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
