package io.github.juego.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.juego.SpaceNavigation;
import io.github.juego.Superclasses.Pantalla;


public class PantallaGameOver extends Pantalla implements Screen {
    private Texture backgroundImage;
	private SpaceNavigation game;
	private OrthographicCamera camera;

	public PantallaGameOver(SpaceNavigation game) {
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
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = crearPantallaJuego();
            game.setScreen(ss);
            dispose();
        }
    }

    @Override
    protected void setupUI(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundImage, 0, 0, 1200, 800);


        game.getFont().draw(game.getBatch(), "Game Over !!! ", 100, 400);
        game.getFont().draw(game.getBatch(), "Presiona cualquier tecla para continuar...", 100, 300);

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

    /* -------------------------------------- */
    /*            Metodos auxiliares          */
    /* -------------------------------------- */

    private Screen crearPantallaJuego() {
        Screen ss = new PantallaMenu(game);
        ss.resize(1200, 800);
        return ss;
    }

}
