package io.github.juego.models;

public interface Colisionable {
    boolean checkCollision(GameObject other);
    void onCollision(GameObject other);
}

