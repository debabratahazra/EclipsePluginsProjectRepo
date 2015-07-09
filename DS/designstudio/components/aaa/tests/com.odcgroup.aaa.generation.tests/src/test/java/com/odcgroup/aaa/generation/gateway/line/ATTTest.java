package com.odcgroup.aaa.generation.gateway.line;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.aaa.generation.gateway.line.value.DATStringValue;


public class ATTTest {
	
	@Test
	public void testATT() {
		// Given
		List<String> columnsNames = new ArrayList<String>();
		columnsNames.add("Col1");
		columnsNames.add("Col2");
		columnsNames.add("Col3");
		ATT att1 = new ATT(columnsNames);
		ATT att2 = new ATT(new String[] {"Col1", "Col2", "Col3"});

		// Then
		verifyATT(att1);
		verifyATT(att2);
	}

	/**
	 * @param att
	 */
	private void verifyATT(ATT att) {
		Assert.assertEquals("Wrong number of columns", 3, att.getColumnNames().size());
		Assert.assertEquals("Wrong column found", "Col1", att.getColumnNames().get(0));
		Assert.assertEquals("Wrong column found", "Col2", att.getColumnNames().get(1));
		Assert.assertEquals("Wrong column found", "Col3", att.getColumnNames().get(2));
	}
	
	@Test
	public void testATT_withDAT() {
		ATT att = new ATT(new String[] {"col"});
		Assert.assertTrue("Should be empty", att.getDATs().isEmpty());
		
		DAT dat1 = new DAT(new DATStringValue[]{new DATStringValue("abc")});
		DAT dat2 = new DAT(new DATStringValue[]{new DATStringValue("abc")});
		att.addDAT(dat1);
		att.addDAT(dat2);
		
		Assert.assertEquals("Wrong number of DATs", 2, att.getDATs().size());
		Assert.assertEquals("Wrong DAT found", dat1, att.getDATs().get(0));
		Assert.assertEquals("Wrong DAT found", dat2, att.getDATs().get(1));
		
		List<DAT> newDATs = new ArrayList<DAT>();
		newDATs.add(dat2);
		newDATs.add(dat1);
		att.setDATs(newDATs);
		
		Assert.assertEquals("Wrong number of DATs", 2, att.getDATs().size());
		Assert.assertEquals("Wrong DAT found", dat2, att.getDATs().get(0));
		Assert.assertEquals("Wrong DAT found", dat1, att.getDATs().get(1));
		
		att.clearDAT();
		Assert.assertTrue("Should be empty", att.getDATs().isEmpty());
		
	}
}
