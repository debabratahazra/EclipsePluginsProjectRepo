package com.temenos.t24.tools.eclipse.basic.t24unit;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * Class represents a T24 Unit test case. Implements {@link IT24TreeViewNode}
 * since it is a node in the T24 Unit test view.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestCase implements IT24TreeViewNode {

    /** Label of the node */
    private String label;
    /** Parent test, this test case belongs to */
    private T24UnitTest parent;
    /** child object of this node */
    private T24UnitTestResult result;
    /** status of this test case */
    private T24UnitTestStatus status;

    /**
     * Constructs a T24 Unit test case object.
     * 
     * @param label
     * @param result
     * @param status
     */
    public T24UnitTestCase(String label, T24UnitTestResult result, T24UnitTestStatus status) {
        this.label = label;
        this.result = result;
        this.status = status;
        result.setParent(T24UnitTestCase.this);
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the parent of this node
     * 
     * @param parent
     */
    public void setParent(T24UnitTest parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode getParent() {
        return parent;
    }

    /**
     * Returns true if this is a failed case. False otherwise.
     * 
     * @return true/false
     */
    public boolean isFailedCase() {
        if (ResultTypes.FAILED.equals(status.getResult())) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this is an error case. False otherwise.
     * 
     * @return true/false
     */
    public boolean isErrorCase() {
        if (ResultTypes.ERROR.equals(status.getResult())) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this is a passed case. False otherwise.
     * 
     * @return true/false
     */
    public boolean isPassedCase() {
        if (ResultTypes.PASSED.equals(status.getResult())) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getChildren() {
        if (!hasChildren()) {
            return null;
        }
        IT24TreeViewNode[] results = new IT24TreeViewNode[1];
        results[0] = result;
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage() {
        return status.getImage();
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasChildren() {
        if (result != null) {
            return true;
        }
        return false;
    }
}
