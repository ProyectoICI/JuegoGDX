package io.github.juego.Screens;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import io.github.juego.SpaceNavigation;
import io.github.juego.models.Asteroide;
import io.github.juego.models.Misil;
import io.github.juego.models.Nave;
import io.github.juego.Superclasses.Pantalla;
import io.github.juego.strategies.ai.RoamArea;
import io.github.juego.strategies.ai.ChasePlayer;

import static com.badlogic.gdx.math.MathUtils.random;


public class PantallaJuego extends Pantalla implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
    private Sprite sprChase;
	private Sound explosionSound;
    private Sound soundBala;
    private Sound sonidoHerido;
	private Music gameMusic;
    private Texture txBala;
    private Texture txNave;
    private Texture txChase;
	private Texture backgroundImage;
    private int score;
	private int ronda;
	private float velXAsteroides;
	private float velYAsteroides;
	private int cantAsteroides;
    private double multDificultad;

	private Nave nave;
	private ArrayList<Asteroide> asteroides1 = new ArrayList<>();
	private ArrayList<Asteroide> asteroides2 = new ArrayList<>();
    private ArrayList<Asteroide> astEnemigos = new ArrayList<>();
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

        // Inicializamos los multiplicadores de dificultad
        switch (game.getDificultad()) {
            case 1:
                multDificultad = 0.7;
                break;
            case 2:
                multDificultad = 1;
                break;
            case 3:
                multDificultad = 1.3;
                break;
        }

        // Se inicializa la nave
        nave = builder
            .x(Gdx.graphics.getWidth()/2-50)
            .y(30)
            .xSpeed(0)
            .ySpeed(0)
            .texture(txNave)
            .sonidoHerido(sonidoHerido)
            .txBala(txBala)
            .soundBala(soundBala)
            .build();

        // Inicializamos la tarea para crear asteroides enemigos
        checkCrearEnemigos();
    }

    @Override
    protected void loadAssets() {

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
        sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        soundBala = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.4f);
        gameMusic.play();
        backgroundImage = new Texture(Gdx.files.internal("background.png"));

        txChase = new Texture(Gdx.files.internal("asteroides/asteroideEnemigo.png"));
        sprChase = new Sprite(txChase);
        sprChase.setSize(74, 70);


        // Cambiamos el modelo de la nave dependiendo de la dificultad.
        switch (game.getDificultad()) {
            case 1:
                txNave = new Texture(Gdx.files.internal("naves/MainShip1.png"));
                txBala = new Texture(Gdx.files.internal("misiles/Rocket1.png"));
                break;
            case 2:
                txNave = new Texture(Gdx.files.internal("naves/MainShip2.png"));
                txBala = new Texture(Gdx.files.internal("misiles/Rocket2.png"));
                break;
            case 3:
                txNave = new Texture(Gdx.files.internal("naves/MainShip3.png"));
                txBala = new Texture(Gdx.files.internal("misiles/Rocket3.png"));
                break;
        }

    }

    @Override
    protected void gameLogic(float delta) {

        naveNotHurt(delta);

        nave.update(delta);

        collisionHandling();

        checkEnemyOOB();

        System.out.println(astEnemigos);

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
            (float) (width * 0.2),
            (float) (height * 0.04));

        game.getFont().draw(
            batch,
            "HighScore:" + game.getHighScore(),
            (float) (width * 0.88),
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

        for (Asteroide ast : astEnemigos) {
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

        ArrayList<Texture> txAsteroides = new ArrayList<>();
        txAsteroides.add(new Texture(Gdx.files.internal("asteroides/aGreySmall.png")));
        txAsteroides.add(new Texture(Gdx.files.internal("asteroides/aGreyMedium4.png")));

        for (int i = 0; i < (cantAsteroides * multDificultad); i++) {

            float x = r.nextInt(Gdx.graphics.getWidth());
            float y = 50 + r.nextInt(Gdx.graphics.getHeight() - 50);

            int size = 20 + r.nextInt(10);

            float xSpeed = (float) ((r.nextFloat() * 2 - 1) * (velXAsteroides * multDificultad));
            float ySpeed = (float) ((r.nextFloat() * 2 - 1) * (velYAsteroides * multDificultad));

            Texture randomTexture = txAsteroides.get(r.nextInt(txAsteroides.size()));
            Sprite randomSpr = new Sprite(randomTexture);

            Asteroide asteroideNuevo = builder
                .x(x)
                .y(y)
                .size(size)
                .xSpeed(xSpeed)
                .ySpeed(ySpeed)
                .sprite(randomSpr)
                .build();

            asteroideNuevo.setAIBehavior(new RoamArea());

            asteroides1.add(asteroideNuevo);
            asteroides2.add(asteroideNuevo);
        }
    }

    private void crearAsteroideEnemigo() {
        if (random.nextInt(30) == 0) {
            Asteroide.BuilderAsteroide builderEnemigo = new Asteroide.BuilderAsteroide();

            float x = random.nextInt(Gdx.graphics.getWidth());
            float y = Gdx.graphics.getHeight();

            int size = 5 + random.nextInt(4);

            float xSpeed = ((random.nextFloat() * 2 - 1) * (20));
            float ySpeed = ((random.nextFloat() * 2 - 1) * (20));

            Asteroide astEnemigo = builderEnemigo
                .x(x)
                .y(y)
                .size(size)
                .xSpeed(xSpeed)
                .ySpeed(ySpeed)
                .sprite(sprChase)
                .build();

            astEnemigo.setAIBehavior(new ChasePlayer());
            astEnemigo.setObjective(nave);

            astEnemigos.add(astEnemigo);
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

    private void checkCrearEnemigos() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                crearAsteroideEnemigo();
            }

        },1,1);
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

                for (int k = 0; k < astEnemigos.size(); k++) {
                    if (misil.checkCollision(astEnemigos.get(k))) {
                        explosionSound.play();
                        astEnemigos.remove(k);
                        k--;
                        score += 20;
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

            for (Asteroide astEn : astEnemigos) {
                astEn.update(delta);
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
        float multVelocidad = (float) (1.5F * multDificultad);
        int asteroidesAgregados = (int) (7 * multDificultad);

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
            .ySpeed((float) (300 * (0.7 / multDificultad)))
            .sprite(txBala)
            .build();
        this.agregarBala(bala);
        soundBala.play();
    }

    private void checkEnemyOOB() {
        astEnemigos.removeIf(astEn -> astEn.getX() < 0 || astEn.getX() > Gdx.graphics.getWidth() || astEn.getY() < 0 || astEn.getY() > Gdx.graphics.getHeight());
    }

    private void collisionHandling() {
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
        for (int i = 0; i < astEnemigos.size(); i++) {
            Asteroide astEn = astEnemigos.get(i);

            if (nave.checkCollision(astEn)) {
                astEnemigos.remove(i);
                i--;
            }
        }
    }


    public void agregarBala(Misil bb) {
        balas.add(bb);
    }

}
