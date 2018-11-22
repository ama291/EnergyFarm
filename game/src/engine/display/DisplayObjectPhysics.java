package engine.display;
import java.util.HashMap;

public class DisplayObjectPhysics {
    private int mass;
    private int xPos;
    private int yPos;
    private int xVelocity;
    private int yVelocity;
    private HashMap<String, Integer> xForces;
    private HashMap<String, Integer> yForces;
    private DisplayObject parent;

    // Constructor
    public DisplayObjectPhysics(int mass, int xPos, int yPos) {
        xForces = new HashMap<String, Integer>();
        yForces = new HashMap<String, Integer>();
        setMass(mass);
        setXPos(xPos);
        setYPos(yPos);
    }

    // Getter methods
    public int getMass() { return mass; }
    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    public int getXVelocity() { return xVelocity; }
    public int getYVelocity() {return yVelocity; }
    public DisplayObject getParent() { return parent; }

    //Setter Methods
    public void setMass(int mass) { this.mass = mass; }
    public void setXPos(int xPos) { this.xPos = xPos; }
    public void setYPos(int yPos) { this.yPos = yPos; }
    public void setXVelocity(int xV) {this.xVelocity = xV; }
    public void setYVelocity(int yV) {this.yVelocity = yV; }
    public void setParent(DisplayObject p) { this.parent = p; }

    //Add/Set or Remove Forces
    public void setXForce(String forceName, Integer force) { xForces.put(forceName, force); }
    public void setYForce(String forceName, Integer force) { yForces.put(forceName, force); }
    public void removeXForce(String forceName) { xForces.remove(forceName); }
    public void removeYForce(String forceName) { yForces.remove(forceName); }

    // Get Net Forces, Acceleration
    public int getNetXForce() {
        int netXForce = 0;
        for (Integer k : xForces.values()) {
            netXForce += k;
        }
        return netXForce;
    }
    public int getNetYForce() {
        int netYForce = 0;
        for (Integer k : yForces.values()) {
            netYForce += k;
        }
        return netYForce;
    }
    public int getXAcceleration() {
        return getNetXForce() / mass;
    }
    public int getYAcceleration() {
        return getNetYForce() / mass;
    }

    public void incrementTick() {
        this.xPos = parent.getPosition().x;
        this.yPos = parent.getPosition().y;
        int xAcc = this.getXAcceleration();
        int yAcc = this.getYAcceleration();
        this.xPos += xVelocity;
        this.yPos += yVelocity;
        this.xVelocity += xAcc;
        this.yVelocity += yAcc;
    }

}
