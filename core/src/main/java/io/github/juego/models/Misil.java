package io.github.juego.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Misil extends GameObject implements Colisionable{

	private float xSpeed;
	private float ySpeed;
	private boolean destroyed = false;
	private Sprite spr;

    public Misil(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed);
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update(float delta) {
        spr.setPosition(spr.getX()+xSpeed * delta, spr.getY()+ySpeed * delta);
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }

    }

    // TODO: Implementar la interfaz
    public boolean checkCollision(GameObject asteroide) {
        if(spr.getBoundingRectangle().overlaps(asteroide.getArea())){
            // Se destruyen ambos
            onCollision(asteroide);
            return true;
        }
        return false;
    }

    public void onCollision(GameObject asteroide) { this.destroyed = true; }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public boolean isDestroyed() {return destroyed;}

}
