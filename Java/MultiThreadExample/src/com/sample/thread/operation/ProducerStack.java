package com.sample.thread.operation;

import com.sample.thread.stack.MyStack;

public class ProducerStack extends Thread {

	private MyStack stack;

	public ProducerStack(MyStack stack) {
		this.stack = stack;
	}

	@Override
	public void run() {
		while (true) {
			if (!stack.isStackFull()) {
				try {
					int val = (int) (Math.random() * 10);
					stack.push(val);
					System.out.println("Push Element is : " + val);
					synchronized (stack) {
						System.out.println("Notify from ProducerStack...");
						stack.notify();
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {

				}
			} else {
				synchronized (stack) {
					try {
						System.out.println("Waiting from ProducerStack");
						stack.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}
}
