package com.odcgroup.integrationfwk.ui.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.editors.FlowsEditor;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;

/*
 * this is a utility class with compendium of useful methods
 */
public class Utils {

	/**
	 * Helps to clean the invalid characters (which are all not compatible with
	 * schema element name) from given value.
	 * 
	 * @param value
	 * @return string which doesn't contains any invalid characters.
	 */
	private static String cleanInvalidCharacters(String value) {
		if (value == null || value.length() == 0) {
			return value;
		}
		// removing space and non alpha numeric characters. What if the incoming
		// value starts with number which is invalid schema element name!!! As
		// of now we are believing in T24 which gives us the value as
		// ENQUIRY.NUMBER, not as 123ENQUIRY.NUMBER
		value = value.replaceAll("[\\W\\s]", "");
		// replace the numbers with '_' symbol which we are not require now
		// since we are believing in T24.
		// text = text.replaceAll("\\d+", "_");

		// if the field name starts with the number, clean the numeric
		// characters
		if (Character.isDigit(value.charAt(0))) {
			value = value.replaceFirst("\\d+", "");
		}

		return value;
	}

	/**
	 * checking whether the given string contains any invalid characters or not.
	 * Here invalid in the sense of characters which are all not allowed in XML
	 * schema.
	 * 
	 * @param value
	 * @return true if the given string contains invalid character, false
	 *         otherwise
	 */
	public static boolean containsInvalidChar(String value) {
		Pattern inValidCharPattern = Pattern.compile("^[\\d].*|.*[\\W\\s]+.*");
		Matcher inValidCharMatcher = inValidCharPattern.matcher(value);
		return inValidCharMatcher.matches();
	}

	/**
	 * Make sure project name, flow name and Event name can contain only
	 * alphanumeric values and do not start with number
	 * **/

	public static boolean containsValidChar(String value) {
		Pattern ValidCharPattern = Pattern.compile("[a-zA-Z0-9]+");
		Matcher ValidCharMatcher = ValidCharPattern.matcher(value);
		return ValidCharMatcher.matches();
	}

	/**
	 * /** Helps to enclose the given value into double quotes.
	 * 
	 * @param value
	 * @return empty string if the given value is null, as it is if in case of
	 *         the incoming value is already enclosed with double quotes,
	 *         otherwise returns the double quoted value.
	 */
	public static String convertToDoubleQuotedString(String value) {
		if (value == null) {
			return "";
		}

		if (value.matches("^[\"]+.*[\"]+$")) {
			return value;
		}

		return "\"" + value + "\"";
	}

	/**
	 * Responsible for converting given T24 Version name to T24 application
	 * name.
	 * 
	 * @param version
	 *            - T24 Version.
	 * @return T24 Application name corresponding to given version name.
	 */
	public static String convertVersionToApplication(String version) {
		if (version == null || version.length() == 0) {
			// shouldn't happen.
			return "";
		}

		if (!version.contains(",")) {
			// not an version hence return as it is.
			return version;
		}

		return version.substring(0, version.indexOf(","));
	}

	/**
	 * Method which helps to get the all dirty integration editors(events/flow
	 * editor)regardless of which project it belongings to.
	 * 
	 * @return list of dirty integration editors.
	 */
	// TODO: to be moved to eclipse related util class
	public static List<IEditorPart> getDirtyIntegrationEditors() {
		List<IEditorPart> dirtyIntegrationEditors = new ArrayList<IEditorPart>();
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWindow == null) {
			return dirtyIntegrationEditors;
		}
		IWorkbenchPage activePage = activeWindow.getActivePage();
		if (activePage == null) {
			return dirtyIntegrationEditors;
		}
		for (IEditorPart dirtyEditor : activePage.getDirtyEditors()) {
			if (isIntegrationEditor(dirtyEditor)) {
				dirtyIntegrationEditors.add(dirtyEditor);
			}
		}
		return dirtyIntegrationEditors;
	}

	/**
	 * Method which helps to get the all dirty editors belonging to given
	 * project.
	 * 
	 * @param project
	 *            - instance of {@link TWSConsumerProject}
	 * @return collection of dirty editors.
	 */
	// TODO: to be moved to eclipse related util class.
	public static List<IEditorPart> getDirtyIntegrationProjectEditors(
			TWSConsumerProject project) {
		List<IEditorPart> allDirtyIntegrationEditor = getDirtyIntegrationEditors();
		for (ListIterator<IEditorPart> editorIterator = allDirtyIntegrationEditor
				.listIterator(); editorIterator.hasNext();) {
			IEditorPart editor = editorIterator.next();
			if (!isEditorBelongingToGivenProject(editor, project)) {
				editorIterator.remove();
			}
		}
		return allDirtyIntegrationEditor;
	}

	/*
	 * Methid received habndle to file and returns the name of the file without
	 * extention
	 */
	public static String getFileNameWithoutExtention(IFile iFile,
			String extention) {
		String fileName = iFile.getName();
		String justName = removeFileExtention(fileName, extention);
		return justName;
	}

	public static String[] getFilteredArray(String[] array, String filter) {
		if (array == null) {
			return null;
		}

		List<String> workingList = new LinkedList<String>();
		for (int i = 0; i < array.length; i++) {
			if (array[i].startsWith(filter)) {
				workingList.add(array[i]);
			}
		}
		String[] after = workingList.toArray(new String[0]);

		return after;

	}

	/**
	 * removing the project name from the given string and return the flow name
	 * only
	 * 
	 * @param flowNameWithProjectName
	 * @return
	 */
	public static String getFlowName(String flowNameWithProjectName) {
		return flowNameWithProjectName.substring(flowNameWithProjectName
				.indexOf("-") + 1);
	}

	/**
	 * Helps to get the month name equivalent to given month number.
	 * 
	 * @param month
	 *            - month in number.
	 * @return name of the month.
	 */
	private static String getMonthName(int month) {
		String[] monthNamesArray = { "Jan", "Feb", "Mar", "Apr", "May", "June",
				"July", "Aug", "Sep", "Oct", "Nov", "Dec" };
		// helps to get the month name in full (1 -> January).
		// Since the 3 letters month name is requested, handled via stored
		// values, not through this core java method.
		// return new DateFormatSymbols().getMonths()[month];
		return monthNamesArray[month];
	}

	public static String getSchemaName(String text) {
		if (text == null || text.trim().length() == 0) {
			// for exceptional scenario
			return text;
		}

		if (!text.contains(".")) {
			// given text value doesn't contains . symbol (which we are require
			// to process further). Hence return the text after clean up and
			// convert it into small case.
			return cleanInvalidCharacters(text.toLowerCase());
		}
		// split the whole text with . symbol
		String[] textParts = text.split("\\.");
		if (textParts.length == 0) {
			// return the text as it is if it's only containing dots(..)
			return text;
		}
		// convert the first part of whole string to lower case, to achieve the
		// camel case string.
		text = textParts[0].toLowerCase();
		for (int i = 1; i < textParts.length; i++) {
			String temp = textParts[i];
			if (temp.length() == 0) {
				continue;
			}
			// convert the whole string to lower case.
			temp = temp.toLowerCase();
			// convert the first character to upper case.
			// and then add the remaining characters.
			text += Character.toUpperCase(temp.charAt(0)) + temp.substring(1);
		}
		text = cleanInvalidCharacters(text);
		return text;
	}

	public static String getTimeStamp(boolean forFileName) {
		Calendar calendar = new GregorianCalendar();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String strHour = (hour < 10 ? "0" : "") + hour;
		int minute = calendar.get(Calendar.MINUTE);
		String strMinute = (minute < 10 ? "0" : "") + minute;
		// calendar.get(Calendar.SECOND);
		int date = calendar.get(Calendar.DATE);
		String month = getMonthName(calendar.get(Calendar.MONTH));
		int year = calendar.get(Calendar.YEAR);
		if (calendar.get(Calendar.AM_PM) == 0) {
		} else {
		}
		if (forFileName) {
			return date + "-" + month + "-" + year + " " + strHour + "-"
					+ strMinute;
		} else {
			return date + "-" + month + "-" + year + " " + strHour + ":"
					+ strMinute;
		}
	}

	/**
	 * Method which helps to decide whether the given editor is belonging to
	 * given consumer project or not.
	 * 
	 * @param editor
	 *            - instance of {@link IEditorPart}
	 * @param project
	 *            - instance of {@link TWSConsumerProject}
	 * @return true if its belonging to given project, false otherwise.
	 */
	private static boolean isEditorBelongingToGivenProject(IEditorPart editor,
			TWSConsumerProject project) {
		// logic goes here is: since the incoming project is twsconsumer project
		// we could assume that the incoming editor is belonging to either
		// events editor or flows editor. since project name is unique and both
		// of the editor has project name within it we are going to match this
		// project name with incoming project's name.
		if (editor instanceof EventsEditor) {
			return ((EventsEditor) editor).getProjectName().equalsIgnoreCase(
					project.getTWSProjectName());
		} else if (editor instanceof FlowsEditor) {
			return ((FlowsEditor) editor).getProjectName().equalsIgnoreCase(
					project.getTWSProjectName());
		} else {
			return false;
		}
	}

	/**
	 * Method which helps to decide whether the given editor part is belonging
	 * to integration project or not.
	 * 
	 * @param editorPart
	 *            - instance of {@link IEditorPart} or its compatible instance.
	 * @return true if the given editor belonging to integration project, false
	 *         otherwise.
	 */
	private static boolean isIntegrationEditor(IEditorPart editorPart) {
		if (editorPart == null) {
			return false;
		}
		return editorPart instanceof EventsEditor
				|| editorPart instanceof FlowsEditor;
	}

	/*
	 * Opens file in an editor
	 */
	public static void openEditor(final IFile iFile) {

		IWorkbench workbench2 = IntegrationToolingActivator.getDefault()
				.getWorkbench();

		final IWorkbenchWindow workbenchWindow = workbench2
				.getActiveWorkbenchWindow();

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					String editorId = null;

					// System.out.println("workbechn   " +
					// PlatformUI.getWorkbench().getEditorRegistry().findEditor(EventsEditor.EDITOR_ID));
					// IEditorDescriptor editor =
					// PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(iFile.getLocation().toOSString(),
					// iFile.getContentDescription().getContentType());
					// working with file name rather than content
					IEditorDescriptor editor = PlatformUI.getWorkbench()
							.getEditorRegistry()
							.getDefaultEditor(iFile.getName());

					// IEditorDescriptor editor =
					// PlatformUI.getWorkbench().getEditorRegistry().findEditor(EventsEditor.EDITOR_ID);
					if (editor != null) {
						editorId = editor.getId();
					}
					workbenchWindow.getActivePage().openEditor(
							new FileEditorInput(iFile), editorId);
				} catch (PartInitException ex) {
					TWSConsumerLog.logError(ex);
				} catch (CoreException ex) {
					TWSConsumerLog.logError(ex);
				}
			}
		});

	}

	/*
	 * Method is generic for opening a wizard in eclipse copied form
	 * http://torkildr.blogspot.com/2010/07/invoking-eclipse-wizard.html
	 */
	public static void openWizard(String id) {
		// First see if this is a "new wizard".
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
				.getNewWizardRegistry().findWizard(id);
		// If not check if it is an "import wizard".
		if (descriptor == null) {
			descriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
					.findWizard(id);
		}
		// Or maybe an export wizard
		if (descriptor == null) {
			descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
					.findWizard(id);
		}
		try {
			// Then if we have a wizard, open it.
			if (descriptor != null) {
				IWizard wizard = descriptor.createWizard();
				WizardDialog wd = new WizardDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), wizard);

				System.out.print("wizard" + wizard);
				wd.setTitle(wizard.getWindowTitle());
				wd.open();
			}
		} catch (CoreException e) {
			TWSConsumerLog.logError(e);
		}
	}

	public static String removeFileExtention(String fileName,
			String extentionName) {
		if ((fileName == null) || fileName.equals("")) {
			throw new IllegalArgumentException(
					"File name can not be null or empty");
		}
		if ((extentionName == null) || extentionName.equals("")) {
			throw new IllegalArgumentException(
					"Extention name can not be null or empty");
		}
		if (!fileName.endsWith(extentionName)) {
			throw new IllegalArgumentException("File name " + fileName
					+ " does not have extention " + extentionName);
		}
		// String result = fileName.replace("." + extentionName, "");
		String result = fileName.substring(0,
				fileName.lastIndexOf("." + extentionName));
		return result;
	}

	/*
	 * Method removes the names of Applications / Versions which is starting
	 * with 'AA.ARR.'
	 */
	public List<String> removeAAArrangementRelated(List<String> inputList) {
		List<String> validList = new ArrayList<String>();
		if (inputList != null) {
			Iterator<String> applications = inputList.iterator();
			while (applications.hasNext()) {
				String application = applications.next().toString();
				if (!application.trim().startsWith("AA.ARR")) {
					validList.add(application.trim());
				}
			}
		}
		return validList;
	}
}
