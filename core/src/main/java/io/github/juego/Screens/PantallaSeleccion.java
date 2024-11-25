package io.github.juego.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.juego.SpaceNavigation;
import io.github.juego.Superclasses.Pantalla;

public class PantallaSeleccion extends Pantalla implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private Texture backgroundImage;

    private Texture naveFacil;
    private Texture naveNormal;
    private Texture naveDificil;

    private ShapeRenderer shapeRenderer;
    private int selectedIndex = 0;

    public PantallaSeleccion(SpaceNavigation game) {
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
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void loadAssets() {
        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        naveFacil = new Texture(Gdx.files.internal("naves/MainShip1.png"));
        naveNormal = new Texture(Gdx.files.internal("naves/MainShip2.png"));
        naveDificil = new Texture(Gdx.files.internal("naves/MainShip3.png"));
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

        BitmapFont dificultad = game.getFont();
        dificultad.setColor(1, 0, 0, 1);
        dificultad.draw(game.getBatch(), "Selecciona la dificultad", 50, 700);

        // Dibujamos las tres naves

        BitmapFont fontFacil = game.getFont();
        fontFacil.setColor(0, 1, 0, 1);
        fontFacil.draw(game.getBatch(), "Facil", 390, 470);
        game.getBatch().draw(naveFacil, 350, 300, 128, 108);

        BitmapFont fontNormal = game.getFont();
        fontNormal.setColor(1, 1, 0, 1);
        fontNormal.draw(game.getBatch(), "Normal", 575, 470);
        game.getBatch().draw(naveNormal, 550, 300, 128, 108);

        BitmapFont fontDificil = game.getFont();
        fontDificil.setColor(1, 0, 0, 1);
        fontDificil.draw(game.getBatch(), "Dificil", 780, 470);
        game.getBatch().draw(naveDificil, 750, 300, 128, 108);

        game.getBatch().end();

        // Draw selection box
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        switch (selectedIndex) {
            case 0:
                shapeRenderer.rect(350, 300, 128, 108);
                break;
            case 1:
                shapeRenderer.rect(550, 300, 128, 108);
                break;
            case 2:
                shapeRenderer.rect(750, 300, 128, 108);
                break;
        }
        shapeRenderer.end();
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
        backgroundImage.dispose();
        naveFacil.dispose();
        naveNormal.dispose();
        naveDificil.dispose();
        shapeRenderer.dispose();
    }

    private void manejarInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            selectedIndex = (selectedIndex - 1 + 3) % 3;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            selectedIndex = (selectedIndex + 1) % 3;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Handle selection
            switch (selectedIndex) {
                case 0:
                    game.setDificultad(1);
                    break;
                case 1:
                    game.setDificultad(2);
                    break;
                case 2:
                    game.setDificultad(3);
                    break;
            }
            // Transition to the next screen
            Screen ss = new PantallaJuego(game,
                game.getRondaDefault(),
                game.getVidasDefault(),
                0,
                game.getVelXAsteroidesDefault(),
                game.getVelYAsteroidesDefault(),
                game.getCantAsteroidesDefault());
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }
}

