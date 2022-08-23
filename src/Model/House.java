package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class House extends Rectangle {

	private List<Person> residents = new ArrayList<>();

	public House(double w, double h, double x, double y, Color color) {
		super(w, h, color);
		setLayoutX(x);
		setLayoutY(y);

		Random random = new Random();
		int max = 5;
		int min = 0;
		int temp = random.nextInt(max + min) + min;

		for (int i = 0; i < temp; i++) {
			residents.add(new Person(10));
		}
	}

	public List<Person> getResidents() {
		return residents;
	}

	public void setResidents(List<Person> residents) {
		this.residents = residents;
	}

	public boolean isFull() {

		if (residents.size() == 5) {

			return true;
		}
		return false;
	}
}
