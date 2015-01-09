package design_pattern.creational;

public class Singleton {

	private static Singleton instance;

	private Singleton() {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new CloneNotSupportedException(
				"Not allowed clone method in Singleton class");
	}

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				//Double Check locking in Singleton
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}
