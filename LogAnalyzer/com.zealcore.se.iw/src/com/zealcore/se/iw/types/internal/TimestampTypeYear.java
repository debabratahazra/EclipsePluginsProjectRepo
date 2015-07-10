package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * @author cafa
 * 
 */
public final class TimestampTypeYear implements ITimestampType {
    private static final String TIMESTAMP_LABEL = "Timestamp (Y - Year)";

    private static final String TIMESTAMP_ID = "TIMESTAMP_YEAR";

    public String getId() {
        return TimestampTypeYear.TIMESTAMP_ID;
    }

    public String getLabel() {
        return TimestampTypeYear.TIMESTAMP_LABEL;
    }

    public void modify(final GenericLogEvent event,
            final TimestampUtil timestamp, final String year) {
        if (canMatch(year)) {
            timestamp.setYear(year);
            event.setTs(timestamp.getTs());
        } else {
            throw new RuntimeException("Error parsing year: " + year
                    + " on pattern " + "CCYY");
        }
    }

    public String valueOf(final String year) {
        return year;
    }

    public boolean canMatch(final String proposal) {
        int year;
        try {
            year = Integer.valueOf(proposal);
            if (year < 0 || year > 9999) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
