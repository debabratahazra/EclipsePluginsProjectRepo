package com.zealcore.se.ui.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.model.AbstractObject;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.SEProperty;

/**
 * Non extensive test of the NamedItemContentProvider
 * 
 * @author stch
 * 
 */
public class NamedItemContentProviderTest {

    private static final int TEN = 10;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Ignore
    @Test
    public void testGetChildrenArtifactAndCollection() {

        final NamedItemContentProvider subject = new NamedItemContentProvider();

        final IArtifact abstractArtifact = EasyMock.createMock(IArtifact.class);
        final SEProperty property = EasyMock.createMock(SEProperty.class);

        final ArrayList<SEProperty> properties = new ArrayList<SEProperty>();
        properties.add(property);

        org.easymock.EasyMock
                .expect(abstractArtifact.getZPropertyAnnotations()).andReturn(
                        properties);

        MockUtil.replay(abstractArtifact);

        final Object[] children = subject.getChildren(abstractArtifact);
        Assert.assertTrue(Arrays.asList(children).contains(property));

        final Object[] collectionChildren = subject.getChildren(properties);

        // Check that we got all the children frmo the collection
        Assert.assertEquals(properties.size(), collectionChildren.length);

        // Check that all the children returned are actually members of the
        // collection
        for (final Object object : collectionChildren) {
            Assert.assertTrue(properties.contains(object));
        }

        // An array from a content provider may never be null, only empty
        Assert.assertNotNull(children);

        MockUtil.verify(abstractArtifact);

    }

    @Test
    public void testGetChildrenSEPropertyPrimitive() {

        final NamedItemContentProvider subject = new NamedItemContentProvider();

        final SEProperty property = EasyMock.createMock(SEProperty.class);

        final Integer i = Integer.valueOf(NamedItemContentProviderTest.TEN);
        org.easymock.EasyMock.expect(property.getData()).andReturn(i);

        MockUtil.replay(property);

        // Gets the children of a property, the children of a property
        // is specified as the children of the property data
        final Object[] children = subject.getChildren(property);

        Assert.assertNotNull(children);
        // Integers does not have any children
        Assert.assertTrue(children.length == 0);

        Assert.assertNotNull(children);

        MockUtil.verify(property);

    }

    @Ignore
    @Test
    public void testGetChildrenSEPropertyArtifact() {

        final NamedItemContentProvider subject = new NamedItemContentProvider();

        final SEProperty parent = EasyMock.createMock(SEProperty.class);

        final IArtifact abstractArtifact = EasyMock.createMock(IArtifact.class);
        final SEProperty artifactProperty = EasyMock
                .createMock(SEProperty.class);
        final ArrayList<SEProperty> artifactProperties = new ArrayList<SEProperty>();
        artifactProperties.add(artifactProperty);

        org.easymock.EasyMock.expect(parent.getData()).andReturn(
                abstractArtifact);
        org.easymock.EasyMock
                .expect(abstractArtifact.getZPropertyAnnotations()).andReturn(
                        artifactProperties);

        MockUtil.replay(parent, abstractArtifact, artifactProperty);

        // Gets the children of a property, the children of a property
        // is specified as the children of the property data

        final Object[] children = subject.getChildren(parent);

        Assert.assertNotNull(children);

        // The children of a property is the children of property#getData
        // which in this case would be the children of an artifact, which is
        // SEProperties

        Assert.assertTrue(children.length == 1);
        for (final Object object : children) {
            Assert.assertTrue(artifactProperties.contains(object));
        }

        Assert.assertNotNull(children);

        MockUtil.verify(parent, abstractArtifact, artifactProperty);

    }

    @Test
    public void testArrayInput() {
        final NamedItemContentProvider subject = new NamedItemContentProvider();
        final String[] input = new String[] { "One", "two" };
        final Object[] elements = subject.getElements(input);
        Assert.assertNotNull(elements);
        Assert.assertTrue(elements.length == input.length);
    }

    @Test
    public void testSearchResultInput() {
        final NamedItemContentProvider subject = new NamedItemContentProvider();

        final SearchResult result = EasyMock.createMock(SearchResult.class);

        final List<Object> empty = new ArrayList<Object>();
        final List<Object> stuff = new ArrayList<Object>();
        stuff.add(new AbstractObject() {});

        org.easymock.EasyMock.expect(result.getItems()).andReturn(empty);

        org.easymock.EasyMock.expect(result.getItems()).andReturn(stuff);
        MockUtil.replay(result);

        Object[] children = subject.getChildren(result);
        Assert.assertTrue(children.length == 0);

        children = subject.getChildren(result);
        Assert.assertTrue(children.length == 1);
        Assert.assertSame(children[0], stuff.get(0));

        MockUtil.verify(result);

    }

}
