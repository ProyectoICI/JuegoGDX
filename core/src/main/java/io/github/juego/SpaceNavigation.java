package io.github.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.juego.views.PantallaMenu;


public class SpaceNavigation extends Game {
	private String nombreJuego = "Space Navigation";
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;

    // Variables por defecto para el juego
    private int vidasDefault = 3;
    private int rondaDefault = 1;
    private int velXAsteroidesDefault = 6;
    private int velYAsteroidesDefault = 6;
    private int cantAsteroidesDefault = 10;

	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
		Screen ss = new PantallaMenu(this);
		this.setScreen(ss);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}


    // Setters y Getters para las variables por defecto
    public int getVidasDefault() { return vidasDefault; }

    public void setVidasDefault(int vidasDefault) { this.vidasDefault = vidasDefault; }

    public int getRondaDefault() { return rondaDefault; }

    public void setRondaDefault(int rondaDefault) { this.rondaDefault = rondaDefault; }

    public int getVelXAsteroidesDefault() { return velXAsteroidesDefault; }

    public void setVelXAsteroidesDefault(int velXAsteroidesDefault) { this.velXAsteroidesDefault = velXAsteroidesDefault; }

    public int getVelYAsteroidesDefault() { return velYAsteroidesDefault; }

    public void setVelYAsteroidesDefault(int velYAsteroidesDefault) { this.velYAsteroidesDefault = velYAsteroidesDefault; }

    public int getCantAsteroidesDefault() { return cantAsteroidesDefault; }

    public void setCantAsteroidesDefault(int cantAsteroidesDefault) { this.cantAsteroidesDefault = cantAsteroidesDefault; }

}
