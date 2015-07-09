package com.odcgroup.mdf.editor.ui.providers.decorators;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

import com.odcgroup.mdf.metamodel.MdfClass;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class AbstractClassDecorator implements ILightweightLabelDecorator,
        IPropertyChangeListener {

    private final Collection<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();
    private final IThemeManager themeManager;
    private Font italicFont;

    public AbstractClassDecorator() {
        themeManager = PlatformUI.getWorkbench().getThemeManager();
        themeManager.addPropertyChangeListener(this);

        Runnable runnable = new Runnable() {
        	public void run() {
                FontRegistry fonts = themeManager.getCurrentTheme().getFontRegistry();
                italicFont = fonts.getItalic(JFaceResources.DEFAULT_FONT);
        	}
        };
        Display.getDefault().syncExec(runnable);
    }

    /**
     * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
     *      org.eclipse.jface.viewers.IDecoration)
     */
    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof MdfClass) {
            MdfClass klass = (MdfClass) element;

            if (klass.isAbstract() && italicFont!=null) {
            	decoration.setFont(italicFont);
            }
        }
    }

    public void addListener(ILabelProviderListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void propertyChange(PropertyChangeEvent event) {
        // if the user is now using a new theme or if the user
        // has customized the value of the background color
        // update the control
        if (event.getProperty().equals(IThemeManager.CHANGE_CURRENT_THEME)
                || event.getProperty().equals(JFaceResources.DEFAULT_FONT)) {
            synchronized (listeners) {
                for (ILabelProviderListener listener : listeners) {
                    listener.labelProviderChanged(new LabelProviderChangedEvent(
                            this));
                }
            }
        }
        Runnable runnable = new Runnable() {
        	public void run() {
                FontRegistry fonts = themeManager.getCurrentTheme().getFontRegistry();
                italicFont = fonts.getItalic(JFaceResources.DEFAULT_FONT);
        	}
        };
        Display.getDefault().syncExec(runnable);
    }

}
