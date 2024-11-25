package io.github.juego.strategies.ai;

import com.badlogic.gdx.Gdx;
import io.github.juego.Interfaces.AIBehaviour;
import io.github.juego.Superclasses.GameObject;

/**
 * Esta estrategia es utilizada por los asteroides fugaces que aparecen contra el jugador.
 */
public class ChasePlayer implements AIBehaviour {
    private boolean initialized = false;
    private float targetX;
    private float targetY;
    private float speed = 200; // Set a fixed speed for the asteroid

    @Override
    public void state(GameObject object, GameObject objective, float delta) {
        if (!initialized) {
            // Get the initial coordinates of the player's spaceship
            targetX = objective.getX();
            targetY = objective.getY();
            initialized = true;

            // Calculate the direction vector from the asteroid to the target coordinates
            float directionX = targetX - object.getX();
            float directionY = targetY - object.getY();

            // Normalize the direction vector
            float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            directionX /= length;
            directionY /= length;

            // Set the velocity of the asteroid to move towards the target coordinates
            object.setVelocityX(directionX * speed);
            object.setVelocityY(directionY * speed);
        }

        // Update the position of the asteroid
        object.setX((object.getVelocityX() * delta) + object.getX());
        object.setY((object.getVelocityY() * delta) + object.getY());

        object.getSpr().setPosition(object.getX(), object.getY());
    }
}


