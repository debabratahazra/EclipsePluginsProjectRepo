package design_pattern.mainclass;

import design_pattern.creational.Factory;
import design_pattern.internal.Pet;

public class MainFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Factory petFactory = new Factory();

		// factory instantiates an object
		Pet pet = petFactory.getPet("bark");

		// you don't know which object factory created
		System.out.println(pet.speak());
	}
}
