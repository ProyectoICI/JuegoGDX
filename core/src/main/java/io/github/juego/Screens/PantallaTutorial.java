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

public class PantallaTutorial extends Pantalla implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private Texture tutorialImage;
    private Texture backgroundImage;

    public PantallaTutorial(SpaceNavigation game) {
        super(game);
        this.game = game;

        initialize();
        loadAssets();
    }

    @Override
    public void render(float delta) { showScreen(delta); }

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
        tutorialImage = new Texture(Gdx.files.internal("Tutorial.png"));
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
        game.getBatch().draw(tutorialImage, 0, 0, 1200, 800);
        BitmapFont font = game.getFont();
        font.setColor(1, 0, 0, 1);
        font.draw(game.getBatch(), "Presiona cualquier tecla para volver atras.", 50, 700);

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
    public void resize(int i, int i1) {

    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void manejarInput() {
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaMenu(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }
}
