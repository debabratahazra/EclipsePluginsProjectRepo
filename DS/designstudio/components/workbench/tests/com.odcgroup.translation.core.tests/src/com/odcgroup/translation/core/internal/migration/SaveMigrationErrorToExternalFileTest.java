package com.odcgroup.translation.core.internal.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Validate that file are properly written to an external error file
 * if something goes wrong during migration
 * @author yan
 */
public class SaveMigrationErrorToExternalFileTest extends AbstractDSUnitTest {

	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testWriteNotProcessTranslations() throws IOException {
		List<TranslationVO> translations = new LinkedList<TranslationVO>();
		for (int i=0;i<5;i++) {
			translations.add(new TranslationVO("some.key"+i, "some.lang"+i, "some text"+i, "some error message"+i));
		}
		String migrationFile = new MessageRepositoryMigration().writeNotProcessTranslations(getOfsProject(), translations);

		Assert.assertTrue("The file should exist", new File(migrationFile).exists());
		List<String> fileContents = getContents(migrationFile);
		Assert.assertEquals(6, fileContents.size()); // 1 header + 5 lines
		Assert.assertEquals("project;key;language;value;problem", fileContents.get(0));
		Assert.assertEquals(getProject().getName() + MessageRepositoryMigration.SEPARATOR + 
				"some.key3" + MessageRepositoryMigration.SEPARATOR +  
				"some.lang3" + MessageRepositoryMigration.SEPARATOR +  
				"some text3" + MessageRepositoryMigration.SEPARATOR +
				"some error message3", 
				fileContents.get(4));
	}

	private List<String> getContents(String filename) throws IOException {
		List<String> contents = new LinkedList<String>();
		BufferedReader input = new BufferedReader(new FileReader(new File(filename)));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				contents.add(line);
			}
		} finally {
			input.close();
		}
		return contents;
	}

}
