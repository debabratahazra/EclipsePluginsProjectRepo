package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PrepareDeploymentHelperTest {

	private static final String THIS_DOES_NOT_EXIST = "this_does_not_exist.jar";

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	private static final String ORGINAL_JAR_NAME = "test.jar";
	private static final String BACKED_UP_JAR_NAME = ORGINAL_JAR_NAME + PrepareDeploymentHelper.ORIGINAL_EXTENSION;

	private File backedUpJar;
	private File originalJar;
	private File thisDoesNotExistJar;

	@After
	public void tearDown() throws Exception {
		if(backedUpJar != null && backedUpJar.exists()) {backedUpJar.delete();}
		if(originalJar != null && originalJar.exists()) {originalJar.delete();}
		if(thisDoesNotExistJar != null && thisDoesNotExistJar.exists()) {thisDoesNotExistJar.delete();}
	}

	@Test
	public void testRestoreBackupJarReturnsStatusErrorWhenNullFileIsPassed() throws Exception {
		File nullFile = null;

		IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(nullFile);

		Assert.assertEquals(IStatus.ERROR, actualStatus.getSeverity());
	}

	@Test
	public void testRestoreBackupJarRestoresOriginalDeployedFile() throws Exception {
		originalJar = testFolder.newFile(ORGINAL_JAR_NAME);
		backedUpJar = testFolder.newFile(BACKED_UP_JAR_NAME);
		Assert.assertTrue("Test file was not created.", originalJar.exists());
		Assert.assertTrue("Test file was not created.", backedUpJar.exists());

		IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(backedUpJar);

		Assert.assertEquals(Status.OK_STATUS, actualStatus);
		Assert.assertTrue(originalJar.exists());
	}

	@Test
	public void testRestoreBackupJarRestoresOriginalDeployedFileAndRemovesBackupFile() throws Exception {
		backedUpJar = testFolder.newFile(BACKED_UP_JAR_NAME);
		originalJar = testFolder.newFile(ORGINAL_JAR_NAME);
		Assert.assertTrue("Test file was not created.", originalJar.exists());
		Assert.assertTrue("Test file was not created.", backedUpJar.exists());

		IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(backedUpJar);

		Assert.assertEquals(Status.OK_STATUS, actualStatus);
		Assert.assertFalse(backedUpJar.exists());
	}

	@Test
	public void testRestoreBackupJarReturnsErrorStatusWhenTryingToRestoreFileWithoutOriginalAppened() throws Exception {
		originalJar = testFolder.newFile(ORGINAL_JAR_NAME);
		Assert.assertTrue("Test file was not created.", originalJar.exists());

		IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(originalJar);

		Assert.assertEquals(IStatus.ERROR, actualStatus.getSeverity());
		Assert.assertTrue("File was not restored.", originalJar.exists());
	}

	@Test
	public void testRestoreBackupJarReturnsErrorStatusWhenUnableToRestoreName() throws Exception {
		backedUpJar = testFolder.newFile(BACKED_UP_JAR_NAME);
		Assert.assertTrue("Test file was not created.", backedUpJar.exists());

		RandomAccessFile raf = new RandomAccessFile(backedUpJar, "rw");
		FileChannel fc = raf.getChannel();
		FileLock lock = fc.tryLock();

		try {
			IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(backedUpJar);

			Assert.assertEquals(IStatus.ERROR, actualStatus.getSeverity());
			Assert.assertTrue("Test file was not created.", backedUpJar.exists());
		} finally {
			lock.release();
			raf.close();
		}
	}

	@Test
	public void testRestoreBackupJarReturnsErrorStatusWhenUnableToDeleteExistingFolder() throws Exception {
		originalJar = testFolder.newFile(ORGINAL_JAR_NAME);
		backedUpJar = testFolder.newFile(BACKED_UP_JAR_NAME);
		Assert.assertTrue("Test file was not created.", originalJar.exists());
		Assert.assertTrue("Test file was not created.", backedUpJar.exists());

		RandomAccessFile raf = new RandomAccessFile(originalJar, "rw");
		FileChannel fc = raf.getChannel();
		FileLock lock = fc.tryLock();

		try {
			IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(backedUpJar);
			Assert.assertEquals(IStatus.ERROR, actualStatus.getSeverity());
			Assert.assertTrue("Test file was not created.", backedUpJar.exists());
		} finally {
			lock.release();
			raf.close();
		}
	}

	@Test
	public void testRestoreBackupJarReturnsOKtatusWhenJarToRestoreDoesntExistAndOriginalDoesntExist() throws Exception {
		IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(new File(THIS_DOES_NOT_EXIST + PrepareDeploymentHelper.ORIGINAL_EXTENSION));

		Assert.assertEquals(Status.OK_STATUS, actualStatus);
	}

	@Test
	public void testRestoreBackupJarReturnsErrorStatusWhenJarToRestoreDoesntExist() throws Exception {
		originalJar = testFolder.newFile(ORGINAL_JAR_NAME);
		backedUpJar = new File(originalJar + PrepareDeploymentHelper.ORIGINAL_EXTENSION);
		Assert.assertTrue("Test file was not created.", originalJar.exists());
		Assert.assertFalse("Test file should not exist.", backedUpJar.exists());
		IStatus actualStatus = PrepareDeploymentHelper.restoreBackupJar(backedUpJar);

		Assert.assertEquals(IStatus.ERROR, actualStatus.getSeverity());
	}
}
