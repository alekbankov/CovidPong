package Model;

import java.util.Hashtable;
import java.util.Random;

import Controller.BasicCollision;
import Controller.InfectionService;
import javafx.scene.paint.Color;

public class Home extends House {

	public static Hashtable<Integer, Home> allHomes = new Hashtable<Integer, Home>();
	private static final int PROBABLE_INFECTION_RATE = 80;

	// Konstruktor: Alle Häuser Orange und werden mit jeweiligen Daten auf Spielfeld
	// gesetzt
	public Home(double w, double h, double x, double y) {
		super(w, h, x, y, Color.ORANGE);
		allHomes.put((int) (x + y), this);
	}

	// -- Bei Kollision mit Person wird Person den residents hinzugefügt, Circle vom
	// Spielfeld entfernt und niemand entlassen:
	public void captureOne(Person person) {

		if (this.isFull()) {

			BasicCollision collisionFromHouse = new BasicCollision();
			collisionFromHouse.performCollision(person);

		} else {

			person.setVisible(false);
			this.getResidents().add(person);
		}

	}

	public void infectionInHouse(InfectionService gb) {
		if (getResidents().size() < 2) {
			return;
		}

		Person p1 = getResidents().get(0);
		Person p2 = getResidents().get(1);

		Random random = new Random();

		int deviation = random.nextInt(20) - 10;

		if (gb.infectionProbability() * 100 > PROBABLE_INFECTION_RATE + deviation) {
			if (p1.isInfected()) {
				p2.setInfected(true);
			} else if (p2.isInfected()) {
				p1.setInfected(true);
			}
		}
	}

}