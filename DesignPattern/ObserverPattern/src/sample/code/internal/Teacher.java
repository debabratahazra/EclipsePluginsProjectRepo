package sample.code.internal;

import java.util.Observable;
import java.util.Observer;

public class Teacher implements Observer {

	@Override
	public void update(Observable arg0, Object arg) {
		System.out.println("Message changed of Teacher: " + arg);
	}

}
