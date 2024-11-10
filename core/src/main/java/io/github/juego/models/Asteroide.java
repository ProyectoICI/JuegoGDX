package io.github.juego.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.juego.Interfaces.AIBehaviour;
import io.github.juego.Interfaces.Colisionable;
import io.github.juego.Interfaces.ObjetosBuilder;
import io.github.juego.Superclasses.GameObject;


public class Asteroide extends GameObject implements Colisionable {

    private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;
    private Sprite spr;
    private AIBehaviour aiBehaviour;

    public static class BuilderAsteroide implements ObjetosBuilder<Asteroide> {
        private float x;
        private float y;
        private float xSpeed;
        private float ySpeed;
        private float size;
        private Sprite spr;
        private AIBehaviour aiBehaviour;

        public BuilderAsteroide x(float x) {
            this.x = x;
            return this;
        }

        public BuilderAsteroide y(float y) {
            this.y = y;
            return this;
        }

        public BuilderAsteroide size(float size) {
            this.size = size;
            return this;
        }

        public BuilderAsteroide xSpeed(float xSpeed) {
            this.xSpeed = xSpeed;
            return this;
        }

        public BuilderAsteroide ySpeed(float ySpeed) {
            this.ySpeed = ySpeed;
            return this;
        }

        public BuilderAsteroide sprite(Texture tx) {
            this.spr = new Sprite(tx);
            return this;
        }

        @Override
        public Asteroide build() {
            return new Asteroide(this);
        }
    }

    public Asteroide(BuilderAsteroide builder) {
        super(builder.x, builder.y, builder.xSpeed, builder.ySpeed);

        this.x = builder.x;
        this.y = builder.y;
        this.xSpeed = builder.xSpeed;
        this.ySpeed = builder.ySpeed;
        this.spr = new Sprite(builder.spr);
        this.aiBehaviour = builder.aiBehaviour;

        // Validate that the edge of the sphere does not go out of bounds
        if (x - builder.size < 0) this.x = x + builder.size;
        if (x + builder.size > Gdx.graphics.getWidth()) this.x = x - builder.size;
        if (y - builder.size < 0) this.y = y + builder.size;
        if (y + builder.size > Gdx.graphics.getHeight()) this.y = y - builder.size;

        spr.setPosition(this.x, this.y);
        this.setVelocityX(this.xSpeed);
        this.setVelocityY(this.ySpeed);
    }

    @Override
    public void update(float delta) {
        if (aiBehaviour != null) {
            aiBehaviour.state(this, null, delta);
        }
    }

    @Override
    public boolean checkCollision(GameObject asteroide) {
        if(spr.getBoundingRectangle().overlaps(asteroide.getSpr().getBoundingRectangle())){
            onCollision(asteroide);
            return true;
        }
        return false;
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

    @Override
    public float getX() { return super.getX(); }

    @Override
    public float getY() { return super.getY(); }

    @Override
    public void setX(float x) { super.setX(x); }

    @Override
    public void setY(float y) { super.setY(y); }

    @Override
    public Rectangle getArea() { return spr.getBoundingRectangle(); }

    @Override
    public void draw(SpriteBatch batch) { spr.draw(batch); }

    @Override
    public float getVelocityX() { return super.getVelocityX(); }

    @Override
    public void setVelocityX(float velocityX) { super.setVelocityX(velocityX); }

    @Override
    public float getVelocityY() { return super.getVelocityY(); }

    @Override
    public void setVelocityY(float velocityY) { super.setVelocityY(velocityY); }

    @Override
    public Sprite getSpr() { return spr; }

    @Override
    public void setSpr(Sprite spr) { super.setSpr(spr); }

    @Override
    public void setAIBehavior(AIBehaviour aiBehavior) { this.aiBehaviour = aiBehavior; }

    @Override
    public AIBehaviour getAIBehavior() { return aiBehaviour; }

}
