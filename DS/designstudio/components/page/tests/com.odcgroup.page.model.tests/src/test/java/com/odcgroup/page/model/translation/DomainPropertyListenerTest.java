package com.odcgroup.page.model.translation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.translation.InheritableTranslation;

public class DomainPropertyListenerTest {

	private Notifier mockNotifier;
	private EList<Adapter> mockAdapters;
	
	@Before
	public void setUp() throws Exception {
		mockAdapters = createMockAdaptersList();
		mockNotifier = mock(Notifier.class);
		when(mockNotifier.eAdapters()).thenReturn(mockAdapters);
	}

	@SuppressWarnings("unchecked")
	private EList<Adapter> createMockAdaptersList() {
		return (EList<Adapter>)mock(EList.class);
	}
	
	@Test
	public void testWhenWeakReferenceReturnsNullThatListenerIsRemoved() throws Exception {
		// given 
		InheritableTranslation translation = null;
		DomainPropertyListener listener = createListener(translation);
		// when
		listener.notifyChanged(null);
		// then
		verify(mockAdapters).remove(listener);
	}
	
	private DomainPropertyListener createListener(InheritableTranslation translation) {
		DomainPropertyListener listener = new DomainPropertyListener(translation);
		listener.setTarget(mockNotifier);
		return listener;
	}
}
