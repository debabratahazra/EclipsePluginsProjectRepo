/**
 * 
 */
package com.zealcore.se.ui.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IStateTransition;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.ITimedTransition;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.ui.IconManager;

// TODO Move this to more general package
public final class NamedItemLabelProvider extends LabelProvider {

    private static final String SUFFIX = ": ";

    private static final String ABSTRACT_PREFIX = "Abstract";

    private final Map<ImageDescriptor, Image> images = new HashMap<ImageDescriptor, Image>();

    @Override
    public String getText(final Object element) {

        if (element instanceof IWorkbenchAdapter) {
            final IWorkbenchAdapter adapter = (IWorkbenchAdapter) element;
            return adapter.getLabel(element);
        }

        if (element instanceof SEProperty) {

            final SEProperty prop = (SEProperty) element;

            if (prop.getData() instanceof IObject) {
                final IObject named = (IObject) prop.getData();
                return getDisplayName(prop) + NamedItemLabelProvider.SUFFIX
                        + named.toString();
            }

            if (prop.getData() instanceof Collection) {
                return getDisplayName(prop);

            }

            return getDisplayName(prop) + NamedItemLabelProvider.SUFFIX
                    + prop.getData();

        }
        return super.getText(element);
    }

    private String getDisplayName(final SEProperty prop) {
        final String name = prop.getName();
        if (name.startsWith(NamedItemLabelProvider.ABSTRACT_PREFIX)) {
            return name
                    .replaceFirst(NamedItemLabelProvider.ABSTRACT_PREFIX, "");
        }
        return name;
    }

    @Override
    public Image getImage(final Object element) {

        ImageDescriptor desc = IconManager
                .getImageDescriptor(IconManager.BLOB_IMAGE);

        if (element instanceof IWorkbenchAdapter) {
            IWorkbenchAdapter adapter = (IWorkbenchAdapter) element;
            desc = adapter.getImageDescriptor(element);
        } else {

            Object item = element;
            if (item instanceof SEProperty) {
                final SEProperty property = (SEProperty) item;
                item = property.getData();
            }
            if (item instanceof ILogEvent) {
                desc = IconManager
                        .getImageDescriptor(IconManager.EVENT_SMALL_IMG);

            } else if (item instanceof ITaskDuration) {
                desc = IconManager
                        .getImageDescriptor(IconManager.TASK_SMALL_IMG);
            } else if (item instanceof IStateTransition
                    || item instanceof ITimedTransition) {
                desc = IconManager
                        .getImageDescriptor(IconManager.UPRIGHT_ARROW);
            }
        }

        Image image = this.images.get(desc);
        if (image != null) {
            return image;
        }

        image = desc.createImage();
        this.images.put(desc, image);
        return image;
    }

    @Override
    public void dispose() {

        for (final Image i : this.images.values()) {
            i.dispose();
        }
        this.images.clear();
    }
}
