package Controller;

import Model.Person;

public abstract class CollisionStrategy {
    public abstract void performCollision(Person person);
}