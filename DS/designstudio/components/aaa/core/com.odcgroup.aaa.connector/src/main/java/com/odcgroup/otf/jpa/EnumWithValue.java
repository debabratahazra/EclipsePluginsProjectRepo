package com.odcgroup.otf.jpa;

/**
 * Interface for Java5 enum which have an (arbitrary) numeric value (in addition to the Java system ordinal value).
 * 
 * In addition to the value() method of this interface, such an enum must also have a <tt>static valueOf(int value)</tt> method. Example:
 * 
 * <p><code>
 * public enum SomeEnum {
 *   YING(123), YANG(456), PEACE(789);
 *   
 *   private final int value;
 *   private SomeEnum(int value) {
 *     this.value = value;
 *   }
 *
 *   public int value() {
 *     return this.value;
 *   }
 *  
 *   public static SomeEnum valueOf(int value) {
 *     for (SomeEnum m : values()) { if(m.value == value) return m; }
 *     throw new IllegalArgumentException("No Enumeration for this constant: " + value);
 *   }
 * }
 * </code><p>
 * 
 * @author Michael Vorburger (MVO)
 * @since 17.09.2008
 */
public interface EnumWithValue {

    int value();
}
