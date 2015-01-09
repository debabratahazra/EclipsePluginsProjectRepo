package design_pattern.creational;


interface Builder {
	
	public void BuildBasement();

	public void BuildWall();

	public void BuildRoof();

	public Product GetProduct();
}

class Director {
	
	public void Construct(Builder builder) {
		builder.BuildBasement();
		builder.BuildWall();
		builder.BuildRoof();
	}
}

class ConcreteBuilder implements Builder {
	
	private Product _product = new Product();

	@Override
	public void BuildBasement() {
		_product.Part1 = "Basement Constructed";
	}

	@Override
	public void BuildWall() {
		_product.Part2 = "Wall Constructed";
	}

	@Override
	public void BuildRoof() {
		_product.Part3 = "Roof Constructed";
	}

	@Override
	public Product GetProduct() {
		return _product;
	}
}

class Product {
	
	public String Part1 = "";
	public String Part2 = "";
	public String Part3 = "";
}

public class BuilderPattern {
	public static void run() {
		Director director = new Director();
		Builder builder = new ConcreteBuilder(); 
		director.Construct(builder);
		
		Product product = builder.GetProduct();
		System.out.println("Product Details : " + product.Part1 + " " + product.Part2 + " " + product.Part3);
	}
	
}