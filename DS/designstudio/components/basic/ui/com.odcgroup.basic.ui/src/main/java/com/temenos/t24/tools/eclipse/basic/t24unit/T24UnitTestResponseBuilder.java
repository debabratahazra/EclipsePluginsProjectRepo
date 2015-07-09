package com.temenos.t24.tools.eclipse.basic.t24unit;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.actions.ActionCommon;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Class responsible for building the T24 Unit test response from the
 * {@link Response} object returned from T24 Server. This class understands the
 * standard response from T24 and builds the T24 Unit test response which is
 * used to build the T24 Unit Test view.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestResponseBuilder {

    public static T24UnitTestResponseBuilder responseBuilder = new T24UnitTestResponseBuilder();
    private static final String TEST_CASE = "TEST_CASE_";
    private static final String TAG_COMPILED = "//compiled";
    private static final String TAG_LOCKED = "//locked";
    private static final String TAG_SAVED = "//saved";
    private static final String TAG_RESULT = "//result";
    private static final String TAG_ERROR = "//error";
    private static final String TAG_USER_NAME = "//lusername";
    private static final String TAG_USER_PHONE = "//contactno";
    private static final String TAG_USER_EMAIL = "//email";
    private static final String seperator = " ";
    private static final String TRUE = "TRUE";
    private static final String ERR_MSG_FILE_COMPILE = "Unable to compile file.";
    private static final String ERR_MSG_FILE_SAVE = "Unable to save file.";
    private static final String ERR_GENERIC = "Unable to perform operation.";
    private static final String ERR_NULL_RESPONSE = "Execution failed. No result available.";
    private static final String ERR_NO_RESULTS = "No results found.";

    private T24UnitTestResponseBuilder() {
    }

    /**
     * Returns the instance of this class
     * 
     * @return
     */
    public static T24UnitTestResponseBuilder getInstance() {
        return responseBuilder;
    }

    /**
     * Builds and returns the T24 Unit test object from the {@link Response}
     * object
     * 
     * @param file
     * @param response
     * @return T24 Unit test object
     */
    @SuppressWarnings("unchecked")
    public T24UnitTest buildTestFromResponse(TestFile testFile, Response response) {
        T24UnitTest aTest = getBadTest(testFile, response);
        if (aTest != null) {
            return aTest;
        }
        Document doc = null;
        aTest = new T24UnitTest(testFile);
        String responseXml = (String) response.getObject();
        try {
            doc = DocumentHelper.parseText(responseXml);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<Element> resultList = doc.selectNodes(TAG_RESULT);
        int position = 0;
        T24UnitTestCase testCase = null;
        for (Element resultElement : resultList) {
            position++;
            testCase = getTestCase(resultElement, position);
            aTest.addTestCase(testCase);
        }
        return aTest;
    }

    /**
     * Analyses the response xml received from T24 and checks for any error in
     * the test
     * 
     * @param file
     * @param responseXml
     * @return T24 Unit test object if test is failed
     */
    private T24UnitTest getBadTest(TestFile testFile, Response response) {
        T24UnitTest aTest = new T24UnitTest(testFile);
        if (!response.getPassed()) {
            aTest.setErrorMessage(response.getRespMessage());
            return aTest;
        }
        String errorMessage = ERR_GENERIC;
        String responseXml = (String) response.getObject();
        if (!ActionCommon.isRawResponseOK(responseXml)) {
            aTest.setErrorMessage(ERR_NULL_RESPONSE);
            return aTest;
        }
        String savedText = XmlUtil.getSafeText(responseXml, TAG_SAVED);
        String errorText = getErrorText(responseXml);
        if (savedText != null && savedText.length() > 0 && !savedText.equalsIgnoreCase(TRUE)) {
            errorMessage = ERR_MSG_FILE_SAVE;
            String lockedText = getLockedText(responseXml);
            if (lockedText != null && lockedText.length() > 0) {
                errorMessage = errorMessage + seperator + lockedText;
            }
            if (errorText != null && errorText.length() > 0) {
                errorMessage = errorMessage + seperator + errorText;
            }
            aTest.setErrorMessage(errorMessage);
            return aTest;
        }
        String compiledText = XmlUtil.getSafeText(responseXml, TAG_COMPILED);
        errorMessage = ERR_MSG_FILE_COMPILE;
        if (compiledText != null && compiledText.length() > 0 && !compiledText.equalsIgnoreCase(TRUE)) {
            errorMessage = errorMessage + seperator + errorText;
            aTest.setErrorMessage(errorMessage);
            return aTest;
        }
        if (errorText != null) {
            aTest.setErrorMessage(ERR_GENERIC + seperator + errorText);
            return aTest;
        }
        return null;
    }

    /**
     * Returns the error message if the test file is locked by other users
     * 
     * @param responseXml
     * @return
     */
    private String getLockedText(String responseXml) {
        String lockedMsg = null;
        if (TRUE.equalsIgnoreCase(XmlUtil.getSafeText(responseXml, TAG_LOCKED))) {
            String userName = XmlUtil.getSafeText(responseXml, TAG_USER_NAME);
            String phone = XmlUtil.getSafeText(responseXml, TAG_USER_PHONE);
            String email = XmlUtil.getSafeText(responseXml, TAG_USER_EMAIL);
            lockedMsg = "File locked by " + userName + "." + "Email:" + email + " Phone:" + phone;
        }
        return lockedMsg;
    }

    /**
     * Returns the error message received from T24 Server
     * 
     * @param responseXml
     * @return
     */
    private String getErrorText(String responseXml) {
        String errorText = XmlUtil.getSafeText(responseXml, TAG_ERROR);
        if (errorText != null && errorText.length() > 0) {
            return errorText;
        }
        return null;
    }

    /**
     * Returns the test case from the result element of the response
     * 
     * @param resultElement
     * @param position
     * @return T24 Unit test case
     */
    private T24UnitTestCase getTestCase(Element resultElement, final int position) {
        T24UnitTestStatus status = null;
        T24UnitTestCase testCase = null;
        T24UnitTestResult result = null;
        String resultText = resultElement.getTextTrim();
        if (resultText == null || resultText.length() < 1) {
            status = new T24UnitTestStatus(ResultTypes.ERROR);
            result = new T24UnitTestResult(ERR_NO_RESULTS);
            testCase = new T24UnitTestCase(TEST_CASE + position, result, status);
            return testCase;
        }
        String resultCode = resultText.substring(0, 1);
        if (T24UnitTestConstants.RESPONSE_CODE_PASSED.equals(resultCode)) {
            status = new T24UnitTestStatus(ResultTypes.PASSED);
            result = new T24UnitTestResult(status.getResultText());
        } else {
            String resultMsg = null;
            status = new T24UnitTestStatus(ResultTypes.FAILED);
            if (resultText.length() > 2) {
                resultMsg = status.getResultText() + " " + resultText.substring(2);
            } else {
                resultMsg = status.getResultText();
            }
            result = new T24UnitTestResult(resultMsg);
        }
        testCase = new T24UnitTestCase(TEST_CASE + position, result, status);
        return testCase;
    }
}
