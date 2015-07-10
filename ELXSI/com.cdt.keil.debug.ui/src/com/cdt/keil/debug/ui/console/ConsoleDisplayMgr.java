package com.cdt.keil.debug.ui.console;

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
 
/**
 * Create an instance of this class in any of your plugin classes.
 * 
 * Use it as follows ...
 * 
 * ConsoleDisplayMgr.getDefault().println("Some error msg", ConsoleDisplayMgr.MSG_ERROR);
 * ...
 * ...
 * ConsoleDisplayMgr.getDefault().clear();
 * ...  
 */
public class ConsoleDisplayMgr
{
	private static ConsoleDisplayMgr fDefault = null;
	private String fTitle = null;
	private MessageConsole fMessageConsole = null;
	
	public static final int MSG_INFORMATION = 1;
	public static final int MSG_ERROR = 2;
	public static final int MSG_WARNING = 3;
	
	public static IConsole[] console=null;
	
	public ConsoleDisplayMgr(String messageTitle)
	{		
		fDefault = this;
		fTitle = messageTitle;		
	}
	
	public static ConsoleDisplayMgr getDefault() {
		return fDefault;
	}	
		
	public void println(String msg, int msgKind)
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
		getNewMessageConsoleStream(msgKind).println(msg);				
	}
	
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
	
	public void clear()
	{		
		IDocument document = getMessageConsole().getDocument();
		if (document != null) {
			document.set("");
		}			
	}	
		
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
			case MSG_WARNING:
				swtColorId = SWT.COLOR_DARK_GREEN;
				break;
			default:				
		}	
		
		MessageConsoleStream msgConsoleStream = getMessageConsole().newMessageStream();		
		
		try{
			msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId));
		}catch(Exception e){}
		
		return msgConsoleStream;
	}
	
	private MessageConsole getMessageConsole()
	{
		if( fMessageConsole == null )
			createMessageConsoleStream(fTitle);	
		
		return fMessageConsole;
	}
		
	private void createMessageConsoleStream(String title)
	{
		 
		fMessageConsole = new MessageConsole(title, null);
		console = new IConsole[]{ fMessageConsole };
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(console);
	}	
}
 
