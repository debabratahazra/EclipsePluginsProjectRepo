package com.temenos.t24.tools.eclipse.basic.t24unit;

import java.util.ArrayList;

import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * Root object of the T24 Unit test viewer tree. Implements the T24 Tree viewer
 * interface {@link IT24TreeViewRoot}.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestSuite implements IT24TreeViewRoot {

    /** List of parent nodes or immediate children of this root */
    private ArrayList<T24UnitTest> unitTests = new ArrayList<T24UnitTest>();

    /**
     * {@inheritDoc}
     */
    public void addParentNode(IT24TreeViewNode node) {
        if (node instanceof T24UnitTest) {
            unitTests.add((T24UnitTest) node);
        }
    }

    /**
     * {@inheritDoc}
     */
    public IT24TreeViewNode[] getParentNodes() {
        int arraySize = unitTests.size();
        IT24TreeViewNode[] parentArray = new IT24TreeViewNode[arraySize];
        unitTests.toArray(parentArray);
        return parentArray;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNodes() {
        if (unitTests.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns all the tests which are added as immediate children of this root.
     * 
     * @return tests
     */
    public ArrayList<T24UnitTest> getAllTests() {
        return (ArrayList<T24UnitTest>) unitTests;
    }
}
