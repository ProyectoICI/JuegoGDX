package io.github.juego.Interfaces;

import io.github.juego.Superclasses.GameObject;
/**
 * Clase necesaria para implementar el patrón 'Strategy' en el juego, ya que ayudaría a cualquier elemento del juego a
 * poseer un 'estado' de su inteligencia artificial, y cambiarlos según necesarios en Runtime, haciendo la experiencia
 * considerablemente más dinamica de ser necesario.
 */
public interface AIBehaviour {
    /**
     * Define el estado del actor principal.
     * @param objecto Objecto actor principal al cual se le cambia el estado, seguidor del objectivo.
     * @param objetivo Objecto objetivo, el actor que le ocurren las acciones del seguidor
     * @param delta Parametro opcional deltaTime
     */
    void state(GameObject objecto, GameObject objetivo, float delta);
}
