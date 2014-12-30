package com.cdt.keil.debug.ui.breakpoints;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

public class SDKEnableBreakpointActionDelegate extends AbstractRulerActionDelegate {
		
	protected IAction createAction(ITextEditor editor,
			IVerticalRulerInfo rulerInfo) {
		return new SDKEnableBreakpointAction(editor, rulerInfo);
	}
}
