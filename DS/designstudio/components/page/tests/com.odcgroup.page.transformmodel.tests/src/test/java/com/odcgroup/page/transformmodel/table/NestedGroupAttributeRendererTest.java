package com.odcgroup.page.transformmodel.table;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;
import com.odcgroup.page.transformmodel.translation.WidgetAdapterTranslationKeyFactory;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * @author amc
 */
public class NestedGroupAttributeRendererTest {

	private static final String EXPECTED_I18_POSTFIX = ".text</i18n:text>";
	private static final String EXPECTED_I18_PREFIX = "<i18n:text>domain.myenum.";
	private static final String EXPECTED_UDP_ITEM = "<udp:item column=\"MYENUM\"/>";
	private NestedGroupAttributeRenderer renderer;
	private StringBuilder script;
	private ITableColumn column;
	private String groupName = "MYENUM";
	private WidgetAdapterTranslationKeyFactory keyFactory;
	
	@Before
	public void setUp() throws Exception {
		
		script = new StringBuilder();
		column = mock(ITableColumn.class);
		keyFactory = mock(WidgetAdapterTranslationKeyFactory.class);
		renderer = new NestedGroupAttributeRenderer(script, keyFactory);
	}
	
	@Test
	public void testWhenColumnHasEnumDatasetPropertyThatRenderAppendsUdpItemTagInsideI18Tag() throws Exception {
		// given 
		MdfEnumeration mockEntity = mock(MdfEnumeration.class);
		createMockDatasetPropertyWithTranslation(mockEntity);
		// when
		renderer.render(column, groupName);
		// then
		Assert.assertEquals(EXPECTED_I18_PREFIX+EXPECTED_UDP_ITEM+EXPECTED_I18_POSTFIX, script.toString());
	}
	
	@Test
	public void testWhenColumnHasNonEnumDatasetPropertyThatRenderAppendsOnlyUdpItemTag() throws Exception {
		// given 
		MdfEntity mockEntity = mock(MdfEntity.class);
		createMockDatasetPropertyWithTranslation(mockEntity);
		// when
		renderer.render(column, groupName);
		// then
		Assert.assertEquals(EXPECTED_UDP_ITEM, script.toString());
	}
	
	@Test
	public void testWhenColumnHasNullDatasetPropertyThatRenderAppendsOnlyUdpItemTag() throws Exception {
		// given 
		createMockDatasetProperty(null);
		// when
		renderer.render(column, groupName);
		// then
		verify(keyFactory, never()).getKey(any(IWidgetAdapter.class), any(Object.class));
		Assert.assertEquals(EXPECTED_UDP_ITEM, script.toString());
	}

	private void createMockDatasetPropertyWithTranslation(MdfEntity mockEntity) {
		createMockDatasetProperty(mockEntity);
		
		ITranslationKey mockKey = mock(ITranslationKey.class);
		when(mockKey.getKey(ITranslationKind.NAME)).thenReturn("domain.myenum.text");
		when(keyFactory.getKey(column, mockEntity)).thenReturn(mockKey);
	}

	private void createMockDatasetProperty(MdfEntity mockEntity) {
		MdfDatasetProperty mockDatasetProperty = mock(MdfDatasetProperty.class);
		when(mockDatasetProperty.getType()).thenReturn(mockEntity);
		when(column.getDatasetProperty(groupName)).thenReturn(mockDatasetProperty);
	}
	
}
