package design_pattern.creational;

class Bike implements Cloneable {

	private int gears;
	private String bikeType;
	private String model;

	public Bike() {
		bikeType = "Standard";
		model = "Leopard";
		gears = 4;
	}

	@Override
	public Bike clone() {
		return new Bike();
	}

	public void makeAdvanced() {
		bikeType = "Advanced";
		model = "Jaguar";
		gears = 6;
	}

	public String getModel() {
		return model;
	}

	public int getGears() {
		return gears;
	}

	public String getBikeType() {
		return bikeType;
	}
}

public class Prototype {

	public Bike makeJaguar(Bike basicBike) {
		basicBike.makeAdvanced();
		return basicBike;
	}

	public static void run() {
		Bike bike = new Bike();
		Bike basicBike = bike.clone();
		Prototype protptypeBike = new Prototype();
		Bike advancedBike = protptypeBike.makeJaguar(basicBike);
		System.out.println("Prototype Design Pattern: "
				+ advancedBike.getModel());
	}
}
