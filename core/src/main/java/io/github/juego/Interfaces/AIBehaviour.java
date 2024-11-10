package io.github.juego.Interfaces;

import io.github.juego.Superclasses.GameObject;

public interface AIBehaviour {
    /**
     * Define el estado del actor principal.
     * @param objecto Objecto actor principal al cual se le cambia el estado, seguidor del objectivo.
     * @param objetivo Objecto objetivo, el actor que le ocurren las acciones del seguidor
     * @param delta Parametro opcional deltaTime
     */
    void state(GameObject objecto, GameObject objetivo, float delta);
}
