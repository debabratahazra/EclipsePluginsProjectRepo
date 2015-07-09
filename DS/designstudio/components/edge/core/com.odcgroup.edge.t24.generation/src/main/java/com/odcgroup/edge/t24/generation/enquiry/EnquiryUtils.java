package com.odcgroup.edge.t24.generation.enquiry;


/**
 * <code>EnquiryUtils</code> is a collection of static utility methods used in Enquiry processing
 *
 * @author Simon Hayes
 */
public class EnquiryUtils {
    
    public static String stripEnclosingQuotes(String s)
    {
        String result = s;

        if ( s != null )
        {
            final int len = s.length();

            if ( ( len >= 2 ) && ( s.charAt( 0 ) == '"' ) && ( s.charAt( len - 1 ) == '"' ) )
                result = s.substring( 1, len - 1 );
        }

        return result;
    }
}
