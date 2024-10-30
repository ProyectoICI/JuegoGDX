package io.github.juego.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import io.github.juego.views.PantallaJuego;


public class Nave extends GameObject {

	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;

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

    // TODO: Utilizar una especie de deltaTime para actualizar la posicion de la nave
    public void draw(SpriteBatch batch) { spr.draw(batch); }

    public boolean checkCollision(Asteroide asteroide) {
        if(!herido && asteroide.getArea().overlaps(spr.getBoundingRectangle())){
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
            if (vidas<=0)
          	    destruida = true;
            return true;
        }
        return false;
    }

    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }

    public void setVidas(int vidas2) {vidas = vidas2;}
    public int getVidas() {return vidas;}

    //public boolean isDestruida() {return destruida;}
    @Override
    public float getX() {return spr.getX();}

    @Override
    public float getY() {return spr.getY();}

    public float getSpiteWidth() { return spr.getWidth(); }

    public float getSpiteHeight() { return spr.getHeight(); }

}
