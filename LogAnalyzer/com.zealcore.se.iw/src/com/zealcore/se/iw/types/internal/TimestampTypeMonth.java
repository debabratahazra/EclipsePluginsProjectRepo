package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * @author cafa
 * 
 */
public final class TimestampTypeMonth implements ITimestampType {

    private static final String TIMESTAMP_LABEL = "Timestamp (M - Month)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_MONTH";

    public String getId() {
        return TimestampTypeMonth.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeMonth.TIMESTAMP_LABEL;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String month) {
        if (canMatch(month)) {
            timestamp.setMonth(month);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing month: " + month
                    + " on pattern " + "mm");
        }
    }

    public String valueOf(final String text) {
        return text;
    }

    public boolean canMatch(final String proposal) {
        int month;
        try {
            month = Integer.valueOf(proposal);
            if (month < 1 || month > 12) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
