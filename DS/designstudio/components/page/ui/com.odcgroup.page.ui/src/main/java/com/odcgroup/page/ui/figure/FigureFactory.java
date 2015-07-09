package com.odcgroup.page.ui.figure;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.xml.sax.SAXException;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.page.util.ClassUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.richtext.RichTextUtils;

/**
 * The factory for creating custom figures.
 * 
 * @author atr
 */
public class FigureFactory {
	
	/** Flag indicating whether we are in design mode. */
	private boolean isDesignMode;
	
	/** The project. */
	private IProject project;
	
	/** The decorator for displaying validation errors. */
	private WidgetFigureDecorator figureDecorator;
	
	private ITranslationManager manager; 
	private Locale defaultLocale;
	
	/**
	 * Creates a new FigureFactory.
	 * 
	 * @param isDesignMode Flag indicating whether we are in design mode
	 * @param project The project
	 */
	public FigureFactory(boolean isDesignMode, IProject project) {	
		
		this.isDesignMode = isDesignMode;
		this.project = project;
		this.figureDecorator = new ValidationFigureDecorator();
		
		manager = TranslationCore.getTranslationManager(project);
		defaultLocale = manager.getPreferences().getDefaultLocale();
		
	}
	
	/**
	 * Removes any existing rich-text decorators in the translation.
	 * @param richText the decorated translation
	 * @return the string without the decorators.
	 */
	private String removeRichTextDecorators(String richText) {
		String result = richText;
		try {
			result = RichTextUtils.removeRichTextDecorator(richText);
		} catch (Exception ex) {
			// ignore
			String message = "Cannot remove rich text decorators: ";
			Logger.error(message, ex);
		}
		return result;
	}
	
	/**
	 * Creates the child figures for the given Widget and adds them to the figure.
	 * 
	 * @param widget The parent Widget
	 * @param figure The figure to add the child Figures to
	 */
	private void createChildFigures(Widget widget, IWidgetFigure figure) {
		List<Widget> children = widget.getContents(); 
		for (int index = 0; index < children.size() ; index++) {
			Widget child = children.get(index);
			IWidgetFigure awf = create(child);
			if (awf != null) {
				
				ITranslation translation = manager.getTranslation(child);
				if (translation != null) {
					try {
						String name = translation.getText(ITranslationKind.NAME, defaultLocale); 
						if (child.getType().isRichtext()) {
							name = removeRichTextDecorators(name);
						}
						awf.setText(name);
					} catch (TranslationException e) {
						// ignore
					}
				}
				
				figure.add(awf,index);
				// Call the same method recursively to create the complete tree
				createChildFigures(child, awf);
			}
		}
			
	}
	
	/**
	 * Gets the figure decorator.
	 * 
	 * @return the figureDecorator
	 */
	private WidgetFigureDecorator getFigureDecorator() {
		return figureDecorator;
	}
	
	/**
	 * Creates the figure for the Widget.
	 * 
	 * @param widget
	 *            The Widget to create the figure for
	 * @return IFigure The Figure for the Widget, or null if the widget has no figure.
	 */
	public IWidgetFigure create(Widget widget) {	
		UIModel uimodel = UIModelRegistry.getUIModel();
		RendererInfo ri = uimodel.getRenderers().findRendererInfo(widget.getType());
		if (ri == null) {
			// some widget may have no representation.
			return null;
		}

		FigureContext fc = new FigureContext(
				project, 
				isDesignMode, 
				createFigureConstants(ri));
		
		String className = ri.getFigure();
		IWidgetFigure fig = (IWidgetFigure) ClassUtils.newInstance(getClass().getClassLoader(), className, new Object[]{widget, fc});
		if (fig instanceof IncludeFigure) {
			//fig.setOpaque(true);
			fig.setBackgroundColor(ColorConstants.blue);
		} else {
			//fig.setOpaque(true);
			//fig.setBackgroundColor(ColorConstants.white);
		}

		if (fig instanceof IWidgetFigure) {
			IWidgetFigure wf = (IWidgetFigure) fig;
			wf.getDecorators().add(getFigureDecorator());
		}

		return fig;
	}
	
	/**
	 * Creates the figure for the root Widget and all its contained Widgets.
	 * 
	 * @param rootWidget
	 *            The root Widget to create the figure for
	 * @return IFigure The Figure for the Widget
	 */
	public IFigure createTree(Widget rootWidget) {
		IWidgetFigure rootFigure = create(rootWidget);
		if (rootFigure == null) {
			String message = "No RendererInfo defined for this Widget: " + rootWidget.getTypeName();
			Logger.error(message);
			throw new PageException(message);
		}

		ITranslation translation = manager.getTranslation(rootWidget);
		if (translation != null) {
			try {
				String name = translation.getText(ITranslationKind.NAME, defaultLocale); 
				if (rootWidget.getType().isRichtext()) {
					name = removeRichTextDecorators(name);
				}
				rootFigure.setText(name);
			} catch (TranslationException e) {
				// ignore
			}
		}
		
		createChildFigures(rootWidget, rootFigure);
		return rootFigure;
	}
	
	/**
	 * Creates the figure constants.
	 * 
	 * @param ri The RendererInfo
	 * @return FigureConstants The FigureConstants
	 */
	private FigureConstants createFigureConstants(RendererInfo ri) {
		if (isDesignMode) {
			return new DesignFigureConstants(ri);
		} else {
			return new PreviewFigureConstants(ri);
		}
	}
}