package design_pattern.structural;

public class Decorator {

	public void run() {
		Room room = new CurtainDecorator(new ColorDecorator(new SimpleRoom()));
		System.out.println(room.showRoom());
	}
}

interface Room {
	public String showRoom();
}

class SimpleRoom implements Room {

	@Override
	public String showRoom() {
		return "Normal Room";
	}

}

abstract class RoomDecorator implements Room {

	protected Room specialRoom;

	public RoomDecorator(Room specialRoom) {
		this.specialRoom = specialRoom;
	}

	public String showRoom() {
		return specialRoom.showRoom();
	}
}

class ColorDecorator extends RoomDecorator {

	public ColorDecorator(Room specialRoom) {
		super(specialRoom);
	}

	public String showRoom() {
		return specialRoom.showRoom() + addColors();
	}

	private String addColors() {
		return " + Blue Color";
	}
}

class CurtainDecorator extends RoomDecorator {

	public CurtainDecorator(Room specialRoom) {
		super(specialRoom);
	}

	public String showRoom() {
		return specialRoom.showRoom() + addCurtains();
	}

	private String addCurtains() {
		return " + Red Curtains";
	}
}