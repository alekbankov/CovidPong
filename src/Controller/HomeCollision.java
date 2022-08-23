package Controller;

import Model.Person;

public class HomeCollision extends CollisionStrategy {
	@Override
	public void performCollision(Person person) {

		person.setDeltaY(person.getDeltaY() * -1); // kollision mit Home änder Y-Bewegung
		// TODO hier ist noch ein Fehler irgendwo bzw. deswegen funktioniert das
		// Strategy Pattern noch nicht
	}
}
