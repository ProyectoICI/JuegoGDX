package io.github.juego.models;

public interface Colisionable {
    void checkCollision(GameObject other);
    void onCollision(GameObject other);
}

