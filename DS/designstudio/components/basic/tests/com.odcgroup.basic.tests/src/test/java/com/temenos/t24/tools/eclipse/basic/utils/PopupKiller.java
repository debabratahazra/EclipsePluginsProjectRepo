package com.temenos.t24.tools.eclipse.basic.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang.ObjectUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yandenmatten
 */
public class PopupKiller extends Thread {

	private static Logger logger = LoggerFactory.getLogger(PopupKiller.class);

	private static Class<?>[] DIALOG_TYPE_TO_KILL = {MessageDialog.class, ErrorDialog.class};

	private static boolean active = true;

	public PopupKiller() {
		setName("PopupKiller");
	}

	public void run() {
		PlatformUI.getWorkbench().addWorkbenchListener(new IWorkbenchListener() {
			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				return true;
			}

			@Override
			public void postShutdown(IWorkbench workbench) {
				PopupKiller.active = false;
			}

		});

		while (PopupKiller.active) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (Display.getDefault() != null) {
						for (Shell s: Display.getDefault().getShells()) {
							Object data = s.getData();
							if (data instanceof Dialog) {
								Dialog dialog = (Dialog)data;
								if (isToBeCloseAutomatically(dialog)) {
									logger.warn("Closing the popup " + s.getText() + "...");
									String message = getPrivateMessageIfAny(dialog);
									if (message != null) {
										logger.warn("The message was: " + message);
									}
									s.close();
								}
							}
						}
					}
				}

				private boolean isToBeCloseAutomatically(Dialog dialog) {
					for (Class<?> c : DIALOG_TYPE_TO_KILL) {
						if (dialog.getClass().isAssignableFrom(c));
							return true;
					}
					return false;
				};
			});

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.warn("PopupKiller thread interrupted");
				return;
			}
		}
	}

	private static String getPrivateMessageIfAny(Object dialogInstance) {
		return getPrivateMessageIfAny(dialogInstance.getClass(), dialogInstance);
	}

	private static String getPrivateMessageIfAny(Class<? extends Object> dialogClass, Object dialogInstance) {

		if (dialogClass == Object.class)
			return null;

		try {
			for (Field field: dialogClass.getDeclaredFields()) {
				if ("message".equals(field.getName())) {
					field.setAccessible(true);
					return ObjectUtils.toString(field.get(dialogInstance));
				}
			}
			return getPrivateMessageIfAny(dialogClass.getSuperclass(), dialogInstance);
		} catch (Exception e) {
			return null;
		}
	}
}
