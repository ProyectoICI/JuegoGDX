package io.github.juego.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.juego.Interfaces.Colisionable;
import io.github.juego.Interfaces.ObjetosBuilder;
import io.github.juego.Superclasses.GameObject;


public class Misil extends GameObject implements Colisionable {

	private float xSpeed;
	private float ySpeed;
	private boolean destroyed = false;
	private Sprite spr;

    public Misil(BuilderMisil builder) {
        super(builder.x, builder.y, builder.xSpeed, builder.ySpeed);
        this.xSpeed = builder.xSpeed;
        this.ySpeed = builder.ySpeed;
        this.spr = new Sprite(builder.spr);
        this.spr.setPosition(builder.x, builder.y);
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
    @Override
    public boolean checkCollision(GameObject asteroide) {
        if(spr.getBoundingRectangle().overlaps(asteroide.getArea())){
            // Se destruyen ambos
            onCollision(asteroide);
            return true;
        }
        return false;
    }

    @Override
    public void onCollision(GameObject asteroide) { this.destroyed = true; }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public boolean isDestroyed() {return destroyed;}

    // ----------------------------------------------------
    // ------------ Builder estatico de Misil -------------
    // ----------------------------------------------------
    /**
     * Clase anidada BuilderMisil que nos serviría para implementar el patrón 'Builder' en el juego, simplificando
     * los constructores además de modularizando de mejor manera el código al poder tener varias formas de crear un objeto
     * especificado con las funciones que tenga el Builder.
     */
    public static class BuilderMisil implements ObjetosBuilder<Misil> {

        private float x;
        private float y;
        private float xSpeed;
        private float ySpeed;
        private Sprite spr;

        @Override
        public BuilderMisil x(float x) {
            this.x = x;
            return this;
        }

        @Override
        public BuilderMisil y(float y) {
            this.y = y;
            return this;
        }

        @Override
        public BuilderMisil xSpeed(float xSpeed) {
            this.xSpeed = xSpeed;
            return this;
        }

        @Override
        public BuilderMisil ySpeed(float ySpeed) {
            this.ySpeed = ySpeed;
            return this;
        }

        public BuilderMisil sprite(Texture tx) {
            this.spr = new Sprite(tx);
            return this;
        }

        @Override
        public Misil build() {
            return new Misil(this);
        }
    }

}
