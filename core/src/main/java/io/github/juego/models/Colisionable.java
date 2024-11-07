package io.github.juego.models;

public interface Colisionable {

    /**
     * Checkea si es que en el fotograma donde se llama el metodo ocurrió una colisión entre dos objetos.
     * @param other El objeto a checkear la colisión
     * @return <p><font color = "blue">true</font> si es que ocurre una colisión</p>
     */
    boolean checkCollision(GameObject other);

    /**
     * Realiza la lógica al ser verdadera la colisión provocada por el metodo <i>checkCollision()</i>
     * @param other El objeto a realizar la acción al colisionar
     */
    void onCollision(GameObject other);
}

