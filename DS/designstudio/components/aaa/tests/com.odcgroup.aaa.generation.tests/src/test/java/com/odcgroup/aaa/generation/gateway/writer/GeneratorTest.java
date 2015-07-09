package com.odcgroup.aaa.generation.gateway.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.aaa.generation.gateway.line.ATT;
import com.odcgroup.aaa.generation.gateway.line.CMD;
import com.odcgroup.aaa.generation.gateway.line.CMDType;
import com.odcgroup.aaa.generation.gateway.line.DAT;
import com.odcgroup.aaa.generation.gateway.line.value.DATBooleanValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATDateValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATNumberValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATStringValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATValue;
import com.odcgroup.aaa.generation.gateway.validator.GatewayValidationException;


public class GeneratorTest {

	@Test
	public void testCMDWithATT() throws GatewayValidationException, IOException {
		// Given
		Calendar cal = Calendar.getInstance();
		cal.set(2011, java.util.Calendar.NOVEMBER, 12);
		
		CMD cmd = new CMD(CMDType.INSERT, "someTable");
		
		ATT att1 = new ATT(new String[]{"String", "Number", "Date", "Boolean"});
		DAT dat1 = new DAT(new DATValue[]{new DATStringValue("abc"), new DATNumberValue(3.1415d), new DATDateValue(cal.getTime()), new DATBooleanValue(true)});
		DAT dat2 = new DAT(new DATValue[]{new DATStringValue("def"), new DATNumberValue(100000), new DATDateValue(cal.getTime()), new DATBooleanValue(true)});
		att1.addDAT(dat1);
		att1.addDAT(dat2);
		
		cmd.setATT(att1);
		
		CMD cmd2 = new CMD(CMDType.INSERT, "someOtherTable", "someObjectEntityName");
		
		ATT att2 = new ATT(new String[]{"col_A", "col_B"});
		DAT dat4 = new DAT(new DATValue[]{new DATStringValue("abc"), new DATStringValue("def")});
		DAT dat5 = new DAT(new DATValue[]{new DATStringValue("ghi"), new DATStringValue("jkl")});
		att2.addDAT(dat4);
		att2.addDAT(dat5);
		
		cmd2.setATT(att2);

		List<CMD> cmds = new ArrayList<CMD>();
		cmds.add(cmd);
		cmds.add(cmd2);
		
		// When
		Settings settings = new Settings();
		String generation = new Generator(settings).generateCommands(cmds, "someProject", "some DS Version", cal.getTime());
		
		// Then
		StringWriter sw = new StringWriter();
		IOUtils.copy(this.getClass().getResourceAsStream("expectedGeneratedFile.txt"), sw);
		
		StringTokenizer stGenerated = new StringTokenizer(generation);
		StringTokenizer stExpected = new StringTokenizer(sw.toString());
		
		Assert.assertEquals("Nb lines generated dosen't match the nb lines expected", stExpected.countTokens(), stGenerated.countTokens());
		while (stGenerated.hasMoreTokens()) {
			String expected = stExpected.nextToken();
			String generated = stGenerated.nextToken();
			Assert.assertEquals("Contents mismatch", expected, generated);
		}
	}
	
	@Test
	public void testGenerator() {
		Settings settings = new Settings();
		settings.setQuote('\'');
		settings.setPrefixDataNone(true);
		settings.setDATSeparator(' ');
		StringBuilder result = new StringBuilder();
		DAT dat = new DAT(new DATValue[]{new DATStringValue("abc"), new DATStringValue("def")});
		new Generator(settings).generateDAT(dat, result);
		Assert.assertEquals("Quote setting not taken in account properly", "DAT 'abc' 'def'\n", result.toString());
	}
	

}
