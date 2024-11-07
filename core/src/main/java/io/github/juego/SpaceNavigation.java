package io.github.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.juego.models.GameSettings;
import io.github.juego.views.PantallaMenu;


public class SpaceNavigation extends Game {
	private String nombreJuego = "Space Navigation";
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;

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
    public int getVidasDefault() { return GameSettings.getInstance().getVidasDefault(); }

    public void setVidasDefault(int vidasDefault) { GameSettings.getInstance().setVidasDefault(vidasDefault); }

    public int getRondaDefault() { return GameSettings.getInstance().getRondaDefault(); }

    public void setRondaDefault(int rondaDefault) { GameSettings.getInstance().setRondaDefault(rondaDefault); }

    public int getVelXAsteroidesDefault() { return GameSettings.getInstance().getVelXAsteroidesDefault(); }

    public void setVelXAsteroidesDefault(int velXAsteroidesDefault) { GameSettings.getInstance().setVelXAsteroidesDefault(velXAsteroidesDefault); }

    public int getVelYAsteroidesDefault() { return GameSettings.getInstance().getVelYAsteroidesDefault(); }

    public void setVelYAsteroidesDefault(int velYAsteroidesDefault) { GameSettings.getInstance().setVelYAsteroidesDefault(velYAsteroidesDefault); }

    public int getCantAsteroidesDefault() { return GameSettings.getInstance().getCantAsteroidesDefault(); }

    public void setCantAsteroidesDefault(int cantAsteroidesDefault) { GameSettings.getInstance().setCantAsteroidesDefault(cantAsteroidesDefault); }

}
