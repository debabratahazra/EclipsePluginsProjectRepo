package design_pattern.structural;

public class Bridge {

	public void run() {

		Shape s1 = new Rectangle(new RedColor());
		s1.colorIt();
		Shape s2 = new Circle(new BlueColor());
		s2.colorIt();
	}
}

interface MyColor {

	public void fillColor();
}

abstract class Shape {

	MyColor color;

	Shape(MyColor color) {
		this.color = color;
	}

	abstract public void colorIt();
}

class Rectangle extends Shape {

	Rectangle(MyColor color) {
		super(color);
	}

	public void colorIt() {
		System.out.print("Rectangle filled with ");
		color.fillColor();
	}
}

class Circle extends Shape {

	Circle(MyColor color) {
		super(color);
	}

	public void colorIt() {
		System.out.print("Circle filled with ");
		color.fillColor();
	}
}

class RedColor implements MyColor {

	public void fillColor() {
		System.out.println("red color");
	}
}

class BlueColor implements MyColor {

	public void fillColor() {
		System.out.println("blue color");
	}
}
