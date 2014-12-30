package com.tel.autosysframework.editor;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class AutosysSourceViwerConfiguration extends SourceViewerConfiguration {

	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getAnnotationHover(sourceViewer);
	}

	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		// TODO Auto-generated method stub
		return super.getAutoEditStrategies(sourceViewer, contentType);
	}
	

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getConfiguredContentTypes(sourceViewer);
	}

	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getConfiguredDocumentPartitioning(sourceViewer);
	}

	public int[] getConfiguredTextHoverStateMasks(ISourceViewer sourceViewer,
			String contentType) {
		// TODO Auto-generated method stub
		return super.getConfiguredTextHoverStateMasks(sourceViewer, contentType);
	}

	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getContentAssistant(sourceViewer);
	}

	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getContentFormatter(sourceViewer);
	}

	public String[] getDefaultPrefixes(ISourceViewer sourceViewer,
			String contentType) {
		// TODO Auto-generated method stub
		return super.getDefaultPrefixes(sourceViewer, contentType);
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		// TODO Auto-generated method stub
		return super.getDoubleClickStrategy(sourceViewer, contentType);
	}

	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getHyperlinkDetectors(sourceViewer);
	}

	public IHyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getHyperlinkPresenter(sourceViewer);
	}

	public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getHyperlinkStateMask(sourceViewer);
	}

	public String[] getIndentPrefixes(ISourceViewer sourceViewer,
			String contentType) {
		// TODO Auto-generated method stub
		return super.getIndentPrefixes(sourceViewer, contentType);
	}

	protected String[] getIndentPrefixesForTab(int tabWidth) {
		// TODO Auto-generated method stub
		return super.getIndentPrefixesForTab(tabWidth);
	}

	public IInformationControlCreator getInformationControlCreator(
			ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getInformationControlCreator(sourceViewer);
	}

	public IInformationPresenter getInformationPresenter(
			ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getInformationPresenter(sourceViewer);
	}

	public IAnnotationHover getOverviewRulerAnnotationHover(
			ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getOverviewRulerAnnotationHover(sourceViewer);
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getPresentationReconciler(sourceViewer);
	}

	public IQuickAssistAssistant getQuickAssistAssistant(
			ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getQuickAssistAssistant(sourceViewer);
	}

	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getReconciler(sourceViewer);
	}

	public int getTabWidth(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getTabWidth(sourceViewer);
	}

	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType, int stateMask) {
		// TODO Auto-generated method stub
		return super.getTextHover(sourceViewer, contentType, stateMask);
	}

	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType) {
		// TODO Auto-generated method stub
		return super.getTextHover(sourceViewer, contentType);
	}

	public IUndoManager getUndoManager(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return super.getUndoManager(sourceViewer);
	}

}
