package com.odcgroup.aaa.connector.domainmodel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author can
 */
public class DictEntityTest {

	private static final long ONE = new Long(1).longValue();
	private static final long TWO = new Long(2).longValue();
	private static final long THREE = new Long(3).longValue();

	@Test
	public void testAttributesAreReturnedInASortedOrder() throws Exception {

		DictAttribute attribute1 = createMockAttribute(ONE);
		DictAttribute attribute2 = createMockAttribute(TWO);
		DictAttribute attribute3 = createMockAttribute(THREE);

		DictEntity dictEntity = new DictEntity();
		dictEntity.addAttribute(attribute3);
		dictEntity.addAttribute(attribute1);
		dictEntity.addAttribute(attribute2);

		List<DictAttribute> sortedAttributes = dictEntity.getSortedAttributes();

		Assert.assertNotNull(sortedAttributes);
		Assert.assertEquals(ONE, sortedAttributes.get(0).getDictID());
		Assert.assertEquals(TWO, sortedAttributes.get(1).getDictID());
		Assert.assertEquals(THREE, sortedAttributes.get(2).getDictID());
	}

	/**
	 * @param longValue
	 * @return
	 */
	private DictAttribute createMockAttribute(Long longValue) {
		DictAttribute attribute = mock(DictAttribute.class);
		when(attribute.getDictID()).thenReturn(longValue);
		return attribute;
	}
}
