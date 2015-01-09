package com.sample.code.clock.ui.views;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidget extends Canvas {

	public ClockWidget(Composite parent, int style) {
		super(parent, style);
		
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
			ClockWidget.this.paintControl(e);
			}
			});
		new Thread("TickTock") {
			public void run() {
				while (!ClockWidget.this.isDisposed()) {
					ClockWidget.this.getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (ClockWidget.this != null && !ClockWidget.this.isDisposed())
								ClockWidget.this.redraw();
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

	private void paintControl(PaintEvent e) {
		@SuppressWarnings("deprecation")
		int seconds = new Date().getSeconds();
		int arc = (15 - seconds) * 6 % 360;
		Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
		e.gc.setBackground(blue);
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);
	}
	
	@Override
	public Point computeSize(int w,int h,boolean changed) {
		int size;
		if(w == SWT.DEFAULT) {
		size = h;
		} else if (h == SWT.DEFAULT) {
		size = w;
		} else {
		size = Math.min(w,h);
		}
		if(size == SWT.DEFAULT)
		size = 50;
		return new Point(size,size);
		}
}
