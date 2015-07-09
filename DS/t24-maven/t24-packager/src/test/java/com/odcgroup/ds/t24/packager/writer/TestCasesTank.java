package com.odcgroup.ds.t24.packager.writer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Ignore;

import com.odcgroup.ds.t24.packager.data.Cd;
import com.odcgroup.ds.t24.packager.data.DataHeader;
import com.odcgroup.ds.t24.packager.data.Record;

@Ignore
public class TestCasesTank {

	public DataHeader createTestCase1() {
		DataHeader header = new DataHeader("5965_TRAINEE23", "5965_TRAINEE23", "US0010001");
		header.setGbDescription("T24 Update for the Component FT_ModelBank");
		
		Cd cd1 = new Cd();
		cd1.setType("BG");
		cd1.setNumber(207840);
		
		Record record1 = new Record();
		record1.setFilename("F.VERSION");
		record1.setName("FUNDS.TRANSFER,SCV.OC.LCY");
		
		List<Record> records = new ArrayList<Record>();
		records.add(record1);
		
		cd1.setRecords(records);
		
		cd1.setReference("207078");
		cd1.setProblem("USER.SMS.GROUP>AI.PERSONAL Version FUNDS.TRANSFER,SCV.OC.LCY is not correct in RTC");
		cd1.setSymptom("USER.SMS.GROUP>AI.PERSONAL version is not correct in RTC");
		cd1.setFixDescription("Correct record was loaded into RTC");
		cd1.setPriority("Normal");

		List<Cd> cds = new ArrayList<Cd>();
		cds.add(cd1);
		header.setCds(cds);
		
		header.setSavedVersion("1");
		header.setSavedFrom("/resdev/devbuild/TestBase/TestBase.run");
		header.setSavedRelease("R11.000");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6-1, 1, 18, 20, 3);
		header.setSavedDate(cal.getTime());
		header.setWorkset("R11");
		header.setProduct("FT");
		header.setUseDimensions("Y");
		header.setCurrNo("1");

		cal.set(11, 6-1, 1, 18, 19);
		header.setCreationModificationDate(cal.getTime());
		header.setUserDepartmentCode("1");

		return header;
	}

}
