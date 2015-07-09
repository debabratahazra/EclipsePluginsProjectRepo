package sample.code.main;

import java.util.Observable;

import sample.code.internal.Student;
import sample.code.internal.Teacher;

public class ObserverPatternExample extends Observable {
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		setChanged();
		notifyObservers(message);
	}

	public static void main(String[] args) {
		ObserverPatternExample observe = new ObserverPatternExample();
		
		Student student = new Student();
		Teacher teacher = new Teacher();
		
		observe.addObserver(student);
		observe.addObserver(teacher);
		
		observe.setMessage("New message came !!!");

	}

}
