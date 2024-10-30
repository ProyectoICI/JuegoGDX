package io.github.juego.views;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
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


public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private float velXAsteroides;
	private float velYAsteroides;
	private int cantAsteroides;

	private Nave nave;
	private ArrayList<Asteroide> asteroides1 = new ArrayList<>();
	private ArrayList<Asteroide> asteroides2 = new ArrayList<>();
	private ArrayList<Misil> balas = new ArrayList<>();

    // Constructor que utiliza variables predefinidas para inicializar el juego al inicio
    public PantallaJuego(SpaceNavigation game) {
        this.game = game;
        this.ronda = game.getRondaDefault();
        this.velXAsteroides = game.getVelXAsteroidesDefault();
        this.velYAsteroides = game.getVelYAsteroidesDefault();
        this.cantAsteroides = game.getCantAsteroidesDefault();
        this.score = 0;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        //Inicialización de assets
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.4f);
        gameMusic.play();

        // Se inicializa la nave
        nave = new Nave(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
            new Texture(Gdx.files.internal("Rocket2.png")),
            Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(game.getVidasDefault());

        // Se crean los asteroides
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            Asteroide bb = new Asteroide(
                1+r.nextInt((int)Gdx.graphics.getWidth()),
                50+r.nextInt((int)Gdx.graphics.getHeight()-50),
                20+r.nextInt(10),
                velXAsteroides+r.nextFloat(4),
                velYAsteroides+r.nextFloat(4),
                new Texture(Gdx.files.internal("aGreyMedium4.png")));
            asteroides1.add(bb);
            asteroides2.add(bb);
        }
    }

    // Constructor para cargar las pantallas de juego de rondas subsiguientes
	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
			float velXAsteroides, float velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;

		batch = game.getBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.1f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //

		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();

	    // cargar imagen de la nave, 64x64
	    nave = new Nave(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
	    				new Texture(Gdx.files.internal("Rocket2.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas);
        //crear asteroides
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	        Asteroide asteroideNuevo = new Asteroide(
                    r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10),
                    velXAsteroides+r.nextFloat(4),
                    velYAsteroides+r.nextFloat(4),
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));
	  	    asteroides1.add(asteroideNuevo);
	  	    asteroides2.add(asteroideNuevo);
	  	}
	}

    // TODO: Utilizar posiciones relativas a las dimensiones de la pantalla, no pixeles
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}

    @Override
	public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();
        if (!nave.estaHerido()) {

            // Colisiones entre balas y su destrucción
            for (int i = 0; i < balas.size(); i++) {
                Misil b = balas.get(i);
                b.update();
                for (int j = 0; j < asteroides1.size(); j++) {
                    if (b.checkCollision(asteroides1.get(j))) {
                        explosionSound.play();
                        asteroides1.remove(j);
                        asteroides2.remove(j);
                        j--;
                        score += 10;
                    }
                }

                //   b.draw(batch);
                if (b.isDestroyed()) {
                    balas.remove(b);
                    i--; // Para no saltarse 1 tras eliminar en el ArrayList
                }
            }
            // Actualizar movimiento de asteroides dentro del area
            for (Asteroide ast : asteroides1) {
                ast.update(delta);
            }

            // Colisiones entre asteroides y sus rebotes
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

        // Dibujar las balas
        for (Misil b : balas) {
            b.draw(batch);
        }

        nave.draw(batch, this, delta);

        // Dibujar asteroides y manejo de colisiones
        for (int i = 0; i < asteroides1.size(); i++) {
            Asteroide b = asteroides1.get(i);
            b.draw(batch);

            // Perdió vida / Game over
            if (nave.checkCollision(b)) {

                // Asteroide se destruye con el choque
                asteroides1.remove(i);
                asteroides2.remove(i);
                i--;
            }
        }

        if (nave.estaDestruido()) {
            if (score > game.getHighScore())
                game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
        batch.end();

        // Nivel completado!
        if (asteroides1.isEmpty()) {
            cargarNuevaRonda();
        }

    }

    // En este metodo podemos ajustar la dificultad de cada ronda directamente
    private void cargarNuevaRonda() {
        float multVelocidad = 1.5F;
        int asteroidesAgregados = 10;

        Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score,
            multVelocidad, multVelocidad, cantAsteroides + asteroidesAgregados);
        ss.resize(1200, 800);
        game.setScreen(ss);
        dispose();

    }

    public boolean agregarBala(Misil bb) {
    	return balas.add(bb);
    }

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

}
