package io.github.juego.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected float x, y;  // Position
    protected float velocityX, velocityY;  // Velocity
    private Sprite spr;
    private AIBehaviour aiBehaviour;

    // Constructor
    public GameObject(float x, float y, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.velocityX = xSpeed;
        this.velocityY = ySpeed;
    }


    public void stateAI(GameObject object, float delta) {
        if (aiBehaviour != null) {
            aiBehaviour.state(this, object, delta);
        }
    }


    public abstract void update(float delta);

    // Getters y Setters
    public float getX() { return x; }
    public float getY() { return y; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    public float getVelocityX() { return velocityX; }
    public float getVelocityY() { return velocityY; }

    public void setVelocityX(float velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(float velocityY) { this.velocityY = velocityY; }

    public Rectangle getArea() { return spr.getBoundingRectangle(); }

    public Sprite getSpr() { return spr; }
    public void setSpr(Sprite spr) { this.spr = spr; }

    public void setAIBehavior(AIBehaviour aiBehavior) { this.aiBehaviour = aiBehavior; }

    // Metodo abstracto para el renderizado
    public abstract void draw(SpriteBatch batch);
}
