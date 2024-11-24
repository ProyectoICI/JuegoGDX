package io.github.juego.models;

/**
 * Clase dedicada a guardar las ajustes del juego, seguiría el patrón
 * Singleton para llevarlo a cabo para una experiencia más customizable
 * con solamente una instancia de ajustes para la totalidad del juego.
 */
public class GameSettings {
    // Variables por defecto para el juego
    private static GameSettings instance;
    private int vidasDefault;
    private int rondaDefault;
    private int velXAsteroidesDefault;
    private int velYAsteroidesDefault;
    private int cantAsteroidesDefault;

    private GameSettings() {
        this.vidasDefault = 3;
        this.rondaDefault = 1;
        this.velXAsteroidesDefault = 6;
        this.velYAsteroidesDefault = 6;
        this.cantAsteroidesDefault = 10;
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public int getVidasDefault() { return vidasDefault; }

    public void setVidasDefault(int vidasDefault) { this.vidasDefault = vidasDefault; }

    public int getRondaDefault() { return rondaDefault; }

    public void setRondaDefault(int rondaDefault) { this.rondaDefault = rondaDefault; }

    public int getVelXAsteroidesDefault() { return velXAsteroidesDefault; }

    public void setVelXAsteroidesDefault(int velXAsteroidesDefault) { this.velXAsteroidesDefault = velXAsteroidesDefault; }

    public int getVelYAsteroidesDefault() { return velYAsteroidesDefault; }

    public void setVelYAsteroidesDefault(int velYAsteroidesDefault) { this.velYAsteroidesDefault = velYAsteroidesDefault; }

    public int getCantAsteroidesDefault() { return cantAsteroidesDefault; }

    public void setCantAsteroidesDefault(int cantAsteroidesDefault) { this.cantAsteroidesDefault = cantAsteroidesDefault; }
}
