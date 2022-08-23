package Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    public Paddle(double w, double h, double x, double y, Color color){
        super(w,h,color);
        setLayoutX(x);
        setLayoutY(y);
    }
}
