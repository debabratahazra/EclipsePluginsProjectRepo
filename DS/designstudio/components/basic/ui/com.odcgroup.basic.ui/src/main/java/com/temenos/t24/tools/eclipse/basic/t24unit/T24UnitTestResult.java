package com.temenos.t24.tools.eclipse.basic.t24unit;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * Class represents the result of an individual T24 Unit test case.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestResult implements IT24TreeViewNode {

    private T24UnitTestCase parent;
    private String label;
    private Image image;

    /**
     * Constructs the test result object
     * 
     * @param label
     */
    public T24UnitTestResult(String label) {
        this.label = label;
        this.image = EclipseUtil.getImage(T24UnitTestConstants.IMAGE_FILE_TEST);
    }

    /**
     * Sets the parent test case of this result
     * 
     * @param parent
     */
    public void setParent(T24UnitTestCase parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage() {
        return image;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel() {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasChildren() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public T24UnitTestCase getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getChildren() {
        return null;
    }
}
