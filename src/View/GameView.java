package View;

import Controller.GameBoard;
import Controller.MouseSteering;
import Controller.Paddle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class GameView {

    // Angaben über Anzahl der Spalten und Reihen für Homes und Medicalfacilities
    private static double SCREEN_WIDTH;
    private static double SCREEN_HEIGHT;
    private static final Toolkit kit = Toolkit.getDefaultToolkit();

    public static void start(Stage primaryStage, Paddle player, Pane root) {

        //-- BildschrimHöhe und Breite als Vorlage:
        SCREEN_WIDTH = kit.getScreenSize().width;
        SCREEN_HEIGHT = kit.getScreenSize().height;

        //-- Fenster wird erstellt:
        Scene scene = new Scene(GameBoard.content(primaryStage), SCREEN_WIDTH*0.9, SCREEN_HEIGHT*0.9); // Fenster wird initilisiert
        primaryStage.setScene(scene);                               // fenster wird definiert
        primaryStage.initStyle((StageStyle.UNDECORATED));           // obere Leiste ist weg (keine Funktionalität)
        primaryStage.show();                                        // Fenster wird angezeigt

        //-- Mausbewegung wird mit dem Paddle verbunden:
        MouseSteering.bindPaddleMouseEvents(player, root);
    }
}
