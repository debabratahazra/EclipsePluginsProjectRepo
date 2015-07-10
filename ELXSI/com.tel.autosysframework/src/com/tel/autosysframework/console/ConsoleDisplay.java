package com.tel.autosysframework.console;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
 

public class ConsoleDisplay
{
	private static ConsoleDisplay fDefault = null;
	private String fTitle = null;
	private MessageConsole fMessageConsole = null;	
	
	public static final int MSG_INFORMATION = 1;		//Blue color
	public static final int MSG_ERROR = 2;				//Red color
	private static final int MSG_SIMULATION = 3;		//Cyan color
	public static final int MSG_WARNING = 4;			//Green color
	
	
	public static IConsole[] console = null;
	
	/**
	 * Constructor: Create the new console with the title name.
	 * @param messageTitle
	 */
	public ConsoleDisplay(String messageTitle)
	{		
		fDefault = this;
		fTitle = messageTitle;		
	}
	
	/**
	 * Get ConsoleDisplay class instance. 
	 * @return
	 */
	public static ConsoleDisplay getDefault() {
		return fDefault;
	}	
		
	/**
	 * print the message with new line in the console.
	 * @param msg
	 * @param msgKind => 1- Blue, 2- Red, 3- Green,  4- Cyan
	 */
	public void println(String msg, int msgKind)
	{		
		if( msg == null ) return;
		
		/* if console-view in Java-perspective is not active, then show it and
		 * then display the message in the console attached to it */		
		if( !displayConsoleView() )
		{
			/*If an exception occurs while displaying in the console, then just display at least the same in a message-box */
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", msg);
			return;
		}
		
		/* display message on console */	
		getNewMessageConsoleStream(msgKind).println(msg);				
	}
	
	/**
	 * Print the message in the console.
	 * @param msg
	 * @param msgKind  => 1- Blue, 2- Red, 3- Cyan, 4- Green
	 */
	public void print(String msg, int msgKind)
	{		
		if( msg == null ) return;
		
		/* if console-view in Java-perspective is not active, then show it and
		 * then display the message in the console attached to it */		
		if( !displayConsoleView() )
		{
			/*If an exception occurs while displaying in the console, then just diplay atleast the same in a message-box */
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", msg);
			return;
		}
		
		/* display message on console */	
		getNewMessageConsoleStream(msgKind).print(msg);				
	}
	
	/**
	 * Clear the console
	 */
	public void clear()
	{		
		IDocument document = getMessageConsole().getDocument();
		if (document != null) {
			document.set("");
		}			
	}	
		
	/**
	 * Create a new instance of Eclipse Console and activate it.
	 * @return
	 */
	private boolean displayConsoleView()
	{
		try
		{
			IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if( activeWorkbenchWindow != null )
			{
				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				if( activePage != null )
					activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW, null, IWorkbenchPage.VIEW_VISIBLE);
			}
			
		} catch (PartInitException partEx) {			
			return false;
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Color the message
	 * @param msgKind
	 * @return
	 */
	private MessageConsoleStream getNewMessageConsoleStream(int msgKind)
	{		
		int swtColorId = SWT.COLOR_BLACK;
		
		switch (msgKind)
		{
			case MSG_INFORMATION:
				swtColorId = SWT.COLOR_BLUE;				
				break;
			case MSG_ERROR:
				swtColorId = SWT.COLOR_RED;
				break;
			case MSG_SIMULATION:
				swtColorId = SWT.COLOR_DARK_GREEN;				
				break;
			case MSG_WARNING:
				swtColorId = SWT.COLOR_DARK_CYAN;				
				break;
			default:				
		}	
		
		MessageConsoleStream msgConsoleStream = getMessageConsole().newMessageStream();		
		
		try{
			//If Non-UI thread access UI thread, then it goes to an exception. 
			msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId));
		}catch(Exception e){}
		
		return msgConsoleStream;
	}
	
	/**
	 * Get the instance of MessageConsole class
	 * @return instance
	 */
	private MessageConsole getMessageConsole()
	{
		if( fMessageConsole == null )
			createMessageConsoleStream(fTitle);			
		return fMessageConsole;
	}
		
	/**
	 * Create the stream for write into the console.
	 * @param title
	 */
	private void createMessageConsoleStream(String title)
	{
		 
		fMessageConsole = new MessageConsole(title, null);
		console = new IConsole[]{ fMessageConsole };
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(console);
	}	
}
 
