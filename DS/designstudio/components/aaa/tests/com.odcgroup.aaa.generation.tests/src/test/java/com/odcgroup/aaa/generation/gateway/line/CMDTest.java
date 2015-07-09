package com.odcgroup.aaa.generation.gateway.line;

import junit.framework.Assert;

import org.junit.Test;

import com.odcgroup.aaa.generation.gateway.line.value.DATStringValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATValue;


public class CMDTest {
	
	@Test
	public void testCMD() {
		CMD cmd = new CMD(CMDType.INSERT, "someTable");
		Assert.assertEquals("Wrong type", CMDType.INSERT, cmd.getType());
		Assert.assertEquals("Wrong table name", "someTable", cmd.getEntityName());
	}
	
	@Test
	public void testCMDWithATT() {
		CMD cmd = new CMD(CMDType.INSERT, "someTable");
		
		ATT att1 = new ATT(new String[]{"colA", "colB"});
		DAT dat1 = new DAT(new DATValue[]{new DATStringValue("abc"), new DATStringValue("def")});
		DAT dat2 = new DAT(new DATValue[]{new DATStringValue("ghi"), new DATStringValue("jkl")});
		att1.addDAT(dat1);
		att1.addDAT(dat2);
		
		cmd.setATT(att1);
		
		Assert.assertEquals("Wrong ATT", att1, cmd.getATT());
	}
}
