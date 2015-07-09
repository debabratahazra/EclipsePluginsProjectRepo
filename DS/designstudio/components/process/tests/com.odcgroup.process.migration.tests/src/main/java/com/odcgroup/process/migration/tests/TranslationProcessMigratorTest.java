package com.odcgroup.process.migration.tests;

import java.util.Locale;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.migration.ProcessTranslationMigrator;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.migration.TranslationVO;

/**
 * @author yan
 */
public class TranslationProcessMigratorTest {

	private static final EObject DUMMY_WORKFLOW_TASK_MODEL = new EObjectImpl() {};
	private static final Process DUMMY_WORKFLOW_MODEL = ProcessFactory.eINSTANCE.createProcess();
	
	private static class WorkflowTranslationMigratorForTest extends ProcessTranslationMigrator {
		private boolean pushTranslationInModelCalled;
		private ITranslationKind kindFound;
		private Locale localeFound;
		private String textFound;
		private EObject modelFound;
		
		@Override
		protected boolean pushTranslationInModel(EObject workflowModelElement,
				ITranslationKind kind, Locale locale, String text) {
			this.pushTranslationInModelCalled = true;
			this.modelFound = workflowModelElement;
			this.kindFound = kind;
			this.localeFound = locale;
			this.textFound = text;
			return true;
		}
		
		protected EObject findWorkflowTaskModel(String key) {
			return DUMMY_WORKFLOW_TASK_MODEL;
		}

		/**
		 * @param ofsProject
		 * @param modelURI
		 * @param key
		 * @return
		 */
		protected Process findWorkflowModel(String key) {
			return DUMMY_WORKFLOW_MODEL;
		}

	}
	
	private WorkflowTranslationMigratorForTest migrator;
	
	@Before
	public void setUp() {
		migrator = new WorkflowTranslationMigratorForTest();
	}
	
	@Test
	public void testWorkflowTranslationMigrator1() {
		TranslationVO vo = new TranslationVO("process.task.dummy.name", "fr", "dummy");
		boolean result = migrator.process(vo);
		Assert.assertTrue("should be successful", result);
		Assert.assertTrue("should have pushed the translation", migrator.pushTranslationInModelCalled);
		Assert.assertEquals("should have found a task model", DUMMY_WORKFLOW_TASK_MODEL, migrator.modelFound);
		Assert.assertEquals("wrong kind found", ITranslationKind.NAME, migrator.kindFound);
		Assert.assertEquals("wrong language", "fr", migrator.localeFound.toString());
		Assert.assertEquals("wrong text", "dummy", migrator.textFound);
	}
	
	@Test
	public void testWorkflowTranslationMigrator2() {
		TranslationVO vo = new TranslationVO("process.task.dummy.description", "fr", "dummy");
		boolean result = migrator.process(vo);
		Assert.assertTrue("should be successful", result);
		Assert.assertTrue("should have pushed the translation", migrator.pushTranslationInModelCalled);
		Assert.assertEquals("should have found a task model", DUMMY_WORKFLOW_TASK_MODEL, migrator.modelFound);
		Assert.assertEquals("wrong kind found", ITranslationKind.TEXT, migrator.kindFound);
		Assert.assertEquals("wrong language", "fr", migrator.localeFound.toString());
		Assert.assertEquals("wrong text", "dummy", migrator.textFound);
	}		
		
	@Test
	public void testWorkflowTranslationMigrator3() {
		TranslationVO vo = new TranslationVO("process.dummy.description", "en", "dummy");
		boolean result = migrator.process(vo);
		Assert.assertTrue("should be successful", result);
		Assert.assertTrue("should have pushed the translation", migrator.pushTranslationInModelCalled);
		Assert.assertEquals("should have found a task model", DUMMY_WORKFLOW_MODEL, migrator.modelFound);
		Assert.assertEquals("wrong kind found", ITranslationKind.TEXT, migrator.kindFound);
		Assert.assertEquals("wrong language", "en", migrator.localeFound.toString());
		Assert.assertEquals("wrong text", "dummy", migrator.textFound);
	}		
		
}
