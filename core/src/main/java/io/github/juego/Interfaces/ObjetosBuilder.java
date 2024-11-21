package io.github.juego.Interfaces;

public interface ObjetosBuilder<T> {

    /**
     * Metodo para definir la posición x del objeto en el respectivo builder.
     * @return La instancia del constructor mismo.
     */
    ObjetosBuilder<T> x(float x);

    /**
     * Metodo para definir la posición y del objeto en el respectivo builder.
     * @return La instancia del constructor mismo.
     */
    ObjetosBuilder<T> y(float y);

    /**
     * Metodo para definir la velocidad x del objeto en el respectivo builder.
     * @return La instancia del constructor mismo.
     */
    ObjetosBuilder<T> xSpeed(float xSpeed);

    /**
     * Metodo para definir la velocidad y del objeto en el respectivo builder.
     * @return La instancia del constructor mismo.
     */
    ObjetosBuilder<T> ySpeed(float ySpeed);

    /**
     * Construye el objeto en base al patrón de diseño 'Builder' luego de construirlo paso a paso.
     * @return El objeto a construir, dependiendo del tipo que se le asigne.
     */
    T build();
}
