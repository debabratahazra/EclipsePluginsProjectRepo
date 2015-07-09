package com.odcgroup.workbench.dsl.naming;

import java.util.Arrays;

import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * An IQualifiedNameConverter which, contrary to the standard {@link IQualifiedNameConverter.DefaultImpl}), supports different delimiters.
 * 
 * This implementation currently assumes that the delimiters are all different,
 * and that the QualifiedName String contains each delimiter exactly once.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=382897
 * 
 * @author Michael Vorburger
 */
public abstract class MultiDelimiterQualifiedNameConverter implements IQualifiedNameConverter {

//    private String[] delimiterPatterns;
    
    /**
     * Delimiters for this QualifiedNameConverter.
     * This is expected to return a constant, not delimiters that change base on something. 
     * @return Array of QualifiedName Delimiters
     */
    public abstract String[] getDelimiters();

    /**
     * Converts the given qualified name to a string.
     * 
     * @exception IllegalArgumentException when the qualified name is null.
     */
    @Override
    public String toString(QualifiedName qualifiedName) {
        if (qualifiedName == null)
            throw new IllegalArgumentException("Qualified name cannot be null");
        if (qualifiedName.getSegmentCount() == 1)
            return qualifiedName.getFirstSegment();
        
        final StringBuilder builder = new StringBuilder();
        final String[] delims = getDelimiters();
        int i = 0;
        for (String segment : qualifiedName.getSegments()) {
            if (i > delims.length)
                throw new IllegalArgumentException("Qualified name '" + qualifiedName.toString()
                        + "' cannot be String-ified by this " + MultiDelimiterQualifiedNameConverter.class.getName()
                        + " because there are too few Delimiters: " + Arrays.asList(getDelimiters()));
            if (i > 0)
                builder.append(delims[i-1]);
            builder.append(segment);
            ++i;
        }
        return builder.toString();
    }

    /**
     * Splits the given string into segments and returns them as a {@link} QualifiedName.
     * 
     * @exception IllegalArgumentException if the input is empty or null.
     */
    @Override
    public QualifiedName toQualifiedName(String qualifiedNameAsString) {
        if (qualifiedNameAsString == null)
            throw new IllegalArgumentException("Qualified name cannot be null");
        if (qualifiedNameAsString.trim().isEmpty())
            throw new IllegalArgumentException("Qualified name cannot be empty");
        final String[] delims = getDelimiters(); // getDelimiterPatterns();
        final String[] segments = new String[delims.length + 1];
        
        int i = 0;
        int fromIndex = 0;
        int nextIndex;
        do {
            nextIndex = qualifiedNameAsString.indexOf(delims[i], fromIndex);
            if (nextIndex > -1) {
                segments[i++] = qualifiedNameAsString.substring(fromIndex, nextIndex);
                fromIndex = nextIndex + 1;
            } else {
                break;
            }
        } while (i < delims.length );
        segments[i] = qualifiedNameAsString.substring(fromIndex);
        
        if (i == delims.length) {
            return QualifiedName.create(segments);
        } else {
            final String[] lessSegments = new String[i + 1];
            System.arraycopy(segments, 0, lessSegments, 0, i + 1);
            return QualifiedName.create(lessSegments);
        }
    }

//    private String[] getDelimiterPatterns() {
//        final String[] delimiters = getDelimiters();
//        final List<String> delimiterPatternsList = new ArrayList<String>(delimiters.length);
//        if (delimiterPatterns == null) {
//            for (String delimiter : delimiters) {
//                delimiterPatternsList.add(Pattern.quote(delimiter));
//            }
//            delimiterPatterns = delimiterPatternsList.toArray(new String[0]);
//        }
//        return delimiterPatterns;
//    }
    
}
