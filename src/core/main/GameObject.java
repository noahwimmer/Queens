package core.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Random;

public abstract class GameObject extends MouseAdapter {

    /**The x value of the object's position */
    protected float x;
    /**The y value of the object's position*/
    protected float y;
    /**The velocity of the object in the x direction */
    protected float velX;
    /**The velocity of the object in the y direction */
    protected float velY;

    protected int id;

    protected Handler handler;

    private Random r = new Random();

    /**
     *  New & improved constructor for GameObjects. Used for random spawns
     * @param spawnLocation Rectangle bounds for spawn. <code>Constants.spawnZone</code> is for the entire screen.
     */
    public GameObject(Rectangle spawnLocation) {
        this.x = (r.nextFloat() * (spawnLocation.width - spawnLocation.x)) + (spawnLocation.x - 25);
        this.y = (r.nextFloat() * (spawnLocation.height - spawnLocation.y)) + (spawnLocation.y - 25);
    }

    /**
     * Old but useful constructor. Used to spawn objects in not random locations.
     * @param x X position of spawn
     * @param y Y position of spawn
     */
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Every time the game does a loop this method is called.
     */
    public abstract void tick();

    /**
     * This method handles the rendering of the objects
     * @param g Graphics component inside
     */
    public abstract void render(Graphics g);

    /**
     * Call this method to return the hitbox of the object.
     * @return The bouds (hitbox) of the object.
     */
    public abstract Rectangle getBounds();

    /**
     * Use this method to get the X position of the object.
     * @return The X position of the object
     */
    public float getX() {
        return x;
    }

    /**
     * Use this method to set a new X position.
     * @param x The new X position of the object
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Use this method to get the Y position of the object
     * @return The Y position of the object
     */
    public float getY() {
        return y;
    }

    /**
     * Use this method to set a new Y position of the object.
     * @param y The new Y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Use this method to get the current velocity in the X direction.
     * @return The velocity in the X position
     */
    public float getVelX() {
        return velX;
    }

    /**
     * Use this method to set a new velocity in the X direction
     * @param velX The new velocity
     */
    public void setVelX(float velX) {
        this.velX = velX;
    }

    /**
     * Use this method to get the current velocity in the Y direction.
     * @return The velocity in the Y position
     */
    public float getVelY() {
        return velY;
    }

    /**
     * Use this method to set a new velocity in the Y direction
     * @param velY The new velocity
     */
    public void setVelY(float velY) {
        this.velY = velY;
    }

    public int getID() {
        return id;
    }
}