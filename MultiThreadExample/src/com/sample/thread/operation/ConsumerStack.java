package com.sample.thread.operation;

import com.sample.thread.stack.MyStack;

public class ConsumerStack extends Thread {

	private MyStack stack;

	public ConsumerStack(MyStack stack) {
		this.stack = stack;
	}

	@Override
	public void run() {
		while (true) {
			if (stack.hasElement()) {
				try {
					int val = stack.pop();
					System.out.println("Pop Element : " + val);
					synchronized (stack) {
						System.out.println("Notify from ConsumerStack");
						stack.notify();
					}
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {

				}
			} else {
				synchronized (stack) {
					try {
						System.out.println("Waiting from ComsumerStack");
						stack.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
