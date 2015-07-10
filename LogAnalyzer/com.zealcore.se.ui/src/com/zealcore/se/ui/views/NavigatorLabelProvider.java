package com.zealcore.se.ui.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class NavigatorLabelProvider extends WorkbenchLabelProvider {

    @Override
    protected ImageDescriptor decorateImage(final ImageDescriptor input,
            final Object element) {
        if (element instanceof IAdaptable) {
            final IAdaptable adapt = (IAdaptable) element;

            final ILogSessionWrapper wrapper = (ILogSessionWrapper) adapt
                    .getAdapter(ILogSessionWrapper.class);
            if (wrapper != null) {

                final ImageDescriptor imageDesc = IconManager
                        .getImageDescriptor(IconManager.LOGFILE_SMALL_IMAGE_ID);

                return super.decorateImage(imageDesc, adapt);
            }
        }
        return super.decorateImage(input, element);
    }
}
