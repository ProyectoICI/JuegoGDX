package io.github.juego.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Asteroide extends GameObject implements Colisionable {
	private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;
    private Sprite spr;

    public Asteroide(float x, float y, int size, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed);

        spr = new Sprite(tx);
        this.x = x;

        //validar que borde de esfera no quede fuera
        if (x-size < 0) this.x = x+size;
        if (x+size > Gdx.graphics.getWidth())this.x = x-size;

        this.y = y;
        //validar que borde de esfera no quede fuera
        if (y-size < 0) this.y = y+size;
        if (y+size > Gdx.graphics.getHeight())this.y = y-size;

        spr.setPosition(x, y);
        this.setVelocityX(xSpeed);
        this.setVelocityY(ySpeed);
    }

    // TODO: Utilizar una especie de deltaTime para la actualización de posición
    @Override
    public void update(float delta) {
        x += (getVelocityX() * delta);
        y += (getVelocityY() * delta);

        if (x+getVelocityX() < 0 || x+getVelocityX()+spr.getWidth() > Gdx.graphics.getWidth())
        	setVelocityX(getVelocityX() * -1);
        if (y+getVelocityY() < 0 || y+getVelocityY()+spr.getHeight() > Gdx.graphics.getHeight())
        	setVelocityY(getVelocityY() * -1);
        spr.setPosition(x, y);
    }


    // TODO: Posiblemente tambien se deba de usar deltaTime para el checkeo de rebotes
    @Override
    public void checkCollision(GameObject asteroide) {
        if(spr.getBoundingRectangle().overlaps(asteroide.getSpr().getBoundingRectangle())){
            onCollision(asteroide);
        }
    }

    @Override
    public void onCollision(GameObject asteroide) {
        // rebote
        if (getVelocityX() ==0) setVelocityX(getVelocityX() + asteroide.getVelocityX()/2);
        if (asteroide.getVelocityX() ==0) asteroide.setVelocityX(asteroide.getVelocityX() + getVelocityX()/2);
        setVelocityX(- getVelocityX());
        asteroide.setVelocityX(-asteroide.getVelocityX());

        if (getVelocityY() ==0) setVelocityY(getVelocityY() + asteroide.getVelocityY()/2);
        if (asteroide.getVelocityY() ==0) asteroide.setVelocityY(asteroide.getVelocityY() + getVelocityY()/2);
        setVelocityY(- getVelocityY());
        asteroide.setVelocityY(- asteroide.getVelocityY());
    }


    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    @Override
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }



    @Override
    public float getVelocityX() {
        return super.getVelocityX();
    }

    @Override
    public void setVelocityX(float velocityX) {
        super.setVelocityX(velocityX);
    }

    @Override
    public float getVelocityY() {
        return super.getVelocityY();
    }

    @Override
    public void setVelocityY(float velocityY) { super.setVelocityY(velocityY); }

    @Override
    public Sprite getSpr() { return spr; }

    @Override
    public void setSpr(Sprite spr) { this.spr = spr; }

}
