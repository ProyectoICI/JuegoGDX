package io.github.juego.Superclasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.juego.Interfaces.AIBehaviour;

public abstract class GameObject {
    protected float x, y;  // Position
    protected float velocityX, velocityY;  // Velocity
    private Sprite spr;
    private AIBehaviour aiBehaviour;
    private GameObject objective;

    // Constructor
    public GameObject(float x, float y, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.velocityX = xSpeed;
        this.velocityY = ySpeed;
        objective = null;
    }


    public void stateAI(GameObject objective, float delta) {
        if (aiBehaviour != null) {
            aiBehaviour.state(this, objective, delta);
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
    public AIBehaviour getAIBehavior() { return aiBehaviour; }

    public void setObjective(GameObject objective) { this.objective = objective; }

    // Metodo abstracto para el renderizado
    public abstract void draw(SpriteBatch batch);
}
