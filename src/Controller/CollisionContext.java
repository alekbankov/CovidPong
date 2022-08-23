package Controller;

import javafx.geometry.Bounds;
import Model.*;

import java.util.ArrayList;

public class CollisionContext {

	private CollisionPolicy collisionPolicy;

	// TODO größere Frage: Was passiert wenn in einem Frame mehrere Collisions
	// gleichzeitig stattfinden bzw. Kollisions unterhalb der
	// einzelnen Personen!!??
	// Evtll nicht 100% geeigent für Collisions weil einige Zusatzinformaationen für
	// Kollision gebraucht werden

	public CollisionContext() {
		collisionPolicy = new CollisionPolicy();
	}

	/**
	 * Codierte Rückgabe bezüglich des Kollisionstypen als Teil des Strategy
	 * Pattern: 0 = Basic Collision 1 = People Collision 2 = Home Collision 3 =
	 * MedicalFacilityCollision 4 = Keine Kollision
	 */
	// -- Kollisionen werden ausgeführt:
	// Als parameter habe ich noch medicalfacilities hinzugefügt
	void executeCollision(Person person, Bounds bounds, ArrayList<House> houses,
			ArrayList<MedicalFacility> medicalfacilities, Paddle player) {
		// -- Policy entscheidet welche durchgeführt wird:
		int choice = selectStrategy(person, bounds, houses, medicalfacilities, player);

		final boolean atRightBorder = person.getLayoutX() >= (bounds.getMaxX() - person.getRadius());
		final boolean atLeftBorder = person.getLayoutX() <= (bounds.getMinX() + person.getRadius());

		// -- Kollision mit Wand oder Paddle ändert entweder die X oder Y Richtung
		if (choice == 0) {
			if (atRightBorder || atLeftBorder) {
				BasicCollision basicCollision = new BasicCollision();
				basicCollision.performCollisionX(person);
			} else {
				BasicCollision basicCollision = new BasicCollision();
				basicCollision.performCollisionY(person);
			}
		}

		// -- People Kollision:
		else if (choice == 1) {
			PeopleCollision peopleCollision = new PeopleCollision();
			peopleCollision.performCollision(person);
		}

		// -- Kollision mit Home:
		else if (choice == 2) {
			HomeCollision homeCollision = new HomeCollision();
			homeCollision.performCollision(person);
		}

		// -- Kollision mit Medical Center:
		else if (choice == 3) {
			MedicalFacilityCollision medicalFacilityCollision = new MedicalFacilityCollision();
			medicalFacilityCollision.performCollision(person);
		}
	}

	// -- Collision wird mittels CollisionPolicy ermittelt:
	// TODO braucht man hier die Methode, Ergebnis wird nur weitergeleitet

	// Als parameter habe ich noch medicalfacilites hinzugefügt
	public int selectStrategy(Person person, Bounds bounds, ArrayList<House> houses,
			ArrayList<MedicalFacility> medicalfacilities, Paddle player) {
		return collisionPolicy.evaluateCollision(person, bounds, houses, medicalfacilities, player);
	}
}
