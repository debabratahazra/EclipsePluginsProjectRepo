package com.odcgroup.page.transformmodel.tests.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.util.TransformModelRegistry;

/**
 * Tests the TransformModelRegistry.
 * 
 * @author Gary Hayes
 */
public class TransformModelRegistryTest {

    /**
     * Makes sure that there are no errors in the UIModel.
     */
    @Test
	public void testTransformModel() {
        TransformModel tm = TransformModelRegistry.getTransformModel();
        Assert.assertTrue(tm.eResource().getErrors().size() == 0);
    }

    /**
     * Validates that the namespace prefixes and url's are unique.
     */
    @Test
	public void testUniqueUrisAndPrefixes() {
        TransformModel tm = TransformModelRegistry.getTransformModel();
        Set<String> pSet = new HashSet<String>();
        Set<String> uSet = new HashSet<String>();
        for (Namespace n : tm.getNamespaces()) {
            String p = n.getPrefix();
            Assert.assertFalse("Non unique prefix found: " + p, pSet.contains(p));
            pSet.add(p);

            String u = n.getUri();
            Assert.assertFalse("Non unique uri found: " + u, uSet.contains(u));
            uSet.add(u);
        }
    }
}
