package io.github.juego.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import io.github.juego.Interfaces.Colisionable;
import io.github.juego.Interfaces.ObjetosBuilder;
import io.github.juego.Superclasses.GameObject;


public class Nave extends GameObject implements Colisionable {

    private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    /*
    public Nave(float x, float y, float xVel, float yVel, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(x, y, xVel, yVel);
        sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        //spr.setOriginCenter();
        spr.setBounds(x, y, 45, 45);

    }
    */

    public static class BuilderNave implements ObjetosBuilder<Nave> {

        private float x;
        private float y;
        private float xVel;
        private float yVel;
        private Sprite spr;
        private Sound sonidoHerido;
        private Sound soundBala;
        private Texture txBala;

        public BuilderNave x(float x) {
            this.x = x;
            return this;
        }

        public BuilderNave y(float y) {
            this.y = y;
            return this;
        }

        public BuilderNave xVel(float xVel) {
            this.xVel = xVel;
            return this;
        }

        public BuilderNave yVel(float yVel) {
            this.yVel = yVel;
            return this;
        }

        public BuilderNave texture(Texture tx) {
            this.spr = new Sprite(tx);
            return this;
        }

        public BuilderNave sonidoHerido(Sound sonidoHerido) {
            this.sonidoHerido = sonidoHerido;
            return this;
        }

        public BuilderNave soundBala(Sound soundBala) {
            this.soundBala = soundBala;
            return this;
        }

        public BuilderNave txBala(Texture txBala) {
            this.txBala = txBala;
            return this;
        }

        @Override
        public Nave build() {
            return new Nave(this);
        }
    }

    public Nave(BuilderNave builder) {
        super(builder.x, builder.y, builder.xVel, builder.yVel);
        this.sonidoHerido = builder.sonidoHerido;
        this.soundBala = builder.soundBala;
        this.txBala = builder.txBala;
        this.spr = new Sprite(builder.spr);
        this.spr.setPosition(builder.x, builder.y);
        this.spr.setBounds(builder.x, builder.y, 45, 45);
    }

    @Override
    public void update(float delta) {
        float x = spr.getX();
        float y = spr.getY();
        if (!herido) {
            // que se mueva con teclado
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                xVel-=60;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                xVel+=60;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))  {
                yVel-=60;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))  {
                yVel+=60;
            }

	        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spr.setRotation(++rotacion);
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spr.setRotation(--rotacion);

	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	xVel -=Math.sin(Math.toRadians(rotacion));
	        	yVel +=Math.cos(Math.toRadians(rotacion));
	        	System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	        	xVel +=Math.sin(Math.toRadians(rotacion));
	        	yVel -=Math.cos(Math.toRadians(rotacion));

	        }*/

            // que se mantenga dentro de los bordes de la ventana
            if (x + xVel * delta < 0 || x + xVel * delta + spr.getWidth() > Gdx.graphics.getWidth())
                xVel *= -1;
            if (y + yVel * delta < 0 || y + yVel * delta + spr.getHeight() > Gdx.graphics.getHeight())
                yVel *= -1;

            spr.setPosition(x + xVel * delta, y + yVel * delta);

        } else {
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }


    // TODO: Implementar la interfaz
    @Override
    public boolean checkCollision(GameObject asteroide) {
        if(!herido && asteroide.getArea().overlaps(spr.getBoundingRectangle())){
            onCollision(asteroide);
            return true;
        }
        return false;
    }

    @Override
    public void onCollision(GameObject asteroide) {
        // rebote
        if (xVel ==0) xVel += asteroide.getVelocityX() / 2;
        if (asteroide.getVelocityX() ==0) asteroide.setVelocityX(asteroide.getVelocityX() + xVel/2);
        xVel = - xVel;
        asteroide.setVelocityX(-asteroide.getVelocityX());

        if (yVel ==0) yVel += asteroide.getVelocityY() /2;
        if (asteroide.getVelocityY() ==0) asteroide.setVelocityY(asteroide.getVelocityY() + yVel/2);
        yVel = - yVel;
        asteroide.setVelocityY(- asteroide.getVelocityY());

        //actualizar vidas y herir
        vidas--;
        herido = true;
        tiempoHerido=tiempoHeridoMax;
        sonidoHerido.play();
        if (vidas<=0) {
            destruida = true;
        }
    }

    public void draw(SpriteBatch batch) { spr.draw(batch); }

    public boolean estaDestruido() { return !herido && destruida; }
    public boolean estaHerido() { return herido; }

    public void setVidas(int vidas2) {vidas = vidas2;}
    public int getVidas() {return vidas;}

    //public boolean isDestruida() {return destruida;}
    @Override
    public float getX() {return spr.getX();}

    @Override
    public float getY() {return spr.getY();}

    public float getSpriteWidth() { return spr.getWidth(); }

    public float getSpriteHeight() { return spr.getHeight(); }


}
