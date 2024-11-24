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
import io.github.juego.models.GameSettings;

public class PantallaAjustes extends Pantalla implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private Texture backgroundImage;
    private int selectedIndex = 0;
    private boolean editing = false;

    public PantallaAjustes(SpaceNavigation game) {
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

        BitmapFont escapeFont = game.getFont();
        escapeFont.setColor(1, 0, 0, 1);
        escapeFont.draw(game.getBatch(), "Presiona ESCAPE para volver atras.", 50, 700);

        BitmapFont font = game.getFont();
        GameSettings settings = GameSettings.getInstance();

        String[] settingsLabels = {
            "Vidas: " + settings.getVidasDefault(),
            "Ronda: " + settings.getRondaDefault(),
            "Velocidad X Asteroides: " + settings.getVelXAsteroidesDefault(),
            "Velocidad Y Asteroides: " + settings.getVelYAsteroidesDefault(),
            "Cantidad de Asteroides: " + settings.getCantAsteroidesDefault()
        };

        for (int i = 0; i < settingsLabels.length; i++) {
            if (i == selectedIndex) {
                font.setColor(1, 1, 0, 1); // Highlight selected option
            } else {
                font.setColor(1, 1, 1, 1); // Normal color for other options
            }
            font.draw(game.getBatch(), settingsLabels[i], 100, 600 - i * 50);
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
    }

    private void manejarInput() {
        if (Gdx.input.isKeyJustPressed((Input.Keys.ESCAPE))) {
            Screen ss = new PantallaMenu(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        if (!editing) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                selectedIndex = (selectedIndex - 1 + 5) % 5;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                selectedIndex = (selectedIndex + 1) % 5;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                editing = true;
            }
        } else {
            GameSettings settings = GameSettings.getInstance();
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                modifySetting(settings, selectedIndex, -1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                modifySetting(settings, selectedIndex, 1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                editing = false;
            }
        }
    }

    private void modifySetting(GameSettings settings, int index, int delta) {
        switch (index) {
            case 0:
                settings.setVidasDefault(Math.max(1, settings.getVidasDefault() + delta));
                break;
            case 1:
                settings.setRondaDefault(Math.max(1, settings.getRondaDefault() + delta));
                break;
            case 2:
                settings.setVelXAsteroidesDefault(Math.max(1, settings.getVelXAsteroidesDefault() + delta));
                break;
            case 3:
                settings.setVelYAsteroidesDefault(Math.max(1, settings.getVelYAsteroidesDefault() + delta));
                break;
            case 4:
                settings.setCantAsteroidesDefault(Math.max(1, settings.getCantAsteroidesDefault() + delta));
                break;
        }
    }

}


