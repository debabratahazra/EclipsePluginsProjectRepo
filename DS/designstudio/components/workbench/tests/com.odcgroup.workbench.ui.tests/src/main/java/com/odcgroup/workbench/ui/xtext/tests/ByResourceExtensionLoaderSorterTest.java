package com.odcgroup.workbench.ui.xtext.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.odcgroup.workbench.ui.xtext.ByResourceExtensionLoaderSorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.junit.Test;

/**
 * Unit test for ByResourceExtensionLoaderSorter.
 *
 * @author Michael Vorburger
 */
@SuppressWarnings("restriction")
public class ByResourceExtensionLoaderSorterTest {

	/**
	 * Test method for {@link com.odcgroup.workbench.core.xtext.ByResourceExtensionLoaderSorter#sort(java.util.Collection)}.
	 */
	@Test
	public void testSort() {
		IResourceLoader.Sorter sorter = new ByResourceExtensionLoaderSorter();
		Collection<URI> uris = new ArrayList<URI>();
		uris.add(URI.createURI("a.menu"));
		uris.add(URI.createURI("b.domain"));
		uris.add(URI.createURI("some.another"));  // DS-8328: It's important to have TWO different unknown extensions
		uris.add(URI.createURI("a.version"));
		uris.add(URI.createURI("a.rim"));
		uris.add(URI.createURI("some.other"));    // DS-8328: It's important to have TWO different unknown extensions
		uris.add(URI.createURI("an.eson"));
		uris.add(URI.createURI("a.domain"));
		uris.add(URI.createURI("an.enquiry"));

		Collection<URI> sortedURIs = sorter.sort(uris);
		Iterator<URI> iterator = sortedURIs.iterator();
		assertEquals(URI.createURI("a.domain"), iterator.next());
		assertEquals(URI.createURI("b.domain"), iterator.next());
		assertEquals(URI.createURI("a.version"), iterator.next());
		assertEquals(URI.createURI("an.enquiry"), iterator.next());
		assertEquals(URI.createURI("an.eson"), iterator.next());
		assertEquals(URI.createURI("a.menu"), iterator.next());
		assertEquals(URI.createURI("a.rim"), iterator.next());
		assertEquals(URI.createURI("some.another"), iterator.next());
		assertEquals(URI.createURI("some.other"), iterator.next());
		assertFalse(iterator.hasNext());
	}

	/**
	 * This test is important to catch programmnig errors when handling the case
	 * that a certain model type (file extension) may not be present.
	 */
	@Test
	public void testSortWithoutModels() {
		IResourceLoader.Sorter sorter = new ByResourceExtensionLoaderSorter();
		Collection<URI> uris = new ArrayList<URI>();
		sorter.sort(uris);
	}
}
