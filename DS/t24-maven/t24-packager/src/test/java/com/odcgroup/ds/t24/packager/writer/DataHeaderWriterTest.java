package com.odcgroup.ds.t24.packager.writer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.ds.t24.packager.data.Cd;
import com.odcgroup.ds.t24.packager.data.DataHeader;
import com.odcgroup.ds.t24.packager.data.Record;
import com.odcgroup.ds.t24.packager.generator.PackageTypeEnum;

public class DataHeaderWriterTest {

	@Test
	public void testR11_FT_ModelBank_1() throws IOException {
		// Given
		DataHeader header = new TestCasesTank().createTestCase1();
		
		// When
		DataHeaderWriter writer = new DataHeaderWriter(PackageTypeEnum.TAFC);
		byte[] generated = writer.write(header);
		byte[] expected = readExpectedResult("R11_FT_ModelBank_1");

		// Then
		Assert.assertArrayEquals("The generated header doesn't match the expected result", 
				expected, generated);
	}
	
	@Test
	public void testR11_FT_ModelBank_2() throws IOException {
		// Given
		DataHeader header = new DataHeader("5126_TRAINEE23", "5126_TRAINEE23", "US0010001");
		header.setGbDescription("T24 Update for the Component FT_ModelBank");
		
		Cd cd1 = new Cd();
		cd1.setType("BG");
		cd1.setNumber(226350);
		
		Record record1 = new Record();
		record1.setFilename("F.VERSION");
		record1.setName("FUNDS.TRANSFER,SCV.OC.LCY");
		
		List<Record> records = new ArrayList<Record>();
		records.add(record1);
		
		cd1.setRecords(records);
		
		cd1.setReference("PACS00063199:224095");
		cd1.setProblem("Upgrade from 201014 to R11 for UK");
		cd1.setSymptom("Not able to authroize the versions 1AC.ACCOUNT.OPENNIG,INPUT.PW 2AC.ACCOUNT.OPENING,INPUT.PW.JOINT 3FUNDS.TRANSFER,SCV.OC.LCY");
		cd1.setFixDescription("Need to remove the enquiry ELIGIBILE.PRODUCTS to authorize the versions AC.ACCOUNT.OPENNIG,INPUT.PW & AC.ACCOUNT.OPENING,INPUT.PW.JOINT and for the version FUNDS.TRANSFER,SCV.OC.LCY latest record is provided to upgrade the F.RELEASE.DATA with field markers");
		cd1.setPriority("Normal");

		Cd cd2 = new Cd();
		cd2.setType("BG");
		cd2.setNumber(207840);
		
		Record record2_1 = new Record();
		record2_1.setFilename("F.VERSION");
		record2_1.setName("FUNDS.TRANSFER,SCV.OC.LCY");
		
		List<Record> records2 = new ArrayList<Record>();
		records2.add(record2_1);
		
		cd2.setRecords(records2);
		
		cd2.setReference("207078");
		cd2.setProblem("USER.SMS.GROUP>AI.PERSONAL Version FUNDS.TRANSFER,SCV.OC.LCY is not correct in RTC");
		cd2.setSymptom("USER.SMS.GROUP>AI.PERSONAL version is not correct in RTC");
		cd2.setFixDescription("Correct record was loaded into RTC");
		cd2.setPriority("Normal");
		cd2.setRestoredFrom("BACKUP");

		List<Cd> cds = new ArrayList<Cd>();
		cds.add(cd1);
		cds.add(cd2);
		header.setCds(cds);
		
		header.setSavedVersion("1");
		header.setSavedFrom("/resdev/devbuild/TestBase/TestBase.run");
		header.setSavedRelease("R11.000");

		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6-1, 15, 15, 44, 17);
		header.setSavedDate(cal.getTime());
		header.setWorkset("R11");
		header.setProduct("FT");
		header.setUseDimensions("Y");
		header.setCurrNo("1");
		
		cal.set(2011, 6-1, 15, 15, 44);
		header.setCreationModificationDate(cal.getTime());
		header.setUserDepartmentCode("1");
		
		// When
		DataHeaderWriter writer = new DataHeaderWriter(PackageTypeEnum.TAFC);
		byte[] generated = writer.write(header);
		byte[] expected = readExpectedResult("R11_FT_ModelBank_2");

		// Then
		Assert.assertArrayEquals("The generated header doesn't match the expected result", 
				expected, generated);
	}
	
	@Test
	public void testR11_FT_ModelBank_3() throws IOException {
		// Given
		DataHeader header = new DataHeader("2471_TRAINEE23", "2471_TRAINEE23", "US0010001");
		header.setGbDescription("T24 Update for the Component FT_ModelBank");
		
		Cd cd1 = new Cd();
		cd1.setType("BG");
		cd1.setNumber(235315);
		
		Record record1_1 = new Record();
		record1_1.setFilename("F.VERSION");
		record1_1.setName("FUNDS.TRANSFER,FCY.CHEQUE");
		
		Record record1_2 = new Record();
		record1_2.setFilename("F.VERSION");
		record1_2.setName("FUNDS.TRANSFER,INDIRECT.CLG");
		
		Record record1_3 = new Record();
		record1_3.setFilename("F.VERSION");
		record1_3.setName("FUNDS.TRANSFER,LOCAL.CLG");
		
		Record record1_4 = new Record();
		record1_4.setFilename("F.VERSION");
		record1_4.setName("FUNDS.TRANSFER,OUTSTATION.CLG");
		
		List<Record> records1 = new ArrayList<Record>();
		records1.add(record1_1);
		records1.add(record1_2);
		records1.add(record1_3);
		records1.add(record1_4);
		
		cd1.setRecords(records1);
		
		cd1.setReference("PACS00075363:227643");
		cd1.setProblem("R11 Upgrade Exception AuthorisationIssues.");
		cd1.setSymptom("Not able to authorize the exception records");
		cd1.setFixDescription("The versions are amended by removing the defaulted FT.TXN.TYPE.CONDITION");
		cd1.setPriority("Normal");
		cd1.setRestoredFrom("");

		
		Cd cd2 = new Cd();
		cd2.setType("BG");
		cd2.setNumber(226350);
		
		Record record2 = new Record();
		record2.setFilename("F.VERSION");
		record2.setName("FUNDS.TRANSFER,SCV.OC.LCY");
		
		List<Record> records = new ArrayList<Record>();
		records.add(record2);
		
		cd2.setRecords(records);
		
		cd2.setReference("PACS00063199:224095");
		cd2.setProblem("Upgrade from 201014 to R11 for UK");
		cd2.setSymptom("Not able to authroize the versions 1AC.ACCOUNT.OPENNIG,INPUT.PW 2AC.ACCOUNT.OPENING,INPUT.PW.JOINT 3FUNDS.TRANSFER,SCV.OC.LCY");
		cd2.setFixDescription("Need to remove the enquiry ELIGIBILE.PRODUCTS to authorize the versions AC.ACCOUNT.OPENNIG,INPUT.PW & AC.ACCOUNT.OPENING,INPUT.PW.JOINT and for the version FUNDS.TRANSFER,SCV.OC.LCY latest record is provided to upgrade the F.RELEASE.DATA with field markers");
		cd2.setPriority("Normal");
		cd2.setRestoredFrom("BACKUP");

		
		Cd cd3 = new Cd();
		cd3.setType("BG");
		cd3.setNumber(207840);
		
		Record record3 = new Record();
		record3.setFilename("F.VERSION");
		record3.setName("FUNDS.TRANSFER,SCV.OC.LCY");
		
		List<Record> records3 = new ArrayList<Record>();
		records3.add(record3);
		
		cd3.setRecords(records3);
		
		cd3.setReference("207078");
		cd3.setProblem("USER.SMS.GROUP>AI.PERSONAL Version FUNDS.TRANSFER,SCV.OC.LCY is not correct in RTC");
		cd3.setSymptom("USER.SMS.GROUP>AI.PERSONAL version is not correct in RTC");
		cd3.setFixDescription("Correct record was loaded into RTC");
		cd3.setPriority("Normal");
		cd3.setRestoredFrom("BACKUP");

		List<Cd> cds = new ArrayList<Cd>();
		cds.add(cd1);
		cds.add(cd2);
		cds.add(cd3);
		header.setCds(cds);
		
		header.setSavedVersion("1");
		header.setSavedFrom("/resdev/devbuild/TestBase/TestBase.run");
		header.setSavedRelease("R11.000");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6-1, 29, 20, 3, 52);
		header.setSavedDate(cal.getTime());
		header.setWorkset("R11");
		header.setProduct("FT");
		header.setUseDimensions("Y");
		header.setCurrNo("1");
		cal.set(2011, 6-1, 29, 20, 3);
		header.setCreationModificationDate(cal.getTime());
		header.setUserDepartmentCode("1");
		
		// When
		DataHeaderWriter writer = new DataHeaderWriter(PackageTypeEnum.TAFC);
		byte[] generated = writer.write(header);
		byte[] expected = readExpectedResult("R11_FT_ModelBank_3");
		
		// Then
		Assert.assertArrayEquals("The generated header doesn't match the expected result", 
				expected, generated);
	}
	
	private byte[] readExpectedResult(String resourceName) throws IOException {
		InputStream is = getClass().getResourceAsStream("/" + resourceName);
		if (is == null)
			throw new IllegalArgumentException("no resource named like this found on classpath: " + resourceName);
		try {
			return IOUtils.toByteArray(is);
		} finally {
			is.close();
		}
	}
	
}
