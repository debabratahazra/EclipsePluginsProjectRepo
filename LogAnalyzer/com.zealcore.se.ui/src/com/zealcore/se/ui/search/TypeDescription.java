package com.zealcore.se.ui.search;

import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.util.PropertyLayout;

class TypeDescription extends Composite {

    private static final String GET_PREFIX = "get";

    public TypeDescription(final Composite parent, final int style) {
        super(parent, style);
        setLayout(new PropertyLayout());

    }

    protected void describe(final Class<?> type) {
        for (final Control control : getChildren()) {
            control.dispose();
        }
        for (final Method method : type.getMethods()) {
            if (method.getName().startsWith(TypeDescription.GET_PREFIX)
                    && method.getParameterTypes().length == 0
                    && method.isAnnotationPresent(ZCProperty.class)) {

                describeName(method);
                describeReturn(method);
                describeDescription(method);

                new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
                new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
            }
        }

        pack();

    }

    private void describeDescription(final Method method) {
        Label label;
        Label value;
        final String description = method.getAnnotation(ZCProperty.class)
                .description();
        if (description != null && description.length() > 0) {
            label = new Label(this, SWT.NULL);
            label.setText("Description:");
            value = new Label(this, SWT.NULL);
            value.setText(description);
        }
    }

    private void describeName(final Method method) {
        final Label label = new Label(this, SWT.NULL);
        label.setText("Property:");
        final Label value = new Label(this, SWT.NULL);
        value.setText(method.getName().substring(
                TypeDescription.GET_PREFIX.length()));
    }

    private void describeReturn(final Method method) {

        final Label label = new Label(this, SWT.NULL);
        label.setText("Returns:");

        if (IObject.class.isAssignableFrom(method.getReturnType())) {
            final Link link = new Link(this, SWT.NONE);
            link.setText("<a>" + method.getReturnType().getSimpleName()
                    + "</a>");
            link.addListener(SWT.Selection, new Listener() {
                public void handleEvent(final Event event) {
                    describe(method.getReturnType());
                }
            });
        } else {
            Label value;
            value = new Label(this, SWT.NULL);
            value.setText(method.getReturnType().getSimpleName());
        }
    }
}
