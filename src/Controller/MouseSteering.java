package Controller;

import javafx.scene.layout.Pane;

public class MouseSteering {
    public static void bindPaddleMouseEvents(Paddle player, Pane root) {
        root.setOnMouseMoved(mouseEvent ->
                player.setLayoutX(mouseEvent.getSceneX()));
    }
}
