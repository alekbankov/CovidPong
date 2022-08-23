package Controller;

import Model.*;

public class BasicCollision extends CollisionStrategy {
	@Override
	public void performCollision(Person person) {

		double x = person.getCenterX();

		double y = person.getCenterY();

	}

	public void performCollisionX(Person person) {
		person.setDeltaX(person.getDeltaX() * -1); // Abprallen an linker oder rechter Wand -> bewegung in X Richtung
													// änder sich
		// TODO hier ist noch ein Fehler irgendwo bzw. deswegen funktioniert das
		// Strategy Pattern noch nicht
	}

	public void performCollisionY(Person person) {
		person.setDeltaY(person.getDeltaY() * -1); // Abprallen an linker oder rechter Wand -> bewegung in Y Richtung
													// änder sich
		// TODO hier ist noch ein Fehler irgendwo bzw. deswegen funktioniert das
		// Strategy Pattern noch nicht
	}
}
