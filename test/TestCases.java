
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Controller.GameBoard;
import application.BreakOut;
import audio.AudioPlayer;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import Controller.CollisionPolicy;
import Controller.InfectionService;
import Controller.Paddle;
import Model.Home;
import Model.House;
import Model.MedicalFacility;
import Model.Person;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;



@ExtendWith(EasyMockExtension.class)
public class TestCases {
	private MedicalFacility med = new MedicalFacility(5.0, 5.0, 5.0, 5.0);

	// 10 useful tests, Funktionalitaet

	@TestSubject
	private Home testSubject = new Home(5.0, 5.0, 5.0, 5.0);

	@Mock
	private InfectionService mock;

	// Testet zukünftiges Szenario: Im GameBoard wird der InfectionService
	// implementiert, welcher die Methode infectionProbability() erfasst und
	// aufgrund von Inhalten des GameBoards wie Anzahl toatal Infiziert und weiteren
	// Variablen verändert wird. Aufgrund dieser fehlenden Implementation wird diese
	// Implementation durch einen Mock bereitgestellt. Hierbei wird nun getest, ob
	// bei einem 100% Ansteckungsrisiko beide Personen nach einem Aufruf einer
	// möglichen Infektion eines Hauses infiziert werden
	@Test // Test 1
	public void testInfectionInHouseSuccessful() {

		List<Person> persons = new LinkedList<>();
		Person person1 = new Person(5.0);
		Person person2 = new Person(5.0);
		persons.add(person1);
		persons.add(person2);

		person2.setInfected(true);

		testSubject.setResidents(persons);

		expect(mock.infectionProbability()).andReturn(1.0);

		replay(mock);

		testSubject.infectionInHouse(mock);

		assertTrue(person1.isInfected());
		assertTrue(person2.isInfected());
	}

	// Ähnlich zu dem vorherigen Test: Allerdings wird hier der EdgeCase getestet,
	// ob selbst bei 100% Ansteckungsrisiko keine Infektion zwischen zwei Personen
	// auftritt, sofern beide selbst nicht infiziert sind. (Eine Ansteckung würde
	// logisch keinen Sinn ergeben...)
	@Test // test 2
	public void testInfectionInHouseUnsuccessful() {

		List<Person> persons = new LinkedList<>();
		Person person1 = new Person(5.0);
		Person person2 = new Person(5.0);
		persons.add(person1);
		persons.add(person2);

		testSubject.setResidents(persons);

		expect(mock.infectionProbability()).andReturn(1.0);

		replay(mock);

		testSubject.infectionInHouse(mock);

		assertFalse(person1.isInfected());
		assertFalse(person2.isInfected());
	}

	// Testet, ob 5 Personen erfolgreich aus der MedicalFacility mittel
	// cureDisease() entfernt wurden. Bei leerer MedicalFacility wird null
	// zurückgegeben
	@Test // test 3
	public void testMedicalFacilityWith5() {
		List<Person> persons = new LinkedList<>();
		Person person1 = new Person(5.0);
		Person person2 = new Person(5.0);
		Person person3 = new Person(5.0);
		Person person4 = new Person(5.0);
		Person person5 = new Person(5.0);
		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
		persons.add(person4);
		persons.add(person5);

		med.setResidents(persons);
		assertSame(persons, med.getResidents());

		assertNotNull(med.cureDisease());
		assertNotNull(med.cureDisease());
		assertNotNull(med.cureDisease());
		assertNotNull(med.cureDisease());
		assertNotNull(med.cureDisease());
		assertNull(med.cureDisease());
	}

	// Testing ability to heal patients in medicalFacility
	@Test // test 4
	public void testHealingAbility() {
		List<Person> persons = new LinkedList<>();
		Person person1 = new Person(5.0);
		Person person2 = new Person(5.0);
		person1.setInfected(false);
		person1.setInfected(true);
		persons.add(person1);
		persons.add(person2);

		med.setResidents(persons);

		assertEquals(med.cureDisease().isInfected(), false);
		assertEquals(med.cureDisease().isInfected(), false);
	}

	// TODO @Pascal fix so that person is in Home
	@Test // test 5
	public void testCollisionPolicy() {
		CollisionPolicy cp = new CollisionPolicy();
		ArrayList<House> house = new ArrayList<>();
		house.add(new Home(10, 10, 10, 10));
		ArrayList<MedicalFacility> med = new ArrayList<>();
		med.add(new MedicalFacility(5, 5, 5, 5));
		Paddle p = new Paddle(0, 0, 0, 0, Color.WHITE);
		Bounds bound = new BoundingBox(0, 0, 0, 0);

		Person person = new Person(5.0);
		person.setDeltaX(10);
		person.setDeltaY(10);
		System.out.println(cp.evaluateCollision(person, bound, house, med, p));
	}

	// TODO mögliche zusätzliche Tests: Ball und Wand Kollision, Ball und Paddle
	// Kollision, Ball und Ball mit Infektion / ohne Infektion, Paddle Steuerung,
	// etc.

	// tests if a new created home is added to the map of all Houses. This is
	// essential for the functionality of the game
	@Test //test 6
	public void testIfHomeIsAdded() {

		Home home1 = new Home(5.0, 5.0, 5.0, 5.0);

		assertTrue(Home.allHomes.containsKey((int) (home1.getLayoutX() + home1.getLayoutY())));
	}

	// Tests future scenarion of entering a house, when a person collides with the
	// house and the house is not completly filled yet.
	@Test // test 7
	public void testEnterHouse() {
		Person testPerson = new Person(5.0);
		Home homeTest = new Home(5.0, 5.0, 5.0, 5.0);

		homeTest.setResidents(new LinkedList<Person>());
		homeTest.captureOne(testPerson);

		assertFalse(testPerson.isVisible());
		assertEquals(homeTest.getResidents().get(0), testPerson);

	}
	
	// Tests scenario when house is already full and a person collides with the house, 
	// if it just bounces off the house and stays visible
	@Test // test 8
	public void testEnterFullHouse() {
		List<Person> residents = new LinkedList<>();
		Person testPerson = new Person(5.0);
		Person testPerson2 = new Person(5.0);	
		for(int i = 0; i < 5 ; i++) {
			residents.add(i, testPerson);
		}

		testSubject.setResidents(residents);
		testSubject.captureOne(testPerson2);

		assertTrue(testPerson2.isVisible());
		assertFalse(testPerson2.isInhabitant());
	}

}
