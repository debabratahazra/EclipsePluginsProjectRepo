package design_pattern.creational;

import design_pattern.internal.Animal;
import design_pattern.internal.SpeciesFactory;

public class AbstractFactory {

	public SpeciesFactory getSpeciesFactory(String type) {
		if ("mammal".equals(type)) {
			return new MammalFactory();
		} else {
			return new ReptileFactory();
		}
	}

}

class MammalFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) {
		if ("bat".equals(type)) {
			return new Bat();
		} else {
			return new Cat();
		}
	}

}

class ReptileFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) {
		if ("snake".equals(type)) {
			return new Snake();
		} else {
			return new Tyrannosaurus();
		}
	}

}

class Cat extends Animal {

	@Override
	public String makeSound() {
		return "Meow";
	}

}

class Bat extends Animal {

	@Override
	public String makeSound() {
		return "Cheee";
	}

}

class Snake extends Animal {

	@Override
	public String makeSound() {
		return "Hiss";
	}

}

class Tyrannosaurus extends Animal {

	@Override
	public String makeSound() {
		return "Roar";
	}

}
