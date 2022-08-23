package Model;

import javafx.scene.shape.Circle;

public class Person extends Circle {

	private double deltaX = 0; // Bewegung in X Richtung
	private double deltaY = 0; // bewegung in Y Richtung
	boolean infected = false;
	private int healingTime;
	private boolean inhabitant = false;

	// -- Konstruktor:
	public Person(double radius) {
		super(radius);
	}

	// -- Methoden:

	// -- Getter und Setter:
	public double getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}

	public boolean isInfected() {
		return infected;
	}

	public void setInfected(boolean infected) {
		this.infected = infected;
	}

	// Sets the healingTime of a person
	public void setHealingTime(int healingTime) {
		this.healingTime = healingTime;
	}

	// Getter for HealingTime
	public int getHealingTime() {
		return healingTime;
	}

	public boolean isInhabitant() {
		return inhabitant;
	}

	public void setInhabitant(boolean inhabitant) {
		this.inhabitant = inhabitant;
	}
}
