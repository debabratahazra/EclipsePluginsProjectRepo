package com.sample.thread.main;

import com.sample.thread.operation.ConsumerStack;
import com.sample.thread.operation.ProducerStack;
import com.sample.thread.stack.MyStack;

public class MainClass {
	
	public static void main(String[] args) {
		
		MyStack stack = new MyStack();
		ProducerStack producerStack = new ProducerStack(stack);
		ConsumerStack consumerStack = new ConsumerStack(stack);
		
		producerStack.start();
		consumerStack.start();
		
		try {
			producerStack.join();
			consumerStack.join();
		} catch (Exception e) {
		}
	}
}
