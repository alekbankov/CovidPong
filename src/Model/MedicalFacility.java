package Model;

import java.util.Random;

import javafx.scene.paint.Color;

public class MedicalFacility extends House {

	// Konstruktor Weise Krankenhäuser mit jeweiligen Parametern für
	// Spielfeldposition:
	public MedicalFacility(double w, double h, double x, double y) {
		super(w, h, x, y, Color.ANTIQUEWHITE);
	}

	// Methode um Patienten von Covid zu heilen und Ihn aus dem Krankenhaus zu
	// entlassen und als Circle auf Spielfeld zu spawnen
	public Person cureDisease() {
		if (getResidents().isEmpty()) {
			return null;
		}

		Random random = new Random();

		int remove = random.nextInt(getResidents().size());
		Person person = getResidents().remove(remove);
		person.setInfected(false);
		return person;
	}
}
