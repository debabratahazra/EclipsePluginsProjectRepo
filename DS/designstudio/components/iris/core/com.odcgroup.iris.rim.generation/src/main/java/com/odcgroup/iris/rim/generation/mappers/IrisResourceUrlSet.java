package com.odcgroup.iris.rim.generation.mappers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.odcgroup.iris.generator.Resource;


/**
 * An <code>IrisResourceUrlSet</code> represents a {@link FileSystemPersitable persistable} ordered set of pro forma IRIS resource URLs.
 *
 * It is used by {@link LightweightPatternCOS2ResourceMapper} as the means to collect up (and persist) a list of resource URLs for multi-threaded
 * loading on opening of a given top-level (CompositeScreen ESON-defined) COS.
 *
 * The {@link #addEntryFor(String, String, String) addEntryFor} method "knows" how to translate a supplied 'target' resource name plus a RIM-style
 * "parameters [ ... ]" definition into to a corresponding pro forma resource URL (with markers of the form: {paramName} for dynamic values).
 *
 *
 * @author Simon Hayes
 */
public class IrisResourceUrlSet implements FileSystemPersistable {
    private static final String ENQUIRY_RESOURCE_NAME_PREFIX = Resource.RESOURCE_TYPE.enquiry.toString();

    private interface Patterns {
        Pattern
            IRIS_PARAMS_NVPAIR_LIST_EXTRACTOR = Pattern.compile("^parameters *\\[ *(.+[^ ]) *] *$", Pattern.CASE_INSENSITIVE),
            IRIS_PARAM_NVPAIR_FINDER_SPLITTER = Pattern.compile("([a-z]+[a-z0-9]*) ?= ?\\\"([^\\\"]+)\"", Pattern.CASE_INSENSITIVE);
    }

    private final List<String> m_uniqueIrisUrlList = new ArrayList<String>();
    private final String m_filename;

    public IrisResourceUrlSet(String p_fileName) {
        m_filename = p_fileName;
    }

    public void addEntryFor(String p_targetResourceName, String p_irisTransitionParams, String p_transitionTitle) {
        final String irisResourceUrl = buildIrisUrl(p_targetResourceName, p_irisTransitionParams, p_transitionTitle);

        if ((irisResourceUrl != null) && ! m_uniqueIrisUrlList.contains(irisResourceUrl))
            m_uniqueIrisUrlList.add(irisResourceUrl);
    }

    @Override
    public String getFilename() {
        return m_filename;
    }

    @Override
    public String getFileDescription() {
        return "Referenced IRIS Resource URL List";
    }

    @Override
    public CharSequence getFileContent() {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);

        final int numIrisUrls = m_uniqueIrisUrlList.size();

        for (int i = 0; i < numIrisUrls; ++i)
            pw.println(m_uniqueIrisUrlList.get(i));

        return sw.getBuffer();
    }

    private static String buildIrisUrl(String p_targetResourceName, String p_irisTransitionParams, String p_transitionTitle) {
        final String unqualifiedResourceName = toUnqualifiedResourceName(p_targetResourceName);

        String result = null;

        if (unqualifiedResourceName != null)
        {
            final StringBuilder sb = new StringBuilder();

            sb.append('/').append(unqualifiedResourceName);

            if (unqualifiedResourceName.startsWith(ENQUIRY_RESOURCE_NAME_PREFIX))
                sb.append("()");

            final String queryStringParams = genQueryStringFromTransitionParams(p_irisTransitionParams);

            if (queryStringParams != null)
                sb.append(queryStringParams);

            result = sb.toString();
        }

        return result;
    }

    private static String genQueryStringFromTransitionParams(String p_irisTransitionParams) {
        String irisTransitionParams = trimEmptyToNull(p_irisTransitionParams);

        if (irisTransitionParams == null)
            return null;

        final Matcher nvPairListExtractor = Patterns.IRIS_PARAMS_NVPAIR_LIST_EXTRACTOR.matcher(irisTransitionParams);

        if (! nvPairListExtractor.matches())
            return null;

        final String nvPairList = nvPairListExtractor.group(1);
        final Matcher nvPairFinderSplitter = Patterns.IRIS_PARAM_NVPAIR_FINDER_SPLITTER.matcher(nvPairList);

        final IrisParamsList irisParamsList = new IrisParamsList();

        while(nvPairFinderSplitter.find())
        {
            final String paramName = nvPairFinderSplitter.group(1);
            final String paramValue = nvPairFinderSplitter.group(2);

            irisParamsList.add(new NVPair(paramName, paramValue));
        }

        return irisParamsList.toUrlQueryString();
    }

    private static String toUnqualifiedResourceName(String p_targetResourceName) {
        String result = trimEmptyToNull(p_targetResourceName);

        if (result != null) {
            final int indexOfFinalDot = result.lastIndexOf('.');

            if (indexOfFinalDot >= 0)
                result = (indexOfFinalDot < (result.length() - 1)) ? result.substring(indexOfFinalDot + 1) : null;
        }

        return result;
    }

    private static String trimEmptyToNull(String s) {
        return ((s == null) || (s.length() == 0) || ((s = s.trim()).length() == 0)) ? null : s;
    }

    private static class IrisParamsList {
        private List<NVPair> m_nonFilterParams;
        private NVPair m_filterParam;

        void add(NVPair p_paramNVPair) {
            if ("filter".equals(p_paramNVPair.name))
                m_filterParam = p_paramNVPair;

            else {
                if (m_nonFilterParams == null)
                    m_nonFilterParams = new ArrayList<NVPair>();

                m_nonFilterParams.add(p_paramNVPair);
            }
        }

        String toUrlQueryString() {
            final int numNonFilterParams = (m_nonFilterParams == null) ? 0 : m_nonFilterParams.size();

            String result = null;

            if ((numNonFilterParams > 0) || (m_filterParam != null)) {
                final StringBuilder irisUrlBuffer = new StringBuilder();

                char delim = '?';

                for (int i = 0; i < numNonFilterParams; ++i) {
                    irisUrlBuffer.append(delim);
                    m_nonFilterParams.get(i).appendAsUrlQueryStringParam(irisUrlBuffer);
                    delim = '&';
                }

                if (m_filterParam != null) {
                    irisUrlBuffer.append(delim);
                    m_filterParam.appendAsUrlQueryStringParam(irisUrlBuffer);
                }

                result = irisUrlBuffer.toString();
            }

            return result;
        }
    } // class IrisParamsList

    private static class NVPair {
        final String name;
        final String value;

        NVPair(String p_name, String p_value) {
            name = p_name;
            value = p_value;
        }

        void appendAsUrlQueryStringParam(StringBuilder p_urlBuffer) {
            p_urlBuffer
                .append(name)
                .append('=')
                .append(value == null ? "" : value.replace(' ', '+'));
        }
    } // class NVPair
}
