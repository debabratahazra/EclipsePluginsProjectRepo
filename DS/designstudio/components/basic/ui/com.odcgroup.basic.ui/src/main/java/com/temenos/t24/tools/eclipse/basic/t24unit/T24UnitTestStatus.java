package com.temenos.t24.tools.eclipse.basic.t24unit;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * Class represents the status of any T24 Unit test case. Status object
 * encapsulates the image and type of the result.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestStatus {

    private String imageFile;
    private ResultTypes RESULT;

    /**
     * Constructs the status object.
     * 
     * @param RESULT {@link ResultTypes}
     */
    public T24UnitTestStatus(ResultTypes RESULT) {
        this.RESULT = RESULT;
        setImageFile();
    }

    /**
     * Sets the image which represents this status.
     */
    private void setImageFile() {
        if (RESULT.equals(ResultTypes.PASSED)) {
            imageFile = T24UnitTestConstants.IMAGE_FILE_PASS;
        } else if (RESULT.equals(ResultTypes.FAILED)) {
            imageFile = T24UnitTestConstants.IMAGE_FILE_FAIL;
        } else if (RESULT.equals(ResultTypes.ERROR)) {
            imageFile = T24UnitTestConstants.IMAGE_FILE_ERROR;
        }
    }

    /**
     * Returns the image of this status
     * 
     * @return Image
     */
    public Image getImage() {
        return EclipseUtil.getImage(imageFile);
    }

    /**
     * Returns the result type of this status
     * 
     * @return RESULT
     */
    public ResultTypes getResult() {
        return RESULT;
    }

    /**
     * Returns the String version of this status
     * 
     * @return String Result
     */
    public String getResultText() {
        if (RESULT.equals(ResultTypes.PASSED)) {
            return T24UnitTestConstants.TEXT_MSG_PASS;
        } else if (RESULT.equals(ResultTypes.FAILED)) {
            return T24UnitTestConstants.TEXT_MSG_FAIL;
        } else if (RESULT.equals(ResultTypes.ERROR)) {
            return T24UnitTestConstants.TEXT_MSG_ERROR;
        }
        return "";
    }
}
