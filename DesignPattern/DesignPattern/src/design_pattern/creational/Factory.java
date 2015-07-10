package design_pattern.creational;

import design_pattern.internal.Pet;

class Dog implements Pet {
	@Override
	public String speak() {
		return "Bark bark...";
	}
}

class Duck implements Pet {
	@Override
	public String speak() {
		return "Quack quack...";
	}
}

public class Factory {
	
	public Pet getPet(String petType) {
		Pet pet = null;

		// Based on logic factory instantiates an object
		if ("bark".equals(petType))
			pet = new Dog();
		else if ("quack".equals(petType))
			pet = new Duck();
		return pet;
	}
}
