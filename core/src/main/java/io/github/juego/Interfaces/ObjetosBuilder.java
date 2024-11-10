package io.github.juego.Interfaces;

public interface ObjetosBuilder<T> {

    /**
     * Construye el objeto en base al patrón de diseño 'Builder' luego de construirlo paso a paso.
     * @return El objeto a construir, dependiendo del tipo que se le asigne.
     */
    T build();
}
