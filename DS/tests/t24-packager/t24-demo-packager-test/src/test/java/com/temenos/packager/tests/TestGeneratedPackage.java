package com.temenos.packager.tests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.junit.Assert;
import org.junit.Test;
import com.odcgroup.ds.t24.packager.helper.TarHelper;

public class TestGeneratedPackage {
	
	private static final String[] EXPECTED_ENTRIES = new String[] {
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/BP/E.MB.ACCT.NAME.b",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/BP/E.MB.BUILD.CREDIT.INT.CONDS.b",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/R\\d\\d_FT_ModelBank_1",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC001",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC002",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC003",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC004",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC005",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC006",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC007",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/REC008",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/t24lib/ft_modelbank/jLibDefinition",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/t24lib/ft_modelbank/lib.el",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/t24lib/ft_modelbank/lib0.so.2",
			"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/ds-generated/FUNDS_TRANSFER,AI_BENEFICIARY_CONFIRM.version.xml",
//					"R\\d\\d_FT_ModelBank_1_WIN_\\d\\d.0/ds-generated/test.enquiry.xml",
			};
	
	@Test
	public void testGeneratedPackage() throws URISyntaxException, IOException {
		URL tarUrl = getClass().getResource("/generated-package.tar");
		Assert.assertNotNull("The generated tar is missing", tarUrl);

		InputStream tarStream = null;
		try {
			File tar = new File(tarUrl.toURI());
			List<String> entries = TarHelper.listTarEntries(tar);
			boolean entriesMissing = false;
			StringBuffer errorMessage = new StringBuffer();
			for (String expectedEntry: EXPECTED_ENTRIES) {
				boolean entryFound = false;
				for (String entry: entries) {
					if (entry.matches(expectedEntry)) {
						entryFound = true;
						break;
					}
				}
				if (!entryFound) {
					entriesMissing = true;
					errorMessage.append("Entry missing: " + expectedEntry + "\n");
				}
			}
			
			String allEntries = "Complete list of entries:\n" + StringUtils.join(entries, " \n");
			Assert.assertFalse(errorMessage.toString() + allEntries, entriesMissing);
			Assert.assertEquals("Wrong number of entries.\n" + allEntries, EXPECTED_ENTRIES.length, entries.size());
		} finally {
			IOUtils.closeQuietly(tarStream);
		}
	}

}
