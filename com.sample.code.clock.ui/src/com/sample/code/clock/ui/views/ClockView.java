package com.sample.code.clock.ui.views;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ClockView extends ViewPart {
	public ClockView() {
	}

	public void createPartControl(Composite parent) {
		final Canvas clock = new Canvas(parent, SWT.NONE);
		clock.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				@SuppressWarnings("deprecation")
				int seconds = new Date().getSeconds();
				int arc = (15 - seconds) * 6 % 360;
				Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
				e.gc.setBackground(blue);
				e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);
			}
		});

		new Thread("TickTock") {
			public void run() {
				while (!clock.isDisposed()) {
					clock.getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (clock != null && !clock.isDisposed())
								clock.redraw();
						}
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}.start();
	}

	public void setFocus() {
	}
}