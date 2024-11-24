package io.github.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import io.github.juego.models.GameSettings;
import io.github.juego.Screens.PantallaMenu;


public class SpaceNavigation extends Game {
	private String nombreJuego = "Space Navigation";
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;

    public void create() {
        highScore = 0;
        batch = new SpriteBatch();

        // Generamos un font de alta calidad usando FreeType
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ubuntu-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.minFilter = TextureFilter.Linear;
        parameter.magFilter = TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();

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
