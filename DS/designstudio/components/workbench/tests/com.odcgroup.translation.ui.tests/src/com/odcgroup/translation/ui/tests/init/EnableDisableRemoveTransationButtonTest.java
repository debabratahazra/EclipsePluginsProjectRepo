package com.odcgroup.translation.ui.tests.init;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.command.TranslationRemoveCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

public class EnableDisableRemoveTransationButtonTest {

	ITranslationModel model;
	ITranslation translation;
	
	@Test
	public void testNonIsInheritableTranslationWithTextButtonShouldBeEnabled() {
		ITranslationModel model = mock(ITranslationModel.class);
       	ITranslation translation = mock(ITranslation.class);
       	when(model.getTranslation()).thenReturn(translation);
    	when(model.getText()).thenReturn("sometext");
       	when(translation.isInheritable()).thenReturn(false);
       	MockTranslationRemoveCommand command = new MockTranslationRemoveCommand(model);
       	command.updateCommandState(model);
       	Assert.assertTrue(command.isEnabled());
	}
	
	@Test
	public void testNonIsInheritableTranslationWithNoTextButtonShouldBeDisabled() {
		ITranslationModel model = mock(ITranslationModel.class);
       	ITranslation translation = mock(ITranslation.class);
       	when(model.getTranslation()).thenReturn(translation);
    	when(model.getText()).thenReturn(null);
       	when(translation.isInheritable()).thenReturn(false);
       	MockTranslationRemoveCommand command = new MockTranslationRemoveCommand(model);
       	command.updateCommandState(model);
       	Assert.assertFalse(command.isEnabled());
	}
	
	@Test
	public void testInheritableTranslationWithInheritanceWithWithTextButtonShouldBeEnabled() {
		ITranslationModel model = mock(ITranslationModel.class);
       	ITranslation translation = mock(ITranslation.class);
       	when(model.getTranslation()).thenReturn(translation);
    	when(model.getText()).thenReturn("sometext");
       	when(translation.isInheritable()).thenReturn(false);
       	try {
			when(translation.isInherited(any(ITranslationKind.class), any(Locale.class))).thenReturn(true);
			MockTranslationRemoveCommand command = new MockTranslationRemoveCommand(model);
	       	command.updateCommandState(model);
	       	Assert.assertTrue(command.isEnabled());
		} catch (TranslationException e) {
			//
		}
       
	}
	
	@Test
	public void testInheritableTranslationWithoutInheritanceWithWithoutTextButtonShouldBeDisabled() {
		ITranslationModel model = mock(ITranslationModel.class);
       	ITranslation translation = mock(ITranslation.class);
       	when(model.getTranslation()).thenReturn(translation);
    	when(model.getText()).thenReturn("sometext");
       	when(translation.isInheritable()).thenReturn(true);
       	try {
			when(translation.isInherited(any(ITranslationKind.class), any(Locale.class))).thenReturn(false);
			when(translation.getText(any(ITranslationKind.class), any(Locale.class))).thenReturn(null);
			MockTranslationRemoveCommand command = new MockTranslationRemoveCommand(model);
	       	command.updateCommandState(model);
	       	Assert.assertFalse(command.isEnabled());
		} catch (TranslationException e) {
			//
		}
       
	}
	
	private class MockTranslationRemoveCommand extends TranslationRemoveCommand {
		
		public MockTranslationRemoveCommand(ITranslationModel model) {
			super(model);
		}

		public void execute(String newText) throws CoreException {
			// TODO Auto-generated method stub
		}

		@Override
		protected void initializeTexts() {
			// TODO Auto-generated method stub
			
		}
		@Override
		protected void updateCommandState(ITranslationModel model) {
				super.updateCommandState(model);
		}
		
	}
}
