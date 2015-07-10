package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * @author cafa
 * 
 */
public final class TimestampTypeDay implements ITimestampType {

    private static final String TIMESTAMP_LABEL = "Timestamp (D - Day)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_DAY";

    public String getId() {
        return TimestampTypeDay.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeDay.TIMESTAMP_LABEL;
    }

    public void modify(final GenericLogEvent event, final TimestampUtil timestamp,
            final String day) /* throws ImportException */{
        if (canMatch(day)) {
            timestamp.setDay(day);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing day: " + day
                    + " on pattern " + "dd");
        }
    }

    public String valueOf(final String text) {
        return text;
    }

    public boolean canMatch(final String proposal) {
        int day;
        try {
            day = Integer.valueOf(proposal);
            if (day < 1 || day > 31) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
