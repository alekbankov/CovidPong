package Controller;

import audio.AudioPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import Model.*;
import View.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import audio.AudioPlayerInterface;

public class GameBoard extends Application implements InfectionService {

	private static final Pane root = new Pane(); // In Root sind alle Elemente gespeichert, die auf Fenster geadded
													// werden
	private static final Paddle player = new Paddle(250, 35, 550, 800, Color.GRAY); // Paddle unten dran
	private static final Person person = new Person(10); // TODO Später noch eine Liste von Playern, die gerade auf dem
															// Spielfeld sind (Bälle)
	private static final ArrayList<House> houses = new ArrayList<>(); // Alle Blöcke auf dem Spielfeld

	private AudioPlayerInterface audioPlayer = new AudioPlayer();// responsible for handling background music

	// -- Im COntent werden alle Elemente auf dem Spielfeld initialisiert: (Spieler,
	// Bricks, Ball)
	public static Parent content(Stage primaryStage) {

		// Spieler wird hinzugefügt:
		root.getChildren().add(player); // Spielplyer von unten wird geadded

		// Ball wird hinzugefügt:
		person.setFill(Color.AQUAMARINE);
		person.relocate(600, 250);
		root.getChildren().add(person);

		// Häuser / medicalFacilities werden hinzugefügt
		createHomes(primaryStage);

		// Ball wird bewegt:
		moveBall(primaryStage);

		// Root (alle gespeicherten Elemente) wird zurückgegeben:
		return root;
	}

	// -- Ball wird bewegt und Kollisionen werden geprüft:
	private static void moveBall(Stage primaryStage) {

		CollisionContext collisionContext = new CollisionContext();

		Timeline BallLoop = new Timeline(new KeyFrame(Duration.millis(15), new EventHandler<ActionEvent>() {

			Random random = new Random();
			double min = 0;
			double max = 10;

			double deltaX = min + (max - min) * random.nextDouble();
			double deltaY = min + (max - min) * random.nextDouble();

			@Override
			public void handle(ActionEvent actionEvent) {
				person.setLayoutX(person.getLayoutX() + deltaX);
				person.setLayoutY(person.getLayoutY() + deltaY);

				// TODO Strategy Pattern ?

				Bounds bounds = root.getBoundsInLocal();
				final boolean atRightBorder = person.getLayoutX() >= (bounds.getMaxX() - person.getRadius());
				final boolean atLeftBorder = person.getLayoutX() <= (bounds.getMinX() + person.getRadius());
				final boolean atTopBorder = person.getLayoutY() <= (bounds.getMinY() + person.getRadius());
				final boolean atBottomBorder = person.getLayoutY() >= (bounds.getMaxY() - person.getRadius());

				// TODO Collision an Seite!!! X Wert ändern

				// -- Ball prallt an den Wänden ab
				// ----------------------------------------------------------------------
				if (atRightBorder || atLeftBorder) {
					deltaX *= -1; // Richtung des Balles in X-Richtung wird umgedreht
				}
				if (atTopBorder || atBottomBorder) {
					deltaY *= -1; // Richtung des Balles in X-Richtung wird umgedreht
				}

				// -- Ball prallt am Spieler ab
				// -------------------------------------------------------------------------
				if (person.getBoundsInParent().intersects(player.getBoundsInParent())) {
					deltaY *= -1;
				}

				// TODO ich kann bei der Kollision noch nicht entscheiden, ob der Ball mit der
				// rechten bzw linken oder oberen bzw unteren
				// Umrandung kollidiert. Bei der rechten und linken muss dann deltaX *= -1
				// stehen, bei der oberen und unteren deltaY *= -1
				// Unterscheidung notwendig!!!
				// -- Ball soll an den Bricks abprallen
				// -----------------------------------------------------------------
				houses.forEach(box -> {
					if (person.getBoundsInParent().intersects(box.getBoundsInParent())) {
						deltaY *= -1;

						if (box instanceof Home) {
							for (int i = 0; i < box.getResidents().size(); i++) {

								person.setFill(Color.AQUAMARINE);
								person.relocate(box.getBoundsInParent().getMaxX(), box.getBoundsInParent().getMaxY()); // Wenn
																														// Person
																														// in
																														// Haus
																														// eintritt
																														// gehen
																														// alle
																														// raus
								root.getChildren().add(person);

							}
						}
					}
				});
			}
		}));

		// -- Sorgt dafür, dass der BallLoop sich in einer Schleife befindet und sich
		// der Ball ständig bewegt:
		BallLoop.setCycleCount(Timeline.INDEFINITE);
		BallLoop.play();
	}

	// -- Bricks werden erzeugt und an der jeweiligen Position auf die root
	// angeheftet -> später mit root auf das Spielfeld:
	private static void createHomes(Stage primaryStage) {

		Toolkit kit = Toolkit.getDefaultToolkit();

		// -- BildschrimHöhe und Breite als Vorlage:
		double SCREEN_WIDTH = kit.getScreenSize().width;
		double SCREEN_HEIGHT = kit.getScreenSize().height;

		int rowNum = 20;
		double hosiptalChance = 0.3;

		// -- Homes werden auf Spielfeld gebracht
		for (int y = 0; y < 2; y++) { // Reihen
			for (int x = 0; x < rowNum; x++) { // Spalten

				double width = SCREEN_WIDTH / (rowNum + 1);
				double height = SCREEN_HEIGHT / (rowNum + 1);
				double positionX = (x + 1) * (width / (rowNum + 1)) + x * width;

				Random random = new Random();

				// -- Entweder Haus oder Krankenhaus wird hinzugefügt:
				if (random.nextDouble() <= hosiptalChance) {
					MedicalFacility medicalFacility = new MedicalFacility(width, height, positionX, 75 + y * 100);
					root.getChildren().add(medicalFacility);
					houses.add(medicalFacility);
				} else {
					Home home = new Home(width, height, positionX, 75 + y * 100);
					root.getChildren().add(home); // enemie wird in Liste für enemies in root hinzugefügt
					houses.add(home);
				}
			}
		}
	}

	// -- Application wird in GameView angezeigt:
	@Override
	public void start(Stage primaryStage) {
		playMusic();
		GameView.start(primaryStage, player, root); // -- delegation to View Package
	}

	// Main launched Startet die Application
	public static void startApp() {
		launch();
	}

	@Override
	public double infectionProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	//implemented Audio Interface

	public AudioPlayerInterface getAudioPlayer() {
		return this.audioPlayer;
	}

	public void setAudioPlayer(AudioPlayerInterface audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public void playMusic() {
		this.audioPlayer.playBackgroundMusic();
	}

	public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
	}
}