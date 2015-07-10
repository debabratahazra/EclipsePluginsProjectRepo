package com.sample.thread.stack;

public class MyStack {

	private int index = 0;
	private final int STACK_SIZE = 10;

	private int[] data;

	public MyStack() {
		data = new int[STACK_SIZE];
	}

	public synchronized void push(int value) throws Exception {
		if (getStackSize() >= STACK_SIZE) {
			throw new Exception("Stack Overflow");
		} else {
			data[index] = value;
			index++;
		}
	}

	public synchronized int pop() throws Exception {
		if (getStackSize() < 0) {
			throw new Exception("Stack is Empty");
		} else {
			index--;
			return data[index];
		}
	}

	@Override
	public String toString() {
		String getAllValue = new String();
		if (getStackSize() < STACK_SIZE) {
			for (int value : data) {
				getAllValue += String.valueOf(value);
			}
		}
		return getAllValue;
	}

	public synchronized boolean hasElement() {
		if (getStackSize() > 0) {
			return true;
		}
		return false;

	}

	public synchronized boolean isStackFull() {
		if (getStackSize() >= STACK_SIZE) {
			return true;
		}
		return false;
	}

	private int getStackSize() {
		return index;
	}
}
