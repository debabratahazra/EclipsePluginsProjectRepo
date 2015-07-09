package com.odcgroup.aaa.generation.gateway.line;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.aaa.generation.gateway.line.value.DATStringValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATValue;


public class DATTest {
	
	private DAT dat;
	
	@Before
	public void setUp() {
		List<DATValue> values = new ArrayList<DATValue>();
		values.add(new DATStringValue("abc1"));
		values.add(new DATStringValue("abc2"));
		values.add(new DATStringValue("abc3"));
		dat = new DAT(values);
	}

	@Test
	public void testDAT() {
		Assert.assertEquals("Wrong number of DAT", 3, dat.getData().size());
		Assert.assertEquals("Wrong data found", "abc1", dat.getData().get(0).toString());
		Assert.assertEquals("Wrong data found", "abc2", dat.getData().get(1).toString());
		Assert.assertEquals("Wrong data found", "abc3", dat.getData().get(2).toString());
	}
	
	@Test
	public void testDAT_protection() {
		try {
			dat.getData().add(new DATStringValue("test"));
			Assert.fail();
		} catch (RuntimeException e) {
			// Correct behavior
		}
	}
	
	@Test
	public void testDATModification() {
		List<DATValue> newData = new ArrayList<DATValue>();
		newData.add(new DATStringValue("def1"));
		newData.add(new DATStringValue("def2"));
		dat.setData(newData);
		Assert.assertEquals("Wrong number of DAT", 2, dat.getData().size());
		Assert.assertEquals("Wrong data found", "def1", dat.getData().get(0).toString());
		Assert.assertEquals("Wrong data found", "def2", dat.getData().get(1).toString());
	}
	
}
