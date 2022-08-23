package Controller;

import javafx.geometry.Bounds;
import Model.*;

import java.util.ArrayList;

public class CollisionPolicy {

	public CollisionPolicy() {
	}

	/**
	 * Gibt codierte Rückgabe bezüglich des Kollisionstypen als Teil des Strategy
	 * Pattern: 0 = Basic Collision 1 = People Collision 2 = Home Collision 3 =
	 * MedicalFacilityCollision 4 = Keine Kollision
	 */

	// Als Parameter habe ich noch medicalfacilities hinzugefügt.
	public int evaluateCollision(Person person, Bounds bounds, ArrayList<House> houses,
			ArrayList<MedicalFacility> medicalfacilities, Paddle player) {

		final boolean atRightBorder = person.getLayoutX() >= (bounds.getMaxX() - person.getRadius());
		final boolean atLeftBorder = person.getLayoutX() <= (bounds.getMinX() + person.getRadius());
		final boolean atTopBorder = person.getLayoutY() <= (bounds.getMinY() + person.getRadius());
		final boolean atBottomBorder = person.getLayoutY() >= (bounds.getMaxY() - person.getRadius());

		// Basic Collision
		if (atRightBorder || atLeftBorder || atBottomBorder || atTopBorder) {
			return 0;
		}

		// -- Kollision mit paddle:
		if (person.getBoundsInParent().intersects(player.getBoundsInParent())) {
			return 0;
		}

		// TODO Collision unterhalb der spieler

		// -- Kollision mit Häusern:
		// TODO unterscheidung nach Houses und medical Centers

		// Wird überprüft, ob es für jedes Haus in ArrayList eine Kollision mit Paddle
		// gibt, wenn ja return 2
		for (House house : houses) {
			if (house.getBoundsInParent().intersects(player.getBoundsInParent())) {
				return 2;
			}
		}

		// Wird überprüft, ob es für jedes MedicalFacility in ArrayList eine Kollision
		// mit Paddle gibt, wenn ja return 3
		for (MedicalFacility medicalfacility : medicalfacilities) {
			if (medicalfacility.getBoundsInParent().intersects(player.getBoundsInParent())) {
				return 3;
			}
		}

		// Ich habe hier auskommentiert
		/*
		 * for(int i = 0; i < houses.size(); i++){
		 * if(person.getBoundsInParent().intersects(player.getBoundsInParent())) {
		 * return 2; } }
		 */

		// Keine Kollision:
		return 4;
	}

}
