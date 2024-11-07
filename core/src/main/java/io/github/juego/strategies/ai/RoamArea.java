package io.github.juego.strategies.ai;

import com.badlogic.gdx.Gdx;
import io.github.juego.models.AIBehaviour;
import io.github.juego.models.GameObject;

/**
 * Esta estrategia es la común, los asteroides simplemente vagan en el espacio con velocidades especificas ya dadas
 */
public class RoamArea implements AIBehaviour {

    @Override
    public void state(GameObject object, GameObject objective, float delta) {
        object.setX((object.getVelocityX() * delta) + object.getX());
        object.setY((object.getVelocityY() * delta) + object.getY());



        if (object.getX()+object.getVelocityX() < 0 || object.getX()+object.getVelocityX()+object.getSpr().getWidth() > Gdx.graphics.getWidth())
            object.setVelocityX(object.getVelocityX() * -1);
        if (object.getY()+object.getVelocityY() < 0 || object.getY()+object.getVelocityY()+object.getSpr().getHeight() > Gdx.graphics.getHeight())
            object.setVelocityY(object.getVelocityY() * -1);


        object.getSpr().setPosition(object.getX(), object.getY());
    }
}
