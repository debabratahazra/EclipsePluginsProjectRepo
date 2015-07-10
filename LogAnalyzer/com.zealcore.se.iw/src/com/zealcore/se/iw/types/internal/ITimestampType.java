/**
 * 
 */
package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * @author cafa
 * 
 */
public interface ITimestampType extends IFieldType {
    void modify(GenericLogEvent event, TimestampUtil timestamp, String value);
}
