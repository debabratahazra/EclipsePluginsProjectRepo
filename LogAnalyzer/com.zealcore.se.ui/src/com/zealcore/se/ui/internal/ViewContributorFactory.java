package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;

import com.zealcore.se.core.AbstractExtensionVisitor;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.editors.ILogBrowserContribution;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.editors.IRuler;

public final class ViewContributorFactory {

    private ViewContributorFactory() {

    }

    /**
     * Creates a new lsit of IViewSetViewContribution for the given view id.
     * 
     * @param viewId
     *                the id
     * 
     * @return a list of contributions
     */
    public static List<ILogBrowserContribution> createContributors(
            final String viewId) {

        final String extensionPoint = "viewSetViewContribition";

        final List<ILogBrowserContribution> contibutors = new ArrayList<ILogBrowserContribution>();
        final AbstractExtensionVisitor visitor = new AbstractExtensionVisitor(
                SeUiPlugin.PLUGIN_ID, extensionPoint) {

            @Override
            protected void visit(final IConfigurationElement element)
                    throws CoreException {
                String viewerId = element.getAttribute("viewerId");
                if (viewerId != null) {
                    if (viewerId.equals(viewId)) {
                        Object object = element
                                .createExecutableExtension("class");
                        ILogBrowserContribution adaptee = (ILogBrowserContribution) object;
                        contibutors.add(new ContributionAdapter(adaptee));
                    }
                }
            }
        };

        try {
            visitor.run();
        } catch (final CoreException e) {
            SeUiPlugin.logError(e);
        }
        return contibutors;
    }

    /**
     * This adapter catches RuntimeExceptions and re-throws them, but marking
     * this contribution as having failures and will stop functioning.
     * 
     * TODO Use the id and plug-in to create Status objects on exceptions, that
     * way a more informative failure message can be used.
     * 
     * @author stch
     * 
     */
    private static class ContributionAdapter implements ILogBrowserContribution {

        private ILogBrowserContribution adaptee;

        private boolean failure;

        public ContributionAdapter(final ILogBrowserContribution adaptee) {
            this.adaptee = adaptee;
        }

        public void decorateFigures(final Graphics graphics,
                final IFigure figure) {
            if (failure) {
                return;
            }
            try {
                adaptee.decorateFigures(graphics, figure);
            } catch (RuntimeException e) {
                failure = true;
                throw e;
            }
        }

        public void dispose() {
            adaptee.dispose();
        }

        @SuppressWarnings("unchecked")
        public List<IFigure> getElements(final IRuler ruler,
                final IFigure figure) {
            if (failure) {
                return Collections.EMPTY_LIST;
            }
            try {
                return adaptee.getElements(ruler, figure);
            } catch (RuntimeException e) {
                failure = true;
                throw e;
            }
        }

        public void init(final ILogsetBrowser view) {
            try {
                adaptee.init(view);
            } catch (RuntimeException e) {
                failure = true;
                SeUiPlugin.reportUnhandledRuntimeException(adaptee.getClass(),
                        e, true);
            }
        }

    }
}
