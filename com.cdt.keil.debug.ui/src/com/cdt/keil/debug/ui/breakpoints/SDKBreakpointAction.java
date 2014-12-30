package com.cdt.keil.debug.ui.breakpoints;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DebugAction;
import com.cdt.keil.debug.ui.editor.info.EditorAddressInfo;
import com.cdt.keil.debug.ui.editor.info.HighlevelAddressInfo;
import com.cdt.keil.debug.ui.internal.BreakpointMap;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;


@SuppressWarnings("restriction")
public class SDKBreakpointAction extends Action implements IUpdate {

	private IVerticalRulerInfo ruler;
	private ITextEditor textEditor;
	@SuppressWarnings("unchecked")
	private List markers;
	public static IMarker [] existingMarkers;
	private static int i=0;
	private static int []arr=new int[90];
	public static int enable[]=new int[99];
	public static int breakpointRemoveAt;

	private String addLabel;
	private String removeLabel;
	
	
	public SDKBreakpointAction(ITextEditor editor, IVerticalRulerInfo r) {
		ruler = r;
		textEditor = editor;
		addLabel = "Add Breakpoint"; 
		removeLabel = "Remove Breakpoint";
	}
	
	
	/** 
	 * @return the resource for which to create the marker or <code>null</code>
	 */
	protected IResource getResource() {
		IEditorInput input= textEditor.getEditorInput();
		IResource resource= (IResource) input.getAdapter(IFile.class);
		if (resource == null) {
			resource= (IResource) input.getAdapter(IResource.class);
		}		
		return resource;
	}
	
	/**
	 * Checks whether a position includes the ruler's line of activity.
	 *
	 * @param position the position to be checked
	 * @param document the document the position refers to
	 * @return <code>true</code> if the line is included by the given position
	 */
	protected boolean includesRulerLine(Position position, IDocument document) {
		if (position != null) {
			try {
				int markerLine= document.getLineOfOffset(position.getOffset());				
				int line= ruler.getLineOfLastMouseButtonActivity();				
				if (line == markerLine) {
					return true;
				}
			} catch (BadLocationException x) {}
			catch(Exception e){}
		}
		return false;
	}
	
	/**
	 * @return this action's vertical ruler
	 */
	protected IVerticalRulerInfo getVerticalRulerInfo() {
		return ruler;
	}
	
	/**
	 * @return this action's editor
	 */
	protected ITextEditor getTextEditor() {
		return textEditor;
	}
	
	/**
	 * @return the marker annotation model of the editor's input.
	 */
	protected AbstractMarkerAnnotationModel getAnnotationModel() {
		IDocumentProvider provider= textEditor.getDocumentProvider();
		IAnnotationModel model= provider.getAnnotationModel(textEditor.getEditorInput());
		if (model instanceof AbstractMarkerAnnotationModel) {
			return (AbstractMarkerAnnotationModel) model;
		}
		return null;
	}
	
	/**
	 * @return the document of the editor's input
	 */
	protected IDocument getDocument() {
		IDocumentProvider provider= textEditor.getDocumentProvider();
		return provider.getDocument(textEditor.getEditorInput());
	}
	
	/**
	 * @see IUpdate#update()
	 */
	public void update() {
		markers= getMarkers();		
		setText(markers.isEmpty() ? addLabel : removeLabel);
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void getLineOExistingMarkers(List markers){

		IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
		try {
			
			Iterator e= markers.iterator();
			
			while (e.hasNext()) {
				IBreakpoint breakpoint= breakpointManager.getBreakpoint((IMarker) e.next());
				ILineBreakpoint lr=(ILineBreakpoint)breakpoint;			
				arr[i]=lr.getLineNumber();			
				i++;				
			}
		} catch (CoreException e) {	}
		catch(Exception e){}
	}
	
	@SuppressWarnings("unchecked")
	protected List getMarkers() {
		
		List breakpoints= new ArrayList();		
		IResource resource= getResource();
		IDocument document= getDocument();
		AbstractMarkerAnnotationModel model= getAnnotationModel();
		
		if (model != null && resource != null) {
			try {
				
				IMarker[] markers= null;
				if (resource instanceof IFile)
					markers= resource.findMarkers(SDKLineBreakpoint.SDK_BREAKPOINT_MARKER, true, IResource.DEPTH_INFINITE);
				
				else {					
					IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
					markers= root.findMarkers(SDKLineBreakpoint.SDK_BREAKPOINT_MARKER, true, IResource.DEPTH_INFINITE);
				}
				
				if (markers != null) {
					IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
	
					existingMarkers=markers;
					SDKLineBreakpoint.breakpointCount=existingMarkers.length;				
				
								
				for (int i= 0; i < markers.length; i++) 
				{					
						IBreakpoint breakpoint= breakpointManager.getBreakpoint(markers[i]);
														
						if (breakpoint != null && breakpointManager.isRegistered(breakpoint)) {
							Position pos = model.getMarkerPosition(markers[i]); 
							if (includesRulerLine(pos, document))
								breakpoints.add(markers[i]);
						}
					}				
				}
			} catch (CoreException x) {				
			}catch(Exception e){}
		}
		return breakpoints;
	}

	/**
	 * @see Action#run()
	 */
	public void run() {
		
		if (markers.isEmpty()) {			
			addMarker();			
		} else {
			removeMarkers(markers);
		}
		
	}
	private boolean isLabel(String str) {
		StringTokenizer token = new StringTokenizer(str);
		if(token.countTokens()==2 && str.charAt(str.length()-1)==':') {
			return true;
		}else {
			return false;
		}
	}
	
	
	public boolean populate(){
		boolean startFlag = false;	
		try {
			File file1=(File)((FileEditorInput)getTextEditor().getEditorInput()).getFile();		
			String file3=file1.getLocation()+"";			
			//String file=((FileEditorInput)getTextEditor().getEditorInput()).getFile()+"";			
				
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file3)));			
									
			reader.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return startFlag;
	}
	
	protected void addMarker() {	
		//Breakpoint Marker Validation check only during adding breakpoint.
		
		try {
			int lineNumberr = 1;
			File file1=(File)((FileEditorInput)getTextEditor().getEditorInput()).getFile();		
			String file3=file1.getLocation()+"";			
			//String file=((FileEditorInput)getTextEditor().getEditorInput()).getFile()+"";
			BufferedReader reader;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file3)));				
			String line = "";	
			
			//Check for File content Validation.
				while((line = reader.readLine()) !=null) {
					if(DebugAction.debugMode==1 && line.contains("$FUNC")){
						//AL Debug.
						break;
					}else if(DebugAction.debugMode==2){
						//HL Debug.
						break;
					}else if(DebugAction.debugMode==3){
						//ML Debug.
						break;
					}					
					lineNumberr ++;
				}				
				
			} catch (IOException e1) {}
			catch(Exception e){}
			
			IDocument document= getDocument();
			int rulerLine = getVerticalRulerInfo().getLineOfLastMouseButtonActivity();			
			int lineNumber = rulerLine + 1;
			if (lineNumber < ++lineNumberr)				
				return;

			IRegion region = document.getLineInformation(lineNumber - 1);
			String lineText = document.get(region.getOffset(), region.getLength()).trim();
			

			//Check Line content validation for Adding Breakpoint.
			if(DebugAction.debugMode==1){
				//AL Debug Mode.
				if(lineText.length() < 1 || 
						lineText.startsWith("/") || lineText.startsWith(";") ||
						lineText.startsWith("*") || isLabel(lineText) || lineText.startsWith("$")
						|| lineText.endsWith("ret")) {
					ConsoleDisplayMgr.getDefault().println("BOARD DOES NOT SUPPORT THIS KIND OF BREAKPOINT.", 3);
					return;	
				}	
				
			}else if(DebugAction.debugMode==2){
				//HL Debug Mode.
				if(lineText.length() < 1 ||
						lineText.startsWith("#") ||
						lineText.trim().equalsIgnoreCase("{") ||
						lineText.startsWith("/") ||
						lineText.startsWith("*") || isLabel(lineText) ||
						lineText.endsWith("/")){
					ConsoleDisplayMgr.getDefault().println("BOARD DOES NOT SUPPORT THIS KIND OF BREAKPOINT.", 3);
					return;						
				}
				
			}else if (DebugAction.debugMode==3){
				//ML Debug Mode.
				if(lineText.length() < 1 || 
						lineText.startsWith("/") || lineText.startsWith(";") ||
						lineText.startsWith("*") || isLabel(lineText) || lineText.startsWith("$")
						|| lineText.endsWith("ret") ||
						lineText.startsWith("#") ||
						lineText.trim().equalsIgnoreCase("{")){
					ConsoleDisplayMgr.getDefault().println("BOARD DOES NOT SUPPORT THIS KIND OF BREAKPOINT.", 3);
					return;
				}
			}else{
				//Debug Mode isn't selected.
				ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
				return;						
			}			
			
			IEditorInput editorInput= getTextEditor().getEditorInput();				
			final IResource resource = (IResource)editorInput.getAdapter(IResource.class);
			SDKLineBreakpoint br = new SDKLineBreakpoint(resource, lineNumber);
			IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
			breakpointManager.addBreakpoint(br);
			
		} catch (BadLocationException e) {} catch (CoreException e) {}	
		catch(Exception e){}
	}	
		
	@SuppressWarnings("unchecked")
	protected void removeMarkers(List markers) {
		//Remove Breakpoint from the Editor + Target Board.
		
		IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
		try {
			Iterator it= markers.iterator();
			while (it.hasNext()) {
				IBreakpoint breakpoint= breakpointManager.getBreakpoint((IMarker) it.next());
				ILineBreakpoint lr=(ILineBreakpoint)breakpoint;
				
				//Remove breakpoint from the target board.
				switch(DebugAction.debugMode){
				
				case 1:
					//AL Mode.										
					short[] id=new EditorAddressInfo().idInfoAL(lr.getLineNumber());
					IdebugInterface.removeBreakpoint(id, true);
					
					breakpointManager.removeBreakpoint(breakpoint, true);
					BreakpointMap.removeMap(
							new EditorAddressInfo().AddressInformation(lr.getLineNumber()));
					break;
				case 2:
					//HL Mode.
					short[] bId = new HighlevelAddressInfo().idInfo(lr.getLineNumber());
					if(bId==null) return;
					
					IdebugInterface.removeBreakpointHL(bId, true,lr.getLineNumber());
					breakpointManager.removeBreakpoint(breakpoint, true);
					break;
				case 3:
					//ML Mode. [Remove Breakpoint]
					
					//Remove breakpoint from the target board.
					id=new EditorAddressInfo().idInfoML(lr.getLineNumber(),textEditor.getTitle());
					if(id==null) return;
					int[] pack = IdebugInterface.removeBreakpoint(id, true);
					if(pack==null) return;
					
					
					
					breakpointManager.removeBreakpoint(breakpoint, true);
					BreakpointMap.removeMap(
							new EditorAddressInfo().AddressInformation(lr.getLineNumber()));
					break;
				default:
					//Debug Mode isn't selected.
					ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
					return;					
					
				}			
												
				if(breakpoint.isEnabled()==true)
					SDKLineBreakpoint.breakpointCount--;			
			}
		} catch (CoreException e) {}
		catch (Exception e1){}
	}
	
}
