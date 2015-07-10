package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampManager;
import com.zealcore.se.core.util.TimestampUtil;

public class TimestampTypeMicrosecond implements ITimestampType {

    private static final long TIMESTAMP_MULTIPLICATOR = TimestampManager.NANOS_PER_MICRO;

    private static final String TIMESTAMP_LABEL = "Timestamp (us - Microsecond)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_MICROSECOND";

    public String getId() {
        return TimestampTypeMicrosecond.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeMicrosecond.TIMESTAMP_LABEL;
    }

    public Long valueOf(final String time) {
        return Long.parseLong(time)
                * TimestampTypeMicrosecond.TIMESTAMP_MULTIPLICATOR;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String us) {
        if (canMatch(us)) {
            timestamp.setNs(us + "000");
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing microsecond: " + us
                    + " on pattern " + "uuuuuu");
        }
    }

    public boolean canMatch(final String proposal) {
        int microsecond;
        try {
            microsecond = Integer.valueOf(proposal);
            if (microsecond < 0 || microsecond > 999999) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
