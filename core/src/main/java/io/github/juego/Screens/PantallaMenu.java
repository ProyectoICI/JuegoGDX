package io.github.juego.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.juego.SpaceNavigation;
import io.github.juego.Superclasses.Pantalla;


public class PantallaMenu extends Pantalla implements Screen {
    private Texture backgroundImage;
    private int selectedIndex = 0;
    private final String[] menuOptions = {"Jugar", "Ajustes", "Tutorial"};
	private SpaceNavigation game;
	private OrthographicCamera camera;

	public PantallaMenu(SpaceNavigation game) {
        super(game);
        this.game = game;

        initialize();
        loadAssets();
	}

	@Override
	public void render(float delta) {
        showScreen(delta);
	}

    /* -------------------------------------- */
    /*      Metodos de renderizado Custom     */
    /* -------------------------------------- */

    @Override
    protected void initialize() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
    }

    @Override
    protected void loadAssets() {
        backgroundImage = new Texture(Gdx.files.internal("background.png"));

    }

    @Override
    protected void gameLogic(float delta) {
        manejarInput();
    }

    @Override
    protected void setupUI(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundImage, 0, 0, 1200, 800);
        BitmapFont font = game.getFont();

        BitmapFont TitleFont = game.getFont();
        TitleFont.setColor(0, 1, 1, 1);
        TitleFont.draw(game.getBatch(), "Navegador Espacial", 50, 500);

        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                font.setColor(1, 1, 0, 1); // Amarillo si está seleccionado
            } else {
                font.setColor(1, 1, 1, 1);
            }
            font.draw(game.getBatch(), menuOptions[i], 140, 400 - i * 50);
        }
        game.getBatch().end();
    }

    @Override
    protected void renderLayer1(float delta) {

    }

    @Override
    protected void renderLayer2(float delta) {

    }

    @Override
    protected void renderLayer3(float delta) {

    }

    @Override
    protected void renderLayer4(float delta) {

    }

    /* -------------------------------------- */
    /*      Metodos de renderizado LibGDX     */
    /* -------------------------------------- */


    @Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

    private void manejarInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = (selectedIndex + 1) % menuOptions.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

            if (selectedIndex == 0) {
                game.setScreen(new PantallaJuego(game,
                game.getRondaDefault(),
                game.getVidasDefault(),
                0,
                game.getVelXAsteroidesDefault(),
                game.getVelYAsteroidesDefault(),
                game.getCantAsteroidesDefault()));
            } else if (selectedIndex == 1) {
                game.setScreen(new PantallaAjustes(game));
            } else if (selectedIndex == 2) {
                game.setScreen(new PantallaTutorial(game));
            }
            dispose();
        }
    }


}

