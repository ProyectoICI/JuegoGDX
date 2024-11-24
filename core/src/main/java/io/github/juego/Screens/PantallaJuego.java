package io.github.juego.Screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.juego.SpaceNavigation;
import io.github.juego.models.Asteroide;
import io.github.juego.models.Misil;
import io.github.juego.models.Nave;
import io.github.juego.Superclasses.Pantalla;
import io.github.juego.strategies.ai.RoamArea;


public class PantallaJuego extends Pantalla implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sound explosionSound;
    private Sound soundBala;
	private Music gameMusic;
    private Texture txBala;
	private Texture backgroundImage;
    private int score;
	private int ronda;
	private float velXAsteroides;
	private float velYAsteroides;
	private int cantAsteroides;

	private Nave nave;
	private ArrayList<Asteroide> asteroides1 = new ArrayList<>();
	private ArrayList<Asteroide> asteroides2 = new ArrayList<>();
	private ArrayList<Misil> balas = new ArrayList<>();

    // Constructor
	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
			float velXAsteroides, float velYAsteroides, int cantAsteroides) {
        super(game);
        this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;

        loadAssets();
        initialize();

        nave.setVidas(vidas);

        crearAsteroides();

	}

    @Override
	public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        showScreen(delta);
    }

    /* -------------------------------------- */
    /*      Metodos de renderizado Custom     */
    /* -------------------------------------- */

    @Override
    protected void initialize() {
        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        Nave.BuilderNave builder = new Nave.BuilderNave();

        // Se inicializa la nave
        nave = builder
            .x(Gdx.graphics.getWidth()/2-50)
            .y(30)
            .xSpeed(0)
            .ySpeed(0)
            .texture(new Texture(Gdx.files.internal("naves/MainShip3.png")))
            .sonidoHerido(Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")))
            .txBala(new Texture(Gdx.files.internal("misiles/Rocket2.png")))
            .soundBala(Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")))
            .build();
    }

    @Override
    protected void loadAssets() {

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.4f);
        gameMusic.play();

        soundBala = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        txBala = new Texture(Gdx.files.internal("misiles/Rocket2.png"));
        backgroundImage = new Texture(Gdx.files.internal("background.png"));

    }

    @Override
    protected void gameLogic(float delta) {

        naveNotHurt(delta);

        nave.update(delta);

        collisionHandling();

        if (nave.estaDestruido()) {
            naveDestruida();
        }

        if (asteroides1.isEmpty()) {
            cargarNuevaRonda();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            crearBala();
        }

    }

    @Override
    protected void setupUI(float delta) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(1f);
        game.getFont().setColor(1,1,1,1);
        game.getFont().draw(
            batch,
            str,
            (float) (width * 0.01),
            (float) (height * 0.04));

        game.getFont().draw(
            batch,
            "Score:" + this.score,
            (float) (width * 0.18),
            (float) (height * 0.04));

        game.getFont().draw(
            batch,
            "HighScore:" + game.getHighScore(),
            (float) (width * 0.9),
            (float) (height * 0.04));

        batch.end();
    }

    @Override
    protected void renderLayer1(float delta) {
        // Dibujar las balas
        for (Misil misil : balas) {
            misil.draw(batch);
        }

        // Se dibujan los asteroides
        for (Asteroide ast : asteroides1) {
            ast.draw(batch);
        }

        nave.draw(batch);
    }

    @Override
    protected void renderLayer2(float delta) {

    }

    @Override
    protected void renderLayer3(float delta) {

    }

    @Override
    protected void renderLayer4(float delta) {
        batch.begin();
        batch.draw(backgroundImage, 0, 0, 1200, 800);
    }

    /* -------------------------------------- */
    /*      Metodos de renderizado LibGDX     */
    /* -------------------------------------- */

    @Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
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
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}

    /* -------------------------------------- */
    /*            Metodos auxiliares          */
    /* -------------------------------------- */

    private void crearAsteroides() {
        Random r = new Random();
        Asteroide.BuilderAsteroide builder = new Asteroide.BuilderAsteroide();

        for (int i = 0; i < cantAsteroides; i++) {

            float x = r.nextInt(Gdx.graphics.getWidth());
            float y = 50 + r.nextInt(Gdx.graphics.getHeight() - 50);

            int size = 20 + r.nextInt(10);

            float xSpeed = (r.nextFloat() * 2 - 1) * velXAsteroides;
            float ySpeed = (r.nextFloat() * 2 - 1) * velYAsteroides;

            Asteroide asteroideNuevo = builder
                .x(x)
                .y(y)
                .size(size)
                .xSpeed(xSpeed)
                .ySpeed(ySpeed)
                .sprite(new Texture(Gdx.files.internal("asteroides/aGreyMedium4.png")))
                .build();

            asteroideNuevo.setAIBehavior(new RoamArea());

            asteroides1.add(asteroideNuevo);
            asteroides2.add(asteroideNuevo);
        }
    }

    private void rebotesColisionesAst() {
        for (int i = 0; i < asteroides1.size(); i++) {
            Asteroide ast1 = asteroides1.get(i);
            for (int j = 0; j < asteroides2.size(); j++) {
                Asteroide ast2 = asteroides2.get(j);
                if (i < j) {
                    ast1.checkCollision(ast2);

                }
            }
        }
    }

    private void naveNotHurt(float delta) {
        if (!nave.estaHerido()) {
            // Colisiones entre balas y su destrucción
            for (int i = 0; i < balas.size(); i++) {
                Misil misil = balas.get(i);
                misil.update(delta);
                for (int j = 0; j < asteroides1.size(); j++) {
                    if (misil.checkCollision(asteroides1.get(j))) {
                        explosionSound.play();
                        asteroides1.remove(j);
                        asteroides2.remove(j);
                        j--;
                        score += 10;
                    }
                }

                if (misil.isDestroyed()) {
                    balas.remove(misil);
                    i--; // Para no saltarse 1 tras eliminar en el ArrayList
                }
            }

            // Actualizar movimiento de asteroides dentro del area
            for (Asteroide ast : asteroides1) {
                ast.update(delta);
            }

            // Colisiones entre asteroides y sus rebotes
            rebotesColisionesAst();
        }
    }

    private void naveDestruida() {
        if (score > game.getHighScore()) {
            game.setHighScore(score);
        }
        Screen ss = new PantallaGameOver(game);
        ss.resize(1200, 800);
        game.setScreen(ss);
        dispose();
    }

    // En este metodo podemos ajustar la dificultad de cada ronda directamente
    private void cargarNuevaRonda() {
        float multVelocidad = 1.5F;
        int asteroidesAgregados = 10;

        Screen ss = new PantallaJuego(game,
            ronda+1,
            nave.getVidas(),
            score,
            multVelocidad,
            multVelocidad,
            cantAsteroides + asteroidesAgregados);
        ss.resize(1200, 800);
        game.setScreen(ss);
        dispose();

    }

    private void crearBala() {
        Misil.BuilderMisil builder = new Misil.BuilderMisil();
        Misil bala = builder
            .x(nave.getX() + nave.getSpriteWidth() / 2 - 5)
            .y(nave.getY() + nave.getSpriteHeight() - 5)
            .xSpeed(0)
            .ySpeed(300)
            .sprite(txBala)
            .build();
        this.agregarBala(bala);
        soundBala.play();
    }

    private void collisionHandling() {
        // Dibujar asteroides y manejo de colisiones
        for (int i = 0; i < asteroides1.size(); i++) {
            Asteroide ast = asteroides1.get(i);

            // Perdió vida / Game over
            if (nave.checkCollision(ast)) {

                // Asteroide se destruye con el choque
                asteroides1.remove(i);
                asteroides2.remove(i);
                i--;
            }
        }
    }


    public void agregarBala(Misil bb) {
        balas.add(bb);
    }

}
