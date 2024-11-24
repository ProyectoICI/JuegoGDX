package io.github.juego.Superclasses;

import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.juego.SpaceNavigation;

/**
 * Clase necesaria para simplificar y organizar de mejor forma los pasos que deben de seguir las clases Pantalla,
 * esto es debido a que obliga a cada clase Pantalla a seguir el orden especificado por el 'renderPipeline' para organizar
 * de mejor forma los elementos en pantalla. Esto se logra siguiendo e implementando el patrón de diseño "Template Method".
 */
public abstract class Pantalla {

    protected SpaceNavigation game;
    protected OrthographicCamera camera;

    public Pantalla(SpaceNavigation game) {
        this.game = game;
    }

    /**
     * Metodo template con los pasos en el "rendering pipeline" de la Pantalla que lo implemente
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    public final void showScreen(float delta) {
        renderPipeline(delta);
        gameLogic(delta);
    }

    public final void renderPipeline(float delta) {
        renderLayer4(delta);
        renderLayer3(delta);
        renderLayer2(delta);
        renderLayer1(delta);
        setupUI(delta);
    }

    /**
     * Metodo que inicializa el objeto Pantalla necesario y sus variables asociadas
     */
    protected abstract void initialize();

    /**
     * Metodo para cargar los assets necesarios previos al renderizado, usualmente se realiza en conjunto con la inicialización
     */
    protected abstract void loadAssets();

    /**
     * Metodo para cargar la interfaz en el renderizado, está por encima del rendering pipeline para no ocultar la misma.
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    protected abstract void setupUI(float delta);

    /**
     * Metodo encargado de manejar la lógica (de existir) del juego, ej. Empezar el juego
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    protected abstract void gameLogic(float delta);

    /**
     * Metodos auxiliares para el renderizado del juego.
     * Primera capa de renderizado, la capa con mayor prioridad. Se renderiza por encima de las demas capas.
     * Se utiliza para mantener el orden de renderizado en el rendering pipeline
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    protected abstract void renderLayer1(float delta);

    /**
     * Metodos auxiliares para el renderizado del juego.
     * Segunda capa de renderizado, se renderiza por encima de las subsiguientes capas.
     * Se utiliza para mantener el orden de renderizado en el rendering pipeline
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    protected abstract void renderLayer2(float delta);

    /**
     * Metodos auxiliares para el renderizado del juego.
     * Tercera capa de renderizado, se renderiza por encima de las subsiguientes capas.
     * Se utiliza para mantener el orden de renderizado en el rendering pipeline
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    protected abstract void renderLayer3(float delta);

    /**
     * Metodos auxiliares para el renderizado del juego.
     * Cuarta capa de renderizado. La capa con menor prioridad en el juego.
     * Se utiliza para mantener el orden de renderizado en el rendering pipeline
     * @param delta Párametro deltaTime por si es necesario en el "rendering pipeline"
     */
    protected abstract void renderLayer4(float delta);

}
