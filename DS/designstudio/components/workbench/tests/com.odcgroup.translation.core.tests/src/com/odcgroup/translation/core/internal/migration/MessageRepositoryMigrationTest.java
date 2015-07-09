package com.odcgroup.translation.core.internal.migration;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author yan
 */
public class MessageRepositoryMigrationTest {
	
	private static class AlwaysSuccessfullMigrator implements ITranslationModelMigrator {
		public int isKeyAccptedCalled;
		public int processCalled;
		public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		}
		public boolean isKeyAccepted(String key) {
			isKeyAccptedCalled++;
			return true;
		}
		public boolean process(TranslationVO vo) {
			processCalled++;
			return true;
		}
		public void reset() {
			isKeyAccptedCalled = 0;
			processCalled = 0;
		}
		public List<TranslationVO> endMigration() {
			return new ArrayList<TranslationVO>();
		}
	};

	private static class RejectAllMigrator implements ITranslationModelMigrator { 
		public int isKeyAccptedCalled;
		public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		}
		public boolean isKeyAccepted(String key) {
			isKeyAccptedCalled++;
			return false;
		}
		public boolean process(TranslationVO vo) {
			throw new IllegalStateException("Shoud never be called");
		}
		public void reset() {
			isKeyAccptedCalled = 0;
		}
		public List<TranslationVO> endMigration() {
			return new ArrayList<TranslationVO>();
		}
	};

	private static class AcceptAllAlwaysFailMigrator implements ITranslationModelMigrator { 
		public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		}
		public boolean isKeyAccepted(String key) {
			return true;
		}
		public boolean process(TranslationVO vo) {
			return false;
		}
		public void reset() {
		}
		public List<TranslationVO> endMigration() {
			return new ArrayList<TranslationVO>();
		}
	};

	private static class AcceptDummyModelsAlwaysSuccessfulMigrator implements ITranslationModelMigrator { 
		public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		}
		public boolean isKeyAccepted(String key) {
			return key.startsWith("dummy.");
		}
		public boolean process(TranslationVO vo) {
			return true;
		}
		public void reset() {
		}
		public List<TranslationVO> endMigration() {
			return new ArrayList<TranslationVO>();
		}
	};

	private static class ShouldNeverBeCalledMigrator implements ITranslationModelMigrator {
		public void startMigration(Logger migrationLogger, IOfsProject ofsProject) {
		}
		public boolean isKeyAccepted(String key) {
			throw new IllegalStateException("Shoud never be called");
		}
		public boolean process(TranslationVO vo) {
			throw new IllegalStateException("Shoud never be called");
		}
		public List<TranslationVO> endMigration() {
			throw new IllegalStateException("Shoud never be called");
		}
	};
		
	private static final AlwaysSuccessfullMigrator ALWAYS_SUCCESSFULL_MIGRATOR = 
			new AlwaysSuccessfullMigrator();
	
	private static final RejectAllMigrator REJECT_ALL_MIGRATOR =
			new RejectAllMigrator();

	private static final AcceptAllAlwaysFailMigrator ACCEPT_ALL_ALWAYS_FAIL_MIGRATOR =
			new AcceptAllAlwaysFailMigrator();

	private static final ShouldNeverBeCalledMigrator SHOULD_NEVER_BE_CALLED_MIGRATOR =
			new ShouldNeverBeCalledMigrator();
	
	private static final AcceptDummyModelsAlwaysSuccessfulMigrator ACCEPT_DUMMY_MODELS_ALWAYS_SUCCESSFUL_MIGRATOR = 
			new AcceptDummyModelsAlwaysSuccessfulMigrator();

	private static final TranslationVO DUMMY_VO = new TranslationVO("dummy.key.text", "en", "hello");
	private static final TranslationVO TEST_VO = new TranslationVO("test.key.text", "fr", "salue");
	
	@After
	public void tearDown() {
		ALWAYS_SUCCESSFULL_MIGRATOR.reset();
		REJECT_ALL_MIGRATOR.reset();
		ACCEPT_ALL_ALWAYS_FAIL_MIGRATOR.reset();
		ACCEPT_DUMMY_MODELS_ALWAYS_SUCCESSFUL_MIGRATOR.reset();
	}
	
	@Test
	public void testProcessByMigrator1() {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		migration.modelMigrators.clear();
		migration.modelMigrators.add(ALWAYS_SUCCESSFULL_MIGRATOR);
		migration.modelMigrators.add(SHOULD_NEVER_BE_CALLED_MIGRATOR);
		
		Assert.assertTrue("migration should be successful", migration.processByMigrator(DUMMY_VO));
	}

	@Test
	public void testProcessByMigrator2() {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		migration.modelMigrators.clear();
		migration.modelMigrators.add(REJECT_ALL_MIGRATOR);
		
		Assert.assertFalse("migration should be failing", migration.processByMigrator(DUMMY_VO));
		Assert.assertEquals("should be called once", 1, REJECT_ALL_MIGRATOR.isKeyAccptedCalled);
	}

	@Test
	public void testProcessByMigrator3() {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		migration.modelMigrators.clear();
		migration.modelMigrators.add(ACCEPT_ALL_ALWAYS_FAIL_MIGRATOR);
		migration.modelMigrators.add(SHOULD_NEVER_BE_CALLED_MIGRATOR);
		
		Assert.assertFalse("migration should be failing", migration.processByMigrator(DUMMY_VO));
	}

	@Test
	public void testProcessByMigrator4() {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		migration.modelMigrators.clear();
		migration.modelMigrators.add(ACCEPT_DUMMY_MODELS_ALWAYS_SUCCESSFUL_MIGRATOR);
		migration.modelMigrators.add(SHOULD_NEVER_BE_CALLED_MIGRATOR);
		
		Assert.assertTrue("migration should be successful", migration.processByMigrator(DUMMY_VO));
	}

	@Test
	public void testProcessByMigrator5() {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		migration.modelMigrators.clear();
		migration.modelMigrators.add(ACCEPT_DUMMY_MODELS_ALWAYS_SUCCESSFUL_MIGRATOR);
		
		Assert.assertFalse("migration should be failing", migration.processByMigrator(TEST_VO));
	}

	@Test
	public void testProcessByMigrator6() {
		MessageRepositoryMigration migration = new MessageRepositoryMigration();
		migration.modelMigrators.clear();
		migration.modelMigrators.add(ACCEPT_DUMMY_MODELS_ALWAYS_SUCCESSFUL_MIGRATOR);
		migration.modelMigrators.add(ALWAYS_SUCCESSFULL_MIGRATOR);
		
		Assert.assertTrue("migration should be successful", migration.processByMigrator(DUMMY_VO));
		Assert.assertEquals("should nerver be called", 0, ALWAYS_SUCCESSFULL_MIGRATOR.isKeyAccptedCalled);
		Assert.assertEquals("should nerver be called", 0, ALWAYS_SUCCESSFULL_MIGRATOR.processCalled);
	}

}
