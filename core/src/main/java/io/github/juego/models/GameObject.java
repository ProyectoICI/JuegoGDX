package io.github.juego.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.juego.views.PantallaJuego;

public abstract class GameObject {
    protected float x, y;  // Position
    protected float velocityX, velocityY;  // Velocity

    // Constructor
    public GameObject(float x, float y, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.velocityX = xSpeed;
        this.velocityY = ySpeed;
    }

    // Metodo abstracto para la actualizacion de posicion usando deltaTime
    public abstract void update(float delta);

    // Getters y Setters
    public float getX() { return x; }
    public float getY() { return y; }

    public float getVelocityX() { return velocityX; }
    public float getVelocityY() { return velocityY; }

    public void setVelocityX(float velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(float velocityY) { this.velocityY = velocityY; }



    // Metodo abstracto para el renderizado
    public abstract void draw(SpriteBatch batch);
}
